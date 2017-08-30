package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.text.Html;
import android.widget.CheckBox;
import android.widget.Toast;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.customWigets.TextInput;
import nz.co.udenbrothers.clockwork.global.Api;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.serverObjects.SigninInfo;
import nz.co.udenbrothers.clockwork.tools.Match;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class SignUpActivity extends MainActivity implements AsynCallback {

    private CheckBox terms;
    private TextInput Efname, Elname, Email, Epass, Epass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        Efname = findViewById(R.id.firstName);
        Elname = findViewById(R.id.lastName);
        Email = findViewById(R.id.editStaffMail);
        Epass = findViewById(R.id.editStaffPass);
        Epass2 = findViewById(R.id.editStaffPass2);
        terms = findViewById(R.id.termCondd);
        terms.setText(Html.fromHtml("I agree to the <b>Terms and Service</b>"));

        clicked(R.id.staffCreateButton, this::createAccount);
    }

    public void postCallback(Response response){
        if(response.statusCode == 204){
            alert("Sign up successful");
            toActivity(SignInActivity.class);
        }
        else if(response.statusCode == 400){
            Email.error("Email already in use");
        }
        else {
            alert("Problem with connection or server. Try again later");
        }
    }

    private void createAccount(){
        String fusr = Efname.getTxt();
        String lusr = Elname.getTxt();
        String pass = Epass.getTxt();
        String pass2 = Epass2.getTxt();
        String mail = Email.getTxt();

        if (fusr.equals("")){
            Efname.error("Invalid first name");
            return;
        }

        if (lusr.equals("")){
            Elname.error("Invalid last name");
            return;
        }

        if (!Match.mail(mail)) {
            Email.error("Invaid email");
            return;
        }

        if (pass.length() < 6){
            Epass.error("Must be at least 6 length");
            Email.setText("");
            return;
        }

        if (!pass.equals(pass2)){
            Epass2.error("Password not match");
            Epass2.setText("");
            return;
        }

        if (!terms.isChecked()){
            Toast.makeText(this, "Please agree to terms and conditions", Toast.LENGTH_SHORT).show();
            return;
        }

        SigninInfo profile = new SigninInfo(fusr, lusr, mail, pass);
        new RequestTask(this,"POST", profile.toJson(), null).execute(Api.SIGNUP);
    }

    @Override
    public void onBackPressed() {
        toActivity(SignInActivity.class);
    }
}
