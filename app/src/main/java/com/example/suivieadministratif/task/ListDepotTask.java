package com.example.suivieadministratif.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.model.Depot;
import com.example.suivieadministratif.module.Stock.FicheArticle.FicheArticleActivity;
import com.example.suivieadministratif.module.Stock.FicheArticle.ui.main.FicheArticlePagerAdapter;
import com.example.suivieadministratif.param.Param;
import com.google.android.material.tabs.TabLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class ListDepotTask extends AsyncTask<String, String, String> {

    String res;

    AppCompatActivity activity ;

    TabLayout tabs_depot ;
    Spinner sp_article ;
    ViewPager viewPager ;
    String  Origine  ;
    ConnectionClass connectionClass ;
    String user, password, base, ip ;


    ArrayList<Depot> listDepot = new ArrayList<>();


    public ListDepotTask(AppCompatActivity activity , TabLayout tabs_depot , Spinner sp_article , ViewPager viewPager , String  Origine  ) {
        this.activity = activity;
        this.tabs_depot=tabs_depot  ;
        this.sp_article=sp_article  ;
        this.viewPager=viewPager ;
        this.Origine=Origine;


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

                String query = " select  distinct  Depot.CodeDepot , Libelle from   Depot  \n" ;



                Log.e("query_depot", query );

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (Origine.equals("FicheArticleActivity"))
                {
                    FicheArticleActivity.listDepot.clear(); ;

                }
                while (rs.next()) {

                    String CodeDepot = rs.getString("CodeDepot");
                    String Libelle = rs.getString("Libelle");


                    Depot  depot  = new Depot(CodeDepot  , Libelle);


                    if (Origine.equals("FicheArticleActivity"))
                    {
                        FicheArticleActivity.listDepot.add(depot) ;

                    }
                    else
                        listDepot.add(depot) ;
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

         if (Origine.equals("FicheArticleActivity"))
        {

            FicheArticlePagerAdapter sectionsPagerAdapter = new FicheArticlePagerAdapter ( activity , activity. getSupportFragmentManager() ,FicheArticleActivity.listDepot,0 );
            viewPager.setAdapter(sectionsPagerAdapter);
            tabs_depot.setupWithViewPager(viewPager);

        }


    }


}
