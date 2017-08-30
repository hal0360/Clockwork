package nz.co.udenbrothers.clockwork.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static String convert(String s)  {
        Date datek;
        try {
            datek = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH).parse(s);
        } catch (ParseException e) {
            return null;
        }
        return dateToStr(datek);
    }

    public static String dateToStr(Date date, String format){
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
        df.setTimeZone(TimeZone.getDefault());
        return df.format(date);
    }

    public static Date strToDate(String dateStr) {
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

    public static String weekDay(){
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String weekDay;

        if (Calendar.MONDAY == dayOfWeek) {
            weekDay = "monday";
        } else if (Calendar.TUESDAY == dayOfWeek) {
            weekDay = "tuesday";
        } else if (Calendar.WEDNESDAY == dayOfWeek ) {
            weekDay = "wednesday";
        } else if (Calendar.THURSDAY == dayOfWeek) {
            weekDay = "thursday";
        } else if (Calendar.FRIDAY == dayOfWeek) {
            weekDay = "friday";
        } else if (Calendar.SATURDAY == dayOfWeek) {
            weekDay = "saturday";
        } else  {
            weekDay = "sunday";
        }

        return weekDay;
    }
}
