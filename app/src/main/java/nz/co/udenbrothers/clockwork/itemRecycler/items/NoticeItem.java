package nz.co.udenbrothers.clockwork.itemRecycler.items;

import android.content.Context;

import java.util.Date;

import nz.co.udenbrothers.clockwork.global.Type;
import nz.co.udenbrothers.clockwork.models.Notice;
import nz.co.udenbrothers.clockwork.models.Shift;


public class NoticeItem extends Item {

    public Notice notice;

    public NoticeItem(Notice notice, Context context) {
        super(Type.NOTICE, context);
        this.notice = notice;
    }
}
