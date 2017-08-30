package nz.co.udenbrothers.clockwork.sql_stuff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import nz.co.udenbrothers.clockwork.App;


public class SqlAccess extends SQLiteOpenHelper {

    private static SqlAccess sInstance;

    private SqlAccess(Context context) {
        super(context, SQL.DBNAME, null, SQL.VERSION);
    }

    public static synchronized SqlAccess getInstance() {
        if (sInstance == null) sInstance = new SqlAccess(App.get());
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    public synchronized void makeTable(HashSet<String> fields, String table) {
        SQLiteDatabase db = getWritableDatabase();
        String CREATE_TABLE_NEW = "CREATE TABLE IF NOT EXISTS " + table + " (";

        for (String field : fields){
            CREATE_TABLE_NEW = CREATE_TABLE_NEW + field + ", ";
        }

        CREATE_TABLE_NEW = CREATE_TABLE_NEW.substring(0, CREATE_TABLE_NEW.length() - 2);
        CREATE_TABLE_NEW = CREATE_TABLE_NEW + ")";

        db.execSQL(CREATE_TABLE_NEW);
        db.close();
    }

    public synchronized void createTable(String sqls) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqls);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropAll();
    }

    public int update(String table, ContentValues cv, String field, String value){
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.update(table, cv, field + "=" + value, null);
        db.close();
        return rows;
    }

    public synchronized void dropAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM " + SQL.DBNAME + " WHERE type='table'", null);
        List<String> tables = new ArrayList<>();
        while (c.moveToNext()) {
            tables.add(c.getString(0));
        }
        for (String table : tables) {
            String dropQuery = "DROP TABLE IF EXISTS " + table;
            db.execSQL(dropQuery);
        }
        c.close();
        db.close();
    }

    public synchronized long add(String table, ContentValues cv){
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert(table, null, cv);
        db.close();
        return id;
    }

    public synchronized void get(String table, Cur cur) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + table;
        try {
            cur.setup(db.rawQuery(query, null),db);
        } catch(SQLiteException e) {
            cur.setNull();
        }
    }

    public synchronized void get(String table, String field, String value, Cur cur) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + table + " WHERE " + field + "= ?";
        try {
            cur.setup(db.rawQuery(query, new String[] { value }),db);
        } catch(SQLiteException e) {
            cur.setNull();
        }
    }

    public synchronized void getByQuery(String query, Cur cur){
        SQLiteDatabase db = this.getWritableDatabase();
        cur.setup(db.rawQuery(query, null),db);
    }

    public synchronized void delete(String table, String field, String value){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table + " WHERE " + field + "= '" + value + "'");
        db.close();
    }

    public synchronized void clear(String table){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, null, null);
        db.close();
    }
}
