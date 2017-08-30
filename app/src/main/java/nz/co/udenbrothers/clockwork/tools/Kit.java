package nz.co.udenbrothers.clockwork.tools;

import android.content.Context;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentResult;

import nz.co.udenbrothers.clockwork.temps.Screen;

public class Kit {

    public static String QrScanResult(IntentResult result){
        if(result != null) {
            if (result.getContents() == null) {
                return "";
            }
            return result.getContents();
         //   return newRes.replaceAll("'","\'");
        }
        else {return "";}
    }

    public static void show(Context context, String mss){
        Toast.makeText(context, mss, Toast.LENGTH_LONG).show();
    }

    public static int dps(int dp){
        return (int)((dp * Screen.density) + 0.5);
    }

}
