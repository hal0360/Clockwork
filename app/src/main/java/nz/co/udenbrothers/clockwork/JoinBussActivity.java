package nz.co.udenbrothers.clockwork;

import android.content.Intent;
import android.os.Bundle;

import com.google.zxing.integration.android.IntentIntegrator;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.global.Api;
import nz.co.udenbrothers.clockwork.serverObjects.LinkInfo;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.customWigets.QRView;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class JoinBussActivity extends MainActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_buss);

        QRView qrView = findViewById(R.id.qrCode);
        qrView.set(Profile.userID());

        clicked(R.id.openScanerButton, ()-> new IntentIntegrator(this).initiateScan());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String bid = Kit.QrScanResult(IntentIntegrator.parseActivityResult(requestCode, resultCode, data));
        if(bid.equals("")) return;
      //  LinkInfo linkInfo = new LinkInfo(pref.getStr("userId"));
       // new RequestTask(this,"POST", linkInfo.toJson(),null).execute(Api.LINK_BUSS(cid));
    }

}
