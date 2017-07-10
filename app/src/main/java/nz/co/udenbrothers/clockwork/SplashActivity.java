package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import nz.co.udenbrothers.clockwork.global.Screen;

public class SplashActivity extends MainActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Screen.density = metrics.density;
        Screen.height = metrics.heightPixels;
        Screen.width = metrics.widthPixels;

        handler = new Handler();
        if(pref.getInt("profileRole") == 1){
            handler.postDelayed(()->toActivity(StaffHomeActivity.class), 600);
        }
        else if (pref.getInt("profileRole") == 2){
            handler.postDelayed(()->toActivity(BossHomeActivity.class), 600);
        }
        else {
            handler.postDelayed(()->toActivity(SignActivity.class), 600);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
