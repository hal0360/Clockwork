package nz.co.udenbrothers.clockwork.itemRecycler.items;

import android.support.annotation.NonNull;

import java.util.Date;

import nz.co.udenbrothers.clockwork.global.V;
import nz.co.udenbrothers.clockwork.models.Shift;


public class ShiftItem extends Item  implements Comparable<ShiftItem>{

    public Shift shift;
    public Date startDate;
    public Date endDate;

    public ShiftItem(Shift shift) {
        super(V.SHIFT);
        this.shift = shift;
    }

    @Override
    public int compareTo(@NonNull ShiftItem item) {
        if (this.startDate == null || item.startDate == null) return 0;
        return item.startDate.compareTo(this.startDate);
    }
}
