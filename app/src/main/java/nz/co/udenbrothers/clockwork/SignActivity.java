package nz.co.udenbrothers.clockwork;

import android.os.Bundle;

public class SignActivity extends MyActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        clicked(R.id.staffButton,()-> toActivity(SignUpStaffActivity.class));
        clicked(R.id.businessButton,()-> toActivity(SignUpBusinessActivity.class));
        clicked(R.id.signinText,()-> toActivity(SignInActivity.class));
    }
}
