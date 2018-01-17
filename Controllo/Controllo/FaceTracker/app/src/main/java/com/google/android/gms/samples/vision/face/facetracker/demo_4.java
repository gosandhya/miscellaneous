package com.google.android.gms.samples.vision.face.facetracker;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class demo_4 extends Fragment {
    TextView mTextView;
    TextView mTextView11;
    boolean checkLang = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_demo_4, container,
                false);

        final Button button_translate = (Button) rootView.findViewById(R.id.transBtn);
        button_translate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(checkLang == true)
                {


                    mTextView11 = (TextView)getView().findViewById(R.id.demoinst4);
                    mTextView11.setText("Perform 2nd gesture (G3) for up to 5 seconds. On success you will hear a beep.");
                    button_translate.setText("اردو");

                    checkLang = false;
                }
                else
                {

                    mTextView11 = (TextView)getView().findViewById(R.id.demoinst4);
                    mTextView11.setText("اب چہرے پر دوسرے جسچر (جی3) کے تاثرات کو پانچ سیکند انجام دیں جیس کے بعد بیپ کی آواز سناءی دے گی۔");

                    button_translate.setText("English");

                    checkLang = true;
                }

            }
        });



        return rootView;
    }
}