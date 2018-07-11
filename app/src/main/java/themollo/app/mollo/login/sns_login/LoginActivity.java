package themollo.app.mollo.login.sns_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;

import org.json.JSONObject;

import java.util.Arrays;

import themollo.app.mollo.MainActivity;
import themollo.app.mollo.R;
import themollo.app.mollo.login.sns_login.kakao.KakaoLogin;

public class LoginActivity extends AppCompatActivity {

    private ISessionCallback iSessionCallback;
    private LoginButton btFacebook;
    private CallbackManager callbackManager;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeSessionCallback();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Session.getCurrentSession().isOpened()
                || AccessToken.isCurrentAccessTokenActive()) {
            moveToMain();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);

        //kakao
        iSessionCallback = new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                moveToMain();
                Log.i("kakao", "session opened");
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {
                Log.i("kakao", "session open failed : " + exception.toString());
            }
        };
        Session.getCurrentSession().addCallback(iSessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();

        //facebook
        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.btFacebook);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("facebook", object.toString());
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("facebook", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

                moveToMain();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.i("facebook", error.toString());
            }
        });


    }

    public void moveToMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    public void removeSessionCallback() {
        Session.getCurrentSession().removeCallback(iSessionCallback);
    }
}
