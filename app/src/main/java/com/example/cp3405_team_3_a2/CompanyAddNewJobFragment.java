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

public class CompanyAddNewJobFragment extends Fragment {
    private HomeFragment.OnFragmentInteractionListener mListener;

    private DatabaseHelper databaseHelper;

    public CompanyAddNewJobFragment() {
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

        View view = inflater.inflate(R.layout.fragment_company_add_new_job, container, false);
        Context con =  getActivity();

        final EditText jobTitle = view.findViewById(R.id.jobTitleEditText);
        final EditText location = view.findViewById(R.id.locationEditText);
        final EditText type = view.findViewById(R.id.typeEditText);
        final EditText salary = view.findViewById(R.id.salaryEditText);
        final EditText dateDue = view.findViewById(R.id.dateDueEditText);
        final EditText description = view.findViewById(R.id.descriptionEditText);

        final long unixTime = System.currentTimeMillis() / 1000L;

        Button createNewJob = view.findViewById(R.id.saveJobListButton);
        createNewJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save information to database
                databaseHelper.insertJob("google", jobTitle.getText().toString(),
                        description.getText().toString(), type.getText().toString(), salary.getText().toString(),
                        dateDue.getText().toString(), unixTime, location.getText().toString());

                jobTitle.setText("");
                location.setText("");
                type.setText("");
                salary.setText("");
                dateDue.setText("");
                description.setText("");
                ((MainActivity) Objects.requireNonNull(getActivity())).changeFragment(5);
            }
        });

        Button cancel = view.findViewById(R.id.cancelNewJobListButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobTitle.setText("");
                location.setText("");
                type.setText("");
                salary.setText("");
                dateDue.setText("");
                description.setText("");
                ((MainActivity) Objects.requireNonNull(getActivity())).changeFragment(5);
            }
        });

        return view;
    }

    public void updateListView(){
        String email = ((MainActivity) Objects.requireNonNull(getActivity())).getEmail();
        Cursor data = databaseHelper.getJobDetails(email, 2);
        ArrayList<String> jobNameArray = new ArrayList<>();
        ArrayList<String> recommendedByArray = new ArrayList<>();
        ArrayList<String> jobDescriptArray = new ArrayList<>();


        while(data.moveToNext()){
            jobNameArray.add(data.getString(1));
            recommendedByArray.add(data.getString(7));
            jobDescriptArray.add(data.getString(2));
        }

        String[] nameArray = jobNameArray.toArray(new String[0]);
        String[] infoArray = recommendedByArray.toArray(new String[0]);
        String[] jobDetailArray = jobDescriptArray.toArray(new String[0]);
        Context con =  getActivity();
        JobListAdapter jobListAdapter = new JobListAdapter((MainActivity)con, nameArray, infoArray, jobDetailArray);
        ListView listView = getView().findViewById(R.id.jobList);
        listView.setAdapter(jobListAdapter);
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
