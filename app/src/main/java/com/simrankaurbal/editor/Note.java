package com.simrankaurbal.editor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;

public class Note extends AppCompatActivity {

    int noteid;

    @Override
    protected void onCreate(Bundle savedInstatnceState)
    {
        super.onCreate(savedInstatnceState);
        setContentView(R.layout.edit1);

        EditText editText = (EditText) findViewById(R.id.editText);
        // retrieve data
        Intent intent = getIntent();
        // int created for getting data nd default value given if invalid value is passed
        noteid = intent.getIntExtra("noteid",-1);

        if (noteid != -1)
        {
            editText.setText(MainActivity.notes.get(noteid));

        }
        else {

            MainActivity.notes.add(""); // empty note as initally it will empty
            // note id to access it
            noteid = MainActivity.notes.size() -1;
            MainActivity.arrayAdapter.notifyDataSetChanged(); // update the array adapter to display in the listview

        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // to update note from main activity
                MainActivity.notes.set(noteid,String.valueOf(s));
                 // update listview
                MainActivity.arrayAdapter.notifyDataSetChanged();

                // as here we are saving new entry so we have used permanent storage
                // shared preferences to save data permanently -- same method could be used, one used in memorable places-- but here trying new method using strings
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.simrankaurbal.editor", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.notes); // string created from array list
                sharedPreferences.edit().putStringSet("notes",set).apply(); // saving in shared preferences



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
