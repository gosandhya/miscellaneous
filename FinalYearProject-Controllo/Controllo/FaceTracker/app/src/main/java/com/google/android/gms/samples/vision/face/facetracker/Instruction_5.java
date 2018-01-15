package com.google.android.gms.samples.vision.face.facetracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Instruction_5 extends Fragment {

    TextView mTextView;
    TextView mTextView11;
    boolean checkLang = false;
    public Instruction_5() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_instruction_5, container,
                false);


        Button button_demo = (Button) rootView.findViewById(R.id.button_demo);
        button_demo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Demo_Controller.class);
                startActivity(intent);
            }
        });



        final Button button_translate = (Button) rootView.findViewById(R.id.transBtn);
        button_translate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(checkLang == true)
                {
                    mTextView = (TextView)getView().findViewById(R.id.instruct5);
                    mTextView.setText("After the beep, your app is now ready for a task.\n\nEvery task has two more gestures. Each gesture is followed by the same beep. \n\nTo perform another task, go back to step 3. \n\nRefer to the gesture table and gesture expressions in the menu to perform a task. ");

                    mTextView11 = (TextView)getView().findViewById(R.id.trans5);
                    mTextView11.setText("STEP 5:");

                    button_translate.setText("اردو");

                    checkLang = false;
                }
                else
                {
                    mTextView = (TextView)getView().findViewById(R.id.instruct5);
                    mTextView.setText("بیپ کے بعد آپکا پروگرام کام کرنے کے لیے تیار ہے ۔\n" +
                            "ہر کام کے دو اور تاثرات ہیں۔ ہر تاثر ک بعد اسی طرح بیپ سناءی دیگی۔\n" +
                            "اگلے کام کے لیے تیسرے مرحلے  پر واپس جاءیں۔" +
                            "دوسرے کام انجام دینے کے لیے منیوں میں جسچر ٹیبل (gesture table) اور جسچر  ایکسپرش یشن پے رجوں کریں۔");

                    mTextView11 = (TextView)getView().findViewById(R.id.trans5);
                    mTextView11.setText("پانچواں مرحلہ");

                    button_translate.setText("English");


                    checkLang = true;
                }
            }
        });


        return rootView;
    }

}
