package nz.co.udenbrothers.clockwork.dao;

import android.content.Context;
import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.models.Site;

public class SiteDAO extends ModelDAO {

    protected void init(){
        table = "Site";
        field("owner", "text");
        field("name", "text");
    }

    public SiteDAO(Context context){
        super(context);
    }

    public void add(Site site){
        key.put("name", site.name);
        key.put("owner", site.owner);
        site.id = save();
    }

    public ArrayList<Site> getAll(){
        ArrayList<Site> sites = new ArrayList<>();
        load();
        while (cur.next()){
            Site site = new Site();
            site.id = cur.getLong("id");
            site.name = cur.getStr("name");
            site.owner = cur.getStr("owner");
            sites.add(site);
        }
        return sites;
    }
}
