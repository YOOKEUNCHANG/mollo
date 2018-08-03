package themollo.app.mollo.home;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import themollo.app.mollo.MyAccountActivity;
import themollo.app.mollo.R;
import themollo.app.mollo.alarm.AlarmActivity;
import themollo.app.mollo.lullaby.LullabyActivity;
import themollo.app.mollo.util.AppUtilBasement;
import themollo.app.mollo.util.BackPressController;
import themollo.app.mollo.util.SketchBook;

public class HomeActivity extends AppUtilBasement {

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

    @BindView(R.id.tvStore)
    TextView tvStore;

    @BindView(R.id.tvAlarm)
    TextView tvAlarm;

    @BindView(R.id.tvLullaby)
    TextView tvLullaby;

    @BindView(R.id.tvDevice)
    TextView tvDevice;

    @BindView(R.id.tvAccount)
    TextView tvAccount;

    @BindView(R.id.tvAlarmButton)
    TextView tvAlarmButton;

    @BindView(R.id.tvLullabyButton)
    TextView tvLullabyButton;

    @BindView(R.id.tvDiffuserButton)
    TextView tvDiffuserButton;


    private DrawerArrow drawerArrow;
    private float drawerOffset;
    private boolean flipped;
    private BackPressController backPressController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        butterBind();
        backPressController = new BackPressController(this);

        dlHomeLayout.setScrimColor(Color.TRANSPARENT);
        drawerArrow = new DrawerArrow(getResources());
        drawerArrow.setStrokeColor(getResources().getColor(R.color.light_gray));
        ivDrawerArrow.setImageDrawable(drawerArrow);

        setButtonListener();
        registerActionToggle();
    }

    @Override
    public void onBackPressed() {
        backPressController.onBackPressed();
    }

    public void registerActionToggle(){
        ActionBarDrawerToggle actionBarDrawerToggle
                = new ActionBarDrawerToggle(this, dlHomeLayout, R.string.open, R.string.close) {
            private float scaleXFactor = 1.5f;
            private float scaleYFactor = 8f;
            private float scaleFactor = 3f;

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = (float) (drawerView.getWidth()*(1.3) * slideOffset*(1.3));
//                float slideY = (float) (drawerView.getHeight()*(1.2) * slideOffset*(1.2));
                flContent.setTranslationX(slideX);
//                llContent.setScaleX(1-(slideOffset / scaleXFactor));
                flContent.setScaleY(1-(slideOffset / scaleYFactor));


            }
        };
        dlHomeLayout.addDrawerListener(actionBarDrawerToggle);
    }

    @OnClick({R.id.tvDiffuserButton, R.id.tvDevice})
    void moveToDevice(){
        //popup
    }

    @OnClick({R.id.tvAlarm, R.id.tvAlarmButton})
    void moveToAlarm(){
//        moveTo(AlarmActivity.class);
        moveTo(SketchBook.class);
    }

    @OnClick({R.id.tvLullaby, R.id.tvLullabyButton})
    void moveToLullaby(){
        moveTo(LullabyActivity.class);
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

                if(slideOffset >= .500){
                    flipped = true;
                    drawerArrow.setFlip(flipped);
                }else if(slideOffset <= .005){
                    flipped = false;
                    drawerArrow.setFlip(flipped);
                }

                drawerArrow.setParameter(drawerOffset);
            }
        });

        tvStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://themollo.com/"));
                startActivity(intent);
            }
        });

        tvAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTo(MyAccountActivity.class);
            }
        });

    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }
}
