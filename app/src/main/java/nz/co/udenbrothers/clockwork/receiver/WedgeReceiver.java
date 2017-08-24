package nz.co.udenbrothers.clockwork.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import nz.co.udenbrothers.clockwork.BossHomeActivity;
import nz.co.udenbrothers.clockwork.StaffHomeActivity;
import nz.co.udenbrothers.clockwork.serverices.WedgeService;
import nz.co.udenbrothers.clockwork.tools.Pref;

public class WedgeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Pref pref = new Pref(context);

        if(pref.getInt("profileRole") == 0) return;
        if(pref.getStr("currentProject").equals("")) return;
        if(!pref.getBool("wedge",true)) return;

        context. startService(new Intent(context, WedgeService.class));

    }
}
