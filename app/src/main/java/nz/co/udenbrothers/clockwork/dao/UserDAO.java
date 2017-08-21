package nz.co.udenbrothers.clockwork.dao;

import android.content.Context;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.models.User;

/**
 * Created by user on 10/07/2017.
 */

public class UserDAO extends ModelDAO {

    public UserDAO(Context context) {
        super(context);
    }

    @Override
    protected void init() {
        table = "Notice";
        field("userId", "text");
        field("firstName", "text");
        field("email", "text");
        field("lastName", "text");
        field("active", "int");
    }

    public void add(User user){
        key.put("userId", user.userId);
        key.put("firstName", user.firstName);
        key.put("email", user.email);
        key.put("lastName", user.lastName);
        key.put("active", user.active);
        user.id = save();
    }

    public ArrayList<User> getAll(){
        ArrayList<User> users = new ArrayList<>();
        load();
        while (cur.next()){
            User user = new User(cur.getStr("userId"), cur.getStr("firstName"), cur.getStr("lastName"), cur.getStr("email"));
            user.active = cur.getInt("active");
            user.id = cur.getInt("id");
            users.add(user);
        }
        return users;
    }

    public ArrayList<User> getBy(String ki, String val){
        ArrayList<User> users = new ArrayList<>();
        loadBy(ki, val);
        while (cur.next()){
            User user = new User(cur.getStr("userId"), cur.getStr("firstName"), cur.getStr("lastName"), cur.getStr("email"));
            user.active = cur.getInt("active");
            user.id = cur.getInt("id");
            users.add(user);
        }
        return users;
    }
}
