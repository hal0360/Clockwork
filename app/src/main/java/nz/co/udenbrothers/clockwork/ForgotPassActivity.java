package nz.co.udenbrothers.clockwork;

import android.os.Bundle;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.global.URL;
import nz.co.udenbrothers.clockwork.serverObjects.PassMail;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class ForgotPassActivity extends MainActivity  implements AsynCallback {

    private EditText Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        Email = (EditText) findViewById(R.id.editMail);

        clicked(R.id.passResetButton,()->{
            String mail = Email.getText().toString().trim();
            Matcher m = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*$").matcher(mail);
            if (!m.matches( )) {
                Email.requestFocus();
                Email.setError("Invaid email");
                return;
            }

            PassMail passMail = new PassMail(mail);
            new RequestTask(this,"POST",passMail.toJson(),null).execute(URL.FORGOT_PASS);
            pref.putStr("profileEmail",mail);
            block("Please wait....");
        });
    }

    @Override
    public void onBackPressed() {
        toActivity(SignInActivity.class);
    }

    @Override
    public void postCallback(Response response) {
        unblock();
        if(response.statusCode >= 200 && response.statusCode < 300){
            alert("successful");
        }
        else {
            alert("Problem with connection or server. Try again later");
        }
    }

}
