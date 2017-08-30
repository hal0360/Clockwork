package nz.co.udenbrothers.clockwork.serverObjects;

/**
 * Created by user on 13/06/2017.
 */

public class LoginInfo extends ServObj{

    public String apiToken;
    public String firstName;
    public String lastName;
    public String userId;
    public int userRoleId;

    public LoginInfo(String api, String fn, String ln){
        apiToken = api;
        firstName = fn;
        lastName = ln;
    }
}
