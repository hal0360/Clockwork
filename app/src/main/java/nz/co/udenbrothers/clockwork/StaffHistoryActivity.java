package nz.co.udenbrothers.clockwork;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nz.co.udenbrothers.clockwork.dao.StampDAO;
import nz.co.udenbrothers.clockwork.itemRecycler.ItemAdaptor;
import nz.co.udenbrothers.clockwork.itemRecycler.itemFactories.ItemMaker;
import nz.co.udenbrothers.clockwork.itemRecycler.itemFactories.StampItemMaker;
import nz.co.udenbrothers.clockwork.models.Stamp;
import nz.co.udenbrothers.clockwork.tools.Kit;


public class StaffHistoryActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_history);

        clicked(R.id.imageHam, this::showMenu);
        StampDAO stampDAO = new StampDAO(this);
        ArrayList<Stamp> stamps = stampDAO.getAll();

        ItemMaker itemMaker = new StampItemMaker(this);

        ItemAdaptor itemAdaptor = new ItemAdaptor(itemMaker.toItems(stamps));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.stampList);
        Kit.recyclerSetup(this,recyclerView, itemAdaptor);

        TextView totalHM = (TextView) findViewById(R.id.totalStampHourTxt);
        totalHM.setText(Kit.gethourMin(stampDAO.getBeforeTotal()));

       // Dialog dialog = Kit.getDialog(this,R.layout.export_layout);
       // clicked(dialog.findViewById(R.id.exportButton),()->{
           // dialog.dismiss();
       // });

        clicked(R.id.exportDataButton,()->{
            CSVWriter writer = null;
            try {
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(path, "Cloclwork.csv");
                file.delete();
                writer = new CSVWriter(new FileWriter(new File(path, "Cloclwork.csv"), true), ',');
                List<String[]> data = new ArrayList<>();
                for(Stamp stamp: stamps){
                    data.add(new String[] {stamp.site_name, stamp.staff_name, stamp.startTime, stamp.endTime, stamp.comment});
                }
                writer.writeAll(data);
                writer.close();

                File filelocation = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Cloclwork.csv");
                Uri pathh = Uri.fromFile(filelocation);
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("vnd.android.cursor.dir/email");
                emailIntent.putExtra(Intent.EXTRA_STREAM, pathh);
                startActivity(Intent.createChooser(emailIntent , "EXPORTING CSV FILE..."));

            } catch (IOException e) {
                alert("Error! Cannot create CSV file");
            }
        });
    }
}
