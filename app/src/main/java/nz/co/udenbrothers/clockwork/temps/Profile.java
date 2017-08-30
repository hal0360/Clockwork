package nz.co.udenbrothers.clockwork.temps;

import nz.co.udenbrothers.clockwork.App;


public class Profile {

    public static String firstName(){
        return App.getStr("firstName");
    }

    public static String lastName(){
        return App.getStr("lastName");
    }

    public static void firstName(String n){
        App.putStr("firstName", n);
    }

    public static void lastName(String n){
        App.putStr("lastName", n);
    }

    public static int role(){
        return App.getInt("role");
    }

    public static void role(int r){
        App.putInt("role", r);
    }

    public static void userID(String s){
        App.putStr("userID", s);
    }

    public static String userID(){
        return App.getStr("userID");
    }

    public static void token(String s){
        App.putStr("token", s);
    }

    public static String token(){
        return App.getStr("token");
    }

    public static void email(String s){
        App.putStr("email", s);
    }

    public static String email(){
        return App.getStr("email");
    }

    public static void company(String s){
        App.putStr("company", s);
    }

    public static String company(){
        return App.getStr("company");
    }

    public static void bussID(String s){
        App.putStr("bussID", s);
    }

    public static String bussID(){
        return App.getStr("bussID");
    }

    public static void logout(){
        token(null);
        userID(null);
        role(0);
    }
}
