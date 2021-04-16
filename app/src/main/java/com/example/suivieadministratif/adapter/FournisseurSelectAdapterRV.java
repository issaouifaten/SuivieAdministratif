package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.Fournisseur;
import com.example.suivieadministratif.module.achat.FicheFournisseurActivity;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class FournisseurSelectAdapterRV extends RecyclerView.Adapter<FournisseurSelectAdapterRV.ViewHolder> {


    private final Activity activity;

    private final ArrayList<Fournisseur> listFrns;  // = new ArrayList<>();
    String Origine;


    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat HeureF = new SimpleDateFormat("HH:mm");
    DecimalFormat decFormat = new DecimalFormat("0.00");


    public FournisseurSelectAdapterRV(Activity activity, ArrayList<Fournisseur> listFrns) {

        this.activity = activity;
        this.listFrns = listFrns;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fournisseur_select, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Fournisseur frns = listFrns.get(position);

        holder.txt_nom_prenom.setText(frns.getRaisonSocial());
        holder.txt_nom_utilisateur.setText(frns.getCodeFournisseur());


        if (frns.getNbrClick() == 0) {
            holder.cb_select_user.setChecked(false);
        } else if (frns.getNbrClick() == 1) {
            holder.cb_select_user.setChecked(true);
        }

        holder.card_item_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                holder.cb_select_user.setChecked(true);
                frns.setNbrClick(1);


                for (Fournisseur fr : listFrns) {

                    if (!frns.getCodeFournisseur().equals(fr.getCodeFournisseur())) {
                        holder.cb_select_user.setChecked(false);
                        fr.setNbrClick(0);
                    }

                }
                notifyDataSetChanged();

                Intent toFicheFournisseur = new Intent(activity, FicheFournisseurActivity.class);
                toFicheFournisseur.putExtra("CodeFournisseur", frns.getCodeFournisseur());
                toFicheFournisseur.putExtra("RaisonSociale", frns.getRaisonSocial());
                activity.startActivity(toFicheFournisseur);



                notifyDataSetChanged();
            }


        });


    }


    @Override
    public int getItemCount() {
        Log.e("size", "" + listFrns.size());
        return listFrns.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public CardView card_item_user;
        public CheckBox cb_select_user;
        public TextView txt_nom_prenom;
        public TextView txt_nom_utilisateur;

        public ViewHolder(View itemView) {
            super(itemView);

            card_item_user = (CardView) itemView.findViewById(R.id.card_item_user);
            cb_select_user = (CheckBox) itemView.findViewById(R.id.cb_select);
            txt_nom_prenom = (TextView) itemView.findViewById(R.id.txt_nom_prenom);
            txt_nom_utilisateur = (TextView) itemView.findViewById(R.id.txt_nom_utilisateur);

        }

    }


}
