package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.Caisse;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CaisseAdapterRV extends RecyclerView.Adapter<CaisseAdapterRV.ViewHolder> {


    private final Activity activity;

    private final ArrayList<Caisse> listCaisse ;


    public static String login;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("0.000");


    public CaisseAdapterRV(Activity activity, ArrayList<Caisse> listCaisse ) {

        this.activity = activity;
        this.listCaisse = listCaisse;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_caisse, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Caisse caisse = listCaisse.get(position);



        if (caisse.getModeReglement().equals("Espece"))
            holder.txt_mode_reglement.setText("(E)");
       else if (caisse.getModeReglement().equals("Chéque"))
            holder.txt_mode_reglement.setText("(C)");



        holder.txt_nom_compte.setText(  caisse.getNomCompte()  );

        holder.txt_solde.setText(formatter.format(caisse.getSolde()) + "");
        holder.txt_type_piece.setText( "");

    }


    @Override
    public int getItemCount() {
        Log.e("size", "" + listCaisse.size());
        return listCaisse.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public CardView card_caisse;


        public TextView txt_type_piece ;
        public TextView txt_nom_compte;
        public TextView txt_solde;

        public TextView txt_mode_reglement;


        public ViewHolder(View itemView) {
            super(itemView);

            card_caisse = (CardView) itemView.findViewById(R.id.card_caisse);
            txt_type_piece = (TextView) itemView.findViewById(R.id.txt_type_piece);
            txt_nom_compte = (TextView) itemView.findViewById(R.id.txt_nom_compte);
            txt_solde = (TextView) itemView.findViewById(R.id.txt_solde);
            txt_mode_reglement = (TextView) itemView.findViewById(R.id.txt_mode_reglement);


        }

    }

}
