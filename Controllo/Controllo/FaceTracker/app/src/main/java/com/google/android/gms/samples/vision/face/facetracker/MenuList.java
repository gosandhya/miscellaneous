package com.google.android.gms.samples.vision.face.facetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MenuList extends AppCompatActivity {

    public static boolean camm = false;

    String [] menuitemss = new String[]
            {

                    "Instructions",
                    "Gesture Table",
                    "Gesture Expression",
                    "Switch Camera",
                    "Defaut/custom",
                    "Emergency Contact",
                    "Demo",
                    "About"
            };

    String [] category = new String[]
            {

                    "Instructions",
                    "Gesture Table",
                    "Gesture Expression",
                    "Switch Camera",
                    "Emergency Contact",
                    "Defaut/custom",
                    "Emergency Contact",
                    "Demo",
                    "About"
            };
    Integer [] menuiconss = {


            R.drawable.instuctgreen,
            R.drawable.tablegreen,
            R.drawable.settinggreen,
            R.drawable.camswitch,
            R.drawable.caligreen,
            R.drawable.setcontact,
            R.drawable.gesture,
            R.drawable.aboutus
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        CustomList adapter = new CustomList(MenuList.this,menuitemss, category, menuiconss);
        ListView list = (ListView) findViewById(R.id.listview);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                if( menuitemss[i] ==  "About")
                {
                    Intent intent = new Intent(getApplicationContext(),About.class);
                    startActivity(intent);
                }
                if( menuitemss[i] == "Instructions")
                {
                    Intent intent = new Intent(getApplicationContext(),Instruction_Controller.class);
                    startActivity(intent);

                }
                if( menuitemss[i] == "Switch Camera")
                {
                    //Intent intent = new Intent(getApplicationContext(),Instruction_Controller.class);
                   // startActivity(intent);

                    if (camm) {
                        camm = false;
                    }
                    else {
                        camm = true;
                    }
                    Intent intent = new Intent(getApplicationContext(),FaceTrackerActivity.class);
                     startActivity(intent);


                }if(menuitemss[i] == "Emergency Contact")
               {
                   Intent intent = new Intent(getApplicationContext(),SetContact.class);
                   startActivity(intent);
               }

                if(menuitemss[i] == "Gesture Table")
               {
                   Intent intent = new Intent(getApplicationContext(),GestureTable.class);
                   startActivity(intent);
               }if(menuitemss[i] == "Defaut/custom")
               {
                   Intent intent = new Intent(getApplicationContext(),Chooser.class);
                   startActivity(intent);
               }if(menuitemss[i] == "Gesture Expression")
               {
                   Intent intent = new Intent(getApplicationContext(),GestureExpression.class);
                   startActivity(intent);
               }
                if(menuitemss[i] == "Demo")
               {
                   Intent intent = new Intent(getApplicationContext(),Demo_Controller.class);
                   startActivity(intent);
               }

                // Toast.makeText(MainActivity.this,"You Clicked "+ places[+ i],Toast.LENGTH_SHORT).show();
             /*   Intent goToSecond = new Intent();
               // goToSecond.setClass(MenuList.this, secondActivity.class);
                goToSecond.putExtra("image", imageId[i]);
                goToSecond.putExtra("place", places[i]);
                //goToSecond.putExtra("position",i);
                startActivity(goToSecond);*/
            }
        });
    }
}
