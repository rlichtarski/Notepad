package com.example.toja.notepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
    private NoteAdapter noteAdapter;
    private RecyclerView recyclerView;
    private TextView emptyView;
    private NoteViewModel noteViewModel;
    private List<Note> notesList;
    private boolean isRVVisible = false;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            Note note = notesList.get(position);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            NoteDialogFragment noteDialogFragment = NoteDialogFragment.newInstance(note.getId(), note.getNote(), note.getDate());
            noteDialogFragment.show(fragmentTransaction, "fragmentTransaction");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyView = findViewById(R.id.emptyView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);
        noteAdapter.setItemClickListener(onClickListener);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this,new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if(notes.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    isRVVisible = false;
                } else {
                    if(!isRVVisible) {
                        recyclerView.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                        isRVVisible = true;
                    }
                    notesList = notes;
                    noteAdapter.submitList(notes);
                }
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder,RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder,int direction) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(MainActivity.this);
                }

                builder.setMessage(R.string.delete_question)
                        .setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface,int i) {
                                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                            }
                        })
                        .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface,int i) {
                                noteAdapter.notifyItemChanged(viewHolder.getAdapterPosition());  //swipe back a note
                            }
                        });
                builder.create();
                builder.show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_all_notes) {
            if(!isRVVisible) {   //if recycler view is not visible it means there are no notes
                Toast.makeText(this,"There aren't any notes to delete",Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(MainActivity.this,android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(MainActivity.this);
                }

                builder.setMessage(R.string.delete_question)
                        .setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface,int i) {
                                noteViewModel.deleteAllNotes();
                                Toast.makeText(MainActivity.this,"Deleted all notes",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface,int i) {
                            }
                        });
                builder.create();
                builder.show();
            }
        }
        return true;
    }

    public void addEditNote(Note note,String methodType) {
        switch (methodType) {
            case "insert":
                noteViewModel.insert(note);
                break;
            case "update":
                noteViewModel.update(note);
                break;
        }
    }

    private void showWriteFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy, h:mm a");
        String formattedDate = simpleDateFormat.format(date);
        AddNoteFragment addNoteFragment = AddNoteFragment.newInstance(formattedDate);
        addNoteFragment.show(fragmentManager,"");
    }

}
