package nz.co.udenbrothers.clockwork.itemRecycler.items;

import android.content.Context;

/**
 * Created by user on 09/04/2017.
 */

public class Item{

    public int type;
    public String des = "";
    public Context context;

    public Item(int type, Context context){
        this.type = type;
        this.context = context;
    }


}
