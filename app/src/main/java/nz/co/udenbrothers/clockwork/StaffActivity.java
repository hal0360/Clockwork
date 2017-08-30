package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;

import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.temps.Screen;
import nz.co.udenbrothers.clockwork.tools.Popup;



public abstract class StaffActivity extends MainActivity{

    protected Popup sideMenu;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();

        sideMenu = new Popup(this, R.layout.side_menu_layout, R.style.MyMenuDialog);
        sideMenu.clicked(R.id.homeButton, ()-> navigate(StaffHomeActivity.class));
        sideMenu.clicked(R.id.profileButton, ()-> navigate(StaffProfileActivity.class));
        sideMenu.clicked(R.id.historyButton, ()-> navigate(StaffHistoryActivity.class));
        sideMenu.clicked(R.id.settingsButton, ()-> navigate(StaffSettingActivity.class));
        sideMenu.clicked(R.id.exportCSVButton, ()-> pushActivity(StaffExportActivity.class));
        sideMenu.clicked(R.id.noAdsButton, ()-> pushActivity(UpgradeActivity.class));
        sideMenu.clicked(R.id.logoutButton, ()->{
           logout();
            navigate(SplashActivity.class);
        });
        sideMenu.setDimension(Screen.width*0.6, Screen.height*0.921);
        sideMenu.setGravity(Gravity.BOTTOM | Gravity.END);
    }


    @Override
    protected void onResume(){
        super.onResume();
        if(Profile.role() != 1){
            navigate(SplashActivity.class);
        }
    }

    public void navigate(Class actClass){
        if (sideMenu.isShowing()){
            sideMenu.dismiss();
            handler.postDelayed(()->toActivity(actClass), 300);
        }
        else toActivity(actClass);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
