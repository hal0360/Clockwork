package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.global.Type;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;

import nz.co.udenbrothers.clockwork.itemRecycler.items.ProjectItem;
import nz.co.udenbrothers.clockwork.itemRecycler.items.TopItem;
import nz.co.udenbrothers.clockwork.models.Model;
import nz.co.udenbrothers.clockwork.models.Project;

/**
 * Created by user on 15/06/2017.
 */

public class HomeItemMaker extends ItemMaker {
    public HomeItemMaker(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Item> toItems(ArrayList<? extends Model> models) {
        ArrayList<Item> items = new ArrayList<>();
        for (Model model : models){
            ProjectItem projectItem = new ProjectItem(Type.PROJECT,(Project) model,context);
            items.add(projectItem);
        }
        items.add(newItem(Type.TOTAL,"TOTAL"));
        return items;
    }

    @Override
    public Item toItem(Model model) {
        return new ProjectItem(Type.PROJECT,(Project) model,context);
    }

    public TopItem getTopItem(Handler handler, String des){
        TopItem topItem = new TopItem(Type.TOP,handler,context);
        topItem.des = des;
        return topItem;
    }
}
