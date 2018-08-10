package themollo.app.mollo.sleeping;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.transition.TransitionSet;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.felipecsl.gifimageview.library.GifImageView;
import com.mbh.timelyview.TimelyShortTimeView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.analysis.AnalysisActivity;
import themollo.app.mollo.util.AppUtilBasement;

public class SleepActivity extends AppUtilBasement {

    @BindString(R.string.alarm_to_sleep)
    String alarmToSleep;

    @BindView(R.id.pbSleepProgressBar)
    ProgressBar pbSleepProgressBar;

    @BindView(R.id.ttvCurTime)
    TimelyShortTimeView ttvCurTime;

    @BindView(R.id.gifBack)
    GifImageView gifBack;

    @BindView(R.id.tvStopButton)
    TextView tvStopButton;



    private Drawable boot = new SleepingAnimator();
    private Timer timer;
    private static final String gifURL = "https://media.giphy.com/media/d1G6qsjTJcHYhzxu/giphy.gif";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        butterBind();
        setButtonListener();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transitionOverride();
            pbSleepProgressBar.setTransitionName(alarmToSleep);
        }



        pbSleepProgressBar.setIndeterminateDrawable(boot);
        ttvCurTime.setTextColor(Color.WHITE);
        ttvCurTime.setTimeFormat(TimelyShortTimeView.FORMAT_HOUR_MIN);
        ttvCurTime.setSeperatorsTextSize(50);
        ttvCurTime.setTime("99:99");
        ttvCurTime.setTime("00:00");

        new GifDataDownloader(){
            @Override
            protected void onPostExecute(byte[] bytes) {
                gifBack.setBytes(bytes);
                gifBack.startAnimation();
            }
        }.execute(gifURL);
    }

    private void transitionOverride(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setDuration(4000);
            getWindow().setSharedElementExitTransition(changeBounds);
            getWindow().setSharedElementEnterTransition(changeBounds);


        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Press stop button to stop alarm", Toast.LENGTH_SHORT).show();
        return;
    }

    @Override
    protected void onResume() {
        super.onResume();
        gifBack.startAnimation();
        timer = new Timer();
        timer.scheduleAtFixedRate(getTimerTask(), 1500, 1500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gifBack.stopAnimation();
        timer.cancel();
        timer = null;
    }


    @Override
    public void setButtonListener() {
        tvStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SleepActivity.this, AnalysisActivity.class));
            }
        });
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }

    private TimerTask getTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Date now = new Date();
                        ttvCurTime.setTime(now);
                    }
                });
            }
        };
    }
}
