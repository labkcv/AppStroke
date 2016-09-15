package id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.util.Attributes;
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.ac.ub.filkom.sekcv.appstroke.R;
import id.ac.ub.filkom.sekcv.appstroke.controller.MainPage;
import id.ac.ub.filkom.sekcv.appstroke.controller.adapter.RecyclerViewAdapter;
import id.ac.ub.filkom.sekcv.appstroke.model.custom.android.support.v4.app.TitledFragment;
import id.ac.ub.filkom.sekcv.appstroke.model.custom.java.util.ObservableLinkedList;
import id.ac.ub.filkom.sekcv.appstroke.model.util.TaskDelegatable;
import jp.wasabeef.recyclerview.animators.FadeInRightAnimator;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager> created by :
 * Name         : syafiq
 * Date / Time  : 31 August 2016, 6:50 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class MedicalRecord extends TitledFragment
{
    public static final String CLASSNAME = "MedicalRecord";
    public static final String CLASSPATH = "controller.mainpage.viewpager";
    public static final String TAG       = CLASSPATH + "." + CLASSNAME;
    public static final int    ID        = 0b010;

    private Unbinder            unbinder;
    private View                container;
    private RecyclerViewAdapter adapter;
    private Observer            medicalRecordObserver;
    private MainPage            root;

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static MedicalRecord newInstance(String title)
    {
        Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "newInstance");

        final MedicalRecord fragment = new MedicalRecord();
        fragment.setTitle(title);
        return fragment;
    }

    //----------------------------------------------------------------------------------------------
    //---App Life Cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "onCreate");

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "onCreateView");

        this.container = inflater.inflate(R.layout.mainpage_viewpager_medical_record, container, false);
        this.unbinder = ButterKnife.bind(this, this.container);
        this.root = ((MainPage) super.getActivity());

        new StartUpTask(new TaskDelegatable()
        {
            @Override
            public void delegate()
            {
                MedicalRecord.this.setMedicalRecordObserver();
                MedicalRecord.this.initializeMedicalRecordContainer();
                MedicalRecord.this.root.getMedicalRecordData().addObserver(MedicalRecord.this.medicalRecordObserver);
            }
        }).execute();

        return this.container;
    }

    @Override
    public void onAttach(Context context)
    {
        Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "onAttach");

        super.onAttach(context);
    }

    @Override
    public void onDestroyView()
    {
        Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "onDestroyView");

        this.root.getMedicalRecordData().deleteObserver(MedicalRecord.this.medicalRecordObserver);
        this.unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "onActivityCreated");

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "onStart");

        super.onStart();
    }

    @Override
    public void onResume()
    {
        Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "onResume");

        super.onResume();
    }

    @Override
    public void onPause()
    {
        Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "onPause");

        super.onPause();
    }

    @Override
    public void onStop()
    {
        Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "onStop");

        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "onSaveInstanceState");

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "onViewStateRestored");

        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDestroy()
    {
        Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "onDestroy");

        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "onDetach");

        super.onDetach();
    }

    //----------------------------------------------------------------------------------------------
    //---App Interface Dependency
    //----------------------------------------------------------------------------------------------

    private void setMedicalRecordObserver()
    {
        Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "setMedicalRecordObserver");

        this.medicalRecordObserver = new Observer()
        {
            @Override
            public void update(Observable observable, Object o)
            {
                if(observable instanceof ObservableLinkedList)
                {
                    MedicalRecord.this.adapter.notifyDataSetChanged();
                }
            }
        };
    }

    private void initializeMedicalRecordContainer()
    {
        Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "initializeMedicalRecordContainer");

        final RecyclerView recyclerView = ButterKnife.findById(this.container, R.id.mainpage_viewpager_medical_record_recycler_container);

        recyclerView.setLayoutManager(new LinearLayoutManager(super.getContext()));

        recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(super.getContext(), R.drawable.divider)));
        recyclerView.setItemAnimator(new FadeInRightAnimator());

        this.adapter = new RecyclerViewAdapter(this.root, this.root.getMedicalRecordData().getLists());
        this.adapter.setMode(Attributes.Mode.Multiple);
        recyclerView.setAdapter(this.adapter);
    }

    //----------------------------------------------------------------------------------------------
    //---App User Function
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //---Class Helper
    //----------------------------------------------------------------------------------------------

    private class StartUpTask extends AsyncTask<Void, Void, Void>
    {
        public static final String CLASSNAME = "StartUpTask";

        private final LinkedList<TaskDelegatable> delegations;

        public StartUpTask(TaskDelegatable... delegations)
        {
            Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "." + StartUpTask.CLASSNAME + ".Constructor");

            this.delegations = new LinkedList<>();
            Collections.addAll(this.delegations, delegations);
        }

        @Override
        protected void onPreExecute()
        {
            Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "." + StartUpTask.CLASSNAME + ".onPreExecute");

            super.onPreExecute();
        }

        @SuppressWarnings({"StatementWithEmptyBody", "UnnecessarySemicolon"})
        @Override
        protected Void doInBackground(Void... voids)
        {
            Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "." + StartUpTask.CLASSNAME + ".doInBackground");

            while(!MedicalRecord.this.root.isActivityReady())
            {
                ;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            Log.d(MedicalRecord.CLASSNAME, MedicalRecord.TAG + "." + StartUpTask.CLASSNAME + ".onPostExecute");

            for(final TaskDelegatable delegation : this.delegations)
            {
                delegation.delegate();
            }
        }
    }
}
