package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.CaisseDepense;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CaisseDepenseAdapterRV extends RecyclerView.Adapter<CaisseDepenseAdapterRV.ViewHolder> {


    private final Activity activity;

    private final ArrayList<CaisseDepense> listCaisseDepense ;


    public static String login;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("0.000");


    public CaisseDepenseAdapterRV(Activity activity, ArrayList<CaisseDepense> listCaisseDepense ) {

        this.activity = activity;
        this.listCaisseDepense = listCaisseDepense;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_caisse_depense, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final CaisseDepense caisse = listCaisseDepense.get(position);

        holder.txt_nom_compte.setText(  caisse.getLibelleCompte()  );

        holder.txt_solde.setText(formatter.format(caisse.getSolde()) + "");

        if (caisse.getLibelleCompte().equals("TOTAL DEPENSE 1") )
        {

             holder.card_caisse.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                  /*   Intent  toDetail = new Intent( activity  , MouvementCaisseDepenseDetailActivity.class) ;
                     activity . startActivity(toDetail);*/
                 }
             });

        }

    }

    @Override
    public int getItemCount() {
        Log.e("size", "" + listCaisseDepense.size());
        return listCaisseDepense.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public CardView card_caisse;



        public TextView txt_nom_compte;
        public TextView txt_solde;




        public ViewHolder(View itemView) {
            super(itemView);

            card_caisse = (CardView) itemView.findViewById(R.id.card_caisse);

            txt_nom_compte = (TextView) itemView.findViewById(R.id.txt_nom_compte);
            txt_solde = (TextView) itemView.findViewById(R.id.txt_solde);



        }

    }

}
