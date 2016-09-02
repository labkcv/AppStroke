package id.ac.ub.filkom.sekcv.appstroke.model.db.model;

import android.content.Context;
import android.database.Cursor;

import java.util.LinkedList;
import java.util.List;

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

    public void storeMedicalRecordByUser(int userID, StrokeParameter parameter, StrokeMetadata metadata)
    {
        super.database.execSQL("" +
                        "INSERT INTO " + DatabaseContract.MedicalRecord.TABLE_NAME + " VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)",
                new Object[] {userID, parameter.getAge(), parameter.getCholesterol(), parameter.getHdl(), parameter.getLdl(), parameter.getTriglyceride(), metadata.getStatus()});

    }

    public void storeMedicalRecordDummyByUser(int userID, StrokeParameter parameter, StrokeMetadata metadata, String timestamp)
    {
        super.database.execSQL("" +
                        "INSERT INTO " + DatabaseContract.MedicalRecord.TABLE_NAME + " VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[] {userID, parameter.getAge(), parameter.getCholesterol(), parameter.getHdl(), parameter.getLdl(), parameter.getTriglyceride(), metadata.getStatus(), timestamp});

    }

    public void cleanDataByUser(int userID)
    {
        super.database.execSQL("" +
                        "DELETE FROM " + DatabaseContract.MedicalRecord.TABLE_NAME + " WHERE " + DatabaseContract.MedicalRecord.TABLE_NAME + "." + DatabaseContract.MedicalRecord.COLUMN_NAME_USER + " = ?",
                new Object[] {userID});
    }

    public List<id.ac.ub.filkom.sekcv.appstroke.model.db.entity.MedicalRecord> getDataByUser(int userID)
    {
        final Cursor cursor = super.database.rawQuery("" +
                        "SELECT * FROM " + DatabaseContract.MedicalRecord.TABLE_NAME + " WHERE " + DatabaseContract.MedicalRecord.TABLE_NAME + "." + DatabaseContract.MedicalRecord.COLUMN_NAME_USER + " = ? ORDER BY datetime(" + DatabaseContract.MedicalRecord.TABLE_NAME + "." + DatabaseContract.MedicalRecord.COLUMN_NAME_TIME + ") DESC",
                new String[] {String.valueOf(userID)});

        List<id.ac.ub.filkom.sekcv.appstroke.model.db.entity.MedicalRecord> records = new LinkedList<>();
        if(cursor.moveToFirst())
        {
            do
            {
                records.add(new id.ac.ub.filkom.sekcv.appstroke.model.db.entity.MedicalRecord(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getDouble(3),
                        cursor.getDouble(4),
                        cursor.getDouble(5),
                        cursor.getDouble(6),
                        cursor.getInt(7),
                        cursor.getString(8)
                ));
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        return records;
    }
}
