package id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component> created by :
 * Name         : syafiq
 * Date / Time  : 30 August 2016, 12:21 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Parameter
{
    private static Parameter ourInstance = new Parameter();
    private final id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter[] parameters;

    private Parameter()
    {
        parameters = new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter[]
                {
                        new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter("age", "Age"),
                        new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter("cholesterol", "Cholesterol"),
                        new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter("hdl", "HDL"),
                        new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter("ldl", "LDL"),
                        new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter("triglyceride", "Triglyceride")
                };
    }

    public static Parameter getInstance()
    {
        return ourInstance;
    }

    public id.ac.ub.filkom.sekcv.appstroke.model.dataset.Parameter[] getParameters()
    {
        return parameters;
    }
}
