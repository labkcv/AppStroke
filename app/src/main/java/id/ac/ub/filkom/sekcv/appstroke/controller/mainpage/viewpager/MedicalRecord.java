package id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import java.sql.SQLException;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.ac.ub.filkom.sekcv.appstroke.R;
import id.ac.ub.filkom.sekcv.appstroke.controller.MainPage;
import id.ac.ub.filkom.sekcv.appstroke.controller.adapter.RecyclerViewAdapter;
import id.ac.ub.filkom.sekcv.appstroke.model.custom.android.support.v4.app.TitledFragment;
import id.ac.ub.filkom.sekcv.appstroke.model.db.entity.User;
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
    public static final String TAG = "controller.mainpage.viewpager.MedicalRecord";
    public static final int    ID  = 0b010;

    private Unbinder                                                     unbinder;
    private User                                                         user;
    private id.ac.ub.filkom.sekcv.appstroke.model.db.model.MedicalRecord medicalRecordModel;
    private View                                                         container;

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static MedicalRecord newInstance(String title)
    {
        final MedicalRecord fragment = new MedicalRecord();
        fragment.setTitle(title);
        Log.i("MedicalRecord", "controller.mainpage.viewpager.MedicalRecord.newInstace");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i("MedicalRecord", "controller.mainpage.viewpager.MedicalRecord.onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i("MedicalRecord", "controller.mainpage.viewpager.MedicalRecord.onCreateView");
        this.container = inflater.inflate(R.layout.mainpage_viewpager_medical_record, container, false);
        this.unbinder = ButterKnife.bind(this, this.container);
        this.getUserAccount();
        this.initializeMedicalRecordModel();
        return this.container;
    }

    private void initializeMedicalRecordData()
    {
        final RecyclerView recyclerView = ButterKnife.findById(this.container, R.id.mainpage_viewpager_medical_record_recycler_container);

        // Layout Managers:
        recyclerView.setLayoutManager(new LinearLayoutManager(super.getContext()));

        // Item Decorator:
        //recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        recyclerView.setItemAnimator(new FadeInRightAnimator());

        // Adapter:
        final RecyclerView.Adapter mAdapter = new RecyclerViewAdapter(super.getContext(), this.medicalRecordModel.getDataByUser(this.user.getId()));
        ((RecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Multiple);
        recyclerView.setAdapter(mAdapter);
    }

    private void initializeMedicalRecordModel()
    {
        this.medicalRecordModel = new id.ac.ub.filkom.sekcv.appstroke.model.db.model.MedicalRecord(getContext());
        this.intializeDatabase();
    }

    private void intializeDatabase()
    {
        new AsyncTask<Void, Void, Void>()
        {
            final FragmentActivity activity = MedicalRecord.super.getActivity();
            private ProgressDialog progressDialog;

            @Override
            protected Void doInBackground(Void... voids)
            {
                try
                {
                    MedicalRecord.this.medicalRecordModel.open();
                }
                catch(SQLException ignored)
                {
                    Toast.makeText(this.activity, this.activity.getResources().getString(R.string.mainpage_viewpager_label_error_db), Toast.LENGTH_SHORT).show();
                    this.activity.finish();
                }
                return null;
            }

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                MedicalRecord.this.getActivity().runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        progressDialog = new ProgressDialog(MedicalRecord.super.getContext(), R.style.AppTheme_Dark_Dialog);
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
                MedicalRecord.this.initializeMedicalRecordData();
            }
        }.execute();
    }

    private void getUserAccount()
    {
        this.user = ((MainPage) super.getActivity()).getUser();
    }
    //----------------------------------------------------------------------------------------------


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.i("MedicalRecord", "controller.mainpage.viewpager.MedicalRecord.onAttach");
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        this.unbinder.unbind();
        Log.i("MedicalRecord", "controller.mainpage.viewpager.MedicalRecord.onDestroyView");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.i("MedicalRecord", "controller.mainpage.viewpager.MedicalRecord.onActivityCreated");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.i("MedicalRecord", "controller.mainpage.viewpager.MedicalRecord.onStart");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i("MedicalRecord", "controller.mainpage.viewpager.MedicalRecord.onResume");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.i("MedicalRecord", "controller.mainpage.viewpager.MedicalRecord.onPause");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.i("MedicalRecord", "controller.mainpage.viewpager.MedicalRecord.onStop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.i("MedicalRecord", "controller.mainpage.viewpager.MedicalRecord.onSaveInstanceState");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        Log.i("MedicalRecord", "controller.mainpage.viewpager.MedicalRecord.onViewStateRestored");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i("MedicalRecord", "controller.mainpage.viewpager.MedicalRecord.onDestroy");
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        Log.i("MedicalRecord", "controller.mainpage.viewpager.MedicalRecord.onDetach");
    }
}
