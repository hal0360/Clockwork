package nz.co.udenbrothers.clockwork.models;

import android.content.Context;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.tools.Cur;

public class Notice extends Model{

    public String title;
    public String message;

    public Notice(String ti, String mss){
        title = ti;
        message = mss;
    }

    public static ArrayList<Notice> get(Context context){
        Cur cur = load(context,"Notice");
        ArrayList<Notice> notices = new ArrayList<>();
        while (cur.next()){
            Notice notice = new Notice(cur.getStr("title"), cur.getStr("message"));
            notice.id = cur.getInt("id");
            notices.add(notice);
        }
        return notices;
    }

}