package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.global.Type;
import nz.co.udenbrothers.clockwork.models.Model;
import nz.co.udenbrothers.clockwork.models.Site;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.items.SiteItem;
import nz.co.udenbrothers.clockwork.itemRecycler.items.TopItem;

/**
 * Created by user on 08/04/2017.
 */

public class SiteItemMaker extends ItemMaker {
    public SiteItemMaker(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Item> toItems(ArrayList<? extends Model> models) {
        ArrayList<Item> items = new ArrayList<>();
        for (Model model : models){
            SiteItem siteItem = new SiteItem(Type.SITE,(Site) model,context);
            items.add(siteItem);
        }
        items.add(newItem(Type.TOTAL,"TOTAL"));
        return items;
    }

    @Override
    public Item toItem(Model model) {
        return new SiteItem(Type.SITE,(Site) model,context);
    }

    public TopItem getTopItem(Handler handler, String des){
        TopItem topItem = new TopItem(Type.TOP,handler,context);
        topItem.des = des;
        return topItem;
    }
}
