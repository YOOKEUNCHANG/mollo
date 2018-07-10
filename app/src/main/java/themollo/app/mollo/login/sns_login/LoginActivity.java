package themollo.app.mollo.login.sns_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import themollo.app.mollo.MainActivity;
import themollo.app.mollo.R;
import themollo.app.mollo.login.sns_login.kakao.KakaoLogin;

public class LoginActivity extends AppCompatActivity {

    private KakaoLogin kakaoLogin;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        kakaoLogin.removeCallback();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        kakaoLogin = new KakaoLogin();

    }

}
