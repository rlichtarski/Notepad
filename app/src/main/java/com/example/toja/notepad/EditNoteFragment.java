package com.example.toja.notepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toja.notepad.database.DatabaseHelper;

public class EditNoteFragment extends DialogFragment {

    private DatabaseHelper databaseHelper;
    private EditText mEditText;
    private FloatingActionButton mSaveMemoFAB, mCancelMemoFAB;
    private TextView mCreatedDateTextView;
    private String mNoteDate, mNoteInEditText;
    private int mPosition;

    public static EditNoteFragment newInstance(String noteDate, String noteInEditText, int position) {
        EditNoteFragment fragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putString("noteDate", noteDate);
        args.putString("noteInEditText", noteInEditText);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNoteDate = getArguments().getString("noteDate");
        mNoteInEditText = getArguments().getString("noteInEditText");
        mPosition = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write, container, false);

        databaseHelper = new DatabaseHelper(getActivity());

        mCreatedDateTextView = rootView.findViewById(R.id.createdDateTextView);
        mCreatedDateTextView.setText(mNoteDate);
        mEditText = rootView.findViewById(R.id.editText);
        mEditText.setText(mNoteInEditText);
        mSaveMemoFAB = rootView.findViewById(R.id.saveMemoFAB);
        mCancelMemoFAB = rootView.findViewById(R.id.cancelMemoFAB);

        mSaveMemoFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newNote = mEditText.getText().toString();
                if(newNote.equals(mNoteInEditText)) {
                    Toast.makeText(getActivity(),"The note is not changed",Toast.LENGTH_SHORT).show();
                    EditNoteFragment.this.dismiss();
                } else {
                    boolean isUpdated = databaseHelper.updateData(mPosition,newNote);
                    if (isUpdated) {
                        Toast.makeText(getActivity(),"Note Updated",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(),"Note is not updated",Toast.LENGTH_SHORT).show();
                    }
                    ((MainActivity) getActivity()).swap();
                    EditNoteFragment.this.dismiss();
                }
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
                                    databaseHelper.updateData(mPosition, note);
                                    ((MainActivity) getActivity()).swap();
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
