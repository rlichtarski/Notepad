package com.example.toja.notepad;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class WriteFragment extends DialogFragment {

    private EditText mEditText;

    public WriteFragment() {
        // Required empty public constructor
    }

    public static WriteFragment newInstance() {
        WriteFragment fragment = new WriteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if(savedInstanceState == null) mEditTextValue = "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write,container,false);

        mEditText = rootView.findViewById(R.id.editText);

        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("EditText", mEditText.getText().toString());
    }
}
