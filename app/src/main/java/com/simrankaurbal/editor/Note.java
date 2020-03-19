package com.simrankaurbal.editor;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

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

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
