package themollo.app.mollo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.triggertrap.seekarc.SeekArc;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CircularBarActivity extends AppCompatActivity {

    @BindView(R.id.seekArc)
    SeekArc seekArc;
    @BindView(R.id.tvRotation)
    TextView tvRotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_bar);
        ButterKnife.bind(this);

        seekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int i, boolean b) {
                tvRotation.setText(""+i);
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {

            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {

            }
        });

    }
}
