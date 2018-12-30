package com.example.toja.notepad;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toja.notepad.database.DatabaseHelper;
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

        String noteToQuery = mNote;
        if(noteToQuery.contains("'")) {        //if a string contains ' in query, it need to be doubled, i.e can''t instead of can't
            noteToQuery = noteToQuery.replace("'", "''");   //so it replaces ' with ''
        }

        final String stringQuery = "SELECT " + Note.COL_ID
                + " FROM " + Note.TABLE_NAME
                + " WHERE " + Note.COL_NOTE + " = '" + noteToQuery + "';";   //find the id where the note exists

        Cursor cursor = getCursor(stringQuery);

        if(cursor.getCount() == 1) {
            cursor.moveToFirst();
            mId = cursor.getLong(cursor.getColumnIndex(Note.COL_ID));      //id needed to update the note
        }
        cursor.close();

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

    private Cursor getCursor(String query) {
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        return sqLiteDatabase.rawQuery(query, null);
    }
}
