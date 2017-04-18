package nz.co.udenbrothers.clockwork.itemRecycler.items;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Date;

import nz.co.udenbrothers.clockwork.global.Type;
import nz.co.udenbrothers.clockwork.models.Stamp;

/**
 * Created by user on 09/04/2017.
 */

public class StampItem extends Item implements Comparable<StampItem>{

    public Stamp stamp;
    public Date startDate;
    public Date endDate;

    public StampItem(int type, Stamp stamp, Context context) {
        super(type, context);
        if(type != Type.STAMP){
            throw new IllegalArgumentException("INVALID ITEM TYPE");
        }
        this.stamp = stamp;
    }

    @Override
    public int compareTo(@NonNull StampItem item) {
        if (this.startDate == null || item.startDate == null) return 0;
        return item.startDate.compareTo(this.startDate);
    }
}
