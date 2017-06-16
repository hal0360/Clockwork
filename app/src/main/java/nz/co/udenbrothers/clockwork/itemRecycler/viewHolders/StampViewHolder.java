package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.items.ShiftItem;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.tools.Kit;

/**
 * Created by user on 07/04/2017.
 */

public class StampViewHolder extends ItemHolder{

    private View commentDot;
    private RelativeLayout commentCircle;
    private boolean isViewExpanded = false;
    private TextView startTimeTxt ,endTimeTxt, workedTimeTxt, siteNameTxt, commentTxt;

    public StampViewHolder(View v) {
        super(v);
        commentDot = v.findViewById(R.id.commentDot);
        commentCircle = (RelativeLayout) v.findViewById(R.id.commentCircle);
        siteNameTxt = (TextView) v.findViewById(R.id.siteNameTxt);
        endTimeTxt = (TextView) v.findViewById(R.id.endTimeTxt);
        workedTimeTxt = (TextView) v.findViewById(R.id.workedHourTxt);
        startTimeTxt = (TextView) v.findViewById(R.id.startTimeTxt);
        commentTxt = (TextView) v.findViewById(R.id.commentTxt);
    }

    public void init(Item item) {
        ShiftItem shiftItem = (ShiftItem) item;
        Shift shift = shiftItem.shift;
        siteNameTxt.setText(shift.qrCodeIdentifier);
        Date startdate = Kit.strToDate(shift.shiftTimeStartOnUtc);
        Date endDate = Kit.strToDate(shift.shiftTimeEndOnUtc);
        startTimeTxt.setText(Kit.dateToStr(startdate, "HH:mm"));
        endTimeTxt.setText(Kit.dateToStr(endDate, "HH:mm"));

        workedTimeTxt.setText(Kit.gethourMin(endDate.getTime() - startdate.getTime()));

        commentTxt.setText(shift.comment);
        card.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int totalHeight = card.getMeasuredHeight();
        setHeight(Kit.dps(40));
        if(shift.comment.equals("")){
            commentCircle.setVisibility(View.INVISIBLE);
        }
        else {
            commentCircle.setVisibility(View.VISIBLE);
            commentDot.setVisibility(View.INVISIBLE);
        }

        clicked(card, ()->{
            if(shift.comment.equals("")){
                return;
            }

            if(isViewExpanded){
                commentDot.setVisibility(View.INVISIBLE);
                expandView(Kit.dps(40));
                isViewExpanded = false;
            }
            else {
                commentDot.setVisibility(View.VISIBLE);
                expandView(totalHeight);
                isViewExpanded = true;
            }
        });
    }
}
