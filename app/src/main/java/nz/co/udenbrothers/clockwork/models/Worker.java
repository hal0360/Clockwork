package nz.co.udenbrothers.clockwork.models;

/**
 * Created by user on 05/06/2017.
 */

public class Worker extends Model {

    public String name = "N/A";
    public String email = "N/A";

    @Override
    public boolean equals (Object object) {
        boolean same = false;
        if (object != null && object instanceof Site)
        {
            Site other = (Site) object;
            same = this.name.equals(other.name);
        }
        return same;
    }
}
