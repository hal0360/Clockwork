package nz.co.udenbrothers.clockwork;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.global.URL;
import nz.co.udenbrothers.clockwork.serverObjects.Profile;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class SignUpActivity extends MainActivity implements AsynCallback {

    private CheckBox terms;
    private EditText Efname, Elname, Email, Epass, Epass2;

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

        clicked(terms, ()->{
            terms.setChecked(false);
        });
        clicked(R.id.staffCreateButton, this::createAccount);
    }

    public void postCallback(Response response){
        unblock();
        if(response.statusCode == 204){
            alert("Sign up successful");
            toActivity(SignInActivity.class);
        }
        else if(response.statusCode == 400){
            Email.requestFocus();
            Email.setError("Email already in use");
        }
        else {
            alert("Problem with connection or server. Try again later");
        }
    }

    private void createAccount(){
        String fusr = Efname.getText().toString().trim();
        String lusr = Elname.getText().toString().trim();
        String pass = Epass.getText().toString().trim();
        String pass2 = Epass2.getText().toString().trim();
        String mail = Email.getText().toString().trim();

        if (fusr.equals("")){
            Efname.requestFocus();
            Efname.setError("Invalid name");
            return;
        }

        if (fusr.equals("")){
            Elname.requestFocus();
            Elname.setError("Invalid name");
            return;
        }

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

        if (!pass.equals(pass2)){
            Epass2.requestFocus();
            Epass2.setError("Password not match");
            Epass2.setText("");
            return;
        }

        if (!terms.isChecked()){
            Toast.makeText(this, "Please agree to terms and conditions", Toast.LENGTH_SHORT).show();
            return;
        }

        Profile profile = new Profile(fusr, lusr, mail, pass);
        block("Please wait....");
        new RequestTask(this,"POST", profile.toJson(), null).execute(URL.SIGNUP);
    }

    @Override
    public void onBackPressed() {
        toActivity(SignInActivity.class);
    }
}
