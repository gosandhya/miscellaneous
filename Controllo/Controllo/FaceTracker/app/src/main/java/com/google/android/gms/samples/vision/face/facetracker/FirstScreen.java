package com.google.android.gms.samples.vision.face.facetracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;
import android.graphics.Color;
import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.annotation.TargetApi;
import android.widget.Toolbar;


public class FirstScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

      /*  ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#47A60C")));
       // bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#47A60C")));*/

       // versionChange();
    }

/*
public void versionChange()
{
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        APIChange();
    }

}
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void APIChange()
    {
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#47A60C")));
    }
*/

    public void enter(View v)
    {

        Intent goTosecond = new Intent();
        goTosecond.setClass(this, FaceTrackerActivity.class);
       // goTosecond.putExtra("nbStars",rating);
        startActivity(goTosecond);
      //  finish();
    }
}
