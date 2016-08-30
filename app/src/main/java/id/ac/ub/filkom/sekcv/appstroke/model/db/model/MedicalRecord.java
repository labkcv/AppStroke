package id.ac.ub.filkom.sekcv.appstroke.model.db.model;

import android.content.Context;

import id.ac.ub.filkom.sekcv.appstroke.model.dataset.StrokeMetadata;
import id.ac.ub.filkom.sekcv.appstroke.model.dataset.StrokeParameter;
import id.ac.ub.filkom.sekcv.appstroke.model.db.core.DatabaseContract;
import id.ac.ub.filkom.sekcv.appstroke.model.db.core.DatabaseModel;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.db.model> created by :
 * Name         : syafiq
 * Date / Time  : 29 August 2016, 8:12 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class MedicalRecord extends DatabaseModel
{
    public MedicalRecord(final Context context)
    {
        super(context);
    }

    public void storeMedicalRecord(int userID, StrokeParameter parameter, StrokeMetadata metadata)
    {
        super.database.execSQL("" +
                        "INSERT INTO " + DatabaseContract.MedicalRecord.TABLE_NAME + " VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)",
                new Object[] {userID, parameter.getAge(), parameter.getCholesterol(), parameter.getHdl(), parameter.getLdl(), parameter.getTriglyceride(), metadata.getStatus()});

    }
}
