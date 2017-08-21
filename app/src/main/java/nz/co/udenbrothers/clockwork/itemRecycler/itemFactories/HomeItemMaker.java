package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.global.Type;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.models.Project;


public class HomeItemMaker extends ItemMaker {
    public HomeItemMaker(Context context) {
        super(context);
    }

    public ArrayList<Item> fetch() {
        ArrayList<Project> projects = Project.get(context);
        ArrayList<Item> items = new ArrayList<>();
        if(Project.search(context, pref.getStr("currentProject"))){
            items.add(newItem(Type.TOP, pref.getStr("currentProject")));
        }
        else {
            pref.putStr("currentProject","");
        }

        for (Project model : projects){
            Item item = new Item(Type.PROJECT);
            item.model = model;
            items.add(item);
        }
        items.add(newItem(Type.TOTAL,"TOTAL"));
        return items;
    }

}
