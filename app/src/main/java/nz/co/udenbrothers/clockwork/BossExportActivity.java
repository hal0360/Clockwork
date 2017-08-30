package nz.co.udenbrothers.clockwork;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nz.co.udenbrothers.clockwork.customWigets.Choser;
import nz.co.udenbrothers.clockwork.models.Project;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.models.User;
import nz.co.udenbrothers.clockwork.sql_stuff.SQL;
import nz.co.udenbrothers.clockwork.temps.Profile;

public class BossExportActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_export);

        EditText email = findViewById(R.id.editSendMaill);
        email.setText(Profile.email());

        String[] categories = {"ALL TIME","THIS YEAR","THIS MONTH","THIS WEEK","TODAY"};
        Choser spinner1 = findViewById(R.id.editPeriod);
        spinner1.init(categories, R.layout.export_spinner_iem);
        spinner1.selected(i -> {

        });

        ArrayList<String> categories3 = new ArrayList<>();
        categories3.add("All projects");
        List<Project> projects = SQL.get(Project.class);
        for(Project project: projects) categories3.add(project.qrCodeIdentifier);
        Choser spinner2 = findViewById(R.id.editExpProject);
        spinner2.init(categories3, R.layout.export_spinner_iem);
        spinner2.selected(i -> {

        });

        ArrayList<String> categories2 = new ArrayList<>();
        categories2.add("All members");
        List<User> users = SQL.get(User.class);
        for(User user: users) categories2.add(user.firstName + " " + user.lastName);
        Choser spinner3 = findViewById(R.id.editExpMember);
        spinner3.init(categories2, R.layout.export_spinner_iem);
        spinner3.selected(i -> {

        });

        clicked(R.id.doExportButton,()->{

            File file =  new File(getExternalFilesDir(null), "Shift.csv");

            try {
                file.createNewFile();
                System.out.println(file.getAbsolutePath());
                CSVWriter writer = new CSVWriter(new FileWriter(file));
                writer.writeAll(toStringArray(SQL.get(Shift.class)));
                writer.close();
            } catch (IOException e) {
                Log.e("CSV error: ",e+"");
            }

            Uri path = Uri.fromFile(file);
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent .setType("vnd.android.cursor.dir/email");
            String to[] = {Profile.email()};
            emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
            emailIntent .putExtra(Intent.EXTRA_STREAM, path);
            emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Your subject");
            startActivity(Intent.createChooser(emailIntent , "Sending CSV file..."));

        });
    }

    private static List<String[]> toStringArray(List<Shift> shifts) {
        List<String[]> records = new ArrayList<>();
        records.add(new String[] { "QR Code", "start time", "end time", "user ID" });
        for(Shift shift: shifts){
            records.add(new String[] { shift.qrCodeIdentifier, shift.shiftTimeStartOnUtc, shift.shiftTimeEndOnUtc, shift.userId });
        }
        return records;
    }

    /*
    @Override
    public void postCallback(Response response) {
        if(response.statusCode == 200){
            alert("Export  successful");
        }
        else {
            alert("Problem with connection or server. Try again later");
        }
    }*/
}
