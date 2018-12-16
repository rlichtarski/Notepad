package com.example.toja.notepad;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


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

        TextView note = rootView.findViewById(R.id.memo);
        note.setText(mNote);

        return rootView;
    }
}
