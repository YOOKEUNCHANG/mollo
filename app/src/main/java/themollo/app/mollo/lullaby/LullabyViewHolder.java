package themollo.app.mollo.lullaby;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import themollo.app.mollo.R;

/**
 * Created by alex on 2018. 7. 19..
 */

public class LullabyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ciCircleImage)
    CircleImageView ciCircleImage;
    @BindView(R.id.tvLullabyTitle)
    TextView tvLullabyTitle;
    @BindView(R.id.llFav)
    LinearLayout llFav;
    @BindView(R.id.ivFav)
    ImageView ivFav;

    public LullabyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
