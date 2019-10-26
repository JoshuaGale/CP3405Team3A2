package com.example.cp3405_team_3_a2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StaffStudentListAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;


    //to store the list of names
    private final String[] nameArray;


    public StaffStudentListAdapter(Activity context, String[] nameArrayParam){

        super(context,R.layout.student_row , nameArrayParam);
        this.context=context;
        this.nameArray = nameArrayParam;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.student_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = rowView.findViewById(R.id.text1);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);

        return rowView;

    };
}
