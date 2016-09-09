package id.ac.ub.filkom.sekcv.appstroke.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
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

import butterknife.ButterKnife;
import id.ac.ub.filkom.sekcv.appstroke.R;
import id.ac.ub.filkom.sekcv.appstroke.controller.adapter.MainPageContentAdapter;
import id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager.Diagnose;
import id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager.Home;
import id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager.MedicalRecord;
import id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager.Treatment;
import id.ac.ub.filkom.sekcv.appstroke.model.dataset.Stroke;
import id.ac.ub.filkom.sekcv.appstroke.model.db.entity.User;

public class MainPage extends AppCompatActivity
{
    private ViewPager                                                    viewPager;
    private TabLayout                                                    tabLayout;
    private User                                                         user;
    private Stroke                                                       stroke;
    private id.ac.ub.filkom.sekcv.appstroke.model.db.model.MedicalRecord medicalRecordModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.mainpage_container);


        JodaTimeAndroid.init(this);
        ButterKnife.bind(this);

        this.setToolbar();
        this.initializeModel();
    }

    private void initializeModel()
    {
        this.medicalRecordModel = new id.ac.ub.filkom.sekcv.appstroke.model.db.model.MedicalRecord(super.getApplicationContext());
        this.intializeDatabase();
    }

    private void intializeDatabase()
    {
        new AsyncTask<Void, Void, Void>()
        {
            private ProgressDialog progressDialog;

            @Override
            protected Void doInBackground(Void... voids)
            {
                try
                {
                    MainPage.this.medicalRecordModel.open();
                }
                catch(SQLException ignored)
                {
                    Toast.makeText(MainPage.this, MainPage.super.getResources().getString(R.string.mainpage_viewpager_label_error_db), Toast.LENGTH_SHORT).show();
                    MainPage.this.finish();
                }
                return null;
            }

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                MainPage.this.runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        progressDialog = new ProgressDialog(MainPage.this, R.style.AppTheme_Dark_Dialog);
                        progressDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Defining the database, please wait...");
                        progressDialog.show();

                    }
                });
            }

            @Override
            protected void onPostExecute(Void aVoid)
            {
                super.onPostExecute(aVoid);
                this.progressDialog.dismiss();
                MainPage.this.setUser();
                MainPage.this.getLatestMedicalRecordData();
                MainPage.this.setActivity(MainPage.super.getResources().getConfiguration().orientation, 0);
            }
        }.execute();
    }

    private void getLatestMedicalRecordData()
    {
        id.ac.ub.filkom.sekcv.appstroke.model.db.entity.MedicalRecord record = this.medicalRecordModel.getLatestDataByUser(this.user.getId());
        if(record != null)
        {
            this.stroke = Stroke.newInstanceFromMedicalRecord(record);
        }
    }

    private void setUser()
    {
        Context con;
        try
        {
            con = super.createPackageContext("com.labkcv.selabkc", 0);
            SharedPreferences pref = con.getSharedPreferences("CekLogin", Context.MODE_PRIVATE);
            this.user = new User(1, LocalDate.parse(pref.getString("date", "1993-12-16"), DateTimeFormat.forPattern("yyyy-MM-dd")), pref.getString("name", "Muhammad Syafiq"), pref.getString("email", "syafiq.rezpector@gmail.com"), pref.getString("password", "473bb7b11dd3c3a67a446f7743b4d3af"), true);
        }
        catch(PackageManager.NameNotFoundException e)
        {
            Log.e("Not data shared", e.toString());
            this.user = new User(1, LocalDate.parse("1993-12-16"), "Muhammad Syafiq", "syafiq.rezpector@gmail.com", "473bb7b11dd3c3a67a446f7743b4d3af", true);
        }
    }

    private void setToolbar()
    {
        final Toolbar toolbar = (Toolbar) super.findViewById(R.id.mainpage_toolbar);
        super.setSupportActionBar(toolbar);
        final ActionBar actionBar = super.getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayShowTitleEnabled(false);
            toolbar.setContentInsetStartWithNavigation(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainpage_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.i("MainPage", "MainPage.onSaveInstanceState : Save Out");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("MainPage", "MainPage.onSaveInstanceState : Save In");
    }

    public void setActivity(final int orientation, final int tabNumber)
    {
        switch(orientation)
        {
            case Configuration.ORIENTATION_PORTRAIT:
            {
                final MainPageContentAdapter pagerAdapter = new MainPageContentAdapter(getSupportFragmentManager(), 4);
                pagerAdapter.addFragment(Home.newInstance("Home"));
                pagerAdapter.addFragment(Diagnose.newInstance("Diagnose"));
                pagerAdapter.addFragment(MedicalRecord.newInstance("Medical Record"));
                pagerAdapter.addFragment(Treatment.newInstance("Treatment"));

                this.viewPager = (ViewPager) findViewById(R.id.viewpager);
                this.viewPager.setAdapter(pagerAdapter);
                this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
                {

                    // This method will be invoked when a new page becomes selected.
                    @Override
                    public void onPageSelected(int position)
                    {
                        Log.i("MainPage", "MainPage.setActivity : addOnPageChangeListener : onPageSelected " + position);
                    }

                    // This method will be invoked when the current page is scrolled
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
                    {
                        // Code goes here
                    }

                    // Called when the scroll state changes:
                    // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
                    @Override
                    public void onPageScrollStateChanged(int state)
                    {
                        // Code goes here
                    }
                });
                ;

                // Give the TabLayout the ViewPager
                this.tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

                tabLayout.setupWithViewPager(viewPager);
                Log.d("OrientationChanged", "Create Portrait");
            }
            break;
            case Configuration.ORIENTATION_LANDSCAPE:
            {
            }
        }
        viewPager.setCurrentItem(tabNumber);

    }

    public ViewPager getViewPager()
    {
        return this.viewPager;
    }

    public User getUser()
    {
        return this.user;
    }

    public id.ac.ub.filkom.sekcv.appstroke.model.db.model.MedicalRecord getMedicalRecordModel()
    {
        return this.medicalRecordModel;
    }

    public Stroke getStrokeData()
    {
        return this.stroke;
    }

    public void setStrokeData(Stroke strokeData)
    {
        this.stroke = strokeData;
    }
}
