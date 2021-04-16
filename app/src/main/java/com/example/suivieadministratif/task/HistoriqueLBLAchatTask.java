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
import com.example.suivieadministratif.model.LigneBonLivraisonVente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HistoriqueLBLAchatTask extends AsyncTask <String, String, String> {


    Activity activity;
    ListView lv_list_lbl;
    String NumeroBL ;
    ProgressBar pb;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String NomUtilisateur="" ;
    DateFormat dtfSQL    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    ArrayList<LigneBonLivraisonVente> listLigneBonLivraisonVente = new ArrayList<>() ;

    public HistoriqueLBLAchatTask(Activity activity , ListView lv_list_lbl , String NumeroBL , ProgressBar pb) {
        this.activity = activity;
        this.lv_list_lbl = lv_list_lbl;
        this.NumeroBL = NumeroBL  ;
        this.pb = pb;


        SharedPreferences prefe = activity.getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
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
                String queryAchat = " select \n" +
                        "NumeroBonLivraisonAchat  , CodeArticle  , Quantite   , MontantTTC ,  NetHT  ,  TauxRemise ,    DesignationArticle \n" +
                        " from  LigneBonLivraisonAchat    where NumeroBonLivraisonAchat  = '"+NumeroBL+"'   " ;

                PreparedStatement ps = con.prepareStatement(queryAchat);
                ResultSet rs = ps.executeQuery();

                Log.e("query_achat",queryAchat);

                while (rs.next()) {

                    String NumeroBonLivraisonVente = rs.getString("NumeroBonLivraisonAchat");
                    String CodeArticle = rs.getString("DesignationArticle");
                    int Quantite = rs.getInt("Quantite");

                    double NetHT = rs.getDouble("NetHT");
                    double TauxRemise = rs.getDouble("TauxRemise");
                    double MontantTTC = rs.getDouble("MontantTTC");

                    LigneBonLivraisonVente lbl  = new LigneBonLivraisonVente(NumeroBonLivraisonVente , CodeArticle ,Quantite ,NetHT ,TauxRemise, MontantTTC) ;
                    listLigneBonLivraisonVente.add(lbl) ;


                }


                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";

            Log.e("Error_ACHAT" ,ex.getMessage().toString()) ;

        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);

        LigneBLAdapter ligneBLAdapter  = new LigneBLAdapter(activity  , listLigneBonLivraisonVente) ;
        lv_list_lbl.setAdapter(ligneBLAdapter);

    }



}