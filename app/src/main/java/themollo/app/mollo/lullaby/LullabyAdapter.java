package themollo.app.mollo.lullaby;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.login.LoginManager;

import java.util.ArrayList;

import themollo.app.mollo.R;

/**
 * Created by alex on 2018. 7. 19..
 */

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

        lullabyViewHolder.tvLullabyTitle.setText(lullabyModel.getLullabyTitle());
        if(lullabyModel.isSelected()){
            lullabyViewHolder.ivFav.setImageResource(R.drawable.heart_filled);
        }else{
            lullabyViewHolder.ivFav.setImageResource(R.drawable.heart_empty);
        }

        lullabyViewHolder.llFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if empty, post to server
                //else, remove from server
            }
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
