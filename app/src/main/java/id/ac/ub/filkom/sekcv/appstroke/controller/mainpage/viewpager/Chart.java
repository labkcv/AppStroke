package id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import id.ac.ub.filkom.sekcv.appstroke.R;
import id.ac.ub.filkom.sekcv.appstroke.controller.MainPage;
import id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component.Parameter;
import id.ac.ub.filkom.sekcv.appstroke.model.custom.android.support.v4.app.TitledFragment;
import id.ac.ub.filkom.sekcv.appstroke.model.db.entity.Entity_MedicalRecord;
import id.ac.ub.filkom.sekcv.appstroke.model.util.TaskDelegatable;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager> created by :
 * Name         : syafiq
 * Date / Time  : 04 October 2016, 1:16 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class Chart extends TitledFragment
{
    public static final String CLASSNAME = "Chart";
    public static final String CLASSPATH = "controller.mainpage.viewpager";
    public static final String TAG       = CLASSPATH + "." + CLASSNAME;
    public static final int    ID        = 0b011;

    private View                        container;
    private Observer                    medicalRecordObserver;
    private MainPage                    root;
    private LineChart                   chart;
    private ArrayList<ArrayList<Entry>> chartData;

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static Chart newInstance(String title)
    {
        Log.d(Chart.CLASSNAME, Chart.TAG + ".newInstance");

        final Chart fragment = new Chart();
        fragment.setTitle(title);
        return fragment;
    }

    //----------------------------------------------------------------------------------------------
    //---App Life Cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(Chart.CLASSNAME, Chart.TAG + ".onCreateView");

        this.container = inflater.inflate(R.layout.mainpage_viewpager_chart, container, false);
        this.root = ((MainPage) super.getActivity());

        new StartUpTask(new TaskDelegatable()
        {
            @Override
            public void delegate()
            {
                Chart.this.setChartObserver();
                Chart.this.initializeChartContainer();
                Chart.this.root.getMedicalRecordData().addObserver(Chart.this.medicalRecordObserver);
            }
        }).execute();

        return this.container;
    }

    @Override
    public void onDestroyView()
    {
        Log.d(Chart.CLASSNAME, Chart.TAG + ".onDestroyView");

        this.root.getMedicalRecordData().deleteObserver(this.medicalRecordObserver);
        super.onDestroyView();
    }

    //----------------------------------------------------------------------------------------------
    //---App Interface Dependency
    //----------------------------------------------------------------------------------------------

    private void setChartObserver()
    {
        Log.d(Chart.CLASSNAME, Chart.TAG + ".setChartObserver");

        this.medicalRecordObserver = new Observer()
        {
            @Override
            public void update(Observable observable, Object o)
            {
                new AsyncTask<Void, Void, Void>()
                {
                    @Override
                    protected Void doInBackground(Void... params)
                    {
                        Chart.this.setChartData();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid)
                    {
                        Chart.this.chart.invalidate();
                    }
                }.execute();
            }
        };
    }

    private void initializeChartContainer()
    {
        Log.d(Chart.CLASSNAME, Chart.TAG + ".initializeChartContainer");

        this.chart = (LineChart) this.container.findViewById(R.id.mainpage_viewpager_chart_linechart);

        this.chart.getDescription().setEnabled(false);

        // enable touch gestures
        this.chart.setTouchEnabled(true);

        this.chart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        this.chart.setDragEnabled(true);
        this.chart.setScaleEnabled(true);
        this.chart.setDrawGridBackground(false);
        this.chart.setHighlightPerDragEnabled(true);

        // set an alternative background color
        this.chart.setBackgroundColor(Color.WHITE);
        this.chart.setViewPortOffsets(0f, 0f, 0f, 0f);

        // initialize chart data
        this.chartData = new ArrayList<>(Parameter.values().length - 1);
        for(int i = -1, is = Parameter.values().length - 1; ++i < is; )
        {
            this.chartData.add(new ArrayList<Entry>());
        }

        // add data
        this.setChartData();
        this.chart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = this.chart.getLegend();
        l.setEnabled(true);

        XAxis xAxis = this.chart.getXAxis();
        xAxis.setEnabled(false);
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTextSize(10f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(ContextCompat.getColor(this.getContext(), R.color.appstroke_colorAccent));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, AxisBase axis)
            {
                //return new DateTime((long) value).toString("mm:ss");
                return "";
            }

            @Override
            public int getDecimalDigits()
            {
                return 0;
            }
        });

        YAxis leftAxis = this.chart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(1000f);
        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(ContextCompat.getColor(this.getContext(), R.color.appstroke_colorAccent));

        YAxis rightAxis = this.chart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void setChartData()
    {
        for(final ArrayList<Entry> chartData : this.chartData)
        {
            chartData.clear();
        }

        final LinkedList<Entity_MedicalRecord> entity = this.root.getMedicalRecordData().getLists();

        for(Iterator<Entity_MedicalRecord> i = entity.descendingIterator(); i.hasNext(); )
        {
            final Entity_MedicalRecord data = i.next();
            float                      x    = (float) data.getTime().getMillis();
            this.chartData.get(Parameter.CHOLESTEROL.ordinal() - 1).add(new Entry(x, (float) data.getCholesterol()));
            this.chartData.get(Parameter.HDL.ordinal() - 1).add(new Entry(x, (float) data.getHdl()));
            this.chartData.get(Parameter.LDL.ordinal() - 1).add(new Entry(x, (float) data.getLdl()));
            this.chartData.get(Parameter.TRIGLYCERIDE.ordinal() - 1).add(new Entry(x, (float) data.getTriglyceride()));
        }

        // create a data object with the datasets
        final LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);
        final Parameter[] parameters = Parameter.values();
        final Resources   resources  = super.getResources();
        final String[] chartNames = new String[]
                {
                        resources.getString(R.string.mainpage_viewpager_medical_record_label_cholesterol),
                        resources.getString(R.string.mainpage_viewpager_medical_record_label_hdl),
                        resources.getString(R.string.mainpage_viewpager_medical_record_label_ldl),
                        resources.getString(R.string.mainpage_viewpager_medical_record_label_triglyceride),
                };
        for(int i = 0, is = parameters.length; ++i < is; )
        {
            final id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter param = parameters[i].getParameter();

            LineDataSet set = new LineDataSet(this.chartData.get(i - 1), chartNames[i - 1]);
            set.setAxisDependency(YAxis.AxisDependency.LEFT);
            set.setColor(param.getColor());
            set.setValueTextColor(param.getColor());
            set.setLineWidth(1.5f);
            set.setDrawCircles(true);
            set.setCircleRadius(3f);
            set.setCircleColor(ContextCompat.getColor(this.root, R.color.appstroke_colorPrimaryDark));
            set.setDrawCircleHole(true);
            set.setCircleHoleRadius(1.5f);
            set.setCircleColorHole(ContextCompat.getColor(this.root, R.color.appstroke_colorPrimary));
            set.setDrawValues(false);
            set.setFillColor(ContextCompat.getColor(this.root, R.color.appstroke_colorPrimary));
            set.setFillAlpha(65);
            set.setHighLightColor(Color.rgb(244, 117, 117));
            data.addDataSet(set);
        }

        this.chart.setData(data);
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
            Log.d(Chart.CLASSNAME, Chart.TAG + "." + StartUpTask.CLASSNAME + ".Constructor");

            this.delegations = new LinkedList<>();
            Collections.addAll(this.delegations, delegations);
        }

        @Override
        protected void onPreExecute()
        {
            Log.d(Chart.CLASSNAME, Chart.TAG + "." + StartUpTask.CLASSNAME + ".onPreExecute");

            super.onPreExecute();
        }

        @SuppressWarnings({"StatementWithEmptyBody", "UnnecessarySemicolon"})
        @Override
        protected Void doInBackground(Void... voids)
        {
            Log.d(Chart.CLASSNAME, Chart.TAG + "." + StartUpTask.CLASSNAME + ".doInBackground");

            while(!Chart.this.root.isActivityReady())
            {
                ;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            Log.d(Chart.CLASSNAME, Chart.TAG + "." + StartUpTask.CLASSNAME + ".onPostExecute");

            for(final TaskDelegatable delegation : this.delegations)
            {
                delegation.delegate();
            }
        }
    }
}
