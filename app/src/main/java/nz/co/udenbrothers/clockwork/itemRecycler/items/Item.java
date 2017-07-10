package nz.co.udenbrothers.clockwork.itemRecycler.items;

import android.content.Context;

import nz.co.udenbrothers.clockwork.models.Model;


public class Item{

    public int type;
    public String des = "";
    public Context context;
    public Model model;

    public Item(int type, Context context){
        this.type = type;
        this.context = context;
    }

}
