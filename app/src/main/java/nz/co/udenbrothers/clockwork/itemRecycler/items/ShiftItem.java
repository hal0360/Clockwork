package nz.co.udenbrothers.clockwork.itemRecycler.items;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Date;

import nz.co.udenbrothers.clockwork.global.Type;
import nz.co.udenbrothers.clockwork.models.Shift;

/**
 * Created by user on 15/06/2017.
 */

public class ShiftItem extends Item  implements Comparable<ShiftItem>{
    public Shift shift;
    public Date startDate;
    public Date endDate;

    public ShiftItem(int type, Shift shift, Context context) {
        super(type, context);
        if(type != Type.SHIFT){
            throw new IllegalArgumentException("INVALID ITEM TYPE");
        }
        this.shift = shift;
    }

    @Override
    public int compareTo(@NonNull ShiftItem item) {
        if (this.startDate == null || item.startDate == null) return 0;
        return item.startDate.compareTo(this.startDate);
    }
}
