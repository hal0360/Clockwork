package nz.co.udenbrothers.clockwork.tools;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Cur{

    private Cursor cursor;
    private SQLiteDatabase db;

    public Cur(String table, Context context){
        SqlAccess sql = new SqlAccess(context);
        sql.get(table,this);
    }

    public Cur(String table, Context context,String field, String value){
        SqlAccess sql = new SqlAccess(context);
        sql.get(table,field,value,this);
    }

    public void setup(Cursor c, SQLiteDatabase sql){
        cursor = c;
        db = sql;
    }

    public void setNull(){
        cursor = null;
        db = null;
    }

    public int getCount(){
        return cursor.getCount();
    }

    public int getInt(String field){
        return cursor.getInt(cursor.getColumnIndex(field));
    }

    public double getDouble(String field){
        return cursor.getDouble(cursor.getColumnIndex(field));
    }

    public long getLong(String field){
        return cursor.getLong(cursor.getColumnIndex(field));
    }

    public String getStr(String field){
        return cursor.getString(cursor.getColumnIndex(field));
    }

    public boolean done(){
        cursor.close();
        db.close();
        return false;
    }

    public boolean next() {

        return cursor != null && (cursor.moveToNext() || done());

    }
}
