package com.example.cp3405_team_3_a2;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Objects;

public class CompanyProfileFragment extends Fragment {
    private HomeFragment.OnFragmentInteractionListener mListener;

    private DatabaseHelper databaseHelper;

    public CompanyProfileFragment() {
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

        View view = inflater.inflate(R.layout.fragment_company_profile, container, false);

        EditText nameText = view.findViewById(R.id.companyNameInput);
        EditText descriptionText = view.findViewById(R.id.companyDescriptionInput);

        String email = ((MainActivity) Objects.requireNonNull(getActivity())).getEmail();
        Cursor data = databaseHelper.getCompanyProfile(email);
        data.moveToFirst();

        nameText.setText(data.getString(0));
        descriptionText.setText(data.getString(1));

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
