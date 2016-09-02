package id.ac.ub.filkom.sekcv.appstroke.model.db.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import id.ac.ub.filkom.sekcv.appstroke.model.db.core.DatabaseModel;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.db.model> created by :
 * Name         : syafiq
 * Date / Time  : 30 August 2016, 12:27 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class User extends DatabaseModel
{

    public User(final Context context)
    {
        super(context);
    }

    public id.ac.ub.filkom.sekcv.appstroke.model.db.entity.User getUserByID(int userID)
    {
        Context con;
        try
        {
            con = super.context.createPackageContext("com.labkcv.selabkc", 0);
            SharedPreferences pref = con.getSharedPreferences("CekLogin", Context.MODE_PRIVATE);
            Log.d("PREF DATA", pref.getString("date", "date"));
            Log.d("PREF DATA", pref.getString("name", "name"));
            Log.d("PREF DATA", pref.getString("password", "password"));
            Log.d("PREF DATA", pref.getString("email", "email"));
            return new id.ac.ub.filkom.sekcv.appstroke.model.db.entity.User(1, LocalDate.parse(pref.getString("date", "1993-12-16"), DateTimeFormat.forPattern("yyyy-MM-dd")), pref.getString("name", "Muhammad Syafiq"), pref.getString("email", "syafiq.rezpector@gmail.com"), pref.getString("password", "473bb7b11dd3c3a67a446f7743b4d3af"), true);

        }
        catch(PackageManager.NameNotFoundException e)
        {
            Log.e("Not data shared", e.toString());
            return new id.ac.ub.filkom.sekcv.appstroke.model.db.entity.User(1, LocalDate.parse("1993-12-16"), "Muhammad Syafiq", "syafiq.rezpector@gmail.com", "473bb7b11dd3c3a67a446f7743b4d3af", true);
        }
    }
}
