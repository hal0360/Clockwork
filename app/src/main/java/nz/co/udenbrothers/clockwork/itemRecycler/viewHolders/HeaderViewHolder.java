package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.widget.TextView;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.items.HeaderItem;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.tools.MyDate;


public class HeaderViewHolder extends ItemHolder {

    private TextView dateHeader, totalhour;

    @Override
    public void init(Item item) {
        HeaderItem headerItem = (HeaderItem) item;
        dateHeader.setText(headerItem.des);
        totalhour.setText(MyDate.gethourMin(headerItem.total));
    }

    public HeaderViewHolder(CollectionView cv) {
        super(cv, R.layout.header_card_layout);

        dateHeader = (TextView) findView(R.id.dateHeader);
        totalhour = (TextView) findView(R.id.totalHourMin);
    }

    @Override
    public void cleanUp() {}
}
