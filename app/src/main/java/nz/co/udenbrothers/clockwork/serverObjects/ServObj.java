package nz.co.udenbrothers.clockwork.serverObjects;

import com.google.gson.Gson;

/**
 * Created by user on 08/06/2017.
 */

public abstract class ServObj {

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
