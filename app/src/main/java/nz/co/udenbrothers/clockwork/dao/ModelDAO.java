package nz.co.udenbrothers.clockwork.dao;

import android.content.ContentValues;
import android.content.Context;

import java.util.HashSet;

import nz.co.udenbrothers.clockwork.tools.Cur;
import nz.co.udenbrothers.clockwork.tools.SqlAccess;

public abstract class ModelDAO {

    private SqlAccess sql;
    protected final ContentValues key = new ContentValues();
    protected String table = "";
    private HashSet<String> fields = new HashSet<>();
    protected final Cur cur = new Cur();

    protected abstract void init();

    public ModelDAO(Context context){
        sql = new SqlAccess(context);
        fields.add("id integer primary key");
        init();
        sql.makeTable(fields, table);
    }

    protected final void field(String name, String type){
        type = type.toLowerCase();
        switch (type) {
            case "string":
            case "String":
            case "text":
            case "date":
            case "str":
                fields.add(name + " text");
                break;
            case "int":
            case "INT":
            case "Int":
            case "integer":
                fields.add(name + " integer");
                break;
            case "float":
            case "double":
            case "Float":
            case "Double":
            case "real":
                fields.add(name + " real");
                break;
            default:
                throw new IllegalArgumentException("INVALID FIELD TYPE");
        }
    }

    protected final long save(){
        long id = sql.add(table, key);
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
