package com.example.cp3405_team_3_a2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener {
    DatabaseHelper databaseHelper;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    HomeFragment homeFragment = new HomeFragment();
    StudentProfileFragment studentProfileFragment = new StudentProfileFragment();
    Fragment[] fragments = {homeFragment, studentProfileFragment};


    //home stuff
    String email;
    ListView listView;

    String[] typeArray;
    String[] nameArray;
    String[] infoArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.itemList);
        databaseHelper = new DatabaseHelper(App.getContext());

        BottomNavigationView bottomNavView = findViewById(R.id.nav_view);
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Log.i("boi", Integer.toString(menuItem.getItemId()));
                switch (menuItem.getItemId()){
                    case R.id.homeButton:
                        changeFragment(0);
                        break;

                    case R.id.jobButton:
                        changeFragment(2);
                        break;

                    case R.id.profileButton:
                        changeFragment(1);
                        break;

                }
                //changeFragment(menuItem.getItemId());
                return true;
            }
        });

        //get user email from previous activity for data retrieval
        Intent intent = getIntent();
        email = intent.getStringExtra("userEmail");
        //homeFragment = HomeFragment.newInstance(email);



        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, homeFragment, "HomeFragment");
        fragmentTransaction.commit();
    }

    public void changeFragment(int index){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_container, fragments[index]);
        fragmentTransaction.commit();
    }

    public String getEmail(){
        return email;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Log.i("boi", Integer.toString(item.getItemId()));
        changeFragment(item.getItemId());
        return true;
    }
}