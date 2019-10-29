package com.example.cp3405_team_3_a2;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;


public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private DatabaseHelper databaseHelper;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(App.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        String email = ((MainActivity) Objects.requireNonNull(getActivity())).getEmail();
        int userType = databaseHelper.getUserType(email);
        Cursor data = databaseHelper.getUserRecommendations(email, userType);
        ArrayList<String> jobNameArray = new ArrayList<>();
        ArrayList<String> recommendationArray = new ArrayList<>();
        ArrayList<String> notificationTypeArray = new ArrayList<>();
        ArrayList<String> recommendedByArray = new ArrayList<>();

        switch(userType){
            case 0: //student
                while(data.moveToNext()){
                    jobNameArray.add("New Recommendation: " + data.getString(7));
                    recommendationArray.add("Recommended By: " + data.getString(2));
                    recommendedByArray.add("From: " + data.getString(6) );
                    notificationTypeArray.add("Job Location: " + data.getString(13));

                    data.close();
                }
                break;
            case 1: //staff
                while(data.moveToNext()){
                    jobNameArray.add(data.getString(1));
                    recommendationArray.add("");
                    notificationTypeArray.add(data.getString(3));
                    recommendedByArray.add("From: " + data.getString(6) );
                }
                data.close();
                break;
            case 2: //company
                while(data.moveToNext()){
                    jobNameArray.add(data.getString(1));
                    recommendationArray.add("");
                    notificationTypeArray.add(data.getString(3));
                    recommendedByArray.add("From: " + data.getString(6) );
                }
                data.close();
                break;
        }

        String[] nameArray = jobNameArray.toArray(new String[0]);
        String[] fromArray = recommendedByArray.toArray(new String[0]);
        String[] infoArray = recommendationArray.toArray(new String[0]);
        String[] typeArray = notificationTypeArray.toArray(new String[0]);
        Context con =  getActivity();
        StudentAdapter jobListAdapter = new StudentAdapter((MainActivity)con, nameArray, fromArray, infoArray, typeArray);
        final ListView listView = view.findViewById(R.id.itemList);
        listView.setAdapter(jobListAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String jobName = listView.getItemAtPosition(i).toString();
                jobName = jobName.substring(20);
                Cursor idData = databaseHelper.getJobID(jobName);
                idData.moveToFirst();
                ((MainActivity) Objects.requireNonNull(getActivity())).setJobFocus(idData.getInt(0));
                ((MainActivity) Objects.requireNonNull(getActivity())).changeFragment(10);
            }
        });

        // Inflate the layout for this fragment
        return view;


}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
