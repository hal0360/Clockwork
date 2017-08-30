package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.view.View;
import android.widget.TextView;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.tools.Kit;

public class UserViewHolder extends ItemHolder {

    private boolean isViewExpanded = false;
    private TextView title, yesterday, week, month, records;
    private View activeDot;
    private String uid;

    public UserViewHolder(CollectionView cv) {
        super(cv, R.layout.site_card_layout);

        title =  (TextView) findView(R.id.cardTitle);
        yesterday =  (TextView) findView(R.id.yesterdayHour);
        week =  (TextView) findView(R.id.weekHour);
        month =  (TextView) findView(R.id.monthHour);
        records =  (TextView) findView(R.id.recordTxt);
        activeDot = findView(R.id.activeDot);

      //  clicked(R.id.moreInfoButton, ()-> recycleCallback.moreInfo(uid));

       // clicked(v.findViewById(R.id.deleteButton), () -> recycleCallback.delete(uid, getAdapterPosition()));

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
    public void init(Item item) {

        /*
        User user = (User) item.model;
        recycleCallback = (RecycleCallback) item.context;
        setHeight(Kit.dps(120));
        title.setText(user.firstName + " " + user.lastName);
        uid = user.userId;

        ArrayList<Shift> shifts = Shift.get(item.context, "userId", user.userId);
        int counts = shifts.size();
        if(counts > 0){
            Date startdate = MyDate.strToDate(shifts.get(counts - 1).shiftTimeStartOnUtc);
            Date endDate = MyDate.strToDate(shifts.get(counts - 1).shiftTimeEndOnUtc);
            yesterday.setText(MyDate.dateToStr(startdate, "HH:mm"));
            week.setText(MyDate.dateToStr(endDate, "HH:mm"));
            month.setText(MyDate.gethourMin(endDate.getTime() - startdate.getTime()));
        }
        records.setText(counts + " records in this user");
        */
    }

    @Override
    public void cleanUp() {}
}
