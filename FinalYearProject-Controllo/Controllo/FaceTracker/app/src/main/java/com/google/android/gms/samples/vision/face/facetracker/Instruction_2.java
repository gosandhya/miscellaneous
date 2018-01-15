package com.google.android.gms.samples.vision.face.facetracker;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Instruction_2 extends Fragment {

    TextView mTextView;
    TextView mTextView11;
    boolean checkLang = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_instruction_2, container,
                false);


        final Button button_translate = (Button) rootView.findViewById(R.id.transBtn);
        button_translate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(checkLang == true)
                {
                    mTextView = (TextView)getView().findViewById(R.id.instruct2);
                    mTextView.setText("Ensure that the user's entire face is inside the camera frame.");

                    mTextView11 = (TextView)getView().findViewById(R.id.trans2);
                    mTextView11.setText("STEP : 02");
                    button_translate.setText("اردو");

                    checkLang = false;
                }
                else
                {
                    mTextView = (TextView)getView().findViewById(R.id.instruct2);
                    mTextView.setText("خیال رکھیں کے پورا چہرا کیمرے کے فرءیم میں آجاءے");

                    mTextView11 = (TextView)getView().findViewById(R.id.trans2);
                    mTextView11.setText("دوسرا مرحلہ");

                    button_translate.setText("English");

                    checkLang = true;
                }

            }
        });



        return rootView;
    }
}
