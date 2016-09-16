package id.ac.ub.filkom.sekcv.appstroke;

import android.annotation.TargetApi;
import android.os.Build;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

import id.ac.ub.filkom.sekcv.appstroke.model.db.core.DatabaseHelper;

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

    @Test
    public void testDate()
    {
        DateTimeFormatter df      = DateTimeFormat.forPattern("dd MM yyyy HH:mm:ss.SSS Z");
        DateTime          temp    = df.withOffsetParsed().parseDateTime("30 11 2012 12:08:56.235 +0700");
        DateTimeZone      theZone = temp.getZone();

        Date date = temp.toDate();

        DateTime          dateTime = new DateTime(date);
        DateTimeFormatter df2      = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSZZ");
        DateTimeFormatter df3      = df2.withZone(theZone);

        System.out.println(dateTime.toString(df2));
        System.out.println(dateTime.toString(df3));
        System.out.println(dateTime.toString("HH:mm:ss"));

    }

    @Test
    public void getAvailableTimeZone()
    {
        Set<String>       zoneIds           = DateTimeZone.getAvailableIDs();
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("ZZ");

        for(String zoneId : zoneIds)
        {
            String offset   = dateTimeFormatter.withZone(DateTimeZone.forID(zoneId)).print(0);
            String longName = TimeZone.getTimeZone(zoneId).getDisplayName();

            System.out.println("(" + offset + ") " + zoneId + ", " + longName);
        }
    }

    @Test
    public void consistencyTimeFormat()
    {
        DateTime d1 = new DateTime(DateTimeZone.forID("Asia/Jakarta"));
        System.out.println(d1.toString());


        //Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onCreate : TimeStamp : " + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime("2016-09-16 10:30:51").withZone(DateTimeZone.getDefault()).toString(DatabaseHelper.TIMESTAMP_FORMAT));
        //Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onCreate : TimeStamp : " + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ssZZ").parseDateTime("2016-09-16 10:30:51+00:00").withZone(DateTimeZone.getDefault()).toString(DatabaseHelper.TIMESTAMP_FORMAT));
        //Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onCreate : TimeStamp : " + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseLocalDateTime("2016-09-16 10:30:51").toDateTime(DateTimeZone.UTC).toString(DatabaseHelper.TIMESTAMP_FORMAT));
        System.out.println(".onCreate : TimeStamp : " + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseLocalDateTime("2016-09-16 10:30:51").toDateTime(DateTimeZone.UTC).withZone(DateTimeZone.getDefault()).toString(DatabaseHelper.TIMESTAMP_FORMAT));
        System.out.println(".onCreate : TimeStamp : " + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.UTC).parseLocalDateTime("2016-09-16 10:30:51").toDateTime(DateTimeZone.UTC).withZone(DateTimeZone.getDefault()).toString(DatabaseHelper.TIMESTAMP_FORMAT));
        System.out.println(".onCreate : TimeStamp : " + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.UTC).parseDateTime("2016-09-16 10:30:51").toDateTime(DateTimeZone.UTC).withZone(DateTimeZone.getDefault()).toString(DatabaseHelper.TIMESTAMP_FORMAT));
        System.out.println(".onCreate : TimeStamp : " + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.UTC).parseDateTime("2016-09-16 10:30:51").withZone(DateTimeZone.getDefault()).toString(DatabaseHelper.TIMESTAMP_FORMAT));
        System.out.println(".onCreate : TimeStamp : " + DateTime.parse("2016-09-16 10:30:51+00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ssZZ").withZone(DateTimeZone.getDefault())).toString(DatabaseHelper.TIMESTAMP_FORMAT));

        //Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onCreate : TimeStamp : " + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.UTC).parseLocalDateTime("2016-09-16 10:30:51").toDateTime(DateTimeZone.getDefault()).withZone(DateTimeZone.getDefault()).toString(DatabaseHelper.TIMESTAMP_FORMAT));
        //Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onCreate : TimeStamp : " + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.UTC).parseLocalDateTime("2016-09-16 10:30:51").toDateTime(DateTimeZone.getDefault()).toString(DatabaseHelper.TIMESTAMP_FORMAT));
        //Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onCreate : TimeStamp : " + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ssZZ").parseLocalDateTime("2016-09-16 10:30:51+07:00").toDateTime(DateTimeZone.UTC).toString(DatabaseHelper.TIMESTAMP_FORMAT));
        //Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onCreate : TimeStamp : " + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ssZZ").parseLocalDateTime("2016-09-16 10:30:51+07:00").toDateTime(DateTimeZone.getDefault()).toString(DatabaseHelper.TIMESTAMP_FORMAT));
        //Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onCreate : TimeStamp : " + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ssZZ").parseLocalDateTime("2016-09-16 10:30:51+07:00").toDateTime(DateTimeZone.UTC).withZone(DateTimeZone.getDefault()).toString(DatabaseHelper.TIMESTAMP_FORMAT));
        //Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onCreate : TimeStamp : " + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ssZZ").parseDateTime("2016-09-16 10:30:51+07:00").withZone(DateTimeZone.getDefault()).toString(DatabaseHelper.TIMESTAMP_FORMAT));
        //Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onCreate : TimeStamp : " + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ssZZ").parseLocalDateTime("2016-09-16 10:30:51+07:00").toDateTime().withZone(DateTimeZone.getDefault()).toString(DatabaseHelper.TIMESTAMP_FORMAT));
        //Log.d(MainPage.CLASSNAME, MainPage.TAG + ".onCreate : TimeStamp : " + DateTime.parse("2016-09-16 10:30:51", DateTimeFormat.forPattern(DatabaseHelper.TIMESTAMP_FORMAT)).withZone(DateTimeZone.getDefault()).toString(DatabaseHelper.TIMESTAMP_FORMAT));



    }
}
