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
import com.example.suivieadministratif.adapter.EcheanceClientAdapterLV;
import com.example.suivieadministratif.model.EcheanceClient;
import com.example.suivieadministratif.module.vente.RapportEcheanceClientActivity;
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

public class EcheanceClientTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv_echeance_client;
    ProgressBar pb;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String  CodeClientSelected   ;

    String NomUtilisateur;
    Date  date_debut , date_fin  ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<EcheanceClient> listEcheanceClient = new ArrayList<>();

    double   tot_echeance =0  ;

    public EcheanceClientTask(Activity activity, Date  date_debut , Date date_fin  , ListView lv_echeance_client, ProgressBar pb , String CodeClientSelected  ) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.lv_echeance_client = lv_echeance_client;
        this.pb = pb;

       this.CodeClientSelected = CodeClientSelected;


        SharedPreferences prefe = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);


        Log.e("BON_CMD" ,Param.PEF_SERVER +"-"+ip+"-"+base) ;



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

                String  condition  = "" ;
                if (!CodeClientSelected.equals(""))
                {
                    condition += "  and CodeClient=   '"+CodeClientSelected+"' "  ;
                }


                String query_echeance_client = " select CodeClient  , RaisonSociale , Libelle   ,Porteur  ,NumeroReglementClient  ,Reference  , MontantRecu , Echeance , NomUtilisateurActuel  \n" +
                        "from     Vue_EcheancierClient  \n" +
                        "where Echeance between  '"+df.format(date_debut)+"' and '"+df.format(date_fin)+"'   "+condition;


                Log.e("query_echeance_client",""+query_echeance_client) ;
                PreparedStatement ps = con.prepareStatement(query_echeance_client);
                ResultSet rs = ps.executeQuery();


                while (rs.next()) {

                    String CodeClient = rs.getString("CodeClient");
                    String RaisonSociale = rs.getString("RaisonSociale");
                    String Libelle = rs.getString("Libelle");
                    String Porteur = rs.getString("Porteur");
                    String NumeroReglementClient = rs.getString("NumeroReglementClient");
                    String Reference = rs.getString("Reference");
                    double MontantRecu = rs.getDouble("MontantRecu");
                    Date Echeance = dtfSQL.parse(rs.getString("Echeance"));
                    String NomUtilisateurActuel = rs.getString("NomUtilisateurActuel");

                    EcheanceClient  echeanceClient  = new EcheanceClient(CodeClient  ,RaisonSociale ,Libelle ,Porteur ,NumeroReglementClient ,Reference ,MontantRecu ,Echeance ,NomUtilisateurActuel) ;
                    listEcheanceClient.add(echeanceClient) ;

                    tot_echeance =tot_echeance+MontantRecu ;

                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
            Log.e("ERROR_msv" ,""+ex.getMessage().toString()) ;
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);


        EcheanceClientAdapterLV adapterLV = new EcheanceClientAdapterLV(activity, listEcheanceClient);
        lv_echeance_client.setAdapter(adapterLV);


        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);
        RapportEcheanceClientActivity.txt_tot_ttc.setText(instance.format(tot_echeance));

    }
}