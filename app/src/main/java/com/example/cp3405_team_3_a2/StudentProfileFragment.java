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
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

public class StudentProfileFragment extends Fragment {
    private HomeFragment.OnFragmentInteractionListener mListener;

    private DatabaseHelper databaseHelper;

    public StudentProfileFragment() {
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

        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        EditText nameText = view.findViewById(R.id.student_profile_name);
        EditText linkText = view.findViewById(R.id.student_profile_links);
        EditText jobInterestsText = view.findViewById(R.id.student_profile_job_interests);
        EditText qualificationsText = view.findViewById(R.id.student_profile_qualifications);
        EditText academicHistoryText = view.findViewById(R.id.student_profile_academic_history);
        EditText interestedLocationText = view.findViewById(R.id.student_profile_interested_location);
        String email = ((MainActivity) Objects.requireNonNull(getActivity())).getEmail();
        Cursor data = databaseHelper.getStudentProfile(email);
        data.moveToFirst();

        String fullName = data.getString(2) + " " + data.getString(3);
        nameText.setText(fullName);
        String links = data.getString(7) + " " + data.getString(8);
        linkText.setText(links);
        jobInterestsText.setText(data.getString(4));
        qualificationsText.setText(data.getString(0));
        academicHistoryText.setText(data.getString(1));
        interestedLocationText.setText(data.getString(6));


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

