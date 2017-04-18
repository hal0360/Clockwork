package nz.co.udenbrothers.clockwork.itemRecycler.items;

import android.content.Context;
import android.os.Handler;

import nz.co.udenbrothers.clockwork.global.Type;

/**
 * Created by user on 14/04/2017.
 */

public class HeaderItem extends Item{
    public long total = 0;

    public HeaderItem(int type, Context context) {
        super(type, context);
        if(type != Type.HEADER){
            throw new IllegalArgumentException("INVALID ITEM TYPE");
        }
    }
}
