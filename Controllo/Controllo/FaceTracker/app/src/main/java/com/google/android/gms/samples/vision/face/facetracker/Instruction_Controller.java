package com.google.android.gms.samples.vision.face.facetracker;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

public class Instruction_Controller extends AppCompatActivity {
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Instructions");
        mViewPager = (ViewPager) findViewById(R.id.pager);
/** set the adapter for ViewPager */
        mViewPager.setAdapter(new SamplePagerAdapter(
                getSupportFragmentManager()));

      /*  Intent intent;
        SharedPreferences prefs = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        boolean firstRun=prefs.getBoolean("firstRun",false);

        if(firstRun == false)
        {
            SharedPreferences.Editor ed = prefs.edit();
            ed.putBoolean("firstRun", true);
            ed.commit();
            intent = new Intent(this, FaceTrackerActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            intent = new Intent(this, Instruction_1.class);
            startActivity(intent);
            finish();
        }

*/

    }

    /** Defining a FragmentPagerAdapter class for controlling the fragments to be shown when user swipes on the screen. */
    public class SamplePagerAdapter extends FragmentPagerAdapter {

        public SamplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            /** Show a Fragment based on the position of the current screen */
            if (position == 0) {
                return new Instruction_1();
            } else if(position == 1)
            {
                return new Instruction_2();
            }
            else if(position == 2)
            {
                return new Instruction_3();
            }
            else if(position == 3)
            {
                return new Instruction_4();
            }
            else
            {
                return new Instruction_5();
            }


        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 5;
        }
    }
}


