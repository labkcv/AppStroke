package id.ac.ub.filkom.sekcv.appstroke.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;

import id.ac.ub.filkom.sekcv.appstroke.R;

public class AboutUsPage extends AppCompatActivity
{
    public static final String CLASSNAME = "AboutUsPage";
    public static final String CLASSPATH = "controller";
    public static final String TAG       = CLASSPATH + "." + CLASSNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(AboutUsPage.CLASSNAME, AboutUsPage.TAG + ".onCreate");

        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.aboutuspage_container);

        JodaTimeAndroid.init(this);

        this.setToolbar();

        ((TextView) super.findViewById(R.id.aboutuspage_textview_copyright)).setText(super.getString(R.string.aboutuspage_textview_copyright, DateTime.now().getYear()));
    }

    //----------------------------------------------------------------------------------------------
    //---App Interface Dependency
    //----------------------------------------------------------------------------------------------

    private void setToolbar()
    {
        Log.d(AboutUsPage.CLASSNAME, AboutUsPage.TAG + ".setToolbar");

        final Toolbar toolbar = (Toolbar) super.findViewById(R.id.aboutuspage_toolbar);
        super.setSupportActionBar(toolbar);
        final ActionBar actionBar = super.getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            toolbar.setContentInsetStartWithNavigation(4);
            toolbar.setNavigationOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    AboutUsPage.this.onBackPressed();
                }
            });
        }
    }

    //---------------------------------------------------------------------------------------------
    //----------------Listener Button Edit Below---------------------------------------------------
    //---------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed()
    {
        Log.d(AboutUsPage.CLASSNAME, AboutUsPage.TAG + ".onBackPressed");

        super.onBackPressed();
        super.finish();
    }
}
