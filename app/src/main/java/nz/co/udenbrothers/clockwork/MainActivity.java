package nz.co.udenbrothers.clockwork;

/**
 * Created by user on 13/06/2017.
 */

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

public abstract class MainActivity extends AppCompatActivity implements View.OnClickListener {
    protected Pref pref;
    private SparseArray<Cmd> cmds = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new Pref(this);
    }

    protected final void toActivity(Class actClass){
        Intent intent = new Intent(this, actClass);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        }
        startActivity(intent);
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
