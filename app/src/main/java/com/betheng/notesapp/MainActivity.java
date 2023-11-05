package com.betheng.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static CustomListAdapter arrayAdapter;
//    static ArrayAdapter arrayAdapter;
    static SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Notes");

        sharedPreferences = getApplicationContext().getSharedPreferences("com.betheng.notesapp", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if (set == null){
            notes.add("Example Note");
        } else {
            notes = new ArrayList(set);
        }

        arrayAdapter = new CustomListAdapter(this, notes);
//        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);

        ListView listView = findViewById(R.id.listView);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Hello","Hello");
                Intent intent = new Intent(getApplicationContext(), NotesEditorActivity.class);
                intent.putExtra("noteId", i);
                intent.putExtra("title", "Edit Note");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.add_note){
            Intent intent = new Intent(getApplicationContext(), NotesEditorActivity.class);
            intent.putExtra("title", "New Note");
            startActivity(intent);

            return true;
        }
        return false;
    }
}