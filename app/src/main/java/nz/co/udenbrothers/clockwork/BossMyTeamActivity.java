package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import nz.co.udenbrothers.clockwork.abstractions.RecycleCallback;
import nz.co.udenbrothers.clockwork.global.Type;
import nz.co.udenbrothers.clockwork.itemRecycler.itemFactories.ItemMaker;
import nz.co.udenbrothers.clockwork.models.User;
import nz.co.udenbrothers.clockwork.tools.Kit;

public class BossMyTeamActivity extends BossActivity implements RecycleCallback {


    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_my_team);

        /*
        String s = getIntent().getStringExtra("EXTRA_SESSION_ID");
        recyclerView = (RecyclerView) findViewById(R.id.workerList);
        ItemMaker itemMaker = new ItemMaker(this);
        itemAdaptor = new ItemAdaptor(itemMaker.toItems(User.get(this),Type.USER));
        Kit.recyclerSetup(this,recyclerView, itemAdaptor);
*/

        clicked(R.id.imageHam, this::showMenu);
    }

    @Override
    public void delete(int pos) {

    }

    @Override
    public void forceStop() {

    }

    @Override
    public Object getObj() {
        return null;
    }

    @Override
    public void moreInfo(String name) {

    }
}
