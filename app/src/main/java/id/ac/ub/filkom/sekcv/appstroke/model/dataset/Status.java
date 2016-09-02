package id.ac.ub.filkom.sekcv.appstroke.model.dataset;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.dataset> created by :
 * Name         : syafiq
 * Date / Time  : 29 August 2016, 10:04 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Status
{
    private String key;
    private String name;

    public Status(final String key, final String name)
    {
        this.key = key;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(final String key)
    {
        this.key = key;
    }

    @Override
    public String toString()
    {
        return "Status{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

