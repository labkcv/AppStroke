package id.ac.ub.filkom.sekcv.appstroke.model.dataset;

import android.support.annotation.NonNull;

import id.ac.ub.filkom.sekcv.appstroke.model.db.entity.Entity_MedicalRecord;

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

    public Stroke()
    {
        this(null, null);
    }

    public Stroke(StrokeParameter parameter, StrokeMetadata metadata)
    {
        this.parameter = parameter;
        this.metadata = metadata;
    }

    public static Stroke newInstanceFromMedicalRecord(@NonNull final Entity_MedicalRecord record)
    {
        return new Stroke(
                new StrokeParameter(
                        record.getAge(),
                        record.getCholesterol(),
                        record.getHdl(),
                        record.getLdl(),
                        record.getTriglyceride()
                ),
                new StrokeMetadata(
                        record.getStatus()
                )
        );
    }

    public StrokeParameter getParameter()
    {
        return this.parameter;
    }

    public void setParameter(@NonNull StrokeParameter parameter)
    {
        this.parameter = parameter;
    }

    public StrokeMetadata getMetadata()
    {
        return this.metadata;
    }

    public void setMetadata(@NonNull StrokeMetadata metadata)
    {
        this.metadata = metadata;
    }

    public void updateStroke(@NonNull final Stroke stroke)
    {
        this.updateStroke(stroke.parameter, stroke.metadata);
    }

    public void updateStroke(@NonNull final StrokeParameter parameter, @NonNull final StrokeMetadata metadata)
    {
        this.setParameter(parameter);
        this.setMetadata(metadata);
    }
}
