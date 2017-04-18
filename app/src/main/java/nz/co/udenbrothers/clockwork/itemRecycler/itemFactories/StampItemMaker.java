package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

import nz.co.udenbrothers.clockwork.global.Type;
import nz.co.udenbrothers.clockwork.itemRecycler.items.HeaderItem;
import nz.co.udenbrothers.clockwork.models.Model;
import nz.co.udenbrothers.clockwork.models.Stamp;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.items.StampItem;


public class StampItemMaker extends ItemMaker {
    @Override
    public ArrayList<Item> toItems(ArrayList<? extends Model> models) {

        ArrayList<StampItem> stampItems = new ArrayList<>();
        for (Model model : models){
            Stamp stamp = (Stamp) model;
            StampItem stampItem = new StampItem(Type.STAMP,stamp,context);
            stampItem.startDate = Kit.strToDate(stamp.startTime);
            stampItem.endDate = Kit.strToDate(stamp.endTime);
            stampItems.add(stampItem);
        }

        Collections.sort(stampItems);

        ArrayList<Item> items = new ArrayList<>();
        String headerDate = "";
        String thisDate;
        HeaderItem curHeadItem = new HeaderItem(Type.HEADER,context);
        for (StampItem stampItem : stampItems){
            thisDate = Kit.dateToStr(stampItem.startDate,"yyyy-MM-dd");

            if(!headerDate.equals(Kit.dateToStr(stampItem.startDate,"yyyy-MM-dd"))){
                headerDate = thisDate;
                curHeadItem = new HeaderItem(Type.HEADER,context);
                curHeadItem.des = headerDate;
                items.add(curHeadItem);
            }

            curHeadItem.total = curHeadItem.total + stampItem.endDate.getTime() - stampItem.startDate.getTime();
            items.add(stampItem);
        }
        return items;
    }

    @Override
    public Item toItem(Model model) {
        return null;
    }

    public StampItemMaker(Context context) {
        super(context);
    }
}
