package nz.co.udenbrothers.clockwork.abstractions;


import android.content.Context;

import nz.co.udenbrothers.clockwork.serverObjects.Response;

public interface AsynCallback {
    public void postCallback(Response response);
}
