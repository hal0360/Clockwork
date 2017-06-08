package nz.co.udenbrothers.clockwork.dao;

import android.content.Context;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.models.WorkSite;

public class WorkSiteDAO extends ModelDAO {

    protected void init(){
        table = "WorkSite";
        field("site_id", "int");
        field("worker_id", "int");
    }

    public WorkSiteDAO(Context context){
        super(context);
    }

    public void add(WorkSite workSite){
        key.put("site_name", workSite.site_id);
        key.put("worker_name", workSite.worker_id);
        workSite.id = save();
    }

    public ArrayList<WorkSite> getAll(){
        ArrayList<WorkSite> workSites = new ArrayList<>();
        load();
        while (cur.next()){
            WorkSite workSite = new WorkSite();
            workSite.id = cur.getLong("id");
            workSite.site_id = cur.getLong("site_id");
            workSite.worker_id = cur.getLong("worker_id");
            workSites.add(workSite);
        }
        return workSites;
    }

    public ArrayList<WorkSite> getBy(String ki, long val){
        ArrayList<WorkSite> workSites = new ArrayList<>();
        loadBy(ki, val);
        while (cur.next()){
            WorkSite workSite = new WorkSite();
            workSite.id = cur.getInt("id");
            workSite.site_id = cur.getInt("site_id");
            workSite.worker_id = cur.getInt("worker_id");
            workSites.add(workSite);
        }
        return workSites;
    }
}
