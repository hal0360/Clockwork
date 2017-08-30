package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import nz.co.udenbrothers.clockwork.global.V;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.models.Project;
import nz.co.udenbrothers.clockwork.sql_stuff.SQL;
import nz.co.udenbrothers.clockwork.temps.Act;
import nz.co.udenbrothers.clockwork.temps.Profile;


public class HomeItemMaker extends ItemMaker {

    private List<Project> projects;

    public HomeItemMaker(Context context) {
        super(context);
    }

    public List<Item> fetch() {

        projects = SQL.get(Project.class);

        List<Item> items = new ArrayList<>();
        if(search(Act.current())){
            items.add(newItem(V.TOP, Act.current()));
        }
        else {
            Act.current(null);
        }

        if(Profile.role() == 1){
            for (Project model : projects) items.add(newItem(V.PROJECT, model));
        }
        else if (Profile.role() == 2){
            for (Project model : projects) items.add(newItem(V.SITE, model));
        }
        else {
            throw new ArithmeticException("Fatal");
        }

        items.add(newItem(V.TOTAL,"TOTAL"));
        return items;
    }

    public boolean search(String name) {
        return name != null && projects.contains(new Project(name));
    }

}
