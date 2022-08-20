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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ImpayeClientAdapterLV extends ArrayAdapter<ImpayeClient> {


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    DecimalFormat formatter = new DecimalFormat("0.000");
    private final Activity activity;
    private final ArrayList<ImpayeClient> listImpayeClient;

    public ImpayeClientAdapterLV(Activity activity, ArrayList<ImpayeClient> listImpayeClient) {
        super(activity, R.layout.item_impaye_client, listImpayeClient);
        // TODO Auto-generated constructor stub
        this.activity     = activity;
        this.listImpayeClient = listImpayeClient;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView = inflater.inflate (R.layout.item_impaye_client  , null, true);

        ImpayeClient  imp_client = listImpayeClient.get(position);

        TextView txt_raison_sociale= (TextView) rowView.findViewById(R.id.txt_raison);
        TextView txt_date_echenace = (TextView) rowView.findViewById(R.id.txt_date_echeance);


        TextView txt_reference = (TextView) rowView.findViewById(R.id.txt_reference);

        TextView txt_mode_reg = (TextView) rowView.findViewById(R.id.txt_mode_reg);
        TextView txt_montant_impaye = (TextView) rowView.findViewById(R.id.txt_montant_impaye);
        TextView txt_reste_a_payer = (TextView) rowView.findViewById(R.id.txt_reste_a_paye);

        TextView txt_banque= (TextView) rowView.findViewById(R.id.txt_banque);
        TextView txt_etat  = (TextView) rowView.findViewById(R.id.txt_etat);

        try {

            txt_raison_sociale.setText(imp_client.getRaisonSociale());
            txt_date_echenace.setText(sdf.format(imp_client.getEcheance()));

            txt_reference .setText(imp_client.getReference());
            txt_mode_reg.setText(imp_client.getModeReglement());
            txt_montant_impaye.setText(formatter.format(imp_client.getMontantImpaye()) );
            txt_reste_a_payer.setText(formatter.format(imp_client.getResteAPayer()) );
            txt_banque.setText(imp_client.getBanque());
            txt_etat.setText(imp_client.getEtat());
        }
        catch (Exception ex)
        {
            Log.e("ERROR_echeance_client",ex.getMessage().toString())  ;
        }




        return rowView;

    }




}


