package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.LigneBCAdapter;
import com.example.suivieadministratif.adapter.LigneBLAdapter;
import com.example.suivieadministratif.model.LigneBonCommandeVente;
import com.example.suivieadministratif.model.LigneBonLivraisonVente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HistoriqueLBLTask extends AsyncTask <String, String, String> {


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

    public HistoriqueLBLTask(Activity activity , ListView lv_list_lbl , String NumeroBL , ProgressBar pb) {
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

                String queryClient = "  select \n" +
                        "NumeroBonLivraisonVente  , CodeArticle  ,Quantite   , MontantTTC ,DesignationArticle\n" +
                        " from  LigneBonLivraisonVente    where NumeroBonLivraisonVente  = '"+NumeroBL+"'   " ;

                PreparedStatement ps = con.prepareStatement(queryClient);
                ResultSet rs = ps.executeQuery();



                while (rs.next()) {

                    String NumeroBonLivraisonVente = rs.getString("NumeroBonLivraisonVente");
                    String CodeArticle = rs.getString("DesignationArticle");
                    int Quantite = rs.getInt("Quantite");
                    double MontantTTC = rs.getDouble("MontantTTC");

                    LigneBonLivraisonVente lbl  = new LigneBonLivraisonVente(NumeroBonLivraisonVente , CodeArticle ,Quantite , MontantTTC) ;
                    listLigneBonLivraisonVente.add(lbl) ;

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

        LigneBLAdapter ligneBLAdapter  = new LigneBLAdapter(activity  , listLigneBonLivraisonVente) ;
        lv_list_lbl.setAdapter(ligneBLAdapter);

    }



}