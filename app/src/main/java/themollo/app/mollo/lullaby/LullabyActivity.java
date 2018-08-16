package themollo.app.mollo.lullaby;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.co.recruit_lifestyle.android.widget.PlayPauseButton;
import themollo.app.mollo.R;
import themollo.app.mollo.util.AppUtilBasement;
import themollo.app.mollo.util.LullabyAnimator;

public class LullabyActivity extends AppUtilBasement {

    @BindString(R.string.button_transition)
    String transitionName;

    @BindView(R.id.pbProgrssBar)
    ProgressBar pbProgressBar;

    @BindView(R.id.rvLullabyList)
    RecyclerView rvLullabyList;

    @BindView(R.id.llBack)
    LinearLayout llBack;


    @BindView(R.id.ppbPlayPauseButton)
    PlayPauseButton ppbPlayPauseButton;

    private static final String NOT_START = "notstart";
    private static final String PLAYING = "playing";
    private static final String PAUSE = "pause";

    private static final String JK_URL = "https://www.youtube.com/watch?v=DXw38O1Y7PE";
    private MediaPlayer mediaPlayer;
    private Drawable boot = new LullabyAnimator();
    private LullabyAdapter lullabyAdapter;
    private ArrayList<LullabyModel> lullabyData = new ArrayList<>();

    AnimationDrawable animationDrawable;
    ConstraintLayout llLullaby;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lullaby);
        butterBind();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pbProgressBar.setTransitionName(transitionName);
        }

        pbProgressBar.setIndeterminateDrawable(boot);

        llLullaby = findViewById(R.id.llLullaby);
        llLullaby.setBackgroundResource(R.drawable.gradient_list);

        animationDrawable = (AnimationDrawable) llLullaby.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);

        lullabyData.add(new LullabyModel(getString(R.string.rainy_day), "03:30", true, R.raw.sample1));
        lullabyData.add(new LullabyModel(getString(R.string.summer_night), "03:00", false, R.raw.sample2));
        lullabyData.add(new LullabyModel(getString(R.string.silent_forest), "05:30", false));
        lullabyData.add(new LullabyModel(getString(R.string.afternoon_at_cafe), "04:30", true));

        rvLullabyList.setHasFixedSize(false);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(false);
        rvLullabyList.setLayoutManager(linearLayoutManager);

        lullabyAdapter = new LullabyAdapter(lullabyData);
        rvLullabyList.setAdapter(lullabyAdapter);

        ppbPlayPauseButton.setColor(Color.parseColor("#8B8AFF"));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus)
            animationDrawable.start();
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
    public void setButtonListener() {

    }

    @OnClick(R.id.llBack)
    void backPress() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }

    public class LullabyAdapter extends RecyclerView.Adapter {

        ArrayList<LullabyModel> data = new ArrayList<>();

        public LullabyAdapter(ArrayList<LullabyModel> data) {
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.lullaby_view, parent, false);
            return new LullabyViewHolder(item);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            LullabyModel lullabyModel = data.get(position);
            LullabyViewHolder lullabyViewHolder = (LullabyViewHolder) holder;

            lullabyViewHolder.tvLullabyTitle.setText("" + lullabyModel.getLullabyTitle());
            lullabyViewHolder.tvPlayTime.setText("" + lullabyModel.getLullabyPlayTime());
            if (lullabyModel.isSelected()) {
                lullabyViewHolder.ivHeart.setImageResource(R.drawable.heart_filled);
            } else {
                lullabyViewHolder.ivHeart.setImageResource(R.drawable.heart_empty);
            }


            lullabyViewHolder.ppbPlayState.setColor(Color.WHITE);
            lullabyViewHolder.ppbPlayState.setOnControlStatusChangeListener(new PlayPauseButton.OnControlStatusChangeListener() {
                @Override
                public void onStatusChange(View view, boolean state) {
                    Log.i("mpmp", "state : " + state);

                    if (state) {
                        mediaPlayer = MediaPlayer.create(getBaseContext(), lullabyModel.getRawFileName());
                        mediaPlayer.start();
                    } else {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    public class LullabyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvLullabyTitle)
        TextView tvLullabyTitle;
        @BindView(R.id.tvPlayTime)
        TextView tvPlayTime;
        @BindView(R.id.rlHeart)
        RelativeLayout rlHeart;
        @BindView(R.id.ivHeart)
        ImageView ivHeart;
        @BindView(R.id.ppbPlayState)
        PlayPauseButton ppbPlayState;
        @BindView(R.id.llLullabyView)
        LinearLayout llLullabyView;

        public LullabyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

