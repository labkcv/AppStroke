package id.ac.ub.filkom.sekcv.appstroke.model.db.model;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.Log;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.LinkedList;
import java.util.List;

import id.ac.ub.filkom.sekcv.appstroke.model.db.core.DatabaseContract.User;
import id.ac.ub.filkom.sekcv.appstroke.model.db.core.DatabaseHelper;
import id.ac.ub.filkom.sekcv.appstroke.model.db.core.DatabaseModel;
import id.ac.ub.filkom.sekcv.appstroke.model.db.entity.Entity_User;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.db.model> created by :
 * Name         : syafiq
 * Date / Time  : 30 August 2016, 12:27 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Model_User extends DatabaseModel
{
    public static final String CLASSNAME = "Model_User";
    public static final String CLASSPATH = "model.db.model";
    public static final String TAG       = CLASSPATH + "." + CLASSNAME;

    public Model_User(final Context context)
    {
        super(context);

        Log.d(Model_User.CLASSNAME, Model_User.TAG + ".constructor");
    }

    public boolean isUserAlreadyExists(final String email)
    {
        Log.d(Model_User.CLASSNAME, Model_User.TAG + ".isUserAlreadyExists");

        return this.getUserByEmail(email) != null;
    }

    @Nullable
    public Entity_User getUserByEmail(final String email)
    {
        Log.d(Model_User.CLASSNAME, Model_User.TAG + ".getUserByEmail");

        final Cursor cursor = super.database.rawQuery("" +
                        "SELECT * FROM " + User.TABLE_NAME + " WHERE " + User.TABLE_NAME + "." + User.COLUMN_NAME_EMAIL + " = ? LIMIT 1",
                new String[] {email});

        List<Entity_User> records = this.retrieveData(cursor);
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

    private List<Entity_User> retrieveData(Cursor cursor)
    {
        Log.d(Model_User.CLASSNAME, Model_User.TAG + ".retrieveData");

        LinkedList<Entity_User> records = new LinkedList<>();
        if(cursor.moveToFirst())
        {
            final DateTimeFormatter formatter = DateTimeFormat.forPattern(DatabaseHelper.DATE_FORMAT);
            do
            {
                records.add(new Entity_User(
                        cursor.getInt(0),
                        LocalDate.parse(cursor.getString(2), formatter),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getString(4)
                ));
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return records;
    }

    public void updateUserAccount(int id, String name, String birthdate, String email, String password)
    {
        Log.d(Model_User.CLASSNAME, Model_User.TAG + ".updateUserAccount");

        super.database.execSQL("" +
                        "UPDATE " + User.TABLE_NAME + " SET " + User.COLUMN_NAME_NAME + " = ?, " + User.COLUMN_NAME_BIRTHDATE + " = ?, " + User.COLUMN_NAME_EMAIL + " = ?, " + User.COLUMN_NAME_PASSWORD + " = ? WHERE " + User.COLUMN_NAME_ID + " = ? ",
                new Object[] {name, birthdate, email, password, id});
    }

    public void registerUser(String name, String birthdate, String email, String password)
    {
        Log.d(Model_User.CLASSNAME, Model_User.TAG + ".registerUser");

        super.database.execSQL("" +
                        "INSERT INTO " + User.TABLE_NAME + " VALUES (NULL, ?, ?, ?, ?)",
                new Object[] {name, birthdate, email, password});
    }
}
