package id.ac.ub.filkom.sekcv.appstroke.model.db.core;

import android.provider.BaseColumns;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.db.core> created by :
 * Name         : syafiq
 * Date / Time  : 29 August 2016, 7:04 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public final class DatabaseContract
{
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DatabaseContract()
    {
    }

    /* Inner class that defines the table contents */
    public static class MedicalRecord implements BaseColumns
    {
        public static final String TABLE_NAME               = "medical_record";
        public static final String COLUMN_NAME_ID           = "id";
        public static final String COLUMN_NAME_USER         = "user";
        public static final String COLUMN_NAME_AGE          = "age";
        public static final String COLUMN_NAME_CHOLESTEROL  = "cholesterol";
        public static final String COLUMN_NAME_HDL          = "hdl";
        public static final String COLUMN_NAME_LDL          = "ldl";
        public static final String COLUMN_NAME_TRIGLYCERIDE = "triglyceride";
        public static final String COLUMN_NAME_STATUS       = "status";
        public static final String COLUMN_NAME_TIME         = "time";
    }
}
