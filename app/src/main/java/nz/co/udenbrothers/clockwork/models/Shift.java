package nz.co.udenbrothers.clockwork.models;



public class Shift extends Model {

    public String userId;
    public String qrCodeIdentifier;
    public String shiftTimeStartOnUtc;
    public String shiftTimeEndOnUtc;
    public String comment = "";
    public int uploaded = 0;
    public int stopped = 0;

    public Shift(String name, String start, String end, String uid){
        qrCodeIdentifier = name;
        shiftTimeStartOnUtc = start;
        shiftTimeEndOnUtc = end;
        userId = uid;
    }
}
