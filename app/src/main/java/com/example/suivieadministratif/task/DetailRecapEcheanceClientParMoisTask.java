package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.DetailRecapEcheanceClientAdapterLV;
import com.example.suivieadministratif.adapter.RecapEcheanceClientAdapter;
import com.example.suivieadministratif.model.DetailRecapEcheanceClient;
import com.example.suivieadministratif.model.RecapEcheancierClient;
import com.example.suivieadministratif.module.tresorerie.DetailRecapEcheancierClientParMois;
import com.example.suivieadministratif.module.tresorerie.RecapEcheancierClientActivity;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DetailRecapEcheanceClientParMoisTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv;
    ProgressBar pb;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String  mois  ;
    String  annee  ;


    String CodeModeReg;
    String date_debut, date_fin;


    ArrayList<DetailRecapEcheanceClient> listDetailRecapEcheanceClient = new ArrayList<>();
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    double  total =0 ;


    public DetailRecapEcheanceClientParMoisTask(Activity activity, String  annee  ,String  mois  ,String date_debut, String date_fin, String CodeModeReg, ListView lv, ProgressBar pb) {
        this.activity = activity;
        this.annee = annee  ;
        this.mois = mois ;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.lv = lv;
        this.pb = pb;
        this.CodeModeReg = CodeModeReg;


        SharedPreferences prefe = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);


        Log.e("RETENU", Param.PEF_SERVER + "-" + ip + "-" + base);


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
                String query  = "" ;
                if (CodeModeReg.equals("C+T"))

                {
                    query =  " select ModeReglement.Libelle as ModeReglement ,   NumeroReglementClient  ,Echeance  , CodeTiers  ,Vue_CaisseRecette.RaisonSociale ,Banque.RaisonSociale as Banque , Reference , Solde  \n" +
                            "from Vue_CaisseRecette \n" +
                            "inner join Banque on Banque.CodeBanque=Vue_CaisseRecette.CodeBanque \n" +
                            "inner join ModeReglement on ModeReglement.CodeModeReglement=Vue_CaisseRecette.CodeModeReglement \n" +
                            "where Vue_CaisseRecette.CodeModeReglement  in ('C' , 'T' )\n" +
                            "and Echeance between '"+date_debut+"' and '"+date_fin+"'\n" +
                            " and ((Vue_CaisseRecette.Libelle = 'Reception Regelement' and CodeCompte <> '') \n" +
                            "or (Vue_CaisseRecette.Libelle ='Règlement Client'))\n" +
                            "and  YEAR  ( Echeance  ) =     "+annee+"   and   MONTH   ( Echeance  )   = "+mois+"  \n " +
                            "order by Echeance , NumeroReglementClient";
                }
                else
                {
                    query =  " select ModeReglement.Libelle as ModeReglement  ,   NumeroReglementClient  ,Echeance  , CodeTiers  ,Vue_CaisseRecette.RaisonSociale ,Banque.RaisonSociale as Banque , Reference , Solde  \n" +
                            "from Vue_CaisseRecette \n" +
                            "inner join Banque on Banque.CodeBanque=Vue_CaisseRecette.CodeBanque \n" +
                            "inner join ModeReglement on ModeReglement.CodeModeReglement=Vue_CaisseRecette.CodeModeReglement \n" +
                            "where Vue_CaisseRecette.CodeModeReglement   ='"+CodeModeReg+"' \n" +
                            "and Echeance between '"+date_debut+"' and '"+date_fin+"'\n" +
                            " and ((Vue_CaisseRecette.Libelle = 'Reception Regelement' and CodeCompte <> '') \n" +
                            "or (Vue_CaisseRecette.Libelle ='Règlement Client'))\n" +
                            "and  YEAR  ( Echeance  ) =   "+annee+"  and   MONTH   ( Echeance  )   = "+mois+" \n" +
                            "order by Echeance , NumeroReglementClient";

                }


                Log.e("query_detail_recap_ech", "" + query);
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();

                total =0  ;
                while (rs.next()) {

                    String ModeReglement = rs.getString("ModeReglement");
                    String NumeroReglementClient = rs.getString("NumeroReglementClient");

                    Date Echeance  =dtfSQL.parse(  rs.getString("Echeance"));
                    String CodeClient= rs.getString("CodeTiers");
                    String RaisonSociale = rs.getString("RaisonSociale");
                    String Banque = rs.getString("Banque");
                    String Reference = rs.getString("Reference");

                    double Solde = rs.getDouble("Solde");
                    total+= Solde ;


                    DetailRecapEcheanceClient detail  = new DetailRecapEcheanceClient(ModeReglement ,NumeroReglementClient,Echeance  ,CodeClient  ,RaisonSociale, Banque ,Reference ,Solde) ;

                    listDetailRecapEcheanceClient.add(detail) ;

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

        DetailRecapEcheanceClientAdapterLV adapter   = new DetailRecapEcheanceClientAdapterLV(activity , listDetailRecapEcheanceClient ,"Client") ;
        lv.setAdapter(adapter);


        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);

      DetailRecapEcheancierClientParMois.txt_total.setText(instance.format(total) + " DT");



    }


}