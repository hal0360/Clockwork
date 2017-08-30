package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import nz.co.udenbrothers.clockwork.temps.TEMP;
import nz.co.udenbrothers.clockwork.itemRecycler.items.HeaderItem;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.items.ShiftItem;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.sql_stuff.SQL;
import nz.co.udenbrothers.clockwork.tools.MyDate;
import nz.co.udenbrothers.clockwork.tools.ShiftRecord;


public class HistoryItemMaker extends ItemMaker {

    private ShiftRecord shiftRecord;

    private List<Item> pack(List<ShiftItem> shiftItems){
        List<Item> items = new ArrayList<>();
        String headerDate = "";
        String thisDate;
        HeaderItem curHeadItem = new HeaderItem();
        for (ShiftItem shiftItem : shiftItems){
            thisDate = MyDate.dateToStr(shiftItem.startDate,"yyyy-MM-dd");
            if(!headerDate.equals(MyDate.dateToStr(shiftItem.startDate,"yyyy-MM-dd"))){
                headerDate = thisDate;
                curHeadItem = new HeaderItem();
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
    }

    public List<Item> fetch(int days) {

        if(TEMP.QR != null){
            shiftRecord = new ShiftRecord(SQL.get(Shift.class, s -> s.qrCodeIdentifier.equals(TEMP.QR)));
        }
        else if(TEMP.UID != null){
            shiftRecord = new ShiftRecord(SQL.get(Shift.class, s -> s.userId.equals(TEMP.UID)));
        }
        else {
            shiftRecord = new ShiftRecord(SQL.get(Shift.class));
        }

        return pack(shiftRecord.getBefore(days));
    }

    public String getTotal(int days){
        return shiftRecord.beforeTotal(days);
    }

}
