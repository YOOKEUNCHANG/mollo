package themollo.app.mollo.lullaby;

import android.graphics.drawable.Drawable;
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

import com.github.ybq.nougatbootanimation.NougatBoot;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;
import themollo.app.mollo.util.AppUtilBasement;
import themollo.app.mollo.util.LullabyAnimator;

public class LullabyActivity extends AppUtilBasement {

    @BindView(R.id.pbProgrssBar)
    ProgressBar pbProgressBar;

    @BindView(R.id.rvLullabyList)
    RecyclerView rvLullabyList;

    @BindView(R.id.llBack)
    LinearLayout llBack;

    private Drawable boot = new LullabyAnimator();
    private LullabyAdapter lullabyAdapter;
    private ArrayList<LullabyModel> lullabyData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lullaby);
        butterBind();

        lullabyData.add(new LullabyModel(getString(R.string.default_sound_title), "03:00", true));
        lullabyData.add(new LullabyModel(getString(R.string.default_sound_title), "03:00", true));
        lullabyData.add(new LullabyModel(getString(R.string.default_sound_title), "03:00", true));
        lullabyData.add(new LullabyModel(getString(R.string.default_sound_title), "03:00", true));

        rvLullabyList.setHasFixedSize(false);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(false);
        rvLullabyList.setLayoutManager(linearLayoutManager);

        lullabyAdapter = new LullabyAdapter(lullabyData);
        rvLullabyList.setAdapter(lullabyAdapter);

        pbProgressBar.setIndeterminateDrawable(boot);
    }

    @Override
    public void setButtonListener() {
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
            lullabyViewHolder.ivHeart.setImageResource(R.drawable.heart_white);

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

        public LullabyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

