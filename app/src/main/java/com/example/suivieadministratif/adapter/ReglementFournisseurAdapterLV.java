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
import com.example.suivieadministratif.model.ReglementFournisseur;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ReglementFournisseurAdapterLV extends ArrayAdapter<ReglementFournisseur> {


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    DecimalFormat formatter = new DecimalFormat("0.000");
    private final Activity activity;
    private final ArrayList<ReglementFournisseur> listRegFrns;

    public ReglementFournisseurAdapterLV(Activity activity, ArrayList<ReglementFournisseur> listRegFrns) {
        super(activity, R.layout.item_reglement_fournisseur, listRegFrns);
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.listRegFrns = listRegFrns;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView = inflater.inflate(R.layout.item_reglement_fournisseur, null, true);

        ReglementFournisseur reglementFrns = listRegFrns.get(position);

        TextView txt_raison = (TextView) rowView.findViewById(R.id.txt_raison_social);
        TextView txt_etablie_par = (TextView) rowView.findViewById(R.id.txt_etablie_par);
        TextView txt_total_montant = (TextView) rowView.findViewById(R.id.txt_total_montant);
        TextView txt_date_reglement = (TextView) rowView.findViewById(R.id.txt_date_depense);


        try {
            txt_raison.setText(reglementFrns.getRaisonSociale());
            txt_etablie_par.setText(reglementFrns.getNomUtilisateur());
            txt_total_montant.setText(formatter.format(reglementFrns.getTotalPayement()) + " Dt");
            txt_date_reglement.setText(sdf.format(reglementFrns.getDateReglementFournisseur()));

        } catch (Exception ex){

            Log.e("ERROR_frnsadapter",""+ex.getMessage().toString()) ;
        }




        return rowView;

    }


}


