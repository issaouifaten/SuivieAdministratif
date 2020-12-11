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
import com.example.suivieadministratif.model.ArticleNonMouvemente;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ArticleNonMouvementeAdapterLV extends ArrayAdapter<ArticleNonMouvemente> {


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    DecimalFormat formatter = new DecimalFormat("0.000");
    private final Activity activity;
    private final ArrayList<ArticleNonMouvemente> listArticleNonMouvemente;

    public ArticleNonMouvementeAdapterLV(Activity activity, ArrayList<ArticleNonMouvemente> listArticleNonMouvemente) {
        super(activity, R.layout.item_article_non_mouvemente, listArticleNonMouvemente);
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.listArticleNonMouvemente = listArticleNonMouvemente;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView = inflater.inflate(R.layout.item_article_non_mouvemente, null, true);

        ArticleNonMouvemente art_nn_mvmnt = listArticleNonMouvemente.get(position);


        TextView txt_designation = (TextView) rowView.findViewById(R.id.txt_des_art);
        TextView txt_code_art = (TextView) rowView.findViewById(R.id.txt_code_art);


        TextView txt_qt_stock = (TextView) rowView.findViewById(R.id.txt_qt_stock);
        TextView txt_qt_achete = (TextView) rowView.findViewById(R.id.txt_qt_achete);
        TextView txt_puht = (TextView) rowView.findViewById(R.id.txt_puht);
        TextView txt_v_ht = (TextView) rowView.findViewById(R.id.txt_v_ht);


        DecimalFormat decF = new DecimalFormat("0.000");

        try {
            txt_designation.setText(art_nn_mvmnt.getDesignation());
            txt_code_art.setText(art_nn_mvmnt.getCodeArticle());

            txt_qt_stock.setText(art_nn_mvmnt.getQuantiteInitiale() + "");
            txt_qt_achete.setText(art_nn_mvmnt.getQuantiteAchete() + "");

            txt_puht.setText(decF.format(art_nn_mvmnt.getPrixAchatHT()) + "");
            txt_v_ht.setText(decF.format(art_nn_mvmnt.getValeurHT()));

        } catch (Exception ex) {
            Log.e("ERROR_adapter", ex.getMessage().toString());
        }


/*
        TextView txt_num_piece = (TextView) rowView.findViewById(R.id.txt_piece);
        TextView txt_date_piece = (TextView) rowView.findViewById(R.id.txt_date_piece);


        TextView txt_code_client  = (TextView) rowView.findViewById(R.id.txt_code_client);
        TextView txt_raison_client  = (TextView) rowView.findViewById(R.id.txt_raison_client);


        TextView txt_code_article  = (TextView) rowView.findViewById(R.id.txt_code_article);
        TextView txt_design_article  = (TextView) rowView.findViewById(R.id.txt_design_article);
        TextView txt_qt_article  = (TextView) rowView.findViewById(R.id.txt_qt);


        TextView  txt_fournisseur = (TextView) rowView.findViewById(R.id.txt_frns);

        TextView txt_montant = (TextView) rowView.findViewById(R.id.txt_total_montant);



*/

        return rowView;

    }


}


