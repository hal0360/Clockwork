package nz.co.udenbrothers.clockwork.tools;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import nz.co.udenbrothers.clockwork.global.Screen;
import nz.co.udenbrothers.clockwork.itemRecycler.ItemAdaptor;

public class Kit {

    public static float hoursDifference(Date date1, Date date2) {
        final float MILLI_TO_HOUR = 1000 * 60 * 60.0f;
        return (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
    }

    public static void recyclerSetup(Context context, RecyclerView recyclerView, ItemAdaptor itemAdaptor){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(itemAdaptor);
    }

    public static String gethourMin(long total){
        long secs = total / 1000;
        long hours = secs / 3600;
        secs = secs % 3600;
        long mins = secs / 60;
        return hours + "h " + mins + "m";
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

    public static String dateToStr(Date date, String format){
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
        df.setTimeZone(TimeZone.getDefault());
        return df.format(date);
    }

    public static Date strToDate(String dateStr){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String dateToStr(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getDefault());
        return df.format(date);
    }
}
