package com.google.android.gms.samples.vision.face.facetracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Instruction_4 extends Fragment {

    TextView mTextView;
    TextView mTextView11;
    boolean checkLang = false;
    public Instruction_4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_instruction_4, container,
                false);


       final Button button_translate = (Button) rootView.findViewById(R.id.transBtn);
        button_translate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(checkLang == true)
                {
                    mTextView = (TextView)getView().findViewById(R.id.instruct4);
                    mTextView.setText("On success you will hear a beep noise. If you do not hear the beep, go back to step 3");

                    mTextView11 = (TextView)getView().findViewById(R.id.trans4);
                    mTextView11.setText("STEP 4:");
                    button_translate.setText("اردو");

                    checkLang = false;
                }
                else
                {
                    mTextView = (TextView)getView().findViewById(R.id.instruct4);
                    mTextView.setText("اس کامیاب ٹیسٹ پر بیپ کی آواز سناءی دیگی۔ اگر یہ آواز سناءی  نہ دے تو دوبارہ سے تیسرا مرحلہ دوہراءیں");

                    mTextView11 = (TextView)getView().findViewById(R.id.trans4);
                    mTextView11.setText("چوتھا مرحلہ");

                    button_translate.setText("English");


                    checkLang = true;
                }
            }
        });

        return rootView;

    }

}
