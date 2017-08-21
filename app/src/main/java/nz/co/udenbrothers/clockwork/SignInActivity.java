package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.global.URL;
import nz.co.udenbrothers.clockwork.models.Project;
import nz.co.udenbrothers.clockwork.models.Shift;
import nz.co.udenbrothers.clockwork.serverObjects.GetShiftsInfo;
import nz.co.udenbrothers.clockwork.serverObjects.LoginInfo;
import nz.co.udenbrothers.clockwork.serverObjects.Profile;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class SignInActivity extends MainActivity implements AsynCallback {

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
                return;
            }

            Profile profile = new Profile("N","N",mail,pass);
            new RequestTask(this,"POST",profile.toJson(),null).execute(URL.SIGNIN);
            pref.putStr("profileEmail",mail);
        });

        clicked(R.id.forgotPassTxt,() -> toActivity(ForgotPassActivity.class));
        clicked(R.id.signUpTxt,() -> toActivity(SignUpActivity.class));
    }

    @Override
    public void postCallback(Response response) {

        if(response.url.equals(URL.GET_PROJECTS)){
            if(response.statusCode == 200){
                Project[] projects = gson.fromJson(response.content, Project[].class);
                for(Project project: projects){
                    project.save(this);
                }
            }
            else {
                alert("Failed to download projects");
            }
           // GetShiftsInfo getShiftsInfo = new GetShiftsInfo(pref.getStr("userId"));
           // new RequestTask(this,"GET",getShiftsInfo.toJson(),pref.getStr("token")).execute(URL.GET_SHIFTS);

            toActivity(SplashActivity.class);
        }
        else if(response.url.equals(URL.GET_SHIFTS)){
            if(response.statusCode == 200){
                Shift[] shifts = gson.fromJson(response.content, Shift[].class);
                for(Shift shift: shifts){
                    shift.save(this);
                }
            }
            else {
                alert("Failed to download shifts");
            }
           // toActivity(SplashActivity.class);
        }
        else {
            if(response.statusCode == 200){
                alert("Sign in successful");
                LoginInfo loginInfo = LoginInfo.fromJsom(response.content);
                if (loginInfo != null) {
                    pref.putStr("firstName",loginInfo.firstName);
                    pref.putStr("lastName",loginInfo.lastName);
                    pref.putStr("token",loginInfo.apiToken);
                    pref.putInt("profileRole",loginInfo.userRoleId + 1);
                    pref.putStr("userId",loginInfo.userId);
                    new RequestTask(this,"GET",null,loginInfo.apiToken).execute(URL.GET_PROJECTS);
                }
                else{
                    alert("Error occured");
                }
            }
            else if(response.statusCode == 404){
                Email.requestFocus();
                Email.setError("Email not found");
            }
            else if(response.statusCode == 401){
                Epass.requestFocus();
                Epass.setError("Invalid password");
            }
            else {
                alert("Problem with connection or server. Try again later");
            }
        }

    }

}
