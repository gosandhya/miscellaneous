package com.google.android.gms.samples.vision.face.facetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
public class Demo_Controller extends ActionBarActivity {
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo__controller);

        setTitle("Demo");
        mViewPager = (ViewPager) findViewById(R.id.demo);
/** set the adapter for ViewPager */
        mViewPager.setAdapter(new SamplePagerAdapter(
                getSupportFragmentManager()));

    }

    public class SamplePagerAdapter extends FragmentPagerAdapter {

        public SamplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            /** Show a Fragment based on the position of the current screen */
            if (position == 0) {
                return new demo_1();
            } else if(position == 1)
            {
                return new demo_2();
            }
            else if(position == 2)
            {
                return new demo_3();
            }
            else if(position == 3)
            {
                return new demo_4();
            }
            else
            {
                return new demo_5();
            }

        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 5;
        }
    }
}



