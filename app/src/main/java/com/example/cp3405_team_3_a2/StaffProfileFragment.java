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
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class StaffProfileFragment extends Fragment {
    private HomeFragment.OnFragmentInteractionListener mListener;

    private boolean editable = false;
    private DatabaseHelper databaseHelper;

    public StaffProfileFragment() {
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

        View view = inflater.inflate(R.layout.fragment_staff_profile, container, false);

        final EditText nameText = view.findViewById(R.id.nameInputField);
        final EditText jobPositionText = view.findViewById(R.id.jobPositionInput);
        final EditText qualificationsText = view.findViewById(R.id.qualificationsInput);
        final EditText academicHistoryText = view.findViewById(R.id.academicHistoryInput);
        final Button editButton = view.findViewById(R.id.editButton);
        final String email = ((MainActivity) Objects.requireNonNull(getActivity())).getEmail();
        Cursor data = databaseHelper.getStaffProfile(email);
        data.moveToFirst();

        String fullName = data.getString(2);
        nameText.setText(fullName);
        jobPositionText.setText(data.getString(3));
        qualificationsText.setText(data.getString(0));
        academicHistoryText.setText(data.getString(1));

        editButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (!editable) {
                    nameText.setEnabled(true);
                    jobPositionText.setEnabled(true);
                    qualificationsText.setEnabled(true);
                    academicHistoryText.setEnabled(true);
                    editButton.setText("Save");
                    editable = true;
                }
                else {
                    nameText.setEnabled(false);
                    jobPositionText.setEnabled(false);
                    qualificationsText.setEnabled(false);
                    academicHistoryText.setEnabled(false);
                    editButton.setText("Edit");
                    databaseHelper.updateStaff(email, nameText.getText().toString(), jobPositionText.getText().toString(), qualificationsText.getText().toString(), academicHistoryText.getText().toString());
                    editable = false;

                }

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