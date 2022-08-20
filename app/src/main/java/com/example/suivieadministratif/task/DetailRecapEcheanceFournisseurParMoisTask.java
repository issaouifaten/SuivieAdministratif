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
import com.example.suivieadministratif.adapter.DetailRecapEcheanceClientAdapterLV;
import com.example.suivieadministratif.model.DetailRecapEcheanceClient;
import com.example.suivieadministratif.module.tresorerie.DetailRecapEcheancierClientParMois;
import com.example.suivieadministratif.module.tresorerie.DetailRecapEcheancierFournisseurParMois;
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

public class DetailRecapEcheanceFournisseurParMoisTask extends AsyncTask<String, String, String> {


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


    public DetailRecapEcheanceFournisseurParMoisTask(Activity activity, String  annee  , String  mois  , String date_debut, String date_fin, String CodeModeReg, ListView lv, ProgressBar pb) {
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


                String CNDIDION = ""  ;
                if (CodeModeReg.equals("C+T"))

                {
                    CNDIDION = "T.CodeModeReglement in ('C' , 'T' ) "  ;

                }
                else
                {
                    CNDIDION = " T.CodeModeReglement = '"+CodeModeReg+"' " ;


                }


                String    query =  "  \n" +
                        "select  NumeroReglementFournisseur , T.CodeModeReglement,ModeReglement.Libelle  as ModeReglement ,Reference ,Echeance , MontantRecu  ,T.CodeBanque , Banque.RaisonSociale as Banque  ,CodeFournisseur ,T.RaisonSociale from  \n" +
                        "(\n" +
                        "\n" +
                        "select NumeroReglementFournisseur , CodeModeReglement ,Reference ,Echeance , MontantRecu  ,CodeBanque  ,CodeFournisseur ,RaisonSociale\n" +
                        "from DetailReglementFournisseur \n" +
                        "where    NumeroEtat ='E13'\n" +
                        "\n" +
                        "union all\n" +
                        " \n" +
                        "SELECT DetailCreditBancaire.NumeroCredit AS NumeroReglementFournisseur, 'T' AS CodeModeReglement,CONVERT(NVARCHAR,NumeroOrdre) AS Reference,DetailCreditBancaire.DateValeur AS Echeance,MontantTotal AS MontantRecu,dbo.Banque.CodeBanque AS CodeBanque,CodeCompte  AS CodeFournisseur, 'Crédit Bancaire : '+ Libelle AS RaisonSociale FROM dbo.DetailCreditBancaire \n" +
                        "INNER JOIN dbo.CreditBancaire ON CreditBancaire.NumeroCredit = DetailCreditBancaire.NumeroCredit \n" +
                        "INNER JOIN dbo.BanqueSociete ON BanqueSociete.CodeBanque = CreditBancaire.CodeCompte \n" +
                        "INNER JOIN dbo.Banque ON BanqueSociete.CodeBanque = Banque.CodeBanqueSociete \n" +
                        "where  DetailCreditBancaire.Regler ='False' \n" +
                        "        \n" +
                        "union all   \n" +
                        "       \n" +
                        "SELECT DetailCreditLeasing.NumeroCredit AS NumeroReglementFournisseur, 'T' AS CodeModeReglement,CONVERT(NVARCHAR,NumeroOrdre) AS Reference,DetailCreditLeasing.DateValeur AS Echeance,MontantBrut AS MontantRecu,dbo.Banque.CodeBanque AS CodeBanque,CodeCompte  AS CodeFournisseur,'Crédit Leasing : '+ Libelle AS RaisonSociale FROM dbo.DetailCreditLeasing \n" +
                        "INNER JOIN dbo.CreditLeasing ON CreditLeasing.NumeroCredit = DetailCreditLeasing.NumeroCredit \n" +
                        "INNER JOIN dbo.BanqueSociete ON BanqueSociete.CodeBanque = CreditLeasing.CodeCaisse \n" +
                        "INNER JOIN dbo.Banque ON BanqueSociete.CodeBanque = Banque.CodeBanqueSociete\n" +
                        " where   Regler ='False' \n" +
                        " ) as T\n" +
                        " INNER  JOIN ModeReglement  on ModeReglement.CodeModeReglement =  T.CodeModeReglement\n" +
                        " INNER  JOIN Banque  on Banque.CodeBanque =  T.CodeBanque\n" +
                        " where Echeance  between '"+date_debut+"' and  '"+date_fin+"'\n" +
                        " and    "+CNDIDION+"\n" +
                        " and  YEAR  ( Echeance  ) =   "+annee+"  and   MONTH   ( Echeance  )   = "+mois+" \n" +
                        "order by Echeance , T.NumeroReglementFournisseur\n" +
                        " ";



                Log.e("query_de_recap_ech", "FOURNISSEUR " + query);
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();

                total =0  ;
                while (rs.next()) {

                    String ModeReglement = rs.getString("ModeReglement");
                    String NumeroReglementFournisseur = rs.getString("NumeroReglementFournisseur");

                    Date Echeance  =dtfSQL.parse(  rs.getString("Echeance"));
                    String CodeFournisseur= rs.getString("CodeFournisseur");
                    String RaisonSociale = rs.getString("RaisonSociale");
                    String Banque = rs.getString("Banque");
                    String Reference = rs.getString("Reference");

                    double MontantRecu = rs.getDouble("MontantRecu");
                    total+= MontantRecu ;


                    DetailRecapEcheanceClient detail  = new DetailRecapEcheanceClient(ModeReglement ,NumeroReglementFournisseur,Echeance  ,CodeFournisseur  ,RaisonSociale, Banque ,Reference ,MontantRecu) ;

                    listDetailRecapEcheanceClient.add(detail) ;

                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
            Log.e("ERROR" ,"DETAIl_recap_frns  "+ex.getMessage().toString()) ;
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);


        DetailRecapEcheanceClientAdapterLV adapter   = new DetailRecapEcheanceClientAdapterLV(activity , listDetailRecapEcheanceClient,"FRNS") ;
        lv.setAdapter(adapter);


        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);

      DetailRecapEcheancierFournisseurParMois.txt_total.setText(instance.format(total) + " DT");



    }


}