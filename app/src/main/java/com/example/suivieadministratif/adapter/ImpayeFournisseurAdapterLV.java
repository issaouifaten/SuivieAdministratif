package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.ImpayeClient;
import com.example.suivieadministratif.model.ImpayeFournisseur;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ImpayeFournisseurAdapterLV extends ArrayAdapter<ImpayeFournisseur> {


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    DecimalFormat formatter = new DecimalFormat("0.000");
    private final Activity activity;
    private final ArrayList<ImpayeFournisseur> listImpayeFournisseur;

    public ImpayeFournisseurAdapterLV(Activity activity, ArrayList<ImpayeFournisseur> listImpayeFournisseur) {
        super(activity, R.layout.item_impaye_fournisseur, listImpayeFournisseur);
        // TODO Auto-generated constructor stub
        this.activity     = activity;
        this.listImpayeFournisseur = listImpayeFournisseur;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView = inflater.inflate (R.layout.item_impaye_fournisseur  , null, true);

        ImpayeFournisseur  imp_Frns = listImpayeFournisseur.get(position);

        TextView txt_raison_sociale= (TextView) rowView.findViewById(R.id.txt_raison);
        TextView txt_date_impaye = (TextView) rowView.findViewById(R.id.txt_date_impaye);

        TextView txt_num_impaye = (TextView) rowView.findViewById(R.id.txt_num_impaye);
        TextView txt_montant_impaye = (TextView) rowView.findViewById(R.id.txt_montant_impaye);





        try {

            txt_raison_sociale.setText(imp_Frns.getRaisonSociale());
            txt_date_impaye.setText(sdf.format(imp_Frns.getDateImpayeFournisseur()));


            txt_num_impaye.setText(imp_Frns.getNumeroImpayeFournisseur());
            txt_montant_impaye.setText(formatter.format(imp_Frns.getMontantImpayeFournisseur()) );

        }
        catch (Exception ex)
        {
            Log.e("ERROR_echeance_client",ex.getMessage().toString())  ;
        }




        return rowView;

    }




}


