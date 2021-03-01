package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;


import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.FicheArticleAdapterRV;
import com.example.suivieadministratif.model.FicheArticle;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FicheArticlekParDepotParArticleTask extends AsyncTask<String, String, String> {

    String res;

    Activity activity;

    RecyclerView rv_list_article ;
   // SearchView search_v_article ;
    ProgressBar pb_chargement;

    String CodeDepot , CodeArticle;
    Date  date_debut  ; Date date_fin ;
    ConnectionClass connectionClass;
    String user, password, base, ip;

    ArrayList<FicheArticle> listFicheArticle= new ArrayList<>()  ;


    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    SimpleDateFormat  sdf  = new SimpleDateFormat("dd/MM/yyyy");

    int  full = 0  ;


    public FicheArticlekParDepotParArticleTask(Activity activity, RecyclerView rv_list_article  , String CodeDepot , String CodeArticle , Date  date_debut  , Date date_fin , ProgressBar pb_chargement ) {
        this.activity = activity;
        this.rv_list_article = rv_list_article ;
        this.CodeDepot = CodeDepot ;
        this.CodeArticle = CodeArticle ;
        this.date_debut  = date_debut  ;
        this.date_fin= date_fin  ;
        /*
         this.search_v_article = search_v_article ;
         */

        this.pb_chargement =pb_chargement ;



        SharedPreferences pref = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);
        connectionClass = new ConnectionClass();



    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
          pb_chargement.setVisibility(View.VISIBLE);
         full = 0  ;

    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);       // Connect to database
            Log.e("con", "" + con);
            if (con == null) {
                res = "Check Your Internet Access!";
            } else {

                String query = " \n" +
                        "DECLARE\t@return_value int\n" +
                        "\n" +
                        "EXEC\t@return_value = [dbo].[FicheArticle]\n" +
                        " @CodeDepot = N'"+CodeDepot+"',\n" +
                        " @CodeArticle = N'"+CodeArticle+"',\n" +
                        " @DateDebut = '"+sdf.format(date_debut)+"',\n" +
                        " @DateFin = '"+sdf.format(date_fin)+"'\n" +
                        "\n" +
                        "SELECT\t'Return Value' = @return_value "  ;


                Log.e("query_fiche_article", query);
                listFicheArticle.clear();

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                int solde_en_cours = 0;

                while (rs.next()) {

                    Date DatePiece = dtfSQL.parse(rs.getString("DatePiece"));

                    String Tiers  = rs.getString("Tier");
                    String Operation  = rs.getString("Operation");
                    String NumeroPiece  = rs.getString("NumeroPiece");
                    String CodeDepot  = rs.getString("CodeDepot");
                    String CodeArticle  = rs.getString("CodeArticle");

                    int Entree  = rs.getInt("Entree");
                    int Sortie  = rs.getInt("Sortie");
                    int solde  = Entree-Sortie ;

                    solde_en_cours = solde_en_cours + solde ;


                    Date HeureCreation = dtfSQL.parse(rs.getString("HeureCreaion"));
                    FicheArticle ficheArticle =  new FicheArticle(DatePiece,Tiers,Operation,NumeroPiece,CodeDepot,CodeArticle,Entree,Sortie ,solde_en_cours,HeureCreation) ;

                      listFicheArticle.add(ficheArticle) ;

                    full = 1 ;
                }
            }
            con.close();

        } catch (Exception ex) {

            res = ex.getMessage();
            Log.e("ERROR_listRDV", res.toString());

        }
        return null;

    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        pb_chargement.setVisibility(View.INVISIBLE);

        FicheArticleAdapterRV ficheArticleAdapterRV = new FicheArticleAdapterRV(activity, listFicheArticle);
        rv_list_article.setAdapter(ficheArticleAdapterRV);

    }
}


  /*  private ArrayList<Article> filter(ArrayList<Article> listArticle, String term) {

        term = term.toLowerCase();
        final ArrayList<Article> filetrListArticle = new ArrayList<>();

        for (Article a : listArticle) {
            final String txt_designation = a.getDesignationArticle().toLowerCase();
            final String txt_code_article = a.getCodeArticle().toLowerCase();


            if (txt_designation.contains(term) || txt_code_article
                    .contains(term) ) {
                filetrListArticle.add(a);
            }
        }
        return filetrListArticle;
    }*/





