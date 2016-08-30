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
import org.joda.time.Years;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.ac.ub.filkom.sekcv.appstroke.R;
import id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.SVM;
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
    public static final int    ID  = 0x10010;

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
        this.getUserAccount(super.getArguments().getInt(USER_ID_TAG));
        this.setPredefinedAge();
        this.setFormDoneAction();
        return view;
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
                    Thread.sleep(5000);
                    Diagnose.this.medicalRecordModel.open();
                }
                catch(SQLException ignored)
                {
                    Toast.makeText(this.activity, this.activity.getResources().getString(R.string.mainpage_viewpager_diagnose_label_error_db), Toast.LENGTH_SHORT).show();
                    this.activity.finish();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
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
            }
        }.execute();
    }

    private void getUserAccount(int userID)
    {
        if(userID == 1)
        {
            this.user = new User(1, LocalDate.parse("1993-12-16"), "Muhammad Syafiq", "syafiq.rezpector@gmail.com", "473bb7b11dd3c3a67a446f7743b4d3af", true);
        }
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
                switch(metadata.getStatus())
                {
                    case 0:
                    {
                        this.changeResultContent(R.string.mainpage_viewpager_diagnose_result_content_label_normal, R.drawable.mainpage_viewpager_diagnose_result_content_icon_normal);
                    }
                    break;
                    case 1:
                    {
                        this.changeResultContent(R.string.mainpage_viewpager_diagnose_result_content_label_high, R.drawable.mainpage_viewpager_diagnose_result_content_icon_high);
                    }
                    break;
                    case 2:
                    {
                        this.changeResultContent(R.string.mainpage_viewpager_diagnose_result_content_label_danger, R.drawable.mainpage_viewpager_diagnose_result_content_icon_danger);
                    }
                    break;
                }
                this.resultDialog.show();
                this.medicalRecordModel.storeMedicalRecord(this.user.getId(), record, metadata);
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
