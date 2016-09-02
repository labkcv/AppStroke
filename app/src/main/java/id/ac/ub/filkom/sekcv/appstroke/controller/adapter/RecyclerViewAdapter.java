package id.ac.ub.filkom.sekcv.appstroke.controller.adapter;

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

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import id.ac.ub.filkom.sekcv.appstroke.R;
import id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component.Status;
import id.ac.ub.filkom.sekcv.appstroke.model.db.core.DatabaseHelper;
import id.ac.ub.filkom.sekcv.appstroke.model.db.entity.MedicalRecord;

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

    private final Context             context;
    private final List<MedicalRecord> dataset;

    public RecyclerViewAdapter(Context context, List<MedicalRecord> objects)
    {
        this.dataset = objects;
        this.context = context;

        this.DATE_FORMAT = "dd-MM-yyyy";
        this.TIME_FORMAT = DatabaseHelper.TIME_FORMAT;
        this.TIMESTAMP_FORMAT = DatabaseHelper.TIMESTAMP_FORMAT;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainpage_viewpager_medical_record_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position)
    {
        final MedicalRecord medicalRecord = this.dataset.get(position);
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        final LocalDateTime dateTime = LocalDateTime.parse(medicalRecord.getTime(), DateTimeFormat.forPattern(this.TIMESTAMP_FORMAT));
        viewHolder.localDate.setText(dateTime.toLocalDate().toString(this.DATE_FORMAT));
        viewHolder.localTime.setText(dateTime.toLocalTime().toString(this.TIME_FORMAT));
        viewHolder.year.setText(String.format(this.context.getResources().getString(R.string.mainpage_viewpager_medical_record_label_year), medicalRecord.getAge()));
        this.getStatusDescription(viewHolder.result, viewHolder.simpleIcon, viewHolder.detailIcon, medicalRecord.getStatus());
        viewHolder.cholesterol.setText(String.format("%.4g", medicalRecord.getCholesterol()));
        viewHolder.hdl.setText(String.format("%.4g", medicalRecord.getHdl()));
        viewHolder.ldl.setText(String.format("%.4g", medicalRecord.getLdl()));
        viewHolder.triglyceride.setText(String.format("%.4g", medicalRecord.getTriglyceride()));

        mItemManger.bindView(viewHolder.itemView, position);
    }

    private void getStatusDescription(TextView result, ImageView simpleIcon, ImageView detailIcon, int status)
    {
        if(status == Status.NORMAL.ordinal())
        {
            result.setText(this.context.getResources().getString(R.string.mainpage_viewpager_medical_record_status_normal));
            simpleIcon.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.mainpage_viewpager_medical_record_simple_result_normal));
            detailIcon.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.mainpage_viewpager_medical_record_detail_result_normal));
        }
        else if(status == Status.HIGH.ordinal())
        {
            result.setText(this.context.getResources().getString(R.string.mainpage_viewpager_medical_record_status_high));
            simpleIcon.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.mainpage_viewpager_medical_record_simple_result_high));
            detailIcon.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.mainpage_viewpager_medical_record_detail_result_high));
        }
        else if(status == Status.DANGER.ordinal())
        {
            result.setText(this.context.getResources().getString(R.string.mainpage_viewpager_medical_record_status_danger));
            simpleIcon.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.mainpage_viewpager_medical_record_simple_result_danger));
            detailIcon.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.mainpage_viewpager_medical_record_detail_result_danger));
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
        @Nullable
        @BindView(R.id.mainpage_viewpager_medical_record_item_swipe)
        SwipeLayout swipeLayout;

        @Nullable
        @BindView(R.id.mainpage_viewpager_medical_record_simple_text_view_localdate)
        TextView localDate;

        @Nullable
        @BindView(R.id.mainpage_viewpager_medical_record_simple_text_view_localtime)
        TextView localTime;

        @Nullable
        @BindView(R.id.mainpage_viewpager_medical_record_simple_text_view_year)
        TextView year;

        @Nullable
        @BindView(R.id.mainpage_viewpager_medical_record_simple_image_view_level_icon)
        ImageView simpleIcon;

        @Nullable
        @BindView(R.id.mainpage_viewpager_medical_record_simple_text_view_result)
        TextView result;

        @Nullable
        @BindView(R.id.mainpage_viewpager_medical_record_detail_image_view_level_icon)
        ImageView detailIcon;

        @Nullable
        @BindView(R.id.mainpage_viewpager_medical_record_detail_text_view_cholesterol)
        TextView cholesterol;

        @Nullable
        @BindView(R.id.mainpage_viewpager_medical_record_detail_text_view_hdl)
        TextView hdl;

        @Nullable
        @BindView(R.id.mainpage_viewpager_medical_record_detail_text_view_ldl)
        TextView ldl;

        @Nullable
        @BindView(R.id.mainpage_viewpager_medical_record_detail_text_view_triglyceride)
        TextView triglyceride;


        public SimpleViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Optional
        @OnClick(R.id.mainpage_viewpager_medical_record_item_go_to_detail)
        public void onGoToDetailInitiated()
        {
            if(this.swipeLayout != null)
            {
                this.swipeLayout.open();
            }
        }

        @Optional
        @OnClick(R.id.mainpage_viewpager_medical_record_item_go_to_simple)
        public void onGoToSimpleInitiated()
        {
            if(this.swipeLayout != null)
            {
                this.swipeLayout.close();
            }
        }
    }
}
