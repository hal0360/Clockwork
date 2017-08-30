package nz.co.udenbrothers.clockwork;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by user on 28/08/2017.
 */

public class App extends Application {

    private static App instance;
    private static SharedPreferences p;
    private static SharedPreferences.Editor editor;
    public static App get() { return instance; }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        p = getSharedPreferences("app", MODE_PRIVATE);
        editor = p.edit();
    }

    public static void putStr(String key, String val){
        editor.putString(key, val);
        editor.apply();
    }

    public static String getStr(String key){
        return p.getString(key, null);
    }

    public static void putInt( String key, int val){
        editor.putInt(key, val);
        editor.apply();
    }

    public static int getInt(String key){
        return p.getInt(key, 0);
    }

    public static void putLong( String key, long val){
        editor.putLong(key, val);
        editor.apply();
    }

    public static long getLong(String key){
        return p.getLong(key, 0);
    }

    public static void putFloat( String key, float val){
        editor.putFloat(key, val);
        editor.apply();
    }

    public static float getFloat(String key){
        return p.getFloat(key, 0f);
    }

    public static void putBool(String key, Boolean val){
        editor.putBoolean(key, val);
        editor.apply();
    }

    public static Boolean getBool(String key, Boolean def){
        return p.getBoolean(key, def);
    }

    public static void clear(){
        editor.clear();
        editor.apply();
    }

}