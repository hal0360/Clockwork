package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.global.URL;
import nz.co.udenbrothers.clockwork.models.Project;
import nz.co.udenbrothers.clockwork.serverObjects.ExportInfo;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.tools.Choser;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class BossExportActivity extends MainActivity  implements AsynCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_export);

        EditText email = findViewById(R.id.editSendMaill);
        email.setText(pref.getStr("profileEmail"));

        String[] categories = {"ALL TIME","THIS YEAR","THIS MONTH","THIS WEEK","TODAY"};
        Choser spinner1 = findViewById(R.id.editPeriod);
        spinner1.init(categories, R.layout.export_spinner_iem);
        spinner1.selected(i -> {

        });

        ArrayList<String> categories3 = new ArrayList<>();
        ArrayList<Project> projects = Project.get(this);
        for(Project project: projects) categories3.add(project.qrCodeIdentifier);
        Choser spinner2 = findViewById(R.id.editExpProject);
        spinner2.init(categories3, R.layout.export_spinner_iem);
        spinner2.selected(i -> {

        });

        clicked(R.id.doExportButton,()->{
            ExportInfo exportInfo = new ExportInfo(pref.getStr("userId"),email.getText().toString().trim(),"1999-11-11");
            new RequestTask(this,"POST",exportInfo.toJson(),pref.getStr("token")).execute(URL.EXPORT_SHIFTS);
        });
    }

    @Override
    public void postCallback(Response response) {
        if(response.statusCode == 200){
            alert("Export  successful");
        }
        else {
            alert("Problem with connection or server. Try again later");
        }
    }
}
