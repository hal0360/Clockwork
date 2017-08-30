package nz.co.udenbrothers.clockwork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import nz.co.udenbrothers.clockwork.global.INAPP;
import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.util.IabHelper;


public class UpgradeActivity extends MainActivity {

    private IabHelper mHelper;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        mHelper = new IabHelper(this, INAPP.PUB_KEY);
        mHelper.startSetup(result -> {
            progressDialog.dismiss();
            if (!result.isSuccess()) {
                Log.d(INAPP.TAG, "In-app Billing setup failed: " + result);
                alert("Fatal Error");
                finish();
            } else {
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });

        clicked(R.id.buyit,()-> {
            try {
                mHelper.launchPurchaseFlow(this, INAPP.BUSS_SKU, 10001, mPurchaseFinishedListener, Profile.userID());
            }
            catch(IllegalStateException ex){
                alert("Error: Pls try again");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = (result, inventory) -> {
        if (result.isFailure()) {
            alert("Failed to query inventory: " + result);
            return;
        }
        if(inventory.hasPurchase(INAPP.BUSS_SKU)){
            toActivity(UpgradeBussActivity.class);
        }

    };

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = (result, purchase) -> {
        if (result.isFailure()) {
            Log.d(INAPP.TAG, "failed: " + result);
            alert("Error: Pls try again");
            return;
        }
        if(purchase.getSku().equals(INAPP.BUSS_SKU)){
            toActivity(UpgradeBussActivity.class);
        }
        else {
            alert("Something is wrong. Pls contact the owner of this app");
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

}