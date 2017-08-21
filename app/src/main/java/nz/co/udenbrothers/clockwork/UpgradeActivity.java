package nz.co.udenbrothers.clockwork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import nz.co.udenbrothers.clockwork.util.IabHelper;


public class UpgradeActivity extends MainActivity {

    private ProgressDialog mDialog;
    private static final String TAG = "InAppBilling";
    IabHelper mHelper;
    static final String ITEM_SKU = "business_subscription";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please wait...");
        mDialog.show();

        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkrF+o0Ii99Vh1iJ9tIrNpiiCeDkb46HlW+WzNE3GaJxfG18Fc9IJfKvpQlsev9MP4op8v6KxIrRGR5H6xpqp34OFvraHoTd/VDdJfQ8nMzVbgpV+cwcsTAwVnIdtwzpBaF5NFaFGDTlL0bbUxVawxRoyYas7E6M4lDoKVO7x0hm4bJ1hyXvPRPfebFzvIyNjn6z4ziryVn2fDH/OuE2jw2LynKNH8BCKhfbEoxaV9tTBL/v/4LsO8eN6cHKiuC0ME9d0BwG6kcQ7hMRIexni2PjVw5NKYp1ME4FEFfMaWeiux1id63/AIAdeAowUe7J1ELCCweO1jdlC/W+GXl9UJwIDAQAB";
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.startSetup(result -> {
            mDialog.dismiss();
            if (!result.isSuccess()) {
                Log.d(TAG, "In-app Billing setup failed: " + result);
                alert("Fatal Error");
                finish();
            } else {
                Log.d(TAG, "In-app Billing is set up OK");
            }
        });

        clicked(R.id.buyit,()-> {
            try {
                mHelper.launchPurchaseFlow(this, ITEM_SKU, 10001, mPurchaseFinishedListener, pref.getStr("userId"));
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

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = (result, purchase) -> {
        if (result.isFailure()) {
            Log.d(TAG, "failed: " + result);
            alert("Error: Pls try again");
        }
        else{
            //  purchase.getSku().equals(ITEM_SKU)
            toActivity(UpgradeBussActivity.class);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

}