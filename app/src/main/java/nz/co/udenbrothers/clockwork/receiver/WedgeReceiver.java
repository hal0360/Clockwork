package nz.co.udenbrothers.clockwork.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import nz.co.udenbrothers.clockwork.serverices.WedgeService;
import nz.co.udenbrothers.clockwork.temps.Act;
import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.temps.Setting;

public class WedgeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Profile.role() == 0) return;
        if(Act.current() == null) return;
        if(!Setting.wedge()) return;
        context.startService(new Intent(context, WedgeService.class));
    }
}
