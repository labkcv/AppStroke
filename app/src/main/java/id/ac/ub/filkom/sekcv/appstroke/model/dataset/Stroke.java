package id.ac.ub.filkom.sekcv.appstroke.model.dataset;

import android.support.annotation.NonNull;

import id.ac.ub.filkom.sekcv.appstroke.model.db.entity.MedicalRecord;

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

    @org.jetbrains.annotations.Contract("_ -> !null")
    public static Stroke newInstanceFromMedicalRecord(@NonNull final MedicalRecord record)
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
