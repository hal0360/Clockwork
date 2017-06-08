package nz.co.udenbrothers.clockwork.tools;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Cur{

    private Cursor cursor;
    private SQLiteDatabase db;

    public void setup(Cursor c, SQLiteDatabase sql){
        cursor = c;
        db = sql;
    }

    public int getCount(){
        return cursor.getCount();
    }

    public int getInt(String field){
        return cursor.getInt(cursor.getColumnIndex(field));
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
        return cursor.moveToNext() || done();
    }
}
