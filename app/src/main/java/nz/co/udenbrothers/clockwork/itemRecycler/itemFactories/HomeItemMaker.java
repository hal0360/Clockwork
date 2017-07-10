package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.dao.ProjectDAO;
import nz.co.udenbrothers.clockwork.global.Type;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.items.ProjectItem;
import nz.co.udenbrothers.clockwork.models.Project;


public class HomeItemMaker extends ItemMaker {
    public HomeItemMaker(Context context) {
        super(context);
    }

    public ArrayList<Item> fetch() {
        ProjectDAO projectDAO = new ProjectDAO(context);
        ArrayList<Project> projects = projectDAO.getAll();
        ArrayList<Item> items = new ArrayList<>();
        if(projectDAO.search(pref.getStr("currentProject"))){
            items.add(newItem(Type.TOP, pref.getStr("currentProject")));
        }
        else {
            pref.putStr("currentProject","");
        }
        for (Project model : projects){
            ProjectItem projectItem = new ProjectItem(model,context);
            items.add(projectItem);
        }
        items.add(newItem(Type.TOTAL,"TOTAL"));
        return items;
    }

}
