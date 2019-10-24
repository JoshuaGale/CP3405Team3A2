package com.example.cp3405_team_3_a2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class StudentProfileFragment extends Fragment {
    private HomeFragment.OnFragmentInteractionListener mListener;

    private DatabaseHelper databaseHelper;
    private boolean editable = false;

    public StudentProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(App.getContext());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        ImageView githubImage = view.findViewById(R.id.githubImage);
        ImageView linkedInImage = view.findViewById(R.id.linkedInImage);

        //set editText fields non-editable on creation
        final EditText nameText = view.findViewById(R.id.student_profile_name);
        nameText.setTag(nameText.getKeyListener());
        nameText.setKeyListener(null);

        final EditText jobInterestsText = view.findViewById(R.id.student_profile_job_interests);
        jobInterestsText.setTag(jobInterestsText.getKeyListener());
        jobInterestsText.setKeyListener(null);

        final EditText qualificationsText = view.findViewById(R.id.student_profile_qualifications);
        qualificationsText.setTag(qualificationsText.getKeyListener());
        qualificationsText.setKeyListener(null);

        final EditText academicHistoryText = view.findViewById(R.id.student_profile_academic_history);
        academicHistoryText.setTag(academicHistoryText.getKeyListener());
        academicHistoryText.setKeyListener(null);

        final EditText interestedLocationText = view.findViewById(R.id.student_profile_interested_location);
        interestedLocationText.setTag(interestedLocationText.getKeyListener());
        interestedLocationText.setKeyListener(null);

        //get original edittext background drawable
        final Drawable originalBackground = nameText.getBackground();

        final Button editButton = view.findViewById(R.id.editButton);
        final String email = ((MainActivity) Objects.requireNonNull(getActivity())).getEmail();
        final Cursor data = databaseHelper.getStudentProfile(email);
        data.moveToFirst();

        //set profile fields from database
        String fullName = data.getString(2);
        nameText.setText(fullName);
        final String gitHubLink = data.getString(6);
        final String linkedInLink = data.getString(7);
        jobInterestsText.setText(data.getString(3));
        qualificationsText.setText(data.getString(0));
        academicHistoryText.setText(data.getString(1));
        final CheckBox locationVisibilityCheckBox = view.findViewById(R.id.locationVisibilityCheckBox);

        locationVisibilityCheckBox.setEnabled(false);

        //check for the user's profile visibility
        if(data.getInt(data.getColumnIndex("LOCATION_VISIBILITY")) != 0){
            locationVisibilityCheckBox.setChecked(true);
            interestedLocationText.setText(data.getString(5));
        }
        else{
            locationVisibilityCheckBox.setChecked(false);
            interestedLocationText.setText("");
        }

        //create on click functionality for edit button
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(!editable){
                    editButton.setText("SAVE");
                    //set fields editable
                    Drawable drawable = getResources().getDrawable(R.drawable.edit_text_border);

                    nameText.setKeyListener((KeyListener) nameText.getTag());
                    nameText.setBackground(drawable);

                    qualificationsText.setKeyListener((KeyListener) qualificationsText.getTag());
                    qualificationsText.setBackground(drawable);

                    jobInterestsText.setKeyListener((KeyListener) jobInterestsText.getTag());
                    jobInterestsText.setBackground(drawable);

                    academicHistoryText.setKeyListener((KeyListener) academicHistoryText.getTag());
                    academicHistoryText.setBackground(drawable);

                    interestedLocationText.setKeyListener((KeyListener) interestedLocationText.getTag());
                    interestedLocationText.setBackground(drawable);

                    locationVisibilityCheckBox.setEnabled(true);

                    editable = true;
                }
                else{
                    editButton.setText("EDIT");
                    //set fields non-editable
                    nameText.setKeyListener(null);
                    nameText.setBackground(originalBackground);

                    qualificationsText.setKeyListener(null);
                    qualificationsText.setBackground(originalBackground);

                    jobInterestsText.setKeyListener(null);
                    jobInterestsText.setBackground(originalBackground);

                    academicHistoryText.setKeyListener(null);
                    academicHistoryText.setBackground(originalBackground);

                    interestedLocationText.setKeyListener(null);
                    interestedLocationText.setBackground(originalBackground);

                    databaseHelper.updateStudent(email, qualificationsText.getText().toString(),
                            academicHistoryText.getText().toString(), nameText.getText().toString(),
                            jobInterestsText.getText().toString(), locationVisibilityCheckBox.isChecked(),
                            interestedLocationText.getText().toString(), gitHubLink, linkedInLink);

                    locationVisibilityCheckBox.setEnabled(false);

                    editable = false;
                }
            }
        });

        //create on click functionality for external links
        githubImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openLink(gitHubLink);
            }
        });

        linkedInImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openLink(linkedInLink);
            }
        });

        locationVisibilityCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(locationVisibilityCheckBox.isChecked()){
                    interestedLocationText.setText(data.getString(5));
                    interestedLocationText.setEnabled(true);
                }
                else{
                    interestedLocationText.setText("");
                    interestedLocationText.setEnabled(false);
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

    private void openLink(String link){
        if(!link.startsWith("http://") && !link.startsWith("https://")){
            link += "https://" + link;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }
}

