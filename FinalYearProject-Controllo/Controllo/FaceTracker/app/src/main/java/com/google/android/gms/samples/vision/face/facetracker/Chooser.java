package com.google.android.gms.samples.vision.face.facetracker;

import android.content.Intent;
import android.hardware.camera2.params.Face;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.view.View;
import android.widget.RadioGroup;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.util.Log;
public class Chooser extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button okButton;
    public static boolean selfCalib = false;
    public String inRad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        setTitle("Choose Method");

        addListenerOnButton();




    }

    public void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        okButton = (Button) findViewById(R.id.btnOk);


        okButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);
                inRad = (String)radioButton.getText();

                Toast.makeText(Chooser.this,
                        inRad, Toast.LENGTH_SHORT).show();
                Log.d("radioButt", inRad);

                if (inRad.equals("Personalized Settings")){
                    selfCalib = true;
                    Intent i = new Intent(getApplicationContext(),DbAct.class);
                    startActivity(i);
                }
                else{
                    selfCalib = false;
                    Intent i = new Intent(getApplicationContext(),FaceTrackerActivity.class);
                    startActivity(i);
                }

//                gotoDb();


            }

        });


    }


    public void gotoDb () {
        if (inRad == "default") {
            selfCalib = false;
            Intent i = new Intent(getApplicationContext(),FaceTrackerActivity.class);
            startActivity(i);
            setContentView(R.layout.main);
        }
        if (inRad == "self") {
            selfCalib = true;
            Intent i = new Intent(getApplicationContext(), DbAct.class);
            startActivity(i);
            setContentView(R.layout.activity_db);
        }

    }

    public void gotoCam () {


    }

//
//    public void onRadioButtonClicked() {
//
////        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
//        okButton = (Button) findViewById(R.id.btnOk);
//        Intent i = new Intent(getApplicationContext(),FirstScreen.class);
//        startActivity(i);
//        setContentView(R.layout.activity_first_screen);
//    }

}