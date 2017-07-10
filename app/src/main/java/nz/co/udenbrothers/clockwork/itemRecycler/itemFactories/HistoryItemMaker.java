package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.dao.ShiftDAO;
import nz.co.udenbrothers.clockwork.itemRecycler.items.HeaderItem;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.items.ShiftItem;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.tools.MyDate;
import nz.co.udenbrothers.clockwork.tools.ShiftRecord;


public class HistoryItemMaker extends ItemMaker {

    private ShiftDAO shiftDAO;
    private ShiftRecord shiftRecord;

    private ArrayList<Item> pack(ArrayList<ShiftItem> shiftItems){
        ArrayList<Item> items = new ArrayList<>();
        String headerDate = "";
        String thisDate;
        HeaderItem curHeadItem = new HeaderItem(context);
        for (ShiftItem shiftItem : shiftItems){
            thisDate = MyDate.dateToStr(shiftItem.startDate,"yyyy-MM-dd");
            if(!headerDate.equals(MyDate.dateToStr(shiftItem.startDate,"yyyy-MM-dd"))){
                headerDate = thisDate;
                curHeadItem = new HeaderItem(context);
                curHeadItem.des = headerDate;
                items.add(curHeadItem);
            }
            curHeadItem.total = curHeadItem.total + shiftItem.endDate.getTime() - shiftItem.startDate.getTime();
            items.add(shiftItem);
        }
        return items;
    }

    public HistoryItemMaker(Context context) {
        super(context);
        shiftDAO = new ShiftDAO(context);
    }

    public ArrayList<Item> fetch(int days) {
        shiftRecord = new ShiftRecord(context, shiftDAO.getAll());
        return pack(shiftRecord.getBefore(days));
    }

    public String getTotal(int days){
        return shiftRecord.beforeTotal(days);
    }

    public ArrayList<Item> fetchBy(String key, String val, int days) {
        shiftRecord = new ShiftRecord(context, shiftDAO.getBy(key, val));
        return pack(shiftRecord.getBefore(days));
    }
}
