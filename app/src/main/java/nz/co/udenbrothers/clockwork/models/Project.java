package nz.co.udenbrothers.clockwork.models;

import java.util.Objects;

/**
 * Created by user on 14/06/2017.
 */

public class Project extends Model{

    public int businessId;
    public String qrCodeIdentifier;
    public float totalHoursToday = 0.0f;
    public float totalHoursThisWeek = 0.0f;
    public float totalHoursThisMonth = 0.0f;
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
}
