package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;

import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.temps.Screen;
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
        sideMenu.clicked(R.id.logoutButton, ()->{
            logout();
            navigate(SplashActivity.class);
        });
        sideMenu.setDimension(Screen.width*0.6, Screen.height*0.921);
        sideMenu.setGravity(Gravity.BOTTOM | Gravity.END);
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
        if(Profile.role() != 2){
            navigate(SplashActivity.class);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}


