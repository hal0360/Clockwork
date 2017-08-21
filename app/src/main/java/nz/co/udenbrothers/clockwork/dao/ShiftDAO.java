package nz.co.udenbrothers.clockwork.dao;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonParseException;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.models.Project;
import nz.co.udenbrothers.clockwork.models.Shift;

/**
 * Created by user on 15/06/2017.
 */

public class ShiftDAO extends ModelDAO {

    protected void init(){
        table = "Shift";
        field("userId", "str");
        field("qrCodeIdentifier", "str");
        field("shiftTimeStartOnUtc", "str");
        field("shiftTimeEndOnUtc", "str");
        field("uploaded", "int");
        field("comment", "str");
    }

    public ShiftDAO(Context context){
        super(context);
    }

    public void add(Shift shift){
        key.put("userId", shift.userId);
        key.put("uploaded", shift.uploaded);
        key.put("qrCodeIdentifier", shift.qrCodeIdentifier);
        key.put("shiftTimeStartOnUtc", shift.shiftTimeStartOnUtc);
        key.put("shiftTimeEndOnUtc", shift.shiftTimeEndOnUtc);
        key.put("comment", shift.comment);
        shift.id = save();
    }

    public void addFromJson(String json){
        try {
            Shift[] shifts = gson.fromJson(json, Shift[].class);
            for (Shift shift : shifts) add(shift);
        } catch (JsonParseException e) { Log.e("Parse",e+""); }
    }

    public void update(Shift shift){
        if(shift.id <= 0) throw new IllegalArgumentException("INVALID STAMP ID");
        key.put("userId", shift.userId);
        key.put("qrCodeIdentifier", shift.qrCodeIdentifier);
        key.put("uploaded", shift.uploaded);
        key.put("shiftTimeEndOnUtc", shift.shiftTimeEndOnUtc);
        key.put("shiftTimeStartOnUtc", shift.shiftTimeStartOnUtc);
        key.put("comment", shift.comment);
        save(shift.id);
    }

    public ArrayList<Shift> getAll(){
        ArrayList<Shift> shifts = new ArrayList<>();
        load();
        while (cur.next()){
            Shift shift = new Shift(cur.getStr("qrCodeIdentifier"),cur.getStr("shiftTimeStartOnUtc"),cur.getStr("shiftTimeEndOnUtc"),cur.getStr("userId"));
            shift.id = cur.getInt("id");
            shift.uploaded = cur.getInt("uploaded");
            shift.comment = cur.getStr("comment");
            shifts.add(shift);
        }
        return shifts;
    }

    public ArrayList<Shift> getBy(String ki, String val){
        ArrayList<Shift> shifts = new ArrayList<>();
        loadBy(ki, val);
        while (cur.next()){
            Shift shift = new Shift(cur.getStr("qrCodeIdentifier"),cur.getStr("shiftTimeStartOnUtc"),cur.getStr("shiftTimeEndOnUtc"),cur.getStr("userId"));
            shift.id = cur.getInt("id");
            shift.uploaded = cur.getInt("uploaded");
            shift.comment = cur.getStr("comment");
            shifts.add(shift);
        }
        return shifts;
    }
}
