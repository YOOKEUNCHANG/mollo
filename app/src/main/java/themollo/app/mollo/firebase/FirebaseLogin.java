package themollo.app.mollo.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import themollo.app.mollo.AppUtilBasement;
import themollo.app.mollo.MainActivity;
import themollo.app.mollo.login.sns_login.LoginActivity;

/**
 * Created by alex on 2018. 7. 12..
 */

public abstract class FirebaseLogin extends AppUtilBasement {

    private static final String TAG = "FirebaseLogin";

    public boolean isValid(String userEmail, String userPwd){
        if(TextUtils.isEmpty(userEmail)){
            return false;
        }
        if(TextUtils.isEmpty(userPwd)){
            return false;
        }
        return true;
    }

    public void firebaseAnonySignIn(final Context context) {
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (getFirebaseUser() != null) {
                                Log.i("firebase", getFirebaseUser().getUid());
                            }
                            Toast.makeText(context, "익명 로그인 되었습니다", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "익명 로그인 싪패", Toast.LENGTH_LONG).show();
                            Log.i("firebase", "anony login failed : " + task.getException().toString());
                        }
                    }
                });
    }

    public void firebaseEmailSignIn(String userEmail, String userPwd, final Context context) {
        showPD();
        getFirebaseAuth().signInWithEmailAndPassword(userEmail, userPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "이메일로 로그인 되었습니다.", Toast.LENGTH_LONG).show();
                    moveTo(MainActivity.class);
                }else{
                    Toast.makeText(context, "이메일로 로그인에 실패하였습니다.", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "error msg : " + task.getException());
                }
                stopPD();
            }
        });

        if(!isValid(userEmail, userPwd)) return;
    }

    public void firebaseEmailSignUp(String userEmail, String userPwd, final Context context){

        showPD();
        getFirebaseAuth().createUserWithEmailAndPassword(userEmail, userPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
                }
                stopPD();
            }
        });

        if(!isValid(userEmail, userPwd)) return;
    }

    public FirebaseUser getFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }
}
