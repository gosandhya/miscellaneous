package com.google.android.gms.samples.vision.face.facetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;


public class Instruction_1 extends Fragment {
    TextView mTextView;
    TextView mTextView11;
    boolean checkLang = false;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_instruction_1, container,
                false);

        Button button_demo = (Button) rootView.findViewById(R.id.goToCamBtn);
        button_demo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FaceTrackerActivity.class);
                startActivity(intent);
            }
        });


        final Button button_translate = (Button) rootView.findViewById(R.id.transBtn);
        button_translate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(checkLang == true)
                {
                    mTextView = (TextView)getView().findViewById(R.id.ins1_text);
                    mTextView.setText("Place the smartphone directly facing the user.");

                    mTextView11 = (TextView)getView().findViewById(R.id.step1);
                    mTextView11.setText("STEP : 01");

                    button_translate.setText("اردو");


                    checkLang = false;
                }
                else
                {
                    mTextView = (TextView)getView().findViewById(R.id.ins1_text);
                    mTextView.setText("اسمارٹ فون کو استمعال کرنے والے کے چہرے کے سامنے رکھیں۔");

                    mTextView11 = (TextView)getView().findViewById(R.id.step1);
                    mTextView11.setText("پہلا مرحلہ");

                    button_translate.setText("English");

                    checkLang = true;
                }

            }
        });




        return rootView;
    }


}