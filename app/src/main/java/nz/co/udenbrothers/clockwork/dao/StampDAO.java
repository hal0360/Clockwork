package nz.co.udenbrothers.clockwork.dao;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

import nz.co.udenbrothers.clockwork.models.Stamp;
import nz.co.udenbrothers.clockwork.tools.Kit;


public class StampDAO extends ModelDAO{

    protected void init(){
        table = "Stamp";
        field("site_name", "str");
        field("staff_name", "str");
        field("startTime", "date");
        field("comment", "text");
        field("endTime", "str");
    }

    public StampDAO(Context context){
        super(context);
    }

    public void add(Stamp stamp){
        key.put("site_name", stamp.site_name);
        key.put("staff_name", stamp.staff_name);
        key.put("startTime", stamp.startTime);
        key.put("comment", stamp.comment);
        key.put("endTime", stamp.endTime);
        stamp.id = save();
    }

    public void update(Stamp stamp){
        if(stamp.id <= 0) throw new IllegalArgumentException("INVALID STAMP ID");
        key.put("site_name", stamp.site_name);
        key.put("staff_name", stamp.staff_name);
        key.put("startTime", stamp.startTime);
        key.put("comment", stamp.comment);
        key.put("endTime", stamp.endTime);
        save(stamp.id);
    }

    public ArrayList<Stamp> getAll(){
        ArrayList<Stamp> list = new ArrayList<>();
        load();
        while (cur.next()){
            Stamp stamp = new Stamp();
            stamp.id = cur.getLong("id");
            stamp.endTime = cur.getStr("endTime");
            stamp.startTime = cur.getStr("startTime");
            stamp.site_name = cur.getStr("site_name");
            stamp.comment = cur.getStr("comment");
            stamp.staff_name = cur.getStr("staff_name");
            list.add(stamp);
        }
        return list;
    }

    public ArrayList<Stamp> getBy(String ki, String val){
        ArrayList<Stamp> list = new ArrayList<>();
        loadBy(ki, val);
        while (cur.next()){
            Stamp stamp = new Stamp();
            stamp.id = cur.getLong("id");
            stamp.endTime = cur.getStr("endTime");
            stamp.startTime = cur.getStr("startTime");
            stamp.site_name = cur.getStr("site_name");
            stamp.staff_name = cur.getStr("staff_name");
            stamp.comment = cur.getStr("comment");
            list.add(stamp);
        }
        return list;
    }

    public long getBeforeTotal(String field, String val, int days){
        loadByQuery("SELECT  * FROM " + table + " WHERE " + field + "= '" + val + "' AND startTime >= date('now','-" + days + " day')");
        long total  =0;
        while (cur.next()){
            total = total + Kit.strToDate(cur.getStr("endTime")).getTime() - Kit.strToDate(cur.getStr("startTime")).getTime();
        }
        return total;
    }

    public long getBeforeTotal( int days){
        loadByQuery("SELECT  * FROM " + table + " WHERE startTime >= date('now','-" + days + " day')");
        long total  =0;
        while (cur.next()){
            total = total + Kit.strToDate(cur.getStr("endTime")).getTime() - Kit.strToDate(cur.getStr("startTime")).getTime();
        }
        return total;
    }

    public long getBeforeTotal(){
        load();
        long total  =0;
        while (cur.next()){
            total = total + Kit.strToDate(cur.getStr("endTime")).getTime() - Kit.strToDate(cur.getStr("startTime")).getTime();
        }
        return total;
    }
}
