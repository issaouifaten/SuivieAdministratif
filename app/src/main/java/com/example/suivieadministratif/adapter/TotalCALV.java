package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suivieadministratif.R ;
import com.example.suivieadministratif.model.ChiffreAffaireParSociete;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.cardview.widget.CardView;


public class TotalCALV extends ArrayAdapter<ChiffreAffaireParSociete> {




    DecimalFormat formatter = new DecimalFormat("0.000");
    private final Activity activity;
    private final ArrayList<ChiffreAffaireParSociete> listCA;

    public TotalCALV(Activity activity, ArrayList<ChiffreAffaireParSociete> listCA) {
        super(activity, R.layout.item_tot_ca  , listCA);
        // TODO Auto-generated constructor stub
        this.activity     = activity;
        this.listCA = listCA;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView = inflater.inflate (R.layout.item_tot_ca  , null, true);

        ChiffreAffaireParSociete ca = listCA.get(position);

        CardView   card_soc = (CardView) rowView.findViewById(R.id.card_soc);
        TextView txt_nom_soc = (TextView) rowView.findViewById(R.id.txt_nom_societe);
        TextView txt_ca_soc = (TextView) rowView.findViewById(R.id.txt_ca_soc);

        txt_nom_soc.setText(ca.getNonSociete());
        txt_ca_soc.setText(formatter.format(ca.getChiffreAffaire())+ " Dt");


        if (position % 2 ==0 )  // paire
        {
            card_soc.setBackgroundColor(Color.parseColor("#F0F0F0"));
        }

        if( position  ==  (listCA.size()-1)  )
        {
            card_soc.setBackgroundColor(Color.parseColor("#EDFCF0F0"));
        }

        return rowView;

    }




}


