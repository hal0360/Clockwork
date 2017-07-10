package nz.co.udenbrothers.clockwork.tools;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import nz.co.udenbrothers.clockwork.global.Screen;
import nz.co.udenbrothers.clockwork.itemRecycler.ItemAdaptor;

public class Kit {

    public static String QrScanResult(IntentResult result){
        if(result != null) {
            if (result.getContents() == null) {
                return "";
            }
            String newRes  = result.getContents();
            return newRes.replaceAll("'","\'");
        }
        else {return "";}
    }

    public static void recyclerSetup(Context context, RecyclerView recyclerView, ItemAdaptor itemAdaptor){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(itemAdaptor);
    }

    public static void show(Context context, String mss){
        Toast.makeText(context, mss, Toast.LENGTH_LONG).show();
    }

    public static int dps(int dp){
        return (int)((dp * Screen.density) + 0.5);
    }

    public static Dialog getDialog(Context context, int id){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(id);
        return dialog;
    }
}
