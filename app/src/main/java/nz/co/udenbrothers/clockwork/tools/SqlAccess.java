package nz.co.udenbrothers.clockwork.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashSet;


public class SqlAccess extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "clockDB";

    public SqlAccess(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    public void makeTable(HashSet<String> fields, String table) {
        final SQLiteDatabase db = getWritableDatabase();
        String CREATE_TABLE_NEW = "CREATE TABLE IF NOT EXISTS " + table + " (";

        for (String field : fields){
            CREATE_TABLE_NEW = CREATE_TABLE_NEW + field + ", ";
        }

        CREATE_TABLE_NEW = CREATE_TABLE_NEW.substring(0, CREATE_TABLE_NEW.length() - 2);
        CREATE_TABLE_NEW = CREATE_TABLE_NEW + ")";

        db.execSQL(CREATE_TABLE_NEW);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Cursor c = db.rawQuery("SELECT name FROM clockDB WHERE type='table'", null);
        ArrayList<String> tables = new ArrayList<>();
        while (c.moveToNext()) {
            tables.add(c.getString(0));
        }
        for (String table : tables) {
            String dropQuery = "DROP TABLE IF EXISTS " + table;
            db.execSQL(dropQuery);
        }
        c.close();
    }

    public int update(String table, ContentValues cv, String field, String value){
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.update(table, cv, field + "=" + value, null);
        db.close();
        return rows;
    }

    public void drop(String table){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + table);
        db.close();
    }

    public long add(String table, ContentValues cv){
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert(table, null, cv);
        db.close();
        return id;
    }

    public void get(String table, Cur cur) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + table;
        cur.setup(db.rawQuery(query, null),db);
    }

    public void get(String table, String field, String value, Cur cur) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + table + " WHERE " + field + "= '" + value + "'";
        cur.setup(db.rawQuery(query, null),db);
    }

    public void getByQuery(String query, Cur cur){
        SQLiteDatabase db = this.getWritableDatabase();
        cur.setup(db.rawQuery(query, null),db);
    }

    public void delete(String table, String field, String value){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table + " WHERE " + field + "= '" + value + "'");
        db.close();
    }

    public void clear(String table){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, null, null);
        db.close();
    }
}
