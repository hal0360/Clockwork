package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.widget.CheckBox;

public class WorkdaysSelectActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workdays_select);

        CheckBox mon = findViewById(R.id.checkMon);
        CheckBox tue = findViewById(R.id.checkTue);
        CheckBox wed = findViewById(R.id.checkWed);
        CheckBox thu = findViewById(R.id.checkThu);
        CheckBox fri = findViewById(R.id.checkFri);
        CheckBox sat = findViewById(R.id.checkSat);
        CheckBox sun = findViewById(R.id.checkSun);

        if(pref.getBool("monday",true)) mon.setChecked(true);
        if(pref.getBool("tuesday",true)) tue.setChecked(true);
        if(pref.getBool("wednesday",true)) wed.setChecked(true);
        if(pref.getBool("thursday",true)) thu.setChecked(true);
        if(pref.getBool("friday",true)) fri.setChecked(true);
        if(pref.getBool("sunday",true)) sun.setChecked(true);
        if(pref.getBool("saturday",true)) sat.setChecked(true);

        clicked(mon,()->{
            if(pref.getBool("monday",true)){
                pref.putBool("monday", false);
                mon.setChecked(false);
            }
            else {
                pref.putBool("monday", true);
                mon.setChecked(true);
            }
        });
        clicked(tue,()->{
            if(pref.getBool("tuesday",true)){
                pref.putBool("tuesday", false);
                tue.setChecked(false);
            }
            else {
                pref.putBool("tuesday", true);
                tue.setChecked(true);
            }
        });
        clicked(wed,()->{
            if(pref.getBool("wednesday",true)){
                pref.putBool("wednesday", false);
                wed.setChecked(false);
            }
            else {
                pref.putBool("wednesday", true);
                wed.setChecked(true);
            }
        });
        clicked(thu,()->{
            if(pref.getBool("thursday",true)){
                pref.putBool("thursday", false);
                thu.setChecked(false);
            }
            else {
                pref.putBool("thursday", true);
                thu.setChecked(true);
            }
        });
        clicked(fri,()->{
            if(pref.getBool("friday",true)){
                pref.putBool("friday", false);
                fri.setChecked(false);
            }
            else {
                pref.putBool("friday", true);
                fri.setChecked(true);
            }
        });
        clicked(sat,()->{
            if(pref.getBool("saturday",true)){
                pref.putBool("saturday", false);
                sat.setChecked(false);
            }
            else {
                pref.putBool("saturday", true);
                sat.setChecked(true);
            }
        });
        clicked(sun,()->{
            if(pref.getBool("sunday",true)){
                pref.putBool("sunday", false);
                sun.setChecked(false);
            }
            else {
                pref.putBool("sunday", true);
                sun.setChecked(true);
            }
        });

    }
}
