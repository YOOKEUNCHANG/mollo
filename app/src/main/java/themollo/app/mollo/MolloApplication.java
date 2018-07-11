package themollo.app.mollo;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.kakao.auth.KakaoSDK;

import themollo.app.mollo.login.sns_login.kakao.KakaoSDKAdapter;

/**
 * Created by alex on 2018. 7. 9..
 */

public class MolloApplication extends Application {

    private static MolloApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        MolloApplication.instance = this;
        KakaoSDK.init(new KakaoSDKAdapter());
    }

    public static MolloApplication getInstance(){
        return instance;
    }
}
