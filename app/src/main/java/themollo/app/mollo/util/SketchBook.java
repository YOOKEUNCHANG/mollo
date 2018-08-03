package themollo.app.mollo.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import com.mbh.timelyview.TimelyView;
import com.nineoldandroids.animation.ObjectAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;


public class SketchBook extends AppUtilBasement {
    public static final int DURATION = 1000;
    public static final int ONE_ARC_WHOLE_VALUE = 360;
    public static final int DEVIDE_VALUE = 60;
    public static final int NO_VALUE = -1;
    private volatile ObjectAnimator objectAnimator = null;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch_book);
        butterBind();

        int white = ContextCompat.getColor(getBaseContext(), R.color.white);
        timelyTextView.setTextColor(white);
        timelyTextView.setStrokeWidth(4);

        sleepArcValue = 600;
        wakeupArcValue = 0;

//        saSleep.setStartAngle(SLLEP_ARC_START_ANGLE);
//        saWakeup.setStartAngle(WAKEUP_ARC_START_ANGLE);

        objectAnimator = timelyTextView.animate(0, 9);
        objectAnimator.setDuration(DURATION);
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
//                Log.i("thumb_pos", "thumb x pos : " + mySeekArc.getThumbXPos() + " thumb y pos : " + mySeekArc.getThumbYPos());
                tvFollowSleepTime.setTranslationX(XPosWhenThumbTop - xpos);
                tvFollowSleepTime.setTranslationY(YPos - mySeekArc.getThumbYPos());

                int hour = 18 + progress / 60;
                int min = progress % 60;
                if(hour >= 24) {
                    hour -= 24;
                    tvFollowSleepTime.setText(hour + ":" + min);
                }else{
                    tvFollowSleepTime.setText(hour + ":" + min);
                }

                tvUpperSeekArc.setText("SleepTime seekarc rotation : " + progress);
                tvTotalSleepTime.setText(getTotalSleepHourValue() + "");

                to = getTotalSleepHourValue() / 60;
                objectAnimator = timelyTextView.animate(from, to);
                objectAnimator.start();
                from = to;
//                numAnimate();
            }

            @Override
            public void onStartTrackingTouch(MySeekArc mySeekArc) {

            }

            @Override
            public void onStopTrackingTouch(MySeekArc mySeekArc) {
//                to = getTotalSleepHourValue() / 60;
//                objectAnimator = timelyTextView.animate(from, to);
//                objectAnimator.setDuration(ONE_ARC_WHOLE_VALUE);
//                objectAnimator.start();
//                from = to;
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

                int hour = 6 + progress / 60;
                int min = progress % 60;

                tvFollowWakeupTime.setText(hour + ":" + min);

                tvLowerSeekArc.setText("WakeupTime seekarc rotation : " + progress);
                tvTotalSleepTime.setText(getTotalSleepHourValue() + "");

                to = getTotalSleepHourValue() / 60;
                objectAnimator = timelyTextView.animate(from, to);
                objectAnimator.start();
                from = to;
//                numAnimate();
            }

            @Override
            public void onStartTrackingTouch(MySeekArc mySeekArc) {

            }

            @Override
            public void onStopTrackingTouch(MySeekArc mySeekArc) {
//                to = getTotalSleepHourValue() / 60;
//                objectAnimator = timelyTextView.animate(from, to);
//                objectAnimator.setDuration(ONE_ARC_WHOLE_VALUE);
//                objectAnimator.start();
//                from = to;
            }
        });
    }

//    public float getXPos(SeekArc seekArc, int progress){
//        return (float) ((float) ((seekArc.getWidth()-seekArc.getPaddingRight()-seekArc.getPaddingLeft()))
//                * Math.cos(Math.toRadians(180-progress/2)) / 360);
//    }

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




