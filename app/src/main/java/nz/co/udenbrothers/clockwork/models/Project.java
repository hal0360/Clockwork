package nz.co.udenbrothers.clockwork.models;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.lang.reflect.Field;
import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.tools.Cur;

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

    public static boolean search(Context context, String name){
        Cur cur = load(context,"Project");
        while (cur.next()) if(name.equals(cur.getStr("qrCodeIdentifier"))) return true;
        return false;
    }

    public static ArrayList<Project> get(Context context){
        Cur cur = load(context,"Project");
        ArrayList<Project> projects = new ArrayList<>();
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
