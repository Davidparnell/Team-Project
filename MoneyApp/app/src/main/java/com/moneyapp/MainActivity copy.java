package com.example.project.project;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.project.db.AppDatabase;
import com.example.project.project.db.NoteDAO;
import com.example.project.project.model.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    NoteDAO noteDAO = null;
    MyAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            //database
        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "note").allowMainThreadQueries().build();
        noteDAO = database.getNoteDAO();
            //adapter for gridview list
        adapter = new MyAdapter(this, noteDAO);
        final List<String> noteNames = adapter.getNoteNames();
            //enter name of note
        final EditText editText = findViewById(R.id.name);
            //list of clickable notes
        GridView gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                startEditActivity(noteNames.get(position));
            }
        });
            //insert new note
        Button newNoteButton = findViewById(R.id.newNote);
        newNoteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context context = getApplication();
                if(!editText.getText().toString().equals("") && !noteDAO.getNames().contains(editText.getText().toString())) {
                    Note note = new Note();
                    note.setName(editText.getText().toString());
                    noteDAO.insert(note);
                    startEditActivity(editText.getText().toString());
                        //update adapter list
                    adapter.getNoteNames().add(editText.getText().toString());
                    editText.setText("");
                }  //note name already taken
                else if(noteDAO.getNames().contains(editText.getText().toString())) {
                    Toast.makeText(context, "Already Exists!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context, "Can't Be Blank!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

	@Override
    public void onStart(){
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

	@Override
    protected void onResume() {
        super.onResume();  //update adapter list after delete
        adapter.setNoteNames(noteDAO.getNames());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

	@Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void startEditActivity(String name) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}
