package com.example.cp3405_team_3_a2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    String email;
    DatabaseHelper databaseHelper;
    ListView listView;

    String[] typeArray;
    String[] nameArray;
    String[] infoArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listView = findViewById(R.id.itemList);
        databaseHelper = new DatabaseHelper(App.getContext());

        //get user email from previous activity for data retrieval
        Intent intent = getIntent();
        email = intent.getStringExtra("userEmail");
        populateStudentListView();
   }

    public void populateStudentListView() {
        Cursor data = databaseHelper.getUserRecommendations(email);
        ArrayList<String> jobNameArray = new ArrayList<>();
        ArrayList<String> recommendationArray = new ArrayList<>();
        ArrayList<String> notificationTypeArray = new ArrayList<>();

        while(data.moveToNext()){
            jobNameArray.add(data.getString(1));
            recommendationArray.add(data.getString(2));
            notificationTypeArray.add(data.getString(3));

        }

        nameArray = jobNameArray.toArray(new String[0]);
        infoArray = recommendationArray.toArray(new String[0]);
        typeArray = notificationTypeArray.toArray(new String[0]);

        NotificationAdapter notificationAdapter = new NotificationAdapter(this, nameArray, infoArray, typeArray);
        listView = findViewById(R.id.itemList);
        listView.setAdapter(notificationAdapter);
    }



}
