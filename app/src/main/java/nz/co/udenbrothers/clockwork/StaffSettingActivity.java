package nz.co.udenbrothers.clockwork;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import nz.co.udenbrothers.clockwork.receiver.ClockInReceiver;
import nz.co.udenbrothers.clockwork.receiver.ClockOutReceiver;
import nz.co.udenbrothers.clockwork.temps.Noti;
import nz.co.udenbrothers.clockwork.temps.Setting;

public class StaffSettingActivity extends StaffActivity {

    private TextView wDaysTxt, COtxt, CItxt;
    private SwitchCompat wifiSwitch, notiSwitch, reminderSwitch, wedgeSwitch;
    private RelativeLayout COtab, CItab, WDtab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_setting);

        clicked(R.id.imageHam, ()-> sideMenu.show());

        wifiSwitch = findViewById(R.id.wifiSwitch);
        notiSwitch = findViewById(R.id.notificationSwitch);
        reminderSwitch = findViewById(R.id.reminderSwitch);
        wedgeSwitch = findViewById(R.id.wedgeSwitch);
        COtxt = findViewById(R.id.clockOutTxt);
        CItxt = findViewById(R.id.clockInTxt);
        wDaysTxt = findViewById(R.id.workDaysTxt);
        COtab = findViewById(R.id.clockOutTab);
        CItab = findViewById(R.id.clockInTab);
        WDtab = findViewById(R.id.workDaysTab);
        setUp();

        clicked(R.id.wifiTab,()->{
            if(Setting.wifi()) Setting.wifi(false);
            else Setting.wifi(true);
            wifiSwitch.setChecked(Setting.wifi());
        });

        clicked(R.id.wedgeTab,()->{
            if(Setting.wedge()) Setting.wedge(false);
            else Setting.wedge(true);
            wedgeSwitch.setChecked(Setting.wedge());
        });

        clicked(R.id.notiTab,()->{
            if(Setting.notification()) Setting.notification(false);
            else Setting.notification(true);
            notiSwitch.setChecked(Setting.notification());
        });

        clicked(R.id.reminderTab,()->{
            if(Setting.reminder()) Setting.reminder(false);
            else Setting.reminder(true);
            setUp();
        });

        clicked(R.id.clockOutTab,()->{
            TimePickerDialog timepickerdialog = TimePickerDialog.newInstance((view, hour, min, sec) -> {
                Noti.COhour(hour);
                Noti.COmin(min);
                setUp();
            }, Noti.COhour(), Noti.COmin(), true);
            timepickerdialog.setAccentColor(Color.parseColor("#ffaf4d"));
            timepickerdialog.show(getFragmentManager(), "Timepickerdialog");
        });

        clicked(R.id.clockInTab,()->{
            TimePickerDialog timepickerdialog = TimePickerDialog.newInstance((view, hour, min, sec) -> {
                Noti.CIhour(hour);
                Noti.CImin(min);
                setUp();
            }, Noti.CIhour(), Noti.CImin(), true);
            timepickerdialog.setAccentColor(Color.parseColor("#ffaf4d"));
            timepickerdialog.show(getFragmentManager(), "Timepickerdialog");
        });

        clicked(R.id.workDaysTab,()-> pushActivity(WorkdaysSelectActivity.class));
    }

    private void setUp(){
        wifiSwitch.setChecked(Setting.wifi());
        notiSwitch.setChecked(Setting.notification());
        reminderSwitch.setChecked(Setting.reminder());
        wedgeSwitch.setChecked(Setting.wedge());
        COtxt.setText(Noti.COhour() + ":" + Noti.COmin());
        CItxt.setText(Noti.CIhour() + ":" + Noti.CImin());

        if(Setting.reminder()){
            ClockInReceiver.starting(this);
            ClockOutReceiver.starting(this);
            COtab.setVisibility(View.VISIBLE);
            CItab.setVisibility(View.VISIBLE);
            WDtab.setVisibility(View.VISIBLE);
        }else {
            ClockInReceiver.cancelling(this);
            ClockOutReceiver.cancelling(this);
            COtab.setVisibility(View.GONE);
            CItab.setVisibility(View.GONE);
            WDtab.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String daysTxt = "";
        if(App.getBool("monday",true)) daysTxt = daysTxt + ",M";
        if(App.getBool("tuesday",true)) daysTxt = daysTxt + ",T";
        if(App.getBool("wednesday",true)) daysTxt = daysTxt + ",W";
        if(App.getBool("thursday",true)) daysTxt = daysTxt + ",T";
        if(App.getBool("friday",true)) daysTxt = daysTxt + ",F";
        if(App.getBool("saturday",true)) daysTxt = daysTxt + ",S";
        if(App.getBool("sunday",true)) daysTxt = daysTxt + ",S";
        wDaysTxt.setText(daysTxt.substring(1));
    }
}
