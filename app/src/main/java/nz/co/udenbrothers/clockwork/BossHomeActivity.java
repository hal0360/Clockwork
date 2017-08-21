package nz.co.udenbrothers.clockwork;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.Calendar;
import java.util.Date;

import nz.co.udenbrothers.clockwork.abstractions.RecycleCallback;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.itemFactories.HomeItemMaker;
import nz.co.udenbrothers.clockwork.models.Project;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.MyDate;
import nz.co.udenbrothers.clockwork.tools.Popup;
import nz.co.udenbrothers.clockwork.tools.UploadShift;

public class BossHomeActivity extends BossActivity implements RecycleCallback{

    private Handler handler;
    private HomeItemMaker homeItemMaker;
    private CollectionView collectionView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_home);

        handler = new Handler();
        homeItemMaker = new HomeItemMaker(this);
        collectionView = (CollectionView) findViewById(R.id.siteList);
        collectionView.init(homeItemMaker.fetch());

      //  clicked(R.id.imageHam, this::showMenu);
        clicked(R.id.activityButton, ()-> new IntentIntegrator(this).initiateScan());
    }

    @Override
    public void delete(int pos) {
        collectionView.delete(pos);
    }

    @Override
    public void forceStop() {
        Shift shift = new Shift(pref.getStr("currentProject"),pref.getStr("startTime"), MyDate.dateToStr(new Date()),pref.getStr("uid"));
        shift.comment = "This shift was force stopped by the user";
        shift.save(this);
        shift.stopped = 1;
        pref.putStr("currentProject", "");
        alert("acvtivity force stopped");
        collectionView.delete(0);

        Calendar rightNow = Calendar.getInstance();
        Popup popup = new Popup(this, R.layout.time_comment_layout);
        TimePicker timePicker = (TimePicker) popup.getView(R.id.stopTimePicker);
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
        EditText commentBox = (EditText) popup.getView(R.id.commentBox);
        popup.clicked(R.id.saveButton, ()->{
            shift.comment = commentBox.getText().toString().trim();
            commentBox.setText("");
            shift.save(this);
            new UploadShift(this).upload(shift);
            popup.dismiss();
        });
        popup.show();
    }

    @Override
    public Object getObj() {
        return handler;
    }

    @Override
    public void moreInfo(String name) {
        pref.putStr("selectedProjectName",name);
        navigate(StaffHistoryActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String newName = Kit.QrScanResult(IntentIntegrator.parseActivityResult(requestCode, resultCode, data));
        if(newName.equals("")) return;
        Project newPro = new Project(newName);
        if(!Project.search(this, newName)) newPro.save(this);
        if(!pref.getStr("currentProject").equals("")){
            Shift shift = new Shift(pref.getStr("currentProject"),pref.getStr("startTime"), MyDate.dateToStr(new Date()),pref.getStr("uid"));
            shift.save(this);

            Popup popup = new Popup(this, R.layout.comment_layout);
            EditText commentBox = (EditText) popup.getView(R.id.commentBox);
            popup.clicked(R.id.saveButton, ()->{
                shift.comment = commentBox.getText().toString().trim();
                commentBox.setText("");
                shift.save(this);
                new UploadShift(this).upload(shift);
                popup.dismiss();
            });
            popup.show();

            if(!pref.getStr("currentProject").equals(newName)){
                pref.putStr("currentProject", newName);
                pref.putStr("startTime", MyDate.dateToStr(new Date()));
            }
            else{ pref.putStr("currentProject", ""); }
        }
        else {
            pref.putStr("currentProject", newName);
            pref.putStr("startTime", MyDate.dateToStr(new Date()));
        }
        collectionView.refresh(homeItemMaker.fetch());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
