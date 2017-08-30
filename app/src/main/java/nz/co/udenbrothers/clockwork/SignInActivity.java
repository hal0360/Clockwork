package nz.co.udenbrothers.clockwork;

import android.os.Bundle;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.customWigets.TextInput;
import nz.co.udenbrothers.clockwork.global.Api;
import nz.co.udenbrothers.clockwork.serverObjects.LoginInfo;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.serverObjects.SigninInfo;
import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.tools.Json;
import nz.co.udenbrothers.clockwork.tools.Match;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class SignInActivity extends MainActivity implements AsynCallback {

    private TextInput Email, Epass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Email = findViewById(R.id.editMail);
        Epass = findViewById(R.id.editPass);

        clicked(R.id.signInButton,()->{
            String pass = Epass.getTxt();
            String mail = Email.getTxt();

            if (!Match.mail(mail)) {
                Email.error("Invaid email");
                return;
            }

            if (pass.length() < 6){
                Epass.error("Must be at least 6 length");
                return;
            }

            SigninInfo profile = new SigninInfo("N","N",mail,pass);
            new RequestTask(this,"POST",profile.toJson(),null).execute(Api.SIGNIN);
            Profile.email(mail);
        });

        clicked(R.id.forgotPassTxt,() -> toActivity(ForgotPassActivity.class));
        clicked(R.id.signUpTxt,() -> toActivity(SignUpActivity.class));
    }

    @Override
    public void postCallback(Response response) {

        if (response.statusCode == 200) {
            alert("Sign in successful");
            LoginInfo loginInfo = Json.from(response.content, LoginInfo.class);
            if (loginInfo != null) {
                Profile.firstName(loginInfo.firstName);
                Profile.lastName(loginInfo.lastName);
                Profile.role(loginInfo.userRoleId + 1);
                Profile.token(loginInfo.apiToken);
                Profile.userID(loginInfo.userId);

                toActivity(LoadingActivity.class);
            } else {
                alert("Error occured");
            }
        } else if (response.statusCode == 404) {
            Email.requestFocus();
            Email.setError("Email not found");
        } else if (response.statusCode == 401) {
            Epass.requestFocus();
            Epass.setError("Invalid password");
        } else {
            alert("Problem with connection or server. Try again later");
        }

    }

}
