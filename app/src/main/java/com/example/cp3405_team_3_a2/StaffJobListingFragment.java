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

public class StaffJobListingFragment extends Fragment {
    private HomeFragment.OnFragmentInteractionListener mListener;

    private DatabaseHelper databaseHelper;

    public StaffJobListingFragment() {
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

        View view = inflater.inflate(R.layout.fragment_staff_job_listing, container, false);

        final ListView listView = view.findViewById(R.id.jobList);
        Button newButton = view.findViewById(R.id.new_button);
        Button completedButton = view.findViewById(R.id.completed_button);


        String email = ((MainActivity) Objects.requireNonNull(getActivity())).getEmail();
        Cursor data = databaseHelper.getJobDetails(email, 3);
        ArrayList<String> jobNameArray = new ArrayList<>();
        ArrayList<String> jobDescriptionArray = new ArrayList<>();

        while(data.moveToNext()){
            jobNameArray.add(data.getString(1));
            jobDescriptionArray.add(data.getString(2));
        }

        String[] nameArray = jobNameArray.toArray(new String[0]);
        String[] infoArray = jobDescriptionArray.toArray(new String[0]);
        Context con =  getActivity();
        final StaffAdapter jobListAdapter = new StaffAdapter((MainActivity)con, nameArray, infoArray);
        listView.setAdapter(jobListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String jobName = listView.getItemAtPosition(i).toString();
                Cursor idData = databaseHelper.getJobID(jobName);
                idData.moveToFirst();
                ((MainActivity) Objects.requireNonNull(getActivity())).setJobFocus(idData.getInt(0));
                ((MainActivity) Objects.requireNonNull(getActivity())).changeFragment(7);

            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateListView(3);
            }
        });

        completedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateListView(1);
            }
        });
        return view;
    }

    public void updateListView (int listType){
        String email = ((MainActivity) Objects.requireNonNull(getActivity())).getEmail();
        Cursor data = databaseHelper.getJobDetails(email, listType);
        ArrayList<String> jobNameArray = new ArrayList<>();
        ArrayList<String> jobDescriptionArray = new ArrayList<>();

        while(data.moveToNext()){
            jobNameArray.add(data.getString(1));
            jobDescriptionArray.add(data.getString(2));
        }

        String[] nameArray = jobNameArray.toArray(new String[0]);
        String[] infoArray = jobDescriptionArray.toArray(new String[0]);
        Context con =  getActivity();
        final StaffAdapter jobListAdapter = new StaffAdapter((MainActivity)con, nameArray, infoArray);
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
