package nz.co.udenbrothers.clockwork;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.global.URL;
import nz.co.udenbrothers.clockwork.models.Project;
import nz.co.udenbrothers.clockwork.serverObjects.ExportInfo;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class StaffExportActivity extends MainActivity implements AdapterView.OnItemSelectedListener, AsynCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_export);

        EditText email = (EditText) findViewById(R.id.editSendMaill);
        email.setText(pref.getStr("profileEmail"));

        Spinner spinner = (Spinner) findViewById(R.id.editPeriod);
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

        Spinner spinner2 = (Spinner) findViewById(R.id.editExportTo);
        spinner2.setOnItemSelectedListener(this);
        ArrayList<String> categories2 = new ArrayList<>();
        categories2.add("Email");
        categories2.add("Facebook");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);

        Spinner spinner3 = (Spinner) findViewById(R.id.editExpProject);
        spinner3.setOnItemSelectedListener(this);
        ArrayList<String> categories3 = new ArrayList<>();
        ArrayList<Project> projects = Project.get(this);
        for(Project project: projects){
            categories3.add(project.qrCodeIdentifier);
        }
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter3);

        clicked(R.id.doExportButton,()->{
            ExportInfo exportInfo = new ExportInfo(pref.getStr("userId"),email.getText().toString().trim(),"1999-11-11");
            new RequestTask(this,"POST",exportInfo.toJson(),pref.getStr("token")).execute(URL.EXPORT_SHIFTS);
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.editPeriod:
                //Do something
              //  alert(parent.getSelectedItem().toString() + " nigg");
                break;
            case R.id.editExportTo:
                //Do another thing
              //  alert(parent.getSelectedItem().toString() + " fgfg");
                break;
            case R.id.editExpProject:
                //Do another thing
               // alert(parent.getSelectedItem().toString() + " fgfg");
                break;

        }

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
            alert("Export  successful");
        }
        else {
            alert("Problem with connection or server. Try again later");
        }
    }
}
