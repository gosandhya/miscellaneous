package com.google.android.gms.samples.vision.face.facetracker;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class demo_1 extends Fragment {
    TextView mTextView;
    TextView mTextView11;
    boolean checkLang = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_demo_1, container,
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
                    mTextView = (TextView)getView().findViewById(R.id.demotrans1);
                    mTextView.setText("Task: Turn the lamp on.");

                    mTextView11 = (TextView)getView().findViewById(R.id.demoinst1);
                    mTextView11.setText("Lamp is off.");

                    button_translate.setText("اردو");

                    checkLang = false;
                }
                else
                {
                    mTextView = (TextView)getView().findViewById(R.id.demotrans1);
                    mTextView.setText("کام:لیمپ کو روشن کرنا");

                    mTextView11 = (TextView)getView().findViewById(R.id.demoinst1);
                    mTextView11.setText("لیمپ بجھا ہوا ہے۔");

                    button_translate.setText("English");


                    checkLang = true;
                }

            }
        });


        return rootView;
    }
}