package nz.co.udenbrothers.clockwork.serverObjects;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.dao.ShiftDAO;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.Pref;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

/**
 * Created by user on 15/06/2017.
 */

public class UploadShift implements AsynCallback{

    private Context context;
    private Gson gson;
    private Pref pref;
    private ArrayList<Shift> shifts;
    private ShiftDAO shiftDAO;

    public UploadShift(Context context){
        this.context = context;
        gson = new Gson();
        pref = new  Pref(context);
        shiftDAO = new ShiftDAO(context);
    }

    public void upload(Shift shift){
        shifts = new ArrayList<>();
        shifts.add(shift);
        new RequestTask(this,"POST", gson.toJson(shifts), pref.getStr("token")).execute("https://clockwork-api.azurewebsites.net/v1/projects/shifts/save");
    }

    public void upload(ArrayList<Shift> shiftys){
        shifts = shiftys;
        new RequestTask(this,"POST", gson.toJson(shifts), pref.getStr("token")).execute("https://clockwork-api.azurewebsites.net/v1/projects/shifts/save");
    }

    @Override
    public void postCallback(Response response) {
        if(response.statusCode == 204){
            Kit.show(context,"shifts uploaded");
            for (Shift shift: shifts){
                shift.uploaded = 1;
                shiftDAO.update(shift);
            }
        }
        else {
            Kit.show(context,"shifts falied to upload");
        }
    }

    @Override
    public Context getContex() {
        return context;
    }
}
