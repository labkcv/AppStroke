package id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.core.component> created by :
 * Name         : syafiq
 * Date / Time  : 30 August 2016, 11:55 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public enum Status
{
    NORMAL(new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Status("normal", "Normal")),
    HIGH(new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Status("rentan", "Rentan")),
    DANGER(new id.ac.ub.filkom.sekcv.appstroke.model.dataset.Status("mengkhawatirkan", "Mengkhawatirkan"));

    private final id.ac.ub.filkom.sekcv.appstroke.model.dataset.Status status;

    Status(id.ac.ub.filkom.sekcv.appstroke.model.dataset.Status status)
    {
        this.status = status;
    }

    public id.ac.ub.filkom.sekcv.appstroke.model.dataset.Status getStatus()
    {
        return status;
    }
}
