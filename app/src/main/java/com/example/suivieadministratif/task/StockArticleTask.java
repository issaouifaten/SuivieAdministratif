package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.ArticleFaibleRotationAdapterLV;
import com.example.suivieadministratif.adapter.ArticleStockAdapter;
import com.example.suivieadministratif.adapter.StockArticleAdapterRV;
import com.example.suivieadministratif.model.Article;
import com.example.suivieadministratif.model.ArticleFaibleRotation;
import com.example.suivieadministratif.model.Depot;
import com.example.suivieadministratif.module.Stock.EtatDeStockActivity;
import com.example.suivieadministratif.module.Stock.FicheArticleActivity;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

public class StockArticleTask extends AsyncTask<String, String, String> {


    Activity activity;

    RecyclerView rv_article;

    TextView txt_rech;
    ProgressBar pb_rech;

    String CodeDepot;
    String LibelleDepot;
    String term_search;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    ArrayList<Article> listArticle = new ArrayList<>();


    float total_ht = 0;
    double total_tva = 0;
    double total_ttc = 0;
    double total_vente_ttc = 0;
    int total_qt = 0;


    public StockArticleTask(Activity activity, RecyclerView rv_article, TextView txt_rech, ProgressBar pb_rech, String codeDepot, String LibelleDepot, String term_search) {
        this.activity = activity;
        this.rv_article = rv_article;
        this.txt_rech = txt_rech;
        this.pb_rech = pb_rech;
        CodeDepot = codeDepot;
        this.LibelleDepot = LibelleDepot;
        this.term_search = term_search;

        Log.e("StockArticleTask", "term_search " + term_search);


        SharedPreferences prefe = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);

        Log.e("BON_CMD", Param.PEF_SERVER + "-" + ip + "-" + base);


        connectionClass = new ConnectionClass();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb_rech.setVisibility(View.VISIBLE);
        txt_rech.setText("Recherche en cours ...");


        total_ht = 0;
        total_tva = 0;
        total_ttc = 0;
        total_qt = 0;

        EtatDeStockActivity.txt_tot_ht.setText("---.---");
        EtatDeStockActivity.txt_tot_tva.setText("---.---");
        EtatDeStockActivity.txt_tot_ttc.setText("---.---");
        EtatDeStockActivity.txt_tot_quantite.setText("---");

        StockArticleAdapterRV articleStockAdapter = new StockArticleAdapterRV(activity, listArticle,LibelleDepot);
        rv_article.setAdapter(articleStockAdapter);

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);
            if (con == null) {
                z = "Error in connection with SQL server";
            } else {


                String QueryStock = "  select   CodeDepot  , Article.CodeArticle  , Article.Designation  \n" +
                        "  ,Article.PrixAchatHT , (Article.PrixAchatHT  * TVA.TauxTVA  /100)  as MontantTVA  ,\n" +
                        "  (Article.PrixAchatHT  * (1+  (TVA.TauxTVA  /100) ) ) as MontantTTC \n" +
                        "  ,Article.PrixVenteTTC\n" +
                        "  ,Stock.Quantite , (Article.PrixAchatHT *Stock.Quantite   ) AS   tt_ht \n " +
                        ", (Article.PrixAchatHT  * (1+  (TVA.TauxTVA  /100) )  *Stock.Quantite    ) AS   tt_TTC " +
                        "from   Stock  \n" +
                        "inner  join Article  on  Article.CodeArticle= Stock.CodeArticle \n" +
                        "inner  join  TVA on TVA.CodeTVA  = Article.CodeTVA\n" +
                        "where  CodeDepot = '" + CodeDepot + "'  and Quantite >= 0\n" +
                        "and   (Article.Designation   like  '%" + term_search + "%'  or  Article.CodeArticle  like  '%" + term_search + "%' ) \n" +
                        "  ";


                Log.e("QueryProc", "" + QueryStock);

                PreparedStatement ps = con.prepareStatement(QueryStock);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    String CodeDepot = rs.getString("CodeDepot");
                    String CodeArticle = rs.getString("CodeArticle");
                    String Designation = rs.getString("Designation");

                    float PrixAchatHT = rs.getFloat("PrixAchatHT");
                    double MontantTVA = rs.getDouble("MontantTVA");
                    double MontantTTC = rs.getDouble("MontantTTC");
                    double PrixVenteTTC = rs.getDouble("PrixVenteTTC");


                    float tt_ht = rs.getFloat("tt_ht");
                    float tt_TTC = rs.getFloat("tt_TTC");

                    int Quantite = rs.getInt("Quantite");


                    total_ht += tt_ht ;

                    total_tva += MontantTVA * Quantite ;
                    total_ttc += tt_TTC;
                    total_qt += Quantite;

                    Article article = new Article(CodeDepot, CodeArticle, Designation, PrixAchatHT, MontantTTC, PrixVenteTTC, MontantTVA, Quantite);
                    listArticle.add(article);

                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
            Log.e("ERROR_art_faible_rot", "" + ex.getMessage().toString());
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb_rech.setVisibility(View.INVISIBLE);

        txt_rech.setText(listArticle.size() + " articles trouvés");

        StockArticleAdapterRV articleStockAdapter = new StockArticleAdapterRV(activity, listArticle ,LibelleDepot);
        rv_article.setAdapter(articleStockAdapter);


        final NumberFormat format = NumberFormat.getNumberInstance(Locale.FRENCH);
        format.setMinimumFractionDigits(3);
        format.setMaximumFractionDigits(3);


        EtatDeStockActivity.txt_tot_ht.setText( format.format(total_ht) +"");
        EtatDeStockActivity.txt_tot_tva.setText(format.format(total_tva));
        EtatDeStockActivity.txt_tot_ttc.setText(format.format(total_ttc));
        EtatDeStockActivity.txt_tot_quantite.setText(total_qt + "");




    }


}