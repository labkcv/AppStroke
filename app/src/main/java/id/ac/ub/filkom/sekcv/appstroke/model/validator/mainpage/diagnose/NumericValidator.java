package id.ac.ub.filkom.sekcv.appstroke.model.validator.mainpage.diagnose;

import android.support.annotation.NonNull;

import com.rengwuxian.materialedittext.validation.METValidator;

import java.text.ParseException;
import java.util.Locale;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.validator.mainpage.diagnose> created by :
 * Name         : syafiq
 * Date / Time  : 28 August 2016, 6:03 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class NumericValidator extends METValidator
{

    public NumericValidator(@NonNull String errorMessage)
    {
        super(errorMessage);
    }

    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty)
    {
        try
        {
            java.text.NumberFormat.getInstance(Locale.getDefault()).parse(text.toString());
            return true;
        }
        catch(ParseException ignored)
        {
            return false;
        }
    }
}
