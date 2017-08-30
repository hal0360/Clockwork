package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.models.Model;



public class ItemMaker {

    protected Context context;

    public ItemMaker(Context context){
        this.context = context;
    }

    public final List<Item> toItems(List<? extends Model> models, int type){
        List<Item> items = new ArrayList<>();
        for (Model model : models){
            Item item = new Item(type);
            item.model = model;
            items.add(item);
        }
        return items;
    }

    public final Item newItem(int type, Model model) {
        Item item = new Item(type);
        item.model = model;
        return item;
    }


    public final Item newItem(int type, String des) {
        Item item = new Item(type);
        item.des = des;
        return item;
    }
}
