package nz.co.udenbrothers.clockwork.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class MyDate {

    public static float hoursDifference(Date date1, Date date2) {
        final float MILLI_TO_HOUR = 1000 * 60 * 60.0f;
        return (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
    }

    public static String gethourMin(long total){
        long secs = total / 1000;
        long hours = secs / 3600;
        secs = secs % 3600;
        long mins = secs / 60;
        return hours + "h " + mins + "m";
    }

    public static String dateToStr(Date date, String format){
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
        df.setTimeZone(TimeZone.getDefault());
        return df.format(date);
    }

    public static Date strToDate(String dateStr){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String dateToStr(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getDefault());
        return df.format(date);
    }
}
