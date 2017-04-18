package nz.co.udenbrothers.clockwork.itemRecycler.items;

import android.content.Context;

import nz.co.udenbrothers.clockwork.global.Type;
import nz.co.udenbrothers.clockwork.models.Site;

/**
 * Created by user on 09/04/2017.
 */

public class SiteItem extends Item{

    public Site site;
    public boolean active = false;

    public SiteItem(int type, Site site, Context context) {
        super(type, context);
        if(type != Type.SITE){
            throw new IllegalArgumentException("INVALID ITEM TYPE");
        }
        this.site =site;
    }
}
