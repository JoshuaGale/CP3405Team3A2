package com.example.cp3405_team_3_a2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    String email;
    DatabaseHelper databaseHelper;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listView = findViewById(R.id.itemList);
        ArrayList<ArrayList<String>> itemList;
        databaseHelper = new DatabaseHelper(App.getContext());



        //get user email from previous activity for data retrieval
        Intent intent = getIntent();
        email = intent.getStringExtra("userEmail");
        //itemList = databaseHelper.getUserRecommendations(email);
        populateListView();
//        //set adapter for the ListView and add on click functionality
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, boi);
//        listView.setAdapter(arrayAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
   }

    public void populateListView() {
        Cursor data = databaseHelper.getUserRecommendationsTrial(email);
        ArrayList<String> notificationData = new ArrayList<>();
        int i = 1;
        while(data.moveToNext()){
            notificationData.add(data.getString(1) + " " + data.getString(2));
            i++;
        }
        //listView.setAdapter(new NotificationAdapter(this, new String[] { "data1", "data2" }));
        Log.i("boi", notificationData.toString());
        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.row, notificationData);
        listView.setAdapter(adapter);
    }
}
