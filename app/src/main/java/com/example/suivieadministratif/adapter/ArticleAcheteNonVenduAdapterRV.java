package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.Article;
import com.example.suivieadministratif.model.ArticleAcheteNonVendu;
import com.example.suivieadministratif.module.Stock.FicheArticleActivity;
import com.example.suivieadministratif.module.Stock.StockAlaDateActivity;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ArticleAcheteNonVenduAdapterRV extends RecyclerView.Adapter<ArticleAcheteNonVenduAdapterRV.ViewHolder> {


    private final Activity activity;

    private final ArrayList<ArticleAcheteNonVendu> listArticle;


    String LibelleDepot ;
    public static String login;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("0.000");


    public ArticleAcheteNonVenduAdapterRV(Activity activity, ArrayList<ArticleAcheteNonVendu> listArticle , String  LibelleDepot ) {

        this.activity = activity;
        this.listArticle = listArticle;
        this.LibelleDepot=LibelleDepot ;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_achete_nn_vendu , parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final ArticleAcheteNonVendu article = listArticle.get(position);


        final NumberFormat format = NumberFormat.getNumberInstance(Locale.FRENCH);
        format.setMinimumFractionDigits(3);
        format.setMaximumFractionDigits(3);

        holder.txt_code_article.setText(article.getCodeArticle());
        holder.txt_designation_article.setText(article.getDesignation());


        holder.txt_prix_achat_ht.setText(format.format(article.getPrixAchatHT()));
        holder.txt_prix_ttc.setText(format.format(article.getMontantTTC()));

        holder.txt_quantite_stock.setText(article.getQTStock() + "");
        holder.txt_quantite_achat.setText(article.getQtAchat() + "");
        holder.txt_quantite_stock_ala_date.setText(article.getQunatite_stock_a_la_date()+ "");


        holder.btn_fiche_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent toFicheArticle  = new Intent(activity  , FicheArticleActivity.class)  ;

                ArticleAcheteNonVendu artSelected  = listArticle.get(position)  ;

                toFicheArticle.putExtra("CodeDepot" , article.getCodeDepot() )  ;
                toFicheArticle.putExtra("LibelleDepot" , LibelleDepot )  ;
                toFicheArticle.putExtra("CodeArticle" , artSelected.getCodeArticle() )  ;
                toFicheArticle.putExtra("DesignArticle" , artSelected.getDesignation())  ;
                toFicheArticle.putExtra("prix_achat_ht"  ,artSelected.getPrixAchatHT())  ;
                toFicheArticle.putExtra("prix_mnt_tva"  ,artSelected.getMontantTVA())  ;
                toFicheArticle.putExtra("prix_montant_ttc"  ,artSelected.getMontantTTC())  ;
                toFicheArticle.putExtra("qunatite"  ,artSelected.getQTStock())  ;


                activity.startActivity(toFicheArticle);

            }
        });


        holder.btn_stock_ala_date.setVisibility(View.INVISIBLE);

        holder.btn_stock_ala_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent toStockAladate  = new Intent(activity  , StockAlaDateActivity.class);


                ArticleAcheteNonVendu artSelected  = listArticle.get(position)  ;
                toStockAladate.putExtra("CodeDepot" , article.getCodeDepot())  ;
                toStockAladate.putExtra("LibelleDepot" , LibelleDepot )  ;
                toStockAladate.putExtra("CodeArticle" , artSelected.getCodeArticle() )  ;
                toStockAladate.putExtra("DesignArticle" , artSelected.getDesignation())  ;
                toStockAladate.putExtra("prix_achat_ht"  ,artSelected.getPrixAchatHT())  ;
                toStockAladate.putExtra("prix_mnt_tva"  ,artSelected.getMontantTVA())  ;
                toStockAladate.putExtra("prix_montant_ttc"  ,artSelected.getMontantTTC())  ;

                toStockAladate.putExtra("qunatite"  ,artSelected.getQTStock())  ;
                activity.startActivity(toStockAladate) ;



            }
        });


    }

    @Override
    public int getItemCount() {
        Log.e("size", "" + listArticle.size());
        return listArticle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView txt_designation_article;
        TextView txt_code_article;

        TextView txt_prix_achat_ht;
        TextView txt_prix_ttc;


        TextView txt_quantite_stock;
        TextView txt_quantite_achat;
        TextView txt_quantite_stock_ala_date;

        LinearLayout btn_fiche_article;
        LinearLayout btn_stock_ala_date;


        public ViewHolder(View itemView) {
            super(itemView);

            txt_designation_article = (TextView) itemView.findViewById(R.id.txt_designation);
            txt_code_article = (TextView) itemView.findViewById(R.id.txt_code_article);


            txt_prix_achat_ht = (TextView) itemView.findViewById(R.id.txt_prix_achat_ht);
            txt_prix_ttc = (TextView) itemView.findViewById(R.id.txt_prix_ttc);


            txt_quantite_stock = (TextView) itemView.findViewById(R.id.txt_quantite_stock);
            txt_quantite_achat = (TextView) itemView.findViewById(R.id.txt_quantite_achat);
            txt_quantite_stock_ala_date = (TextView) itemView.findViewById(R.id.txt_quantite_a_la_date);

            btn_fiche_article= (LinearLayout)  itemView.findViewById(R.id.btn_fiche_article);
            btn_stock_ala_date= (LinearLayout)  itemView.findViewById(R.id.btn_stock_ala_date);

        }

    }

}
