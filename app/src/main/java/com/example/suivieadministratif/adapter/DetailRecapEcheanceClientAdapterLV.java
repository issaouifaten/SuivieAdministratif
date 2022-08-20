package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.DetailRecapEcheanceClient;
import com.example.suivieadministratif.model.PortfeuilleClient;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class DetailRecapEcheanceClientAdapterLV extends ArrayAdapter<DetailRecapEcheanceClient> {


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    DecimalFormat formatter = new DecimalFormat("0.000");
    private final Activity activity;
    private final ArrayList<DetailRecapEcheanceClient> listDetailRecapEcheanceClient ;

    String  Object  ;


    public DetailRecapEcheanceClientAdapterLV(Activity activity, ArrayList<DetailRecapEcheanceClient> listDetailRecapEcheanceClient  , String  Object   ) {
        super(activity, R.layout.item_portfeuille_client, listDetailRecapEcheanceClient);
        // TODO Auto-generated constructor stub
        this.activity     = activity;
        this.listDetailRecapEcheanceClient = listDetailRecapEcheanceClient;
        this.Object = Object  ;

    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView = inflater.inflate (R.layout.item_portfeuille_client  , null, true);

        DetailRecapEcheanceClient  ec = listDetailRecapEcheanceClient.get(position);

        TextView  txt_raison_sociale= (TextView) rowView.findViewById(R.id.txt_raison);
        TextView txt_date_echenace = (TextView) rowView.findViewById(R.id.txt_date_echeance);
        TextView txt_libelle = (TextView) rowView.findViewById(R.id.txt_libelle);
        TextView txt_montant = (TextView) rowView.findViewById(R.id.txt_montant);
        TextView txt_banque = (TextView) rowView.findViewById(R.id.txt_banque);
        TextView txt_num_reg  = (TextView) rowView.findViewById(R.id.txt_num_reg);
        TextView  txt_reference= (TextView) rowView.findViewById(R.id.txt_reference);

      ImageView img= (ImageView) rowView.findViewById(R.id.img);

        try {
            txt_raison_sociale.setText(ec.getRaisonSociale());
            txt_date_echenace.setText(sdf.format(ec.getEcheance()));
            txt_libelle.setText(ec.getModeReglement()+ " de montant ");
            txt_montant.setText(formatter.format(ec.getMontant())+" Dt");
            txt_num_reg.setText(ec.getNumReglement());
            txt_banque.setText(ec.getBanque());
            txt_reference.setText(ec.getRefrence());

            if (Object.equals("FRNS"))
                img.setImageResource(R.drawable.ic_frns);
            if (Object.equals("Client"))
                img.setImageResource(R.drawable.ic_customer);

        }
        catch (Exception ex)

        {
            Log.e("ERROR_echeance_client",ex.getMessage().toString())  ;
        }



        return rowView;

    }




}


