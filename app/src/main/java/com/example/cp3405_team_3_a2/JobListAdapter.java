package com.example.cp3405_team_3_a2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class JobListAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;

    //to store the list of job title
    private final String[] jobNameArray;

    //to store the list of staff who recommended the job
    private final String[] recommendedByArray;

    //to store the list of job details/descriptions
    private final String[] jobDetailArray;


    public JobListAdapter(Activity context, String[] jobNameParam, String[] recommendedByParam, String[] jobDescriptParam){

        super(context,R.layout.row , jobNameParam);
        this.context=context;
        this.jobNameArray = jobNameParam;
        this.recommendedByArray = recommendedByParam;
        this.jobDetailArray = jobDescriptParam;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = rowView.findViewById(R.id.text1);
        TextView infoTextField = rowView.findViewById(R.id.text2);
        TextView recommendedTextField = rowView.findViewById(R.id.text3);



        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(jobNameArray[position]);
        infoTextField.setText(jobDetailArray[position]);
        recommendedTextField.setText(recommendedByArray[position]);

        return rowView;

    };

}
