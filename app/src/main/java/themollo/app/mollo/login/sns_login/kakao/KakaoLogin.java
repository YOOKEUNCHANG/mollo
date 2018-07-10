package themollo.app.mollo.login.sns_login.kakao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;

import themollo.app.mollo.MainActivity;

/**
 * Created by alex on 2018. 7. 9..
 */

public class KakaoLogin extends AppCompatActivity{
    private ISessionCallback iSessionCallback;

    public KakaoLogin() {
        createSessionCallback();
        Session.getCurrentSession().addCallback(iSessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    public void removeCallback(){
        Session.getCurrentSession().removeCallback(iSessionCallback);
    }

    public void createSessionCallback(){
        iSessionCallback = new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                moveToMain();
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {

            }
        };
    }

    public void moveToMain(){
        startActivity(new Intent(getBaseContext(), MainActivity.class));
    }



}
