package com.google.android.gms.samples.vision.face.facetracker;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class demo_5 extends Fragment {
    TextView mTextView;
    TextView mTextView11;
    boolean checkLang = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_demo_5, container,
                false);


        Button button_demo = (Button) rootView.findViewById(R.id.button_demo);
        button_demo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetContact.class);
                startActivity(intent);
            }
        });

        final Button button_translate = (Button) rootView.findViewById(R.id.transBtn);
        button_translate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(checkLang == true)
                {


                    mTextView11 = (TextView)getView().findViewById(R.id.demoinst5);
                    mTextView11.setText("Lamp is on.");
                    button_translate.setText("اردو");

                    checkLang = false;
                }
                else
                {

                    mTextView11 = (TextView)getView().findViewById(R.id.demoinst5);
                    mTextView11.setText("لیمپ روشن ہے۔");
                    button_translate.setText("English");

                    checkLang = true;
                }

            }
        });







        return rootView;
    }
}