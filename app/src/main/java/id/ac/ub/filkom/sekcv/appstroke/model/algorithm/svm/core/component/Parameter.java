package id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component> created by :
 * Name         : syafiq
 * Date / Time  : 30 August 2016, 11:08 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public enum Parameter
{
    AGE(new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter("age", "Age")),
    CHOLESTEROL(new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter("cholesterol", "Cholesterol")),
    HDL(new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter("hdl", "HDL")),
    LDL(new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter("ldl", "LDL")),
    TRIGLYCERIDE(new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter("triglyceride", "Triglyceride"));

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
