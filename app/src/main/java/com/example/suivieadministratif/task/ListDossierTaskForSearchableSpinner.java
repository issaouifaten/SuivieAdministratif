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
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.CommandeFournisseurNonConforme;
import com.example.suivieadministratif.ui.statistique_rapport_activite.importation.SuivieDossierImportationActivity;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListDossierTaskForSearchableSpinner extends AsyncTask<String,String,String> {

    Connection con;
    String res ;

    Activity activity  ;
    SearchableSpinner sp_fournisseur ;
    String origine  ;
    int cloture    ;
    String  CodeFournisseur  ;

    ArrayList<String> listNumeroDossier = new ArrayList<>() ;


    ConnectionClass connectionClass;
    String user, password, base, ip;

    public ListDossierTaskForSearchableSpinner(Activity activity , SearchableSpinner sp_fournisseur ,   int cloture ,  String  CodeFournisseur ,String origine) {
        this.activity = activity  ;
        this.sp_fournisseur = sp_fournisseur  ;
        this.cloture=cloture  ;
        this.CodeFournisseur=CodeFournisseur ;
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

                String  CONDIION= "  " ;

                if (origine .equals("SuivieDossierImportationActivity")) {

                    if (cloture == 2 )
                    {
                        CONDIION=CONDIION+"" ;
                    }
                   else
                    {
                        CONDIION=CONDIION+"\n  and  Coturer =   "+ cloture+"  " ;
                    }


                   if (CodeFournisseur .equals(""))
                   {
                       CONDIION=CONDIION+"" ;
                   }
                   else {
                       CONDIION=CONDIION+"\n  and  CodeFournisseur =   "+ CodeFournisseur+"  " ;
                   }




                }

                String query = " select * from  Dossier   where 1=1 " +
                        CONDIION+
                        "\nOrder by NumeroDossier desc  \n    "   ;

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query) ;


                listNumeroDossier.clear();



                listNumeroDossier.add("Tout les dossiers")  ;


                while ( rs.next() ) {


                    String NumeroDossier =rs.getString("NumeroDossier") ;

                    listNumeroDossier.add( NumeroDossier )  ;


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

        SpinnerAdapter adapter = new SpinnerAdapter(activity  , listNumeroDossier)  ;
        sp_fournisseur.setAdapter(adapter);
        sp_fournisseur.setSelection(0);

        sp_fournisseur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("Depot_selected"  ,""+listNumeroDossier.get(position).toString())  ;




                   if (origine .equals("SuivieDossierImportationActivity"))
                {
                    SuivieDossierImportationActivity.CodeDossierSelected = listNumeroDossier.get(position)  ;

                    SuivieDossierImportationTask  suivieDossierImportationTask= new SuivieDossierImportationTask(activity  ,    SuivieDossierImportationActivity.CodeDossierSelected  , SuivieDossierImportationActivity.lv_list_suivie_dossier ,SuivieDossierImportationActivity.pb);
                    suivieDossierImportationTask.execute() ;


                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
        );

    }


}
