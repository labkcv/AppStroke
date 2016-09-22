package id.ac.ub.filkom.sekcv.appstroke.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import id.ac.ub.filkom.sekcv.appstroke.R;
import id.ac.ub.filkom.sekcv.appstroke.R2;
import id.ac.ub.filkom.sekcv.appstroke.controller.MainPage;
import id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager.Treatment;
import id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component.Status;
import id.ac.ub.filkom.sekcv.appstroke.model.dataset.Stroke;
import id.ac.ub.filkom.sekcv.appstroke.model.db.core.DatabaseHelper;
import id.ac.ub.filkom.sekcv.appstroke.model.db.entity.Entity_MedicalRecord;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.controller.adapter> created by :
 * Name         : syafiq
 * Date / Time  : 31 August 2016, 7:20 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class RecyclerViewAdapter extends RecyclerSwipeAdapter<RecyclerViewAdapter.SimpleViewHolder>
{
    private final String DATE_FORMAT;
    private final String TIME_FORMAT;
    private final String TIMESTAMP_FORMAT;

    private final MainPage                   root;
    private final List<Entity_MedicalRecord> dataset;
    private       DateTimeFormatter          ageFormatter;

    public RecyclerViewAdapter(MainPage root, List<Entity_MedicalRecord> objects)
    {
        this.dataset = objects;
        this.root = root;

        this.DATE_FORMAT = "dd MMMM yyyy";
        this.TIME_FORMAT = DatabaseHelper.TIME_FORMAT;
        this.TIMESTAMP_FORMAT = DatabaseHelper.TIMESTAMP_FORMAT;
        this.updateTimeFormatter();
    }

    public void updateTimeFormatter()
    {
        this.ageFormatter = DateTimeFormat.forPattern(this.TIMESTAMP_FORMAT + "ZZ").withZone(DateTimeZone.getDefault());
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainpage_viewpager_medical_record_item, parent, false);
        return new SimpleViewHolder(view, parent.getContext());
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position)
    {
        final Entity_MedicalRecord medicalRecord = this.dataset.get(position);
        final DateTime             dateTime      = DateTime.parse(medicalRecord.getTime() + "+00:00", this.ageFormatter);
        final Locale               locale        = Locale.getDefault();
        if(viewHolder.swipeLayout != null)
        {
            viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        }
        if(viewHolder.localDate != null)
        {
            viewHolder.localDate.setText(dateTime.toString(this.DATE_FORMAT));
        }
        if(viewHolder.localTime != null)
        {
            viewHolder.localTime.setText(dateTime.toString(this.TIME_FORMAT));
        }
        if(viewHolder.year != null)
        {
            viewHolder.year.setText(String.format(this.root.getResources().getString(R.string.mainpage_viewpager_medical_record_label_year), medicalRecord.getAge()));
        }
        this.getStatusDescription(viewHolder.result, viewHolder.simpleIcon, viewHolder.detailIcon, medicalRecord.getStatus());
        if(viewHolder.detailIcon != null)
        {
            viewHolder.detailIcon.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View view)
                {
                    RecyclerViewAdapter.this.root.updateStroke(Stroke.newInstanceFromMedicalRecord(RecyclerViewAdapter.this.dataset.get(position)));
                    RecyclerViewAdapter.this.root.getViewPager().setCurrentItem(Treatment.ID);
                    return false;
                }
            });
        }
        if(viewHolder.cholesterol != null)
        {
            viewHolder.cholesterol.setText(String.format(locale, "%.3f", medicalRecord.getCholesterol()));
        }
        if(viewHolder.hdl != null)
        {
            viewHolder.hdl.setText(String.format(locale, "%.3f", medicalRecord.getHdl()));
        }
        if(viewHolder.ldl != null)
        {
            viewHolder.ldl.setText(String.format(locale, "%.3f", medicalRecord.getLdl()));
        }
        if(viewHolder.triglyceride != null)
        {
            viewHolder.triglyceride.setText(String.format(locale, "%.3f", medicalRecord.getTriglyceride()));
        }

        mItemManger.bindView(viewHolder.itemView, position);
    }

    private void getStatusDescription(TextView result, ImageView simpleIcon, ImageView detailIcon, int status)
    {
        final Context context = this.root.getApplicationContext();
        if(status == Status.NORMAL.ordinal())
        {
            result.setText(context.getResources().getString(R.string.mainpage_viewpager_medical_record_status_normal));
            simpleIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mainpage_viewpager_medical_record_simple_result_normal));
            detailIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mainpage_viewpager_medical_record_detail_result_normal));
        }
        else if(status == Status.HIGH.ordinal())
        {
            result.setText(context.getResources().getString(R.string.mainpage_viewpager_medical_record_status_high));
            simpleIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mainpage_viewpager_medical_record_simple_result_high));
            detailIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mainpage_viewpager_medical_record_detail_result_high));
        }
        else if(status == Status.DANGER.ordinal())
        {
            result.setText(context.getResources().getString(R.string.mainpage_viewpager_medical_record_status_danger));
            simpleIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mainpage_viewpager_medical_record_simple_result_danger));
            detailIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mainpage_viewpager_medical_record_detail_result_danger));
        }
    }

    @Override
    public int getItemCount()
    {
        return dataset.size();
    }


    @Override
    public int getSwipeLayoutResourceId(int position)
    {
        return R.id.mainpage_viewpager_medical_record_item_swipe;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder
    {
        final Context context;
        @Nullable
        @BindView(R2.id.mainpage_viewpager_medical_record_item_swipe)
        SwipeLayout swipeLayout;
        @Nullable
        @BindView(R2.id.mainpage_viewpager_medical_record_simple_text_view_localdate)
        TextView    localDate;
        @Nullable
        @BindView(R2.id.mainpage_viewpager_medical_record_simple_text_view_localtime)
        TextView    localTime;
        @Nullable
        @BindView(R2.id.mainpage_viewpager_medical_record_simple_text_view_year)
        TextView    year;
        @Nullable
        @BindView(R2.id.mainpage_viewpager_medical_record_simple_image_view_level_icon)
        ImageView   simpleIcon;
        @Nullable
        @BindView(R2.id.mainpage_viewpager_medical_record_simple_text_view_result)
        TextView    result;
        @Nullable
        @BindView(R2.id.mainpage_viewpager_medical_record_detail_image_view_level_icon)
        ImageView   detailIcon;
        @Nullable
        @BindView(R2.id.mainpage_viewpager_medical_record_detail_text_view_cholesterol)
        TextView    cholesterol;
        @Nullable
        @BindView(R2.id.mainpage_viewpager_medical_record_detail_text_view_hdl)
        TextView    hdl;
        @Nullable
        @BindView(R2.id.mainpage_viewpager_medical_record_detail_text_view_ldl)
        TextView    ldl;
        @Nullable
        @BindView(R2.id.mainpage_viewpager_medical_record_detail_text_view_triglyceride)
        TextView    triglyceride;

        public SimpleViewHolder(View itemView, final Context context)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = context;
        }

        @Optional
        @OnClick(R2.id.mainpage_viewpager_medical_record_item_go_to_detail)
        public void onGoToDetailInitiated()
        {
            if(this.swipeLayout != null)
            {
                this.swipeLayout.open();
            }
        }

        @Optional
        @OnClick(R2.id.mainpage_viewpager_medical_record_item_go_to_simple)
        public void onGoToSimpleInitiated()
        {
            if(this.swipeLayout != null)
            {
                this.swipeLayout.close();
            }
        }
    }
}
