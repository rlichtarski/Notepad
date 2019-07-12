package com.example.toja.notepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.toja.notepad.database.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditNoteFragment extends DialogFragment {
    private static final String ARG_POSITION_ID = "id";
    private static final String ARG_POSITION_NOTE = "note";
    private static final String ARG_POSITION_DATE = "date";

    private EditText mEditText;
    private FloatingActionButton mSaveMemoFAB, mCancelMemoFAB;
    private TextView mCreatedDateTextView;
    private String mNoteDate, mNoteInEditText;
    private int mId;

    public static EditNoteFragment newInstance(int id , String noteDate, String noteInEditText) {
        EditNoteFragment fragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION_ID, id);
        args.putString(ARG_POSITION_DATE, noteDate);
        args.putString(ARG_POSITION_NOTE, noteInEditText);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = getArguments().getInt(ARG_POSITION_ID);
        mNoteDate = getArguments().getString(ARG_POSITION_DATE);
        mNoteInEditText = getArguments().getString(ARG_POSITION_NOTE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write, container, false);

        mCreatedDateTextView = rootView.findViewById(R.id.createdDateTextView);
        mCreatedDateTextView.setText(mNoteDate);
        mEditText = rootView.findViewById(R.id.editText);
        mEditText.setText(mNoteInEditText);
        mSaveMemoFAB = rootView.findViewById(R.id.saveMemoFAB);
        mCancelMemoFAB = rootView.findViewById(R.id.cancelMemoFAB);

        mSaveMemoFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note = mEditText.getText().toString();
                if(note.equals(mNoteInEditText)) {
                    Toast.makeText(getActivity(),"The note is not changed",Toast.LENGTH_SHORT).show();
                } else {
                    Note newNote = new Note(note, mNoteDate);
                    newNote.setId(mId);
                    ((MainActivity)getActivity()).addEditNote(newNote, "update");
                }
                EditNoteFragment.this.dismiss();
            }
        });

        mCancelMemoFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEditText.getText().toString().equals(mNoteInEditText)) {   //if the user didn't do anything
                    EditNoteFragment.this.dismiss();
                } else {
                    AlertDialog.Builder builder;
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(getActivity());
                    }

                    builder.setMessage(R.string.save_your_changes)
                            .setPositiveButton(R.string.save,new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface,int i) {
                                    String note = mEditText.getText().toString();
                                    Note newNote = new Note(note, mNoteDate);
                                    ((MainActivity)getActivity()).addEditNote(newNote, "update");

                                    EditNoteFragment.this.dismiss();
                                }
                            })
                            .setNegativeButton(R.string.discard,new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface,int i) {
                                    EditNoteFragment.this.dismiss();
                                }
                            });
                    builder.show();
                }

            }
        });

        return rootView;
    }
}
