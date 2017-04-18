package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.models.Model;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;

/**
 * Created by user on 08/04/2017.
 */

public abstract class ItemMaker {

    protected Context context;

    public ItemMaker(Context context){
        this.context = context;
    }

    public abstract ArrayList<Item> toItems(ArrayList<? extends Model> models);
    public abstract Item toItem(Model model);

    public final Item newItem(int type) {
        return new Item(type, context);
    }

    public final Item newItem(int type, String des) {
        Item item = new Item(type, context);
        item.des = des;
        return item;
    }
}
