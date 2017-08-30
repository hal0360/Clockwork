package nz.co.udenbrothers.clockwork.models;

public class Project extends Model{

    public String businessId;
    public String qrCodeIdentifier;
    public String companyName;

    public Project(String name){
        qrCodeIdentifier = name;
    }

    @Override
    public boolean equals (Object o) {

        boolean sameSame = false;
        if (o != null && o instanceof Project)
        {
            Project another = (Project) o;
            sameSame = qrCodeIdentifier.equals(another.qrCodeIdentifier);
        }
        return sameSame;

    }

    public String name(){
        return qrCodeIdentifier;
    }



}
