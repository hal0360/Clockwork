package nz.co.udenbrothers.clockwork.temps;

import nz.co.udenbrothers.clockwork.App;

/**
 * Created by user on 29/08/2017.
 */

public class Act {
    public static String current(){
        return App.getStr("current");
    }

    public static String startTime(){
        return App.getStr("startTime");
    }

    public static void current(String n){
        App.putStr("current", n);
    }

    public static void startTime(String n){
        App.putStr("startTime", n);
    }
}
