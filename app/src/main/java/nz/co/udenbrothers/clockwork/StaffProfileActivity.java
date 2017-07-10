package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import nz.co.udenbrothers.clockwork.itemRecycler.ItemAdaptor;
import nz.co.udenbrothers.clockwork.itemRecycler.itemFactories.NoticeItemMaker;
import nz.co.udenbrothers.clockwork.tools.Kit;

public class StaffProfileActivity extends StaffActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_profile);

     //   clicked(R.id.editProfileButton,()-> toActivity(StaffEditProfileActivity.class));

        clicked(R.id.imageHam, this::showMenu);
        TextView name = (TextView) findViewById(R.id.profileNameTxt);
        name.setText(pref.getStr("firstName") + " " + pref.getStr("lastName"));
        TextView email = (TextView) findViewById(R.id.profileMailTxt);
        email.setText(pref.getStr("profileEmail"));
        TextView company = (TextView) findViewById(R.id.profileComTxt);
        if(pref.getStr("company").equals("")){
            company.setText("N/A");
        }else {
            company.setText(pref.getStr("company"));
        }

        clicked(R.id.editProfileButton,() -> pushActivity(StaffEditProfileActivity.class));

        NoticeItemMaker noticeItemMaker = new NoticeItemMaker(this);
        ItemAdaptor itemAdaptor = new ItemAdaptor(noticeItemMaker.fetch());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.noticeList);
        Kit.recyclerSetup(this,recyclerView, itemAdaptor);

    }
}
