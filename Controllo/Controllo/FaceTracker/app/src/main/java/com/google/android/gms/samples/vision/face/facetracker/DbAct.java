package com.google.android.gms.samples.vision.face.facetracker;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.hardware.ConsumerIrManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class DbAct extends Activity {

    SQLiteDatabase mydatabase;

    public static String userSetSmile;
    //public static boolean selfCalib = true; //THIS HAS TO BE IN UI RELATED OPTION! AND INITIALLY SHOULD BE FALSE
    public static boolean isCalibrated; // an internal boolean
    public static boolean nowValRet = false; //boolean for once the values are ret.
    public static int storedGestures;
    public static float[][] retVal = new float[8][3];
    public static boolean camm;
    //    ConsumerIrManager manager = (ConsumerIrManager)getSystemService(Context.CONSUMER_IR_SERVICE);
    TextView tellUser;
    TextView mHeading;
    String numm;
    String forUser = "0";
    String steps = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        setTitle("Database");


//
        mydatabase = openOrCreateDatabase("controllo.db",MODE_PRIVATE,null);
//        mydatabase.execSQL("DROP TABLE IF EXISTS " + "userData" + ";");
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS " + "userData" + " (userId INTEGER PRIMARY KEY AUTOINCREMENT, rightE VARCHAR, leftE VARCHAR,smile VARCHAR);");


        //for checking if pre-defined values already exist in the DB
        if (checkDB()) {
            //2D array here for inserting values from DB
            Cursor resultSet = mydatabase.rawQuery("select * from userData;", null);
            int totalInDb = resultSet.getCount();
            Log.d("DBretrieve", Integer.toString(totalInDb));
//            resultSet.moveToFirst();

            resultSet.moveToFirst();


            for (int i =0; i< totalInDb; i++) {
                int gett = 1;
                for (int j=0; j<3; j++) {

                    String inn = resultSet.getString(gett);
                    retVal[i][j] = Float.valueOf(inn);
//                        Log.d("DBretrieve", inn);
                    gett ++;
                }
//
//                    String a = resultSet.getString(1);
//                    String b = resultSet.getString(2);
//                    String c = resultSet.getString(3);
//                    Log.d("DBretrieve", "gesture done");
                resultSet.moveToNext();
            }
            for (int i =0; i<totalInDb; i++) {
                for (int j=0; j<3; j++) {
                    Log.d("DBretrieve", Float.toString(retVal[i][j]));
                }
                Log.d("DBretrieve", "Gesture done");
            }

            nowValRet = true;
            //resultSet.moveToFirst();
//            resultSet.moveToPosition(resultSet.getCount()-1);
//            String smileInDB = resultSet.getString(1);
//            userSetSmile = smileInDB;
//            Log.d("DBControllo", smileInDB);
//            Log.d("DBControllo", "got it");
        }
        else {
            Log.d("DBControllo", "not yet");
            String getSmile = String.format("%.2f",Tweak.uSmile);
            String getLE = String.format("%.2f",Tweak.uLE);
            String getRE = String.format("%.2f", Tweak.uRE);
            Log.d("DBControllo", getSmile);
            if (Tweak.uSmile > 0.00 && Tweak.uLE >0.00 && Tweak.uRE > 0.00) {
                mydatabase.execSQL("INSERT INTO userData (smile,rightE,leftE) VALUES(" + getSmile + ","+getRE+","+getLE+");");
                userSetSmile = getSmile;
            }
        }

        tellUser = (TextView)findViewById(R.id.textView4);
        mHeading = (TextView)findViewById(R.id.textView2);
        numm = Integer.toString(numOfGest());
        storedGestures = numOfGest();

        if (storedGestures == 0) {
            forUser= "Controllo has " + numm+ " gestures,kindly make R:open, L:open, S:no"; //for telling users
            steps = "GESTURE 1";
        }
        else if (storedGestures == 1) {
            forUser= "Controllo has " + numm+ " gestures, kindly make R:open, L:open, S:yes"; //for telling users
            steps = "GESTURE 2";
        }
        else if (storedGestures == 2) {
            forUser= "Controllo has " + numm+ " gestures, kindly make R:close, L:close, S:no"; //for telling users
            steps = "GESTURE 3";
        }
        else if (storedGestures == 3) {
            forUser= "Controllo has " + numm+ " gestures, kindly make R:close, L:close, S:yes"; //for telling users
            steps = "GESTURE 4";
        }
        else if (storedGestures == 4) {
            forUser= "Controllo has " + numm+ " gestures, kindly make R:close, L:open, S:yes"; //for telling users
            steps = "GESTURE 4";
        }

        else if (storedGestures == 5) {
            forUser = "Controllo has " + numm + " and is Ready"; //for telling users
            steps = "READY!";

            if (retVal == null){
                Cursor resultSet = mydatabase.rawQuery("select * from userData;", null);
                int totalInDb = resultSet.getCount();
                Log.d("DBretrieve", Integer.toString(totalInDb));
//            resultSet.moveToFirst();

                resultSet.moveToFirst();


                for (int i =0; i< totalInDb; i++) {
                    int gett = 1;
                    for (int j=0; j<3; j++) {

                        String inn = resultSet.getString(gett);
                        retVal[i][j] = Float.valueOf(inn);
//                        Log.d("DBretrieve", inn);
                        gett ++;
                    }
//
//                    String a = resultSet.getString(1);
//                    String b = resultSet.getString(2);
//                    String c = resultSet.getString(3);
//                    Log.d("DBretrieve", "gesture done");
                    resultSet.moveToNext();
                }
                for (int i =0; i<totalInDb; i++) {
                    for (int j=0; j<3; j++) {
                        Log.d("DBretrieve", Float.toString(retVal[i][j]));
                    }
                    Log.d("DBretrieve", "Gesture done");
                }

                nowValRet = true;

            }

        }
//

        tellUser.setText(forUser);
        mHeading.setText(steps);

        //down here the text will be changed accordingly



    }

    public boolean checkDB() {
        boolean innerBool;
        Cursor resultSet = mydatabase.rawQuery("select * from userData",null);
        if (resultSet.getCount() == 8) {
            innerBool = true;
        }
        else {
            innerBool = false;
        }
        return innerBool;
    }

    public int numOfGest() {
        int total = 0;
        Cursor resultSet = mydatabase.rawQuery("select * from userData",null);
        total = resultSet.getCount();

        return total;
    }


    public void gotoNext(View view) {
        Intent i = new Intent(getApplicationContext(),FaceTrackerActivity.class);
        startActivity(i);
        setContentView(R.layout.main);
    }

    public void removeCal(View view) {
        mydatabase.execSQL("DROP TABLE IF EXISTS  " + "userData" + ";");
        userSetSmile = null;
        isCalibrated = false;
        nowValRet = false;
        numm = "0";
        forUser= "Controllo has " + numm+ " R:open, L:open, S:no"; //for telling users
        steps = "GESTURE 1";
        tellUser.setText(forUser);
        mHeading.setText(steps);

    }
//MASLA HERE

    //    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void checkIR(View view) {
        if (camm) {
            camm = false;
        }
        if (!camm) {
            camm = true;
        }



        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // only for gingerbread and newer versions
            IRwork();

        }
        else {
            Toast.makeText(getApplication(), "get an expensive cell",
                    Toast.LENGTH_LONG).show();
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void IRwork() {
        ConsumerIrManager mCIR= (ConsumerIrManager) getSystemService(android.content.Context.CONSUMER_IR_SERVICE);
        if (mCIR.hasIrEmitter()) {
            Toast.makeText(getApplication(), "Your expensive cell have IR",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplication(), "Your expensive cell have IR",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void setContact (View view) {
        Intent i = new Intent(getApplicationContext(),SetContact.class);
        startActivity(i);
        setContentView(R.layout.activity_set_emer_contact);
    }

//    private void takeScreenshot() {
//        Date now = new Date();
//        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
//
//        try {
//            // image naming and path  to include sd card  appending name you choose for file
//            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";
//
//            // create bitmap screen capture
//            View v1 = getWindow().getDecorView().getRootView();
//            v1.setDrawingCacheEnabled(true);
//            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
//            v1.setDrawingCacheEnabled(false);
//
//            File imageFile = new File(mPath);
//
//            FileOutputStream outputStream = new FileOutputStream(imageFile);
//            int quality = 100;
//            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
//            outputStream.flush();
//            outputStream.close();
//
//            openScreenshot(imageFile);
//        } catch (Throwable e) {
//            // Several error may come out with file handling or OOM
//            e.printStackTrace();
//        }
//    }
//
//    private void openScreenshot(File imageFile) {
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        Uri uri = Uri.fromFile(imageFile);
//        intent.setDataAndType(uri, "image/*");
//        startActivity(intent);
//    }



}