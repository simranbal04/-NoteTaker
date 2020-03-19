package com.simrankaurbal.editor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.R.layout;
import android.R.menu.*;
import android.R.drawable;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static  ArrayAdapter arrayAdapter;

    // adding note menu here


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.addnote,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.addnote)
        {
            Intent intent = new Intent(getApplicationContext(),Note.class);
            startActivity(intent);
            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listview);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.simrankaurbal.editor", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);

        // if the set is null
        if (set == null)
        {
            notes.add("Testing");

        }
        else {
            notes = new ArrayList<>(set);
        }

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),Note.class);
                intent.putExtra("noteid",position);
                startActivity(intent);
            }
        });

        // for deleting the selected note
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // alert dialog for user to confirm about deleting the note
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are You Sure?")
                        .setMessage("Do You Want To Delete This Now?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notes.remove(position);
                        arrayAdapter.notifyDataSetChanged();

                        // shared preferences to delete data permanently -- same method could be used, one used in memorable places-- but here trying new method using strings
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.simrankaurbal.editor", Context.MODE_PRIVATE);
                        HashSet<String> set = new HashSet<>(MainActivity.notes); // string created from array list
                        sharedPreferences.edit().putStringSet("notes",set).apply(); // saving in shared preferences

                    }
                }).setNegativeButton("No",null)
                        .show();

                return true;
            }
        });
    }
}
