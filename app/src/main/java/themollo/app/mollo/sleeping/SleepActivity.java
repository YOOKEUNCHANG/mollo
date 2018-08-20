package themollo.app.mollo.sleeping;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.felipecsl.gifimageview.library.GifImageView;
import com.mbh.timelyview.TimelyShortTimeView;
import com.melnykov.fab.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.recruit_lifestyle.android.widget.PlayPauseButton;
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

    @BindView(R.id.ppbSound)
    PlayPauseButton ppbSound;

    @BindView(R.id.llSound)
    LinearLayout llSound;

    @BindView(R.id.tvStartAlarmTime)
    TextView tvStartAlarmTime;

    @BindView(R.id.tvEndAlarmTime)
    TextView tvEndAlarmTime;

    @BindString(R.string.alarm_start_time)
    String alarmStartTime;

    @BindString(R.string.alarm_end_time)
    String alarmEndTime;

    private Calendar calendar;
    private Drawable boot = new SleepingAnimator();
    private Timer timer;
    private String sleepTime;
    private String wakeupTime;
    private boolean sleepTimeDayOver;
    private static final String gifURL = "https://media.giphy.com/media/d1G6qsjTJcHYhzxu/giphy.gif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        butterBind();
        setButtonListener();

        sleepTime = getIntent().getStringExtra(SLEEP_TIME);
        wakeupTime = getIntent().getStringExtra(WAKEUP_TIME);
        sleepTimeDayOver = getIntent().getBooleanExtra(SLEEP_TIME_DAY_OVER, false);
        calendar = Calendar.getInstance();

        int wakeupHour = toInt(wakeupTime.split(":")[0]);
        int wakeupMin = toInt(wakeupTime.split(":")[1]);

        Log.i("time", "wakeupHour : " + wakeupHour + " / wakeupMin : " + wakeupMin);

        calendar.set(Calendar.HOUR_OF_DAY, wakeupHour);
        calendar.set(Calendar.MINUTE, wakeupMin);

        tvStartAlarmTime.setText(sleepTime);
        tvEndAlarmTime.setText(wakeupTime);

        Intent alarmIntent = new Intent("themollo.app.mollo.sleeping.AlarmStart");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                R.integer.pendingIntentRequestCode,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
        );

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

        new GifDataDownloader() {
            @Override
            protected void onPostExecute(byte[] bytes) {
                Animation fadeIn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in);
                gifBack.setBytes(bytes);
                gifBack.startAnimation(fadeIn);
            }
        }.execute(gifURL);

        ppbSound.setColor(Color.WHITE);
    }

    private void transitionOverride() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setDuration(4000);
            getWindow().setSharedElementExitTransition(changeBounds);
            getWindow().setSharedElementEnterTransition(changeBounds);

//            Fade fade = new Fade();
//            fade.setDuration(4000);
//            getWindow().setEnterTransition(fade);
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
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        llSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ppbSound.performClick();
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
