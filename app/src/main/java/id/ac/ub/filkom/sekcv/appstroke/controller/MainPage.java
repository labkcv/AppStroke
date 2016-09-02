package id.ac.ub.filkom.sekcv.appstroke.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import butterknife.ButterKnife;
import id.ac.ub.filkom.sekcv.appstroke.R;
import id.ac.ub.filkom.sekcv.appstroke.controller.adapter.MainPageContentAdapter;
import id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager.Diagnose;
import id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager.Home;
import id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager.MedicalRecord;
import id.ac.ub.filkom.sekcv.appstroke.model.db.entity.User;

public class MainPage extends AppCompatActivity
{
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private User      user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.mainpage_container);


        JodaTimeAndroid.init(this);
        ButterKnife.bind(this);

        this.setDummyUser();
        this.setToolbar();
        this.setActivity(super.getResources().getConfiguration().orientation, 0);
    }

    private void setDummyUser()
    {
        Context con;
        try
        {
            con = super.createPackageContext("com.labkcv.selabkc", 0);
            SharedPreferences pref = con.getSharedPreferences("CekLogin", Context.MODE_PRIVATE);
            Log.d("PREF DATA", pref.getString("date", "date"));
            Log.d("PREF DATA", pref.getString("name", "name"));
            Log.d("PREF DATA", pref.getString("password", "password"));
            Log.d("PREF DATA", pref.getString("email", "email"));
            Log.d("PREF DATA1", LocalDate.parse(pref.getString("date", "1993-12-16"), DateTimeFormat.forPattern("yyyy-MM-dd")).toString());
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
                final MainPageContentAdapter pagerAdapter = new MainPageContentAdapter(getSupportFragmentManager(), 3);
                pagerAdapter.addFragment(Home.newInstance("Home"));
                pagerAdapter.addFragment(Diagnose.newInstance("Diagnose", this.user.getId()));
                pagerAdapter.addFragment(MedicalRecord.newInstance("Medical Record", this.user.getId()));

                this.viewPager = (ViewPager) findViewById(R.id.viewpager);
                viewPager.setAdapter(pagerAdapter);

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
        return viewPager;
    }
}
