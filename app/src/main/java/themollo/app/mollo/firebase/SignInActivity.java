package themollo.app.mollo.firebase;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import themollo.app.mollo.R;
import themollo.app.mollo.survey.DoSurveyActivity;

public class SignInActivity extends FirebaseLogin {

    @BindView(R.id.etUserID)
    EditText etUserID;
    @BindView(R.id.etUserPwd)
    EditText etUserPwd;
    @BindView(R.id.llSignUp)
    LinearLayout llSignUp;
    @BindView(R.id.llFindPwd)
    LinearLayout llFindPwd;
    @BindView(R.id.llSignIn)
    LinearLayout llSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setButtonListener();
    }

    @Override
    public void setButtonListener() {
        llSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(SignUpActivity.class);
            }
        });

        llFindPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(FindPwdActivity.class);
            }
        });

        llSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(DoSurveyActivity.class);
            }
        });
    }
}
