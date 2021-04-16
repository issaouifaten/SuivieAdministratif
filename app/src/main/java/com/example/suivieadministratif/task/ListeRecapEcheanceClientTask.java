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

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.RecapEcheanceClientAdapter;
import com.example.suivieadministratif.adapter.RetenuClientAdapter;
import com.example.suivieadministratif.model.RecapEcheancierClient;
import com.example.suivieadministratif.model.RetenuClientFournisseur;
import com.example.suivieadministratif.module.tresorerie.DetailRecapEcheancierClientParMois;
import com.example.suivieadministratif.module.tresorerie.RecapEcheancierClientActivity;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.ListRetenuClientActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ListeRecapEcheanceClientTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv;
    ProgressBar pb;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String CodeModeReg;
    String date_debut, date_fin;


    ArrayList<RecapEcheancierClient> listRecapEcheancierClient = new ArrayList<>();

    double  total =0 ;


    public ListeRecapEcheanceClientTask(Activity activity, String date_debut, String date_fin, String CodeModeReg, ListView lv, ProgressBar pb) {
        this.activity = activity;
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
                    query =  " select  YEAR  ( Echeance  )  as annee ,MONTH   ( Echeance  ) as mois , SUM ( Solde  )   as TotalMntEcheance \n" +
                            " from Vue_CaisseRecette where CodeModeReglement in ('C' , 'T' ) \n" +
                            "and Echeance between '"+date_debut+"' and '"+date_fin+"'\n" +
                            "and ((Libelle = 'Reception Regelement' and CodeCompte <> '') \n" +
                            "or (Libelle ='Règlement Client'))\n" +
                            "group by   YEAR  ( Echeance  )  , MONTH   ( Echeance  )  \n" +
                            "order  by YEAR  (Echeance),MONTH   (Echeance) ";
                }
                else
                {
                    query =  " select  YEAR  ( Echeance  )  as annee ,MONTH   ( Echeance  ) as mois , SUM ( Solde  )   as TotalMntEcheance \n" +
                            " from Vue_CaisseRecette where CodeModeReglement  = '"+CodeModeReg+"' \n" +
                            "and Echeance between '"+date_debut+"' and '"+date_fin+"'\n" +
                            "and ((Libelle = 'Reception Regelement' and CodeCompte <> '') \n" +
                            "or (Libelle ='Règlement Client'))\n" +
                            "group by   YEAR  ( Echeance  )  , MONTH   ( Echeance  )  \n" +
                            "order  by YEAR  (Echeance),MONTH   (Echeance) ";

                }




                Log.e("query_retenu", "" + query);
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();

                total =0  ;
                while (rs.next()) {

                    String annee = rs.getString("annee");
                    String mois = rs.getString("mois");

                    String Libellemois = "";

                    switch (mois) {
                        case "1":
                            Libellemois = "Janvier";
                            break;
                        case "2":
                            Libellemois = "Février";
                            break;
                        case "3":
                            Libellemois = "Mars";
                            break;
                        case "4":
                            Libellemois = "Avril";
                            break;
                        case "5":
                            Libellemois = "Mai";
                            break;
                        case "6":
                            Libellemois = "Juin";
                            break;
                        case "7":
                            Libellemois = "Juillet";
                            break;
                        case "8":
                            Libellemois = "Août";
                            break;
                        case "9":
                            Libellemois = "Septembre";
                            break;
                        case "10":
                            Libellemois = "Octobre";
                            break;
                        case "11":
                            Libellemois = "Novembre";
                            break;
                        case "12":
                            Libellemois = "Décembre";
                            break;

                    }


                    double TotalMntEcheance = rs.getDouble("TotalMntEcheance");

                    total+= TotalMntEcheance ;
                    RecapEcheancierClient recapEcheancierClient = new RecapEcheancierClient(annee, mois, Libellemois, TotalMntEcheance);
                    listRecapEcheancierClient.add(recapEcheancierClient) ;

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

        RecapEcheanceClientAdapter  adapter   = new RecapEcheanceClientAdapter(activity , listRecapEcheancierClient) ;
        lv.setAdapter(adapter);


        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);

        RecapEcheancierClientActivity.txt_total.setText(instance.format(total) + " DT");


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position , long l) {

                RecapEcheancierClient  recapSelected  =  listRecapEcheancierClient.get(position) ;

                Intent  toDetail  = new Intent(activity  , DetailRecapEcheancierClientParMois.class) ;


                toDetail.putExtra("annee" ,recapSelected.getAnnee());
                toDetail.putExtra("mois" ,recapSelected.getMois());
                toDetail.putExtra("libelle_mois" ,recapSelected.getLibelleMois());
                toDetail.putExtra("date_debut" ,date_debut );
                toDetail.putExtra("date_fin" ,date_fin);
                toDetail.putExtra("mode_reglement" ,CodeModeReg);



                activity.startActivity(toDetail);


            }
        });
    }


}