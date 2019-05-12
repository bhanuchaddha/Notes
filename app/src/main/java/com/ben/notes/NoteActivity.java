package com.ben.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.ben.notes.model.Notes;

public class NoteActivity extends AppCompatActivity {

    private String note;
    private int index;
    SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        EditText noteView = findViewById(R.id.note);

        Intent intent = getIntent();
        index = intent.getIntExtra("noteIndex", -1);
        if(index == -1){ // new note
            MainActivity.noteList.add("");
            index = MainActivity.noteList.size() -1 ;// position of latest note
        }
        note = MainActivity.noteList.get(index);
        noteView.setText(note);

        //Set edit listener
        noteView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.noteList.set(index, s.toString());
                sharedPreferenceUtil.save(NoteActivity.this, "notes", new Notes().setNotes(MainActivity.noteList));
                MainActivity.arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
