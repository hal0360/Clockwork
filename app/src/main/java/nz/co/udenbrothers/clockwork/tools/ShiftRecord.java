package nz.co.udenbrothers.clockwork.tools;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import nz.co.udenbrothers.clockwork.global.Type;
import nz.co.udenbrothers.clockwork.itemRecycler.items.ShiftItem;
import nz.co.udenbrothers.clockwork.models.Shift;

/**
 * Created by user on 15/06/2017.
 */

public class ShiftRecord {

    private ArrayList<ShiftItem> shiftItems;

    public ShiftRecord(Context context, ArrayList<Shift> shifts){

        shiftItems = new ArrayList<>();
        for (Shift shift : shifts){
            ShiftItem shiftItem = new ShiftItem(Type.SHIFT,shift,context);
            shiftItem.startDate = Kit.strToDate(shift.shiftTimeStartOnUtc);
            shiftItem.endDate = Kit.strToDate(shift.shiftTimeEndOnUtc);
            shiftItems.add(shiftItem);
        }
        Collections.sort(shiftItems);

    }

    public int getCount(){
        return shiftItems.size();
    }

    public String beforeTotal(int days){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1*days);
        long total = 0;
        for (ShiftItem shiftItem: shiftItems){
            if(c.getTime().compareTo(shiftItem.startDate) < 0){
                total  = total + shiftItem.endDate.getTime() - shiftItem.startDate.getTime();
            }
        }
        return Kit.gethourMin(total);
    }
}
