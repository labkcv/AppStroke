package id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.ac.ub.filkom.sekcv.appstroke.R;
import id.ac.ub.filkom.sekcv.appstroke.model.custom.android.support.v4.app.TitledFragment;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager> created by :
 * Name         : syafiq
 * Date / Time  : 30 August 2016, 12:17 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Home extends TitledFragment
{
    public static final String TAG = "controller.mainpage.viewpager.Home";
    public static final int    ID  = 0b000;

    private Unbinder unbinder;

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static Home newInstance(String title)
    {
        final Home fragment = new Home();
        fragment.setTitle(title);
        Log.i("Home", "controller.mainpage.viewpager.Home.newInstace");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i("Home", "controller.mainpage.viewpager.Home.onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i("Home", "controller.mainpage.viewpager.Home.onCreateView");
        View view = inflater.inflate(R.layout.mainpage_viewpager_home, container, false);
        this.unbinder = ButterKnife.bind(this, view);
        return view;
    }

    //----------------------------------------------------------------------------------------------


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.i("Home", "controller.mainpage.viewpager.Home.onAttach");
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        this.unbinder.unbind();
        Log.i("Home", "controller.mainpage.viewpager.Home.onDestroyView");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.i("Home", "controller.mainpage.viewpager.Home.onActivityCreated");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.i("Home", "controller.mainpage.viewpager.Home.onStart");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i("Home", "controller.mainpage.viewpager.Home.onResume");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.i("Home", "controller.mainpage.viewpager.Home.onPause");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.i("Home", "controller.mainpage.viewpager.Home.onStop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.i("Home", "controller.mainpage.viewpager.Home.onSaveInstanceState");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        Log.i("Home", "controller.mainpage.viewpager.Home.onViewStateRestored");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i("Home", "controller.mainpage.viewpager.Home.onDestroy");
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        Log.i("Home", "controller.mainpage.viewpager.Home.onDetach");
    }
}
