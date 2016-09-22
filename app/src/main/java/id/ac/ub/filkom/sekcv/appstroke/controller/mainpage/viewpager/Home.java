package id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.LinkedList;

import id.ac.ub.filkom.sekcv.appstroke.R;
import id.ac.ub.filkom.sekcv.appstroke.controller.MainPage;
import id.ac.ub.filkom.sekcv.appstroke.model.custom.android.support.v4.app.TitledFragment;
import id.ac.ub.filkom.sekcv.appstroke.model.util.TaskDelegatable;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager> created by :
 * Name         : syafiq
 * Date / Time  : 30 August 2016, 12:17 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Home extends TitledFragment
{
    public static final String CLASSNAME = "Home";
    public static final String CLASSPATH = "controller.mainpage.viewpager";
    public static final String TAG       = CLASSPATH + "." + CLASSNAME;
    public static final int    ID        = 0b000;

    private MainPage root;

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static Home newInstance(String title)
    {
        Log.d(Home.CLASSNAME, Home.TAG + ".newInstance");

        final Home fragment = new Home();
        fragment.setTitle(title);
        return fragment;
    }

    //----------------------------------------------------------------------------------------------
    //---App Life Cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(Home.CLASSNAME, Home.TAG + ".onCreate");

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(Home.CLASSNAME, Home.TAG + ".onCreateView");

        final View view = inflater.inflate(R.layout.mainpage_viewpager_home, container, false);
        this.root = ((MainPage) super.getActivity());

        new StartUpTask().execute();

        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        Log.d(Home.CLASSNAME, Home.TAG + ".onAttach");

        super.onAttach(context);
    }

    @Override
    public void onDestroyView()
    {
        Log.d(Home.CLASSNAME, Home.TAG + ".onDestroyView");

        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        Log.d(Home.CLASSNAME, Home.TAG + ".onActivityCreated");

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        Log.d(Home.CLASSNAME, Home.TAG + ".onStart");

        super.onStart();
    }

    @Override
    public void onResume()
    {
        Log.d(Home.CLASSNAME, Home.TAG + ".onResume");

        super.onResume();
    }

    @Override
    public void onPause()
    {
        Log.d(Home.CLASSNAME, Home.TAG + ".onPause");

        super.onPause();
    }

    @Override
    public void onStop()
    {
        Log.d(Home.CLASSNAME, Home.TAG + ".onStop");

        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@Nullable Bundle outState)
    {
        Log.d(Home.CLASSNAME, Home.TAG + ".onSaveInstanceState");

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        Log.d(Home.CLASSNAME, Home.TAG + ".onViewStateRestored");

        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDestroy()
    {
        Log.d(Home.CLASSNAME, Home.TAG + ".onDestroy");

        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        Log.d(Home.CLASSNAME, Home.TAG + ".onDetach");

        super.onDetach();
    }

    //----------------------------------------------------------------------------------------------
    //---App Interface Dependency
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //---App User Function
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //---Class Helper
    //----------------------------------------------------------------------------------------------

    private final class StartUpTask extends AsyncTask<Void, Void, Void>
    {
        public static final String CLASSNAME = "StartUpTask";

        private final LinkedList<TaskDelegatable> delegations;

        public StartUpTask(TaskDelegatable... delegations)
        {
            Log.d(Home.CLASSNAME, Home.TAG + "." + StartUpTask.CLASSNAME + ".Constructor");

            this.delegations = new LinkedList<>();
            Collections.addAll(this.delegations, delegations);
        }

        @Override
        protected void onPreExecute()
        {
            Log.d(Home.CLASSNAME, Home.TAG + "." + StartUpTask.CLASSNAME + ".onPreExecute");

            super.onPreExecute();
        }

        @Nullable
        @SuppressWarnings({"StatementWithEmptyBody", "UnnecessarySemicolon"})
        @Override
        protected Void doInBackground(Void... voids)
        {
            Log.d(Home.CLASSNAME, Home.TAG + "." + StartUpTask.CLASSNAME + ".doInBackground");

            while(!Home.this.root.isActivityReady())
            {
                ;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            Log.d(Home.CLASSNAME, Home.TAG + "." + StartUpTask.CLASSNAME + ".onPostExecute");

            for(final TaskDelegatable delegation : this.delegations)
            {
                delegation.delegate();
            }
        }
    }
}
