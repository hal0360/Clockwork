package nz.co.udenbrothers.clockwork.tools;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Pref {

    private SharedPreferences p;

    public Pref(Context context){
        p = context.getSharedPreferences("app", MODE_PRIVATE);
    }

    public void putStr(String key, String val){
        SharedPreferences.Editor editor = p.edit();
        editor.putString(key, val);
        editor.apply();
    }

    public String getStr(String key){
        return p.getString(key, "");
    }

    public void putInt( String key, int val){
        SharedPreferences.Editor editor = p.edit();
        editor.putInt(key, val);
        editor.apply();
    }

    public int getInt(String key){
        return p.getInt(key, 0);
    }

    public void putBool(String key, Boolean val){
        SharedPreferences.Editor editor = p.edit();
        editor.putBoolean(key, val);
        editor.apply();
    }

    public Boolean getBool(String key){
        return p.getBoolean(key, false);
    }

    public void clear(String key){
        SharedPreferences.Editor editor = p.edit();
        editor.remove(key);
        editor.apply();
    }
}
