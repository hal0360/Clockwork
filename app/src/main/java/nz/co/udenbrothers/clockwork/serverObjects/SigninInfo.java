package nz.co.udenbrothers.clockwork.serverObjects;

/**
 * Created by user on 29/08/2017.
 */

public class SigninInfo extends ServObj{
    public String FirstName;
    public String LastName;
    public String Email;
    public String Password;
    public String PasswordToConfirm;

    public SigninInfo(String name, String name2, String emal, String pass){
        FirstName = name;
        LastName = name2;
        Email = emal;
        Password = pass;
        PasswordToConfirm = pass;
    }
}
