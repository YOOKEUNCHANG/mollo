package themollo.app.mollo.lullaby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import themollo.app.mollo.R;

public class LullabyListActivity extends AppCompatActivity {

    @BindView(R.id.rvLullabyList)
    RecyclerView rvLullabyList;

    private LullabyAdapter adapter;
    private ArrayList<LullabyModel> lullabyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lullaby_list);

        lullabyList.add(new LullabyModel("url", "", true));
        lullabyList.add(new LullabyModel("url", "", true));
        lullabyList.add(new LullabyModel("url", "", true));
        lullabyList.add(new LullabyModel("url", "", true));


        adapter = new LullabyAdapter(lullabyList);
        rvLullabyList.setAdapter(adapter);
    }
}
