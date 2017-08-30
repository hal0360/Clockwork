package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.widget.TextView;

import nz.co.udenbrothers.clockwork.temps.Profile;

public class StaffEditProfileActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_edit_profile);

        TextView editFname = findViewById(R.id.editEditFname);
        TextView editLname = findViewById(R.id.editEditLname);
        TextView editMail = findViewById(R.id.editEditMail);

        editFname.setText(Profile.firstName());
        editLname.setText(Profile.lastName());
        editMail.setText(Profile.email());

        clicked(R.id.addCompButton,()-> pushActivity(JoinBussActivity.class));
    }
}
