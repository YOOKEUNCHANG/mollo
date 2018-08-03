package themollo.app.mollo.survey.survey_fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.home.HomeActivity;
import themollo.app.mollo.sample.MainActivity;
import themollo.app.mollo.survey.DoSurveyActivity;
import themollo.app.mollo.survey.FragmentLifeCycle;
import themollo.app.mollo.util.FragUtilBasement;

public class Survey_p7 extends FragUtilBasement implements FragmentLifeCycle {

    @BindView(R.id.flNottaking)
    FrameLayout flNottaking;
    @BindView(R.id.ivNottakingCircle)
    ImageView ivNottakingCircle;

    @BindView(R.id.flOnce)
    FrameLayout flOnce;
    @BindView(R.id.ivOnceCircle)
    ImageView ivOnceCircle;

    @BindView(R.id.flTwice)
    FrameLayout flTwice;
    @BindView(R.id.ivTwiceCircle)
    ImageView ivTwiceCircle;

    @BindView(R.id.flMore)
    FrameLayout flMore;
    @BindView(R.id.ivMoreCircle)
    ImageView ivMoreCircle;

    @BindView(R.id.tvDone)
    TextView tvDone;

    private String KEY = ORDINARY_DAY_DISORDER;
    private static String VALUE = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.survey_7, container, false);
        butterbind(view);

        flNottaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivNottakingCircle.setVisibility(View.VISIBLE);
                ivOnceCircle.setVisibility(View.GONE);
                ivTwiceCircle.setVisibility(View.GONE);
                ivMoreCircle.setVisibility(View.GONE);
                VALUE = getString(R.string.not_take);
            }
        });

        flOnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivNottakingCircle.setVisibility(View.GONE);
                ivOnceCircle.setVisibility(View.VISIBLE);
                ivTwiceCircle.setVisibility(View.GONE);
                ivMoreCircle.setVisibility(View.GONE);
                VALUE = getString(R.string.once_a_week);
            }
        });

        flTwice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivNottakingCircle.setVisibility(View.GONE);
                ivOnceCircle.setVisibility(View.GONE);
                ivTwiceCircle.setVisibility(View.VISIBLE);
                ivMoreCircle.setVisibility(View.GONE);
                VALUE = getString(R.string.twice_a_week);
            }
        });

        flMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivNottakingCircle.setVisibility(View.GONE);
                ivOnceCircle.setVisibility(View.GONE);
                ivTwiceCircle.setVisibility(View.GONE);
                ivMoreCircle.setVisibility(View.VISIBLE);
                VALUE = getString(R.string.more_than_twice);
            }
        });

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), HomeActivity.class));
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
        prefLog("p7 resumed");
    }

    @Override
    public void onPauseFragment(Context context) {
        prefLog("p7 paused");
        SharedPreferences.Editor editor
                = context.getSharedPreferences(SURVEY, Context.MODE_PRIVATE).edit();
        editor.putString(KEY, VALUE).commit();

        prefLog("key : " + KEY + " value : " + VALUE);
    }

}
