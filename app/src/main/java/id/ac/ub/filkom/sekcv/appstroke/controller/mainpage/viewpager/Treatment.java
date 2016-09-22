package id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.ac.ub.filkom.sekcv.appstroke.R;
import id.ac.ub.filkom.sekcv.appstroke.R2;
import id.ac.ub.filkom.sekcv.appstroke.controller.MainPage;
import id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component.Status;
import id.ac.ub.filkom.sekcv.appstroke.model.custom.android.support.v4.app.TitledFragment;
import id.ac.ub.filkom.sekcv.appstroke.model.dataset.ObservableStroke;
import id.ac.ub.filkom.sekcv.appstroke.model.dataset.Stroke;
import id.ac.ub.filkom.sekcv.appstroke.model.dataset.StrokeParameter;
import id.ac.ub.filkom.sekcv.appstroke.model.util.TaskDelegatable;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager> created by :
 * Name         : syafiq
 * Date / Time  : 02 September 2016, 11:38 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Treatment extends TitledFragment
{
    public static final String CLASSNAME = "Treatment";
    public static final String CLASSPATH = "controller.mainpage.viewpager";
    public static final String TAG       = CLASSPATH + "." + CLASSNAME;
    public static final int    ID        = 0b100;

    @BindView(R2.id.mainpage_viewpager_treatment_text_view_cholesterol)  TextView   cholesterol;
    @BindView(R2.id.mainpage_viewpager_treatment_text_view_hdl)          TextView   hdl;
    @BindView(R2.id.mainpage_viewpager_treatment_text_view_ldl)          TextView   ldl;
    @BindView(R2.id.mainpage_viewpager_treatment_text_view_triglyceride) TextView   triglyceride;
    @BindView(R2.id.mainpage_viewpager_treatment_text_view_level_status) TextView   status;
    @BindView(R2.id.mainpage_viewpager_treatment_image_view_level_icon)  ImageView  icon;
    @BindView(R2.id.mainpage_viewpager_treatment_container)              ScrollView treatmentContainer;

    private Unbinder unbinder;
    private Observer strokeObserver;
    private MainPage root;

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static Treatment newInstance(String title)
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".newInstance");

        final Treatment fragment = new Treatment();
        fragment.setTitle(title);
        return fragment;
    }

    //----------------------------------------------------------------------------------------------
    //---App Life Cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".onCreate");

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".onCreateView");

        final View view = inflater.inflate(R.layout.mainpage_viewpager_treatment, container, false);
        this.unbinder = ButterKnife.bind(this, view);
        this.root = ((MainPage) super.getActivity());

        new StartUpTask(new TaskDelegatable()
        {
            @Override
            public void delegate()
            {
                Treatment.this.setStrokeObserver();
                Treatment.this.updateTreatmentData();
                Treatment.this.root.getStrokeData().addObserver(strokeObserver);
            }
        }).execute();

        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".onAttach");

        super.onAttach(context);
    }

    @Override
    public void onDestroyView()
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".onDestroyView");

        this.root.getStrokeData().deleteObserver(this.strokeObserver);
        this.unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".onActivityCreated");

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".onStart");

        super.onStart();
    }

    @Override
    public void onResume()
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".onResume");

        super.onResume();
    }

    @Override
    public void onPause()
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".onPause");

        super.onPause();
    }

    @Override
    public void onStop()
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".onStop");

        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".onSaveInstanceState");

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".onViewStateRestored");

        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDestroy()
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".onDestroy");

        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".onDetach");

        super.onDetach();
    }

    //----------------------------------------------------------------------------------------------
    //---App Interface Dependency
    //----------------------------------------------------------------------------------------------

    private void setStrokeObserver()
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".setStrokeObserver");

        this.strokeObserver = new Observer()
        {
            @Override
            public void update(Observable observable, Object o)
            {
                if(observable instanceof ObservableStroke)
                {
                    Treatment.this.updateTreatmentData();
                }
            }
        };
    }

    private void updateTreatmentData()
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".updateTreatmentData");

        final Stroke stroke = this.root.getStrokeData().getStroke();
        if(stroke != null)
        {
            final Locale          locale        = Locale.getDefault();
            final StrokeParameter medicalRecord = stroke.getParameter();
            this.cholesterol.setText(String.format(locale, "%.3f", medicalRecord.getCholesterol()));
            this.hdl.setText(String.format(locale, "%.3f", medicalRecord.getHdl()));
            this.ldl.setText(String.format(locale, "%.3f", medicalRecord.getLdl()));
            this.triglyceride.setText(String.format(locale, "%.3f", medicalRecord.getTriglyceride()));
            this.getStatusDescription(this.status, this.icon, stroke.getMetadata().getStatus());
            this.displayTreatment(stroke.getMetadata().getStatus());
        }
        else
        {
            Toast.makeText(super.getContext(), super.getResources().getString(R.string.mainpage_viewpager_medical_treatment_data_not_defined), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("InflateParams")
    private void displayTreatment(int status)
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".displayTreatment");

        this.treatmentContainer.removeAllViews();
        final LayoutInflater inflater = (LayoutInflater) this.root.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(status == Status.NORMAL.ordinal())
        {
            inflater.inflate(R.layout.mainpage_viewpager_treatment_status_0, this.treatmentContainer);
        }
        else if(status == Status.HIGH.ordinal())
        {
            inflater.inflate(R.layout.mainpage_viewpager_treatment_status_1, this.treatmentContainer);
        }
        else if(status == Status.DANGER.ordinal())
        {
            inflater.inflate(R.layout.mainpage_viewpager_treatment_status_2, this.treatmentContainer);
        }
    }

    private void getStatusDescription(TextView statusHolder, ImageView iconHolder, int status)
    {
        Log.d(Treatment.CLASSNAME, Treatment.TAG + ".getStatusDescription");

        final Context context = super.getContext();
        if(status == Status.NORMAL.ordinal())
        {
            statusHolder.setText(context.getResources().getString(R.string.mainpage_viewpager_medical_record_status_normal));
            iconHolder.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mainpage_viewpager_medical_record_detail_result_normal));
        }
        else if(status == Status.HIGH.ordinal())
        {
            statusHolder.setText(context.getResources().getString(R.string.mainpage_viewpager_medical_record_status_high));
            iconHolder.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mainpage_viewpager_medical_record_detail_result_high));
        }
        else if(status == Status.DANGER.ordinal())
        {
            statusHolder.setText(context.getResources().getString(R.string.mainpage_viewpager_medical_record_status_danger));
            iconHolder.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mainpage_viewpager_medical_record_detail_result_danger));
        }
    }

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
            Log.d(Treatment.CLASSNAME, Treatment.TAG + "." + StartUpTask.CLASSNAME + ".Constructor");

            this.delegations = new LinkedList<>();
            Collections.addAll(this.delegations, delegations);
        }

        @Override
        protected void onPreExecute()
        {
            Log.d(Treatment.CLASSNAME, Treatment.TAG + "." + StartUpTask.CLASSNAME + ".onPreExecute");

            super.onPreExecute();
        }

        @Nullable
        @SuppressWarnings({"StatementWithEmptyBody", "UnnecessarySemicolon"})
        @Override
        protected Void doInBackground(Void... voids)
        {
            Log.d(Treatment.CLASSNAME, Treatment.TAG + "." + StartUpTask.CLASSNAME + ".doInBackground");

            while(!Treatment.this.root.isActivityReady())
            {
                ;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            Log.d(Treatment.CLASSNAME, Treatment.TAG + "." + StartUpTask.CLASSNAME + ".onPostExecute");

            for(final TaskDelegatable delegation : this.delegations)
            {
                delegation.delegate();
            }
        }
    }
}
