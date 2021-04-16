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
import com.example.suivieadministratif.adapter.PortfeuilleClientChequeAdapterLV;
import com.example.suivieadministratif.model.EcheanceClient;
import com.example.suivieadministratif.model.PortfeuilleClient;
import com.example.suivieadministratif.module.tresorerie.PortFeuilleChequeActivity;
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

public class PortfeuilleClientChequeTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv_prtf_client;
    ProgressBar pb;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String  CodeClientSelected   ;

    String NomUtilisateur;

    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<PortfeuilleClient> listPrtfeilleClient= new ArrayList<>();

    double   tot_echeance =0  ;

    public PortfeuilleClientChequeTask(Activity activity,  ListView lv_prtf_client, ProgressBar pb , String CodeClientSelected  ) {
        this.activity = activity;

        this.lv_prtf_client = lv_prtf_client;
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
                    condition += "  and CodeTiers =  '"+CodeClientSelected+"' "  ;
                }


                String query_echeance_client = " select  NumeroReglementClient  ,  Echeance  , CodeTiers as CodeClient , Vue_CaisseRecette.RaisonSociale,  NumeroPiece,Reference , Solde as Montant ,\n" +
                        "Banque.RaisonSociale as Banque \n" +
                        " from Vue_CaisseRecette \n" +
                        " inner join Banque on Banque.CodeBanque=Vue_CaisseRecette.CodeBanque \n" +
                        "where  Vue_CaisseRecette.CodeModeReglement='C' \n" +
                        "and Vue_CaisseRecette.Libelle='Reception Regelement' "+condition + "  order   by   Echeance  desc   ";


                Log.e("query_echeance_client",""+query_echeance_client) ;
                PreparedStatement ps = con.prepareStatement(query_echeance_client);
                ResultSet rs = ps.executeQuery();


                while (rs.next()) {
                    String NumeroReglementClient = rs.getString("NumeroReglementClient");
                    Date Echeance = dtfSQL.parse(rs.getString("Echeance"));

                    String CodeClient = rs.getString("CodeClient");
                    String RaisonSociale = rs.getString("RaisonSociale");
                    String NumeroPiece = rs.getString("NumeroPiece");
                    String Reference = rs.getString("Reference");
                    double Montant = rs.getDouble("Montant");
                    String Banque = rs.getString("Banque");


                    PortfeuilleClient portfeuilleClient  = new PortfeuilleClient(NumeroReglementClient ,Echeance ,CodeClient ,RaisonSociale ,NumeroPiece  ,Reference ,Montant ,Banque) ;
                    listPrtfeilleClient.add(portfeuilleClient) ;

                    tot_echeance =tot_echeance+Montant ;

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

        PortfeuilleClientChequeAdapterLV   adapterLV  = new PortfeuilleClientChequeAdapterLV(activity  , listPrtfeilleClient  ,"Chèque") ;
        lv_prtf_client.setAdapter(adapterLV);



        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);
        PortFeuilleChequeActivity.txt_tot_portfeuille.setText(instance.format(tot_echeance));

    }
}