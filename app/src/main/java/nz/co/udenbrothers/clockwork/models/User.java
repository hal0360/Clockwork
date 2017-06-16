package nz.co.udenbrothers.clockwork.models;

/**
 * Created by user on 14/06/2017.
 */

public class User extends Model {

    public String UserId;
    public String FirstName;
    public String LastName;
    public String Email;

    public User (String uid, String fn, String ln, String email){
        UserId = uid;
        FirstName = fn;
        LastName = ln;
        Email = email;
    }
}
