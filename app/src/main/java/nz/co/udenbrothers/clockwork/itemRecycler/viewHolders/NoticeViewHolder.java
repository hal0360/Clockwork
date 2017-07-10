package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.view.View;
import android.widget.TextView;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.items.NoticeItem;
import nz.co.udenbrothers.clockwork.models.Notice;

/**
 * Created by user on 04/07/2017.
 */

public class NoticeViewHolder extends ItemHolder {

    private TextView title, message;

    public NoticeViewHolder(View v) {
        super(v);
        title = (TextView) v.findViewById(R.id.title);
        message = (TextView) v.findViewById(R.id.message);
    }

    @Override
    public void init(Item item) {
        NoticeItem noticeItem = (NoticeItem)item;
        Notice notice = noticeItem.notice;
        title.setText(notice.title);
        message.setText(notice.message);
    }
}
