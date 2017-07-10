package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.dao.NoticeDAO;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.items.NoticeItem;
import nz.co.udenbrothers.clockwork.models.Notice;


public class NoticeItemMaker extends ItemMaker {
    public NoticeItemMaker(Context context) {
        super(context);
    }

    public ArrayList<Item> fetch() {
        NoticeDAO noticeDAO = new NoticeDAO(context);
        ArrayList<Notice> notices = noticeDAO.getAll();
        ArrayList<Item> items = new ArrayList<>();
        for (Notice model : notices){
            NoticeItem noticeItem = new NoticeItem(model,context);
            items.add(noticeItem);
        }
        return items;
    }
}
