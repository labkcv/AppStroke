package id.ac.ub.filkom.sekcv.appstroke.model.validator.mainpage.diagnose;

import android.support.annotation.NonNull;

import com.rengwuxian.materialedittext.validation.METValidator;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.validator.mainpage.diagnose> created by :
 * Name         : syafiq
 * Date / Time  : 28 August 2016, 6:45 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class NotEmptyValidator extends METValidator
{

    public NotEmptyValidator(@NonNull String errorMessage)
    {
        super(errorMessage);
    }

    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty)
    {
        return text.toString().trim().length() >= 1;
    }
}
