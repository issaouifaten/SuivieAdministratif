package com.example.suivieadministratif.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.BonRetourVente;
import com.example.suivieadministratif.model.SuivieImportationDossier;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SuivieImportationAdapter extends ArrayAdapter<SuivieImportationDossier> {


    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("0.000");

    private final Activity activity;
    private final ArrayList<SuivieImportationDossier> listSuivieImportationDossier ;

    public SuivieImportationAdapter(Activity activity  , ArrayList<SuivieImportationDossier> listSuivieImportationDossier) {
        super(activity, R.layout.item_suivie_importation , listSuivieImportationDossier);
        // TODO Auto-generated constructor stub

        this.activity=activity;
        this.listSuivieImportationDossier=listSuivieImportationDossier;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();
        Context context = parent.getContext();

        View rowView=inflater.inflate(R.layout.item_suivie_importation, null, true);


        SuivieImportationDossier  s_importation = listSuivieImportationDossier.get(position);


        CardView card_suivie_importation  = (CardView) rowView.findViewById(R.id.item_suivie_importation);
        TextView txt_raison_social        = (TextView) rowView.findViewById(R.id.txt_raison_social);
        TextView txt_date_achat       = (TextView) rowView.findViewById(R.id.txt_date_achat);


        TextView  txt_tot_net_ht          = (TextView) rowView.findViewById(R.id.txt_tot_net_ht);
        TextView  txt_tot_tva         = (TextView) rowView.findViewById(R.id.txt_tot_tva);
        TextView  txt_timbre_fiscal= (TextView) rowView.findViewById(R.id.txt_timbre_fiscal);
        TextView  txt_tot_ttc= (TextView) rowView.findViewById(R.id.txt_tot_ttc);


        txt_raison_social .setText (s_importation.getRaisonSociale());
        txt_date_achat.setText(df.format(s_importation.getDateAchatDiver()));


        txt_tot_net_ht.setText(formatter.format(s_importation.getTotalNetHT()));
        txt_tot_tva.setText(formatter.format(s_importation.getTotalTVA()));
        txt_timbre_fiscal.setText(formatter.format(s_importation.getTimbreFiscal()));
        txt_tot_ttc.setText(formatter.format(s_importation.getTotalTTC()));



        return rowView ;

    }


}
