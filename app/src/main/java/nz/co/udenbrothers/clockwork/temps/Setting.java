package nz.co.udenbrothers.clockwork.temps;

import nz.co.udenbrothers.clockwork.App;

/**
 * Created by user on 29/08/2017.
 */

public class Setting {

    public static boolean wifi(){
        return App.getBool("wifi", false);
    }

    public static void wifi(boolean b){
        App.putBool("wifi", b);
    }

    public static boolean notification(){
        return App.getBool("notification", true);
    }

    public static void notification(boolean b){
        App.putBool("notification", b);
    }

    public static boolean reminder(){
        return App.getBool("reminder", false);
    }

    public static void reminder(boolean b){
        App.putBool("reminder", b);
    }

    public static boolean wedge(){
        return App.getBool("wedge", true);
    }

    public static void wedge(boolean b){
        App.putBool("wedge", b);
    }
}
