package id.ac.ub.filkom.sekcv.appstroke.model.validator.mainpage.diagnose;

import android.support.annotation.NonNull;

import com.rengwuxian.materialedittext.validation.METValidator;

import java.text.ParseException;
import java.util.Locale;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.validator.mainpage.diagnose> created by :
 * Name         : syafiq
 * Date / Time  : 28 August 2016, 5:55 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class RangeValidator<DataType extends Number> extends METValidator
{

    private final DataType min;
    private final DataType max;

    public RangeValidator(@NonNull String errorMessage, DataType min, DataType max)
    {
        super(String.format(errorMessage, min.toString(), max.toString()));
        if(min.doubleValue() > max.doubleValue())
        {
            this.min = max;
            this.max = min;
        }
        else
        {
            this.min = min;
            this.max = max;
        }
    }

    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty)
    {
        Number value;
        try
        {
            value = java.text.NumberFormat.getInstance(Locale.getDefault()).parse(text.toString());
        }
        catch(ParseException e)
        {
            value = 0;
        }
        return value.doubleValue() >= min.doubleValue() && value.doubleValue() <= this.max.doubleValue();
    }
}
