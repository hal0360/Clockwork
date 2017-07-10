package nz.co.udenbrothers.clockwork;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.serverObjects.ExportInfo;
import nz.co.udenbrothers.clockwork.serverObjects.LoginInfo;
import nz.co.udenbrothers.clockwork.serverObjects.Profile;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class StaffExportActivity extends MainActivity  implements AdapterView.OnItemSelectedListener, AsynCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_export);

        Spinner spinner = (Spinner) findViewById(R.id.editPeriod);
        EditText email = (EditText) findViewById(R.id.editSendMaill);
        email.setText(pref.getStr("profileEmail"));
        spinner.setOnItemSelectedListener(this);
        ArrayList<String> categories = new ArrayList<>();
        categories.add("ALL TIME");
        categories.add("THIS YEAR");
        categories.add("THIS MONTH");
        categories.add("THIS WEEK");
        categories.add("TODAY");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        clicked(R.id.doExportButton,()->{
            ExportInfo exportInfo = new ExportInfo(pref.getStr("userId"),email.getText().toString().trim(),"1999-11-11");
            new RequestTask(this,"POST",exportInfo.toJson(),pref.getStr("token"),true).execute("https://clockwork-api.azurewebsites.net/v1/projects/export/shifts");
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

      /*  Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);

        switch (position) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                throw new IllegalArgumentException("INVALID FIELD TYPE");
        }*/



    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void postCallback(Response response) {
        if(response.statusCode == 204){
            alert("Export in successful");
        }
        else {
            alert("Problem with connection or server. Try again later");
        }
    }

    @Override
    public Context getContex() {
        return this;
    }
}
