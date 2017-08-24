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
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.MyDate;
import nz.co.udenbrothers.clockwork.tools.Popup;
import nz.co.udenbrothers.clockwork.tools.UploadShift;

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
        if(!Project.search(this, newName)) newPro.save(this);
        if(!pref.getStr("currentProject").equals("")){
            Shift shift = new Shift(pref.getStr("currentProject"),pref.getStr("startTime"), MyDate.dateToStr(new Date()),pref.getStr("uid"));
            shift.save(this);

            Popup popup = new Popup(this, R.layout.comment_layout);
            EditText commentBox = (EditText) popup.getView(R.id.commentBox);
            popup.clicked(R.id.saveButton, ()->{
                shift.comment = commentBox.getText().toString().trim();
                commentBox.setText("");
                shift.save(this);
                new UploadShift(this).upload(shift);
                popup.dismiss();
            });
            popup.show();

            if(!pref.getStr("currentProject").equals(newName)){
                pref.putStr("currentProject", newName);
                pref.putStr("startTime", MyDate.dateToStr(new Date()));
            }
            else{ pref.putStr("currentProject", ""); }
        }
        else {
            pref.putStr("currentProject", newName);
            pref.putStr("startTime", MyDate.dateToStr(new Date()));
        }
        collectionView.refresh(homeItemMaker.fetch());
    }
}
