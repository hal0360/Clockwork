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
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class BossHomeActivity extends MyActivity implements RecycleCallback{

    private ItemAdaptor itemAdaptor;
    private ArrayList<Project> projects;
    private Handler handler;
    private  RecyclerView recyclerView;
    private HomeItemMaker homeItemMaker;
    private ProjectDAO projectDAO;
    private ShiftDAO shiftDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_home);

        handler = new Handler();
        projectDAO = new ProjectDAO(this);
        homeItemMaker = new HomeItemMaker(this);
        shiftDAO = new ShiftDAO(this);
        recyclerView = (RecyclerView) findViewById(R.id.siteList);
        setUpItems();

        clicked(R.id.imageHam, this::showMenu);
        clicked(R.id.activityButton, ()-> new IntentIntegrator(this).initiateScan());
    }

    public void deleteItem(int index){
        itemAdaptor.delete(index);
    }

    private void setUpItems(){
        projects = projectDAO.getAll();
        ArrayList<Item> items = homeItemMaker.toItems(projects);
        if(!pref.getStr("currentProject").equals("")){
            items.add(0,homeItemMaker.getTopItem(handler,"Working now: " + pref.getStr("currentProject")));
        }
        itemAdaptor = new ItemAdaptor(items);
        Kit.recyclerSetup(this,recyclerView, itemAdaptor);
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

            Project newPro = new Project(newName);
            if(!projects.contains(newPro)){
                projectDAO.add(newPro);
            }

            if(!pref.getStr("currentProject").equals("")){
                alert("Finish working: " + pref.getStr("currentProject"));
                Shift shift = new Shift(pref.getStr("currentProject"),pref.getStr("startTime"),Kit.dateToStr(new Date()),pref.getStr("uid"));
                shiftDAO.add(shift);
                Dialog dialog = Kit.getDialog(this, R.layout.comment_layout);
                Button saveButton = (Button) dialog.findViewById(R.id.saveButton);
                EditText commentBox = (EditText) dialog.findViewById(R.id.commentBox);
                TextView boxTxt = (TextView) dialog.findViewById(R.id.boxTitle);
                boxTxt.setText("Checkout: " + newName);
                clicked(saveButton, ()->{
                    shift.comment = commentBox.getText().toString().trim();
                    shiftDAO.update(shift);
                    new UploadShift(this).upload(shift);
                    dialog.dismiss();
                });
                dialog.show();

                if(!pref.getStr("currentProject").equals(newName)){
                    pref.putStr("currentProject", newName);
                    pref.putStr("startTime", Kit.dateToStr(new Date()));
                    alert("Start working: " + newName);
                }
                else{
                    pref.putStr("currentProject", "");
                }
            }
            else {
                pref.putStr("currentProject", newName);
                pref.putStr("startTime", Kit.dateToStr(new Date()));
                alert("Start working: " + newName);
            }
            setUpItems();
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
