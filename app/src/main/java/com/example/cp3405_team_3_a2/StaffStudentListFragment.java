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

public class StaffStudentListFragment extends Fragment {
    private HomeFragment.OnFragmentInteractionListener mListener;

    private DatabaseHelper databaseHelper;

    public StaffStudentListFragment() {
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

        View view = inflater.inflate(R.layout.fragment_staff_student_list, container, false);

        final ListView listView = view.findViewById(R.id.student_list);

        Cursor data = databaseHelper.getStudents();
        ArrayList<String> studentNameArray = new ArrayList<>();
        ArrayList<String> studentEmailArray = new ArrayList<>();

        while(data.moveToNext()){
            studentNameArray.add(data.getString(0));
            studentEmailArray.add(data.getString(1));
        }

        String[] nameArray = studentNameArray.toArray(new String[0]);
        final String[] emailArray = studentEmailArray.toArray(new String[0]);
        Context con =  getActivity();

        final StaffStudentListAdapter studentListAdapter = new StaffStudentListAdapter((MainActivity)con, nameArray);
        listView.setAdapter(studentListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int jobID = ((MainActivity) Objects.requireNonNull(getActivity())).getJobFocus();
                long unixTime = System.currentTimeMillis() / 1000L;
                String currentEmail = ((MainActivity) Objects.requireNonNull(getActivity())).getEmail();
                String studentEmail = emailArray[i];
                databaseHelper.insertRecommendation(studentEmail, currentEmail, jobID, unixTime);
                ((MainActivity) Objects.requireNonNull(getActivity())).changeFragment(9);

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
