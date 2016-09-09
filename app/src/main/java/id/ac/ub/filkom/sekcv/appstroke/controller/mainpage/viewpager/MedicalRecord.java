package id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.daimajia.swipe.util.Attributes;
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;

import java.util.List;

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

    private Unbinder                                                            unbinder;
    private User                                                                user;
    private id.ac.ub.filkom.sekcv.appstroke.model.db.model.MedicalRecord        medicalRecordModel;
    private View                                                                container;
    private RecyclerViewAdapter                                                 adapter;
    private List<id.ac.ub.filkom.sekcv.appstroke.model.db.entity.MedicalRecord> records;

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
        this.initializeMedicalRecordData();
        this.initializeMedicalRecordDataContainer();
        this.setRefreshBehaviour();
        return this.container;
    }

    private void initializeMedicalRecordData()
    {
        this.records = this.medicalRecordModel.getDataByUser(this.user.getId());
    }

    private void setRefreshBehaviour()
    {
        final com.baoyz.widget.PullRefreshLayout layout = ButterKnife.findById(MedicalRecord.this.container, R.id.mainpage_viewpager_medical_record_refresh_layout);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                MedicalRecord.this.records.clear();
                MedicalRecord.this.records.addAll(MedicalRecord.this.medicalRecordModel.getDataByUser(MedicalRecord.this.user.getId()));
                MedicalRecord.this.adapter.notifyDataSetChanged();
                layout.setRefreshing(false);
            }
        });
    }

    private void initializeMedicalRecordDataContainer()
    {
        final RecyclerView recyclerView = ButterKnife.findById(this.container, R.id.mainpage_viewpager_medical_record_recycler_container);

        // Layout Managers:
        recyclerView.setLayoutManager(new LinearLayoutManager(super.getContext()));

        // Item Decorator:
        recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(super.getContext(), R.drawable.divider)));
        recyclerView.setItemAnimator(new FadeInRightAnimator());

        // Adapter:
        this.adapter = new RecyclerViewAdapter(super.getContext(), this.records);
        this.adapter.setMode(Attributes.Mode.Multiple);
        recyclerView.setAdapter(adapter);
    }

    private void initializeMedicalRecordModel()
    {
        this.medicalRecordModel = ((MainPage) super.getActivity()).getMedicalRecordModel();
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
