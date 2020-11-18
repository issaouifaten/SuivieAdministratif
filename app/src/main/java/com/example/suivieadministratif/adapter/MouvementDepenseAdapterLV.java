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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class MouvementDepenseAdapterLV extends ArrayAdapter<MouvementCaisseDepense> {


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    DecimalFormat formatter = new DecimalFormat("0.000");
    private final Activity activity;
    private final ArrayList<MouvementCaisseDepense> listMvmntCaisseDep;

    public MouvementDepenseAdapterLV(Activity activity, ArrayList<MouvementCaisseDepense> listMvmntCaisseDep) {
        super(activity, R.layout.item_depense  , listMvmntCaisseDep);
        // TODO Auto-generated constructor stub
        this.activity     = activity;
        this.listMvmntCaisseDep = listMvmntCaisseDep;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView = inflater.inflate (R.layout.item_depense  , null, true);

        MouvementCaisseDepense mvmntCaisse = listMvmntCaisseDep.get(position);

        TextView txt_type_dep = (TextView) rowView.findViewById(R.id.txt_type_depense);
        TextView txt_libelle_dep = (TextView) rowView.findViewById(R.id.txt_depense);
        TextView txt_etablie_par  = (TextView) rowView.findViewById(R.id.txt_etablie_par);
        TextView  txt_total_montant  = (TextView) rowView.findViewById(R.id.txt_total_montant);
        TextView txt_date_depense = (TextView) rowView.findViewById(R.id.txt_date_depense);


        txt_type_dep  .setText(mvmntCaisse.getTypeDepense());
        txt_libelle_dep.setText(mvmntCaisse.getLibelleDep());
        txt_etablie_par.setText(mvmntCaisse.getNomUtilisateur());
        txt_total_montant.setText(formatter.format(mvmntCaisse.getTotalPayer())+" Dt");
        txt_date_depense.setText(sdf.format(mvmntCaisse.getHeureCreation()));


        return rowView;

    }




}


