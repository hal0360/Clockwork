package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import nz.co.udenbrothers.clockwork.temps.Profile;

public class WorkdaysSelectActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workdays_select);

        RelativeLayout backG = findViewById(R.id.backG);
        if(Profile.role() == 1) backG.setBackgroundColor(0xFFFFAF4D);
        else backG.setBackgroundColor(0xFF00BFFF);

        CheckBox mon = findViewById(R.id.checkMon);
        CheckBox tue = findViewById(R.id.checkTue);
        CheckBox wed = findViewById(R.id.checkWed);
        CheckBox thu = findViewById(R.id.checkThu);
        CheckBox fri = findViewById(R.id.checkFri);
        CheckBox sat = findViewById(R.id.checkSat);
        CheckBox sun = findViewById(R.id.checkSun);

        if(App.getBool("monday",true)) mon.setChecked(true);
        if(App.getBool("tuesday",true)) tue.setChecked(true);
        if(App.getBool("wednesday",true)) wed.setChecked(true);
        if(App.getBool("thursday",true)) thu.setChecked(true);
        if(App.getBool("friday",true)) fri.setChecked(true);
        if(App.getBool("sunday",true)) sun.setChecked(true);
        if(App.getBool("saturday",true)) sat.setChecked(true);

        clicked(mon,()->{
            if(App.getBool("monday",true)){
                App.putBool("monday", false);
                mon.setChecked(false);
            }
            else {
                App.putBool("monday", true);
                mon.setChecked(true);
            }
        });
        clicked(tue,()->{
            if(App.getBool("tuesday",true)){
                App.putBool("tuesday", false);
                tue.setChecked(false);
            }
            else {
                App.putBool("tuesday", true);
                tue.setChecked(true);
            }
        });
        clicked(wed,()->{
            if(App.getBool("wednesday",true)){
                App.putBool("wednesday", false);
                wed.setChecked(false);
            }
            else {
                App.putBool("wednesday", true);
                wed.setChecked(true);
            }
        });
        clicked(thu,()->{
            if(App.getBool("thursday",true)){
                App.putBool("thursday", false);
                thu.setChecked(false);
            }
            else {
                App.putBool("thursday", true);
                thu.setChecked(true);
            }
        });
        clicked(fri,()->{
            if(App.getBool("friday",true)){
                App.putBool("friday", false);
                fri.setChecked(false);
            }
            else {
                App.putBool("friday", true);
                fri.setChecked(true);
            }
        });
        clicked(sat,()->{
            if(App.getBool("saturday",true)){
                App.putBool("saturday", false);
                sat.setChecked(false);
            }
            else {
                App.putBool("saturday", true);
                sat.setChecked(true);
            }
        });
        clicked(sun,()->{
            if(App.getBool("sunday",true)){
                App.putBool("sunday", false);
                sun.setChecked(false);
            }
            else {
                App.putBool("sunday", true);
                sun.setChecked(true);
            }
        });

        clicked(R.id.comButton, this::finish);
    }
}
