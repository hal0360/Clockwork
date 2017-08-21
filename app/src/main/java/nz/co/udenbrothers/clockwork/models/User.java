package nz.co.udenbrothers.clockwork.models;

import android.content.Context;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.tools.Cur;

/**
 * Created by user on 14/06/2017.
 */

public class User extends Model {

    public String userId;
    public String firstName;
    public String lastName;
    public String email;
    public int active;

    public User (String uid, String fn, String ln, String emai){
        userId = uid;
        firstName = fn;
        lastName = ln;
        email = emai;
        active = 0;
    }

    public static ArrayList<User> get(Context context){
        Cur cur = load(context,"Shift");
        ArrayList<User> users = new ArrayList<>();
        while (cur.next()){
            User user = new User(cur.getStr("userId"), cur.getStr("firstName"), cur.getStr("lastName"), cur.getStr("email"));
            user.active = cur.getInt("active");
            user.id = cur.getInt("id");
            users.add(user);
        }
        return users;
    }

    public static ArrayList<User> get(Context context, String key, String val){
        Cur cur = load(context,"Shift", key, val);
        ArrayList<User> users = new ArrayList<>();
        while (cur.next()){
            User user = new User(cur.getStr("userId"), cur.getStr("firstName"), cur.getStr("lastName"), cur.getStr("email"));
            user.active = cur.getInt("active");
            user.id = cur.getInt("id");
            users.add(user);
        }
        return users;
    }
}
