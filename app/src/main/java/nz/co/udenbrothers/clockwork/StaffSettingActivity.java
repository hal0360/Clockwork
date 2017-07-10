package nz.co.udenbrothers.clockwork;



import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.Switch;
import android.widget.TextView;

import nz.co.udenbrothers.clockwork.Reciever.ClockInReceiver;
import nz.co.udenbrothers.clockwork.Reciever.ClockOutReceiver;

public class StaffSettingActivity extends StaffActivity {

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_setting);

        clicked(R.id.imageHam, this::showMenu);

        Switch COswitch = (Switch) findViewById(R.id.clockOutSwitch);
        TextView COtxt = (TextView) findViewById(R.id.clockOutTxt);
        Switch CIswitch = (Switch) findViewById(R.id.clockInSwitch);
        TextView CItxt = (TextView) findViewById(R.id.clockInTxt);
        ClockInReceiver clockInReceiver = new ClockInReceiver();
        ClockOutReceiver clockOutReceiver = new ClockOutReceiver();

        if(pref.getBool("clockOut")){
            COtxt.setText(pref.getInt("COhour") + ":" + pref.getInt("COmin"));
            COswitch.setChecked(true);
        }

        if(pref.getBool("clockIn")){
            CItxt.setText(pref.getInt("CIhour") + ":" + pref.getInt("CImin"));
            CIswitch.setChecked(true);
        }

        clicked(COswitch, () -> {
            if(!COswitch.isChecked()){
                clockOutReceiver.cancelling(this);
                pref.putBool("clockOut",false);
                return;
            }
            pref.putBool("clockOut",true);
            clockOutReceiver.starting(this);
            new TimePickerDialog(this, (timePicker, selectedHour, selectedMinute) -> {
                COtxt.setText(selectedHour + ":" + selectedMinute);
                pref.putInt("COhour", selectedHour);
                pref.putInt("COmin", selectedMinute);
                clockOutReceiver.starting(this);
            }, pref.getInt("COhour"), pref.getInt("COmin"), true).show();
        });

        clicked(CIswitch, () -> {
            if(!CIswitch.isChecked()){
                pref.putBool("clockIn",false);
                clockInReceiver.cancelling(this);
                return;
            }
            pref.putBool("clockIn",true);
            clockInReceiver.starting(this);
            new TimePickerDialog(this, (timePicker, selectedHour, selectedMinute) -> {
                CItxt.setText(selectedHour + ":" + selectedMinute);
                pref.putInt("CIhour", selectedHour);
                pref.putInt("CImin", selectedMinute);
                clockInReceiver.starting(this);
            }, pref.getInt("CIhour"), pref.getInt("CImin"), true).show();
        });
    }
}
