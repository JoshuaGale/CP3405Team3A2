package com.example.cp3405_team_3_a2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(App.getContext());
        databaseHelper.insertStudent("joshua.gale@my.jcu.edu.au", "yeaboi",
                0, "nothing", "zilch", "joshua",
                "gale", "nada", false, "nowhere");

    }
}
