package nz.co.udenbrothers.clockwork.abstractions;

import android.content.Context;

/**
 * Created by user on 02/04/2017.
 */

public interface RecycleCallback {

    public void deleteProject(String name, int pos);

    public void forceStop();

    public Object getObj();

    public void moreInfo(String name);

}
