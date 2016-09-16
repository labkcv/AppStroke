package id.ac.ub.filkom.sekcv.appstroke.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
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
import android.widget.Toast;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;

import butterknife.ButterKnife;
import id.ac.ub.filkom.sekcv.appstroke.R;
import id.ac.ub.filkom.sekcv.appstroke.controller.adapter.MainPageContentAdapter;
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
import id.ac.ub.filkom.sekcv.appstroke.model.util.TaskDelegatable;

public class MainPage extends AppCompatActivity
{
    public static final String CLASSNAME = "MainPage";
    public static final String CLASSPATH = "controller";
    public static final String TAG       = CLASSPATH + "." + CLASSNAME;
    public static final int    ID        = 0x100;

    private ViewPager              viewPager;
    private MainPageContentAdapter pagerAdapter;

    private Entity_User                                user;
    private ObservableStroke                           stroke;
    private ObservableLinkedList<Entity_MedicalRecord> medicalRecordData;
    private Model_MedicalRecord                        medicalRecordModel;
    private boolean                                    isActivityReady;

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
        ButterKnife.bind(this);

        this.setToolbar();

        this.setUser();
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

        final int id = item.getItemId();

        if(id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState)
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onSaveInstanceState");

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull final Bundle savedInstanceState)
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onRestoreInstanceState");

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onStart");

        super.onStart();
    }

    @Override
    protected void onRestart()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onRestart");

        super.onRestart();
    }

    @Override
    protected void onResume()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onResume");

        super.onResume();
    }

    @Override
    protected void onPause()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onPause");

        super.onPause();
    }

    @Override
    protected void onStop()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onStop");

        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onDestroy");

        this.isActivityReady = false;
        this.medicalRecordModel.close();
        super.onDestroy();
    }

    //----------------------------------------------------------------------------------------------
    //---App Interface Dependency
    //----------------------------------------------------------------------------------------------

    private void initializeStartup()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".initializeStartup");

        this.medicalRecordModel = new Model_MedicalRecord(super.getApplicationContext());
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
            context = super.createPackageContext("com.labkcv.selabkc", 0);
            final SharedPreferences pref = context.getSharedPreferences("CekLogin", Context.MODE_PRIVATE);
            this.user = new Entity_User(1, LocalDate.parse(pref.getString("date", "1993-12-16"), DateTimeFormat.forPattern("yyyy-MM-dd")), pref.getString("name", "Muhammad Syafiq"), pref.getString("email", "syafiq.rezpector@gmail.com"), pref.getString("password", "473bb7b11dd3c3a67a446f7743b4d3af"), true);

            Log.d(MainPage.CLASSNAME, MainPage.TAG + ".setUser : Data Shared");
        }
        catch(PackageManager.NameNotFoundException e)
        {
            this.user = new Entity_User(1, LocalDate.parse("1993-12-16"), "Muhammad Syafiq", "syafiq.rezpector@gmail.com", "473bb7b11dd3c3a67a446f7743b4d3af", true);

            Log.d(MainPage.CLASSNAME, MainPage.TAG + ".setUser : No Data Shared");
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
            actionBar.setDisplayShowTitleEnabled(false);
            toolbar.setContentInsetStartWithNavigation(0);
        }
    }

    public void setActivity(final int orientation, final int tabNumber)
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".setActivity");

        switch(orientation)
        {
            case Configuration.ORIENTATION_PORTRAIT:
            {
                this.pagerAdapter = new MainPageContentAdapter(super.getSupportFragmentManager(), 4);
                this.pagerAdapter.addFragment(Home.newInstance("Home"));
                this.pagerAdapter.addFragment(Diagnose.newInstance("Diagnose"));
                this.pagerAdapter.addFragment(MedicalRecord.newInstance("Medical Record"));
                this.pagerAdapter.addFragment(Treatment.newInstance("Treatment"));

                this.viewPager = (ViewPager) findViewById(R.id.viewpager);
                this.viewPager.setAdapter(pagerAdapter);

                final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
                tabLayout.setupWithViewPager(this.viewPager);

                Log.d(MainPage.CLASSNAME, MainPage.TAG + ".setActivity : PORTRAIT");
            }
            break;
            case Configuration.ORIENTATION_LANDSCAPE:
            {
            }
        }
        this.viewPager.setCurrentItem(tabNumber);
    }

    //----------------------------------------------------------------------------------------------
    //---App User Function
    //----------------------------------------------------------------------------------------------

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

    public MainPageContentAdapter getPagerAdapter()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".getPagerAdapter");

        return this.pagerAdapter;
    }

    public ObservableLinkedList<Entity_MedicalRecord> getMedicalRecordData()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".getMedicalRecordData");

        return this.medicalRecordData;
    }

    public void updateMedicalRecordData()
    {
        Log.d(MainPage.CLASSNAME, MainPage.TAG + ".updateMedicalRecordData");

        this.medicalRecordData.replaceList(this.medicalRecordModel.getDataByUser(this.user.getId()));
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

        this.isActivityReady = (this.user != null) && (this.medicalRecordModel != null) && this.medicalRecordModel.isDatabaseReady();
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
