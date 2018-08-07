package themollo.app.mollo.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mbh.timelyview.TimelyShortTimeView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import themollo.app.mollo.account.MyAccountActivity;
import themollo.app.mollo.R;
import themollo.app.mollo.analysis.AnalysisActivity;
import themollo.app.mollo.lullaby.LullabyActivity;
import themollo.app.mollo.util.AppUtilBasement;
import themollo.app.mollo.util.BackPressController;
import themollo.app.mollo.util.SketchBook;

public class HomeActivity extends AppUtilBasement {

    @BindString(R.string.button_transition)
    String transitionName;

    @BindString(R.string.alarm_start_time)
    String alarmStartTime;

    @BindString(R.string.alarm_end_time)
    String alarmEndTime;

    @BindColor(R.color.moving_circle_color)
    int movingColor;

    @BindView(R.id.dlHomeLayout)
    DrawerLayout dlHomeLayout;

    @BindView(R.id.llHomeMenu)
    LinearLayout llHomeMenu;

    @BindView(R.id.llSettings)
    LinearLayout llSettings;

    @BindView(R.id.flContent)
    FrameLayout flContent;

    @BindView(R.id.ivDrawerArrow)
    ImageView ivDrawerArrow;

    @BindView(R.id.llStore)
    LinearLayout llStore;

    @BindView(R.id.llAlarm)
    LinearLayout llAlarm;

    @BindView(R.id.llLullaby)
    LinearLayout llLullaby;

    @BindView(R.id.llSleepPattern)
    LinearLayout llSleepPattern;

    @BindView(R.id.llDevice)
    LinearLayout llDevice;

    @BindView(R.id.llMyAccount)
    LinearLayout llAccount;

    @BindView(R.id.tvLullabyButton)
    TextView tvLullabyButton;

    @BindView(R.id.tvDiffuserButton)
    TextView tvDiffuserButton;

    @BindView(R.id.ttvCurTime)
    TimelyShortTimeView ttvCurTime;

    @BindView(R.id.rlAlarmButton)
    RelativeLayout rlAlarmButton;

    @BindView(R.id.tvStartAlarmTime)
    TextView tvStartAlarmTime;

    @BindView(R.id.tvEndAlarmTime)
    TextView tvEndAlarmTime;

    @BindView(R.id.llDrawer)
    LinearLayout llDrawer;

    private DrawerArrow drawerArrow;
    private float drawerOffset;
    private boolean flipped;
    private BackPressController backPressController;
    private Timer timer;

    @Override
    protected void onResume() {
        super.onResume();
        timer = new Timer();
        timer.scheduleAtFixedRate(getTimerTask(), 1500, 1500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
        timer = null;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        butterBind();

        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);
        rlAlarmButton.setTransitionName(transitionName);
        backPressController = new BackPressController(this);

        
        drawerArrow = new DrawerArrow(getResources());
        drawerArrow.setStrokeColor(getResources().getColor(R.color.light_gray));
        ivDrawerArrow.setImageDrawable(drawerArrow);

        ttvCurTime.setTextColor(Color.WHITE);
        ttvCurTime.setTimeFormat(TimelyShortTimeView.FORMAT_HOUR_MIN);
        ttvCurTime.setSeperatorsTextSize(50);
        ttvCurTime.setTime("99:99");
        ttvCurTime.setTime("00:00");

        setButtonListener();
        registerActionToggle();

    }

    @Override
    public void onBackPressed() {
        backPressController.onBackPressed();
    }

    public void registerActionToggle() {
        ActionBarDrawerToggle actionBarDrawerToggle
                = new ActionBarDrawerToggle(this, dlHomeLayout, R.string.open, R.string.close) {
            private float scaleXFactor = 1.5f;
            private float scaleYFactor = 8f;
            private float scaleFactor = 3f;

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = (float) (drawerView.getWidth() * (1.3) * slideOffset * (1.3));
//                float slideY = (float) (drawerView.getHeight()*(1.2) * slideOffset*(1.2));
                flContent.setTranslationX(slideX);
//                llContent.setScaleX(1-(slideOffset / scaleXFactor));
                flContent.setScaleY(1 - (slideOffset / scaleYFactor));
            }
        };
        dlHomeLayout.addDrawerListener(actionBarDrawerToggle);

    }

    @OnClick({R.id.tvDiffuserButton})
    void moveToDevice() {
        //popup
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.rlAlarmButton)
    void moveToAlarmAnim() {
//        moveTo(AlarmActivity.class);

        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this,
                rlAlarmButton, transitionName);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                Pair.create(rlAlarmButton, transitionName),
                Pair.create(tvStartAlarmTime, alarmStartTime),
                Pair.create(tvEndAlarmTime, alarmEndTime));

        Intent intent = new Intent(this, SketchBook.class);
        startActivity(intent, options.toBundle());

    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.tvLullabyButton)
    void moveToLullabyAnim() {
//        moveTo(LullabyActivity.class);

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                Pair.create(tvLullabyButton, transitionName));

        Intent intent = new Intent(this, LullabyActivity.class);
        startActivity(intent, options.toBundle());
    }


    @Override
    public void setButtonListener() {
        llHomeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlHomeLayout.openDrawer(Gravity.LEFT);
            }
        });

        dlHomeLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                drawerOffset = slideOffset;

                if (slideOffset >= .500) {
                    flipped = true;
                    drawerArrow.setFlip(flipped);
                } else if (slideOffset <= .005) {
                    flipped = false;
                    drawerArrow.setFlip(flipped);
                }

                drawerArrow.setParameter(drawerOffset);
            }
        });

        llStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://themollo.com/"));
                startActivity(intent);
            }
        });

        llAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(SketchBook.class);
            }
        });

        llLullaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(LullabyActivity.class);
            }
        });

        llSleepPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(AnalysisActivity.class);
            }
        });

        llAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(MyAccountActivity.class);
            }
        });


    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }
}
