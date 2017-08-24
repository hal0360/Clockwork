package nz.co.udenbrothers.clockwork.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Pref {

    private SharedPreferences p;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public Pref(Context context){
        p = context.getSharedPreferences("app", MODE_PRIVATE);
        editor = p.edit();
    }

    public void putStr(String key, String val){
        if(val == null) editor.putString(key, "");
        else editor.putString(key, val);
        editor.apply();
    }

    public String getStr(String key){
        return p.getString(key, "");
    }

    public void putInt( String key, int val){
        editor.putInt(key, val);
        editor.apply();
    }

    public int getInt(String key){
        return p.getInt(key, 0);
    }

    public void putBool(String key, Boolean val){
        editor.putBoolean(key, val);
        editor.apply();
    }

    public Boolean getBool(String key, Boolean def){
        return p.getBoolean(key, def);
    }

    public void clear(){
        editor.clear();
        editor.apply();
    }
}
