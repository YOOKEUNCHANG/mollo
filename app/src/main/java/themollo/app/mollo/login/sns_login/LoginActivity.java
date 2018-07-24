package themollo.app.mollo.login.sns_login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.sample.MainActivity;
import themollo.app.mollo.R;
import themollo.app.mollo.firebase.FirebaseLogin;
import themollo.app.mollo.firebase.SignInActivity;

public class LoginActivity extends FirebaseLogin {

    private static final String TAG = "LoginActivity";

    private ISessionCallback iSessionCallback;
    private CallbackManager callbackManager;
    
    @BindView(R.id.btAnonySignIn)
    Button btAnonySignIn;
    @BindView(R.id.btSignIn)
    Button btSignIn;
    @BindView(R.id.btFacebook)
    LoginButton btFacebook;


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
            moveTo(MainActivity.class);
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

        ButterKnife.bind(this);

        setRegisterKakaoCallback();
        setRegisterFacebookCallback();
        setButtonListener();
    }

    public void requestInfo(){
        ArrayList<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("kakao_account.email");

        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {

            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.i(TAG, "errmsg : " + errorResult.toString());
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onSuccess(MeV2Response result) {
                Log.i(TAG, "user id : " + result.getId());
                Log.i(TAG, "email : " + result.getKakaoAccount().getEmail());
                Log.i(TAG, "profile path : " + result.getProfileImagePath());
            }
        });
    }

    @Override
    public void setButtonListener(){
        btAnonySignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAnonySignIn(getBaseContext());
            }
        });

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(SignInActivity.class);
            }
        });

    }

    @Override
    public void butterBind() {

    }

    public void setRegisterKakaoCallback(){
        //kakao
        iSessionCallback = new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                moveTo(MainActivity.class);
                Log.i("kakao", "session opened");
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {
                Log.i("kakao", "session open failed : " + exception.toString());
            }
        };
        Session.getCurrentSession().addCallback(iSessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();

        requestInfo();
    }
    
    public void setRegisterFacebookCallback(){
        //facebook
        callbackManager = CallbackManager.Factory.create();

        btFacebook.setReadPermissions(Arrays.asList("public_profile", "email"));
        btFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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

                moveTo(MainActivity.class);
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

    public void removeSessionCallback() {
        Session.getCurrentSession().removeCallback(iSessionCallback);
    }


}
