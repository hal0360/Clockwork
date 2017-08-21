package nz.co.udenbrothers.clockwork;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import nz.co.udenbrothers.clockwork.global.Screen;

public class SplashActivity extends MainActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler();

      //  alert(getNavigationBarHeight()+"");

      //  pref.putInt("profileRole",2);

        if(pref.getInt("profileRole") == 1){
            handler.postDelayed(()->toActivity(StaffHomeActivity.class), 600);
        }
        else if (pref.getInt("profileRole") == 2){
            handler.postDelayed(()->toActivity(BossHomeActivity.class), 600);
        }
        else {
            handler.postDelayed(()->toActivity(SignInActivity.class), 600);
        }

    }

    @Override
    protected void onStart(){
        super.onStart();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Screen.density = metrics.density;

        RelativeLayout mainRe = (RelativeLayout) findViewById(R.id.SplashMainScreen);
        final ViewTreeObserver observer= mainRe.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                Screen.width  = mainRe.getWidth();
                Screen.height = mainRe.getHeight();
                if (Screen.width > 0 && Screen.height  > 0) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        mainRe.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        mainRe.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    //DO SOMETHING WITH NON-ZERO DIMENSIONS
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
