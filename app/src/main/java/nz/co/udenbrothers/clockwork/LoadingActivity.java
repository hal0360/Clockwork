package nz.co.udenbrothers.clockwork;

import android.os.Bundle;

import java.util.List;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.global.Api;
import nz.co.udenbrothers.clockwork.models.Project;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.serverObjects.GetShiftsInfo;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.sql_stuff.SQL;
import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.tools.Json;
import nz.co.udenbrothers.clockwork.tools.MyDate;
import nz.co.udenbrothers.clockwork.tools.RequestTask;


public class LoadingActivity extends MainActivity implements AsynCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        List<Shift> shifts = SQL.get(Shift.class, s -> s.uploaded == 0);

        new RequestTask(this,"POST", Json.to(shifts), Profile.token(),false).execute(Api.SAVE_SHIFTS);

    }

    public void postCallback(Response response){

        switch (response.url) {
            case Api.SAVE_SHIFTS:

                new RequestTask(this, "GET", null, Profile.token(), false).execute(Api.GET_PROJECTS);

                break;
            case Api.GET_PROJECTS:

                if (response.statusCode == 200) {
                    List<Project> projects = Json.parse(response.content, Project.class);
                    for (Project project : projects) project.save();
                } else {
                    alert("Failed to download projects");
                }
                GetShiftsInfo getShiftsInfo = new GetShiftsInfo(Profile.userID());
                new RequestTask(this, "GET", getShiftsInfo.toJson(), Profile.token(), false).execute(Api.GET_SHIFTS);

                break;
            case Api.GET_SHIFTS:

                if (response.statusCode == 200) {
                    List<Shift> shifts = Json.parse(response.content, Shift.class);
                    for (Shift shift : shifts) {
                        shift.shiftTimeEndOnUtc = MyDate.convert(shift.shiftTimeEndOnUtc);
                        shift.shiftTimeStartOnUtc = MyDate.convert(shift.shiftTimeStartOnUtc);
                        shift.save();
                    }
                } else {
                    alert("Failed to download shifts");
                }

                if (Profile.role() == 1) {
                    toActivity(StaffHomeActivity.class);
                } else if (Profile.role() == 2) {
                    new RequestTask(this, "GET", null, Profile.token(), false).execute(Api.GET_USERS);
                } else {
                    toActivity(SignInActivity.class);
                }

                break;
            case Api.GET_USERS:

                toActivity(BossHomeActivity.class);

                break;
        }

    }

    @Override
    public void onBackPressed() {
        return;
    }
}
