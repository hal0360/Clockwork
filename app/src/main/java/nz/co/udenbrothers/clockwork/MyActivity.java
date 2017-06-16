package nz.co.udenbrothers.clockwork;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.gson.Gson;

import nz.co.udenbrothers.clockwork.abstractions.Cmd;
import nz.co.udenbrothers.clockwork.global.Screen;
import nz.co.udenbrothers.clockwork.tools.Pref;


public abstract class MyActivity extends AppCompatActivity implements View.OnClickListener{

    protected Pref pref;
    private SparseArray<Cmd> cmds = new SparseArray<>();
    private Dialog sideMenu;
    protected Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new Pref(this);
        gson = new Gson();

        sideMenu = new Dialog(this, R.style.MyCustomDialog);
        sideMenu.requestWindowFeature(Window.FEATURE_NO_TITLE);

        if(pref.getInt("profileRole") == 1){
            sideMenu.setContentView(R.layout.side_menu_layout);
            clicked(sideMenu.findViewById(R.id.homeButton), ()-> toActivity(StaffHomeActivity.class));
            clicked(sideMenu.findViewById(R.id.profileButton), ()-> toActivity(StaffProfileActivity.class));
            clicked(sideMenu.findViewById(R.id.historyButton), ()-> toActivity(StaffHistoryActivity.class));
            clicked(sideMenu.findViewById(R.id.settingsButton), ()-> toActivity(StaffSettingActivity.class));
        }
        else if(pref.getInt("profileRole") == 2){
            sideMenu.setContentView(R.layout.side_menu_layout_boss);
            clicked(sideMenu.findViewById(R.id.homeButton), ()-> toActivity(BossHomeActivity.class));
            clicked(sideMenu.findViewById(R.id.profileButton), ()-> toActivity(BossProfileActivity.class));
            clicked(sideMenu.findViewById(R.id.myTeamButton), ()-> toActivity(BossMyTeamActivity.class));
            clicked(sideMenu.findViewById(R.id.settingsButton), ()-> toActivity(BossSettingActivity.class));
        }
        else {
            logout();
        }

        clicked(sideMenu.findViewById(R.id.logoutButton), this::logout);
        clicked(sideMenu.findViewById(R.id.exportCSVButton), ()-> {

            hideMenu();
        });
    }

    protected final void logout(){
        pref.putStr("profileName","");
        pref.putInt("profileRole",0);
        toActivity(SplashActivity.class);
    }

    protected final void showMenu(){
        alert("sdsd");
        Rect rectangle = new Rect();
        Window win = getWindow();
        win.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        Window window = sideMenu.getWindow();
        assert window != null;
        window.setLayout((int)(Screen.width * 0.6), (int)((Screen.height - rectangle.top) * 0.925));
        window.setGravity(Gravity.BOTTOM | Gravity.END);
        sideMenu.show();
    }

    protected final void hideMenu(){
        sideMenu.dismiss();
    }

    protected final void toActivity(Class actClass){
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

    protected final void clicked(int id, Cmd cd){
        findViewById(id).setOnClickListener(this);
        cmds.put(id,cd);
    }

    protected final void clicked(View v, Cmd cd){
        v.setOnClickListener(this);
        cmds.put(v.getId(),cd);
    }

    protected final void alert(String mss){
        Toast.makeText(this, mss, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        cmds.get(v.getId()).exec();
    }
}
