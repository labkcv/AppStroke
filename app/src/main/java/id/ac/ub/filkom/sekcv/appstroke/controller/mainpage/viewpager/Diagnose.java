package id.ac.ub.filkom.sekcv.appstroke.controller.mainpage.viewpager;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;

import id.ac.ub.filkom.sekcv.appstroke.R;
import id.ac.ub.filkom.sekcv.appstroke.controller.MainPage;
import id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.SVM;
import id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component.Status;
import id.ac.ub.filkom.sekcv.appstroke.model.custom.android.support.v4.app.TitledFragment;
import id.ac.ub.filkom.sekcv.appstroke.model.dataset.StrokeMetadata;
import id.ac.ub.filkom.sekcv.appstroke.model.dataset.StrokeParameter;
import id.ac.ub.filkom.sekcv.appstroke.model.db.entity.Entity_User;
import id.ac.ub.filkom.sekcv.appstroke.model.db.model.Model_MedicalRecord;
import id.ac.ub.filkom.sekcv.appstroke.model.util.TaskDelegatable;
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
    public static final String CLASSNAME = "Diagnose";
    public static final String CLASSPATH = "controller.mainpage.viewpager";
    public static final String TAG       = CLASSPATH + "." + CLASSNAME;
    public static final int    ID        = 0b001;

    private TextView         resultText;
    private MaterialEditText ageForm;
    private MaterialEditText cholesterolForm;
    private MaterialEditText hdlForm;
    private MaterialEditText ldlForm;
    private MaterialEditText triglycerideForm;

    private DialogPlus          resultDialog;
    private Entity_User         user;
    private Model_MedicalRecord medicalRecordModel;
    private MainPage            root;

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static Diagnose newInstance(String title)
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".newInstance");

        final Diagnose fragment = new Diagnose();
        fragment.setTitle(title);
        return fragment;
    }

    //----------------------------------------------------------------------------------------------
    //---App Life Cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".onCreate");

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".onCreateView");

        final View view = inflater.inflate(R.layout.mainpage_viewpager_diagnose, container, false);
        this.root = ((MainPage) super.getActivity());
        this.registerView(view);
        this.registerListener(view);

        new StartUpTask(new TaskDelegatable()
        {
            @Override
            public void delegate()
            {
                Diagnose.this.getUserAccount();
                Diagnose.this.initializeMedicalRecordModel();
                Diagnose.this.setPredefinedUserAge();
                Diagnose.this.createResultDialog();
                Diagnose.this.generateFieldValidator();
                Diagnose.this.setCalculateAction();
            }
        }).execute();

        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".onAttach");

        super.onAttach(context);
    }

    @Override
    public void onDestroyView()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".onDestroyView");

        this.resultDialog.dismiss();
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".onActivityCreated");

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".onStart");

        super.onStart();
    }

    @Override
    public void onResume()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".onResume");

        super.onResume();
    }

    @Override
    public void onPause()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".onPause");

        super.onPause();
    }

    @Override
    public void onStop()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".onStop");

        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".onSaveInstanceState");

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".onViewStateRestored");

        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDestroy()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".onDestroy");

        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".onDetach");

        super.onDetach();
    }

    //----------------------------------------------------------------------------------------------
    //---App Interface Dependency
    //----------------------------------------------------------------------------------------------

    private void registerView(final View container)
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".registerView");

        this.ageForm = (MaterialEditText) container.findViewById(R.id.mainpage_viewpager_diagnose_edittext_age);
        this.cholesterolForm = (MaterialEditText) container.findViewById(R.id.mainpage_viewpager_diagnose_edittext_cholesterol);
        this.hdlForm = (MaterialEditText) container.findViewById(R.id.mainpage_viewpager_diagnose_edittext_hdl);
        this.ldlForm = (MaterialEditText) container.findViewById(R.id.mainpage_viewpager_diagnose_edittext_ldl);
        this.triglycerideForm = (MaterialEditText) container.findViewById(R.id.mainpage_viewpager_diagnose_edittext_triglyceride);
    }

    private void registerListener(final View container)
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".registerListener");

        container.findViewById(R.id.mainpage_viewpager_diagnose_button_calculate).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Diagnose.this.onCalculatePressed();
            }
        });
    }

    private void setCalculateAction()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".setCalculateAction");

        this.triglycerideForm.setOnEditorActionListener(new TextView.OnEditorActionListener()
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

    private void setPredefinedUserAge()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".setPredefinedUserAge");

        if(this.user != null)
        {
            this.ageForm.setText(String.valueOf(this.calculateUserAge(this.user.getBirthDate())));
            this.ageForm.setEnabled(false);
            this.ageForm.setFocusable(false);
        }
    }

    private int calculateUserAge(LocalDate birthDate)
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".calculateUserAge");

        return Years.yearsBetween(birthDate, new LocalDate()).getYears();
    }

    private void initializeMedicalRecordModel()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".initializeMedicalRecordModel");

        this.medicalRecordModel = this.root.getMedicalRecordModel();
    }

    private void getUserAccount()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".getUserAccount");

        this.user = this.root.getUser();
    }

    private void generateFieldValidator()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".generateFieldValidator");

        final NotEmptyValidator       nonEmptyValidator = new NotEmptyValidator(super.getResources().getString(R.string.mainpage_viewpager_diagnose_validator_nonempty));
        final NumericValidator        numericValidator  = new NumericValidator(super.getResources().getString(R.string.mainpage_viewpager_diagnose_validator_numerical));
        final RangeValidator<Integer> rangeValidator    = new RangeValidator<>(super.getResources().getString(R.string.mainpage_viewpager_diagnose_validator_range), 1, 1000);

        this.ageForm.addValidator(nonEmptyValidator).addValidator(numericValidator).addValidator(rangeValidator);
        this.cholesterolForm.addValidator(nonEmptyValidator).addValidator(numericValidator).addValidator(rangeValidator);
        this.hdlForm.addValidator(nonEmptyValidator).addValidator(numericValidator).addValidator(rangeValidator);
        this.ldlForm.addValidator(nonEmptyValidator).addValidator(numericValidator).addValidator(rangeValidator);
        this.triglycerideForm.addValidator(nonEmptyValidator).addValidator(numericValidator).addValidator(rangeValidator);
    }

    private void createResultDialog()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".createResultDialog");

        this.resultDialog = DialogPlus.newDialog(this.getActivity())
                                      .setHeader(R.layout.mainpage_viewpager_diagnose_result_header)
                                      .setContentHolder(new ViewHolder(R.layout.mainpage_viewpager_diagnose_result_content))
                                      .setFooter(R.layout.mainpage_viewpager_diagnose_result_footer)
                                      .setGravity(super.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? Gravity.BOTTOM : Gravity.CENTER)
                                      .setCancelable(true)
                                      .setPadding(8, 8, 8, 8)
                                      .create();
        this.resultText = (TextView) this.resultDialog.getHolderView().findViewById(R.id.mainpage_viewpager_diagnose_result_holder_textview_result);

        this.resultDialog.getHeaderView().findViewById(R.id.mainpage_viewpager_diagnose_result_header_button_dismiss).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Diagnose.this.onDialogDismissPressed();
            }
        });
        this.resultDialog.getFooterView().findViewById(R.id.mainpage_viewpager_diagnose_result_footer_button_to_treatment).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Diagnose.this.onGoToTreatmentPressed();
            }
        });
    }

    private void changeResultContent(int resIdText, int resIdIcon)
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".changeResultContent");

        this.resultText.setText(resIdText);
        this.resultText.setCompoundDrawablesWithIntrinsicBounds(0, 0, resIdIcon, 0);
    }

    //----------------------------------------------------------------------------------------------
    //---App User Function
    //----------------------------------------------------------------------------------------------

    private void onGoToTreatmentPressed()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".onGoToTreatmentPressed");

        this.onDialogDismissPressed();
        this.root.getViewPager().setCurrentItem(Treatment.ID);
    }

    public void onDialogDismissPressed()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".onDialogDismissPressed");

        this.resultDialog.dismiss();
    }

    public void onCalculatePressed()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".onCalculatePressed");

        final View focus = this.root.getCurrentFocus();
        if(focus != null)
        {
            ((InputMethodManager) this.root.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if(this.isFieldValid())
        {
            final StrokeParameter record = this.generateStrokeData();
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
                if(this.user != null)
                {
                    this.medicalRecordModel.storeMedicalRecordByUser(this.user.getId(), record, metadata);
                }
                this.root.updateStroke(record, metadata);
                this.root.updateMedicalRecordData();
            }
        }
    }

    @Nullable
    private StrokeParameter generateStrokeData()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".generateStrokeData");

        final NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        try
        {
            return new StrokeParameter(
                    formatter.parse(this.ageForm.getText().toString()).intValue(),
                    formatter.parse(this.cholesterolForm.getText().toString()).doubleValue(),
                    formatter.parse(this.hdlForm.getText().toString()).doubleValue(),
                    formatter.parse(this.ldlForm.getText().toString()).doubleValue(),
                    formatter.parse(this.triglycerideForm.getText().toString()).doubleValue()
            );
        }
        catch(ParseException ignored)
        {
        }
        return null;
    }

    private boolean isFieldValid()
    {
        Log.d(Diagnose.CLASSNAME, Diagnose.TAG + ".isFieldValid");

        boolean isValid = this.ageForm.validate();
        isValid &= this.cholesterolForm.validate();
        isValid &= this.hdlForm.validate();
        isValid &= this.ldlForm.validate();
        isValid &= this.triglycerideForm.validate();
        return isValid;
    }

    //----------------------------------------------------------------------------------------------
    //---Class Helper
    //----------------------------------------------------------------------------------------------

    private final class StartUpTask extends AsyncTask<Void, Void, Void>
    {
        public static final String CLASSNAME = "StartUpTask";

        private final LinkedList<TaskDelegatable> delegations;

        public StartUpTask(TaskDelegatable... delegations)
        {
            Log.d(Diagnose.CLASSNAME, Diagnose.TAG + "." + StartUpTask.CLASSNAME + ".Constructor");

            this.delegations = new LinkedList<>();
            Collections.addAll(this.delegations, delegations);
        }

        @Override
        protected void onPreExecute()
        {
            Log.d(Diagnose.CLASSNAME, Diagnose.TAG + "." + StartUpTask.CLASSNAME + ".onPreExecute");

            super.onPreExecute();
        }

        @Nullable
        @SuppressWarnings({"StatementWithEmptyBody", "UnnecessarySemicolon"})
        @Override
        protected Void doInBackground(Void... voids)
        {
            Log.d(Diagnose.CLASSNAME, Diagnose.TAG + "." + StartUpTask.CLASSNAME + ".doInBackground");

            while(!Diagnose.this.root.isActivityReady())
            {
                ;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            Log.d(Diagnose.CLASSNAME, Diagnose.TAG + "." + StartUpTask.CLASSNAME + ".onPostExecute");

            for(final TaskDelegatable delegation : this.delegations)
            {
                delegation.delegate();
            }
        }
    }
}
