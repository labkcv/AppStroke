package id.ac.ub.filkom.sekcv.appstroke.mainpage.viewpager;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.ac.ub.filkom.sekcv.appstroke.R;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.mainpage.viewpager> created by :
 * Name         : syafiq
 * Date / Time  : 11 August 2016, 6:09 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Diagnose extends Fragment
{
    public static final String TAG = "mainpage.viewpager.Diagnose";
    public static final int    ID  = 0x10010;
    //@BindView(R.id.mainpage_viewpager_diagnose_button_calculate) public Button calculateButton;
    public TextView resultText;
    private Unbinder   unbinder;
    private DialogPlus resultDialog;

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static Diagnose newInstance()
    {
        final Diagnose fragment = new Diagnose();
        Log.i("Diagnose", "mainpage.viewpager.Diagnose.newInstace");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i("Diagnose", "mainpage.viewpager.Diagnose.onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.mainpage_viewpager_diagnose, container, false);
        this.unbinder = ButterKnife.bind(this, view);
        Log.i("Diagnose", "mainpage.viewpager.Diagnose.onCreateView");
        this.createResultDialog();
        return view;
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
        final TextView dissmiss      = ButterKnife.findById(this.resultDialog.getHeaderView(), R.id.mainpage_viewpager_diagnose_result_header_button_dismiss);
        final TextView gotoTreatment = ButterKnife.findById(this.resultDialog.getFooterView(), R.id.mainpage_viewpager_diagnose_result_footer_button_to_treatment);
        dissmiss.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Diagnose.this.onDialogDissmissPressed();
            }
        });
        gotoTreatment.setOnClickListener(new View.OnClickListener()
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
        Log.i("Diagnose", "mainpage.viewpager.Diagnose.onGoToTreatmentPressed");
    }

    public void onDialogDissmissPressed()
    {
        Log.i("Diagnose", "mainpage.viewpager.Diagnose.onDialogDissmissPressed");
        this.resultDialog.dismiss();
    }

    @OnClick(R.id.mainpage_viewpager_diagnose_button_calculate)
    public void onCalculatePressed()
    {
        final View focus = super.getActivity().getCurrentFocus();
        if(focus != null)
        {
            Log.i("Diagnose", "mainpage.viewpager.Diagnose.onCalculatePressed : Keyboard Hidden");
            ((InputMethodManager) super.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if(this.isInputValid())
        {
            double random = Math.random();
            if(random < 0.33)
            {
                this.changeResultContent(R.string.mainpage_viewpager_diagnose_result_content_label_smile, R.drawable.mainpage_viewpager_diagnose_result_content_icon_smile);
            }
            else if(random < 0.66)
            {
                this.changeResultContent(R.string.mainpage_viewpager_diagnose_result_content_label_sad, R.drawable.mainpage_viewpager_diagnose_result_content_icon_sad);
            }
            else
            {
                this.changeResultContent(R.string.mainpage_viewpager_diagnose_result_content_label_cry, R.drawable.mainpage_viewpager_diagnose_result_content_icon_cry);
            }
            this.resultDialog.show();
        }
        Log.i("Diagnose", "mainpage.viewpager.Diagnose.onCalculatePressed");
    }

    private void changeResultContent(int resIdText, int resIdIcon)
    {
        this.resultText.setText(resIdText);
        this.resultText.setCompoundDrawablesWithIntrinsicBounds(0, 0, resIdIcon, 0);
        Log.i("Diagnose", "mainpage.viewpager.Diagnose.changeResultContent");
    }

    private boolean isInputValid()
    {
        Log.i("Diagnose", "mainpage.viewpager.Diagnose.isInputValid");
        return true;
    }
    //----------------------------------------------------------------------------------------------

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        this.resultDialog.dismiss();
        this.unbinder.unbind();
        Log.i("Diagnose", "mainpage.viewpager.Diagnose.onDestroyView");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.i("Diagnose", "mainpage.viewpager.Diagnose.onActivityCreated");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.i("Diagnose", "mainpage.viewpager.Diagnose.onStart");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i("Diagnose", "mainpage.viewpager.Diagnose.onResume");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.i("Diagnose", "mainpage.viewpager.Diagnose.onPause");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.i("Diagnose", "mainpage.viewpager.Diagnose.onStop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.i("Diagnose", "mainpage.viewpager.Diagnose.onSaveInstanceState");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        Log.i("Diagnose", "mainpage.viewpager.Diagnose.onViewStateRestored");
    }
}
