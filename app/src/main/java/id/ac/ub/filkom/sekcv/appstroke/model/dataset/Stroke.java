package id.ac.ub.filkom.sekcv.appstroke.model.dataset;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.dataset> created by :
 * Name         : syafiq
 * Date / Time  : 02 September 2016, 10:42 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Stroke
{
    private StrokeParameter parameter;
    private StrokeMetadata  metadata;

    public Stroke(StrokeParameter parameter, StrokeMetadata metadata)
    {
        this.parameter = parameter;
        this.metadata = metadata;
    }

    public StrokeParameter getParameter()
    {
        return parameter;
    }

    public void setParameter(StrokeParameter parameter)
    {
        this.parameter = parameter;
    }

    public StrokeMetadata getMetadata()
    {
        return metadata;
    }

    public void setMetadata(StrokeMetadata metadata)
    {
        this.metadata = metadata;
    }
}
