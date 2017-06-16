package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.view.View;
import android.widget.TextView;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.dao.ShiftDAO;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.items.ProjectItem;
import nz.co.udenbrothers.clockwork.models.Project;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.Pref;
import nz.co.udenbrothers.clockwork.tools.ShiftRecord;


public class SiteViewHolder extends ItemHolder{

    private boolean isViewExpanded = false;
    private TextView title, yesterday, week, month, records;
    private View activeDot;

    public SiteViewHolder(View v) {
        super(v);
        title =  (TextView) v.findViewById(R.id.cardTitle);
        yesterday =  (TextView) v.findViewById(R.id.yesterdayHour);
        week =  (TextView) v.findViewById(R.id.weekHour);
        month =  (TextView) v.findViewById(R.id.monthHour);
        records =  (TextView) v.findViewById(R.id.recordTxt);
        activeDot = v.findViewById(R.id.activeDot);

        clicked(v.findViewById(R.id.moreInfoButton), ()->{});

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
        setHeight(Kit.dps(120));
        title.setText(project.qrCodeIdentifier);
        ShiftDAO shiftDAO = new ShiftDAO(projectItem.context);
        ShiftRecord shiftRecord = new ShiftRecord(projectItem.context, shiftDAO.getBy("qrCodeIdentifier",project.qrCodeIdentifier));
        yesterday.setText(shiftRecord.beforeTotal(1));
        week.setText(shiftRecord.beforeTotal(7));
        month.setText(shiftRecord.beforeTotal(30));
        records.setText(shiftRecord.getCount() + " records in this project");
        Pref pref = new Pref(projectItem.context);
        if(pref.getStr("currentProject").equals(project.qrCodeIdentifier)){
            activeDot.setBackgroundResource( R.drawable.green_dot );
        }
        else {
            activeDot.setBackgroundResource( R.drawable.red_dot );
        }
    }
}
