package nz.co.udenbrothers.clockwork.serverObjects;

import com.google.gson.Gson;

/**
 * Created by user on 08/06/2017.
 */

public class Profile extends ServObj{

    public String FirstName;
    public String LastName;
    public String Email;
    public String Password;
    public String PasswordToConfirm;

    public Profile(String name, String name2, String emal, String pass){
        FirstName = name;
        LastName = name2;
        Email = emal;
        Password = pass;
        PasswordToConfirm = pass;
    }

    public static Profile fromJsom(String text){
        Gson gson = new Gson();
        try {
            return gson.fromJson(text, Profile.class);
        } catch (Exception e) {
            return null;
        }
    }
}
