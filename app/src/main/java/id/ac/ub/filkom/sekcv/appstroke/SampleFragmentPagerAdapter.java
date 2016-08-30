package id.ac.ub.filkom.sekcv.appstroke;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager.Diagnose;

/**
 * Created by syafiq on 8/10/16.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter
{
    final   int    PAGE_COUNT  = 1;
    private String tabTitles[] = new String[] {"Tab1"};
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context)
    {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position)
    {
        return Diagnose.newInstance("Diagnose", 1);
    }


    @Override
    public CharSequence getPageTitle(int position)
    {
        // Generate title based on item position
        return tabTitles[position];
    }
}
