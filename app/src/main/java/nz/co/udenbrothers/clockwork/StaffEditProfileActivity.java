package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.widget.TextView;

public class StaffEditProfileActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_edit_profile);

        TextView editFname = (TextView) findViewById(R.id.editEditFname);
        TextView editLname = (TextView) findViewById(R.id.editEditLname);
        TextView editMail = (TextView) findViewById(R.id.editEditMail);

        editFname.setText(pref.getStr("firstName"));
        editLname.setText(pref.getStr("lastName"));
        editMail.setText(pref.getStr("profileEmail"));

        clicked(R.id.addCompButton,()-> pushActivity(JoinBussActivity.class));
    }
}
