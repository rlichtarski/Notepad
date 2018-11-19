package com.example.toja.notepad;

import android.os.Bundle;
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
                }
                WriteFragment.this.dismiss();
            }
        });

        mSaveNoteFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNote = mEditText.getText().toString();
                showToast(mNote);
            }
        });

        return rootView;
    }

    private void showToast(String note) {
        Toast.makeText(getActivity(), note, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("EditText", mEditText.getText().toString());
    }
}
