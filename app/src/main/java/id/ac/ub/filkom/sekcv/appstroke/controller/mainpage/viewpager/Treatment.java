package id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager;

import android.content.Context;
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

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.ac.ub.filkom.sekcv.appstroke.R;
import id.ac.ub.filkom.sekcv.appstroke.controller.MainPage;
import id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component.Status;
import id.ac.ub.filkom.sekcv.appstroke.model.custom.android.support.v4.app.TitledFragment;
import id.ac.ub.filkom.sekcv.appstroke.model.dataset.Stroke;
import id.ac.ub.filkom.sekcv.appstroke.model.dataset.StrokeParameter;
import id.ac.ub.filkom.sekcv.appstroke.model.db.entity.User;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager> created by :
 * Name         : syafiq
 * Date / Time  : 02 September 2016, 11:38 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Treatment extends TitledFragment
{
    public static final String TAG = "controller.mainpage.viewpager.Treatment";
    public static final int    ID  = 0b100;
    @BindView(R.id.mainpage_viewpager_treatment_text_view_cholesterol)  TextView   cholesterol;
    @BindView(R.id.mainpage_viewpager_treatment_text_view_hdl)          TextView   hdl;
    @BindView(R.id.mainpage_viewpager_treatment_text_view_ldl)          TextView   ldl;
    @BindView(R.id.mainpage_viewpager_treatment_text_view_triglyceride) TextView   triglyceride;
    @BindView(R.id.mainpage_viewpager_treatment_text_view_level_status) TextView   status;
    @BindView(R.id.mainpage_viewpager_treatment_image_view_level_icon)  ImageView  icon;
    @BindView(R.id.mainpage_viewpager_treatment_container)              ScrollView treatmentContainer;
    //@BindView(R.id.mainpage_viewpager_treatment_spinner_list)           MaterialBetterSpinner spinner;
    private                                                             User       user;
    private                                                             Stroke     stroke;
    private                                                             Unbinder   unbinder;
    private                                                             View       container;

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static Treatment newInstance(String title)
    {
        int             i        = 0;
        final Treatment fragment = new Treatment();
        fragment.setTitle(title);
        Log.i("Treatment", "controller.mainpage.viewpager.Treatment.newInstace");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i("Treatment", "controller.mainpage.viewpager.Treatment.onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i("Treatment", "controller.mainpage.viewpager.Treatment.onCreateView");
        this.container = inflater.inflate(R.layout.mainpage_viewpager_treatment, container, false);
        this.unbinder = ButterKnife.bind(this, this.container);
        this.getUserAccountAndData();
        this.updateStrokeDataDisplay(this.stroke);
        //this.createSpinner();
        return this.container;
    }

    private void createSpinner()
    {
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(super.getContext(), android.R.layout.simple_dropdown_item_1line, super.getContext().getResources().getStringArray(R.array.timestamp_list));
        //this.spinner.setAdapter(adapter);
    }

    private void updateStrokeDataDisplay(final Stroke stroke)
    {
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

    private void displayTreatment(int status)
    {
        final LayoutInflater inflater = (LayoutInflater) super.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View                 view     = null;
        if(status == Status.NORMAL.ordinal())
        {
            view = inflater.inflate(R.layout.mainpage_viewpager_treatment_status_0, null);
        }
        else if(status == Status.HIGH.ordinal())
        {
            view = inflater.inflate(R.layout.mainpage_viewpager_treatment_status_1, null);
        }
        else if(status == Status.DANGER.ordinal())
        {
            view = inflater.inflate(R.layout.mainpage_viewpager_treatment_status_2, null);
        }
        if(view != null)
        {
            this.treatmentContainer.addView(view);
        }
    }

    private void getStatusDescription(TextView statusHolder, ImageView iconHolder, int status)
    {
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

    private void getUserAccountAndData()
    {
        this.user = ((MainPage) super.getActivity()).getUser();
        this.stroke = ((MainPage) super.getActivity()).getStrokeData();
    }

    //----------------------------------------------------------------------------------------------


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.i("Treatment", "controller.mainpage.viewpager.Treatment.onAttach");
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        this.unbinder.unbind();
        Log.i("Treatment", "controller.mainpage.viewpager.Treatment.onDestroyView");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.i("Treatment", "controller.mainpage.viewpager.Treatment.onActivityCreated");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.i("Treatment", "controller.mainpage.viewpager.Treatment.onStart");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i("Treatment", "controller.mainpage.viewpager.Treatment.onResume");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.i("Treatment", "controller.mainpage.viewpager.Treatment.onPause");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.i("Treatment", "controller.mainpage.viewpager.Treatment.onStop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.i("Treatment", "controller.mainpage.viewpager.Treatment.onSaveInstanceState");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        Log.i("Treatment", "controller.mainpage.viewpager.Treatment.onViewStateRestored");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i("Treatment", "controller.mainpage.viewpager.Treatment.onDestroy");
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        Log.i("Treatment", "controller.mainpage.viewpager.Treatment.onDetach");
    }
}
