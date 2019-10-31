package com.example.cp3405_team_3_a2;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

public class CompanyJobListingFragment extends Fragment {
    private HomeFragment.OnFragmentInteractionListener mListener;

    private DatabaseHelper databaseHelper;

    public CompanyJobListingFragment() {
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

        View view = inflater.inflate(R.layout.fragment_company_job_listing, container, false);

        String email = ((MainActivity) Objects.requireNonNull(getActivity())).getEmail();
        Cursor data = databaseHelper.getJobDetails(email, 2);
        ArrayList<String> jobNameArray = new ArrayList<>();
        ArrayList<String> recommendedByArray = new ArrayList<>();
        ArrayList<String> jobDescriptArray = new ArrayList<>();

        while(data.moveToNext()){
            jobNameArray.add("Job Title: " + data.getString(1));
            if(data.getString(2).length() > 50){
                recommendedByArray.add("Job Description: " + data.getString(2).substring(0, 90) + " ...");
            }
            else{
                recommendedByArray.add("Job Description: " + data.getString(2));
            }
            jobDescriptArray.add("Job Type: " + data.getString(3));
        }

        String[] nameArray = jobNameArray.toArray(new String[0]);
        String[] infoArray = recommendedByArray.toArray(new String[0]);
        String[] jobDetailArray = jobDescriptArray.toArray(new String[0]);
        Context con =  getActivity();
        StaffAndCompanyAdapter jobListAdapter = new StaffAndCompanyAdapter((MainActivity)con, nameArray, infoArray, jobDetailArray);
        final ListView listView = view.findViewById(R.id.jobList);
        listView.setAdapter(jobListAdapter);

        Button createNewJob = view.findViewById(R.id.newJobListButton);
        createNewJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) Objects.requireNonNull(getActivity())).changeFragment(8);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragment.OnFragmentInteractionListener) {
            mListener = (HomeFragment.OnFragmentInteractionListener) context;
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
