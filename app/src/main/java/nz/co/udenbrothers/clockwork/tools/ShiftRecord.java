package nz.co.udenbrothers.clockwork.tools;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import nz.co.udenbrothers.clockwork.itemRecycler.items.ShiftItem;
import nz.co.udenbrothers.clockwork.models.Shift;


public class ShiftRecord {

    private ArrayList<ShiftItem> shiftItems;

    public ShiftRecord(Context context, ArrayList<Shift> shifts){
        shiftItems = new ArrayList<>();
        for (Shift shift : shifts){
            ShiftItem shiftItem = new ShiftItem(shift,context);
            shiftItem.startDate = MyDate.strToDate(shift.shiftTimeStartOnUtc);
            shiftItem.endDate = MyDate.strToDate(shift.shiftTimeEndOnUtc);
            shiftItems.add(shiftItem);
        }
        Collections.sort(shiftItems);
    }

    public ArrayList<ShiftItem> getBefore(int days){
        if(days <= 0) return shiftItems;
        ArrayList<ShiftItem> newShifts = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1*days);
        for (ShiftItem shiftItem: shiftItems){
            if(c.getTime().compareTo(shiftItem.startDate) < 0){
                newShifts.add(shiftItem);
            }
        }
        return newShifts;
    }

    public String beforeTotal(int days){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        if(days > 0) c.add(Calendar.DATE, -1*days);
        long total = 0;
        for (ShiftItem shiftItem: shiftItems){
            if(c.getTime().compareTo(shiftItem.startDate) < 0){
                total  = total + shiftItem.endDate.getTime() - shiftItem.startDate.getTime();
            }
        }
        return MyDate.gethourMin(total);
    }
}
