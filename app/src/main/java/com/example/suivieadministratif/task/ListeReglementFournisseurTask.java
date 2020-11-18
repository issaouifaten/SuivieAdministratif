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
import com.example.suivieadministratif.adapter.ReglementClientAdapterLV;
import com.example.suivieadministratif.adapter.ReglementFournisseurAdapterLV;
import com.example.suivieadministratif.model.ReglementClient;
import com.example.suivieadministratif.model.ReglementFournisseur;
import com.example.suivieadministratif.module.reglementClient.ReglementClientActivity;
import com.example.suivieadministratif.module.reglementFournisseur.ReglementFournisseurActivity;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ListeReglementFournisseurTask extends AsyncTask<String, String, String> {

    String res;
    Activity activity;
    ListView lv_reglement_fournisseur ;
    ProgressBar pb_chargement ;

    Date  date_debut  , date_fin ;
    ConnectionClass connectionClass ;
    String user, password, base, ip;

    SimpleDateFormat  sdf  = new SimpleDateFormat("dd/MM/yyyy") ;
    DateFormat dtfSQL    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ArrayList <ReglementFournisseur>   listReglementFournisseur = new ArrayList<>() ;

    double total_reglement  =0 ;


    public ListeReglementFournisseurTask(Activity activity  , ListView lv_reglement_fournisseur , ProgressBar pb_chargement , Date  date_debut  , Date date_fin ) {
        this.activity  = activity  ;
        this.lv_reglement_fournisseur = lv_reglement_fournisseur  ;
        this.pb_chargement  = pb_chargement   ;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;


        SharedPreferences pref = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip   = pref.getString("ip", ip);
        base = pref.getString("base", base);
        password = pref.getString("password", password);

        connectionClass = new ConnectionClass();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
         pb_chargement.setVisibility(View.VISIBLE);
        total_reglement  =0 ;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);       // Connect to database
            Log.e("con", "" + con);
            if (con == null) {
                res = "Check Your Internet Access!";
            } else {


                String query = "select NumeroReglementFournisseur ,DateReglement  , RaisonSociale,TotalPayement ,NomUtilisateur  \n" +
                        "from  ReglementFournisseur\n" +
                        " where DateReglement between  '"+sdf.format(date_debut)+"' and '"+sdf.format(date_fin)+"' \n" ;

                Log.e("query_reg_frns", query);

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {


                    String NumeroReglementFournisseur  = rs.getString("NumeroReglementFournisseur");
                    Date DateReglement  =dtfSQL.parse( rs.getString("DateReglement"));
                    String RaisonSociale  = rs.getString("RaisonSociale");
                    Double TotalPayer  = rs.getDouble("TotalPayement");
                    String NomUtilisateur  = rs.getString("NomUtilisateur");


                    total_reglement  =  total_reglement + TotalPayer ;

                    ReglementFournisseur reglementFournisseur  = new  ReglementFournisseur (NumeroReglementFournisseur ,DateReglement ,RaisonSociale ,TotalPayer ,NomUtilisateur ) ;
                    listReglementFournisseur.add(reglementFournisseur) ;


                }
            }
            con.close();

        } catch (Exception ex) {

            res = ex.getMessage();
            Log.e("ERROR_frns", res.toString());

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

        pb_chargement.setVisibility(View.INVISIBLE);

        DecimalFormat  decF  = new DecimalFormat("0.000") ;

        ReglementFournisseurAdapterLV adapterLV = new ReglementFournisseurAdapterLV(activity , listReglementFournisseur) ;
        lv_reglement_fournisseur.setAdapter(adapterLV);

        ReglementFournisseurActivity.txt_tot_reglement.setText(decF.format(total_reglement) +" Dt");
      /*  MouvementCaisseDepenseDetailActivity.txt_tot_depense.setText(decF.format(total_depense) +" Dt");
        MouvementDepenseAdapterLV adapter  = new MouvementDepenseAdapterLV(activity  , listMvntCaisse) ;
        lv_list_mvmnt_caisse.setAdapter(adapter);*/


    }







}
