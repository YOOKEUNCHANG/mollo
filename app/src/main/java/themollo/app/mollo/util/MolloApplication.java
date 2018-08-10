package themollo.app.mollo.util;

import android.app.Application;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.kakao.auth.KakaoSDK;
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;
import com.tsengvn.typekit.Typekit;

import themollo.app.mollo.login.sns_login.kakao.KakaoSDKAdapter;

/**
 * Created by alex on 2018. 7. 9..
 */

public class MolloApplication extends MultiDexApplication {

    private static MolloApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(getApplicationContext());
        MolloApplication.instance = this;
        RxPaparazzo.register(this);
        KakaoSDK.init(new KakaoSDKAdapter());

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "font/NanumBarunGothicLight.ttf"))
                .addBold(Typekit.createFromAsset(this, "font/NanumBarunGothic.ttf"));
    }

    public static MolloApplication getInstance(){
        return instance;
    }
}
