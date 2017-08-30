package nz.co.udenbrothers.clockwork;

import android.os.Bundle;

import nz.co.udenbrothers.clockwork.abstractions.AsynCallback;
import nz.co.udenbrothers.clockwork.customWigets.TextInput;
import nz.co.udenbrothers.clockwork.global.Api;
import nz.co.udenbrothers.clockwork.serverObjects.PassMail;
import nz.co.udenbrothers.clockwork.serverObjects.Response;
import nz.co.udenbrothers.clockwork.tools.Match;
import nz.co.udenbrothers.clockwork.tools.RequestTask;

public class ForgotPassActivity extends MainActivity  implements AsynCallback {

    private TextInput Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        Email = findViewById(R.id.editMail);

        clicked(R.id.passResetButton,()->{
            String mail = Email.getTxt();
            if (!Match.mail(mail)) {
                Email.error("Invaid email");
                return;
            }
            PassMail passMail = new PassMail(mail);
            new RequestTask(this,"POST",passMail.toJson(),null).execute(Api.FORGOT_PASS);
        });
    }

    @Override
    public void onBackPressed() {
        toActivity(SignInActivity.class);
    }

    @Override
    public void postCallback(Response response) {
        if(response.statusCode >= 200 && response.statusCode < 300){
            alert("successful");
        }
        else {
            alert("Problem with connection or server. Try again later");
        }
    }

}
