package nz.co.udenbrothers.clockwork.models;

public class Notice extends Model{

    public String title;
    public String message;

    public Notice(String ti, String mss){
        title = ti;
        message = mss;
    }

}