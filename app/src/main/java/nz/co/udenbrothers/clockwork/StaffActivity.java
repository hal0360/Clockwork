package nz.co.udenbrothers.clockwork;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;

import nz.co.udenbrothers.clockwork.global.Screen;


public abstract class StaffActivity extends MainActivity{

    protected Dialog sideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sideMenu = new Dialog(this, R.style.MyMenuDialog);
        sideMenu.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sideMenu.setContentView(R.layout.side_menu_layout);
        clicked(sideMenu.findViewById(R.id.homeButton), ()-> navigate(StaffHomeActivity.class));
        clicked(sideMenu.findViewById(R.id.profileButton), ()-> navigate(StaffProfileActivity.class));
        clicked(sideMenu.findViewById(R.id.historyButton), ()-> navigate(StaffHistoryActivity.class));
        clicked(sideMenu.findViewById(R.id.settingsButton), ()-> navigate(StaffSettingActivity.class));
        clicked(sideMenu.findViewById(R.id.exportCSVButton), ()-> pushActivity(StaffExportActivity.class));
        clicked(sideMenu.findViewById(R.id.noAdsButton), ()-> pushActivity(UpgradeActivity.class));
        clicked(sideMenu.findViewById(R.id.logoutButton), this::logout);
    }

    protected final void logout(){
        pref.putStr("profileName","");
        pref.putInt("profileRole",0);
        navigate(SplashActivity.class);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(pref.getInt("profileRole") != 1){
            navigate(SplashActivity.class);
        }
    }

    public void navigate(Class actClass){
        Intent intent = new Intent(this, actClass);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        }
        startActivity(intent);
        if (sideMenu != null && sideMenu.isShowing()) {
            sideMenu.dismiss();
        }
        this.finish();
    }

    protected final void showMenu(){
        if (sideMenu != null && sideMenu.isShowing()) {
            sideMenu.dismiss();
            return;
        }

        Rect rectangle = new Rect();
        Window win = getWindow();
        win.getDecorView().getWindowVisibleDisplayFrame(rectangle);


        Window window = sideMenu.getWindow();
        assert window != null;
        window.setLayout((int)(Screen.width * 0.6), (int)((Screen.height) * 0.921));
        window.setGravity(Gravity.BOTTOM | Gravity.END);
        sideMenu.show();
    }

    protected final void hideMenu(){
        sideMenu.dismiss();
    }
}
