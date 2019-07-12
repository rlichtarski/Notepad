package com.example.toja.notepad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class NoteDialogFragment extends DialogFragment {
    private static final String ARG_POSITION_ID = "id";
    private static final String ARG_POSITION_NOTE = "note";
    private static final String ARG_POSITION_DATE = "date";
    private int mId;
    private String mNote;
    private String mDate;

    public static NoteDialogFragment newInstance(int id, String note, String date) {
        NoteDialogFragment fragment = new NoteDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION_ID, id);
        args.putString(ARG_POSITION_NOTE, note);
        args.putString(ARG_POSITION_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getInt(ARG_POSITION_ID);
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
        EditNoteFragment editNoteFragment = EditNoteFragment.newInstance(mId, mDate, mNote);
        editNoteFragment.show(fragmentManager, "");
        NoteDialogFragment.this.dismiss();
    }
}
