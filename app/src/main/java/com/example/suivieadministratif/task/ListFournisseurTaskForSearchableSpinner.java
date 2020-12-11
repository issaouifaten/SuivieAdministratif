package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;


import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.SpinnerAdapter;
import com.example.suivieadministratif.model.Fournisseur;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatArticleFragment;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListFournisseurTaskForSearchableSpinner extends AsyncTask<String,String,String> {

    Connection con;
    String res ;

    Activity activity  ;
    SearchableSpinner sp_frns ;
    String  origine ;

    ArrayList<String> listRaison = new ArrayList<>() ;
    ArrayList<Fournisseur> listFournisseur = new ArrayList<Fournisseur>() ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    public ListFournisseurTaskForSearchableSpinner(Activity activity , SearchableSpinner sp_frns ,String  origine ) {
        this.activity = activity;
        this.sp_frns = sp_frns   ;
        this.origine = origine ;


        SharedPreferences pref = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip   = pref.getString("ip", ip);
        base = pref.getString("base", base);
        password = pref.getString("password", password);

        connectionClass = new ConnectionClass();



    }


    //  lancement  de progress dialog
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // txt_client.setHint("Patientez SVP ...");

    }




    //  donwnload  data
    @Override
    protected String doInBackground(String... strings) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);       // Connect to database
            Log.e("con", "" + con);
            if (con == null) {
                res = "Check Your Internet Access!";
            } else {

                String  CONDITION  = " " ;

                if (origine .equals("dialogArticleNonMouvemente"))
                {
                    CONDITION  = CONDITION  + " and  Commercial = 1 " ;
                }
                else {

                }

                String query = " select  CodeFournisseur  , RaisonSociale  from  Fournisseur  where 1=1  "+ CONDITION ;

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query) ;


                listRaison.clear();
                listRaison.add("Tout les Fournisseurs");

                listFournisseur .add(new Fournisseur("","Tout les Fournisseurs"))  ;

                while ( rs.next() ) {


                    String CodeFournisseur = rs.getString("CodeFournisseur") ;
                    String RaisonSociale =rs.getString("RaisonSociale") ;


                     Fournisseur frns = new Fournisseur(CodeFournisseur ,RaisonSociale) ;
                     listFournisseur.add(frns) ;
                     listRaison.add(frns.getRaisonSocial())  ;
                     Log.e("Fournisseur", frns.getCodeFournisseur() + " - " +frns.getRaisonSocial());


                }
            }
            con.close();

        } catch (Exception ex) {

            res = ex.getMessage();
            Log.e("ERROR", res) ;

        }

        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);


        SpinnerAdapter adapter = new SpinnerAdapter(activity  , listRaison)  ;
        sp_frns.setAdapter(adapter);


        sp_frns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("Client_selected"  ,""+listFournisseur.get(position).toString())  ;

                StatArticleFragment.CodeFrns_selected  = listFournisseur.get(position).getCodeFournisseur() ;
                StatArticleFragment.RaisonFrns_selected  = listFournisseur.get(position).getRaisonSocial()  ;

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
        );


    }


}
