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
import com.example.suivieadministratif.adapter.AlimentationCaisseRecetteAdapterLV;
import com.example.suivieadministratif.model.AlimentationCaisseRecette;
import com.example.suivieadministratif.module.caisse.AlimentationBanqueRecetteActivity;
import com.example.suivieadministratif.module.caisse.AlimentationCaisseActivity;
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

public class AlimentationCaisseTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv_alimentation_caisse;
    ProgressBar pb;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;


    Date  date_debut , date_fin  ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<AlimentationCaisseRecette> listAlimentationCaisseRecette = new ArrayList<>();

    double   tot_recu =0  ;

    public AlimentationCaisseTask(Activity activity, Date  date_debut , Date date_fin  , ListView lv_alimentation_caisse, ProgressBar pb  ) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.lv_alimentation_caisse = lv_alimentation_caisse;
        this.pb = pb;




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

                String query_alimentation_caisse_recette  = "  select  NumeroAlimentation , DateAlimentation  ,CodeCaisseSource , (select   Libelle  from Compte  where CodeCompte = CodeCaisseSource ) as origine\n" +
                        ",CodeCaisseDestination   , (select   Libelle  from Compte  where CodeCompte = CodeCaisseDestination )  as Destination \n" +
                        ",NomUtilisateur  , TotalRecu  , AlimentationCaisse.NumeroEtat  , Etat.Libelle  as Etat , AlimentationCaisse.CodeModeReglement , ModeReglement.Libelle  as ModeReglement, Reference\n" +
                        "from  AlimentationCaisse\n" +
                        " \n" +
                        "inner  join  Compte  on Compte.CodeCompte = AlimentationCaisse.CodeCaisseDestination\n" +
                        "inner join Etat on Etat.NumeroEtat = AlimentationCaisse.NumeroEtat\n" +
                        "inner join ModeReglement  on ModeReglement.CodeModeReglement  = AlimentationCaisse.CodeModeReglement\n" +
                        " where DateAlimentation    between  '"+df.format(date_debut)+"' and  '"+df.format(date_fin)+"' \n" +
                        " order by  NumeroAlimentation desc \n ";


                Log.e("query_alim_c_re",""+query_alimentation_caisse_recette) ;
                PreparedStatement ps = con.prepareStatement(query_alimentation_caisse_recette);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    String NumeroAlimentation = rs.getString("NumeroAlimentation");
                    Date DateAlimentation = dtfSQL.parse(rs.getString("DateAlimentation"));

                    String CodeCaisseSource = rs.getString("CodeCaisseSource");
                    String origine = rs.getString("origine");

                    String CodeCaisseDestination = rs.getString("CodeCaisseDestination");
                    String Destination = rs.getString("Destination");

                    String NomUtilisateur = rs.getString("NomUtilisateur");
                    double TotalRecu = rs.getDouble("TotalRecu");
                    String NumeroEtat = rs.getString("NumeroEtat");
                    String Etat = rs.getString("Etat");
                    String CodeModeReglement = rs.getString("CodeModeReglement");
                    String ModeReglement = rs.getString("ModeReglement");
                    String Reference = rs.getString("Reference");

                    tot_recu +=  TotalRecu  ;

                    AlimentationCaisseRecette alimentationCaisseRecette  =  new
                    AlimentationCaisseRecette (NumeroAlimentation ,DateAlimentation , CodeCaisseSource , origine ,CodeCaisseDestination,Destination  ,TotalRecu, NomUtilisateur  ,
                            NumeroEtat ,Etat  ,CodeModeReglement  ,ModeReglement , Reference )  ;


                    listAlimentationCaisseRecette.add(alimentationCaisseRecette) ;


                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
            Log.e("ERROR_alim" ,""+ex.getMessage().toString()) ;
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);


       AlimentationCaisseRecetteAdapterLV adapterLV = new AlimentationCaisseRecetteAdapterLV(activity, listAlimentationCaisseRecette ,"alimentation_caisse");
       lv_alimentation_caisse.setAdapter(adapterLV);


        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);
        AlimentationCaisseActivity.txt_total_recu.setText(instance.format(tot_recu));

    }

}