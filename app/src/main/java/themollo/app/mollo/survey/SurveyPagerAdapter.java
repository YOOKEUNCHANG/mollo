package themollo.app.mollo.survey;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by alex on 2018. 7. 16..
 */

public class SurveyPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_OF_FRAGMENT = 5;

    public SurveyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Survey_p1();
            case 1:
                return new Survey_p2();
            case 2:
                return new Survey_p3();
            case 3:
                return new Survey_p4();
            case 4:
                return new Survey_p5();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_OF_FRAGMENT;
    }

}
