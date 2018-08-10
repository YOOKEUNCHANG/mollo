package themollo.app.mollo.firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;

public class SignUpActivity extends FirebaseLogin {

    private final String TAG = "SignUpActivity";

    @BindView(R.id.etUserNewName)
    EditText etUserNewName;
    @BindView(R.id.etUserNewPwd)
    EditText etUserNewPwd;
    @BindView(R.id.etUserNewID)
    EditText etUserNewID;
    @BindView(R.id.etUserNewPwdCheck)
    EditText etUserNewPwdCheck;
    @BindView(R.id.llSignUp)
    LinearLayout llSignUp;
    @BindView(R.id.llBack)
    LinearLayout llBack;

    private String userNewEmail = "", userNewPwd = "", userNewName = "", userNewPwdCheck = "";
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        butterBind();
        setButtonListener();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (getFirebaseUser() != null) {
                    //sign in
                } else {
                    //sign out
                }
            }
        };
    }

    private boolean isPwdEquals(String pwd1, String pwd2) {
        if (pwd1.equals(pwd2)) return true;
        else return false;
    }

    @Override
    public void setButtonListener() {
        userNewPwd = etUserNewPwd.getText().toString();
        userNewPwdCheck = etUserNewPwdCheck.getText().toString();
        llSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPwdEquals(userNewPwd, userNewPwdCheck)) {
                    Toast.makeText(SignUpActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                    return;
                }

                emailSignUp();
            }
        });

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }

    public void emailSignUp() {
        showPD();
        userNewEmail = etUserNewID.getText().toString();
        userNewPwd = etUserNewPwd.getText().toString();
        try {
            getFirebaseAuth().createUserWithEmailAndPassword(userNewEmail, userNewPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "계정이 생성되었습니다.", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        stopPD();
                        return;
                    }
                    stopPD();
                }
            });
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            stopPD();
            return;
        }
    }
}
