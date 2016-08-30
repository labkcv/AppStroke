package id.ac.ub.filkom.sekcv.appstroke;

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

import butterknife.ButterKnife;
import id.ac.ub.filkom.sekcv.appstroke.controller.MainPageContentAdapter;
import id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager.Diagnose;
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
        this.user = new User(1, LocalDate.parse("1993-12-16"), "Muhammad Syafiq", "syafiq.rezpector@gmail.com", "473bb7b11dd3c3a67a446f7743b4d3af", true);
    }

    private void setToolbar()
    {
        final Toolbar toolbar = (Toolbar) super.findViewById(R.id.mainpage_toolbar);
        super.setSupportActionBar(toolbar);
        final ActionBar actionBar = super.getSupportActionBar();
        if (actionBar != null)
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
        if (id == R.id.action_settings)
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
        switch (orientation)
        {
            case Configuration.ORIENTATION_PORTRAIT:
            {
                final MainPageContentAdapter pagerAdapter = new MainPageContentAdapter(getSupportFragmentManager(), 1);
                pagerAdapter.addFragment(Diagnose.newInstance("Diagnose", this.user.getId()));

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
                this.viewPager = (ViewPager) findViewById(R.id.viewpager);
                viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(), this));

                // Give the TabLayout the ViewPager
                this.tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
                tabLayout.setupWithViewPager(viewPager);
                Log.d("OrientationChanged", "Create Landscape");
            }
        }
        viewPager.setCurrentItem(tabNumber);
    }
    /*public void setActivity(final int orientation, final int tabNumber)
    {
        switch (orientation)
        {
            case Configuration.ORIENTATION_PORTRAIT:
            {
                this.viewPager = (ViewPager) findViewById(R.id.viewpager);
                viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(), this));

                // Give the TabLayout the ViewPager
                this.tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

                tabLayout.setupWithViewPager(viewPager);
                Log.d("OrientationChanged", "Create Portrait");
            }
            break;
            case Configuration.ORIENTATION_LANDSCAPE:
            {
                this.viewPager = (ViewPager) findViewById(R.id.viewpager);
                viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(), this));

                // Give the TabLayout the ViewPager
                this.tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
                tabLayout.setupWithViewPager(viewPager);
                Log.d("OrientationChanged", "Create Landscape");
            }
        }
        viewPager.setCurrentItem(tabNumber);
    }*/
}
