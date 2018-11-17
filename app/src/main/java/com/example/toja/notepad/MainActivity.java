package com.example.toja.notepad;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy, h:mm a");
                String formattedDate = simpleDateFormat.format(date);
                showToast(formattedDate);
                WriteFragment writeFragment = WriteFragment.newInstance(formattedDate);
                writeFragment.show(fragmentManager, "");
            }
        });
    }

    private void showToast(String formatted) {
        Toast.makeText(this, formatted, Toast.LENGTH_SHORT).show();
    }
}
