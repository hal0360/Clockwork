package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.items.ShiftItem;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.MyDate;



public class StampViewHolder extends ItemHolder{

    private View commentDot;
    private RelativeLayout commentCircle;
    private boolean isViewExpanded = false;
    private TextView startTimeTxt ,endTimeTxt, workedTimeTxt, siteNameTxt, commentTxt;

    public StampViewHolder(CollectionView cv) {
        super(cv, R.layout.stamp_card_layout);

        commentDot = findView(R.id.commentDot);
        commentCircle = (RelativeLayout) findView(R.id.commentCircle);
        siteNameTxt = (TextView) findView(R.id.siteNameTxt);
        endTimeTxt = (TextView) findView(R.id.endTimeTxt);
        workedTimeTxt = (TextView) findView(R.id.workedHourTxt);
        startTimeTxt = (TextView) findView(R.id.startTimeTxt);
        commentTxt = (TextView) findView(R.id.commentTxt);
    }

    @Override
    public void init(Item item) {
        ShiftItem shiftItem = (ShiftItem) item;
        Shift shift = shiftItem.shift;
        siteNameTxt.setText(shift.qrCodeIdentifier);
        Date startdate = MyDate.strToDate(shift.shiftTimeStartOnUtc);
        Date endDate = MyDate.strToDate(shift.shiftTimeEndOnUtc);
        startTimeTxt.setText(MyDate.dateToStr(startdate, "HH:mm"));
        endTimeTxt.setText(MyDate.dateToStr(endDate, "HH:mm"));

        workedTimeTxt.setText(MyDate.gethourMin(endDate.getTime() - startdate.getTime()));


        if(shift.stopped == 1){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                commentCircle.setBackground(ContextCompat.getDrawable(context, R.drawable.red_circle));
                commentDot.setBackground(ContextCompat.getDrawable(context, R.drawable.red_dot));
            }
            else {
                commentCircle.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.red_circle));
                commentDot.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.red_dot));
            }
        }


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

    @Override
    public void cleanUp() {}
}
