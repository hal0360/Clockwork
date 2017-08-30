package nz.co.udenbrothers.clockwork;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import nz.co.udenbrothers.clockwork.abstractions.Cmd;
import nz.co.udenbrothers.clockwork.sql_stuff.SqlAccess;
import nz.co.udenbrothers.clockwork.temps.Profile;

public abstract class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SparseArray<Cmd> cmds = new SparseArray<>();
    private float startingY;
    private InputMethodManager imm;

    /*
    public static boolean active = false;

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    protected final void overlayPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (!Settings.canDrawOverlays(this)) {
            alert("Hey! Please enable draw over other app.");
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }
    }

    protected final void toActivity(Class actClass){
        Intent intent = new Intent(this, actClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
        this.finish();
    }

    protected final void logout(){
        Profile.logout();
        SqlAccess sql = SqlAccess.getInstance();
        sql.dropAll();
    }

    protected final void toActivity(Class actClass, String val){
        Intent intent = new Intent(this, actClass);
        intent.putExtra("selected", val);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
        this.finish();
    }

    protected final void pushActivity(Class actClass){
        Intent intent = new Intent(this, actClass);
        startActivity(intent);
    }

    protected final void clicked(int id, Cmd cd){
        findViewById(id).setOnClickListener(this);
        cmds.put(id, cd);
    }

    protected final void clicked(View v, Cmd cd){
        v.setOnClickListener(this);
        cmds.put(v.getId(), cd);
    }

    protected final void alert(String mss){
        Toast.makeText(this, mss, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View viewCurrent = getCurrentFocus();
        if(viewCurrent == null) return super.dispatchTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                float deltaa = startingY - event.getRawY();
                if (deltaa > 100)
                {
                    TextView nextField = (TextView)viewCurrent.focusSearch(View.FOCUS_DOWN);
                    if(nextField != null) nextField.requestFocus();
                    else {
                        viewCurrent.clearFocus();
                        imm.hideSoftInputFromWindow(viewCurrent.getWindowToken(), 0);
                    }
                }
                else{
                    int scrcoords[] = new int[2];
                    viewCurrent.getLocationOnScreen(scrcoords);
                    float x = event.getRawX() + viewCurrent.getLeft() - scrcoords[0];
                    float y = event.getRawY() + viewCurrent.getTop() - scrcoords[1];
                    if (x < viewCurrent.getLeft() || x > viewCurrent.getRight() || y < viewCurrent.getTop() || y > viewCurrent.getBottom()) imm.hideSoftInputFromWindow(viewCurrent.getWindowToken(), 0);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                startingY = event.getRawY();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public final void onClick(View v) {
        cmds.get(v.getId()).exec();
    }
}
