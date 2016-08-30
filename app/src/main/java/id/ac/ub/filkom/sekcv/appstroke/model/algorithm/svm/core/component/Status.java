package id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component> created by :
 * Name         : syafiq
 * Date / Time  : 30 August 2016, 12:37 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Status
{
    private static Status ourInstance = new Status();
    private final id.ac.ub.filkom.sekcv.appstroke.model.dataset.Status[] statuses;

    private Status()
    {
        statuses = new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Status[]
                {
                        new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Status("normal", "Normal"),
                        new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Status("rentan", "Rentan"),
                        new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Status("mengkhawatirkan", "Mengkhawatirkan"),
                };
    }

    public static Status getInstance()
    {
        return ourInstance;
    }

    public id.ac.ub.filkom.sekcv.appstroke.model.dataset.Status[] getStatuses()
    {
        return statuses;
    }
}
