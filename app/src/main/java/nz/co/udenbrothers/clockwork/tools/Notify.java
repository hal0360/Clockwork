package nz.co.udenbrothers.clockwork.tools;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.models.Notice;
import nz.co.udenbrothers.clockwork.temps.Setting;

public class Notify {

    private Context context;
    private NotificationManager mNotifyMgr;
    private String title;

    public Notify(Context context, String title){
        this.context = context;
        this.title = title;
        mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void send(String mss, int id){
        Notice notice = new Notice(title,mss);
        notice.save();
        if(Setting.notification()){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentTitle(title)
                    .setContentText(mss)
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setDefaults(Notification.FLAG_ONLY_ALERT_ONCE);
            Notification notification = builder.build();
            mNotifyMgr.notify(id, notification);
        }
    }
}
