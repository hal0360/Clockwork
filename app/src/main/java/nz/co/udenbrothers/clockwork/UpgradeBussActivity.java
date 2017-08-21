package nz.co.udenbrothers.clockwork;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.global.URL;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.serverObjects.Upgrade;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class UpgradeBussActivity extends MainActivity implements AsynCallback {

    private EditText Ecom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_buss);

        Ecom = (EditText) findViewById(R.id.companyEdit);

        clicked(R.id.upgradeComfirmButton,()->{
            String com = Ecom.getText().toString().trim();

            if (com.equals("")){
                Ecom.requestFocus();
                Ecom.setError("Invalid name");
            }
            else {
                Upgrade upgrade = new Upgrade(com);
                new RequestTask(this,"POST", upgrade.toJson(), pref.getStr("token")).execute(URL.UPGRADE_BUSS);
                block("Ungrading");
            }
        });
    }

    @Override
    public void postCallback(Response response) {
        unblock();
        if(response.statusCode == 204){
            pref.putInt("profileRole",2);
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
