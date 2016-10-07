package id.ac.ub.filkom.sekcv.appstroke.model.dataset;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.dataset> created by :
 * Name         : syafiq
 * Date / Time  : 29 August 2016, 10:05 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Parameter
{
    private String key;
    private String name;
    private int    color;

    public Parameter(final String key, final String name, final int color)
    {
        this.key = key;
        this.name = name;
        this.color = color;
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

    public int getColor()
    {
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    @Override
    public String toString()
    {
        return "Parameter{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
