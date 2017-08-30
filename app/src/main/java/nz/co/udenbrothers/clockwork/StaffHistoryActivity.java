package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.widget.TextView;

import nz.co.udenbrothers.clockwork.temps.TEMP;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.itemFactories.HistoryItemMaker;
import nz.co.udenbrothers.clockwork.customWigets.Choser;


public class StaffHistoryActivity extends StaffActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_history);

        clicked(R.id.imageHam, ()-> sideMenu.show());

        CollectionView collectionView = findViewById(R.id.stampList);
        HistoryItemMaker itemMaker = new HistoryItemMaker(this);
        collectionView.init(itemMaker.fetch(0));

        if(!(TEMP.QR == null)){
            collectionView.init(itemMaker.fetch(0));
            TextView ttxt = findViewById(R.id.detailTitle);
            ttxt.setText(TEMP.QR);
        }

        TextView totalHM = findViewById(R.id.totalStampHourTxt);

        String[] categories = {"ALL TIME","THIS YEAR","THIS MONTH","THIS WEEK"};
        Choser spinner = findViewById(R.id.timeSelectButton);
        spinner.init(categories, R.layout.time_select_spinner_item);
        spinner.selected(i -> {
            if(i == 0){
                collectionView.refresh(itemMaker.fetch(0));
                totalHM.setText(itemMaker.getTotal(0));
            }else if(i == 1){
                collectionView.refresh(itemMaker.fetch(360));
                totalHM.setText(itemMaker.getTotal(360));
            }else if(i == 2){
                collectionView.refresh(itemMaker.fetch(30));
                totalHM.setText(itemMaker.getTotal(30));
            }else {
                collectionView.refresh(itemMaker.fetch(7));
                totalHM.setText(itemMaker.getTotal(7));
            }
        });

        clicked(R.id.exportDataButton,()-> pushActivity(StaffExportActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        TEMP.QR = null;
    }

}
