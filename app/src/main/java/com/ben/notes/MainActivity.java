package com.ben.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ben.notes.model.Notes;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    SharedPreferenceUtil sharedPreferencesUtil = new SharedPreferenceUtil();
    public static ArrayAdapter arrayAdapter;
    public static List<String> noteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        noteList.clear();
        Notes notes = sharedPreferencesUtil.get(this, "notes", Notes.class);
        noteList.addAll(notes.getNotes());
        if (noteList.isEmpty()) {
            noteList.add("Example Note");

        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, noteList);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("noteIndex", position);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete the note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                noteList.remove(position);
                                sharedPreferencesUtil.save(MainActivity.this, "notes", new Notes().setNotes(MainActivity.noteList));
                                MainActivity.arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.new_note:
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivity(intent);
        }
        return true;
    }
}
