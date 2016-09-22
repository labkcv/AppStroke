package id.ac.ub.filkom.sekcv.appstroke.model.db.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.db.core> created by :
 * Name         : syafiq
 * Date / Time  : 29 August 2016, 8:00 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DatabaseModel
{
    protected final Context        context;
    protected       DatabaseHelper dbHelper;
    protected       SQLiteDatabase database;

    public DatabaseModel(final Context context)
    {
        this.context = context;
        this.dbHelper = DatabaseHelper.getInstance(context);
    }

    public void open() throws SQLException
    {
        if(!this.isDatabaseReady())
        {
            this.database = this.dbHelper.getWritableDatabase();
        }
    }

    public void close()
    {
        this.dbHelper.close();
    }

    public boolean isDatabaseReady()
    {
        return this.database != null && this.database.isOpen();
    }
}
