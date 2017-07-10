package nz.co.udenbrothers.clockwork.Reciever;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CheckReceiver extends BroadcastReceiver {
    public CheckReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean alarmUpO = (PendingIntent.getBroadcast(context, 0, new Intent("clockWorkClockOut"), PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmUpO)
        {
            ClockOutReceiver clockOutReceiver = new ClockOutReceiver();
            clockOutReceiver.starting(context);
        }
        boolean alarmUpI = (PendingIntent.getBroadcast(context, 0, new Intent("clockWorkClockIn"), PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmUpI)
        {
            ClockInReceiver clockInReceiver = new ClockInReceiver();
            clockInReceiver.starting(context);
        }
    }
}
