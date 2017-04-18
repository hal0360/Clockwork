package nz.co.udenbrothers.clockwork;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;

public class SignUpBusinessActivity extends MyActivity implements AsynCallback {

    private CheckBox terms;
    private SharedPreferences pref;
    private EditText Ename, Ecomp, Email, Epass, Epass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_business);
        pref = getSharedPreferences("app", MODE_PRIVATE);
        Ename = (EditText) findViewById(R.id.editBussName);
        Ecomp = (EditText) findViewById(R.id.editBussCom);
        Email = (EditText) findViewById(R.id.editBussMail);
        Epass = (EditText) findViewById(R.id.editBussPass);
        Epass2 = (EditText) findViewById(R.id.editBussPass2);
        terms = (CheckBox) findViewById(R.id.termCond);
        terms.setOnClickListener(this);
        terms.setText(Html.fromHtml("I agree to the <b>Terms and Service</b>"));
        if(pref.getBoolean("accepted", false)){
            terms.setChecked(true);
        }
        findViewById(R.id.businessCreateButton).setOnClickListener(this);
    }

    public void postCallback(String result){

    }

    @Override
    public Context getContex() {
        return this;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.termCond:

                break;
            case R.id.businessCreateButton:
                String usr = Ename.getText().toString().trim();
                String pass = Epass.getText().toString().trim();
                String pass2 = Epass2.getText().toString().trim();
                String mail = Email.getText().toString().trim();
                String comp = Ecomp.getText().toString().trim();

                if (comp.equals("")){
                    Ecomp.requestFocus();
                    Ecomp.setError("Invalid name");
                    return;
                }

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


                break;
        }
    }
}
