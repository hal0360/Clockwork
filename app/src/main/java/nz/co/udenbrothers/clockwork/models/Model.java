package nz.co.udenbrothers.clockwork.models;

import com.google.gson.Gson;

public abstract class Model{

    public long id = -1;

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
