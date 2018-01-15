package com.google.android.gms.samples.vision.face.facetracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

import org.w3c.dom.Text;

public class SetContact extends AppCompatActivity {

    String num;
    EditText editNum;
    EditText editName;
    EditText editMsg;
    public static String numToFt;
    public static String msgToFt;
    public String getNum;
    public String getMessage;

    SQLiteDatabase mydatabase;
    String TAG = "DBCONTACT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_emer_contact);

        editNum = (EditText)findViewById(R.id.editText2);
        editName = (EditText)findViewById(R.id.editText);
        editMsg = (EditText)findViewById(R.id.editText3);

        // TextView lbl = (TextView)findViewById(R.id.textView3);
        // lbl.setText("hola");


        mydatabase = openOrCreateDatabase("controllo.db",MODE_PRIVATE,null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS " + "contact" + " (userId INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, number VARCHAR, message VARCHAR);");
//        mydatabase.execSQL("INSERT INTO contact (name,number,message) VALUES('null','null','null');");
//      mydatabase.execSQL("INSERT INTO contact VALUES("");");
        Log.d(TAG, "onCreate");
        if (checkDb() == 1) {
            //val already present in db
            Cursor resultSet = mydatabase.rawQuery("select * from contact;", null);
            resultSet.moveToFirst();

            String getName = resultSet.getString(1);
            getNum = resultSet.getString(2);
            getMessage = resultSet.getString(3);

            numToFt = getNum;
            msgToFt = getMessage;

            editNum.setText(getNum);
            editName.setText(getName);
            editMsg.setText(getMessage);
//            editNum.addTextChangedListener(watcher);

        }
        else {
//            editNum.setText("0333");
//            editNum.addTextChangedListener(watcher);

        }


    }




    public int checkDb () {
        Cursor resultSet = mydatabase.rawQuery("select * from contact",null);
        if (resultSet.getCount() > 1){
            return 1;
        }
        else {
            return 0;
        }

    }

    public void testMsg (View view) {
        num = editNum.getText().toString();
        Toast.makeText(getApplication(), num,
                Toast.LENGTH_LONG).show();
        mydatabase.execSQL("DROP TABLE IF EXISTS " + "contact" + ";");
    }

    public void back (View view) {
        if (checkDb() == 1) {
            mydatabase.execSQL("UPDATE contact SET name = '"+editName.getText().toString()+"', number = '"+editNum.getText().toString()+"', message = '"+editMsg.getText().toString()+"' WHERE userId = 1 ;");
            numToFt = editNum.getText().toString();
            msgToFt = editMsg.getText().toString();

        }


        else {
            mydatabase.execSQL("INSERT INTO contact (name,number,message) VALUES( '" + editName.getText().toString() + "','"+ editNum.getText().toString() + "','"+ editMsg.getText().toString() +"');");
            numToFt = editNum.getText().toString();
            msgToFt = editMsg.getText().toString();
        }

        Intent i = new Intent(getApplicationContext(),Chooser.class);
        startActivity(i);
        //setContentView(R.layout.activity_chooser);

    }

}