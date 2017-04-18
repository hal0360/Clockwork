package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.widget.TextView;

public class StaffProfileActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_profile);

     //   clicked(R.id.editProfileButton,()-> toActivity(StaffEditProfileActivity.class));

        clicked(R.id.imageHam, this::showMenu);

        TextView name = (TextView) findViewById(R.id.profileNameTxt);
        name.setText(pref.getStr("profileName"));
        TextView email = (TextView) findViewById(R.id.profileMailTxt);
        email.setText(pref.getStr("profileEmail"));
    }
}
