package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.MouvementCaisseDepense;
import com.example.suivieadministratif.model.ReglementClient;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ReglementClientAdapterLV extends ArrayAdapter<ReglementClient> {


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    DecimalFormat formatter = new DecimalFormat("0.000");
    private final Activity activity;
    private final ArrayList<ReglementClient> listRegClient;

    public ReglementClientAdapterLV(Activity activity, ArrayList<ReglementClient> listRegClient) {
        super(activity, R.layout.item_reglement_client  , listRegClient);
        // TODO Auto-generated constructor stub
        this.activity     = activity;
        this.listRegClient = listRegClient;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView = inflater.inflate (R.layout.item_reglement_client  , null, true);

        ReglementClient  reglementClient = listRegClient.get(position);

        TextView txt_raison = (TextView) rowView.findViewById(R.id.txt_raison_social);
        TextView txt_etablie_par  = (TextView) rowView.findViewById(R.id.txt_etablie_par);
        TextView  txt_total_montant  = (TextView) rowView.findViewById(R.id.txt_total_montant);
        TextView txt_date_reglement = (TextView) rowView.findViewById(R.id.txt_date_depense);


        txt_raison  .setText(reglementClient.getRaisonSociale());
        txt_etablie_par.setText(reglementClient.getNomUtilisateur());
        txt_total_montant.setText(formatter.format(reglementClient.getTotalRecu())+" Dt");
        txt_date_reglement.setText(sdf.format(reglementClient.getHeureCreation()));


        return rowView;

    }




}


