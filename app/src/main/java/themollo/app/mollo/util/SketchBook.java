package themollo.app.mollo.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
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
    public static final int DURATION = 1000;
    public static final int ONE_ARC_WHOLE_VALUE = 360;
    public static final int DEVIDE_VALUE = 60;
    public static final int NO_VALUE = -1;
    private volatile ObjectAnimator objectAnimator = null;

    @BindString(R.string.button_transition)
    String transitionName;

    @BindString(R.string.alarm_start_time)
    String alarmStartTime;

    @BindString(R.string.alarm_end_time)
    String alarmEndTime;

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
    private int totalSleepHourValue = 0;
    private volatile int from = 9;
    private volatile int to = 1;
    private int defaultPT = 0;
    private static int rad = 150;

    float px_300dp;
    float px_150dp;
    float XPosWhenThumbTop;
    float YPos;

    private Drawable boot = new LullabyAnimator();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch_book);
        butterBind();

        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);

        tvFollowSleepTime.setTransitionName(alarmStartTime);
        tvFollowWakeupTime.setTransitionName(alarmEndTime);

        pbAlarmProgressBar.setTransitionName(transitionName);
        pbAlarmProgressBar.setIndeterminateDrawable(boot);

        int white = ContextCompat.getColor(getBaseContext(), R.color.white);
        timelyTextView.setTextColor(white);
        timelyTextView.setStrokeWidth(4);

        sleepArcValue = 600;
        wakeupArcValue = 0;

//        saSleep.setStartAngle(SLLEP_ARC_START_ANGLE);
//        saWakeup.setStartAngle(WAKEUP_ARC_START_ANGLE);

        objectAnimator = timelyTextView.animate(0, 9);
        objectAnimator.setDuration(800);
        objectAnimator.start();

        px_300dp = dpToPx(300, getBaseContext());
        px_150dp = dpToPx(150, getBaseContext());


        Log.i("layoutparams", "arc pl : " + saSleep.getPaddingLeft() + " arc pr : " + saSleep.getPaddingRight());

        tvFollowSleepTime.setTranslationX(-120);
        tvFollowSleepTime.setTranslationY(225);

        tvFollowWakeupTime.setTranslationX(784);
        tvFollowWakeupTime.setTranslationY(109);



        saSleep.setOnSeekArcChangeListener(new MySeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(MySeekArc mySeekArc, int progress, boolean fromUser) {
                sleepArcValue = progress;
                int xpos = mySeekArc.getThumbXPos();
                if (xpos >= 0) {
                    XPosWhenThumbTop = (getThumbWindowXPos(mySeekArc) - px_300dp) + 350;
                } else {
                    XPosWhenThumbTop = (getThumbWindowXPos(mySeekArc) - px_300dp) + 490;
                }
                YPos = (getThumbWindowYPos(mySeekArc) - px_150dp);
                tvFollowSleepTime.setTranslationX(XPosWhenThumbTop - xpos);
                tvFollowSleepTime.setTranslationY(YPos - mySeekArc.getThumbYPos());

                tvFollowSleepTime.setText(getTimeText(progress, "sleepTime"));

                numAnim();
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
                int xpos = mySeekArc.getThumbXPos();
                if (xpos < 0) {
                    XPosWhenThumbTop = getThumbWindowXPos(mySeekArc) - 260;
                } else {
                    XPosWhenThumbTop = getThumbWindowXPos(mySeekArc) - 450;
                }

                YPos = getThumbWindowYPos(mySeekArc) - dpToPx(240, getBaseContext());
//                Log.i("thumb_pos", "thumb x pos : " + mySeekArc.getThumbXPos() + " thumb y pos : " + mySeekArc.getThumbYPos());

                tvFollowWakeupTime.setTranslationX(XPosWhenThumbTop - mySeekArc.getThumbXPos());
                tvFollowWakeupTime.setTranslationY(YPos - mySeekArc.getThumbYPos());
                tvFollowWakeupTime.setText(getTimeText(progress, "wakeupTime"));

//                tvLowerSeekArc.setText("WakeupTime seekarc rotation : " + progress);
//                tvTotalSleepTime.setText(getTotalSleepHourValue() + "");

                numAnim();
            }

            @Override
            public void onStartTrackingTouch(MySeekArc mySeekArc) {
                numAnim();
            }

            @Override
            public void onStopTrackingTouch(MySeekArc mySeekArc) {
                numAnim();
            }
        });


    }

    public void numAnim(){
        to = getTotalSleepHourValue() / 60;
        objectAnimator = timelyTextView.animate(from, to);
        objectAnimator.start();
        from = to;
    }

    public String getTimeText(int progress, String type){
        StringBuilder sb = new StringBuilder();
        int hour=0;
        int min=0;
        if(type.equals("sleepTime")){
            hour = 12 + progress / 60;
            min = progress % 60;
        }else if(type.equals("wakeupTime")){
            hour = 6 + progress / 60;
            min = progress % 60;
        }

        if(isUnderTen(hour)){
            sb.append("0" + hour + ":");
            if(isUnderTen(min)){
                sb.append("0" + min);
            }else{
                sb.append(min);
            }
        }else{
            sb.append(hour + ":");
            if(isUnderTen(min)){
                sb.append("0" + min);
            }else{
                sb.append(min);
            }
        }
        return sb.toString();
    }

    public boolean isUnderTen(int i){
        if(i<10 && i>=0) return true;
        else return false;
    }


    public int getThumbWindowXPos(MySeekArc seekArc) {
        return seekArc.getWidth() - seekArc.getPaddingRight() - seekArc.getPaddingLeft();
    }

    public int getThumbWindowYPos(MySeekArc seekArc) {
        return seekArc.getHeight() - seekArc.getPaddingTop() - seekArc.getPaddingBottom();
    }

    public static float dpToPx(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }


    public void numAnimate() {
        int rest = getTotalSleepHourValue() % 60;
        if (rest == 0) {
            to = getTotalSleepHourValue() / 60;
            Log.i("sketchbook", "from : " + from + " to : " + to);
            objectAnimator = timelyTextView.animate(from, to);
            objectAnimator.start();
            from = to;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.llBack)
    void backPress(){
        finishAfterTransition();
    }

    @OnClick(R.id.tvSleepButton)
    void sleep(){
        moveTo(SleepActivity.class);
    }

    public int getSleepArcValue() {
        return sleepArcValue;
    }

    public int getWakeupArcValue() {
        return wakeupArcValue;
    }

    public int getTotalSleepHourValue() {
        return (720 - getSleepArcValue()) + getWakeupArcValue();
    }

    @Override
    public void setButtonListener() {

    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }
}




