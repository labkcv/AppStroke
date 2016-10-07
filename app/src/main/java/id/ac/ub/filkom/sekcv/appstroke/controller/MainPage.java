package id.ac.ub.filkom.sekcv.appstroke.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;
import org.joda.time.Years;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import id.ac.ub.filkom.sekcv.appstroke.R;
import id.ac.ub.filkom.sekcv.appstroke.controller.adapter.MainPageContentAdapter;
import id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager.Chart;
import id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager.Diagnose;
import id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager.Home;
import id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager.MedicalRecord;
import id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager.Treatment;
import id.ac.ub.filkom.sekcv.appstroke.model.custom.java.util.ObservableLinkedList;
import id.ac.ub.filkom.sekcv.appstroke.model.dataset.ObservableStroke;
import id.ac.ub.filkom.sekcv.appstroke.model.dataset.Stroke;
import id.ac.ub.filkom.sekcv.appstroke.model.dataset.StrokeMetadata;
import id.ac.ub.filkom.sekcv.appstroke.model.dataset.StrokeParameter;
import id.ac.ub.filkom.sekcv.appstroke.model.db.entity.Entity_MedicalRecord;
import id.ac.ub.filkom.sekcv.appstroke.model.db.entity.Entity_User;
import id.ac.ub.filkom.sekcv.appstroke.model.db.model.Model_MedicalRecord;
import id.ac.ub.filkom.sekcv.appstroke.model.db.model.Model_User;
import id.ac.ub.filkom.sekcv.appstroke.model.util.TaskDelegatable;

public class MainPage extends AppCompatActivity
{
    public static final String CLASSNAME = "MainPage";
    public static final String CLASSPATH = "controller";
    public static final String TAG       = CLASSPATH + "." + CLASSNAME;
    public static final int    ID        = 0x100;

    private ViewPager              viewPager;

    private Entity_User                                user;
    private ObservableStroke                           stroke;
    private ObservableLinkedList<Entity_MedicalRecord> medicalRecordData;
    private Model_MedicalRecord                        medicalRecordModel;
    private Model_User                                 userModel;
    private boolean                                    isActivityReady;
    private int                                        age;

    //----------------------------------------------------------------------------------------------
    //---App Life Cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onCreate");

        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.mainpage_container);

        JodaTimeAndroid.init(this);

        this.setToolbar();

        this.medicalRecordData = new ObservableLinkedList<>();
        this.stroke = new ObservableStroke(null);

        this.initializeStartup();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull final Menu menu)
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onCreateOptionsMenu");

        super.getMenuInflater().inflate(R.menu.mainpage_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item)
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onOptionsItemSelected");

        final int cItem = item.getItemId();
        if(cItem == R.id.mainpage_toolbar_menu_help)
        {
            this.onToolbarHelpMenuPressed();
        }
        else if(cItem == R.id.mainpage_toolbar_menu_about_us)
        {
            this.onToolbarAboutUsMenuPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onDestroy");

        this.isActivityReady = false;
        this.medicalRecordModel.close();
        super.onDestroy();
    }

    @Override
    public void onBackPressed()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onBackPressed");

        super.onBackPressed();
        super.finish();
    }

    //----------------------------------------------------------------------------------------------
    //---App Interface Dependency
    //----------------------------------------------------------------------------------------------

    private void initializeStartup()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".initializeStartup");

        this.medicalRecordModel = new Model_MedicalRecord(super.getApplicationContext());
        this.userModel = new Model_User(super.getApplicationContext());
        this.initializeModel(new TaskDelegatable()
        {
            @Override
            public void delegate()
            {
                MainPage.this.setActivity(MainPage.super.getResources().getConfiguration().orientation, Home.ID);
            }
        });
    }

    private void getLatestMedicalRecordData()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".getLatestMedicalRecordData");

        if(this.medicalRecordData.getLists().size() > 0)
        {
            this.stroke.updateStroke(Stroke.newInstanceFromMedicalRecord(this.medicalRecordData.getLists().get(0)));
        }
    }

    public void setUser()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".setUser");

        final Context context;
        try
        {
            context = super.createPackageContext("id.ac.ub.filkom.se.kcv.astech.astechlauncher", 0);
            final SharedPreferences pref  = context.getSharedPreferences("CekLogin", Context.MODE_PRIVATE);
            final String            email = pref.getString("email", null);
            if((email != null))
            {
                final String name      = pref.getString("name", null);
                final String password  = pref.getString("password", null);
                final String birthdate = pref.getString("date", null);

                if((this.user = this.userModel.getUserByEmail(email)) == null)
                {
                    Log.d(MainPage.CLASSNAME, MainPage.TAG + ".setUser : Register User Account");

                    this.userModel.registerUser(name, birthdate, email, password);
                }
                else
                {
                    Log.d(MainPage.CLASSNAME, MainPage.TAG + ".setUser : Update User Account");

                    this.userModel.updateUserAccount(this.user.getId(), name, birthdate, email, password);
                }
                this.user = this.userModel.getUserByEmail(email);
                if(this.user != null)
                {
                    this.age = this.calculateUserAge(this.user.getBirthDate());
                }
            }
            else
            {
                Toast.makeText(this, super.getResources().getString(R.string.mainpage_limited_mode), Toast.LENGTH_LONG).show();
            }
        }
        catch(PackageManager.NameNotFoundException e)
        {
            Toast.makeText(this, super.getResources().getString(R.string.mainpage_limited_mode), Toast.LENGTH_LONG).show();
        }

        this.updateActivityState();
    }

    private void setToolbar()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".setToolbar");

        final Toolbar toolbar = (Toolbar) super.findViewById(R.id.mainpage_toolbar);
        super.setSupportActionBar(toolbar);
        final ActionBar actionBar = super.getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            toolbar.setContentInsetStartWithNavigation(4);
            toolbar.setNavigationOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    MainPage.this.onBackPressed();
                }
            });
        }
    }

    public void setActivity(final int orientation, final int tabNumber)
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".setActivity");

        switch(orientation)
        {
            case Configuration.ORIENTATION_PORTRAIT:
            {
                final Resources        resources = super.getResources();
                MainPageContentAdapter pagerAdapter;
                if(this.user == null)
                {
                    pagerAdapter = new MainPageContentAdapter(super.getSupportFragmentManager(), 3);
                    pagerAdapter.addFragment(Home.newInstance(resources.getString(R.string.mainpage_fragment_title_home)));
                    pagerAdapter.addFragment(Diagnose.newInstance(resources.getString(R.string.mainpage_fragment_title_diagnose)));
                    pagerAdapter.addFragment(Treatment.newInstance(resources.getString(R.string.mainpage_fragment_title_treatment)));
                }
                else
                {
                    pagerAdapter = new MainPageContentAdapter(super.getSupportFragmentManager(), 5);
                    pagerAdapter.addFragment(Home.newInstance(resources.getString(R.string.mainpage_fragment_title_home)));
                    pagerAdapter.addFragment(Diagnose.newInstance(resources.getString(R.string.mainpage_fragment_title_diagnose)));
                    pagerAdapter.addFragment(MedicalRecord.newInstance(resources.getString(R.string.mainpage_fragment_title_medical_record)));
                    pagerAdapter.addFragment(Chart.newInstance(resources.getString(R.string.mainpage_fragment_title_chart)));
                    pagerAdapter.addFragment(Treatment.newInstance(resources.getString(R.string.mainpage_fragment_title_treatment)));
                }

                this.viewPager = (ViewPager) findViewById(R.id.viewpager);
                this.viewPager.setAdapter(pagerAdapter);

                final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
                tabLayout.setupWithViewPager(this.viewPager);

                Log.d(MainPage.CLASSNAME, MainPage.TAG + ".setActivity : PORTRAIT");
                break;
            }
        }
        this.viewPager.setCurrentItem(tabNumber);
    }

    //----------------------------------------------------------------------------------------------
    //---App User Function
    //----------------------------------------------------------------------------------------------

    private void onToolbarHelpMenuPressed()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onToolbarHelpMenuPressed");

        final Intent intent = new Intent(this, HelpPage.class);
        intent.putExtra(HelpPage.IS_USER_LOGGED_IN, this.user != null);
        super.startActivity(intent);
    }

    private void onToolbarAboutUsMenuPressed()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onToolbarAboutUsMenuPressed");

        final Intent intent = new Intent(this, AboutUsPage.class);
        super.startActivity(intent);
    }

    public ViewPager getViewPager()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".getViewPager");

        return this.viewPager;
    }

    public Entity_User getUser()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".getUser");

        return this.user;
    }

    public Model_MedicalRecord getMedicalRecordModel()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".getMedicalRecordModel");

        return this.medicalRecordModel;
    }

    public ObservableStroke getStrokeData()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".getStrokeData");

        return this.stroke;
    }

    public void updateStroke(@NonNull Stroke stroke)
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".updateStroke");

        this.stroke.updateStroke(stroke);
    }

    public void updateStroke(StrokeParameter parameter, StrokeMetadata metadata)
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".updateStroke");

        this.stroke.updateStroke(parameter, metadata);
    }

    public ObservableLinkedList<Entity_MedicalRecord> getMedicalRecordData()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".getMedicalRecordData");

        return this.medicalRecordData;
    }

    public void updateMedicalRecordData()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".updateMedicalRecordData");

        if(this.user != null)
        {
            this.medicalRecordData.replaceList(this.medicalRecordModel.getDataByUser(this.user.getId()));
        }
        else
        {
            this.medicalRecordData.replaceList(new ArrayList<Entity_MedicalRecord>(0));
        }
    }

    private synchronized void initializeModel(TaskDelegatable... delegations)
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".initializeModel");

        if(!this.medicalRecordModel.isDatabaseReady())
        {
            new StartUpTask(delegations).execute();
        }
        else
        {
            for(final TaskDelegatable delegation : delegations)
            {
                delegation.delegate();
            }
        }
    }

    public boolean isActivityReady()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".isActivityReady");

        return this.isActivityReady;
    }

    private void updateActivityState()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".updateActivityState");

        this.isActivityReady = (this.medicalRecordModel != null) && this.medicalRecordModel.isDatabaseReady();
    }

    /**
     * According to TestJodaTime#testYearsBetween unit testing
     *
     * @param birthDate : User birth date
     * @return : User age
     */
    public int calculateUserAge(@NonNull final LocalDate birthDate)
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".calculateUserAge");

        final LocalDateTime birthTimestamp  = birthDate.toLocalDateTime(LocalTime.MIDNIGHT);
        final LocalDateTime nowTimestamp    = LocalDateTime.now();
        int                 age             = Years.yearsBetween(birthTimestamp, nowTimestamp).getYears();
        final int           secondDifferent = Seconds.secondsBetween(birthTimestamp.plusYears(age), nowTimestamp).getSeconds();
        if(secondDifferent > 0)
        {
            ++age;
        }
        return age;
    }

    public int getUserAge()
    {
        return this.age;
    }

    //----------------------------------------------------------------------------------------------
    //---Class Helper
    //----------------------------------------------------------------------------------------------

    private class StartUpTask extends AsyncTask<Void, Void, Void>
    {
        public static final String CLASSNAME = "StartUpTask";

        private ProgressDialog              progressDialog;
        private LinkedList<TaskDelegatable> delegations;

        public StartUpTask(TaskDelegatable... delegations)
        {
            Log.d(MainPage.CLASSNAME, MainPage.TAG + "." + StartUpTask.CLASSNAME + ".Constructor");

            this.delegations = new LinkedList<>();
            Collections.addAll(this.delegations, delegations);
        }

        @Override
        protected void onPreExecute()
        {
            Log.d(MainPage.CLASSNAME, MainPage.TAG + "." + StartUpTask.CLASSNAME + ".onPreExecute");

            super.onPreExecute();
            MainPage.this.runOnUiThread(new Runnable()
            {
                public void run()
                {
                    progressDialog = new ProgressDialog(MainPage.this, R.style.AppTheme_Dark_Dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage(MainPage.super.getResources().getString(R.string.mainpage_database_loading_announcement));
                    progressDialog.show();

                }
            });
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            Log.d(MainPage.CLASSNAME, MainPage.TAG + "." + StartUpTask.CLASSNAME + ".doInBackground");

            try
            {
                MainPage.this.medicalRecordModel.open();
                MainPage.this.userModel.open();
                MainPage.this.updateActivityState();
            }
            catch(SQLException ignored)
            {
                Toast.makeText(MainPage.this, MainPage.super.getResources().getString(R.string.mainpage_viewpager_label_error_db), Toast.LENGTH_SHORT).show();
                MainPage.this.finish();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            Log.d(MainPage.CLASSNAME, MainPage.TAG + "." + StartUpTask.CLASSNAME + ".onPostExecute");

            this.progressDialog.dismiss();
            MainPage.this.setUser();
            MainPage.this.updateMedicalRecordData();
            MainPage.this.getLatestMedicalRecordData();
            for(final TaskDelegatable delegation : this.delegations)
            {
                delegation.delegate();
            }
            super.onPostExecute(aVoid);
        }
    }
}
