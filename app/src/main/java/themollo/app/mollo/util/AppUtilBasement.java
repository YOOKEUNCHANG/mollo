package themollo.app.mollo.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by alex on 2018. 7. 16..
 */

public abstract class AppUtilBasement extends AppCompatActivity{

    public static final String exampleName = "EN";
    public static final String CHART = "chart";
    public static final String ALARM = "alarm";
    public static final String LULLABY = "lullaby";
    public static final String HOME = "home";
    public static final String SURVEY = "survey";
    public static final String SLEEP_ARC_PROGRESS = "sleepArcProgress";
    public static final String WAKEUP_ARC_PROGRESS = "wakeupArcProgress";
    public static final String SLEEP_TIME = "sleepTime";
    public static final String WAKEUP_TIME= "wakeupTime";
    public static final String TOP_ARC_XPOS = "topArcXPos";
    public static final String TOP_ARC_YPOS = "topArcYPos";
    public static final String BOTTOM_ARC_XPOS = "bottomArcXPos";
    public static final String BOTTOM_ARC_YPOS = "bottomArcYPos";

    ProgressDialog pd;

    public void showPD(){
        if (pd == null) {
            pd = new ProgressDialog(this);
            pd.setCancelable(false); //임의 취소 불가
            pd.setMessage(".. loading ..");
        }
        pd.show();
    }

    public void stopPD(){
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    public void moveTo(Class cls){
        startActivity(new Intent(getBaseContext(), cls));
    }

    public void toastText(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public void logUtil(String tag, String text){
        Log.i(tag, text);
    }

    public SharedPreferences getSurveyPref() {
        return getSharedPreferences(SURVEY, Context.MODE_PRIVATE);
    }

    public void putSurveyDataPref(String key, String value) {
        SharedPreferences.Editor editor = getSurveyPref().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public SharedPreferences getAlarmPref() {
        return getSharedPreferences(ALARM, Context.MODE_PRIVATE);
    }

    public void putAlarmTimeData(String key, String value){
        SharedPreferences.Editor editor = getAlarmPref().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getAlarmData(String key){
        return getAlarmPref().getString(key, "");
    }

    public String getSurveyData(String key){
        return getSurveyPref().getString(key, "");
    }

    public FirebaseUser getFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
        MultiDex.install(newBase);
    }



    //abstract methods
    public abstract void setButtonListener();
    public abstract void butterBind();

}
