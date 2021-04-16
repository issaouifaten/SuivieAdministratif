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
import com.example.suivieadministratif.adapter.MvmentVenteServiceAdapterLV;
import com.example.suivieadministratif.model.MouvementVenteService;
import com.example.suivieadministratif.module.vente.MouvementVenteServiceActivity;
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

public class MouvementVenteServiceTask extends AsyncTask<String, String, String> {

    Activity activity;

    ListView lv_mvmnt_vente_service;
    ProgressBar pb;
    SearchView search_bar_client ;

    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    float total=0;
    String NomUtilisateur;
    Date  date_debut , date_fin  ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<MouvementVenteService> listMouvementServiceVente = new ArrayList<>();

    public MouvementVenteServiceTask(Activity activity, Date  date_debut , Date date_fin  , ListView lv_mvmnt_vente_service, ProgressBar pb, SearchView search_bar_client) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.lv_mvmnt_vente_service = lv_mvmnt_vente_service;
        this.pb = pb;
        this.search_bar_client = search_bar_client;


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

                String query_mvmnt_service = " select Vue_listeventeglobal.CodeClient  ,Vue_listeventeglobal.RaisonSociale  ,NumeroPiece ,Vue_listeventeglobal.CodeArticle ,Article.Designation  , DatePiece ,Quantite ,MontantTTC ,Vue_listeventeglobal.NomUtilisateur\n" +
                        "         from Vue_listeventeglobal\n" +
                        "        left join Article on Article.CodeArticle=Vue_listeventeglobal.CodeArticle \n" +
                        "        left  join BonLivraisonVente  on  BonLivraisonVente.NumeroBonLivraisonVente = Vue_listeventeglobal.NumeroPiece  \n" +
                        "where  DatePiece between '"+df.format(date_debut)+"' and '"+df.format(date_fin)+"' and Stockable=0  ";


                Log.e("query_mvmnt_vente_ser",""+query_mvmnt_service) ;
                PreparedStatement ps = con.prepareStatement(query_mvmnt_service);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    String CodeClient = rs.getString("CodeClient");
                    String RaisonSociale = rs.getString("RaisonSociale");
                    String NumeroPiece = rs.getString("NumeroPiece");
                    String CodeArticle = rs.getString("CodeArticle");
                    String DesignationArticle = rs.getString("Designation");
                    Date DatePiece = dtfSQL.parse(rs.getString("DatePiece"));
                    int  Quantite = rs.getInt("Quantite");
                    double MontantTTC = rs.getDouble("MontantTTC");
                    String NomUtilisateur = rs.getString("NomUtilisateur");
                    total+=MontantTTC;

                    MouvementVenteService  mouvementVenteService = new MouvementVenteService( CodeClient ,RaisonSociale ,NumeroPiece ,CodeArticle ,DesignationArticle ,DatePiece ,Quantite ,MontantTTC ,NomUtilisateur);
                    listMouvementServiceVente.add(mouvementVenteService);

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

        MvmentVenteServiceAdapterLV mvmentVenteServiceAdapterLV  = new MvmentVenteServiceAdapterLV(activity, listMouvementServiceVente);
        lv_mvmnt_vente_service.setAdapter(mvmentVenteServiceAdapterLV);

      //  txt_tot_mvmnt_vente_service

        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);

        MouvementVenteServiceActivity. txt_tot_ttc.setText(instance.format(total)+"");


    }


}