package nz.co.udenbrothers.clockwork.itemRecycler.items;

import android.content.Context;

import nz.co.udenbrothers.clockwork.global.Type;
import nz.co.udenbrothers.clockwork.models.Project;

/**
 * Created by user on 15/06/2017.
 */

public class ProjectItem extends Item {
    public Project project;
    public boolean active = false;

    public ProjectItem(int type, Project project, Context context) {
        super(type, context);
        if(type != Type.PROJECT){
            throw new IllegalArgumentException("INVALID ITEM TYPE");
        }
        this.project =project;
    }
}