package com.example.sandhyagopchandani.voiceenabledflashcards;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import java.util.ArrayList;
import java.util.Locale;


import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandhyagopchandani.voiceenabledflashcards.db.TaskContract;
import com.example.sandhyagopchandani.voiceenabledflashcards.db.TaskDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private EditText noteText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //noteText = findViewById(R.id.note);
        mTaskListView = (ListView) findViewById(R.id.list_todo);
        mHelper = new TaskDbHelper(this);
        updateUI();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                //Log.d(TAG, "Add a new task");
              //  final EditText taskEditText = new EditText(this);
               // Context context = getApplicationContext();


               // LinearLayout layout = new LinearLayout(this);
               // layout.setOrientation(LinearLayout.VERTICAL);

                //final EditText taskEditText = new EditText(this);
               // taskEditText.setHint("Title");


              //  layout.addView(taskEditText);

               // final EditText descriptionBox = new EditText(this);
              //  descriptionBox.setHint("Description");
              //  layout.addView(descriptionBox);


                /*AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new word")
                        //.setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String word = String.valueOf(taskEditText.getText());
                               // String def = String.valueOf(descriptionBox.getText());
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_NOTE, word);
                               // values.put(TaskContract.TaskEntry.COL_DES,def);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();*/

                // custom dialog
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.custom_dialogue);
                dialog.setTitle("Title...");

                // set the custom dialog components - text, image and button
               // TextView text = (TextView) dialog.findViewById(R.id.textView);
               // text.setText("Android custom dialog example!");
               // ImageView image = (ImageView) dialog.findViewById(R.id.imageButton);
               // image.setImageResource(R.drawable.done);

                Button dialogButton = (Button) dialog.findViewById(R.id.button);
                 noteText = (EditText) dialog.findViewById(R.id.editText);
                ImageButton listenTome = (ImageButton) dialog.findViewById(R.id.imageButton);
                listenTome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                        askSpeechInput();
                    }
                });

                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String word = String.valueOf(noteText.getText());
                        // String def = String.valueOf(descriptionBox.getText());
                        SQLiteDatabase db = mHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(TaskContract.TaskEntry.COL_NOTE, word);
                        // values.put(TaskContract.TaskEntry.COL_DES,def);
                        db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                null,
                                values,
                                SQLiteDatabase.CONFLICT_REPLACE);
                        db.close();
                        updateUI();
                        dialog.dismiss();
                    }
                });

                dialog.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi Take your notes");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }
    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_NOTE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_NOTE);

            taskList.add(cursor.getString(idx));
           // taskList.add(cursor.getString(idxx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item_todo,
                    R.id.note,
                    taskList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.note);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_NOTE + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    noteText.setText(result.get(0));
                }
                break;
            }

        }
    }
}
