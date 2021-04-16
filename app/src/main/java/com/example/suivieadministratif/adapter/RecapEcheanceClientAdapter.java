package com.example.suivieadministratif.adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.RecapEcheancierClient;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


/**
 **  Created by fatima on 20/01/2017.
 **/
public class RecapEcheanceClientAdapter extends ArrayAdapter<RecapEcheancierClient> {


    NumberFormat formatter = new DecimalFormat("0.000");

    private final Activity activity;
    private final ArrayList<RecapEcheancierClient> listRecapEcheancierClient;

    public RecapEcheanceClientAdapter(Activity activity  , ArrayList<RecapEcheancierClient> listRecapEcheancierClient ) {
        super(activity, R.layout.item_recap_echeancier , listRecapEcheancierClient);

        this.activity=activity;
        this.listRecapEcheancierClient = listRecapEcheancierClient ;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView=inflater.inflate(R.layout.item_recap_echeancier, null, true);

        CardView backgroundItem =  (CardView)   rowView.findViewById(R.id.card_prevision);


        TextView txt_libelle_mois = (TextView)   rowView.findViewById(R.id.txt_libelle_mois);
        TextView txt_mnt_echeance = (TextView)   rowView.findViewById(R.id.txt_mnt_echeance);



        RecapEcheancierClient  recapEchClient  = listRecapEcheancierClient.get(position)  ;


        txt_libelle_mois.setText(recapEchClient.getLibelleMois()+ " "+recapEchClient.getAnnee());

        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);

        txt_mnt_echeance.setText(instance.format(recapEchClient.getMontant()));



        if (position % 2 ==0 ) // paire
        {
            backgroundItem.setBackgroundColor(Color.parseColor("#F0F0F0"));
        }



        return rowView;




}}
