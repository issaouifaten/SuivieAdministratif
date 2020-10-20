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
import com.example.suivieadministratif.model.LigneBonRetourVente;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Created by fatima on 20/01/2017.
 */
public class LigneBRAdapter extends ArrayAdapter<LigneBonRetourVente> {


    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");


    NumberFormat formatter = new DecimalFormat("0.000");

    private final Activity activity;
    private final ArrayList<LigneBonRetourVente> listArticle;

    public LigneBRAdapter(Activity activity  , ArrayList<LigneBonRetourVente> listArticle) {
        super(activity, R.layout.item_lbc, listArticle);

        this.activity=activity;
        this.listArticle=listArticle;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();
        Context context = parent.getContext();

        View rowView=inflater.inflate(R.layout.item_lbc  , null, true);

        LigneBonRetourVente article  = listArticle.get(position);

        TextView txt_code_article   = (TextView) rowView.findViewById(R.id.txt_article);
        TextView txt_qt_article    = (TextView) rowView.findViewById(R.id.txt_qt_article);
        TextView txt_prix_ttc      = (TextView) rowView.findViewById(R.id.txt_prix_ttc);



        txt_code_article .setText    (article.getCodeArticle());
        txt_prix_ttc.setText (formatter.format( article.getMontantTTC() ) );
        txt_qt_article.setText   (article.getQuantite()+"");

        return rowView;

    }


}
