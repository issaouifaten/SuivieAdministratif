package com.example.suivieadministratif.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.adapter.BonAchatSuiviCMDRVAdapter;
import com.example.suivieadministratif.adapter.BonCommandeAdapter;
import com.example.suivieadministratif.model.BonCommandeVente;
import com.example.suivieadministratif.model.LigneSuiviCMD_frns;
import com.example.suivieadministratif.model.SuiviCMD_frns;
import com.example.suivieadministratif.module.vente.EtatCommande;
import com.example.suivieadministratif.module.vente.HistoriqueLigneBonCommandeActivity;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.SuivieCommandeFrs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SuivieCMD_FournisseurTask extends AsyncTask<String, String, String> {


    Activity activity;

    RecyclerView rv_suivie_cmd_frns;
    ProgressBar pb;

    Date date_debut, date_fin;

    String CodeDepot;
    String CodeArticle;
    String CodeNatureArticle;


    ConnectionClass connectionClass;
    String user, password, base, ip;



    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<SuiviCMD_frns> listSuiviCMD_frns = new ArrayList<>();

    double total_ = 0;

    String  res  = "" ;

    public SuivieCMD_FournisseurTask(Activity activity, RecyclerView rv_suivie_cmd_frns, ProgressBar pb, Date date_debut, Date date_fin, String codeDepot, String codeArticle, String codeNatureArticle) {
        this.activity = activity;
        this.rv_suivie_cmd_frns = rv_suivie_cmd_frns;
        this.pb = pb;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        CodeDepot = codeDepot;
        CodeArticle = codeArticle;
        CodeNatureArticle = codeNatureArticle;

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
                res = "Error in connection with SQL server";
            } else {

                String  CONDITION = "  " ;

                if (!CodeDepot.equals(""))
                {
                    CONDITION =  CONDITION + "   and CodeDepot  = '"+CodeDepot+"' " ;
                }
                 if (!CodeArticle.equals(""))
                {
                    CONDITION =  CONDITION + "   and LigneBonCommandeAchat.CodeArticle =  '"+CodeArticle+"'  " ;
                }

                if (!CodeNatureArticle.equals(""))
                {
                    CONDITION =  CONDITION + "  and Article.CodeNature = '"+CodeNatureArticle+"'  " ;
                }


                String query_suivie_cmd_frns = "  select distinct ( BonCommandeAchat.NumeroBonCommandeAchat) , BonCommandeAchat.CodeFournisseur , RaisonSociale  , TotalHT  , DateBonCommandeAchat \n" +
                        "\n" +
                        " from  BonCommandeAchat \n" +
                        " \n" +
                        "INNER JOIN LigneBonCommandeAchat on LigneBonCommandeAchat.NumeroBonCommandeAchat = BonCommandeAchat.NumeroBonCommandeAchat\n" +
                        "INNER JOIn Article on Article.CodeArticle =  LigneBonCommandeAchat.CodeArticle \n" +
                        " where 1=1 " +

                        " and  DateBonCommandeAchat between  '"+df.format(date_debut)+"' and '"+df.format(date_fin)+"'\n" +

                        " and  BonCommandeAchat.NumeroEtat<>  'E40' \n" +
                        " and ( BonCommandeAchat.NumeroEtat <>'E00' ) \n" +
                        " and ( BonCommandeAchat.NumeroEtat <>'E22') \n" +
                        " and ( BonCommandeAchat.NumeroEtat <>'E03')\n" +

                        " \n" +CONDITION + " ";


                Log.e("query_suivie_cmd_frns", "" + query_suivie_cmd_frns);
                PreparedStatement ps = con.prepareStatement(query_suivie_cmd_frns);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    String NumeroBonCommandeAchat = rs.getString("NumeroBonCommandeAchat");
                    String CodeFournisseur = rs.getString("CodeFournisseur");
                    String RaisonSociale = rs.getString("RaisonSociale");
                    double TotalHT = rs.getDouble("TotalHT");
                    Date DateBonCommandeAchat = dtfSQL.parse(rs.getString("DateBonCommandeAchat"));

                    total_ = total_ + TotalHT ;


                    SuiviCMD_frns suiviCMD_frns = new SuiviCMD_frns(NumeroBonCommandeAchat ,DateBonCommandeAchat,CodeFournisseur ,RaisonSociale ,TotalHT ) ;



                    String query_detail_suivie_cmd_frns = " select CodeArticle , DesignationArticle   , Quantite   , PrixAchatHT  , Livrer " +
                            " from  LigneBonCommandeAchat   where NumeroBonCommandeAchat = '"+NumeroBonCommandeAchat+"' ";


                    Log.e("query_detail_livraison", query_detail_suivie_cmd_frns);

                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(query_detail_suivie_cmd_frns);
                    ArrayList<LigneSuiviCMD_frns> listLigneSuiviCMD_frns = new ArrayList<>();

                    while (rs2.next()) {

                        String CodeArticle = rs2.getString("CodeArticle");
                        String DesignationArticle = rs2.getString("DesignationArticle");
                        int Quantite = rs2.getInt("Quantite");
                        int Livrer = rs2.getInt("Livrer");
                        double  PrixAchatHT = rs2.getDouble("PrixAchatHT");

                        LigneSuiviCMD_frns  ligneSuiviCMD_frns = new LigneSuiviCMD_frns(CodeArticle, DesignationArticle,PrixAchatHT, Quantite ,Livrer) ;
                        listLigneSuiviCMD_frns.add(ligneSuiviCMD_frns) ;

                    }


                    suiviCMD_frns.setListLigneSuiviCMD_frns(listLigneSuiviCMD_frns);
                    listSuiviCMD_frns.add(suiviCMD_frns);

                }

                res = "Success";
            }
        } catch (Exception ex) {
            res = "Error retrieving data from table";
        }
        return res;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);

        BonAchatSuiviCMDRVAdapter  adapter = new BonAchatSuiviCMDRVAdapter(activity, listSuiviCMD_frns);
        rv_suivie_cmd_frns.setAdapter(adapter);

        DecimalFormat decF = new DecimalFormat("0.000");
        SuivieCommandeFrs.txt_tot_ht.setText(decF.format(total_) + " Dt");


    }


}