package com.example.cp3405_team_3_a2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StaffAndCompanyAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;

    //to store the list of job title
    private final String[] jobTitleArray;

    //to store the list of job descriptions
    private final String[] jobDescriptionArray;

    //to store the list of job employers
    private final String[] employerArray;


    public StaffAndCompanyAdapter(Activity context, String[] jobTitleParam, String[] fromCompanyParam, String[] jobDescriptParam){
        super(context, R.layout.staff_and_company_row, jobTitleParam);
        this.context=context;
        this.jobTitleArray = jobTitleParam;
        this.jobDescriptionArray = fromCompanyParam;
        this.employerArray = jobDescriptParam;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.staff_and_company_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = rowView.findViewById(R.id.text1);
        TextView recommendingCompanyTextField = rowView.findViewById(R.id.text2);
        TextView infoTextField = rowView.findViewById(R.id.text3);


        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(jobTitleArray[position]);
        recommendingCompanyTextField.setText(jobDescriptionArray[position]);
        infoTextField.setText(employerArray[position]);

        return rowView;

    }
}
