package nz.co.udenbrothers.clockwork.serverObjects;

import java.util.ArrayList;

/**
 * Created by user on 18/06/2017.
 */

public class ExportInfo extends ServObj {

    public String start;
    public String email;
    public String end = "2025-06-17";
    public ArrayList<String> userIds;
    public ArrayList<String> projectIds;
    public String max = "0";

    public ExportInfo(String uid, String mail, String started){
        userIds = new ArrayList<>();
        userIds.add(uid);
        email = mail;
        start = started;
    }
}
