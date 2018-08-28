package themollo.app.mollo.survey.survey_fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Survey_p2 extends FragUtilBasement implements FragmentLifeCycle{

    @BindView(R.id.fl1020s)
    FrameLayout fl1020s;
    @BindView(R.id.fl3040s)
    FrameLayout fl3040s;
    @BindView(R.id.fl50s)
    FrameLayout fl50s;

    @BindView(R.id.iv1020sCircle)
    ImageView iv1020sCircle;
    @BindView(R.id.iv3040sCircle)
    ImageView iv3040sCircle;
    @BindView(R.id.iv50sCircle)
    ImageView iv50sCircle;


    private String KEY = AGE;
    private static String VALUE = "10-20s";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.survey_2, container, false);
        butterbind(view);

        fl1020s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv1020sCircle.setVisibility(View.VISIBLE);
                iv3040sCircle.setVisibility(View.GONE);
                iv50sCircle.setVisibility(View.GONE);
                VALUE = getString(R.string.age1020s);
            }
        });

        fl3040s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv1020sCircle.setVisibility(View.GONE);
                iv3040sCircle.setVisibility(View.VISIBLE);
                iv50sCircle.setVisibility(View.GONE);
                VALUE = getString(R.string.age3040s);
            }
        });

        fl50s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv1020sCircle.setVisibility(View.GONE);
                iv3040sCircle.setVisibility(View.GONE);
                iv50sCircle.setVisibility(View.VISIBLE);
                VALUE = getString(R.string.age50s);
            }
        });


        return view;
    }



    @Override
    public void butterbind(View view) {
        ButterKnife.bind(this, view);
    }


    @Override
    public void onResumeFragment(Context context) {
        prefLog("p2 resumed");
    }

    @Override
    public void onPauseFragment(Context context) {
        prefLog("p2 paused");
        putSurveyDataPref(context, KEY, VALUE);
    }

}
