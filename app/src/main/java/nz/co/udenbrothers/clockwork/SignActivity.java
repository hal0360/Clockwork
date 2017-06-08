package nz.co.udenbrothers.clockwork;

import android.os.Bundle;

public class SignActivity extends MyActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        clicked(R.id.signUpButton,()-> toActivity(SignUpActivity.class));
        clicked(R.id.signInButton,()-> toActivity(SignInActivity.class));
    }
}
