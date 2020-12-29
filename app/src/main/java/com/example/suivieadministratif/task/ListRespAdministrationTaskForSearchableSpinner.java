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
import com.example.suivieadministratif.model.ResponsableAdministration;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.CommandeFournisseurNonConforme;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListRespAdministrationTaskForSearchableSpinner extends AsyncTask<String,String,String> {

    Connection con;
    String res ;

    Activity activity  ;
    SearchableSpinner sp_resp_admin ;
    String origine  ;

    ArrayList<String> listNom= new ArrayList<>() ;
    ArrayList<ResponsableAdministration> listResp = new ArrayList<ResponsableAdministration>() ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    public ListRespAdministrationTaskForSearchableSpinner(Activity activity , SearchableSpinner sp_resp_admin , String origine) {
        this.activity = activity  ;
        this.sp_resp_admin = sp_resp_admin  ;
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


                String query = "  \n" +
                        "select  CodeRespensable , Nom  \n" +
                        "from Respensable \n" +
                        "where CodeRespensable in (select MatriculePersonnel from  FonctionnaliterPersonnel where Actif=1 and Autoriser= 1 ) " ;

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query) ;


                listNom.clear();


                    listResp.add(new ResponsableAdministration("" ,"Tout les Responsable")) ;
                listNom.add("Tout les Responsable")  ;


                while ( rs.next() ) {

                    String CodeRespensable = rs.getString("CodeRespensable") ;
                    String Nom =rs.getString("Nom") ;

                    ResponsableAdministration  responsableAdministration  = new ResponsableAdministration(CodeRespensable ,Nom) ;
                    listResp.add ( responsableAdministration ) ;
                    listNom.add  ( Nom )  ;
                    Log.e("Responsable ", responsableAdministration.getCodeResponsable() + " - " +responsableAdministration.getNom() );

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

        SpinnerAdapter adapter = new SpinnerAdapter(activity  , listNom)  ;
        sp_resp_admin.setAdapter(adapter);
        sp_resp_admin.setSelection(0);

        sp_resp_admin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("resp_selected"  ,""+listResp.get(position).toString())  ;

                if (origine .equals("CommandeFournisseurNonConforme"))
                {
                    CommandeFournisseurNonConforme.CodeRespAdmin = listResp.get(position).getCodeResponsable() ;
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
