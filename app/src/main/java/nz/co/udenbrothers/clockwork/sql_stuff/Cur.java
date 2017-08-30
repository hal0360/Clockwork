package nz.co.udenbrothers.clockwork.sql_stuff;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Cur{

    private Cursor cursor;
    private SQLiteDatabase db;

    public Cur(String table){
        SqlAccess sql = SqlAccess.getInstance();
        sql.get(table,this);
    }

    public Cur(String table,String field, String value){
        SqlAccess sql = SqlAccess.getInstance();
        sql.get(table,field,value,this);
    }

    public String cursorToString() {
        JSONArray arr = new JSONArray();
        while (next()) {
            int nColumns = cursor.getColumnCount();
            JSONObject row = new JSONObject();
            for (int i = 0 ; i < nColumns ; i++) {
                String colName = cursor.getColumnName(i);
                if (colName != null) {
                    try {
                        switch (cursor.getType(i)) {
                            case Cursor.FIELD_TYPE_BLOB   : row.put(colName, Arrays.toString(cursor.getBlob(i))); break;
                            case Cursor.FIELD_TYPE_FLOAT  : row.put(colName, cursor.getDouble(i))         ; break;
                            case Cursor.FIELD_TYPE_INTEGER: row.put(colName, cursor.getLong(i))           ; break;
                            case Cursor.FIELD_TYPE_NULL   : row.put(colName, null)                     ; break;
                            case Cursor.FIELD_TYPE_STRING : row.put(colName, cursor.getString(i))         ; break;
                        }
                    } catch (JSONException e) {
                        Log.e("Error json from cursor",e+"");
                    }
                }
            }
            arr.put(row);
        }
        return arr.toString();
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

    private boolean done(){
        cursor.close();
        db.close();
        return false;
    }

    public boolean next() {

        return cursor != null && (cursor.moveToNext() || done());

    }
}
