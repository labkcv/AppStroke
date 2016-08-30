package id.ac.ub.filkom.sekcv.appstroke.model.db.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import id.ac.ub.filkom.sekcv.appstroke.model.db.core.DatabaseContract.MedicalRecord;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.db.core> created by :
 * Name         : syafiq
 * Date / Time  : 29 August 2016, 7:18 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    // If you change the database schema, you must increment the database version.
    public static final int    DATABASE_VERSION = 1;
    public static final String DATABASE_NAME    = "641202b9031ae1995e177280f689621989f7b03d.mcrypt";

    public static final String DATE_FORMAT = "YYYY-MM-DD";

    private static final char COMMA_SEPARATOR = ',';
    private static final char WHITESPACE      = ' ';

    private static final String TYPE_TEXT     = "TEXT";
    private static final String TYPE_REAL     = "REAL";
    private static final String TYPE_INTEGER  = "INTEGER";
    private static final String TYPE_DATETIME = "DATETIME";

    private static final String CONSTRAINT_NOT_NULL          = "NOT NULL";
    private static final String CONSTRAINT_CURRENT_TIMESTAMP = "DEFAULT CURRENT_TIMESTAMP";
    private static final String CONSTRAINT_PRIMARY_KEY       = "PRIMARY KEY";

    private static final String SQL_CREATE_MEDICAL_RECORD_ENTRIES = "" +
            "CREATE TABLE IF NOT EXISTS" + WHITESPACE + MedicalRecord.TABLE_NAME + WHITESPACE +
            "( " +
            MedicalRecord.COLUMN_NAME_ID + WHITESPACE + TYPE_INTEGER + WHITESPACE + CONSTRAINT_NOT_NULL + WHITESPACE + CONSTRAINT_PRIMARY_KEY + COMMA_SEPARATOR + WHITESPACE +
            MedicalRecord.COLUMN_NAME_USER + WHITESPACE + TYPE_INTEGER + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            MedicalRecord.COLUMN_NAME_AGE + WHITESPACE + TYPE_INTEGER + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            MedicalRecord.COLUMN_NAME_CHOLESTEROL + WHITESPACE + TYPE_REAL + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            MedicalRecord.COLUMN_NAME_HDL + WHITESPACE + TYPE_REAL + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            MedicalRecord.COLUMN_NAME_LDL + WHITESPACE + TYPE_REAL + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            MedicalRecord.COLUMN_NAME_TRIGLYCERIDE + WHITESPACE + TYPE_REAL + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            MedicalRecord.COLUMN_NAME_STATUS + WHITESPACE + TYPE_INTEGER + WHITESPACE + CONSTRAINT_NOT_NULL + COMMA_SEPARATOR + WHITESPACE +
            MedicalRecord.COLUMN_NAME_TIME + WHITESPACE + TYPE_TEXT + WHITESPACE + CONSTRAINT_CURRENT_TIMESTAMP + WHITESPACE +
            " );";

    private static final String SQL_DROP_MEDICAL_RECORD_ENTRIES = "" +
            "DROP TABLE IF EXISTS " + MedicalRecord.TABLE_NAME;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("DatabaseHelper", "model.db.core.DatabaseHelper.constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(SQL_CREATE_MEDICAL_RECORD_ENTRIES);
        Log.i("DatabaseHelper", "model.db.core.DatabaseHelper.onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        sqLiteDatabase.execSQL(SQL_DROP_MEDICAL_RECORD_ENTRIES);
        onCreate(sqLiteDatabase);
        Log.i("DatabaseHelper", "model.db.core.DatabaseHelper.onUpgrade");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        super.onDowngrade(db, oldVersion, newVersion);
        Log.i("DatabaseHelper", "model.db.core.DatabaseHelper.onDowngrade");
    }
}
