package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.widget.TextView;

import nz.co.udenbrothers.clockwork.global.V;
import nz.co.udenbrothers.clockwork.itemRecycler.CollectionView;
import nz.co.udenbrothers.clockwork.itemRecycler.itemFactories.ItemMaker;
import nz.co.udenbrothers.clockwork.models.Notice;
import nz.co.udenbrothers.clockwork.sql_stuff.SQL;
import nz.co.udenbrothers.clockwork.temps.Profile;

public class StaffProfileActivity extends StaffActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_profile);

        clicked(R.id.imageHam, ()-> sideMenu.show());

        TextView name = findViewById(R.id.profileNameTxt);
        name.setText(Profile.firstName() + " " + Profile.lastName());
        TextView email = findViewById(R.id.profileMailTxt);
        email.setText(Profile.email());
        TextView company = findViewById(R.id.profileComTxt);
        if(Profile.company() == null){
            company.setText("N/A");
        }else {
            company.setText(Profile.company());
        }

        clicked(R.id.editProfileButton,() -> pushActivity(StaffEditProfileActivity.class));

        ItemMaker itemMaker = new ItemMaker(this);
        CollectionView collectionView = findViewById(R.id.noticeList);
        collectionView.init(itemMaker.toItems(SQL.get(Notice.class), V.NOTICE));

    }
}
