package themollo.app.mollo.survey.survey_fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.Share;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.survey.FragmentLifeCycle;
import themollo.app.mollo.survey.SurveyResultPopup;
import themollo.app.mollo.util.FragUtilBasement;

public class Survey_p7 extends FragUtilBasement implements FragmentLifeCycle {

    @BindView(R.id.flNotAtAll)
    FrameLayout flNotAtAll;
    @BindView(R.id.ivNotAtAllCircle)
    ImageView ivNotAtAllCircle;

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
    private int VALUE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.survey_7, container, false);
        butterbind(view);

        flNotAtAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivNotAtAllCircle.setVisibility(View.VISIBLE);
                ivOnceCircle.setVisibility(View.GONE);
                ivTwiceCircle.setVisibility(View.GONE);
                ivMoreCircle.setVisibility(View.GONE);
                VALUE = 0;
                putSurveyDataPref(getActivity().getBaseContext(), KEY, VALUE+"");
            }
        });

        flOnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivNotAtAllCircle.setVisibility(View.GONE);
                ivOnceCircle.setVisibility(View.VISIBLE);
                ivTwiceCircle.setVisibility(View.GONE);
                ivMoreCircle.setVisibility(View.GONE);
                VALUE = 1;
                putSurveyDataPref(getActivity().getBaseContext(), KEY, VALUE+"");
            }
        });

        flTwice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivNotAtAllCircle.setVisibility(View.GONE);
                ivOnceCircle.setVisibility(View.GONE);
                ivTwiceCircle.setVisibility(View.VISIBLE);
                ivMoreCircle.setVisibility(View.GONE);
                VALUE = 2;
                putSurveyDataPref(getActivity().getBaseContext(), KEY, VALUE+"");
            }
        });

        flMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivNotAtAllCircle.setVisibility(View.GONE);
                ivOnceCircle.setVisibility(View.GONE);
                ivTwiceCircle.setVisibility(View.GONE);
                ivMoreCircle.setVisibility(View.VISIBLE);
                VALUE = 3;
                putSurveyDataPref(getActivity().getBaseContext(), KEY, VALUE+"");
            }
        });

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), SurveyResultPopup.class));
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

    }

}
