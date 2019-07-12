package com.example.toja.notepad;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.toja.notepad.database.model.Note;

public class NoteDialogFragment extends DialogFragment {
    private static final String ARG_POSITION_NOTE = "note";
    private static final String ARG_POSITION_DATE = "date";
    private String mNote;
    private String mDate;
    private long mId;

    public NoteDialogFragment() {
        // Required empty public constructor
    }

    public static NoteDialogFragment newInstance(String note, String date) {
        NoteDialogFragment fragment = new NoteDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_POSITION_NOTE, note);
        args.putString(ARG_POSITION_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNote = getArguments().getString(ARG_POSITION_NOTE);
            mDate = getArguments().getString(ARG_POSITION_DATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_dialog,container,false);

        TextView noteTextView = rootView.findViewById(R.id.memo);
        noteTextView.setText(mNote);         //the note to show

        TextView dateTextView = rootView.findViewById(R.id.dateTextView);
        dateTextView.setText(mDate);

        ImageView mCloseNoteDialogBtn = rootView.findViewById(R.id.closeNoteDialogBtn);
        mCloseNoteDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteDialogFragment.this.dismiss();
            }
        });

        ImageView mEditNoteBtn = rootView.findViewById(R.id.editNoteBtn);
        mEditNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditNoteFragment();
            }
        });

        return rootView;
    }

    private void openEditNoteFragment() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        EditNoteFragment editNoteFragment = EditNoteFragment.newInstance(mDate, mNote, mId);
        editNoteFragment.show(fragmentManager, "");
        NoteDialogFragment.this.dismiss();
    }
}
