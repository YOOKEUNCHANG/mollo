package themollo.app.mollo.lullaby;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private Drawable boot = new LullabyAnimator();
    private LullabyAdapter lullabyAdapter;
    private ArrayList<LullabyModel> lullabyData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lullaby);
        butterBind();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(null);
            getWindow().setExitTransition(null);
            pbProgressBar.setTransitionName(transitionName);
        }

        pbProgressBar.setIndeterminateDrawable(boot);

        lullabyData.add(new LullabyModel(getString(R.string.rainy_day), "03:30", true, true));
        lullabyData.add(new LullabyModel(getString(R.string.summer_night), "03:00", false, false));
        lullabyData.add(new LullabyModel(getString(R.string.silent_forest), "05:30", false, false));
        lullabyData.add(new LullabyModel(getString(R.string.afternoon_at_cafe), "04:30", true, false));

        rvLullabyList.setHasFixedSize(false);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(false);
        rvLullabyList.setLayoutManager(linearLayoutManager);

        lullabyAdapter = new LullabyAdapter(lullabyData);
        rvLullabyList.setAdapter(lullabyAdapter);

        ppbPlayPauseButton.setColor(Color.parseColor("#8B8AFF"));


    }

    @Override
    public void setButtonListener() {

    }

    @OnClick(R.id.llBack)
    void backPress(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }else{
            finish();
        }
    }

    @Override
    public void butterBind() {
        ButterKnife.bind(this);
    }

    public class LullabyAdapter extends RecyclerView.Adapter{

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

            lullabyViewHolder.tvLullabyTitle.setText(""+lullabyModel.getLullabyTitle());
            lullabyViewHolder.tvPlayTime.setText(""+lullabyModel.getLullabyPlayTime());
            if(lullabyModel.isSelected()){
                lullabyViewHolder.ivHeart.setImageResource(R.drawable.heart_filled);
            }else{
                lullabyViewHolder.ivHeart.setImageResource(R.drawable.heart_empty);
            }

            if(lullabyModel.isPlaying()){
                lullabyViewHolder.ivPlayState.setImageResource(R.drawable.pause);
            }else{
                lullabyViewHolder.ivPlayState.setImageResource(R.drawable.play);
            }

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
        @BindView(R.id.ivPlayState)
        ImageView ivPlayState;

        public LullabyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

