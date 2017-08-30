package nz.co.udenbrothers.clockwork.tools;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.List;

import nz.co.udenbrothers.clockwork.sql_stuff.ListOfJson;

/**
 * Created by user on 28/08/2017.
 */

public class Json {

    public static <T> List<T> parse(String js, Class<T> tClass){
        Gson gson = new Gson();
        try {
            return gson.fromJson(js, new ListOfJson<>(tClass));
        }
        catch (JsonParseException e) {
            Log.e("Error json from string",e+"");
            return new ArrayList<>();
        }
    }

    public static <T> T from(String js, Class<T> tClass){
        Gson gson = new Gson();
        try {
            return gson.fromJson(js, tClass);
        }
        catch (JsonParseException e) {
            Log.e("Error json from string",e+"");
            return null;
        }
    }

    public static <T> String to(T t){
        Gson gson = new Gson();
        return gson.toJson(t);
    }

    public static <T> String to(List<T> ts){
        Gson gson = new Gson();
        return gson.toJson(ts);
    }
}
