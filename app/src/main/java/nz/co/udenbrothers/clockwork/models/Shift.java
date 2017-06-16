package nz.co.udenbrothers.clockwork.models;

/**
 * Created by user on 14/06/2017.
 */

public class Shift extends Model {


    public String userId;
    public String qrCodeIdentifier;
    public String shiftTimeStartOnUtc;
    public String shiftTimeEndOnUtc;
    public String comment = "";
    public int uploaded = 0;

    public Shift(String name, String start, String end, String uid){
        qrCodeIdentifier = name;
        shiftTimeStartOnUtc = start;
        shiftTimeEndOnUtc = end;
        userId = uid;
    }
}