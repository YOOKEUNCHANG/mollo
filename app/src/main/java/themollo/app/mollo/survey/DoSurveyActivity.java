package themollo.app.mollo.survey;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import themollo.app.mollo.survey.survey_fragment.SurveyPagerAdapter;
import themollo.app.mollo.survey.survey_fragment.Survey_p1;
import themollo.app.mollo.util.AppUtilBasement;
import themollo.app.mollo.R;

public class DoSurveyActivity extends AppUtilBasement {

    @BindView(R.id.ciIndicator)
    CircleIndicator ciIndicator;
    @BindView(R.id.vpSurvey)
    ViewPager vpSurvey;

    private SurveyPagerAdapter surveyPagerAdapter;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_survey);
        butterBind();

        surveyPagerAdapter = new SurveyPagerAdapter(getSupportFragmentManager());

        vpSurvey.setAdapter(surveyPagerAdapter);
        vpSurvey.setCurrentItem(0);

        ciIndicator.setViewPager(vpSurvey);
        ciIndicator.setBackgroundColor(R.color.appColor);

        surveyPagerAdapter.registerDataSetObserver(ciIndicator.getDataSetObserver());

        View.OnClickListener movePageListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                vpSurvey.setCurrentItem(tag);
            }
        };


    }

    public ViewPager getViewPager(){
        return vpSurvey;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        surveyPagerAdapter.unregisterDataSetObserver(ciIndicator.getDataSetObserver());
    }

    @Override
    public void setButtonListener() {

    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }
}
