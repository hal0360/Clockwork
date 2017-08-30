package nz.co.udenbrothers.clockwork.serverices;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.temps.Act;
import nz.co.udenbrothers.clockwork.tools.MyDate;

public class WedgeService extends Service {

    private double dense;
    private WindowManager windowManager;
    private RelativeLayout wedgeView;
    private Handler handler;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int dps(int dp){
        return (int)((dp * dense) + 0.5);
    }

    @Override public void onCreate() {
        super.onCreate();

        WindowManager.LayoutParams params;
        handler = new Handler();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        dense = metrics.density;
        int winType;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
             winType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
        else {
            winType = WindowManager.LayoutParams.TYPE_PHONE;
        }

        params = new WindowManager.LayoutParams(
                metrics.widthPixels * 4 / 5,
                dps(135),
                winType,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.CENTER;

        params.windowAnimations = android.R.style.Animation_Dialog;

        LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        wedgeView = (RelativeLayout) li.inflate(R.layout.wedge_layout, null);
        windowManager.addView(wedgeView, params);

        TextView started =   wedgeView.findViewById(R.id.startedHour);
        TextView worked = wedgeView.findViewById(R.id.workedHour);
        TextView title =  wedgeView.findViewById(R.id.cardTitle);

        title.setText("Working now: " + Act.current());
        Date startDate = MyDate.strToDate(Act.startTime());
        started.setText(MyDate.dateToStr(startDate,"HH:mm"));

        Date curDate = new Date();
        long diff = curDate.getTime() - startDate.getTime();
        long days = diff / (24 * 60 * 60 * 1000);
        diff -= days * (24 * 60 * 60 * 1000);
        long hours = diff / (60 * 60 * 1000);
        diff -= hours * (60 * 60 * 1000);
        long minutes = diff / (60 * 1000);
        worked.setText(String.format(Locale.ENGLISH,"%02d", hours) + ":" + String.format(Locale.ENGLISH,"%02d", minutes));

        wedgeView.setOnClickListener(v -> stopSelf());

        handler.postDelayed(this::stopSelf, 4000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (wedgeView.isShown()){
            windowManager.removeView(wedgeView);
        }
        handler.removeCallbacksAndMessages(null);
    }
}
