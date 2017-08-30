package nz.co.udenbrothers.clockwork.models;

import android.content.ContentValues;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Field;

import nz.co.udenbrothers.clockwork.sql_stuff.SqlAccess;

public abstract class Model{

    public long id = -1;

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void save(){

        String table = this.getClass().getSimpleName();
        SqlAccess sql = SqlAccess.getInstance();
        ContentValues cv = new ContentValues();
        String CREATE_TABLE_NEW = "CREATE TABLE IF NOT EXISTS " + table + " (id integer primary key, ";

        Class<?> thisClass;
        try {
            thisClass = Class.forName(this.getClass().getName());
            Field[] aClassFields = thisClass.getDeclaredFields();
            for(Field f : aClassFields){
                if(!f.isSynthetic()){
                    if (f.get(this) instanceof Integer) {
                        CREATE_TABLE_NEW = CREATE_TABLE_NEW + f.getName() + " integer, ";
                        cv.put(f.getName(), (Integer) f.get(this));
                    } else if (f.get(this) instanceof String) {
                        CREATE_TABLE_NEW = CREATE_TABLE_NEW + f.getName() + " text, ";
                        cv.put(f.getName(), (String) f.get(this));
                    } else if (f.get(this) == null) {
                        CREATE_TABLE_NEW = CREATE_TABLE_NEW + f.getName() + " text, ";
                        cv.put(f.getName(), "");
                    } else if (f.get(this) instanceof Long) {
                        CREATE_TABLE_NEW = CREATE_TABLE_NEW + f.getName() + " integer, ";
                        cv.put(f.getName(), (Long) f.get(this));
                    } else {
                        CREATE_TABLE_NEW = CREATE_TABLE_NEW + f.getName() + " real, ";
                        cv.put(f.getName(), (Double) f.get(this));
                    }
                }
            }
        } catch (Exception e) {
            Log.e("Class reflection Error",e+"");
            return;
        }

        CREATE_TABLE_NEW = CREATE_TABLE_NEW.substring(0, CREATE_TABLE_NEW.length() - 2);
        CREATE_TABLE_NEW = CREATE_TABLE_NEW + ")";
        sql.createTable(CREATE_TABLE_NEW);

        if(id > 0){
            sql.update(table, cv, "id", id+"");
        }
        else {
            this.id = sql.add(table, cv);
        }
        cv.clear();
    }

    public void delete(){
        SqlAccess sql = SqlAccess.getInstance();
        String table = this.getClass().getSimpleName();
        sql.delete(table, "id", id+"");
    }
}
