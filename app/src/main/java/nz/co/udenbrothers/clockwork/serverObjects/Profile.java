package nz.co.udenbrothers.clockwork.serverObjects;

/**
 * Created by user on 08/06/2017.
 */

public class Profile extends ServObj{

    public String FirstName;
    public String LastName = "N/A";
    public String Email;
    public String Password;
    public String PasswordToConfirm;

    public Profile(String name, String emal, String pass){
        FirstName = name;
        Email = emal;
        Password = pass;
        PasswordToConfirm = pass;
    }
}
