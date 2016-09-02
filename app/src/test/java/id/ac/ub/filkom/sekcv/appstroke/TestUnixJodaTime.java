package id.ac.ub.filkom.sekcv.appstroke;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke> created by :
 * Name         : syafiq
 * Date / Time  : 31 August 2016, 12:09 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class TestUnixJodaTime
{
    @Test
    public void testUnixJodaTime()
    {
        Calendar calendar = new GregorianCalendar(2013, 0, 31);
        System.out.println((new SimpleDateFormat("yyyy-MM-dd")).format(calendar.getTime()));
    }
}
