package themollo.app.mollo.util;

import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import themollo.app.mollo.R;

public class SketchBook2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch_book2);

        ConstraintLayout layout = findViewById(R.id.layout);
        layout.setBackgroundResource(R.drawable.gradient_list);
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
    }
}
