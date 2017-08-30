package nz.co.udenbrothers.clockwork.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import nz.co.udenbrothers.clockwork.temps.Setting;

public class CheckReceiver extends BroadcastReceiver {
    public CheckReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(!Setting.reminder()) return;

        boolean alarmUpO = (PendingIntent.getBroadcast(context, 0, new Intent("clockWorkClockOut"), PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmUpO)
        {
            ClockOutReceiver.starting(context);
        }
        boolean alarmUpI = (PendingIntent.getBroadcast(context, 0, new Intent("clockWorkClockIn"), PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmUpI)
        {
            ClockInReceiver.starting(context);
        }
    }
}
