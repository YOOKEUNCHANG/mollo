package themollo.app.mollo.survey.survey_fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import themollo.app.mollo.survey.FragmentLifeCycle;
import themollo.app.mollo.util.FragUtilBasement;

public class Survey_p1 extends FragUtilBasement implements FragmentLifeCycle{

    @BindView(R.id.ivMaleCircle)
    ImageView ivMaleCircle;

    @BindView(R.id.ivFemaleCircle)
    ImageView ivFemaleCircle;

    @BindView(R.id.flMale)
    FrameLayout flMale;

    @BindView(R.id.flFemale)
    FrameLayout flFemale;

    private String KEY = SEX;
    private static String VALUE = "male";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.survey_1, container, false);
        butterbind(view);

        flMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivMaleCircle.setVisibility(View.VISIBLE);
                ivFemaleCircle.setVisibility(View.GONE);
                VALUE = getString(R.string.male);
            }
        });

        flFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivMaleCircle.setVisibility(View.GONE);
                ivFemaleCircle.setVisibility(View.VISIBLE);
                VALUE = getString(R.string.female);
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
        prefLog("p1 resumed");
    }

    @Override
    public void onPauseFragment(Context context) {
        prefLog("p1 paused");
        SharedPreferences.Editor editor
                = context.getSharedPreferences(SURVEY, Context.MODE_PRIVATE).edit();
        editor.putString(KEY, VALUE).commit();

        prefLog("key : " + KEY + " value : " + VALUE);
    }

}
