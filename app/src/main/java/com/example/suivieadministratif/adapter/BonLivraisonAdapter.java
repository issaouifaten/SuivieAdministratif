package com.example.suivieadministratif.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.BonLivraisonVente;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.cardview.widget.CardView;

public class BonLivraisonAdapter extends ArrayAdapter<BonLivraisonVente> {


    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("0.000");

    private final Activity activity;
    private final ArrayList<BonLivraisonVente> listBonLivraison;

    public BonLivraisonAdapter(Activity activity, ArrayList<BonLivraisonVente> listBonLivraison) {
        super(activity, R.layout.item_etat_entete, listBonLivraison);
        // TODO Auto-generated constructor stub

        this.activity = activity;
        this.listBonLivraison = listBonLivraison;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();

        View rowView = inflater.inflate(R.layout.item_etat_entete, null, true);


        BonLivraisonVente bl = listBonLivraison.get(position);

        CardView card_cmd = (CardView) rowView.findViewById(R.id.item_bon_commande);
        TextView txt_num_bl = (TextView) rowView.findViewById(R.id.txt_num_piece);
        TextView txt_raison_client = (TextView) rowView.findViewById(R.id.txt_raison_client);


        TextView txt_ht = (TextView) rowView.findViewById(R.id.txt_prix_net_ht);
        TextView txt_tva = (TextView) rowView.findViewById(R.id.txt_prix_tva);
        TextView txt_remise = (TextView) rowView.findViewById(R.id.txt_remise);
        TextView txt_ttc = (TextView) rowView.findViewById(R.id.txt_prix_ttc);

        TextView txt_date_bc = (TextView) rowView.findViewById(R.id.txt_date_bc);
        TextView txt_libelle_etat = (TextView) rowView.findViewById(R.id.txt_libelle_etat);


        TextView txt_etablie_par       = (TextView) rowView.findViewById(R.id.txt_etablie_par);
        txt_etablie_par.setText(bl.getNomUtilisateur());

        txt_num_bl.setText(bl.getNumeroBonLivraisonVente());
        txt_raison_client.setText(bl.getRaisonSociale());

        txt_ht.setText(formatter.format(bl.getTotalNetHT()) + "");
        txt_tva.setText(formatter.format(bl.getTotalTVA()) + "");

        txt_ttc.setText(formatter.format(bl.getTotalTTC()) + "");

        txt_date_bc.setText(df.format(bl.getDateBonLivraisonVente()));
        txt_libelle_etat.setText(bl.getLibelleEtat());

        if (bl.getNumeroEtat().equals("E00")) {
            card_cmd.setCardBackgroundColor(activity.getResources().getColor(R.color.back_rouge_));
        }

        return rowView;

    }


}
