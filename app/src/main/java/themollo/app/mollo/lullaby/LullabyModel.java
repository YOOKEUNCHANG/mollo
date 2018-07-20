package themollo.app.mollo.lullaby;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alex on 2018. 7. 19..
 */

public class LullabyModel {
    String imageUrl;
    String lullabyTitle;
    boolean selected;

    public LullabyModel(String imageUrl, String lullabyTitle, boolean selected) {
        this.imageUrl = imageUrl;
        this.lullabyTitle = lullabyTitle;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLullabyTitle() {
        return lullabyTitle;
    }

    public void setLullabyTitle(String lullabyTitle) {
        this.lullabyTitle = lullabyTitle;
    }


}
