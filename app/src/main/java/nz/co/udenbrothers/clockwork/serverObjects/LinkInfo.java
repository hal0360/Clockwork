package nz.co.udenbrothers.clockwork.serverObjects;

/**
 * Created by user on 10/08/2017.
 */

public class LinkInfo extends ServObj {
    public String UserId;
    public int Role;

    public LinkInfo(String uid){
        UserId = uid;
        Role = 2;
    }

}
