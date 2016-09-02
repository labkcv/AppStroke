package id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Years;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.ac.ub.filkom.sekcv.appstroke.R;
import id.ac.ub.filkom.sekcv.appstroke.controller.MainPage;
import id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.SVM;
import id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component.Parameter;
import id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component.Status;
import id.ac.ub.filkom.sekcv.appstroke.model.custom.android.support.v4.app.TitledFragment;
import id.ac.ub.filkom.sekcv.appstroke.model.dataset.StrokeMetadata;
import id.ac.ub.filkom.sekcv.appstroke.model.dataset.StrokeParameter;
import id.ac.ub.filkom.sekcv.appstroke.model.db.entity.User;
import id.ac.ub.filkom.sekcv.appstroke.model.db.model.MedicalRecord;
import id.ac.ub.filkom.sekcv.appstroke.model.validator.mainpage.diagnose.NotEmptyValidator;
import id.ac.ub.filkom.sekcv.appstroke.model.validator.mainpage.diagnose.NumericValidator;
import id.ac.ub.filkom.sekcv.appstroke.model.validator.mainpage.diagnose.RangeValidator;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager> created by :
 * Name         : syafiq
 * Date / Time  : 11 August 2016, 6:09 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Diagnose extends TitledFragment
{
    public static final String TAG = "controller.mainpage.viewpager.Diagnose";
    public static final int    ID  = 0b001;

    private static final String USER_ID_TAG = "Diagnose.userID";
    public                                                            TextView         resultText;
    @BindView(R.id.mainpage_viewpager_diagnose_edittext_age)          MaterialEditText ageForm;
    @BindView(R.id.mainpage_viewpager_diagnose_edittext_cholesterol)  MaterialEditText cholesterolForm;
    @BindView(R.id.mainpage_viewpager_diagnose_edittext_hdl)          MaterialEditText hdlForm;
    @BindView(R.id.mainpage_viewpager_diagnose_edittext_ldl)          MaterialEditText ldlForm;
    @BindView(R.id.mainpage_viewpager_diagnose_edittext_triglyceride) MaterialEditText triglyceridForm;
    private                                                           Unbinder         unbinder;
    private                                                           DialogPlus       resultDialog;
    private                                                           User             user;
    private                                                           MedicalRecord    medicalRecordModel;

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static Diagnose newInstance(String title, int userID)
    {
        final Diagnose fragment = new Diagnose();
        fragment.setTitle(title);
        Bundle bundle = new Bundle();
        bundle.putInt(USER_ID_TAG, userID);
        fragment.setArguments(bundle);
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.newInstace");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.onCreateView");
        View view = inflater.inflate(R.layout.mainpage_viewpager_diagnose, container, false);
        this.unbinder = ButterKnife.bind(this, view);
        this.initializeMedicalRecordModel();
        this.createResultDialog();
        this.generateFieldValidator();
        this.setFormDoneAction();
        //setDummyData(this.user.getId());
        return view;
    }

    private void setDummyData(int userID)
    {
        new AsyncTask<Integer, Void, Void>()
        {
            private final Random random = new Random();

            @Override
            protected Void doInBackground(Integer... param)
            {
                double[][] data = new double[][]
                        {
                                new double[] {44.0, 174.0, 36.5, 123.3, 71.0},
                                new double[] {60.0, 169.0, 36.9, 110.1, 110.0},
                                new double[] {23.0, 196.0, 40.3, 137.1, 93.0},
                                new double[] {41.0, 213.0, 47.4, 143.2, 112.0},
                                new double[] {40.0, 253.0, 46.5, 179.3, 136.0},
                                new double[] {48.0, 197.0, 30.5, 120.5, 79.0},
                                new double[] {49.0, 171.0, 40.1, 104.1, 134.0},
                                new double[] {40.0, 198.0, 40.5, 139.5, 90.0},
                                new double[] {49.0, 184.0, 38.4, 119.2, 133.0},
                                new double[] {59.0, 280.0, 30.5, 202.8, 128.0},
                                new double[] {60.0, 201.0, 41.6, 136.4, 115.0},
                                new double[] {29.0, 145.0, 30.5, 94.7, 99.0},
                                new double[] {46.0, 181.0, 37.7, 118.3, 125.0},
                                new double[] {66.0, 278.0, 57.2, 201.2, 98.0},
                                new double[] {48.0, 186.0, 36.2, 121.8, 140.0},
                                new double[] {66.0, 186.0, 38.1, 132.1, 79.0},
                                new double[] {61.0, 205.0, 45.2, 140.6, 96.0},
                                new double[] {28.0, 182.0, 37.7, 116.9, 137.0},
                                new double[] {34.0, 140.0, 38.8, 137.8, 128.0},
                                new double[] {75.0, 170.0, 37.4, 110.2, 112.0},
                                new double[] {59.0, 280.0, 47.6, 203.2, 146.0},
                                new double[] {45.0, 197.0, 37.4, 144.4, 76.0},
                                new double[] {49.0, 186.0, 36.1, 127.5, 112.0},
                                new double[] {37.0, 188.0, 36.4, 126.4, 126.0},
                                new double[] {39.0, 159.0, 36.7, 149.9, 142.0},
                                new double[] {53.0, 277.0, 51.7, 208.1, 86.0},
                                new double[] {72.0, 253.0, 35.1, 188.1, 149.0},
                                new double[] {54.0, 232.0, 47.9, 164.9, 96.0},
                                new double[] {71.0, 150.0, 34.5, 134.6, 138.0},
                                new double[] {32.0, 229.0, 43.5, 166.3, 96.0},
                                new double[] {54.0, 171.0, 38.1, 115.9, 85.0},
                                new double[] {52.0, 166.0, 37.8, 112.6, 34.0},
                                new double[] {52.0, 203.0, 38.6, 135.6, 144.0},
                                new double[] {58.0, 194.0, 40.1, 65.9, 129.0},
                                new double[] {62.0, 168.0, 35.1, 110.1, 114.0},
                                new double[] {71.0, 174.0, 35.7, 122.1, 81.0},
                                new double[] {34.0, 251.0, 47.1, 182.9, 105.0},
                                new double[] {45.0, 207.0, 46.3, 138.9, 109.0},
                                new double[] {60.0, 180.0, 42.1, 121.1, 84.0},
                                new double[] {48.0, 278.0, 30.2, 218.8, 145.0},
                                new double[] {47.0, 284.0, 52.8, 201.4, 149.0},
                                new double[] {50.0, 184.0, 33.6, 111.8, 93.0},
                                new double[] {53.0, 170.0, 39.7, 103.5, 134.0},
                                new double[] {43.0, 211.0, 35.9, 152.3, 114.0},
                                new double[] {35.0, 169.0, 37.1, 117.1, 74.0},
                                new double[] {70.0, 170.0, 47.1, 150.7, 109.0},
                                new double[] {49.0, 236.0, 42.8, 171.4, 109.0},
                                new double[] {49.0, 178.0, 42.1, 118.9, 85.0},
                                new double[] {62.0, 179.0, 40.1, 120.9, 90.0},
                                new double[] {35.0, 207.0, 40.2, 145.4, 107.0},
                                new double[] {36.0, 209.0, 44.1, 145.3, 98.0},
                                new double[] {59.0, 191.0, 36.1, 126.2, 142.0},
                                new double[] {52.0, 211.0, 42.7, 139.1, 146.0},
                                new double[] {62.0, 169.0, 35.5, 110.0, 104.0},
                                new double[] {59.0, 189.0, 38.2, 134.6, 81.0},
                                new double[] {46.0, 232.0, 41.6, 163.8, 133.0},
                                new double[] {32.0, 170.0, 36.5, 113.9, 98.0},
                                new double[] {61.0, 193.0, 36.7, 138.7, 88.0},
                                new double[] {57.0, 206.0, 37.6, 140.0, 142.0},
                                new double[] {38.0, 300.0, 48.1, 225.3, 133.0},
                                new double[] {56.0, 178.0, 38.4, 110.8, 144.0},
                                new double[] {28.0, 276.0, 54.6, 202.6, 94.0},
                                new double[] {39.0, 276.0, 34.5, 293.6, 115.0},
                                new double[] {49.0, 182.0, 38.6, 122.6, 104.0},
                                new double[] {70.0, 216.0, 42.5, 144.1, 147.0},
                                new double[] {73.0, 200.0, 47.1, 133.1, 99.0},
                                new double[] {50.0, 200.0, 40.0, 141.0, 95.0},
                                new double[] {68.0, 219.0, 41.6, 148.4, 145.0},
                                new double[] {76.0, 197.0, 37.6, 137.4, 110.0},
                                new double[] {56.0, 261.0, 40.5, 193.9, 133.0},
                                new double[] {30.0, 199.0, 40.2, 141.6, 86.0},
                                new double[] {23.0, 223.0, 44.6, 158.8, 98.0},
                                new double[] {63.0, 249.0, 41.5, 183.7, 119.0},
                                new double[] {57.0, 229.0, 40.8, 159.8, 142.0},
                                new double[] {66.0, 224.0, 37.2, 157.4, 147.0},
                                new double[] {30.0, 180.0, 38.4, 122.4, 96.0},
                                new double[] {73.0, 161.0, 37.8, 105.6, 88.0},
                                new double[] {62.0, 160.0, 37.2, 107.0, 79.0},
                                new double[] {57.0, 240.0, 44.2, 171.6, 121.0},
                                new double[] {50.0, 184.0, 36.7, 125.9, 107.0},
                                new double[] {56.0, 148.0, 32.8, 102.8, 67.0},
                                new double[] {57.0, 179.0, 39.5, 120.7, 94.0},
                                new double[] {49.0, 258.0, 40.5, 192.7, 124.0},
                                new double[] {60.0, 182.0, 37.9, 125.5, 93.0},
                                new double[] {38.0, 246.0, 45.1, 141.1, 134.0},
                                new double[] {41.0, 238.0, 44.2, 168.0, 129.0},
                                new double[] {56.0, 240.0, 45.1, 176.3, 133.0},
                                new double[] {56.0, 274.0, 39.5, 206.1, 142.0},
                                new double[] {58.0, 164.0, 39.5, 107.1, 87.0},
                                new double[] {60.0, 192.0, 37.2, 126.8, 140.0},
                                new double[] {70.0, 173.0, 36.2, 111.0, 132.0},
                                new double[] {52.0, 197.0, 40.6, 136.6, 94.0},
                                new double[] {60.0, 169.0, 37.8, 102.6, 143.0},
                                new double[] {55.0, 255.0, 37.8, 184.6, 163.0},
                                new double[] {42.0, 191.0, 40.1, 117.1, 169.0},
                                new double[] {42.0, 242.0, 42.5, 162.5, 185.0},
                                new double[] {25.0, 210.0, 40.1, 134.1, 179.0},
                                new double[] {35.0, 197.0, 39.6, 119.6, 189.0},
                                new double[] {52.0, 157.0, 30.6, 92.6, 169.0},
                                new double[] {51.0, 194.0, 40.3, 129.5, 180.0},
                                new double[] {43.0, 199.0, 35.4, 131.4, 161.0},
                                new double[] {52.0, 176.0, 40.1, 104.5, 157.0},
                                new double[] {61.0, 267.0, 41.5, 188.1, 187.0},
                                new double[] {49.0, 212.0, 40.5, 139.7, 159.0},
                                new double[] {57.0, 168.0, 40.8, 88.8, 192.0},
                                new double[] {60.0, 203.0, 45.1, 124.9, 165.0},
                                new double[] {28.0, 185.0, 42.3, 111.5, 156.0},
                                new double[] {40.0, 224.0, 45.6, 193.0, 186.0},
                                new double[] {52.0, 319.0, 57.2, 228.0, 169.0},
                                new double[] {29.0, 213.0, 36.7, 145.3, 155.0},
                                new double[] {37.0, 239.0, 40.0, 267.9, 187.0},
                                new double[] {46.0, 207.0, 38.2, 136.8, 160.0},
                                new double[] {57.0, 226.0, 42.5, 145.5, 190.0},
                                new double[] {54.0, 186.0, 36.6, 109.8, 198.0},
                                new double[] {45.0, 249.0, 44.2, 170.6, 171.0},
                                new double[] {52.0, 230.0, 36.2, 107.6, 181.0},
                                new double[] {54.0, 199.0, 42.2, 119.2, 188.0},
                                new double[] {50.0, 67.0, 42.1, 112.1, 164.0},
                                new double[] {47.0, 249.0, 42.3, 119.7, 150.0},
                                new double[] {41.0, 209.0, 37.1, 138.3, 168.0},
                                new double[] {56.0, 418.0, 45.7, 338.1, 171.0},
                                new double[] {54.0, 202.0, 38.1, 129.5, 172.0},
                                new double[] {57.0, 176.0, 39.8, 105.6, 153.0},
                                new double[] {53.0, 245.0, 45.7, 56.5, 214.0},
                                new double[] {44.0, 291.0, 46.7, 180.5, 319.0},
                                new double[] {53.0, 223.0, 39.4, 132.2, 252.0},
                                new double[] {26.0, 266.0, 40.2, 181.2, 223.0},
                                new double[] {67.0, 276.0, 51.4, 188.2, 182.0},
                                new double[] {79.0, 340.0, 31.2, 208.9, 162.0},
                                new double[] {38.0, 287.0, 51.6, 172.2, 316.0},
                                new double[] {53.0, 266.0, 45.7, 165.5, 274.0},
                                new double[] {62.0, 162.0, 36.2, 81.0, 224.0},
                                new double[] {45.0, 265.0, 47.6, 150.2, 241.0},
                                new double[] {57.0, 212.0, 36.9, 102.9, 361.0},
                                new double[] {44.0, 268.0, 43.6, 161.2, 316.0},
                                new double[] {85.0, 278.0, 45.3, 140.5, 261.0},
                                new double[] {69.0, 163.0, 32.5, 100.0, 152.0},
                                new double[] {41.0, 173.0, 37.3, 113.5, 281.0},
                                new double[] {33.0, 259.0, 34.1, 166.3, 293.0},
                                new double[] {52.0, 287.0, 36.9, 193.3, 284.0},
                                new double[] {56.0, 189.0, 32.1, 91.7, 326.0},
                                new double[] {49.0, 324.0, 34.0, 252.2, 189.0},
                                new double[] {68.0, 202.0, 37.8, 133.0, 156.0},
                                new double[] {55.0, 180.0, 36.7, 102.5, 204.0},
                                new double[] {65.0, 226.0, 36.1, 145.3, 223.0},
                                new double[] {42.0, 228.0, 38.6, 135.8, 268.0},
                                new double[] {49.0, 204.0, 33.6, 38.3, 461.0},
                                new double[] {51.0, 235.0, 41.6, 146.2, 236.0},
                                new double[] {56.0, 260.0, 42.2, 170.8, 235.0},
                                new double[] {50.0, 180.0, 35.1, 68.7, 381.0},
                                new double[] {29.0, 201.0, 36.1, 147.3, 226.0},
                                new double[] {58.0, 259.0, 44.2, 174.6, 201.0},
                                new double[] {53.0, 246.0, 44.9, 153.5, 238.0},
                                new double[] {56.0, 261.0, 40.6, 176.2, 221.0},
                                new double[] {50.0, 187.0, 36.1, 91.1, 295.0},
                                new double[] {64.0, 192.0, 36.1, 101.9, 260.0},
                                new double[] {53.0, 264.0, 36.9, 159.3, 319.0},
                                new double[] {51.0, 253.0, 39.3, 162.5, 256.0},
                                new double[] {48.0, 201.0, 39.5, 116.7, 224.0},
                                new double[] {53.0, 163.0, 40.1, 105.8, 83.0},
                                new double[] {54.0, 221.0, 39.6, 156.3, 126.0},
                                new double[] {57.0, 170.0, 37.6, 109.4, 115.0},
                                new double[] {43.0, 175.0, 38.6, 117.0, 97.0},
                                new double[] {78.0, 199.0, 36.6, 145.2, 86.0},
                                new double[] {42.0, 207.0, 45.3, 133.3, 142.0},
                                new double[] {58.0, 225.0, 38.4, 159.2, 137.0},
                                new double[] {39.0, 179.0, 38.4, 116.2, 122.0},
                                new double[] {27.0, 165.0, 39.2, 106.8, 95.0},
                                new double[] {40.0, 211.0, 45.6, 167.8, 104.0},
                                new double[] {30.0, 176.0, 36.7, 121.6, 86.0},
                                new double[] {60.0, 281.0, 48.7, 206.5, 129.0},
                                new double[] {48.0, 210.0, 34.8, 213.6, 116.0},
                                new double[] {43.0, 206.0, 33.4, 107.8, 91.0},
                                new double[] {62.0, 147.0, 35.1, 94.5, 87.0},
                                new double[] {95.0, 192.0, 39.2, 123.8, 145.0},
                                new double[] {52.0, 241.0, 40.8, 177.4, 114.0},
                                new double[] {43.0, 181.0, 39.8, 124.8, 82.0},
                                new double[] {46.0, 255.0, 53.7, 110.3, 90.0},
                                new double[] {33.0, 227.0, 40.1, 164.5, 112.0},
                                new double[] {56.0, 162.0, 35.4, 112.0, 73.0},
                                new double[] {70.0, 200.0, 36.5, 136.1, 137.0},
                                new double[] {45.0, 219.0, 41.5, 152.1, 127.0},
                                new double[] {54.0, 198.0, 38.7, 142.1, 86.0},
                                new double[] {50.0, 172.0, 38.7, 112.5, 106.0},
                                new double[] {47.0, 235.0, 45.2, 152.0, 189.0},
                                new double[] {55.0, 169.0, 33.9, 95.8, 198.0},
                                new double[] {65.0, 254.0, 38.6, 183.6, 159.0},
                                new double[] {49.0, 302.0, 52.4, 212.0, 187.0},
                                new double[] {32.0, 237.0, 46.4, 159.0, 158.0},
                                new double[] {51.0, 166.0, 34.1, 100.5, 157.0},
                                new double[] {50.0, 189.0, 35.6, 119.2, 171.0},
                                new double[] {50.0, 196.0, 34.5, 105.1, 282.0},
                                new double[] {41.0, 193.0, 35.7, 113.1, 221.0},
                                new double[] {91.0, 212.0, 30.4, 167.9, 155.0},
                                new double[] {69.0, 240.0, 48.3, 160.7, 155.0},
                                new double[] {80.0, 247.0, 40.5, 159.9, 233.0},
                                new double[] {81.0, 233.0, 40.7, 159.5, 164.0},
                                new double[] {46.0, 250.0, 40.8, 169.0, 201.0},
                                new double[] {70.0, 175.0, 35.1, 99.7, 201.0},
                                new double[] {52.0, 514.0, 32.8, 318.6, 813.0},
                        };

                int                 userId             = param[0];
                final MedicalRecord medicalRecordModel = Diagnose.this.medicalRecordModel;
                while(!medicalRecordModel.isDatabaseReady())
                {
                    ;
                }
                medicalRecordModel.cleanDataByUser(userId);
                final SVM svm = SVM.getInstance();
                for(int i = -1, is = data.length; ++i < is; )
                {
                    final double[]      st        = data[i];
                    final LocalDateTime timestamp = this.getDummyTimestamp(LocalDateTime.now().minusDays(is - i));
                    final StrokeParameter stroke = new StrokeParameter(
                            Years.yearsBetween(Diagnose.this.user.getBirthDate(), timestamp.toLocalDate()).getYears(),
                            st[Parameter.CHOLESTEROL.ordinal()],
                            st[Parameter.HDL.ordinal()],
                            st[Parameter.LDL.ordinal()],
                            st[Parameter.TRIGLYCERIDE.ordinal()]);
                    final StrokeMetadata metadata = new StrokeMetadata(svm.doClassify(stroke));
                    medicalRecordModel.storeMedicalRecordDummyByUser(userId, stroke, metadata, timestamp.toString("yyyy-MM-dd HH:mm:ss"));
                }
                return null;
            }

            private LocalDateTime getDummyTimestamp(LocalDateTime dummyTime)
            {
                if(random.nextDouble() < .5)
                {
                    //plus
                    dummyTime = dummyTime.plusHours(random.nextInt(24 - dummyTime.getHourOfDay()));
                }
                else
                {
                    //minus
                    dummyTime = dummyTime.minusHours(dummyTime.getHourOfDay() - random.nextInt(dummyTime.getHourOfDay()));
                }
                if(random.nextDouble() < .5)
                {
                    //plus
                    dummyTime = dummyTime.plusMinutes(random.nextInt(60 - dummyTime.getMinuteOfHour()));
                }
                else
                {
                    //minus
                    dummyTime = dummyTime.minusMinutes(dummyTime.getMinuteOfHour() - random.nextInt(dummyTime.getMinuteOfHour()));
                }
                if(random.nextDouble() < .5)
                {
                    //plus
                    dummyTime = dummyTime.plusSeconds(random.nextInt(60 - dummyTime.getSecondOfMinute()));
                }
                else
                {
                    //minus
                    dummyTime = dummyTime.minusSeconds(dummyTime.getSecondOfMinute() - random.nextInt(dummyTime.getSecondOfMinute()));
                }
                return dummyTime;
            }
        }.execute(userID);
    }

    private void setFormDoneAction()
    {
        this.triglyceridForm.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent)
            {
                if(action == EditorInfo.IME_ACTION_DONE)
                {
                    Diagnose.this.onCalculatePressed();
                    return true;
                }
                return false;
            }
        });
    }

    private void setPredefinedAge()
    {
        if(this.user != null)
        {
            this.ageForm.setText(String.valueOf(Years.yearsBetween(this.user.getBirthDate(), new LocalDate()).getYears()));
        }
    }

    private void initializeMedicalRecordModel()
    {
        this.medicalRecordModel = new MedicalRecord(getContext());
        this.intializeDatabase();
    }

    private void intializeDatabase()
    {
        new AsyncTask<Void, Void, Void>()
        {
            final FragmentActivity activity = Diagnose.super.getActivity();
            private ProgressDialog progressDialog;

            @Override
            protected Void doInBackground(Void... voids)
            {
                try
                {
                    Diagnose.this.medicalRecordModel.open();
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
                Diagnose.this.getActivity().runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        progressDialog = new ProgressDialog(Diagnose.super.getContext(), R.style.AppTheme_Dark_Dialog);
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
                Diagnose.this.getUserAccount(Diagnose.super.getArguments().getInt(USER_ID_TAG));
                Diagnose.this.setPredefinedAge();

            }
        }.execute();
    }

    private void getUserAccount(int userID)
    {
        final id.ac.ub.filkom.sekcv.appstroke.model.db.model.User userModel = new id.ac.ub.filkom.sekcv.appstroke.model.db.model.User(super.getContext());
        this.user = userModel.getUserByID(userID);
    }

    private void generateFieldValidator()
    {
        final NotEmptyValidator       nonEmptyValidator = new NotEmptyValidator(super.getResources().getString(R.string.mainpage_viewpager_diagnose_validator_nonempty));
        final NumericValidator        numericValidator  = new NumericValidator(super.getResources().getString(R.string.mainpage_viewpager_diagnose_validator_numerical));
        final RangeValidator<Integer> rangeValidator    = new RangeValidator<>(super.getResources().getString(R.string.mainpage_viewpager_diagnose_validator_range), 1, 1000);

        this.ageForm.addValidator(nonEmptyValidator).addValidator(numericValidator).addValidator(rangeValidator);
        this.cholesterolForm.addValidator(nonEmptyValidator).addValidator(numericValidator).addValidator(rangeValidator);
        this.hdlForm.addValidator(nonEmptyValidator).addValidator(numericValidator).addValidator(rangeValidator);
        this.ldlForm.addValidator(nonEmptyValidator).addValidator(numericValidator).addValidator(rangeValidator);
        this.triglyceridForm.addValidator(nonEmptyValidator).addValidator(numericValidator).addValidator(rangeValidator);
    }

    private void createResultDialog()
    {
        this.resultDialog = DialogPlus.newDialog(this.getActivity())
                                      .setHeader(R.layout.mainpage_viewpager_diagnose_result_header)
                                      .setContentHolder(new ViewHolder(R.layout.mainpage_viewpager_diagnose_result_content))
                                      .setFooter(R.layout.mainpage_viewpager_diagnose_result_footer)
                                      .setGravity(super.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? Gravity.BOTTOM : Gravity.CENTER)
                                      .setCancelable(true)
                                      .setPadding(8, 8, 8, 8)
                                      .create();
        this.resultText = ButterKnife.findById(this.resultDialog.getHolderView(), R.id.mainpage_viewpager_diagnose_result_holder_textview_result);
        ButterKnife.findById(this.resultDialog.getHeaderView(), R.id.mainpage_viewpager_diagnose_result_header_button_dismiss).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Diagnose.this.onDialogDissmissPressed();
            }
        });
        ButterKnife.findById(this.resultDialog.getFooterView(), R.id.mainpage_viewpager_diagnose_result_footer_button_to_treatment).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Diagnose.this.onGoToTreatmentPressed();
            }
        });
    }

    private void onGoToTreatmentPressed()
    {
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.onGoToTreatmentPressed");
        this.resultDialog.dismiss();
        ((MainPage) getActivity()).getViewPager().setCurrentItem(Home.ID);
    }

    public void onDialogDissmissPressed()
    {
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.onDialogDissmissPressed");
        this.resultDialog.dismiss();
    }


    @OnClick(R.id.mainpage_viewpager_diagnose_button_calculate)
    public void onCalculatePressed()
    {
        final View focus = super.getActivity().getCurrentFocus();
        if(focus != null)
        {
            Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.onCalculatePressed : Keyboard Hidden");
            ((InputMethodManager) super.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if(this.isFieldValid())
        {
            final StrokeParameter record = this.generateData();
            if(record != null)
            {
                final StrokeMetadata metadata = new StrokeMetadata(SVM.getInstance().doClassify(record));
                if(metadata.getStatus() == Status.NORMAL.ordinal())
                {
                    this.changeResultContent(R.string.mainpage_viewpager_medical_record_status_normal, R.drawable.mainpage_viewpager_diagnose_result_content_icon_normal);
                }
                else if(metadata.getStatus() == Status.HIGH.ordinal())
                {
                    this.changeResultContent(R.string.mainpage_viewpager_medical_record_status_high, R.drawable.mainpage_viewpager_diagnose_result_content_icon_high);
                }
                else if(metadata.getStatus() == Status.DANGER.ordinal())
                {
                    this.changeResultContent(R.string.mainpage_viewpager_medical_record_status_danger, R.drawable.mainpage_viewpager_diagnose_result_content_icon_danger);
                }
                this.resultDialog.show();
                this.medicalRecordModel.storeMedicalRecordByUser(this.user.getId(), record, metadata);
            }
        }
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.onCalculatePressed");
    }

    private StrokeParameter generateData()
    {
        final NumberFormat formatter = NumberFormat.getInstance();
        try
        {
            return new StrokeParameter(
                    formatter.parse(this.ageForm.getText().toString()).intValue(),
                    formatter.parse(this.cholesterolForm.getText().toString()).doubleValue(),
                    formatter.parse(this.hdlForm.getText().toString()).doubleValue(),
                    formatter.parse(this.ldlForm.getText().toString()).doubleValue(),
                    formatter.parse(this.triglyceridForm.getText().toString()).doubleValue()
            );
        }
        catch(ParseException ignored)
        {
        }
        return null;
    }

    private boolean isFieldValid()
    {
        boolean isValid = true;
        isValid &= this.ageForm.validate();
        isValid &= this.cholesterolForm.validate();
        isValid &= this.hdlForm.validate();
        isValid &= this.ldlForm.validate();
        isValid &= this.triglyceridForm.validate();
        return isValid;
    }

    private void changeResultContent(int resIdText, int resIdIcon)
    {
        this.resultText.setText(resIdText);
        this.resultText.setCompoundDrawablesWithIntrinsicBounds(0, 0, resIdIcon, 0);
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.changeResultContent");
    }

    private boolean isInputValid()
    {
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.isInputValid");
        return true;
    }
    //----------------------------------------------------------------------------------------------


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.onAttach");
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        this.resultDialog.dismiss();
        this.unbinder.unbind();
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.onDestroyView");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.onActivityCreated");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.onStart");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.onResume");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.onPause");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.onStop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.onSaveInstanceState");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.onViewStateRestored");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.onDestroy");
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        Log.i("Diagnose", "controller.mainpage.viewpager.Diagnose.onDetach");
    }
}
