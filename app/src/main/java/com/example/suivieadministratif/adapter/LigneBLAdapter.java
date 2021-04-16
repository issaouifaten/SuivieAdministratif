package com.example.suivieadministratif.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.LigneBonLivraisonVente;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by fatima on 20/01/2017.
 */
public class LigneBLAdapter extends ArrayAdapter<LigneBonLivraisonVente> {


    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");



    private final Activity activity;
    private final ArrayList<LigneBonLivraisonVente> listArticle;

    public LigneBLAdapter(Activity activity  , ArrayList<LigneBonLivraisonVente> listArticle) {
        super(activity, R.layout.item_ligne_piece, listArticle);

        this.activity=activity;
        this.listArticle=listArticle;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();
        Context context = parent.getContext();

        View rowView=inflater.inflate(R.layout.item_ligne_piece, null, true);

        LigneBonLivraisonVente article  = listArticle.get(position);

        TextView txt_code_article   = (TextView) rowView.findViewById(R.id.txt_code_article);
        TextView txt_designation   = (TextView) rowView.findViewById(R.id.txt_designation);
        TextView txt_qt_article    = (TextView) rowView.findViewById(R.id.txt_quantite);
        TextView txt_net_ht      = (TextView) rowView.findViewById(R.id.txt_net_ht);
        TextView txt_taux_remise      = (TextView) rowView.findViewById(R.id.txt_taux_remise);
        TextView txt_prix_ttc      = (TextView) rowView.findViewById(R.id.txt_mnt_ttc);

        final NumberFormat formatter = NumberFormat.getNumberInstance(Locale.FRENCH);
        formatter.setMinimumFractionDigits(3);
        formatter.setMaximumFractionDigits(3);

        txt_code_article .setText    (article.getCodeArticle());
        txt_designation .setText    (article.getDesignationArticle() );


        txt_net_ht.setText (formatter.format( article.getNetHT() ) );
        txt_taux_remise.setText (  article.getTauxRemise() +" %" );
        txt_prix_ttc.setText (formatter.format ( article.getMontantTTC() ) );



        txt_qt_article.setText   (article.getQuantite()+"");

        return rowView;

    }


}
