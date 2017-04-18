package nz.co.udenbrothers.clockwork.abstractions;


import android.content.Context;

public interface AsynCallback {
    public void postCallback(String result);
    public Context getContex();
}
