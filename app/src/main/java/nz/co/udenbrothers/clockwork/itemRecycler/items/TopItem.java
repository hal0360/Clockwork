package nz.co.udenbrothers.clockwork.itemRecycler.items;

import android.content.Context;
import android.os.Handler;

import nz.co.udenbrothers.clockwork.global.Type;

/**
 * Created by user on 09/04/2017.
 */

public class TopItem extends Item {

    public Handler handler;

    public TopItem(int type, Handler handler, Context context) {
        super(type, context);
        this.handler = handler;
        if(type != Type.TOP){
            throw new IllegalArgumentException("INVALID ITEM TYPE");
        }
    }
}
