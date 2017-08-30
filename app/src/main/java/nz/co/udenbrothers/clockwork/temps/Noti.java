package nz.co.udenbrothers.clockwork.temps;

import nz.co.udenbrothers.clockwork.App;

/**
 * Created by user on 29/08/2017.
 */

public class Noti {

    public static int COhour(){
        return App.getInt("COhour");
    }

    public static void COhour(int r){
        App.putInt("COhour", r);
    }

    public static int CIhour(){
        return App.getInt("CIhour");
    }

    public static void CIhour(int r){
        App.putInt("CIhour", r);
    }

    public static int COmin(){
        return App.getInt("COmin");
    }

    public static void COmin(int r){
        App.putInt("COmin", r);
    }

    public static int CImin(){
        return App.getInt("CImin");
    }

    public static void CImin(int r){
        App.putInt("CImin", r);
    }

}
