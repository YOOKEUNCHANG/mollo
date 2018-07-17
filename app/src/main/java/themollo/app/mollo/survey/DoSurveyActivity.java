package themollo.app.mollo.survey;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;
import themollo.app.mollo.R;

public class DoSurveyActivity extends AppCompatActivity {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        surveyPagerAdapter.unregisterDataSetObserver(ciIndicator.getDataSetObserver());
    }
}
