package nz.co.udenbrothers.clockwork.tools;


import com.google.gson.JsonObject;

public class Json {

    private JsonObject obj;

    public Json(){
        obj = new JsonObject();
    }

    public void put(String key, String val){
        obj.addProperty(key,val);
    }

    public void put(String key, int val){
        obj.addProperty(key,val);
    }

    public void put(String key, long val){
        obj.addProperty(key,val);
    }

    public void put(String key, float val){
        obj.addProperty(key,val);
    }

    public int getInt(String key){
        return obj.get(key).getAsInt();
    }

    public String getStr(String key){
        return obj.get(key).getAsString();
    }

    public long getLong(String key){
        return obj.get(key).getAsLong();
    }

    public long getFloat(String key){
        return obj.get(key).getAsLong();
    }
}
