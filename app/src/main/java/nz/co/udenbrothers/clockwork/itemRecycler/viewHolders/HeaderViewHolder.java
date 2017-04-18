package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.view.View;
import android.widget.TextView;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.itemRecycler.items.HeaderItem;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.tools.Kit;

/**
 * Created by user on 09/04/2017.
 */

public class HeaderViewHolder extends ItemHolder {

    private TextView dateHeader, totalhour;

    @Override
    public void init(Item item) {
        HeaderItem headerItem = (HeaderItem) item;
        dateHeader.setText(headerItem.des);
        totalhour.setText(Kit.gethourMin(headerItem.total));
    }

    public HeaderViewHolder(View v) {
        super(v);
        dateHeader = (TextView) v.findViewById(R.id.dateHeader);
        totalhour = (TextView) v.findViewById(R.id.totalHourMin);
    }
}
