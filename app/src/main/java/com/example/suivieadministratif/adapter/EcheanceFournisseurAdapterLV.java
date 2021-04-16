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
import com.example.suivieadministratif.model.EcheanceClient;
import com.example.suivieadministratif.model.EcheanceFournisseur;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class EcheanceFournisseurAdapterLV extends ArrayAdapter<EcheanceFournisseur> {


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    DecimalFormat formatter = new DecimalFormat("0.000");
    private final Activity activity;
    private final ArrayList<EcheanceFournisseur> listEcheanceFournisseur;

    public EcheanceFournisseurAdapterLV(Activity activity, ArrayList<EcheanceFournisseur> listEcheanceFournisseur) {
        super(activity, R.layout.item_echeance_fournisseur , listEcheanceFournisseur);
        // TODO Auto-generated constructor stub
        this.activity     = activity;
        this.listEcheanceFournisseur = listEcheanceFournisseur;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView = inflater.inflate (R.layout.item_echeance_fournisseur , null, true);

        EcheanceFournisseur  ef = listEcheanceFournisseur.get(position);

        TextView  txt_raison_sociale= (TextView) rowView.findViewById(R.id.txt_raison);
        TextView txt_date_echenace = (TextView) rowView.findViewById(R.id.txt_date_echeance);
        TextView txt_libelle = (TextView) rowView.findViewById(R.id.txt_libelle);
        TextView txt_montant = (TextView) rowView.findViewById(R.id.txt_montant);
        TextView txt_libelle_compte = (TextView) rowView.findViewById(R.id.txt_libelle_compte);
        TextView txt_reg_frns  = (TextView) rowView.findViewById(R.id.txt_reg_frns);

        try {

            txt_raison_sociale.setText(ef.getRaisonSociale());
            txt_date_echenace.setText(sdf.format(ef.getEcheance()));
            txt_libelle.setText(ef.getLibelle() +" de montant ");


            final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
            instance.setMinimumFractionDigits(3);
            instance.setMaximumFractionDigits(3);

            txt_montant.setText(instance.format(ef.getMontantRecu())+" Dt");
            txt_reg_frns.setText(ef.getNumeroReglementFournisseur());
            txt_libelle_compte.setText(ef.getLibelleCompte());

        }
        catch (Exception ex)
        {
            Log.e("ERROR_ech_frns_adapt",ex.getMessage().toString())  ;
        }


/*
        TextView txt_num_piece = (TextView) rowView.findViewById(R.id.txt_piece);
        TextView txt_date_piece = (TextView) rowView.findViewById(R.id.txt_date_piece);


        TextView txt_code_client  = (TextView) rowView.findViewById(R.id.txt_code_client);
        TextView txt_raison_client  = (TextView) rowView.findViewById(R.id.txt_raison_client);


        TextView txt_code_article  = (TextView) rowView.findViewById(R.id.txt_code_article);
        TextView txt_design_article  = (TextView) rowView.findViewById(R.id.txt_design_article);
        TextView txt_qt_article  = (TextView) rowView.findViewById(R.id.txt_qt);


        TextView  txt_fournisseur = (TextView) rowView.findViewById(R.id.txt_frns);

        TextView txt_montant = (TextView) rowView.findViewById(R.id.txt_total_montant);



*/

        return rowView;

    }




}


