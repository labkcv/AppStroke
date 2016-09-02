package id.ac.ub.filkom.sekcv.appstroke.model.db.entity;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.db.entity> created by :
 * Name         : syafiq
 * Date / Time  : 29 August 2016, 11:39 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class MedicalRecord
{
    private final int    id;
    private final int    user;
    private final int    age;
    private final String time;
    private       double cholesterol;
    private       double hdl;
    private       double ldl;
    private       double triglyceride;
    private       int    status;

    public MedicalRecord(int user, int age, double cholesterol, double hdl, double ldl, double triglyceride, int status, String time)
    {
        this(-1, user, age, cholesterol, hdl, ldl, triglyceride, status, time);
    }

    public MedicalRecord(int id, int user, int age, double cholesterol, double hdl, double ldl, double triglyceride, int status, String time)
    {
        this.id = id;
        this.user = user;
        this.age = age;
        this.time = time;
        this.cholesterol = cholesterol;
        this.hdl = hdl;
        this.ldl = ldl;
        this.triglyceride = triglyceride;
        this.status = status;
    }

    public int getId()
    {
        return id;
    }

    public int getUser()
    {
        return user;
    }

    public int getAge()
    {
        return age;
    }

    public String getTime()
    {
        return time;
    }

    public double getCholesterol()
    {
        return cholesterol;
    }

    public void setCholesterol(double cholesterol)
    {
        this.cholesterol = cholesterol;
    }

    public double getHdl()
    {
        return hdl;
    }

    public void setHdl(double hdl)
    {
        this.hdl = hdl;
    }

    public double getLdl()
    {
        return ldl;
    }

    public void setLdl(double ldl)
    {
        this.ldl = ldl;
    }

    public double getTriglyceride()
    {
        return triglyceride;
    }

    public void setTriglyceride(double triglyceride)
    {
        this.triglyceride = triglyceride;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }
}
