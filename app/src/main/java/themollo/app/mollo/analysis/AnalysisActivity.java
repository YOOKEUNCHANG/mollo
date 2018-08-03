package themollo.app.mollo.analysis;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.util.AppUtilBasement;

public class AnalysisActivity extends AppUtilBasement {

    @BindView(R.id.tlTab)
    TabLayout tlTab;

    @BindView(R.id.vpAnalysis)
    ViewPager vpAnalysis;



    private AnalysisPagerAdapter analysisPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        butterBind();


        tlTab.addTab(tlTab.newTab().setText(R.string.today));
        tlTab.addTab(tlTab.newTab().setText(R.string.monthly));
        tlTab.setTabTextColors(R.color.tab_not_selected, R.color.tab_selected);
        tlTab.setTabGravity(TabLayout.GRAVITY_FILL);

        analysisPagerAdapter = new AnalysisPagerAdapter(getSupportFragmentManager(), tlTab.getTabCount());
        vpAnalysis.setAdapter(analysisPagerAdapter);
        vpAnalysis.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlTab));
        tlTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpAnalysis.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void setButtonListener() {

    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }
}
