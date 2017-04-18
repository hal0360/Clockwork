package nz.co.udenbrothers.clockwork.models;

public class Site extends Model {

    public String owner = "N/A";
    public String name = "N/A";

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
