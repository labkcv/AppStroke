package id.ac.ub.filkom.sekcv.appstroke;

import android.annotation.TargetApi;
import android.os.Build;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This <AppStroke> project in package <id.ac.ub.filkom.sekcv.appstroke> created by :
 * Name         : syafiq
 * Date / Time  : 30 August 2016, 12:40 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class TestLocalDateTime
{
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Test
    public void testLocalDateTime()
    {
        final Random random = ThreadLocalRandom.current();
        for(int i = 201, is = 0; --i > is; )
        {
            LocalDateTime dummyTime = new LocalDateTime().minusDays(i);
            if(random.nextDouble() < .5)
            {
                //plus
                dummyTime = dummyTime.plusHours(random.nextInt(24 - dummyTime.getHourOfDay()));
            }
            else
            {
                //minus
                dummyTime = dummyTime.minusHours(dummyTime.getHourOfDay() - random.nextInt(dummyTime.getHourOfDay()));
            }
            if(random.nextDouble() < .5)
            {
                //plus
                dummyTime = dummyTime.plusMinutes(random.nextInt(60 - dummyTime.getMinuteOfHour()));
            }
            else
            {
                //minus
                dummyTime = dummyTime.minusMinutes(dummyTime.getMinuteOfHour() - random.nextInt(dummyTime.getMinuteOfHour()));
            }
            if(random.nextDouble() < .5)
            {
                //plus
                dummyTime = dummyTime.plusSeconds(random.nextInt(60 - dummyTime.getSecondOfMinute()));
            }
            else
            {
                //minus
                dummyTime = dummyTime.minusSeconds(dummyTime.getSecondOfMinute() - random.nextInt(dummyTime.getSecondOfMinute()));
            }
            System.out.println(dummyTime.toString("dd-MM-yyyy HH:mm:ss"));
        }
    }
}
