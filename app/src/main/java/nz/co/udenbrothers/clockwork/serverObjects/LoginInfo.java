package nz.co.udenbrothers.clockwork.serverObjects;

import com.google.gson.Gson;

import nz.co.udenbrothers.clockwork.models.Shift;

/**
 * Created by user on 13/06/2017.
 */

public class LoginInfo extends ServObj{

    public String apiToken;
    public String firstName;
    public String lastName;
    public String userId;

    public LoginInfo(String api, String fn, String ln){
        apiToken = api;
        firstName = fn;
        lastName = ln;
    }

    public static LoginInfo fromJsom(String text){
        Gson gson = new Gson();
        try {
            return gson.fromJson(text, LoginInfo.class);
        } catch (Exception e) {
            return null;
        }
    }
}
