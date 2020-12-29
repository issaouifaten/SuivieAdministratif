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
import com.example.suivieadministratif.model.Depot;
import com.example.suivieadministratif.model.Fournisseur;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.CommandeFournisseurNonConforme;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.SuivieCommandeFrs;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatArticleFragment;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListFournisseurTaskForSearchableSpinner extends AsyncTask<String,String,String> {

    Connection con;
    String res ;

    Activity activity  ;
    SearchableSpinner sp_fournisseur ;
    String origine  ;

    ArrayList<String> listRaison = new ArrayList<>() ;
    ArrayList<Fournisseur> listFournisseur  = new ArrayList<Fournisseur>() ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    public ListFournisseurTaskForSearchableSpinner(Activity activity , SearchableSpinner sp_fournisseur , String origine) {
        this.activity = activity  ;
        this.sp_fournisseur = sp_fournisseur  ;
        this.origine=origine ;


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


                String query = "  select  CodeFournisseur  , RaisonSociale  from  Fournisseur \n   where 1 =1 " ;

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query) ;


                listRaison.clear();


                    listFournisseur.add(new Fournisseur("" ,"Tout les fournisseur")) ;
                    listRaison.add("Tout les fournisseur")  ;


                while ( rs.next() ) {

                    String CodeFournisseur = rs.getString("CodeFournisseur") ;
                    String RaisonSociale =rs.getString("RaisonSociale") ;

                    Fournisseur  fournisseur  = new Fournisseur(CodeFournisseur ,RaisonSociale) ;
                    listFournisseur.add(fournisseur) ;
                    listRaison.add(fournisseur.getRaisonSocial())  ;
                    Log.e("Fournisseur ", fournisseur.getCodeFournisseur() + " - " +fournisseur.getRaisonSocial()  );

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
        sp_fournisseur.setAdapter(adapter);
        sp_fournisseur.setSelection(0);

        sp_fournisseur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("Depot_selected"  ,""+listFournisseur.get(position).toString())  ;

                if (origine .equals("CommandeFournisseurNonConforme"))
                {
                    CommandeFournisseurNonConforme.CodeFournisseurSelected = listFournisseur.get(position).getCodeFournisseur() ;

                    CommandeFrnsNonConformeTask commandeFrnsNonConformeTask = new CommandeFrnsNonConformeTask(activity ,  CommandeFournisseurNonConforme.rv_list_cmd_frns_nn_conforme , CommandeFournisseurNonConforme.pb , CommandeFournisseurNonConforme.date_debut, CommandeFournisseurNonConforme.date_fin , CommandeFournisseurNonConforme.CodeFournisseurSelected , CommandeFournisseurNonConforme. CodeRespAdmin) ;
                    commandeFrnsNonConformeTask.execute() ;

                }




            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
        );

    }


}
