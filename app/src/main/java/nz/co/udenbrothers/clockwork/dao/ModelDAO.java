package nz.co.udenbrothers.clockwork.dao;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;

import java.util.HashSet;

import nz.co.udenbrothers.clockwork.sql_stuff.Cur;
import nz.co.udenbrothers.clockwork.sql_stuff.SqlAccess;

public abstract class ModelDAO {

    private SqlAccess sql;
    protected final ContentValues key = new ContentValues();
    protected String table = "";
    private HashSet<String> fields = new HashSet<>();
    protected final Cur cur;
    protected Gson gson;

    protected abstract void init();

    public ModelDAO(Context context){
        gson = new Gson();
        sql = SqlAccess.getInstance();
        fields.add("id integer primary key");
        init();
        sql.makeTable(fields, table);
        cur = new Cur(table);
    }

    protected final void field(String name, String type){
        type = type.toLowerCase();
        switch (type) {
            case "string":
            case "text":
            case "str":
                fields.add(name + " text");
                break;
            case "int":
            case "integer":
                fields.add(name + " integer");
                break;
            case "float":
            case "double":
            case "real":
                fields.add(name + " real");
                break;
            default:
                throw new IllegalArgumentException("INVALID FIELD TYPE");
        }
    }

    protected final int save(){
        int id = (int) sql.add(table, key);
        key.clear();
        return id;
    }

    protected final void save(long id){
        sql.update(table, key, "id", id+"");
        key.clear();
    }

    public final void delete(int id){
        sql.delete(table, "id", id+"");
    }

    public final void clear(){
        sql.clear(table);
    }

    protected final void load(){
        sql.get(table, cur);
    }

    public final void deleteBy(String name, String v){
        sql.delete(table, name, v);
    }

    public final void deleteBy(String name, int v){
        sql.delete(table, name, v+"");
    }

    protected final void loadBy(String name, String v){
        sql.get(table, name, v, cur);
    }

    protected final void loadBy(String name, int v){
        sql.get(table, name, v+"", cur);
    }

    protected final void loadBy(String name, long v){
        sql.get(table, name, v+"", cur);
    }

    protected final void loadByQuery(String query){
        sql.getByQuery(query, cur);
    }
}
