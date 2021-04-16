package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.TypeMvmntAdapterLV;
import com.example.suivieadministratif.model.Depot;
import com.example.suivieadministratif.model.TypeMouvement;
import com.example.suivieadministratif.model.TypeMouvementClick;
import com.example.suivieadministratif.module.Stock.EtatDeStockActivity;
import com.example.suivieadministratif.param.Param;
import com.google.android.material.tabs.TabLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListDepotForTabTask extends AsyncTask<String, String, String> {


    Activity activity;

    TabLayout tab_depot;
    ArrayList<Depot> listDepot = new ArrayList<>();

    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ListDepotForTabTask(Activity activity,  TabLayout tab_depot) {
        this.activity = activity;
        this. tab_depot=tab_depot ;

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

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);
            if (con == null) {
                z = "Error in connection with SQL server";
            } else {



                String query_depot = "  select  * from   Depot   ";


                Log.e("query_depot", "" + query_depot);
                PreparedStatement ps = con.prepareStatement(query_depot);
                ResultSet rs = ps.executeQuery();


                while (rs.next()) {

                    String CodeDepot = rs.getString("CodeDepot");
                    String Libelle = rs.getString("Libelle");

                    Depot  depot = new Depot(CodeDepot,Libelle) ;

                    listDepot.add(depot)    ;

                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
            Log.e("ERROR_type_mvmnt", "" + ex.getMessage().toString());
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {


        for (Depot   d  : listDepot)
        {

            tab_depot.addTab(tab_depot.newTab().setText(d.getLibelle()));

        }

        EtatDeStockActivity.CodeDepotSelected=   listDepot.get(0).getCodeDepot() ;
        EtatDeStockActivity.LibelleDepotSelected =   listDepot.get(0).getLibelle() ;


        tab_depot.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int  index =   tab.getPosition() ;
                EtatDeStockActivity.CodeDepotSelected=   listDepot.get(index).getCodeDepot() ;
                EtatDeStockActivity.LibelleDepotSelected =   listDepot.get(index).getLibelle() ;

                StockArticleTask stockArticleTask  = new StockArticleTask(activity , EtatDeStockActivity.rv_list_article  ,EtatDeStockActivity.txt_recherche  ,EtatDeStockActivity.pb ,EtatDeStockActivity.CodeDepotSelected ,EtatDeStockActivity.LibelleDepotSelected ,EtatDeStockActivity.termRecherche)  ;
                stockArticleTask.execute() ;

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

}