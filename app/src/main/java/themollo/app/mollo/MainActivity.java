package themollo.app.mollo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import themollo.app.mollo.login.sns_login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private Button btLogout;
    private Button btSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLogout = findViewById(R.id.btLogout);
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.getCurrentSession().isOpened())
                    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {
                            moveToLogin();
                        }
                    });
                else if(AccessToken.isCurrentAccessTokenActive()){
                    LoginManager.getInstance().logOut();
                    moveToLogin();
                }
            }
        });

        findViewById(R.id.btSensor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToSensor();
            }
        });


    }

    public void moveToLogin(){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    public void moveToSensor(){
        startActivity(new Intent(MainActivity.this, SensorActivity.class));
    }

}
