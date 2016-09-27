package id.ac.ub.filkom.sekcv.appstroke;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke> created by :
 * Name         : syafiq
 * Date / Time  : 27 September 2016, 9:31 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class TestJodaTime
{
    @Test
    public void testYearsBetween()
    {
        final String DATE_FORMAT      = "yyyy-MM-dd";
        final String TIME_FORMAT      = "HH:mm:ss";
        final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

        final DateTimeFormatter dateFormatter      = DateTimeFormat.forPattern(DATE_FORMAT);
        final DateTimeFormatter timeFormatter      = DateTimeFormat.forPattern(TIME_FORMAT);
        final DateTimeFormatter timestampFormatter = DateTimeFormat.forPattern(TIMESTAMP_FORMAT);

        //final LocalDateTime birth = LocalDate.parse("2015-09-27", dateFormatter).toLocalDateTime(LocalTime.MIDNIGHT);
        final LocalDateTime birth = LocalDate.parse("2015-09-27", dateFormatter).toLocalDateTime(LocalTime.MIDNIGHT);
        final LocalDateTime now   = LocalDateTime.now();
        System.out.println("birth               = " + birth.toString(TIMESTAMP_FORMAT));
        System.out.println("now                 = " + now.toString(TIMESTAMP_FORMAT));
        int years = Years.yearsBetween(birth, now).getYears();
        System.out.println("ageNow              = " + years);
        final LocalDateTime birthXyear = birth.plusYears(years);
        System.out.println("birthXYear          = " + birthXyear.toString(TIMESTAMP_FORMAT));
        final int differentInSeconds = Seconds.secondsBetween(birthXyear, now).getSeconds();
        System.out.println("differentInSeconds  = " + differentInSeconds);
        int age = years;
        if(differentInSeconds > 0)
        {
            ++age;
        }
        System.out.println("ageNow              = " + age);
    }
}
