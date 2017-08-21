package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.StaffActivity;
import nz.co.udenbrothers.clockwork.StaffHistoryActivity;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.models.Project;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.MyDate;
import nz.co.udenbrothers.clockwork.tools.Pref;


public class SiteViewHolder extends ItemHolder{

    private boolean isViewExpanded = false;
    private TextView title, yesterday, week, month, records;
    private View activeDot;

    public SiteViewHolder(CollectionView cv) {
        super(cv, R.layout.site_card_layout);

        title =  (TextView) findView(R.id.cardTitle);
        yesterday =  (TextView) findView(R.id.yesterdayHour);
        week =  (TextView) findView(R.id.weekHour);
        month =  (TextView) findView(R.id.monthHour);
        records =  (TextView) findView(R.id.recordTxt);
        activeDot = findView(R.id.activeDot);

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

    @Override
    public void init(Item item){
        Project project = (Project) item.model;
        StaffActivity staffActivity = (StaffActivity) context;
        setHeight(Kit.dps(120));
        title.setText(project.qrCodeIdentifier);
        ArrayList<Shift> shifts = Shift.get(context, "qrCodeIdentifier", project.qrCodeIdentifier);
        int counts = shifts.size();
        if(counts > 0){
            Date startdate = MyDate.strToDate(shifts.get(counts - 1).shiftTimeStartOnUtc);
            Date endDate = MyDate.strToDate(shifts.get(counts - 1).shiftTimeEndOnUtc);
            yesterday.setText(MyDate.dateToStr(startdate, "HH:mm"));
            week.setText(MyDate.dateToStr(endDate, "HH:mm"));
            month.setText(MyDate.gethourMin(endDate.getTime() - startdate.getTime()));
        }
        records.setText(counts + " records in this project");
        Pref pref = new Pref(context);
        if(pref.getStr("currentProject").equals(project.qrCodeIdentifier)){
            activeDot.setBackgroundResource( R.drawable.green_dot );
        }
        else {
            activeDot.setBackgroundResource( R.drawable.red_dot );
        }

        clicked(R.id.moreInfoButton, () -> {
            pref.putStr("selectedProjectName", project.qrCodeIdentifier);
            staffActivity.navigate(StaffHistoryActivity.class);
        });

        clicked(R.id.deleteButton, () -> {
            project.delete(context);
            delete();
        });
    }

    @Override
    public void cleanUp() {}
}
