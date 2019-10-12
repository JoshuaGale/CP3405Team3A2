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

        //set editText fields non-editable on creation
        final EditText nameText = view.findViewById(R.id.nameInputField);
        nameText.setTag(nameText.getKeyListener());
        nameText.setKeyListener(null);

        final EditText jobPositionText = view.findViewById(R.id.jobPositionInput);
        jobPositionText.setTag(jobPositionText.getKeyListener());
        jobPositionText.setKeyListener(null);

        final EditText qualificationsText = view.findViewById(R.id.qualificationsInput);
        qualificationsText.setTag(qualificationsText.getKeyListener());
        qualificationsText.setKeyListener(null);

        final EditText academicHistoryText = view.findViewById(R.id.academicHistoryInput);
        academicHistoryText.setTag(academicHistoryText.getKeyListener());
        academicHistoryText.setKeyListener(null);

        final Button editButton = view.findViewById(R.id.editButton);
        final String email = ((MainActivity) Objects.requireNonNull(getActivity())).getEmail();
        Cursor data = databaseHelper.getStaffProfile(email);
        data.moveToFirst();

        //get original edittext background drawable
        final Drawable originalBackground = nameText.getBackground();

        //set profile fields from the database
        String fullName = data.getString(2);
        nameText.setText(fullName);
        jobPositionText.setText(data.getString(3));
        qualificationsText.setText(data.getString(0));
        academicHistoryText.setText(data.getString(1));

        editButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (!editable) {
                    //get drawable from resource files
                    Drawable drawable = getResources().getDrawable(android.R.drawable.editbox_background);
                    editButton.setText("SAVE");

                    //set fields editable
                    nameText.setKeyListener((KeyListener) nameText.getTag());
                    nameText.setBackground(drawable);

                    jobPositionText.setKeyListener((KeyListener) jobPositionText.getTag());
                    jobPositionText.setBackground(drawable);

                    qualificationsText.setKeyListener((KeyListener) qualificationsText.getTag());
                    qualificationsText.setBackground(drawable);

                    academicHistoryText.setKeyListener((KeyListener) academicHistoryText.getTag());
                    academicHistoryText.setBackground(drawable);

                    editable = true;
                }
                else {
                    editButton.setText("EDIT");
                    //set fields non-editable
                    nameText.setKeyListener(null);
                    nameText.setBackground(originalBackground);

                    jobPositionText.setKeyListener(null);
                    jobPositionText.setBackground(originalBackground);

                    qualificationsText.setKeyListener(null);
                    qualificationsText.setBackground(originalBackground);

                    academicHistoryText.setKeyListener(null);
                    academicHistoryText.setBackground(originalBackground);

                    academicHistoryText.setKeyListener(null);
                    academicHistoryText.setBackground(originalBackground);
                    databaseHelper.updateStaff(email, nameText.getText().toString(),
                            jobPositionText.getText().toString(), qualificationsText.getText().toString(),
                            academicHistoryText.getText().toString());

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
