package nz.co.udenbrothers.clockwork.itemRecycler.itemFactories;

import android.content.Context;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.dao.UserDAO;
import nz.co.udenbrothers.clockwork.global.Type;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.models.Model;
import nz.co.udenbrothers.clockwork.models.User;

/**
 * Created by user on 10/07/2017.
 */

public class UserItemMaker extends ItemMaker{


    public UserItemMaker(Context context) {
        super(context);
    }

    public ArrayList<Item> fetch(){
        UserDAO userDAO = new UserDAO(context);
        ArrayList<User> users = userDAO.getAll();
        ArrayList<Item> items = new ArrayList<>();
        for (User model : users){
            Item item = new Item(Type.USER,context);
            item.model = model;
            items.add(item);
        }
        return items;
    }
}
