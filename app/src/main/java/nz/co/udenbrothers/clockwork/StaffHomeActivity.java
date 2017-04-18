package nz.co.udenbrothers.clockwork;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Date;

import nz.co.udenbrothers.clockwork.abstractions.RecycleCallback;
import nz.co.udenbrothers.clockwork.dao.SiteDAO;
import nz.co.udenbrothers.clockwork.dao.StampDAO;
import nz.co.udenbrothers.clockwork.itemRecycler.ItemAdaptor;
import nz.co.udenbrothers.clockwork.itemRecycler.itemFactories.SiteItemMaker;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.models.Site;
import nz.co.udenbrothers.clockwork.models.Stamp;
import nz.co.udenbrothers.clockwork.tools.Kit;

public class StaffHomeActivity extends MyActivity implements RecycleCallback {

    private ItemAdaptor itemAdaptor;
    private ArrayList<Site> sites;
    private Handler handler;
    private  RecyclerView recyclerView;
    private SiteItemMaker siteItemMaker;
    private SiteDAO siteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_home);

        handler = new Handler();
        siteDAO = new SiteDAO(this);
        sites = siteDAO.getAll();
        siteItemMaker = new SiteItemMaker(this);

        ArrayList<Item> items = siteItemMaker.toItems(sites);
        if(!pref.getStr("currentSite").equals("")){
            items.add(0,siteItemMaker.getTopItem(handler,"Working now: " + pref.getStr("currentSite")));
        }
        itemAdaptor = new ItemAdaptor(items);
        recyclerView = (RecyclerView) findViewById(R.id.siteList);
        Kit.recyclerSetup(this,recyclerView, itemAdaptor);

        clicked(R.id.imageHam, this::showMenu);
        clicked(R.id.activityButton, ()-> new IntentIntegrator(this).initiateScan());
    }

    public void deleteItem(int index){
        itemAdaptor.delete(index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null){
                alert("Cancelled");
                return;
            }
            String newName = result.getContents().trim();
            if(newName.equals("")){
                alert("Invalid code");
                return;
            }

            Site newSite = new Site();
            newSite.name = newName;
            if(!sites.contains(newSite)){
                siteDAO.add(newSite);
                sites.add(newSite);
            }
            ArrayList<Item> items = siteItemMaker.toItems(sites);

            if(!pref.getStr("currentSite").equals("")){
                alert("Finish working: " + pref.getStr("currentSite"));
                StampDAO stampDAO = new StampDAO(this);
                Stamp stamp = new Stamp();
                stamp.site_name = pref.getStr("currentSite");
                stamp.staff_name = pref.getStr("profileName");
                stamp.endTime = Kit.dateToStr(new Date());
                stamp.startTime = pref.getStr("startTime");
                stampDAO.add(stamp);

                Dialog dialog = Kit.getDialog(this, R.layout.comment_layout);
                Button saveButton = (Button) dialog.findViewById(R.id.saveButton);
                EditText commentBox = (EditText) dialog.findViewById(R.id.commentBox);
                TextView boxTxt = (TextView) dialog.findViewById(R.id.boxTitle);
                boxTxt.setText("Checkout: " + newName);
                clicked(saveButton, ()->{
                    stamp.comment = commentBox.getText().toString().trim();
                    stampDAO.update(stamp);
                    dialog.dismiss();
                });
                dialog.show();

                if(!pref.getStr("currentSite").equals(newName)){
                    pref.putStr("currentSite", newName);
                    pref.putStr("startTime", Kit.dateToStr(new Date()));
                    items.add(0,siteItemMaker.getTopItem(handler,"Working now: " + pref.getStr("currentSite")));
                    alert("Start working: " + newName);
                }
                else{
                    pref.putStr("currentSite", "");
                }
            }
            else {
                pref.putStr("currentSite", newName);
                pref.putStr("startTime", Kit.dateToStr(new Date()));
                items.add(0,siteItemMaker.getTopItem(handler,"Working now: " + pref.getStr("currentSite")));
                alert("Start working: " + newName);
            }
            itemAdaptor.update(items);
          //  LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.scrollToPosition(0);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
