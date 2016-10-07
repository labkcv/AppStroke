package id.ac.ub.filkom.sekcv.appstroke;

import org.junit.Test;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke> created by :
 * Name         : syafiq
 * Date / Time  : 05 October 2016, 2:17 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class TestDataType
{
    @Test
    public void testHighFloatIncrement()
    {
        for(double i = 24594196, is = i + 10; ++i < is;)
        {
            System.out.println(i);
        }
    }
}
