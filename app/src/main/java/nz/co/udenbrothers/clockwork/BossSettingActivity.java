package nz.co.udenbrothers.clockwork;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.TextView;

import nz.co.udenbrothers.clockwork.receiver.ClockInReceiver;
import nz.co.udenbrothers.clockwork.receiver.ClockOutReceiver;

public class BossSettingActivity extends BossActivity {

    private TextView wDaysTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_setting);

        clicked(R.id.imageHam, ()-> sideMenu.show());

        ClockInReceiver clockInReceiver = new ClockInReceiver();
        ClockOutReceiver clockOutReceiver = new ClockOutReceiver();
        SwitchCompat wifiSwitch = findViewById(R.id.wifiSwitch);
        SwitchCompat notiSwitch = findViewById(R.id.notificationSwitch);
        SwitchCompat reminderSwitch = findViewById(R.id.reminderSwitch);
        SwitchCompat wedgeSwitch = findViewById(R.id.wedgeSwitch);
        TextView COtxt = findViewById(R.id.clockOutTxt);
        TextView CItxt = findViewById(R.id.clockInTxt);
        wDaysTxt = findViewById(R.id.workDaysTxt);
        wifiSwitch.setClickable(false);
        notiSwitch.setClickable(false);
        reminderSwitch.setClickable(false);
        wedgeSwitch.setClickable(false);

        COtxt.setText(pref.getInt("COhour") + ":" + pref.getInt("COmin"));
        CItxt.setText(pref.getInt("CIhour") + ":" + pref.getInt("CImin"));
        if(pref.getBool("wifi",false)) wifiSwitch.setChecked(true);
        if(pref.getBool("notification",true)) notiSwitch.setChecked(true);
        if(pref.getBool("reminder",true)) reminderSwitch.setChecked(true);
        if(pref.getBool("wedge",true)) wedgeSwitch.setChecked(true);

        clicked(R.id.wifiTab,()->{
            if(pref.getBool("wifi",false)){
                pref.putBool("wifi",false);
                wifiSwitch.setChecked(false);
            }
            else {
                pref.putBool("wifi",true);
                wifiSwitch.setChecked(true);
            }
        });

        clicked(R.id.wedgeTab,()->{
            if(pref.getBool("wedge",true)){
                pref.putBool("wedge",false);
                wedgeSwitch.setChecked(false);
            }
            else {
                pref.putBool("wedge",true);
                wedgeSwitch.setChecked(true);
            }
        });

        clicked(R.id.notiTab,()->{
            if(pref.getBool("notification",true)){
                pref.putBool("notification",false);
                notiSwitch.setChecked(false);
            }
            else {
                pref.putBool("notification",true);
                notiSwitch.setChecked(true);
            }
        });

        clicked(R.id.reminderTab,()->{
            if(pref.getBool("reminder",true)){
                pref.putBool("reminder",false);
                reminderSwitch.setChecked(false);
                clockOutReceiver.cancelling(this);
                clockInReceiver.cancelling(this);
            }
            else {
                pref.putBool("reminder",true);
                reminderSwitch.setChecked(true);
                clockOutReceiver.starting(this);
                clockInReceiver.starting(this);
            }
        });

        clicked(R.id.clockOutTab,()->{
            com.wdullaer.materialdatetimepicker.time.TimePickerDialog timepickerdialog = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance((view, hour, min, sec) -> {
                COtxt.setText(hour + ":" + min);
                pref.putInt("COhour", hour);
                pref.putInt("COmin", min);
                clockOutReceiver.starting(this);
            }, pref.getInt("COhour"), pref.getInt("COmin"), true);
            timepickerdialog.setAccentColor(Color.parseColor("#FF8C00"));
            timepickerdialog.show(getFragmentManager(), "Timepickerdialog");
        });


        clicked(R.id.clockInTab,()->{
            com.wdullaer.materialdatetimepicker.time.TimePickerDialog timepickerdialog = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance((view, hour, min, sec) -> {
                CItxt.setText(hour + ":" + min);
                pref.putInt("CIhour", hour);
                pref.putInt("CImin", min);
                clockInReceiver.starting(this);
            }, pref.getInt("CIhour"), pref.getInt("CImin"), true);
            timepickerdialog.setAccentColor(Color.parseColor("#FF8C00"));
            timepickerdialog.show(getFragmentManager(), "Timepickerdialog");
        });

        clicked(R.id.workDaysTab,()-> pushActivity(WorkdaysSelectActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        String daysTxt = "";
        if(pref.getBool("monday",true)) daysTxt = daysTxt + ",M";
        if(pref.getBool("tuesday",true)) daysTxt = daysTxt + ",T";
        if(pref.getBool("wednesday",true)) daysTxt = daysTxt + ",W";
        if(pref.getBool("thursday",true)) daysTxt = daysTxt + ",T";
        if(pref.getBool("friday",true)) daysTxt = daysTxt + ",F";
        if(pref.getBool("saturday",true)) daysTxt = daysTxt + ",S";
        if(pref.getBool("sunday",true)) daysTxt = daysTxt + ",S";
        wDaysTxt.setText(daysTxt.substring(1));
    }
}
