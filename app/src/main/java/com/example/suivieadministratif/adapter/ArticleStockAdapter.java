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
import com.example.suivieadministratif.model.Article;
import com.example.suivieadministratif.model.BonLivraisonVente;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ArticleStockAdapter extends ArrayAdapter<Article> {


    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("0.000");

    private final Activity activity;
    private final ArrayList<Article> listArticle ;

    public ArticleStockAdapter(Activity activity, ArrayList<Article> listArticle) {
        super(activity, R.layout.item_article_stock, listArticle);
        // TODO Auto-generated constructor stub

        this.activity = activity;
        this.listArticle = listArticle;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();

        View rowView = inflater.inflate(R.layout.item_article_stock , null, true);


        Article article = listArticle.get(position);


        TextView txt_designation_article = (TextView) rowView.findViewById(R.id.txt_designation);
        TextView txt_code_article = (TextView) rowView.findViewById(R.id.txt_code_article);

        TextView txt_prix_achat_ht = (TextView) rowView.findViewById(R.id.txt_prix_achat_ht);
        TextView txt_mnt_tva = (TextView) rowView.findViewById(R.id.txt_mnt_tva);
        TextView txt_prix_ttc = (TextView) rowView.findViewById(R.id.txt_prix_ttc);
        TextView txt_quantite = (TextView) rowView.findViewById(R.id.txt_quantite);


        final NumberFormat format = NumberFormat.getNumberInstance(Locale.FRENCH);
        format.setMinimumFractionDigits(3);
        format.setMaximumFractionDigits(3);



        txt_code_article.setText(article.getCodeArticle());
        txt_designation_article.setText(article.getDesignationArticle());

        txt_prix_achat_ht.setText(format.format(article.getPrixAchatHT()));
        txt_mnt_tva.setText(format.format(article.getMontantTVA()));
        txt_prix_ttc.setText(format.format(article.getMontantTTC()));
        txt_quantite.setText( article.getQuantite() +"");


        return rowView;

    }


}
