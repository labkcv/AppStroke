package id.ac.ub.filkom.sekcv.appstroke.controller.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import id.ac.ub.filkom.sekcv.appstroke.model.custom.android.support.v4.app.TitledFragment;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.controller> created by :
 * Name         : syafiq
 * Date / Time  : 29 August 2016, 3:13 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class MainPageContentAdapter extends FragmentStatePagerAdapter
{
    final   TitledFragment[] fragments;
    private int              counter;

    public MainPageContentAdapter(FragmentManager fm, int totalTab)
    {
        super(fm);
        this.fragments = new TitledFragment[totalTab];
        this.counter = -1;
    }

    public void addFragment(TitledFragment fragment)
    {
        this.fragments[++this.counter] = fragment;
    }

    @Override
    public Fragment getItem(int position)
    {
        return this.fragments[position];
    }

    @Override
    public int getCount()
    {
        return this.fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return this.fragments[position].getTitle();
    }
}
