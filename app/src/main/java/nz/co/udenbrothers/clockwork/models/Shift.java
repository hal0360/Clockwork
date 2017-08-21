package nz.co.udenbrothers.clockwork.models;


import android.content.Context;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.tools.Cur;

public class Shift extends Model {

    public String userId;
    public String qrCodeIdentifier;
    public String shiftTimeStartOnUtc;
    public String shiftTimeEndOnUtc;
    public String comment = "";
    public int uploaded = 0;
    public int stopped = 0;

    public Shift(String name, String start, String end, String uid){
        qrCodeIdentifier = name;
        shiftTimeStartOnUtc = start;
        shiftTimeEndOnUtc = end;
        userId = uid;
    }

    public static ArrayList<Shift> get(Context context){
        Cur cur = load(context,"Shift");
        ArrayList<Shift> shifts = new ArrayList<>();
        while (cur.next()){
            Shift shift = new Shift(cur.getStr("qrCodeIdentifier"),cur.getStr("shiftTimeStartOnUtc"),cur.getStr("shiftTimeEndOnUtc"),cur.getStr("userId"));
            shift.id = cur.getInt("id");
            shift.uploaded = cur.getInt("uploaded");
            shift.comment = cur.getStr("comment");
            shift.stopped = cur.getInt("stopped");
            shifts.add(shift);
        }
        return shifts;
    }

    public static ArrayList<Shift> get(Context context, String key, String val){
        Cur cur = load(context,"Shift", key, val);
        ArrayList<Shift> shifts = new ArrayList<>();
        while (cur.next()){
            Shift shift = new Shift(cur.getStr("qrCodeIdentifier"),cur.getStr("shiftTimeStartOnUtc"),cur.getStr("shiftTimeEndOnUtc"),cur.getStr("userId"));
            shift.id = cur.getInt("id");
            shift.uploaded = cur.getInt("uploaded");
            shift.comment = cur.getStr("comment");
            shift.stopped = cur.getInt("stopped");
            shifts.add(shift);
        }
        return shifts;
    }
}
