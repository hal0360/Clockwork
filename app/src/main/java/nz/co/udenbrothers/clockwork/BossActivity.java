package nz.co.udenbrothers.clockwork;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;

import nz.co.udenbrothers.clockwork.global.Screen;
import nz.co.udenbrothers.clockwork.tools.Popup;

public abstract class BossActivity extends MainActivity{

    protected Popup sideMenu;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();

        sideMenu = new Popup(this, R.layout.side_menu_layout_boss, R.style.MyMenuDialog);
        sideMenu.clicked(R.id.homeButton, ()-> navigate(BossHomeActivity.class));
        sideMenu.clicked(R.id.profileButton, ()-> navigate(BossProfileActivity.class));
        sideMenu.clicked(R.id.myTeamButton, ()-> navigate(BossMyTeamActivity.class));
        sideMenu.clicked(R.id.settingsButton, ()-> navigate(BossSettingActivity.class));
        sideMenu.clicked(R.id.exportCSVButton, ()-> pushActivity(BossExportActivity.class));
        sideMenu.clicked(R.id.logoutButton, this::logout);
        sideMenu.setDimension(Screen.width*0.6, Screen.height*0.921);
        sideMenu.setGravity(Gravity.BOTTOM | Gravity.END);
    }

    protected final void logout(){
        pref.putStr("profileName","");
        pref.putInt("profileRole",0);
        navigate(SplashActivity.class);
    }

    public void navigate(Class actClass){
        if (sideMenu.isShowing()){
            sideMenu.dismiss();
            handler.postDelayed(()->toActivity(actClass), 300);
        }
        else toActivity(actClass);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(pref.getInt("profileRole") != 2){
            navigate(SplashActivity.class);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}


