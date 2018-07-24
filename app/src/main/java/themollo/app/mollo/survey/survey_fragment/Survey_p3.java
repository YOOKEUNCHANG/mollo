package themollo.app.mollo.survey.survey_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.survey.DoSurveyActivity;
import themollo.app.mollo.util.FragUtilBasement;

/**
 * A simple {@link Fragment} subclass.
 */
public class Survey_p3 extends FragUtilBasement{

    @BindView(R.id.etAns3)
    EditText etAns3;

    private String KEY = WAKEUP_TIME;

    public Survey_p3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.survey_3, container, false);
        butterbind(view);


        return view;
    }

    @Override
    public void butterbind(View view) {
        ButterKnife.bind(this, view);
    }
}
