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
import android.widget.TextView;
import java.util.Objects;


public class ViewJobDetailsFragment extends Fragment {
    private HomeFragment.OnFragmentInteractionListener mListener;
    private DatabaseHelper databaseHelper;

    public ViewJobDetailsFragment() {
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

        View view = inflater.inflate(R.layout.fragment_view_job_details, container, false);

        int jobID = ((MainActivity) Objects.requireNonNull(getActivity())).getJobFocus();
        Cursor data = databaseHelper.getJobDetailsView(jobID);
        data.moveToFirst();
        TextView jobTitleTextView = view.findViewById(R.id.editText3);
        jobTitleTextView.setText("");
        String jobTitle = data.getString(1);
        jobTitleTextView.setText(jobTitle);
        TextView jobLocationTextView = view.findViewById(R.id.editText4);
        jobLocationTextView.setText(data.getString(7));
        TextView jobTypeTextView = view.findViewById(R.id.editText5);
        jobTypeTextView.setText(data.getString(3));
        TextView jobSalaryTextView = view.findViewById(R.id.editText6);
        jobSalaryTextView.setText(data.getString(4));
        TextView jobDueDateTextView = view.findViewById(R.id.editText7);
        jobDueDateTextView.setText(data.getString(5));
        TextView jobDescriptionTextView = view.findViewById(R.id.editText8);
        jobDescriptionTextView.setText(data.getString(2));

        data.close();
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

    @Override
    public void onResume(){
        super.onResume();
        int jobID = ((MainActivity) Objects.requireNonNull(getActivity())).getJobFocus();
        Cursor data = databaseHelper.getJobDetailsView(jobID);
        data.moveToFirst();
        TextView jobTitleTextView = getView().findViewById(R.id.editText3);
        jobTitleTextView.setText("");
        String jobTitle = data.getString(1);
        jobTitleTextView.setText(jobTitle);
        TextView jobLocationTextView = getView().findViewById(R.id.editText4);
        jobLocationTextView.setText(data.getString(7));
        TextView jobTypeTextView = getView().findViewById(R.id.editText5);
        jobTypeTextView.setText(data.getString(3));
        TextView jobSalaryTextView = getView().findViewById(R.id.editText6);
        jobSalaryTextView.setText(data.getString(4));
        TextView jobDueDateTextView = getView().findViewById(R.id.editText7);
        jobDueDateTextView.setText(data.getString(5));
        TextView jobDescriptionTextView = getView().findViewById(R.id.editText8);
        jobDescriptionTextView.setText(data.getString(2));

        data.close();
    }
}
