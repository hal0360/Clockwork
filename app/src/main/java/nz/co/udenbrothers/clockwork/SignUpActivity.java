package nz.co.udenbrothers.clockwork;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.serverObjects.Profile;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.tools.Kit;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class SignUpActivity extends MyActivity implements AsynCallback {

    private CheckBox terms;
    private EditText Ename, Email, Epass, Epass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_staff);
        Ename = (EditText) findViewById(R.id.editStaffName);
        Email = (EditText) findViewById(R.id.editStaffMail);
        Epass = (EditText) findViewById(R.id.editStaffPass);
        Epass2 = (EditText) findViewById(R.id.editStaffPass2);
        terms = (CheckBox) findViewById(R.id.termCondd);
        terms.setText(Html.fromHtml("I agree to the <b>Terms and Service</b>"));
        findViewById(R.id.staffCreateButton).setOnClickListener(this);
        Dialog termPop = Kit.getDialog(this,R.layout.terms_cons_layout);

        clicked(termPop.findViewById(R.id.acceptButton),()->{
            terms.setChecked(true);
            termPop.dismiss();
        });
        clicked(terms, ()->{
            terms.setChecked(false);
            termPop.show();
        });
        clicked(R.id.staffCreateButton, this::createAccount);
    }

    public void postCallback(Response response){
        if(response.statusCode == 204){
            alert("Sign in successful");
            toActivity(StaffHomeActivity.class);
        }
        else if(response.statusCode == 400){
            Email.requestFocus();
            Email.setError("Email already in use");
        }
        else {
            alert("Problem with connection or server. Try again later");
        }
    }

    public Context getContex(){
        return this;
    }

    private void createAccount(){
        String usr = Ename.getText().toString().trim();
        String pass = Epass.getText().toString().trim();
        String pass2 = Epass2.getText().toString().trim();
        String mail = Email.getText().toString().trim();

        if (usr.equals("")){
            Ename.requestFocus();
            Ename.setError("Invalid name");
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

        Profile profile = new Profile(usr, mail, pass);
        new RequestTask(this,"POST", profile.toJson(), null).execute("http://yoobie-api.azurewebdfsfsfsites.net/login");
        pref.putStr("profileName",usr);
        pref.putStr("profileEmail",mail);
        pref.putInt("profileRole",1);
    }
}
