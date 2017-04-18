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
            site.name = cur.getStr("name");
            site.owner = cur.getStr("owner");
            sites.add(site);
        }
        return sites;
    }

    public ArrayList<Site> getAllTest(){
        ArrayList<Site> list = new ArrayList<>();
        int iii = 0;
        while (iii < 10){
            iii ++;
            Site site = new Site();
            site.name = "test " + iii;
            site.owner = "";
            list.add(site);
        }
        return list;
    }
}
