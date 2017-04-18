package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.Pref;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.items.TopItem;

public class TopViewHolder extends ItemHolder {

    private TextView title, started, worked;
    private Date startDate;
    private Handler handler;
    private RelativeLayout topHeaderBx;

    public TopViewHolder(View v) {
        super(v);
        title =  (TextView) v.findViewById(R.id.cardTitle);
        started =  (TextView) v.findViewById(R.id.startedHour);
        worked =  (TextView) v.findViewById(R.id.workedHour);
        topHeaderBx = (RelativeLayout) v.findViewById(R.id.topHeaderBox);
    }

    private void countDownStart() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                Date curDate = new Date();
                long diff = curDate.getTime() - startDate.getTime();
                long days = diff / (24 * 60 * 60 * 1000);
                diff -= days * (24 * 60 * 60 * 1000);
                long hours = diff / (60 * 60 * 1000);
                diff -= hours * (60 * 60 * 1000);
                long minutes = diff / (60 * 1000);
                diff -= minutes * (60 * 1000);
                long seconds = diff / 1000;
                worked.setText(String.format(Locale.ENGLISH,"%02d", hours) + ":" + String.format(Locale.ENGLISH,"%02d", minutes) + ":" + String.format(Locale.ENGLISH,"%02d", seconds));
            }
        };
        handler.postDelayed(runnable, 0);
    }


    public void init(Item item){
        TopItem topItem = (TopItem) item;
        title.setText(topItem.des);
        Pref pref = new Pref(topItem.context);
        handler = topItem.handler;
        startDate = Kit.strToDate(pref.getStr("startTime"));
        started.setText(Kit.dateToStr(startDate,"HH:mm"));
        countDownStart();
    }
}
