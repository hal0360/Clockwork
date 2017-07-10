package nz.co.udenbrothers.clockwork;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Date;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.abstractions.RecycleCallback;
import nz.co.udenbrothers.clockwork.dao.ProjectDAO;
import nz.co.udenbrothers.clockwork.dao.ShiftDAO;
import nz.co.udenbrothers.clockwork.itemRecycler.ItemAdaptor;
import nz.co.udenbrothers.clockwork.itemRecycler.itemFactories.HomeItemMaker;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.models.Project;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.serverObjects.UploadShift;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.MyDate;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class BossHomeActivity extends BossActivity implements RecycleCallback{

    private ItemAdaptor itemAdaptor;
    private Handler handler;
    private RecyclerView recyclerView;
    private HomeItemMaker homeItemMaker;
    private ShiftDAO shiftDAO;
    private ProjectDAO projectDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_home);

        handler = new Handler();
        projectDAO = new ProjectDAO(this);
        shiftDAO = new ShiftDAO(this);
        homeItemMaker = new HomeItemMaker(this);
        recyclerView = (RecyclerView) findViewById(R.id.siteList);
        itemAdaptor = new ItemAdaptor(homeItemMaker.fetch());
        Kit.recyclerSetup(this,recyclerView, itemAdaptor);

        clicked(R.id.imageHam, this::showMenu);
        clicked(R.id.activityButton, ()-> new IntentIntegrator(this).initiateScan());
    }

    @Override
    public void deleteProject(String name, int pos) {
        //  projectDAO.deleteBy("qrCodeIdentifier", name);
        //  shiftDAO.deleteBy();
        itemAdaptor.delete(pos);
    }

    @Override
    public void forceStop() {
        Shift shift = new Shift(pref.getStr("currentProject"),pref.getStr("startTime"), MyDate.dateToStr(new Date()),pref.getStr("uid"));
        shift.comment = "This shift was force stopped by the user";
        shiftDAO.add(shift);
        pref.putStr("currentProject", "");
        alert("acvtivity force stopped");
        itemAdaptor.delete(0);
    }

    @Override
    public Object getObj() {
        return handler;
    }

    @Override
    public void moreInfo(String name) {
        pref.putStr("selected",name);
        pushActivity(StaffProjectHistoryActivity.class);
    }

    private void refresh(){
        itemAdaptor.update(homeItemMaker.fetch());
        recyclerView.scrollToPosition(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String newName = Kit.QrScanResult(IntentIntegrator.parseActivityResult(requestCode, resultCode, data));
        if(newName.equals("")) return;
        Project newPro = new Project(newName);
        if(!projectDAO.search(newName)) projectDAO.add(newPro);
        if(!pref.getStr("currentProject").equals("")){
            Shift shift = new Shift(pref.getStr("currentProject"),pref.getStr("startTime"), MyDate.dateToStr(new Date()),pref.getStr("uid"));
            shiftDAO.add(shift);
            Dialog dialog = Kit.getDialog(this, R.layout.comment_layout);
            Button saveButton = (Button) dialog.findViewById(R.id.saveButton);
            EditText commentBox = (EditText) dialog.findViewById(R.id.commentBox);
            clicked(saveButton, ()->{
                String newCom = commentBox.getText().toString().trim();
                shift.comment = newCom.replaceAll("'","\'");
                shiftDAO.update(shift);
                new UploadShift(this).upload(shift);
                dialog.dismiss();
            });
            dialog.show();
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
        refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
