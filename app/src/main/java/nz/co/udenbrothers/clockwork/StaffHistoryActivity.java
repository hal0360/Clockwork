package nz.co.udenbrothers.clockwork;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import nz.co.udenbrothers.clockwork.itemRecycler.ItemAdaptor;
import nz.co.udenbrothers.clockwork.itemRecycler.itemFactories.HistoryItemMaker;
import nz.co.udenbrothers.clockwork.tools.Kit;


public class StaffHistoryActivity extends StaffActivity {

    private ItemAdaptor itemAdaptor;
    private HistoryItemMaker itemMaker;
    private RecyclerView recyclerView;
    private TextView totalHM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_history);

        clicked(R.id.imageHam, this::showMenu);

        itemMaker = new HistoryItemMaker(this);
        itemAdaptor = new ItemAdaptor(itemMaker.fetch(0));
        recyclerView = (RecyclerView) findViewById(R.id.stampList);
        Kit.recyclerSetup(this,recyclerView, itemAdaptor);

        totalHM = (TextView) findViewById(R.id.totalStampHourTxt);
        totalHM.setText(itemMaker.getTotal(0));

        Button timeButt = (Button) findViewById(R.id.timeSelectButton);
        Dialog dialog = Kit.getDialog(this, R.layout.time_select_layout);
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.TOP | Gravity.CENTER);
        clicked(timeButt, dialog::show);
        Button allButt = (Button) dialog.findViewById(R.id.allTimeButton);
        Button weekButt = (Button) dialog.findViewById(R.id.thisWeekButton);
        Button monButt = (Button) dialog.findViewById(R.id.thisMonthButton);
        Button yearButt = (Button) dialog.findViewById(R.id.thisYearButton);
        clicked(allButt, ()-> {
            timeButt.setText("ALL TIME");
            dialog.dismiss();
        });
        clicked(monButt, ()->  {
            timeButt.setText("THIS MONTH");
            dialog.dismiss();
        });
        clicked(weekButt, ()->  {
            timeButt.setText("THIS WEEK");
            dialog.dismiss();
        });
        clicked(yearButt, ()->  {
            timeButt.setText("THIS YEAR");
            dialog.dismiss();
        });

        clicked(R.id.exportDataButton,()-> pushActivity(StaffExportActivity.class));
    }

    private void refresh(int days){
        itemAdaptor.update(itemMaker.fetch(days));
        recyclerView.scrollToPosition(0);
    }
}
