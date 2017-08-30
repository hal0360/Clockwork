package nz.co.udenbrothers.clockwork.serverices;

import android.app.IntentService;
import android.content.Intent;

import java.util.List;

import nz.co.udenbrothers.clockwork.global.Api;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.sql_stuff.SQL;
import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.tools.Json;
import nz.co.udenbrothers.clockwork.tools.RequestTask;



public class UploadService extends IntentService {


    public UploadService() {
        super("UploadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        List<Shift> shifts = SQL.get(Shift.class, s->s.uploaded == 0);

        Response response = RequestTask.myHttpConnection("POST", Json.to(shifts), Api.SAVE_SHIFTS, Profile.token());

        if(response.statusCode >= 200 && response.statusCode < 300){
            for (Shift shift: shifts){
                shift.uploaded = 1;
                shift.save();
            }
        }

    }

}
