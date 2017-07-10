package nz.co.udenbrothers.clockwork.models;

/**
 * Created by user on 04/07/2017.
 */

public class Notice extends Model{

    public String title;
    public String message;

    public Notice(String ti, String mss){
title = ti;
        message = mss;
    }

}