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
import com.example.suivieadministratif.model.BonLivraisonVente;
import com.example.suivieadministratif.model.BonRetourVente;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.cardview.widget.CardView;

public class BonRetourAdapter extends ArrayAdapter<BonRetourVente> {


    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("0.000");

    private final Activity activity;
    private final ArrayList<BonRetourVente> listBonRetour ;

    public BonRetourAdapter(Activity activity  , ArrayList<BonRetourVente> listBonRetour) {
        super(activity, R.layout.item_bon_commande , listBonRetour);
        // TODO Auto-generated constructor stub

        this.activity=activity;
        this.listBonRetour=listBonRetour;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();
        Context context = parent.getContext();

        View rowView=inflater.inflate(R.layout.item_bon_commande, null, true);


        BonRetourVente br = listBonRetour.get(position);

        CardView card_cmd  = (CardView) rowView.findViewById(R.id.item_bon_commande);
        TextView txt_num_br        = (TextView) rowView.findViewById(R.id.txt_num_bc);
        TextView txt_raison_client       = (TextView) rowView.findViewById(R.id.txt_raison_client);
        TextView txt_ttc          = (TextView) rowView.findViewById(R.id.txt_prix_ttc);
        TextView txt_ht           = (TextView) rowView.findViewById(R.id.txt_prix_ht);
        TextView txt_tva          = (TextView) rowView.findViewById(R.id.txt_prix_tva);
        TextView txt_remise       = (TextView) rowView.findViewById(R.id.txt_remise);
        TextView txt_date_bc         = (TextView) rowView.findViewById(R.id.txt_date_bc);
        TextView     txt_libelle_etat= (TextView) rowView.findViewById(R.id.txt_libelle_etat);


        txt_ht.setText   (formatter.format(br.getTotalHT())+"");
        txt_tva.setText   (formatter.format(br.getTotalTVA())+"");
        txt_remise.setText   (formatter.format(br.getTotalRemise())+"");
        txt_ttc.setText   (formatter.format(br.getTotalTTC())+"");

            txt_num_br .setText (br.getNumeroBonRetourVente());
            txt_raison_client.setText(br.getRaisonSociale());
            txt_ttc.setText   (formatter.format(br.getTotalTTC())+" TTC");
            txt_date_bc.setText(df.format(br.getDateBonRetourVente()));
            txt_libelle_etat.setText(br .getLibelleEtat());

            if (br.getNumeroEtat().equals("E00"))
            {
                card_cmd.setCardBackgroundColor(activity.getResources().getColor(R.color.back_rouge_));
            }

        return rowView ;

    }


}
