package nz.co.udenbrothers.clockwork;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.Date;

import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.itemFactories.HomeItemMaker;
import nz.co.udenbrothers.clockwork.models.Project;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.temps.Act;
import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.MyDate;
import nz.co.udenbrothers.clockwork.tools.Popup;

public class BossHomeActivity extends BossActivity{

    private HomeItemMaker homeItemMaker;
    private CollectionView collectionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_home);

        clicked(R.id.imageHam, ()-> sideMenu.show());

        homeItemMaker = new HomeItemMaker(this);
        collectionView = findViewById(R.id.siteList);
        collectionView.init(homeItemMaker.fetch());
        
        clicked(R.id.activityButton, ()-> new IntentIntegrator(this).initiateScan());

        overlayPermission();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String newName = Kit.QrScanResult(IntentIntegrator.parseActivityResult(requestCode, resultCode, data));
        if(newName.equals("")) return;
        Project newPro = new Project(newName);
        if(!homeItemMaker.search(newName)) newPro.save();
        if(!(Act.current() == null)){
            Shift shift = new Shift(Act.current(), Act.startTime(), MyDate.dateToStr(new Date()), Profile.userID());
            shift.save();

            Popup popup = new Popup(this, R.layout.comment_layout);
            EditText commentBox = popup.getView(R.id.commentBox);
            popup.clicked(R.id.saveButton, ()->{
                shift.comment = commentBox.getText().toString().trim();
                shift.save();
                popup.dismiss();
            });
            popup.show();

            if(!Act.current().equals(newName)) setNameNtime(newName);
            else Act.current(null);
        }
        else {setNameNtime(newName);}
        collectionView.refresh(homeItemMaker.fetch());
    }

    private void setNameNtime(String name){
        Act.current(name);
        Act.startTime(MyDate.dateToStr(new Date()));
    }
}
