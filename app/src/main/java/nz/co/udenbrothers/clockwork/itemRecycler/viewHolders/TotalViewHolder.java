package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.view.View;
import android.widget.TextView;
import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.dao.ShiftDAO;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.tools.ShiftRecord;

public class TotalViewHolder extends ItemHolder {

    private TextView title, yesterday, week, month;

    public TotalViewHolder(View v) {
        super(v);
        title =  (TextView) v.findViewById(R.id.cardTitle);
        yesterday =  (TextView) v.findViewById(R.id.yesterdayHour);
        week =  (TextView) v.findViewById(R.id.weekHour);
        month =  (TextView) v.findViewById(R.id.monthHour);
    }

    public void init(Item item){
        title.setText(item.des);
        ShiftDAO shiftDAO = new ShiftDAO(item.context);
        ShiftRecord shiftRecord = new ShiftRecord(item.context, shiftDAO.getAll());
        yesterday.setText(shiftRecord.beforeTotal(1));
        week.setText(shiftRecord.beforeTotal(7));
        month.setText(shiftRecord.beforeTotal(30));
    }
}
