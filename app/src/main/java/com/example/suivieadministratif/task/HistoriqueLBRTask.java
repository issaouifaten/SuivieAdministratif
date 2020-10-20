package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.LigneBLAdapter;
import com.example.suivieadministratif.adapter.LigneBRAdapter;
import com.example.suivieadministratif.model.LigneBonLivraisonVente;
import com.example.suivieadministratif.model.LigneBonRetourVente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HistoriqueLBRTask extends AsyncTask <String, String, String> {


    Activity activity;

    ListView lv_list_lbr;
    String NumeroBR ;
    ProgressBar pb;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String NomUtilisateur="" ;
    DateFormat dtfSQL    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    ArrayList<LigneBonRetourVente> listLigneBonRetourVente = new ArrayList<>() ;

    public HistoriqueLBRTask(Activity activity , ListView lv_list_lbr , String NumeroBR , ProgressBar pb) {
        this.activity = activity;
        this.lv_list_lbr = lv_list_lbr;
        this.NumeroBR = NumeroBR  ;
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

                String queryClient = "  select \n" +
                        "NumeroBonRetourVente  , CodeArticle  ,Quantite   , MontantTTC \n" +
                        " from  LigneBonRetourVente    where NumeroBonRetourVente  = '"+NumeroBR+"'   " ;

                PreparedStatement ps = con.prepareStatement(queryClient);
                ResultSet rs = ps.executeQuery();



                while (rs.next()) {

                    String NumeroBonRetourVente = rs.getString("NumeroBonRetourVente");
                    String CodeArticle = rs.getString("CodeArticle");
                    int Quantite = rs.getInt("Quantite");
                    double MontantTTC = rs.getDouble("MontantTTC");

                    LigneBonRetourVente lbr  = new LigneBonRetourVente(NumeroBonRetourVente , CodeArticle ,Quantite , MontantTTC) ;
                    listLigneBonRetourVente.add(lbr) ;

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

        LigneBRAdapter ligneBRAdapter  = new LigneBRAdapter(activity  , listLigneBonRetourVente) ;
        lv_list_lbr.setAdapter(ligneBRAdapter);

    }



}