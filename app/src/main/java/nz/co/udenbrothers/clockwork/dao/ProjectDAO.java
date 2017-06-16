package nz.co.udenbrothers.clockwork.dao;

import android.content.Context;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.models.Project;

/**
 * Created by user on 14/06/2017.
 */

public class ProjectDAO extends ModelDAO {

    protected void init(){
        table = "Project";
        field("qrCodeIdentifier", "text");
        field("totalHoursToday", "float");
        field("totalHoursThisWeek", "float");
        field("totalHoursThisMonth", "float");
        field("businessId", "int");
        field("companyName", "text");
    }

    public ProjectDAO(Context context){
        super(context);
    }

    public void add(Project project){
        key.put("qrCodeIdentifier", project.qrCodeIdentifier);
        key.put("totalHoursToday", project.totalHoursToday);
        key.put("totalHoursThisWeek", project.totalHoursThisWeek);
        key.put("totalHoursThisMonth", project.totalHoursThisMonth);
        key.put("companyName", project.companyName);
        key.put("businessId", project.businessId);
        project.id = save();
    }

    public ArrayList<Project> getAll(){
        ArrayList<Project> projects = new ArrayList<>();
        load();
        while (cur.next()){
            Project project = new Project(cur.getStr("qrCodeIdentifier"));
            project.id = cur.getLong("id");
            project.totalHoursThisMonth = cur.getFloat("totalHoursThisMonth");
            project.totalHoursThisWeek = cur.getFloat("totalHoursThisWeek");
            project.totalHoursToday = cur.getFloat("totalHoursToday");
            project.companyName = cur.getStr("companyName");
            project.businessId = cur.getInt("businessId");
            projects.add(project);
        }
        return projects;
    }
}
