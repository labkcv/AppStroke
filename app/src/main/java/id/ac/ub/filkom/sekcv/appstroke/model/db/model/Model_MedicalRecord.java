package id.ac.ub.filkom.sekcv.appstroke.model.db.model;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import id.ac.ub.filkom.sekcv.appstroke.model.dataset.StrokeMetadata;
import id.ac.ub.filkom.sekcv.appstroke.model.dataset.StrokeParameter;
import id.ac.ub.filkom.sekcv.appstroke.model.db.core.DatabaseContract.MedicalRecord;
import id.ac.ub.filkom.sekcv.appstroke.model.db.core.DatabaseModel;
import id.ac.ub.filkom.sekcv.appstroke.model.db.entity.Entity_MedicalRecord;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.db.model> created by :
 * Name         : syafiq
 * Date / Time  : 29 August 2016, 8:12 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Model_MedicalRecord extends DatabaseModel
{
    public static final String CLASSNAME = "Model_MedicalRecord";
    public static final String CLASSPATH = "model.db.model";
    public static final String TAG       = CLASSPATH + "." + CLASSNAME;

    public Model_MedicalRecord(final Context context)
    {
        super(context);

        Log.d(Model_MedicalRecord.CLASSNAME, Model_MedicalRecord.TAG + ".constructor");
    }

    public void storeMedicalRecordByUser(int userID, StrokeParameter parameter, StrokeMetadata metadata)
    {
        Log.d(Model_MedicalRecord.CLASSNAME, Model_MedicalRecord.TAG + ".storeMedicalRecordByUser");

        super.database.execSQL("" +
                        "INSERT INTO " + MedicalRecord.TABLE_NAME + " VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)",
                new Object[] {userID, parameter.getAge(), parameter.getCholesterol(), parameter.getHdl(), parameter.getLdl(), parameter.getTriglyceride(), metadata.getStatus()});

    }

    public void storeMedicalRecordDummyByUser(int userID, StrokeParameter parameter, StrokeMetadata metadata, String timestamp)
    {
        Log.d(Model_MedicalRecord.CLASSNAME, Model_MedicalRecord.TAG + ".storeMedicalRecordDummyByUser");

        super.database.execSQL("" +
                        "INSERT INTO " + MedicalRecord.TABLE_NAME + " VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Object[] {userID, parameter.getAge(), parameter.getCholesterol(), parameter.getHdl(), parameter.getLdl(), parameter.getTriglyceride(), metadata.getStatus(), timestamp});

    }

    public void cleanDataByUser(int userID)
    {
        Log.d(Model_MedicalRecord.CLASSNAME, Model_MedicalRecord.TAG + ".cleanDataByUser");

        super.database.execSQL("" +
                        "DELETE FROM " + MedicalRecord.TABLE_NAME + " WHERE " + MedicalRecord.TABLE_NAME + "." + MedicalRecord.COLUMN_NAME_USER + " = ?",
                new Object[] {userID});
    }

    public List<Entity_MedicalRecord> getDataByUser(int userID)
    {
        Log.d(Model_MedicalRecord.CLASSNAME, Model_MedicalRecord.TAG + ".getDataByUser");

        final Cursor cursor = super.database.rawQuery("" +
                        "SELECT * FROM " + MedicalRecord.TABLE_NAME + " WHERE " + MedicalRecord.TABLE_NAME + "." + MedicalRecord.COLUMN_NAME_USER + " = ? ORDER BY datetime(" + MedicalRecord.TABLE_NAME + "." + MedicalRecord.COLUMN_NAME_TIME + ") DESC",
                new String[] {String.valueOf(userID)});

        List<Entity_MedicalRecord> records = this.retrieveData(cursor);
        cursor.close();
        return records;
    }

    public Entity_MedicalRecord getLatestDataByUser(int userID)
    {
        Log.d(Model_MedicalRecord.CLASSNAME, Model_MedicalRecord.TAG + ".getLatestDataByUser");

        final Cursor cursor = super.database.rawQuery("" +
                        "SELECT * FROM " + MedicalRecord.TABLE_NAME + " WHERE " + MedicalRecord.TABLE_NAME + "." + MedicalRecord.COLUMN_NAME_USER + " = ? ORDER BY datetime(" + MedicalRecord.TABLE_NAME + "." + MedicalRecord.COLUMN_NAME_TIME + ") DESC LIMIT 1",
                new String[] {String.valueOf(userID)});

        List<Entity_MedicalRecord> records = this.retrieveData(cursor);
        cursor.close();
        if(records.size() > 0)
        {
            return records.get(0);
        }
        else
        {
            return null;
        }
    }

    private LinkedList<Entity_MedicalRecord> retrieveData(final Cursor cursor)
    {
        Log.d(Model_MedicalRecord.CLASSNAME, Model_MedicalRecord.TAG + ".retrieveData");

        LinkedList<Entity_MedicalRecord> records = new LinkedList<>();
        if(cursor.moveToFirst())
        {
            do
            {
                records.add(new Entity_MedicalRecord(
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
