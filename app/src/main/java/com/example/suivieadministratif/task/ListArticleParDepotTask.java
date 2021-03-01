package com.example.suivieadministratif.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.SpinnerAdapter;
import com.example.suivieadministratif.model.ArticleStock;
import com.example.suivieadministratif.module.Stock.FicheArticle.FicheArticleActivity;
import com.example.suivieadministratif.module.Stock.FicheArticle.ui.main.FicheArticleFragment;
import com.example.suivieadministratif.module.Stock.FicheArticle.ui.main.FicheArticlePagerAdapter;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class ListArticleParDepotTask extends AsyncTask<String, String, String> {

    String res;

    AppCompatActivity activity;


    Spinner sp_article;
    ViewPager viewPager;
    String CodeDepot;
    String Origine;
    ConnectionClass connectionClass;
    String user, password, base, ip;


    ArrayList<ArticleStock> listArticle = new ArrayList<>();
    ArrayList<String> listDesignation = new ArrayList<>();


    public ListArticleParDepotTask(AppCompatActivity activity, Spinner sp_article, String CodeDepot, ViewPager viewPager, String Origine) {
        this.activity = activity;

        this.sp_article = sp_article;
        this.CodeDepot = CodeDepot;
        this.viewPager = viewPager;
        this.Origine = Origine;


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


    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);       // Connect to database
            Log.e("con", "" + con);
            if (con == null) {
                res = "Check Your Internet Access!";
            } else {

                String query = " select   CodeDepot  ,Article.CodeArticle  , Designation  from  Stock  \n" +
                        "INNER JOIN Article  on Article.CodeArticle  = Stock.CodeArticle\n" +
                        "where CodeNature = '1'  and Stockable  = '1'   and Actif  = '1'  \n" +
                        "and CodeDepot = '01' ";


                Log.e("query_depot", query);

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {

                    String CodeArticle = rs.getString("CodeArticle");
                    String Designation = rs.getString("Designation");


                    ArticleStock article = new ArticleStock(CodeArticle, Designation);

                    listArticle.add(article);
                    listDesignation.add(CodeArticle+" - "+Designation);


                }
            }
            con.close();

        } catch (Exception ex) {

            res = ex.getMessage();
            Log.e("ERROR_depot", res.toString());
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

        if (Origine.equals("FicheArticleActivity")) {

            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(activity, listDesignation);
            sp_article.setAdapter(spinnerAdapter);


            FicheArticleActivity.CodeArticleSelected = listArticle.get(0).getCodeArticle();
            FicheArticleActivity.CodeDepotSelected = FicheArticleActivity.listDepot.get(FicheArticleActivity.tab_depot.getSelectedTabPosition()).getCodeDepot();

            FicheArticlekParDepotParArticleTask ficheArticlekParDepotParArticleTask = new FicheArticlekParDepotParArticleTask(activity, FicheArticleFragment.rv_list_fiche_article, FicheArticleActivity.CodeDepotSelected, FicheArticleActivity.CodeArticleSelected, FicheArticleActivity.date_debut, FicheArticleActivity.date_fin, FicheArticleFragment.progressBar);
            ficheArticlekParDepotParArticleTask.execute();


            sp_article.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    // String  CodeArticleSelected = listArticle.get(position).getCodeArticle() ;

                    FicheArticleActivity.CodeArticleSelected = listArticle.get(position).getCodeArticle();
                    FicheArticleActivity.CodeDepotSelected = FicheArticleActivity.listDepot.get(FicheArticleActivity.tab_depot.getSelectedTabPosition()).getCodeDepot();

                    FicheArticlekParDepotParArticleTask ficheArticlekParDepotParArticleTask = new FicheArticlekParDepotParArticleTask(activity, FicheArticleFragment.rv_list_fiche_article, FicheArticleActivity.CodeDepotSelected, FicheArticleActivity.CodeArticleSelected, FicheArticleActivity.date_debut, FicheArticleActivity.date_fin, FicheArticleFragment.progressBar);
                    ficheArticlekParDepotParArticleTask.execute();

                    FicheArticlePagerAdapter sectionsPagerAdapter = new FicheArticlePagerAdapter(activity  ,activity. getSupportFragmentManager() ,FicheArticleActivity.listDepot  ,FicheArticleActivity.tab_depot.getSelectedTabPosition());
                    viewPager.setAdapter(sectionsPagerAdapter);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            /*

            FicheArticlePagerAdapter   sectionsPagerAdapter = new FicheArticlePagerAdapter(activity , activity. getSupportFragmentManager() ,listDepot);
            viewPager.setAdapter(sectionsPagerAdapter);
            tabs_depot.setupWithViewPager(viewPager);

            */

        }


    }


}
