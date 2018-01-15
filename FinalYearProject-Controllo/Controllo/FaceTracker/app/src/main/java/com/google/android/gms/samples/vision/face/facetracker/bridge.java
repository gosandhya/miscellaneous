package com.google.android.gms.samples.vision.face.facetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import android.database.Cursor;
import android.util.Log;

public class bridge extends AppCompatActivity {
    SQLiteDatabase mydatabase;

    public static double usmile;
    public static double lefteye;
    public static double righteye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge);



    }
}
