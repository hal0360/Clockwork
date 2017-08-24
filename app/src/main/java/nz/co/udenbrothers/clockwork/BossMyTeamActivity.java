package nz.co.udenbrothers.clockwork;

import android.os.Bundle;

public class BossMyTeamActivity extends BossActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_my_team);

        clicked(R.id.imageHam, ()-> sideMenu.show());

        clicked(R.id.addStaffButton, ()-> pushActivity(AddStaffActivity.class));

    }
}
