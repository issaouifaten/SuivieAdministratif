package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.SpinnerAdapter;
import com.example.suivieadministratif.adapter.SpinnerAnneMoisAdapter;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Graphique.VariationCAEnMoisActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListAnneeForSpinner extends AsyncTask<String, String, String> {

    Connection con;
    String res;

    Activity activity;
    Spinner sp_annee;
    Spinner sp_mois;

    String  param ;
    String origine;
    ArrayList<String> listAnnee = new ArrayList<>();


    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateFormat dfSQL = new SimpleDateFormat("dd/MM/yyyy");

    ConnectionClass connectionClass;
    String user, password, base, ip;


    public ListAnneeForSpinner(Activity activity, Spinner sp_annee, Spinner sp_mois,  String  param  ,String origine) {
        this.activity = activity;
        this.sp_annee = sp_annee;
        this.sp_mois = sp_mois;
        this.origine = origine;
        this.param = param  ;

        SharedPreferences prefe = activity.getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);


        connectionClass = new ConnectionClass();
    }


    //  lancement  de progress dialog
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //  txt_client.setHint("Patientez SVP ...");

    }


    //  donwnload  data
    @Override
    protected String doInBackground(String... strings) {


        try {

            Connection con = connectionClass.CONN(ip, password, user, base);       // Connect to database
            Log.e("con", "" + con);
            if (con == null) {
                res = "Check Your Internet Access ! ";
            } else {

                // AjoutDemandeInterventionAdmin

                String query = " \n" +
                        "select distinct YEAR(DatePiece) as annee from  dbo.Vue_ListeVenteGlobal\n" +
                        "   order by  annee  ";

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query);

                listAnnee.clear();
                listAnnee.add("Année");

                while (rs.next()) {

                    String annee = rs.getString("annee");
                    listAnnee.add(annee);

                }

                con.close();
            }

        } catch (Exception ex) {

            res = ex.getMessage();

        }

        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        SpinnerAnneMoisAdapter adapter = new SpinnerAnneMoisAdapter(activity, listAnnee);
        sp_annee.setAdapter(adapter);

        sp_annee.setSelection(listAnnee.size() - 1);

        if (origine.equals("Debut"))
        {
            VariationCAEnMoisActivity.annee_debut_selected = listAnnee.get( listAnnee.size() - 1) ;
        }
        else  if (origine .equals("Fin"))
        {
            VariationCAEnMoisActivity.annee_fin_selected = listAnnee.get( listAnnee.size() - 1) ;
        }

        sp_annee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (position > 0) {

                    String annee = listAnnee.get(position);


                    if (origine.equals("Debut"))
                    {
                        VariationCAEnMoisActivity.annee_debut_selected = listAnnee.get( position) ;
                    }
                    else  if (origine .equals("Fin"))
                    {
                        VariationCAEnMoisActivity.annee_fin_selected = listAnnee.get( position) ;
                    }

                    ListMoisForSpinner listMoisForSpinner = new ListMoisForSpinner(activity, sp_mois, annee ,param ,origine);
                    listMoisForSpinner.execute();

                    Log.e("selection_anne", annee);

                }
            }

            @Override
            public void onNothingSelected (AdapterView<?> adapterView) {

            }

        });

    }

}
