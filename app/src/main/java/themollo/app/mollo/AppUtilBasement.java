package themollo.app.mollo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by alex on 2018. 7. 16..
 */

public abstract class AppUtilBasement extends AppCompatActivity{

    public static final String exampleName = "EN";
    public static final String CHART = "chart";
    public static final String ALARM = "alarm";
    public static final String LULLABY = "lullaby";
    public static final String HOME = "home";

    ProgressDialog pd;

    public void showPD(){
        if (pd == null) {
            pd = new ProgressDialog(this);
            pd.setCancelable(false); //임의 취소 불가
            pd.setMessage(".. loading ..");
        }
        pd.show();
    }

    public void stopPD(){
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    public void moveTo(Class cls){
        startActivity(new Intent(getBaseContext(), cls));
    }

    public abstract void setButtonListener();

}
