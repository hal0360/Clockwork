package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.widget.TextView;

public class BossEditProfileActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_edit_profile);

        TextView editFname = findViewById(R.id.editEditFname);
        TextView editLname = findViewById(R.id.editEditLname);
        TextView editMail = findViewById(R.id.editEditMail);

        editFname.setText(pref.getStr("firstName"));
        editLname.setText(pref.getStr("lastName"));
        editMail.setText(pref.getStr("profileEmail"));
    }
}
