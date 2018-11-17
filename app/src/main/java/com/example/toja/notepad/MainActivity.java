package com.example.toja.notepad;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                WriteFragment writeFragment = WriteFragment.newInstance("", "");
                writeFragment.show(fragmentManager, "");
            }
        });
    }
}
