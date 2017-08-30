package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.serverices.UploadService;
import nz.co.udenbrothers.clockwork.temps.Act;
import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.MyDate;
import nz.co.udenbrothers.clockwork.tools.Popup;

public class TopViewHolder extends ItemHolder {

    private boolean isViewExpanded = false;
    private TextView started, worked, title;
    private Date startDate;
    private Handler handler;

    public TopViewHolder(CollectionView cv) {
        super(cv, R.layout.top_card_layout);

        started = findView(R.id.startedHour);
        worked = findView(R.id.workedHour);
        title = findView(R.id.cardTitle);
        handler = new Handler();

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

    @Override
    public void init(Item item){
        title.setText("Working now: " + item.des);
        setHeight(Kit.dps(120));

        startDate = MyDate.strToDate(Act.startTime());
        started.setText(MyDate.dateToStr(startDate,"HH:mm"));
        countDownStart();

        clicked(R.id.stopSessionButton, ()-> {

            Shift shift = new Shift(item.des, Act.startTime(), MyDate.dateToStr(new Date()), Profile.userID());
            shift.comment = "This shift was force stopped by the user: ";
            shift.save();
            shift.stopped = 1;
            Act.current(null);

            Calendar rightNow = Calendar.getInstance();
            Popup popup = new Popup(context, R.layout.time_comment_layout);
            TimePicker timePicker = popup.getView(R.id.stopTimePicker);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.setHour(rightNow.get(Calendar.HOUR_OF_DAY));
            }
            else {
                timePicker.setCurrentHour(rightNow.get(Calendar.HOUR_OF_DAY));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.setMinute(rightNow.get(Calendar.MINUTE));
            }
            else {
                timePicker.setCurrentMinute(rightNow.get(Calendar.MINUTE));
            }
            EditText commentBox = popup.getView(R.id.commentBox);

            popup.clicked(R.id.saveButton, ()->{
                shift.comment =  shift.comment + commentBox.getText().toString().trim();
                shift.save();
                context.startService(new Intent(context, UploadService.class));
                popup.dismiss();
                delete();
            });
            popup.show();

        });

    }

    @Override
    public void cleanUp() {
        handler.removeCallbacksAndMessages(null);
    }
}
