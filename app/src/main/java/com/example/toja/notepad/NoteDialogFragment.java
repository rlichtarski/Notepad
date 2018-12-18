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

import com.example.toja.notepad.database.DatabaseHelper;
import com.example.toja.notepad.database.model.Note;


public class NoteDialogFragment extends DialogFragment {
    private static final String ARG_POSITION = "position";
    private static final String ARG_NOTE = "note";
    private String mNote;
    private int mPosition;

    public NoteDialogFragment() {
        // Required empty public constructor
    }

    public static NoteDialogFragment newInstance(int position, String note) {
        NoteDialogFragment fragment = new NoteDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        args.putString(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(ARG_POSITION);
            mNote = getArguments().getString(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_dialog,container,false);

        final String dateStringQuery = "SELECT " + Note.COL_DATE + " FROM " + Note.TABLE_NAME + " WHERE " + Note.COL_ID + " = " + mPosition + ";";

        TextView note = rootView.findViewById(R.id.memo);
        note.setText(mNote);

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
                String query = getDateItem(dateStringQuery);
                openWriteFragment(query);
            }
        });

        return rootView;
    }

    private void openWriteFragment(String query) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        WriteFragment writeFragment = WriteFragment.newInstance(query);
        writeFragment.show(fragmentManager, "");
        NoteDialogFragment.this.dismiss();
    }

    private String getDateItem(String date) {
        String mDate = "";
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(date, null);
        if(cursor.getCount() == 1) {
            cursor.moveToFirst();
            mDate = cursor.getString(cursor.getColumnIndex(Note.COL_DATE));
        }
        cursor.close();
        return mDate;
    }
}
