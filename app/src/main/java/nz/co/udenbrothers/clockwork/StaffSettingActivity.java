package nz.co.udenbrothers.clockwork;

import android.os.Bundle;

public class StaffSettingActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_setting);

        clicked(R.id.imageHam, this::showMenu);
    }
}
