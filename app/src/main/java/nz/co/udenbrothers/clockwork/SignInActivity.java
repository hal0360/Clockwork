package nz.co.udenbrothers.clockwork;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class SignInActivity extends MyActivity  implements AsynCallback {

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
            new RequestTask(this,"POST","dssd",null).execute("http://yoobie-api.azurewebdfsfsfsites.net/login");

            pref.putStr("profileName","Ron");
            pref.putStr("profileEmail",mail);
        });
    }

    @Override
    public void postCallback(String result) {
        toActivity(StaffHomeActivity.class);
    }

    @Override
    public Context getContex() {
        return this;
    }
}
