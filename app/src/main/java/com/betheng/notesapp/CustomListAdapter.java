package com.betheng.notesapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;

public class CustomListAdapter extends BaseAdapter implements ListAdapter {

    ArrayList<String> objects;
    Context context;

    public CustomListAdapter(Context context, ArrayList<String> objects) {
        this.objects = objects;
        this.context = context;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.notesTextView);
        textView.setText(objects.get(position));

        Button button = convertView.findViewById(R.id.deleteButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemToDelete = position;

                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                objects.remove(itemToDelete);
                                notifyDataSetChanged();

                                HashSet<String> set = new HashSet<>(objects);
                                MainActivity.sharedPreferences.edit().putStringSet("notes", set).apply();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        return convertView;
    }
}
