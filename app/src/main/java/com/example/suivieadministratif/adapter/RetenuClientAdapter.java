package com.example.suivieadministratif.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.RetenuClientFournisseur;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by fatima on 20/01/2017.
 */
public class RetenuClientAdapter extends ArrayAdapter<RetenuClientFournisseur> {


    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");


    NumberFormat formatter = new DecimalFormat("0.000");
    NumberFormat format_taux = new DecimalFormat("0.0");
    private final Activity activity;
    private final ArrayList<RetenuClientFournisseur> listRetenuClient ;

    public RetenuClientAdapter(Activity activity  , ArrayList<RetenuClientFournisseur> listRetenuClient) {
        super(activity, R.layout.item_etat_entete, listRetenuClient);
        // TODO Auto-generated constructor stub

        this.activity=activity;
        this.listRetenuClient = listRetenuClient;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();
        Context context = parent.getContext();

        View rowView=inflater.inflate(R.layout.item_retenu_client, null, true);


        RetenuClientFournisseur retenuClient  = listRetenuClient.get(position);



        TextView txt_raison_client       = (TextView) rowView.findViewById(R.id.txt_raison_social);


        TextView txt_taux_retenu           = (TextView) rowView.findViewById(R.id.txt_taux_retenu);
        TextView txt_retenu           = (TextView) rowView.findViewById(R.id.txt_retenu);
        TextView txt_brut        = (TextView) rowView.findViewById(R.id.txt_brut);
        TextView txt_net          = (TextView) rowView.findViewById(R.id.txt_net);

        TextView txt_date_retenu         = (TextView) rowView.findViewById(R.id.txt_date_retenu);



        txt_raison_client.setText(retenuClient.getRaisonSociale());

        txt_taux_retenu.setText   (format_taux.format(retenuClient.getTauxRetenu())+" % ");
        txt_retenu.setText   (formatter.format(retenuClient.getRetenu())+"");
        txt_brut.setText   (formatter.format(retenuClient.getBrut())+"");
        txt_net.setText   (formatter.format(retenuClient.getNet())+"");

        txt_date_retenu.setText(df.format(retenuClient.getDateRetenu()));




        return rowView ;

    }


}
