package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.dao.StampDAO;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.tools.Kit;

public class TotalViewHolder extends ItemHolder {

    private TextView title, yesterday, week, month;;

    public TotalViewHolder(View v) {
        super(v);
        title =  (TextView) v.findViewById(R.id.cardTitle);
        yesterday =  (TextView) v.findViewById(R.id.yesterdayHour);
        week =  (TextView) v.findViewById(R.id.weekHour);
        month =  (TextView) v.findViewById(R.id.monthHour);

    }

    public void init(Item item){
        title.setText(item.des);

        StampDAO stampDAO = new StampDAO(item.context);
        long yseterdayTotal = stampDAO.getBeforeTotal(1);
        yesterday.setText(Kit.gethourMin(yseterdayTotal));

        long weekTotal = stampDAO.getBeforeTotal(7);
        week.setText(Kit.gethourMin(weekTotal));

        long monthTotal = stampDAO.getBeforeTotal(30);
        month.setText(Kit.gethourMin(monthTotal));
    }
}
