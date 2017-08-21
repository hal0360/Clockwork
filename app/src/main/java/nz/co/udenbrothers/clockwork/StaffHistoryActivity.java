package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.widget.TextView;

import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.itemFactories.HistoryItemMaker;
import nz.co.udenbrothers.clockwork.tools.Choser;


public class StaffHistoryActivity extends StaffActivity{

    private HistoryItemMaker itemMaker;
    private CollectionView collectionView;
    private TextView totalHM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_history);
        clicked(R.id.imageHam, this::showMenu);

        collectionView = (CollectionView) findViewById(R.id.stampList);
        itemMaker = new HistoryItemMaker(this);

        String ProName = pref.getStr("selectedProjectName");
        if(ProName.equals("")){
            collectionView.init(itemMaker.fetch(0));
        }
        else{
            TextView ttxt = (TextView)  findViewById(R.id.detailTitle);
            ttxt.setText(ProName);
            collectionView.init(itemMaker.fetchBy("qrCodeIdentifier", ProName, 0));
            pref.putStr("selectedProjectName","");
        }

        totalHM = (TextView) findViewById(R.id.totalStampHourTxt);
        totalHM.setText(itemMaker.getTotal(0));

        String[] categories = {"ALL TIME","THIS YEAR","THIS MONTH","THIS WEEK"};
        Choser spinner = (Choser) findViewById(R.id.timeSelectButton);
        spinner.init(categories, R.layout.time_select_spinner_item);
        spinner.selected(i -> {
            alert(categories[i]);
        });

        clicked(R.id.exportDataButton,()-> pushActivity(StaffExportActivity.class));
    }

}
