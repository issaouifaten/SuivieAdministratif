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
import com.example.suivieadministratif.model.MouvementVenteService;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class EcheanceClientAdapterLV extends ArrayAdapter<EcheanceClient> {


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    DecimalFormat formatter = new DecimalFormat("0.000");
    private final Activity activity;
    private final ArrayList<EcheanceClient> listEcheanceClient;

    public EcheanceClientAdapterLV(Activity activity, ArrayList<EcheanceClient> listEcheanceClient) {
        super(activity, R.layout.item_echeance_client, listEcheanceClient);
        // TODO Auto-generated constructor stub
        this.activity     = activity;
        this.listEcheanceClient = listEcheanceClient;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView = inflater.inflate (R.layout.item_echeance_client  , null, true);

        EcheanceClient  ec = listEcheanceClient.get(position);

        TextView  txt_raison_sociale= (TextView) rowView.findViewById(R.id.txt_raison);
        TextView txt_date_echenace = (TextView) rowView.findViewById(R.id.txt_date_echeance);
        TextView txt_libelle = (TextView) rowView.findViewById(R.id.txt_libelle);
        TextView txt_montant = (TextView) rowView.findViewById(R.id.txt_montant);
        TextView txt_nom_utilisateur = (TextView) rowView.findViewById(R.id.txt_etablie_par);
        TextView txt_num_reg  = (TextView) rowView.findViewById(R.id.txt_num_reg);

        try {

            txt_raison_sociale.setText(ec.getRaisonSociale());
            txt_date_echenace.setText(sdf.format(ec.getEcheance()));
            txt_libelle.setText(ec.getLibelle() +" de montant ");
            txt_montant.setText(formatter.format(ec.getMontantRecu())+" Dt");
            txt_num_reg.setText(ec.getNumeroReglementClient());
            txt_nom_utilisateur.setText(ec.getNomUtilisateurActuel());
        }
        catch (Exception ex)
        {
            Log.e("ERROR_echeance_client",ex.getMessage().toString())  ;
        }



        return rowView;

    }




}


