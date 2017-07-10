package nz.co.udenbrothers.clockwork;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.serverObjects.LoginInfo;
import nz.co.udenbrothers.clockwork.serverObjects.Profile;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class SignInActivity extends MainActivity  implements AsynCallback {

    private EditText Email, Epass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Email = (EditText) findViewById(R.id.editMail);
        Epass = (EditText) findViewById(R.id.editPass);

        clicked(R.id.signInButton,()->{
            String pass = Epass.getText().toString().trim();
            String mail = Email.getText().toString().trim();
            Matcher m = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*$").matcher(mail);
            if (!m.matches( )) {
                Email.requestFocus();
                Email.setError("Invaid email");
                return;
            }

            if (pass.length() < 6){
                Epass.requestFocus();
                Epass.setError("Must be at least 6 length");
                Email.setText("");
                return;
            }

            Profile profile = new Profile("N","N",mail,pass);
            new RequestTask(this,"POST",profile.toJson(),null,true).execute("https://clockwork-api.azurewebsites.net/v1/authentication/login");
            pref.putStr("profileEmail",mail);
        });
    }

    @Override
    public void postCallback(Response response) {
        if(response.statusCode == 200){
            alert("Sign in successful");
            LoginInfo loginInfo = LoginInfo.fromJsom(response.content);
            if (loginInfo != null) {
                pref.putStr("firstName",loginInfo.firstName);
                pref.putStr("lastName",loginInfo.lastName);
                pref.putStr("token",loginInfo.apiToken);
                pref.putInt("profileRole",1);
                pref.putStr("userId",loginInfo.userId);
                toActivity(StaffHomeActivity.class);
            }
            else{
                alert("Error occured");
            }
        }
        else if(response.statusCode == 404){
            Email.requestFocus();
            Email.setError("Invalid mail or password");
        }
        else {
            alert("Problem with connection or server. Try again later");
        }
    }

    @Override
    public Context getContex() {
        return this;
    }

    @Override
    public void onBackPressed() {
        toActivity(SignActivity.class);
    }
}
