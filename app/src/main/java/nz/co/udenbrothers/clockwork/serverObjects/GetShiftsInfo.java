package nz.co.udenbrothers.clockwork.serverObjects;

import java.util.ArrayList;

/**
 * Created by user on 17/08/2017.
 */

public class GetShiftsInfo extends ServObj{
    public String start = "1999-05-01";
    public String end = "2045-06-17";
    public ArrayList<String> userIds;
    public ArrayList<String> projectIds;
    public String max = "0";

    public GetShiftsInfo(String uid){
        userIds = new ArrayList<>();
        userIds.add(uid);
    }
}
