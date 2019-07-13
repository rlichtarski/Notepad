package com.example.toja.notepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import com.example.toja.notepad.database.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddNoteFragment extends DialogFragment {

    private TextView mDateTextView;
    private EditText mEditText;
    private String mNoteDate;
    private FloatingActionButton mDiscardNoteFAB;
    private FloatingActionButton mSaveNoteFAB;
    private String mNote;

    public AddNoteFragment() {
        // Required empty public constructor
    }

    public static AddNoteFragment newInstance(String noteDate) {
        AddNoteFragment fragment = new AddNoteFragment();
        Bundle args = new Bundle();
        args.putString("noteDate", noteDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write,container,false);

        setCancelable(false);
        mEditText = rootView.findViewById(R.id.editText);
        mDiscardNoteFAB = rootView.findViewById(R.id.cancelMemoFAB);
        mSaveNoteFAB = rootView.findViewById(R.id.saveMemoFAB);
        mDateTextView = rootView.findViewById(R.id.createdDateTextView);
        mDateTextView.setText(mNoteDate);

        mDiscardNoteFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note = mEditText.getText().toString();

                if(!note.isEmpty()) {     //if you're trying to close the fragment, but the note is still not saved
                    showAlertDialog(note);
                } else {
                    AddNoteFragment.this.dismiss();
                }
            }
        });

        mSaveNoteFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNote = mEditText.getText().toString();
                if(mNote.trim().length() == 0) {
                    Toast.makeText(getActivity(),"The note is empty",Toast.LENGTH_SHORT).show();
                } else {
                    addNoteToDatabase(mNote, mNoteDate);
                    AddNoteFragment.this.dismiss();
                }
            }
        });

        return rootView;
    }

    private void addNoteToDatabase(String note, String date) {
        Note newNote = new Note(note, date);
        ((MainActivity)getActivity()).addEditNote(newNote, "insert");
    }

    private void showAlertDialog(final String note) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getActivity());
        }

        builder.setMessage(R.string.save_your_changes)
                .setPositiveButton(R.string.save,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface,int i) {
                        addNoteToDatabase(note, mNoteDate);

                        AddNoteFragment.this.dismiss();
                    }
                })
                .setNegativeButton(R.string.discard,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface,int i) {
                        AddNoteFragment.this.dismiss();
                    }
                });
        builder.create();
        builder.show();
    }
}
