package themollo.app.mollo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

/**
 * Created by alex on 2018. 7. 23..
 */

public abstract class FragUtilBasement extends android.support.v4.app.Fragment{

    public static final String SURVEY = "survey";
    public static final String BED_TIME = "bedtime";
    public static final String WHEN_FALL_ASLEEP = "whenfallasleep";
    public static final String WAKEUP_TIME = "wakeuptime";
    public static final String DEEP_SLEEP_TIME = "deepsleeptime";
    public static final String WHAT_DISTURB = "whatdisturb";
    public static final String SLEEP_QUALITY = "sleepquality";
    public static final String DRUG_FOR_SLEEP = "drugforsleep";
    public static final String ORDINARY_DAY_DISORDER = "ordinarydaydisorder";


    public abstract void butterbind(View view);

    public SharedPreferences getSurveyPref() {
        return getActivity().getSharedPreferences(SURVEY, Context.MODE_PRIVATE);
    }

    public void putSurveyDataPref(String key, String value) {
        SharedPreferences.Editor editor = getSurveyPref().edit();
        editor.putString(key, value);
        editor.commit();

        prefLog("key : " + key + " value : " + value);
    }

    public String getSurveyData(String key){
        return getSurveyPref().getString(key, "");
    }

    public void prefLog(String text){
        Log.i("pref", text);
    }



}
