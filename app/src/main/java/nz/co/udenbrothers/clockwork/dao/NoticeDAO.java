package nz.co.udenbrothers.clockwork.dao;

import android.content.Context;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.models.Notice;

/**
 * Created by user on 04/07/2017.
 */

public class NoticeDAO extends ModelDAO {

    protected void init(){
        table = "Notice";
        field("title", "text");
        field("message", "text");
    }

    public NoticeDAO(Context context){
        super(context);
    }

    public void add(Notice notice){
        key.put("title", notice.title);
        key.put("message", notice.message);
        notice.id = save();
    }

    public ArrayList<Notice> getAll(){
        ArrayList<Notice> notices = new ArrayList<>();
        load();
        while (cur.next()){
            Notice notice = new Notice(cur.getStr("title"), cur.getStr("message"));
            notices.add(notice);
        }
        return notices;
    }
}
