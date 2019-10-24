package com.example.cp3405_team_3_a2;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class CompanyProfileFragment extends Fragment {
    private HomeFragment.OnFragmentInteractionListener mListener;

    private DatabaseHelper databaseHelper;
    boolean editable = false;

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

        //set editText fields non-editable on creation
        final EditText nameText = view.findViewById(R.id.companyNameInput);
        nameText.setTag(nameText.getKeyListener());
        nameText.setKeyListener(null);

        final EditText descriptionText = view.findViewById(R.id.companyDescriptionInput);
        descriptionText.setTag(descriptionText.getKeyListener());
        descriptionText.setKeyListener(null);

        final Button editButton = view.findViewById(R.id.editButton);

        //get original edittext background drawable
        final Drawable originalBackground = nameText.getBackground();

        final String email = ((MainActivity) Objects.requireNonNull(getActivity())).getEmail();
        Cursor data = databaseHelper.getCompanyProfile(email);
        data.moveToFirst();

        nameText.setText(data.getString(0));
        descriptionText.setText(data.getString(1));

        editButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (!editable) {
                    Drawable drawable = getResources().getDrawable(R.drawable.edit_text_border);
                    editButton.setText("SAVE");

                    //set fields editable
                    nameText.setKeyListener((KeyListener) nameText.getTag());
                    nameText.setBackground(drawable);

                    descriptionText.setKeyListener((KeyListener) descriptionText.getTag());
                    descriptionText.setBackground(drawable);

                    editable = true;
                }
                else {
                    editButton.setText("EDIT");
                    //set fields non-editable
                    nameText.setKeyListener(null);
                    nameText.setBackground(originalBackground);

                    descriptionText.setKeyListener(null);
                    descriptionText.setBackground(originalBackground);

                    databaseHelper.updateCompany(email, nameText.getText().toString(), descriptionText.getText().toString());

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
