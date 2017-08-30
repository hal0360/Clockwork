package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import nz.co.udenbrothers.clockwork.BossActivity;
import nz.co.udenbrothers.clockwork.BossMyTeamActivity;
import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.models.Project;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.sql_stuff.SQL;
import nz.co.udenbrothers.clockwork.temps.Act;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.MyDate;


public class SiteViewHolder extends ItemHolder{

    private boolean isViewExpanded = false;
    private TextView title, yesterday, week, month, records;
    private View activeDot;

    public SiteViewHolder(CollectionView cv) {
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

        BossActivity bossActivity = (BossActivity) context;

        setHeight(Kit.dps(120));
        title.setText(project.qrCodeIdentifier);
        List<Shift> shifts = SQL.get(Shift.class);
        int counts = shifts.size();
        if(counts > 0){
            Date startdate = MyDate.strToDate(shifts.get(counts - 1).shiftTimeStartOnUtc);
            Date endDate = MyDate.strToDate(shifts.get(counts - 1).shiftTimeEndOnUtc);
            yesterday.setText(MyDate.dateToStr(startdate, "HH:mm"));
            week.setText(MyDate.dateToStr(endDate, "HH:mm"));
            month.setText(MyDate.gethourMin(endDate.getTime() - startdate.getTime()));
        }
        records.setText("0 staff working  in this project");

        if(Act.current() != null && Act.current().equals(project.qrCodeIdentifier)){
            activeDot.setBackgroundResource( R.drawable.green_dot );
        }
        else {
            activeDot.setBackgroundResource( R.drawable.red_dot );
        }

        clicked(R.id.moreInfoButton, () -> {
           // pref.putStr("selectedProjectName", project.qrCodeIdentifier);
            bossActivity.navigate(BossMyTeamActivity.class);
        });

        clicked(R.id.deleteButton, () -> {
            project.delete();
            delete();
        });
    }

    @Override
    public void cleanUp() {}
}
