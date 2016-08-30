package id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.method.oaa;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke.model.algorithm.svm.method.oaa> created by :
 * Name         : syafiq
 * Date / Time  : 29 August 2016, 10:26 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class OneAgainstAll
{
    final private Parameter[] parameters;
    final private double      bias;
    final private int[]       allowedData;

    public OneAgainstAll(Parameter[] parameters, int[] allowedData, double bias)
    {
        this.parameters = parameters;
        this.allowedData = allowedData;
        this.bias = bias;
    }

    public Parameter[] getParameters()
    {
        return this.parameters;
    }

    public int[] getAllowedData()
    {
        return allowedData;
    }

    public double getBias()
    {
        return this.bias;
    }

    public int doClassify(final double[] kernel)
    {
        final Parameter[] parameter = this.getParameters();
        double            value     = this.bias;
        for(final int dataIndex : this.allowedData)
        {
            value += (parameter[dataIndex].getMultiplier() * parameter[dataIndex].getClazz() * kernel[dataIndex]);
        }
        return (int) Math.signum(value);
    }
}
