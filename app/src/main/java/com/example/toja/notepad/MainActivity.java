package com.example.toja.notepad;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toja.notepad.database.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private SQLiteDatabase mDatabase;
    private RecyclerView recyclerView;
    private TextView mEmptyView;

    private NoteViewModel noteViewModel;
    private List<Note> notesList;

    public MainActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyView = findViewById(R.id.emptyView);

        setRecyclerView();

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this,new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesList = notes;
                mRecyclerViewAdapter.setNotes(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder,RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,int direction) {
                noteViewModel.delete(mRecyclerViewAdapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWriteFragment();
            }
        });
    }

    public void addNote(Note note) {
        noteViewModel.insert(note);
    }


    private void setRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void showWriteFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy, h:mm a");
        String formattedDate = simpleDateFormat.format(date);
        WriteFragment writeFragment = WriteFragment.newInstance(formattedDate);
        writeFragment.show(fragmentManager,"");
    }

}
