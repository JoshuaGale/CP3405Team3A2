package com.example.cp3405_team_3_a2;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

public class StudentJobListingFragment extends Fragment {
    private HomeFragment.OnFragmentInteractionListener mListener;

    private DatabaseHelper databaseHelper;

    public StudentJobListingFragment() {
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

        View view = inflater.inflate(R.layout.fragment_student_job_listing, container, false);


        String email = ((MainActivity) Objects.requireNonNull(getActivity())).getEmail();
        Cursor data = databaseHelper.getJobDetails(email, 0);
        ArrayList<String> jobNameArray = new ArrayList<>();
        ArrayList<String> recommendedByArray = new ArrayList<>();
        ArrayList<String> jobDescriptArray = new ArrayList<>();

        while(data.moveToNext()){
            jobNameArray.add("Job Title: " + data.getString(1));
            recommendedByArray.add("Position Salary: " + data.getString(4));
            jobDescriptArray.add(" at " + data.getString(0) + "\n" + "Job Description: " + data.getString(2));
        }

        String[] nameArray = jobNameArray.toArray(new String[0]);
        String[] infoArray = recommendedByArray.toArray(new String[0]);
        String[] jobDetailArray = jobDescriptArray.toArray(new String[0]);
        Context con =  getActivity();
        JobListAdapter jobListAdapter = new JobListAdapter((MainActivity)con, nameArray, infoArray, jobDetailArray);
        final ListView listView = view.findViewById(R.id.jobList);
        listView.setAdapter(jobListAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String jobName = listView.getItemAtPosition(i).toString();
                jobName = jobName.substring(11);
                Cursor idData = databaseHelper.getJobID(jobName);
                idData.moveToFirst();
                ((MainActivity) Objects.requireNonNull(getActivity())).setJobFocus(idData.getInt(0));
                ((MainActivity) Objects.requireNonNull(getActivity())).changeFragment(10);
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
