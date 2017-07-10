package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.abstractions.RecycleCallback;
import nz.co.udenbrothers.clockwork.dao.ShiftDAO;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.items.ProjectItem;
import nz.co.udenbrothers.clockwork.models.Project;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.MyDate;
import nz.co.udenbrothers.clockwork.tools.Pref;


public class SiteViewHolder extends ItemHolder{

    private boolean isViewExpanded = false;
    private TextView title, yesterday, week, month, records;
    private View activeDot;
    private RecycleCallback recycleCallback;
    private String projectName;

    public SiteViewHolder(View v) {
        super(v);
        title =  (TextView) v.findViewById(R.id.cardTitle);
        yesterday =  (TextView) v.findViewById(R.id.yesterdayHour);
        week =  (TextView) v.findViewById(R.id.weekHour);
        month =  (TextView) v.findViewById(R.id.monthHour);
        records =  (TextView) v.findViewById(R.id.recordTxt);
        activeDot = v.findViewById(R.id.activeDot);

        clicked(v.findViewById(R.id.moreInfoButton), ()-> recycleCallback.moreInfo(projectName));

        clicked(v.findViewById(R.id.deleteButton), () -> recycleCallback.deleteProject(projectName, getAdapterPosition()));

        clicked(card, ()->{
            if(isViewExpanded){
                expandView(Kit.dps(120));
                isViewExpanded = false;
            }
            else {
                expandView(Kit.dps(215));
                isViewExpanded = true;
            }
        });
    }

    public void init(Item item){
        ProjectItem projectItem = (ProjectItem) item;
        Project project = projectItem.project;
        recycleCallback = (RecycleCallback) projectItem.context;
        setHeight(Kit.dps(120));
        title.setText(project.qrCodeIdentifier);
        projectName = project.qrCodeIdentifier;
        ShiftDAO shiftDAO = new ShiftDAO(projectItem.context);
        ArrayList<Shift> shifts = shiftDAO.getBy("qrCodeIdentifier",project.qrCodeIdentifier);
        int counts = shifts.size();
        if(counts > 0){
            Date startdate = MyDate.strToDate(shifts.get(counts - 1).shiftTimeStartOnUtc);
            Date endDate = MyDate.strToDate(shifts.get(counts - 1).shiftTimeEndOnUtc);
            yesterday.setText(MyDate.dateToStr(startdate, "HH:mm"));
            week.setText(MyDate.dateToStr(endDate, "HH:mm"));
            month.setText(MyDate.gethourMin(endDate.getTime() - startdate.getTime()));
        }
        records.setText(counts + " records in this project");
        Pref pref = new Pref(projectItem.context);
        if(pref.getStr("currentProject").equals(project.qrCodeIdentifier)){
            activeDot.setBackgroundResource( R.drawable.green_dot );
        }
        else {
            activeDot.setBackgroundResource( R.drawable.red_dot );
        }
    }
}
