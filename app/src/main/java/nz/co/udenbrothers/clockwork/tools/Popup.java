package nz.co.udenbrothers.clockwork.tools;

import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Checkable;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.abstractions.Cmd;


public class Popup implements View.OnClickListener{

    private SparseArray<Cmd> cmds = new SparseArray<>();
    private Dialog dialog;
    private Window window;

    public Popup(Context context, int id){
        dialog = new Dialog(context, R.style.MyDialog);
        setUp(id);
    }

    public Popup(Context context, int id, int sid){
        dialog = new Dialog(context, sid);
        setUp(id);
    }

    private void setUp(int id){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(id);
        window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
    }

    public void clicked(int id, Cmd cd){
        dialog.findViewById(id).setOnClickListener(this);
        cmds.put(id,cd);
    }

    public void clicked(View v, Cmd cd){
        v.setOnClickListener(this);
        cmds.put(v.getId(),cd);
    }

    public void setDimension(double width, double height){
        window.setLayout((int)width, (int)height);
    }

    public void setGravity(int gravity){
        window.setGravity(gravity);
    }

    public void show(){
        dialog.show();
    }

    public <T extends View & Checkable> T getView(int id){
        return dialog.findViewById(id);
    }

    public void dismiss(){
        dialog.dismiss();
    }

    public boolean isShowing(){
        return dialog.isShowing();
    }

    @Override
    public final void onClick(View v) {
        cmds.get(v.getId()).exec();
    }
}
