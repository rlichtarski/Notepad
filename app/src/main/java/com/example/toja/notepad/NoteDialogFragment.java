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
    private static final String ARG_POSITION = "position";
    private int mPosition;
    private String mNote;
    private String mDate;

    public NoteDialogFragment() {
        // Required empty public constructor
    }

    public static NoteDialogFragment newInstance(int position) {
        NoteDialogFragment fragment = new NoteDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_dialog,container,false);

        final String stringQuery = "SELECT " + Note.COL_DATE + ", " + Note.COL_NOTE
                + " FROM " + Note.TABLE_NAME
                + " WHERE " + Note.COL_ID + " = " + mPosition + ";";

        Cursor cursor = getCursor(stringQuery);

        if(cursor.getCount() == 1) {
            cursor.moveToFirst();
            mDate = cursor.getString(cursor.getColumnIndex(Note.COL_DATE));
            mNote = cursor.getString(cursor.getColumnIndex(Note.COL_NOTE));
        }
        cursor.close();

        TextView dateTextView = rootView.findViewById(R.id.dateTextView);
        dateTextView.setText(mDate);

        TextView noteTextView = rootView.findViewById(R.id.memo);
        noteTextView.setText(mNote);

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
        EditNoteFragment editNoteFragment = EditNoteFragment.newInstance(mDate, mNote, mPosition);
        editNoteFragment.show(fragmentManager, "");
        NoteDialogFragment.this.dismiss();
    }

    private Cursor getCursor(String query) {
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        return sqLiteDatabase.rawQuery(query, null);
    }
}
