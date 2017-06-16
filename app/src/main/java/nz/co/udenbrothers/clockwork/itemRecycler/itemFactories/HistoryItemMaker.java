package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

import nz.co.udenbrothers.clockwork.global.Type;
import nz.co.udenbrothers.clockwork.itemRecycler.items.HeaderItem;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.items.ShiftItem;
import nz.co.udenbrothers.clockwork.models.Model;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.tools.Kit;

/**
 * Created by user on 15/06/2017.
 */

public class HistoryItemMaker extends ItemMaker {
    @Override
    public ArrayList<Item> toItems(ArrayList<? extends Model> models) {

        ArrayList<ShiftItem> shiftItems = new ArrayList<>();
        for (Model model : models){
            Shift shift = (Shift) model;
            ShiftItem shiftItem = new ShiftItem(Type.SHIFT,shift,context);
            shiftItem.startDate = Kit.strToDate(shift.shiftTimeStartOnUtc);
            shiftItem.endDate = Kit.strToDate(shift.shiftTimeEndOnUtc);
            shiftItems.add(shiftItem);
        }

        Collections.sort(shiftItems);

        ArrayList<Item> items = new ArrayList<>();
        String headerDate = "";
        String thisDate;
        HeaderItem curHeadItem = new HeaderItem(Type.HEADER,context);
        for (ShiftItem shiftItem : shiftItems){
            thisDate = Kit.dateToStr(shiftItem.startDate,"yyyy-MM-dd");

            if(!headerDate.equals(Kit.dateToStr(shiftItem.startDate,"yyyy-MM-dd"))){
                headerDate = thisDate;
                curHeadItem = new HeaderItem(Type.HEADER,context);
                curHeadItem.des = headerDate;
                items.add(curHeadItem);
            }

            curHeadItem.total = curHeadItem.total + shiftItem.endDate.getTime() - shiftItem.startDate.getTime();
            items.add(shiftItem);
        }
        return items;
    }

    @Override
    public Item toItem(Model model) {
        return null;
    }

    public HistoryItemMaker(Context context) {
        super(context);
    }
}
