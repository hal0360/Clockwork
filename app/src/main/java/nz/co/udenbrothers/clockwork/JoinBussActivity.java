package nz.co.udenbrothers.clockwork;

import android.content.Intent;
import android.os.Bundle;

import com.google.zxing.integration.android.IntentIntegrator;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.global.URL;
import nz.co.udenbrothers.clockwork.serverObjects.LinkInfo;
import nz.co.udenbrothers.clockwork.serverObjects.LoginInfo;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.QRView;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class JoinBussActivity extends StaffActivity implements AsynCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_buss);

        QRView qrView = (QRView) findViewById(R.id.qrCode);
        qrView.set(pref.getStr("userId"));

        clicked(R.id.openScanerButton, ()-> new IntentIntegrator(this).initiateScan());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String cid = Kit.QrScanResult(IntentIntegrator.parseActivityResult(requestCode, resultCode, data));
        if(cid.equals("")) return;
        LinkInfo linkInfo = new LinkInfo(pref.getStr("userId"));
        new RequestTask(this,"POST", linkInfo.toJson(),null).execute(URL.LINK_BUSS(cid));
    }

    @Override
    public void postCallback(Response response) {
        if(response.statusCode >= 200 && response.statusCode < 300){
            alert(" successful");
            finish();
        }
        else {
            alert("Problem with connection or server. Try again later");
        }
    }
}
