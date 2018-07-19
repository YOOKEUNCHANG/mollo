package themollo.app.mollo.firebase;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.opengl.ETC1;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kakao.usermgmt.request.SignupRequest;

import butterknife.BindView;
import themollo.app.mollo.R;

public class SignUpActivity extends FirebaseLogin {

    @BindView(R.id.etUserNewName)
    EditText etUserNewName;
    @BindView(R.id.etUserNewPwd)
    EditText etUserNewPwd;
    @BindView(R.id.etUserNewID)
    EditText etUserNewID;
    @BindView(R.id.etUserNewPwdCheck)
    EditText etUserNewPwdCheck;
    @BindView(R.id.cbAgree)
    CheckBox cbAgree;
    @BindView(R.id.llSignUp)
    LinearLayout llSignUp;

    private String userNewEmail="", userNewPwd="", userNewName="", userNewPwdCheck="";
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userNewEmail = etUserNewID.getText().toString();
        userNewPwd = etUserNewPwd.getText().toString();
        userNewPwdCheck = etUserNewPwdCheck.getText().toString();
        userNewName = etUserNewName.getText().toString();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(getFirebaseUser() != null){
                    //sign in
                }else{
                    //sign out
                }
            }
        };
    }

    private boolean isPwdEquals(String pwd1, String pwd2){
        if(pwd1.equals(pwd2)) return true;
        else return false;
    }

    @Override
    public void setButtonListener() {
        llSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbAgree.isChecked() && isPwdEquals(userNewPwd, userNewPwdCheck)){
                    emailSignUp();
                }else if(!cbAgree.isChecked()){
                    Toast.makeText(SignUpActivity.this, "약관에 동의해주세요", Toast.LENGTH_LONG).show();
                }else if(!isPwdEquals(userNewPwd, userNewPwdCheck)){
                    etUserNewPwdCheck.setError("비밀번호가 일치하지 않습니다!");
                    return;
                }
            }
        });
    }

    public void emailSignUp(){
        showPD();
        getFirebaseAuth().createUserWithEmailAndPassword(userNewEmail, userNewPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "계정이 생성되었습니다.", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(SignUpActivity.this, "계정 생성에 실패하였습니다", Toast.LENGTH_LONG).show();
                }
                stopPD();
            }
        });
    }
}
