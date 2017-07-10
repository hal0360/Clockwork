package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.global.Type;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.items.ProjectItem;
import nz.co.udenbrothers.clockwork.models.Model;
import nz.co.udenbrothers.clockwork.models.Project;
import nz.co.udenbrothers.clockwork.tools.Pref;



public class ItemMaker {

    protected Context context;
    protected Pref pref;

    public ItemMaker(Context context){
        this.context = context;
        pref = new Pref(context);
    }

    public final ArrayList<Item> toItems(ArrayList<? extends Model> models, int type){
        ArrayList<Item> items = new ArrayList<>();
        for (Model model : models){
            Item item = new Item(type,context);
            item.model = model;
            items.add(item);
        }
        return items;
    }

    public final Item newItem(int type) {
        return new Item(type, context);
    }

    public final Item newItem(int type, String des) {
        Item item = new Item(type, context);
        item.des = des;
        return item;
    }
}
