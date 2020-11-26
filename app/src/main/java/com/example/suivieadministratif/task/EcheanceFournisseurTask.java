package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.EcheanceFournisseurAdapterLV;
import com.example.suivieadministratif.model.EcheanceClient;
import com.example.suivieadministratif.model.EcheanceFournisseur;
import com.example.suivieadministratif.module.reglementFournisseur.RapportEcheanceFournisseurActivity;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EcheanceFournisseurTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv_echeance_fournisseur;
    ProgressBar pb;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String NomUtilisateur;
    Date  date_debut , date_fin  ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<EcheanceFournisseur> listEcheanceFournisseurs = new ArrayList<>();

    double tot_echeance_frns  =0 ;
    public EcheanceFournisseurTask(Activity activity, Date  date_debut , Date date_fin  , ListView lv_echeance_fournisseur, ProgressBar pb ) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.lv_echeance_fournisseur = lv_echeance_fournisseur;
        this.pb = pb;

        SharedPreferences prefe = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);


        Log.e("BON_CMD" ,Param.PEF_SERVER +"-"+ip+"-"+base) ;

        /*SharedPreferences pref=activity.getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt=pref.edit();
        NomUtilisateur= pref.getString("NomUtilisateur",NomUtilisateur);*/

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

                String query_echeance_frns = "    DECLARE\t@return_value int\n" +
                        "\n" +
                        "EXEC\t@return_value = [dbo].[EcheanceFournisseur]\n" +
                        "\t\t@DateDebut = '"+df.format(date_debut)+"',\n" +
                        "\t\t@DateFin = '"+df.format(date_fin)+"'\n" +
                        "\n" +
                        "SELECT\t'Return Value' = @return_value";



                     /*   " select CodeClient  , RaisonSociale , Libelle   ,Porteur  ,NumeroReglementClient  ,Reference  , MontantRecu , Echeance , NomUtilisateurActuel  \n" +
                        "from  Vue_EcheancierClient  \n" +
                        "where Echeance between  '"+df.format(date_debut)+"' and '"+df.format(date_fin)+"' and Stockable=0  ";*/


                Log.e("query_echeance_frns",""+query_echeance_frns) ;
                PreparedStatement ps = con.prepareStatement(query_echeance_frns);
                ResultSet rs = ps.executeQuery();


                while (rs.next()) {

                    String LibelleCompte = rs.getString("LibelleCompte");
                    String Libelle = rs.getString("Libelle");
                    String NumeroReglementFournisseur = rs.getString("NumeroReglementFournisseur");

                    String CodeFournisseur = rs.getString("CodeFournisseur");
                    String CodeCompte = rs.getString("CodeCompte");
                    String Reference = rs.getString("Reference");
                    Date Echeance = dtfSQL.parse(rs.getString("Echeance"));
                    double MontantRecu = rs.getDouble("MontantRecu");

                    String CodeModeReglement = rs.getString("CodeModeReglement");
                    String RaisonSociale = rs.getString("RaisonSociale");
                    Date DateReglement = dtfSQL.parse(rs.getString("DateReglement"));


                    tot_echeance_frns = tot_echeance_frns + MontantRecu ;
                    EcheanceFournisseur echeanceFournisseur  = new EcheanceFournisseur(LibelleCompte  ,Libelle ,NumeroReglementFournisseur ,CodeFournisseur ,CodeCompte ,Reference  ,Echeance ,MontantRecu ,CodeModeReglement ,RaisonSociale ,DateReglement) ;
                    listEcheanceFournisseurs.add(echeanceFournisseur) ;

                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
            Log.e("ERROR_ech_frns_task" ,""+ex.getMessage().toString()) ;
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);

        EcheanceFournisseurAdapterLV  echeanceFournisseurAdapterLV = new EcheanceFournisseurAdapterLV(activity  ,listEcheanceFournisseurs) ;
        lv_echeance_fournisseur.setAdapter(echeanceFournisseurAdapterLV);

        DecimalFormat decF = new DecimalFormat("0.000");
        RapportEcheanceFournisseurActivity.txt_tot_echeance.setText(decF.format(tot_echeance_frns));

    }

}