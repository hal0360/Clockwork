package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import nz.co.udenbrothers.clockwork.itemRecycler.ItemAdaptor;
import nz.co.udenbrothers.clockwork.itemRecycler.itemFactories.HistoryItemMaker;
import nz.co.udenbrothers.clockwork.tools.Kit;

public class StaffProjectHistoryActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_project_history);

        String name = pref.getStr("selected");
        TextView ttxt = (TextView)  findViewById(R.id.projectDetailTitle);
        ttxt.setText(name);

        HistoryItemMaker itemMaker = new HistoryItemMaker(this);
        ItemAdaptor itemAdaptor = new ItemAdaptor(itemMaker.fetchBy("qrCodeIdentifier", name, 0));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.stampList);
        Kit.recyclerSetup(this,recyclerView, itemAdaptor);
        TextView totalHM = (TextView) findViewById(R.id.totalStampHourTxt);
        totalHM.setText(itemMaker.getTotal(0));

        clicked(R.id.backButton, this::finish);
    }
}
