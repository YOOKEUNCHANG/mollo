package themollo.app.mollo.survey.survey_fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.survey.DoSurveyActivity;
import themollo.app.mollo.survey.FragmentLifeCycle;
import themollo.app.mollo.util.FragUtilBasement;

public class Survey_p5 extends FragUtilBasement implements FragmentLifeCycle {

    @BindView(R.id.flInsomnia)
    FrameLayout flInsomnia;
    @BindView(R.id.ivInsomniaCircle)
    ImageView ivInsomniaCicle;

    @BindView(R.id.flSlouch)
    FrameLayout flSlouch;
    @BindView(R.id.ivSlouchCircle)
    ImageView ivSlouchCicle;

    @BindView(R.id.flBathroom)
    FrameLayout flBathroom;
    @BindView(R.id.ivBathroomCircle)
    ImageView ivBathroomCicle;

    @BindView(R.id.flLung)
    FrameLayout flLung;
    @BindView(R.id.ivLungCircle)
    ImageView ivLungCicle;

    @BindView(R.id.flSneeze)
    FrameLayout flSneeze;
    @BindView(R.id.ivSneezeCircle)
    ImageView ivSneezeCicle;

    @BindView(R.id.flStomach)
    FrameLayout flStomach;
    @BindView(R.id.ivStomachCircle)
    ImageView ivStomachCicle;

    @BindView(R.id.flCold)
    FrameLayout flCold;
    @BindView(R.id.ivColdCircle)
    ImageView ivColdCicle;

    @BindView(R.id.flHot)
    FrameLayout flHot;
    @BindView(R.id.ivHotCircle)
    ImageView ivHotCicle;

    @BindView(R.id.flEvil)
    FrameLayout flEvil;
    @BindView(R.id.ivEvilCircle)
    ImageView ivEvilCicle;

    private String KEY = WHAT_DISTURB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.survey_5, container, false);
        butterbind(view);
        
        setButtonValue(flInsomnia, ivInsomniaCicle, getString(R.string.symp1));
        setButtonValue(flSlouch, ivSlouchCicle, getString(R.string.symp2));
        setButtonValue(flBathroom, ivBathroomCicle, getString(R.string.symp3));
        setButtonValue(flLung, ivLungCicle, getString(R.string.symp4));
        setButtonValue(flSneeze, ivSneezeCicle, getString(R.string.symp5));
        setButtonValue(flStomach, ivStomachCicle, getString(R.string.symp9));
        setButtonValue(flCold, ivColdCicle, getString(R.string.symp6));
        setButtonValue(flHot, ivHotCicle, getString(R.string.symp7));
        setButtonValue(flEvil, ivEvilCicle, getString(R.string.symp8));

        return view;
    }

    public void setButtonValue(FrameLayout frameLayout, final ImageView imageView, final String KEY){
        final SharedPreferences pref = getContext().getSharedPreferences(SURVEY, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(KEY, false);
        
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = pref.getBoolean(KEY, false);
                if(!isChecked) {
                    imageView.setVisibility(View.VISIBLE);
                    editor.putBoolean(KEY, true);
                }else{
                    imageView.setVisibility(View.GONE);
                    editor.putBoolean(KEY, false);
                }
                editor.commit();
            }
        });
    }



    @Override
    public void butterbind(View view) {
        ButterKnife.bind(this, view);
    }


    @Override
    public void onResumeFragment(Context context) {
        prefLog("p5 resumed");
    }

    @Override
    public void onPauseFragment(Context context) {
        prefLog("p5 paused");
    }

}
