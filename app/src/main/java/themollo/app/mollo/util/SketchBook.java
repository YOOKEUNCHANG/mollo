package themollo.app.mollo.util;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mbh.timelyview.TimelyView;
import com.nineoldandroids.animation.ObjectAnimator;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import themollo.app.mollo.R;
import themollo.app.mollo.sleeping.SleepActivity;


public class SketchBook extends AppUtilBasement {
    private volatile ObjectAnimator objectAnimator = null;

    @BindString(R.string.button_transition)
    String transitionName;

    @BindString(R.string.alarm_start_time)
    String alarmStartTime;

    @BindString(R.string.alarm_end_time)
    String alarmEndTime;

    @BindString(R.string.alarm_to_sleep)
    String alarmToSleep;

    @BindView(R.id.timelyTextView)
    TimelyView timelyTextView;

    @BindView(R.id.saSleep)
    MySeekArc saSleep;

    @BindView(R.id.saWakeup)
    MySeekArc saWakeup;

    @BindView(R.id.tvUpperSeekArc)
    TextView tvUpperSeekArc;

    @BindView(R.id.tvLowerSeekArc)
    TextView tvLowerSeekArc;

    @BindView(R.id.tvTotalSleepTime)
    TextView tvTotalSleepTime;

    @BindView(R.id.tvFollowSleepTime)
    TextView tvFollowSleepTime;

    @BindView(R.id.tvFollowWakeupTime)
    TextView tvFollowWakeupTime;

    @BindView(R.id.pbAlarmProgressBar)
    ProgressBar pbAlarmProgressBar;

    private int sleepArcValue = 0;
    private int wakeupArcValue = 0;
    private volatile int from = 9;
    private volatile int to = 1;

    float px_300dp;
    float px_150dp;
    float XPosWhenThumbTop;
    float YPos;

    private Drawable boot = new LullabyAnimator();

    @Override
    protected void onResume() {

        int startArcProgress = Integer.parseInt(getAlarmData(SLEEP_ARC_PROGRESS));
        int endArcProgress = Integer.parseInt(getAlarmData(WAKEUP_ARC_PROGRESS));
        String sleepTime = getAlarmData(SLEEP_TIME);
        String wakeupTime = getAlarmData(WAKEUP_TIME);

        saSleep.setProgress(startArcProgress);
        saWakeup.setProgress(endArcProgress);

        tvFollowSleepTime.setText(sleepTime);
        tvFollowWakeupTime.setText(wakeupTime);

        super.onResume();
    }

    private void transitionOverride() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setDuration(1500);
            getWindow().setSharedElementExitTransition(changeBounds);
            getWindow().setSharedElementEnterTransition(changeBounds);

            Fade fade = new Fade();

            fade.setDuration(4000);
            getWindow().setExitTransition(fade);
            fade.setDuration(1500);
            getWindow().setEnterTransition(fade);


        }
    }


    @Override
    protected void onPause() {
        putAlarmTimeData(SLEEP_ARC_PROGRESS, getSleepArcValue()+"");
        putAlarmTimeData(WAKEUP_ARC_PROGRESS, getWakeupArcValue()+"");
        putAlarmTimeData(SLEEP_TIME, tvFollowSleepTime.getText().toString());
        putAlarmTimeData(WAKEUP_TIME, tvFollowWakeupTime.getText().toString());

        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch_book);
        butterBind();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transitionOverride();
            tvFollowSleepTime.setTransitionName(alarmStartTime);
            tvFollowWakeupTime.setTransitionName(alarmEndTime);
            pbAlarmProgressBar.setTransitionName(transitionName);
        }

        pbAlarmProgressBar.setIndeterminateDrawable(boot);

        int white = ContextCompat.getColor(getBaseContext(), R.color.white);
        timelyTextView.setTextColor(white);
        timelyTextView.setStrokeWidth(4);

        sleepArcValue = 600;
        wakeupArcValue = 0;

//        saSleep.setStartAngle(SLLEP_ARC_START_ANGLE);
//        saWakeup.setStartAngle(WAKEUP_ARC_START_ANGLE);

        objectAnimator = timelyTextView.animate(0, 9);
        objectAnimator.setDuration(1000);
        objectAnimator.start();

        px_300dp = dpToPx(300, getBaseContext());
        px_150dp = dpToPx(150, getBaseContext());

        tvFollowSleepTime.setX(29);
        tvFollowSleepTime.setY(363);

        tvFollowWakeupTime.setX(758);
        tvFollowWakeupTime.setY(438);

//        Log.i("layoutparams", "arc pl : " + saSleep.getPaddingLeft() + " arc pr : " + saSleep.getPaddingRight());


        saSleep.setOnSeekArcChangeListener(new MySeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(MySeekArc mySeekArc, int progress, boolean fromUser) {
                sleepArcValue = progress;
                to = getTotalSleepHourValue() / 60;
                if(from != to){
                    timelyTextView.animate(from, to).setDuration(800).start();
                    from = to;
                }
                tvFollowSleepTime.setText(getTimeText(progress, "sleepTime"));
                if (progress < 360)
                    tvFollowSleepTime.setTranslationX(mySeekArc.getThumbXPos() - 150);
                else
                    tvFollowSleepTime.setTranslationX(mySeekArc.getThumbXPos() + 20);
                tvFollowSleepTime.setTranslationY(mySeekArc.getThumbYPos() - 50);

                Log.i("thumb", "saSleep x : " + mySeekArc.getThumbXPos() + " y : " + mySeekArc.getThumbYPos());
            }

            @Override
            public void onStartTrackingTouch(MySeekArc mySeekArc) {

            }

            @Override
            public void onStopTrackingTouch(MySeekArc mySeekArc) {

            }
        });


        saWakeup.setOnSeekArcChangeListener(new MySeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(MySeekArc mySeekArc, int progress, boolean fromUser) {

                wakeupArcValue = progress;
                to = getTotalSleepHourValue() / 60;
                if(from != to){
                    timelyTextView.animate(from, to).setDuration(800).start();
                    from = to;
                }

                tvFollowWakeupTime.setText(getTimeText(progress, "wakeupTime"));

                if (progress < 360)
                    tvFollowWakeupTime.setTranslationX(mySeekArc.getThumbXPos() + 20);
                else
                    tvFollowWakeupTime.setTranslationX(mySeekArc.getThumbXPos() - 150);
                tvFollowWakeupTime.setTranslationY(mySeekArc.getThumbYPos() - px_150dp);

                Log.i("thumb", "saWakeup x : " + mySeekArc.getThumbXPos() + " y : " + mySeekArc.getThumbYPos());
            }

            @Override
            public void onStartTrackingTouch(MySeekArc mySeekArc) {

            }

            @Override
            public void onStopTrackingTouch(MySeekArc mySeekArc) {

            }
        });




    }

    public void numAnim() {
        to = getTotalSleepHourValue() / 60;
        objectAnimator = timelyTextView.animate(from, to);
        objectAnimator.start();
        from = to;
    }


    public String getTimeText(int progress, String type) {
        StringBuilder sb = new StringBuilder();
        int hour = 0;
        int min = 0;

        if (type.equals("sleepTime")) {
            if (progress < 360) {
                hour = 18 + progress / 60;
                min = progress % 60;
            } else {
                hour = -6 + progress / 60;
                min = progress % 60;
            }
        } else if (type.equals("wakeupTime")) {
            hour = 6 + progress / 60;
            min = progress % 60;
        }

        if (isUnderTen(hour)) {
            sb.append("0" + hour + ":");
            if (isUnderTen(min)) {
                sb.append("0" + min);
            } else {
                sb.append(min);
            }
        } else {

            sb.append(hour + ":");
            if (isUnderTen(min)) {
                sb.append("0" + min);
            } else {
                sb.append(min);
            }
        }
        return sb.toString();
    }

    public boolean isUnderTen(int i) {
        if (i < 10 && i >= 0) return true;
        else return false;
    }


//    public int getThumbWindowXPos(MySeekArc seekArc) {
//        return seekArc.getWidth() - seekArc.getPaddingRight() - seekArc.getPaddingLeft();
//    }
//
//    public int getThumbWindowYPos(MySeekArc seekArc) {
//        return seekArc.getHeight() - seekArc.getPaddingTop() - seekArc.getPaddingBottom();
//    }

    public static float dpToPx(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }



    @OnClick(R.id.llBack)
    void backPress() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    @OnClick(R.id.tvSleepButton)
    void sleep() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                    Pair.create(pbAlarmProgressBar, alarmToSleep));
            Intent intent = new Intent(this, SleepActivity.class);
            startActivity(intent, options.toBundle());
        } else {
            moveTo(SleepActivity.class);
        }
    }

    public int getSleepArcValue() {
        return sleepArcValue;
    }

    public int getWakeupArcValue() {
        return wakeupArcValue;
    }

    public int getTotalSleepHourValue() {
        int res = (720 - getSleepArcValue()) + getWakeupArcValue();
        if(res / 60 >= 10) return 9;
        else return (720 - getSleepArcValue()) + getWakeupArcValue();
    }

    @Override
    public void setButtonListener() {

    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }


}




