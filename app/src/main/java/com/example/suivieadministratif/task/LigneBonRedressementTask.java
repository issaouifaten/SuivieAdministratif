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
import com.example.suivieadministratif.adapter.LigneBLAdapter;
import com.example.suivieadministratif.adapter.LigneBRedressAdapter;
import com.example.suivieadministratif.model.LigneBonLivraisonVente;
import com.example.suivieadministratif.model.LigneBonRedressement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LigneBonRedressementTask extends AsyncTask <String, String, String> {


    Activity activity;

    ListView lv_list_ligne;
    String NumeroBR ;
    ProgressBar pb;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String NomUtilisateur="" ;
    DateFormat dtfSQL    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    ArrayList<LigneBonRedressement> listLigneBonRedressement = new ArrayList<>() ;

    public LigneBonRedressementTask(Activity activity , ListView lv_list_ligne , String NumeroBR , ProgressBar pb) {
        this.activity = activity;
        this.lv_list_ligne = lv_list_ligne;
        this.NumeroBR = NumeroBR  ;
        this.pb = pb;


        SharedPreferences prefe = activity.getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);


        SharedPreferences pref=activity.getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt=pref.edit();
        NomUtilisateur= pref.getString("NomUtilisateur",NomUtilisateur);

        connectionClass = new ConnectionClass();

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pb.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {
        try {

            Connection con = connectionClass.CONN(ip, password, user, base);
            if (con == null) {
                z = "Error in connection with SQL server";
            } else {
                String queryAchat = " select  NumeroBonRedressement , CodeArticle  , DesignationArticle , PrixAchatHT , MontantTVA   , MontantTTC   , Quantite   \n" +
                        " from   LigneBonRedressement   where NumeroBonRedressement = '"+NumeroBR+"'  " ;

                PreparedStatement ps = con.prepareStatement(queryAchat);
                ResultSet rs = ps.executeQuery();

                Log.e("query_achat",queryAchat);

                while (rs.next()) {

                    String NumeroBonRedressement = rs.getString("NumeroBonRedressement");
                    String CodeArticle = rs.getString("CodeArticle");
                    String DesignationArticle = rs.getString("DesignationArticle");

                    double PrixAchatHT = rs.getDouble("PrixAchatHT");
                    double MontantTVA = rs.getDouble("MontantTVA");
                    double MontantTTC = rs.getDouble("MontantTTC");


                    int Quantite = rs.getInt("Quantite");


                    LigneBonRedressement  lbr  = new LigneBonRedressement(NumeroBonRedressement , CodeArticle,DesignationArticle  ,PrixAchatHT ,MontantTVA, MontantTTC ,Quantite) ;
                    listLigneBonRedressement.add(lbr) ;

                }


                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";

        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);

        LigneBRedressAdapter ligneBLAdapter  = new LigneBRedressAdapter(activity  , listLigneBonRedressement) ;
        lv_list_ligne.setAdapter(ligneBLAdapter);

    }



}