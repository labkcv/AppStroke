package id.ac.ub.filkom.sekcv.appstroke.controller;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;

import id.ac.ub.filkom.sekcv.appstroke.R;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.controller> created by :
 * Name         : syafiq
 * Date / Time  : 06 October 2016, 7:38 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class HelpPage extends AppIntro2
{
    public static final String CLASSNAME         = "HelpPage";
    public static final String CLASSPATH         = "controller";
    public static final String TAG               = CLASSPATH + "." + CLASSNAME;
    public static final String IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";

    private boolean isUserLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(HelpPage.CLASSNAME, HelpPage.TAG + ".onCreate");

        super.onCreate(savedInstanceState);

        final Bundle extras = super.getIntent().getExtras();
        if((extras != null) && extras.containsKey(HelpPage.IS_USER_LOGGED_IN))
        {
            this.isUserLoggedIn = extras.getBoolean(HelpPage.IS_USER_LOGGED_IN);
        }

        final Resources resources = super.getResources();
        if(this.isUserLoggedIn)
        {
            super.addSlide(AppIntro2Fragment.newInstance(resources.getString(R.string.helppage_content_slide_1_title), resources.getString(R.string.helppage_content_slide_1_description), R.drawable.helppage_content_slide_1_image_pro, ContextCompat.getColor(this, R.color.colorPrimary)));
            super.addSlide(AppIntro2Fragment.newInstance(resources.getString(R.string.helppage_content_slide_2_title), resources.getString(R.string.helppage_content_slide_2_description), R.drawable.helppage_content_slide_2_image_pro, ContextCompat.getColor(this, R.color.colorPrimary)));
            super.addSlide(AppIntro2Fragment.newInstance(resources.getString(R.string.helppage_content_slide_3_title), resources.getString(R.string.helppage_content_slide_3_description), R.drawable.helppage_content_slide_3_image_pro, ContextCompat.getColor(this, R.color.colorPrimary)));
            super.addSlide(AppIntro2Fragment.newInstance(resources.getString(R.string.helppage_content_slide_4_title), resources.getString(R.string.helppage_content_slide_4_description), R.drawable.helppage_content_slide_4_image_pro, ContextCompat.getColor(this, R.color.colorPrimary)));
            super.addSlide(AppIntro2Fragment.newInstance(resources.getString(R.string.helppage_content_slide_5_title), resources.getString(R.string.helppage_content_slide_5_description), R.drawable.helppage_content_slide_5_image_pro, ContextCompat.getColor(this, R.color.colorPrimary)));
            super.addSlide(AppIntro2Fragment.newInstance(resources.getString(R.string.helppage_content_slide_6_title), resources.getString(R.string.helppage_content_slide_6_description), R.drawable.helppage_content_slide_6_image_pro, ContextCompat.getColor(this, R.color.colorPrimary)));
        }
        else
        {
            super.addSlide(AppIntro2Fragment.newInstance(resources.getString(R.string.helppage_content_slide_1_title), resources.getString(R.string.helppage_content_slide_1_description), R.drawable.helppage_content_slide_1_image_trial, ContextCompat.getColor(this, R.color.colorPrimary)));
            super.addSlide(AppIntro2Fragment.newInstance(resources.getString(R.string.helppage_content_slide_2_title), resources.getString(R.string.helppage_content_slide_2_description), R.drawable.helppage_content_slide_2_image_trial, ContextCompat.getColor(this, R.color.colorPrimary)));
            super.addSlide(AppIntro2Fragment.newInstance(resources.getString(R.string.helppage_content_slide_5_title), resources.getString(R.string.helppage_content_slide_5_description), R.drawable.helppage_content_slide_5_image_trial, ContextCompat.getColor(this, R.color.colorPrimary)));
            super.addSlide(AppIntro2Fragment.newInstance(resources.getString(R.string.helppage_content_slide_6_title), resources.getString(R.string.helppage_content_slide_6_description), R.drawable.helppage_content_slide_6_image_trial, ContextCompat.getColor(this, R.color.colorPrimary)));
        }
    }

    //---------------------------------------------------------------------------------------------
    //----------------Listener Button Edit Below---------------------------------------------------
    //---------------------------------------------------------------------------------------------


    @Override
    public void onBackPressed()
    {
        Log.d(HelpPage.CLASSNAME, HelpPage.TAG + ".onBackPressed");

        super.onBackPressed();
        super.finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment)
    {
        Log.d(HelpPage.CLASSNAME, HelpPage.TAG + ".onSkipPressed");

        super.onSkipPressed(currentFragment);
        this.onBackPressed();
    }

    @Override
    public void onDonePressed(Fragment currentFragment)
    {
        Log.d(HelpPage.CLASSNAME, HelpPage.TAG + ".onDonePressed");

        super.onDonePressed(currentFragment);
        this.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        Log.d(HelpPage.CLASSNAME, HelpPage.TAG + ".onSaveInstanceState");

        outState.putBoolean(HelpPage.IS_USER_LOGGED_IN, this.isUserLoggedIn);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        Log.d(HelpPage.CLASSNAME, HelpPage.TAG + ".onRestoreInstanceState");

        if((savedInstanceState != null) && savedInstanceState.containsKey(HelpPage.IS_USER_LOGGED_IN))
        {
            this.isUserLoggedIn = savedInstanceState.getBoolean(HelpPage.IS_USER_LOGGED_IN);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }
}