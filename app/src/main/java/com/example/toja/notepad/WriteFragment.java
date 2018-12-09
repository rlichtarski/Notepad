package com.example.toja.notepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WriteFragment extends DialogFragment {

    private MainActivity mainActivity;
    private RecyclerViewAdapter recyclerViewAdapter;
    private DatabaseHelper databaseHelper;
    private TextView mDateTextView;
    private EditText mEditText;
    private String mCreatedDate;
    private FloatingActionButton mDiscardNoteFAB;
    private FloatingActionButton mSaveNoteFAB;
    private String mNote;

    public WriteFragment() {
        // Required empty public constructor
    }

    public static WriteFragment newInstance(String createdDate) {
        WriteFragment fragment = new WriteFragment();
        Bundle args = new Bundle();
        args.putString("createdDate", createdDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCreatedDate = getArguments().getString("createdDate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write,container,false);

        databaseHelper = new DatabaseHelper(getActivity());

        mainActivity = new MainActivity();
        recyclerViewAdapter = new RecyclerViewAdapter();
        mDiscardNoteFAB = rootView.findViewById(R.id.cancelMemoFAB);
        mSaveNoteFAB = rootView.findViewById(R.id.saveMemoFAB);
        mEditText = rootView.findViewById(R.id.editText);
        mDateTextView = rootView.findViewById(R.id.createdDateTextView);
        mDateTextView.setText(mCreatedDate);

        mDiscardNoteFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note = mEditText.getText().toString();

                if(!note.isEmpty()) {
                    showToast("Something is in EditText");
                    showAlertDialog(note);
                } else {
                    WriteFragment.this.dismiss();
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
                    showToast(mNote);
                    addNoteToDatabase(mNote);
                    WriteFragment.this.dismiss();
                }
            }
        });

        return rootView;
    }

    private void addNoteToDatabase(String note) {
        boolean isInserted = databaseHelper.insertData(note, mCreatedDate);

        if(isInserted) {
            Toast.makeText(getActivity(),"The Note is inserted into the database",Toast.LENGTH_SHORT).show();
        }
    }

    private void showToast(String note) {
        Toast.makeText(getActivity(), note, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();

                        addNoteToDatabase(note);

                        WriteFragment.this.dismiss();
                    }
                })
                .setNegativeButton(R.string.discard,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface,int i) {
                        WriteFragment.this.dismiss();
                    }
                });
        builder.create();
        builder.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("EditText", mEditText.getText().toString());
    }
}
