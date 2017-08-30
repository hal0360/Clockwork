package nz.co.udenbrothers.clockwork;

import android.os.Bundle;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.customWigets.TextInput;
import nz.co.udenbrothers.clockwork.global.Api;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.serverObjects.Upgrade;
import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class UpgradeBussActivity extends MainActivity implements AsynCallback {

    private TextInput Ecom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_buss);

        Ecom = findViewById(R.id.companyEdit);

        clicked(R.id.upgradeComfirmButton,()->{
            String com = Ecom.getTxt();

            if (com.equals("")){
                Ecom.error("Invalid name");
            }
            else {
                Upgrade upgrade = new Upgrade(com);
                new RequestTask(this,"POST", upgrade.toJson(), Profile.token()).execute(Api.UPGRADE_BUSS);
            }
        });
    }

    @Override
    public void postCallback(Response response) {
        if(response.statusCode == 204){
            Profile.role(2);
            finish();
        }
        else if(response.statusCode == 400){
            Ecom.requestFocus();
            Ecom.setError("Name already exsited");
        }
        else {
            alert("Problem with connection or server. Try again later");
        }
    }

}
