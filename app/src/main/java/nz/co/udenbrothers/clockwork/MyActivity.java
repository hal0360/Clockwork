package nz.co.udenbrothers.clockwork;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nz.co.udenbrothers.clockwork.abstractions.Cmd;
import nz.co.udenbrothers.clockwork.dao.StampDAO;
import nz.co.udenbrothers.clockwork.global.Screen;
import nz.co.udenbrothers.clockwork.models.Stamp;
import nz.co.udenbrothers.clockwork.tools.Pref;


public abstract class MyActivity extends AppCompatActivity implements View.OnClickListener{

    protected Pref pref;
    private SparseArray<Cmd> cmds = new SparseArray<>();
    private Dialog sideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new Pref(this);

        sideMenu = new Dialog(this, R.style.MyCustomDialog);
        sideMenu.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sideMenu.setContentView(R.layout.side_menu_layout);

        clicked(sideMenu.findViewById(R.id.homeButton), ()-> toActivity(StaffHomeActivity.class));
        clicked(sideMenu.findViewById(R.id.profileButton), ()-> toActivity(StaffProfileActivity.class));
        clicked(sideMenu.findViewById(R.id.historyButton), ()-> toActivity(StaffHistoryActivity.class));
        clicked(sideMenu.findViewById(R.id.settingsButton), ()-> toActivity(StaffSettingActivity.class));
        clicked(sideMenu.findViewById(R.id.logoutButton), ()-> {
            pref.putStr("profileName","");
            toActivity(SplashActivity.class);
        });
        clicked(sideMenu.findViewById(R.id.exportCSVButton), ()-> {
            CSVWriter writer = null;
            StampDAO stampDAO = new StampDAO(this);
            ArrayList<Stamp> stamps = stampDAO.getAll();
            try {
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(path, "Cloclwork.csv");
                file.delete();
                writer = new CSVWriter(new FileWriter(new File(path, "Cloclwork.csv"), true), ',');
                List<String[]> data = new ArrayList<>();
                for(Stamp stamp: stamps){
                    data.add(new String[] {stamp.site_name, stamp.staff_name, stamp.startTime, stamp.endTime, stamp.comment});
                }
                writer.writeAll(data);
                writer.close();

                File filelocation = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Cloclwork.csv");
                Uri pathh = Uri.fromFile(filelocation);
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("vnd.android.cursor.dir/email");
                emailIntent.putExtra(Intent.EXTRA_STREAM, pathh);
                startActivity(Intent.createChooser(emailIntent , "EXPORTING CSV FILE..."));

            } catch (IOException e) {
                alert("Error! Cannot create CSV file");
            }
            hideMenu();
        });
    }

    protected final void showMenu(){
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
