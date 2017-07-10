package nz.co.udenbrothers.clockwork;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class SignActivity extends MainActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("Reminder")
                .setContentText("Don't forget to clock in")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.app_icon)
                .setDefaults(Notification.FLAG_ONLY_ALERT_ONCE);
        Notification notification = builder.build();
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notification.sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.my_sound);
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        mNotifyMgr.notify(7234, notification);



        clicked(R.id.signUpButton,()-> toActivity(SignUpActivity.class));
        clicked(R.id.signInButton,()-> toActivity(SignInActivity.class));
        clicked(R.id.signInTxt,()->toActivity(SignInActivity.class));
    }
}
