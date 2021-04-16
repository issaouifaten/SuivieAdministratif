package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
/*
import com.example.suivieadministratif.adapter.MouvementDepenseAdapterLV;
import com.example.suivieadministratif.connexion.ConnectionClass;
import com.example.suivieadministratif.connexion.StaticValues;
import com.example.suivieadministratif.model.MouvementCaisseDepense;
import com.example.suivieadministratif.ui.caisse.MouvementCaisseDepenseDetailActivity;*/

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.MouvementDepenseAdapterLV;
import com.example.suivieadministratif.model.MouvementCaisseDepense;
import com.example.suivieadministratif.module.caisse.MouvementCaisseDepenseDetailActivity;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ListeMouvementCaisseDepenceTask extends AsyncTask<String, String, String> {

    String res;

    Activity activity;
    ListView lv_list_mvmnt_caisse ;
    ProgressBar pb_chargement ;

    Date  date_debut  , date_fin ;
    String TypeDepInput  ;

    String CodeDepot ;

    ConnectionClass connectionClass ;
    String user, password, base, ip;

    SimpleDateFormat  sdf  = new SimpleDateFormat("dd/MM/yyyy") ;
    DateFormat dtfSQL    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ArrayList <MouvementCaisseDepense>   listMvntCaisse = new ArrayList<>() ;

    double total_depense  =0 ;


    public ListeMouvementCaisseDepenceTask(Activity activity  , ListView lv_list_mvmnt_caisse , ProgressBar pb_chargement , Date  date_debut  , Date date_fin , String TypeDepInput     ) {

        this.activity  = activity  ;
        this.lv_list_mvmnt_caisse = lv_list_mvmnt_caisse  ;
        this.pb_chargement  = pb_chargement   ;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.TypeDepInput=TypeDepInput  ;

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
        total_depense  =0 ;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);       // Connect to database
            Log.e("con", "" + con);
            if (con == null) {
                res = "Check Your Internet Access!";
            } else {
                String  CONDITION_DEP  = "" ;

                if (TypeDepInput.equals(""))
                {
                    CONDITION_DEP  = "" ;
                }
                else {
                    CONDITION_DEP  = " and TypeDepense  =  '"+TypeDepInput+"' " ;
                }

                String query = " select   NumeroMouvement  ,DateMouvement  ,  TotalPayer , NomUtilisateur  ,HeureCreation , Observation  , RaisonSociale as libelleDep ,  TypeDepense.Libelle  as TypeDepense \n" +
                        "from  MouvementCaisseDepense \n" +
                        "INNER JOIN TypeDepense on TypeDepense.CodeTypeDepense = MouvementCaisseDepense.CodeTypeDepense\n" +
                        "where DateMouvement  between  '"+sdf.format(date_debut)+"' and '"+sdf.format(date_fin)+"' \n" +
                        " "+CONDITION_DEP ;


                Log.e("query_mvnt_caisse", query);


                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {


                    String NumeroMouvement  = rs.getString("NumeroMouvement");
                    Date DateMouvement  =dtfSQL.parse( rs.getString("DateMouvement"));
                    Double TotalPayer  = rs.getDouble("TotalPayer");
                    String NomUtilisateur  = rs.getString("NomUtilisateur");
                    Date HeureCreation  = dtfSQL.parse(rs.getString("HeureCreation") );
                    String Observation  = rs.getString("Observation");
                    String libelleDep  = rs.getString("libelleDep");
                    String TypeDepense  = rs.getString("TypeDepense");

                    total_depense  =  total_depense + TotalPayer ;

                    MouvementCaisseDepense  mouvementCaisseDepense  = new  MouvementCaisseDepense (NumeroMouvement ,DateMouvement ,TotalPayer ,NomUtilisateur ,HeureCreation ,Observation ,libelleDep ,TypeDepense ) ;
                    listMvntCaisse.add(mouvementCaisseDepense) ;


                }
            }
            con.close();

        } catch (Exception ex) {

            res = ex.getMessage();
            Log.e("ERROR_listRDV", res.toString());

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

        final NumberFormat formatter = NumberFormat.getNumberInstance(Locale.FRENCH);
        formatter.setMinimumFractionDigits(3);
        formatter.setMaximumFractionDigits(3);

        MouvementCaisseDepenseDetailActivity.txt_tot_depense.setText(formatter.format(total_depense) +" Dt");
        MouvementDepenseAdapterLV adapter  = new MouvementDepenseAdapterLV(activity  , listMvntCaisse) ;
        lv_list_mvmnt_caisse.setAdapter(adapter);


    }







}
