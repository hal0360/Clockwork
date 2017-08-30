package nz.co.udenbrothers.clockwork.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import java.util.Calendar;

import nz.co.udenbrothers.clockwork.App;
import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.temps.Noti;
import nz.co.udenbrothers.clockwork.tools.MyDate;
import nz.co.udenbrothers.clockwork.tools.Notify;

public class ClockInReceiver extends BroadcastReceiver {
    public ClockInReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(!App.getBool(MyDate.weekDay(),true)) return;
        MediaPlayer.create(context, R.raw.my_sound).start();
        new Notify(context, "Reminder").send("Don't forget to clock in", 7234);
    }

    public static void starting(Context context)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Noti.CIhour());
        calendar.set(Calendar.MINUTE, Noti.CImin());
        if(System.currentTimeMillis() >= calendar.getTimeInMillis()){
            calendar.add(Calendar.DATE, 1);
        }
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent("clockWorkClockIn");
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(pi);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pi);
    }

    public static void cancelling(Context context)
    {
        Intent intent = new Intent("clockWorkClockIn");
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        sender.cancel();
    }
}
