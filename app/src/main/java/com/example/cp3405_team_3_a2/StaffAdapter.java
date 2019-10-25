package com.example.cp3405_team_3_a2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StaffAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;

    //to store the list of countries
    private final String[] typeArray;

    //to store the list of countries
    private final String[] nameArray;


    public StaffAdapter(Activity context, String[] nameArrayParam, String[] typeArrayParam){

        super(context,R.layout.row , nameArrayParam);
        this.context=context;
        this.typeArray = typeArrayParam;
        this.nameArray = nameArrayParam;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = rowView.findViewById(R.id.text1);
        TextView typeTextField = rowView.findViewById(R.id.text2);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        typeTextField.setText(typeArray[position]);

        return rowView;

    };
}
