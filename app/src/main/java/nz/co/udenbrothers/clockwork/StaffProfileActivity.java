package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.widget.TextView;

import nz.co.udenbrothers.clockwork.global.Type;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.itemFactories.ItemMaker;
import nz.co.udenbrothers.clockwork.models.Notice;

public class StaffProfileActivity extends StaffActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_profile);

        clicked(R.id.imageHam, ()-> sideMenu.show());

        TextView name = findViewById(R.id.profileNameTxt);
        name.setText(pref.getStr("firstName") + " " + pref.getStr("lastName"));
        TextView email = findViewById(R.id.profileMailTxt);
        email.setText(pref.getStr("profileEmail"));
        TextView company = findViewById(R.id.profileComTxt);
        if(pref.getStr("company").equals("")){
            company.setText("N/A");
        }else {
            company.setText(pref.getStr("company"));
        }

        clicked(R.id.editProfileButton,() -> pushActivity(StaffEditProfileActivity.class));

        ItemMaker itemMaker = new ItemMaker(this);
        CollectionView collectionView = findViewById(R.id.noticeList);
        collectionView.init(itemMaker.toItems(Notice.get(this), Type.NOTICE));

    }
}
