package nz.co.udenbrothers.clockwork.tools;

import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.abstractions.Cmd;


public class Popup implements View.OnClickListener{

    private SparseArray<Cmd> cmds = new SparseArray<>();
    private Dialog dialog;

    public Popup(Context context, int id){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(id);
    }

    public void clicked(int id, Cmd cd){
        dialog.findViewById(id).setOnClickListener(this);
        cmds.put(id,cd);
    }

    public void clicked(View v, Cmd cd){
        v.setOnClickListener(this);
        cmds.put(v.getId(),cd);
    }

    public void show(){
        dialog.show();
    }

    public View getView(int id){
        return dialog.findViewById(id);
    }

    public void dismiss(){
        dialog.dismiss();
    }

    @Override
    public final void onClick(View v) {
        cmds.get(v.getId()).exec();
    }
}
