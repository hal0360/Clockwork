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
import nz.co.udenbrothers.clockwork.sql_stuff.SQL;
import nz.co.udenbrothers.clockwork.temps.Profile;

public class StaffExportActivity extends MainActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_export);

        EditText email = findViewById(R.id.editSendMaill);
        email.setText(Profile.email());

        String[] categories = {"ALL TIME","THIS YEAR","THIS MONTH","THIS WEEK","TODAY"};
        Choser spinner1 = findViewById(R.id.editPeriod);
        spinner1.init(categories, R.layout.export_spinner_iem);
        spinner1.selected(i -> {

        });

        List<String> categories3 = new ArrayList<>();
        List<Project> projects = SQL.get(Project.class);
        for(Project project: projects) categories3.add(project.qrCodeIdentifier);
        Choser spinner2 = findViewById(R.id.editExpProject);
        spinner2.init(categories3, R.layout.export_spinner_iem);
        spinner2.selected(i -> {

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
}
