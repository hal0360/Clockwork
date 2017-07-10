package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.abstractions.RecycleCallback;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.MyDate;
import nz.co.udenbrothers.clockwork.tools.Pref;

public class TopViewHolder extends ItemHolder {

    private boolean isViewExpanded = false;
    private TextView started, worked, projectName;
    private Date startDate;
    private Handler handler;
    private RecycleCallback recycleCallback;

    public TopViewHolder(View v) {
        super(v);
        started =  (TextView) v.findViewById(R.id.startedHour);
        worked =  (TextView) v.findViewById(R.id.workedHour);
        projectName = (TextView) v.findViewById(R.id.currentProjectTxt);

        clicked(v.findViewById(R.id.stopSessionButton), ()-> recycleCallback.forceStop());

        clicked(card, ()->{
            if(isViewExpanded){
                expandView(Kit.dps(120));
                isViewExpanded = false;
            }
            else {
                expandView(Kit.dps(175));
                isViewExpanded = true;
            }
        });
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
        projectName.setText(item.des);
        setHeight(Kit.dps(120));
        Pref pref = new Pref(item.context);
        recycleCallback = (RecycleCallback) item.context;
        handler = (Handler) recycleCallback.getObj();
        startDate = MyDate.strToDate(pref.getStr("startTime"));
        started.setText(MyDate.dateToStr(startDate,"HH:mm"));
        countDownStart();
    }
}
