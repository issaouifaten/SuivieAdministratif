package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.suivieadministratif.R;

import java.util.ArrayList;

public class SpinnerAnneMoisAdapter extends ArrayAdapter<String> {


    private final Activity activity;
    private final ArrayList<String> listSpinner;


    public SpinnerAnneMoisAdapter(Activity activity  , ArrayList<String> listSpinner) {
        super(activity, R.layout.spinner_item, listSpinner);
        // TODO Auto-generated constructor stub
        this.activity=activity;
        this.listSpinner=listSpinner;

    }


    @Override
    public boolean isEnabled(int position){
        if(position == 0)
        {
            // Disable the first item from Spinner
            // First item will be use for hint
            return false;
        }
        else
        {
            return true;
        }
    }


    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if(position == 0){
            // Set the hint text color gray
            tv.setTextColor(Color.GRAY);
        }
        else {
            tv.setTextColor(Color.BLACK);
        }
        return view;
    }

}
