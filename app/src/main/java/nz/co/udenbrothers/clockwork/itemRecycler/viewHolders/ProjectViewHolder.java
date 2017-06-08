package nz.co.udenbrothers.clockwork.itemRecycler.viewHolders;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.dao.StampDAO;
import nz.co.udenbrothers.clockwork.dao.WorkSiteDAO;
import nz.co.udenbrothers.clockwork.itemRecycler.items.Item;
import nz.co.udenbrothers.clockwork.itemRecycler.items.SiteItem;
import nz.co.udenbrothers.clockwork.models.Site;
import nz.co.udenbrothers.clockwork.models.Stamp;
import nz.co.udenbrothers.clockwork.models.WorkSite;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.Pref;

/**
 * Created by user on 05/06/2017.
 */

public class ProjectViewHolder extends ItemHolder{

    private boolean isViewExpanded = false;
    private TextView title, yesterday, week, month, records;
    private View activeDot;

    public ProjectViewHolder(View v) {
        super(v);
        title =  (TextView) v.findViewById(R.id.cardTitle);
        yesterday =  (TextView) v.findViewById(R.id.yesterdayHour);
        week =  (TextView) v.findViewById(R.id.weekHour);
        month =  (TextView) v.findViewById(R.id.monthHour);
        records =  (TextView) v.findViewById(R.id.recordTxt);
        activeDot = v.findViewById(R.id.activeDot);

        clicked(v.findViewById(R.id.moreInfoButton), ()->{});

        clicked(card, ()->{
            if(isViewExpanded){
                expandView(Kit.dps(120));
                isViewExpanded = false;
            }
            else {
                expandView(Kit.dps(215));
                isViewExpanded = true;
            }
        });
    }

    public void init(Item item){
        SiteItem siteItem = (SiteItem) item;
        Site site = siteItem.site;
        setHeight(Kit.dps(120));
        title.setText(site.name);

        StampDAO stampDAO = new StampDAO(siteItem.context);
        long yseterdayTotal = stampDAO.getBeforeTotal("site_name", site.name, 1);
        yesterday.setText(Kit.gethourMin(yseterdayTotal));

        long weekTotal = stampDAO.getBeforeTotal("site_name", site.name, 7);
        week.setText(Kit.gethourMin(weekTotal));

        long monthTotal = stampDAO.getBeforeTotal("site_name", site.name, 30);
        month.setText(Kit.gethourMin(monthTotal));

        WorkSiteDAO workSiteDAO = new WorkSiteDAO(siteItem.context);
        ArrayList<WorkSite> workSites = workSiteDAO.getBy("site_id", site.id);
        records.setText(workSites.size() + " workers in this project");

        Pref pref = new Pref(siteItem.context);
        if(pref.getStr("currentSite").equals(site.name)){
            activeDot.setBackgroundResource( R.drawable.green_dot );
        }
        else {
            activeDot.setBackgroundResource( R.drawable.red_dot );
        }
    }

}
