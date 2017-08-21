package nz.co.udenbrothers.clockwork.dao;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonParseException;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.models.Project;


public class ProjectDAO extends ModelDAO {

    protected void init(){
        table = "Project";
        field("qrCodeIdentifier", "text");
        field("businessId", "text");
        field("companyName", "text");
    }

    public ProjectDAO(Context context){
        super(context);
    }

    public void add(Project project){
        if(search(project.qrCodeIdentifier)) return;
        key.put("qrCodeIdentifier", project.qrCodeIdentifier);
        key.put("businessId", project.businessId);
        key.put("companyName", project.companyName);
        project.id = save();
    }

    public void addFromJson(String json, Class clsssss){
        try {
            Project[] projects = gson.fromJson(json, Project[].class);
            for (Project project : projects) add(project);
        } catch (JsonParseException e) { Log.e("Parse",e+""); }

    }

    public boolean search(String name){
        load();
        while (cur.next()) if(name.equals(cur.getStr("qrCodeIdentifier"))) return true;
        return false;
    }

    public ArrayList<Project> getAll(){
        ArrayList<Project> projects = new ArrayList<>();
        load();
        while (cur.next()){
            Project project = new Project(cur.getStr("qrCodeIdentifier"));
            project.id = cur.getInt("id");
            project.businessId = cur.getStr("businessId");
            project.companyName = cur.getStr("companyName");
            projects.add(project);
        }
        return projects;
    }
}
