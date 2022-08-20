package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.EcheanceClientAdapterLV;
import com.example.suivieadministratif.adapter.ImpayeClientAdapterLV;
import com.example.suivieadministratif.adapter.ImpayeClientExtensibleAdapter;
import com.example.suivieadministratif.model.EcheanceClient;
import com.example.suivieadministratif.model.ImpayeClient;
import com.example.suivieadministratif.model.ImpayeClientEntete;
import com.example.suivieadministratif.module.tresorerie.ImpayeClientActivity;
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

public class ImpayeClientTask extends AsyncTask<String, String, String> {


    Activity activity;

    String date_debut, date_fin;
    String CodeClient, CodeBanque, CodeModeReg;
    String ConditionEtat;


    ExpandableListView elv_impaye_client ;
    ProgressBar pb;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;


    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<ImpayeClient> listImpayeClient = new ArrayList<>();

    double tot_impayer = 0;
    double tot_reste_a_payer = 0;

    public ImpayeClientTask(Activity activity, String date_debut, String date_fin, String codeClient, String codeBanque, String codeModeReg, String conditionEtat,  ExpandableListView elv_impaye_client , ProgressBar pb) {
        this.activity = activity;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        CodeClient = codeClient;
        CodeBanque = codeBanque;
        CodeModeReg = codeModeReg;
        ConditionEtat = conditionEtat;
        this.elv_impaye_client = elv_impaye_client;
        this.pb = pb;


        SharedPreferences prefe = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);

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

                String condition = "";
                if (!CodeClient.equals("")) {
                    condition += "\n  and CodeClient = '" + CodeClient + "' ";
                }


                if (!CodeBanque.equals("")) {
                    condition += "\n  and CodeBanque = '" + CodeBanque + "' ";
                }


                if (CodeModeReg.equals("C+T")) {
                    condition += "\n  and   Vue_ContentieuxEtat.CodeModeReglement  in  ('C','T') ";
                } else {
                    condition += "\n and Vue_ContentieuxEtat.CodeModeReglement  = '" + CodeModeReg + "' ";
                }

                condition += "\n and DateImpayeClient  between  '" + date_debut + "'  and   '" + date_fin + "'   ";

                if (!ConditionEtat.isEmpty()) {
                    condition += "\n and Vue_ContentieuxEtat.NumeroEtat     " + ConditionEtat;
                }


                String query_echeance_client = " select  DateImpayeClient  ,  CodeClient , Client , ModeReglement.CodeModeReglement , ModeReglement.Libelle as ModeReg  , CodeBanque , Vue_ContentieuxEtat.Libelle as Banque\n" +
                        ", Reference  ,Echeance  , MontantImpayeClient , (MontantImpayeClient - MontantRecu ) as ResteAPayer \n" +
                        ", Vue_ContentieuxEtat.NumeroEtat , Etat.Libelle as Etat\n" +
                        "\n" +
                        "from  Vue_ContentieuxEtat \n" +
                        "INNER JOIN ModeReglement on ModeReglement.CodeModeReglement = Vue_ContentieuxEtat.CodeModeReglement\n" +
                        "INNER JOIN Etat  on Etat.NumeroEtat  = Vue_ContentieuxEtat.NumeroEtat\n" +
                        "where 1=1 " + condition + "\n   order by  CodeClient ";


                Log.e("query_impaye_client", "" + query_echeance_client);
                PreparedStatement ps = con.prepareStatement(query_echeance_client);
                ResultSet rs = ps.executeQuery();


                while (rs.next()) {

                    Date DateImpayeClient = dtfSQL.parse(rs.getString("DateImpayeClient"));

                    String CodeClient = rs.getString("CodeClient");
                    String RaisonSociale = rs.getString("Client");
                    String CodeModeReglement = rs.getString("CodeModeReglement");
                    String ModeReg = rs.getString("ModeReg");

                    String CodeBanque = rs.getString("CodeBanque");
                    String Banque = rs.getString("Banque");

                    String Reference = rs.getString("Reference");
                    Date Echeance = dtfSQL.parse(rs.getString("Echeance"));


                    double MontantImpayeClient = rs.getDouble("MontantImpayeClient");
                    double ResteAPayer = rs.getDouble("ResteAPayer");

                    String NumeroEtat = rs.getString("NumeroEtat");
                    String Etat = rs.getString("Etat");


                    ImpayeClient impayeClient = new ImpayeClient(DateImpayeClient, CodeClient, RaisonSociale, ModeReg, Banque, Reference, Echeance, MontantImpayeClient, ResteAPayer, Etat);
                    listImpayeClient.add(impayeClient);

                    tot_impayer += MontantImpayeClient;
                    tot_reste_a_payer += ResteAPayer;

                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
            Log.e("ERROR_impaye_client", "" + ex.getMessage().toString());
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);


        ArrayList<String> client_distinct = new ArrayList<>();
        ArrayList<ImpayeClientEntete> list_impaye_client = new ArrayList<>();

        for (ImpayeClient ic : listImpayeClient) {

            if (!client_distinct.contains(ic.getCodeClient()))
                client_distinct.add(ic.getCodeClient());
        }

        for (String code_client : client_distinct) {
            ImpayeClientEntete  ic_entete  = new ImpayeClientEntete() ;
            String  raison  = "" ;
            ArrayList<ImpayeClient>  list_imp_par_client  = new ArrayList<>() ;
            double  mnt_imp =0  ;
            double  mnt_reste_a_payer  =0  ;

            for (ImpayeClient   ic  : listImpayeClient)
            {
                if (ic.getCodeClient().equals(code_client))
                {
                    raison = ic.getRaisonSociale()  ;
                    mnt_imp+= ic.getMontantImpaye() ;
                    mnt_reste_a_payer+=ic.getResteAPayer() ;
                    list_imp_par_client.add(ic) ;
                }

            }

            ic_entete.setCodeClient(code_client);
            ic_entete.setRaisonSociale(raison);
            ic_entete.setMontantImpaye(mnt_imp);
            ic_entete.setResteAPayer(mnt_reste_a_payer);

            ic_entete.setListImpayeClient(list_imp_par_client);

            list_impaye_client.add(ic_entete) ;

        }


        ImpayeClientExtensibleAdapter  adapterLV = new ImpayeClientExtensibleAdapter(activity, list_impaye_client);
        elv_impaye_client.setAdapter(adapterLV);

        final NumberFormat format = NumberFormat.getNumberInstance(Locale.FRENCH);
        format.setMinimumFractionDigits(3);
        format.setMaximumFractionDigits(3);

        ImpayeClientActivity.txt_reste_a_payer.setText(format.format(tot_reste_a_payer));
        ImpayeClientActivity.txt_total_impaye.setText(format.format(tot_impayer));


    }
}