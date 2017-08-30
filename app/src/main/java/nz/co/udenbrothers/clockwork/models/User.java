package nz.co.udenbrothers.clockwork.models;

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

}
