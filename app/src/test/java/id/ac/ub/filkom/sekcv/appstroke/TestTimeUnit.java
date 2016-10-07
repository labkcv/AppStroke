package id.ac.ub.filkom.sekcv.appstroke;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke> created by :
 * Name         : syafiq
 * Date / Time  : 05 October 2016, 12:23 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

public class TestTimeUnit
{
    @Test
    public void testMilisecondHour()
    {
        long timeMilis = System.currentTimeMillis();
        System.out.println("timeMilis = " + timeMilis);
        System.out.println(TimeUnit.MILLISECONDS.toDays(timeMilis));
    }
}
