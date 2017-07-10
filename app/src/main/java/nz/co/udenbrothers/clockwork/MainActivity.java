package nz.co.udenbrothers.clockwork;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import nz.co.udenbrothers.clockwork.abstractions.Cmd;
import nz.co.udenbrothers.clockwork.tools.Pref;

public abstract class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected Pref pref;
    private SparseArray<Cmd> cmds = new SparseArray<>();
    protected Gson gson;
    private float startingY;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new Pref(this);
        gson = new Gson();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    protected final void toActivity(Class actClass){
        Intent intent = new Intent(this, actClass);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        }
        startActivity(intent);
        this.finish();
    }

    protected final void pushActivity(Class actClass){
        Intent intent = new Intent(this, actClass);
        startActivity(intent);
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
    public void onClick(View v) {
        cmds.get(v.getId()).exec();
    }

}
