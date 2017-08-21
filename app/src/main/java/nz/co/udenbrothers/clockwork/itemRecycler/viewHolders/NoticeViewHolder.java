package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.widget.TextView;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.models.Notice;


public class NoticeViewHolder extends ItemHolder {

    private TextView title, message;

    public NoticeViewHolder(CollectionView cv) {
        super(cv, R.layout.notice_card_layout);

        title = (TextView) findView(R.id.title);
        message = (TextView) findView(R.id.message);
    }

    @Override
    public void init(Item item) {
        Notice notice = (Notice) item.model;
        title.setText(notice.title);
        message.setText(notice.message);
    }

    @Override
    public void cleanUp() {}
}
