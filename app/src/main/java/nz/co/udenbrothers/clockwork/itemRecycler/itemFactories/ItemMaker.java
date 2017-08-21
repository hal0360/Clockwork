package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.models.Model;
import nz.co.udenbrothers.clockwork.tools.Pref;



public class ItemMaker {

    protected Pref pref;
    protected Context context;

    public ItemMaker(Context context){
        pref = new Pref(context);
        this.context = context;
    }

    public final ArrayList<Item> toItems(ArrayList<? extends Model> models, int type){
        ArrayList<Item> items = new ArrayList<>();
        for (Model model : models){
            Item item = new Item(type);
            item.model = model;
            items.add(item);
        }
        return items;
    }

    public final Item newItem(int type) {
        return new Item(type);
    }

    public final Item newItem(int type, String des) {
        Item item = new Item(type);
        item.des = des;
        return item;
    }
}
