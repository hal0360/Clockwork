package nz.co.udenbrothers.clockwork.Reciever;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.dao.NoticeDAO;
import nz.co.udenbrothers.clockwork.models.Notice;
import nz.co.udenbrothers.clockwork.tools.Pref;

public class ClockOutReceiver extends BroadcastReceiver {
    public ClockOutReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("Reminder")
                .setContentText("Don't forget to clock out")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.app_icon)
                .setDefaults(Notification.FLAG_ONLY_ALERT_ONCE);
        Notification notification = builder.build();
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.my_sound);
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        mNotifyMgr.notify(4327, notification);

        NoticeDAO noticeDAO = new NoticeDAO(context);
        noticeDAO.add(new Notice("Reminder","Don't forget to clock out"));
    }

    public void starting(Context context)
    {
        Pref pref = new Pref(context);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, pref.getInt("COhour"));
        calendar.set(Calendar.MINUTE, pref.getInt("COmin"));
        if(System.currentTimeMillis() >= calendar.getTimeInMillis()){
            calendar.add(Calendar.DATE, 1);
        }
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent("clockWorkClockOut");
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(pi);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pi);
    }

    public void cancelling(Context context)
    {
        Intent intent = new Intent("clockWorkClockOut");
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        sender.cancel();
    }
}
