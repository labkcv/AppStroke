package id.ac.ub.filkom.sekcv.appstroke.model.db.model;

import android.content.Context;
import android.util.Log;

import id.ac.ub.filkom.sekcv.appstroke.model.db.core.DatabaseModel;

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
}
