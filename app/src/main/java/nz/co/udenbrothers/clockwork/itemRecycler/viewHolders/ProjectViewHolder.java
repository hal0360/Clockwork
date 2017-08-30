package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.StaffActivity;
import nz.co.udenbrothers.clockwork.StaffHistoryActivity;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.models.Project;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.sql_stuff.SQL;
import nz.co.udenbrothers.clockwork.temps.Act;
import nz.co.udenbrothers.clockwork.temps.TEMP;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.MyDate;


public class ProjectViewHolder extends ItemHolder{

    private boolean isViewExpanded = false;
    private TextView title, yesterday, week, month, records;
    private View activeDot;

    public ProjectViewHolder(CollectionView cv) {
        super(cv, R.layout.site_card_layout);

        title = findView(R.id.cardTitle);
        yesterday = findView(R.id.yesterdayHour);
        week = findView(R.id.weekHour);
        month = findView(R.id.monthHour);
        records = findView(R.id.recordTxt);
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
        List<Shift> shifts = SQL.get(Shift.class, s -> s.qrCodeIdentifier.equals(project.qrCodeIdentifier));
        int counts = shifts.size();
        if(counts > 0){
            Date startdate = MyDate.strToDate(shifts.get(counts - 1).shiftTimeStartOnUtc);
            Date endDate = MyDate.strToDate(shifts.get(counts - 1).shiftTimeEndOnUtc);
            yesterday.setText(MyDate.dateToStr(startdate, "HH:mm"));
            week.setText(MyDate.dateToStr(endDate, "HH:mm"));
            month.setText(MyDate.gethourMin(endDate.getTime() - startdate.getTime()));
        }
        records.setText(counts + " records in this project");

        if(Act.current() != null && Act.current().equals(project.qrCodeIdentifier)){
            activeDot.setBackgroundResource( R.drawable.green_dot );
        }
        else {
            activeDot.setBackgroundResource( R.drawable.red_dot );
        }

        clicked(R.id.moreInfoButton, () -> {
            TEMP.QR = project.qrCodeIdentifier;
            staffActivity.navigate(StaffHistoryActivity.class);
        });

        clicked(R.id.deleteButton, () -> {
            project.delete();
            delete();
        });
    }

    @Override
    public void cleanUp() {}
}
