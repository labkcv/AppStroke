package id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component;

import android.graphics.Color;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component> created by :
 * Name         : syafiq
 * Date / Time  : 30 August 2016, 11:08 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public enum Parameter
{
    AGE(new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter("age", "Age", Color.parseColor("#8BC34A"))),
    CHOLESTEROL(new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter("cholesterol", "Cholesterol", Color.parseColor("#E91E63"))),
    HDL(new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter("hdl", "HDL", Color.parseColor("#3F51B5"))),
    LDL(new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter("ldl", "LDL", Color.parseColor("#00BCD4"))),
    TRIGLYCERIDE(new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter("triglyceride", "Triglyceride", Color.parseColor("#FFC107")));

    private final id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter parameter;

    Parameter(id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter parameter)
    {
        this.parameter = parameter;
    }

    public id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter getParameter()
    {
        return parameter;
    }

}
