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
import com.example.suivieadministratif.model.MoyenRelation;
import com.example.suivieadministratif.model.NatureRelation;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatSRMFragment;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListNatureRelationSpinner extends AsyncTask<String,String,String> {

    Connection con;
    String res ;

    Activity activity  ;
    SearchableSpinner sp_nature ;

    String origine  ;

    ArrayList<String> listLibelleNature  = new ArrayList<>() ;
    ArrayList<NatureRelation> listNatureRelation  = new ArrayList<NatureRelation>() ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    public ListNatureRelationSpinner(Activity activity , SearchableSpinner sp_nature , String origine) {
        this.activity = activity  ;
        this.sp_nature = sp_nature  ;
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

                if (origine .equals("dialogSuivieRelationFournisseur")) {

                }

                String query = " select CodeNatureRelation , Libelle  from  NatureRelation  " +
                     "\n   where 1 =1  "+CONDIION   ;


                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query) ;


                listLibelleNature.clear();
                listNatureRelation.clear();

                listNatureRelation.add(new NatureRelation("" ,"Tout les Natures")) ;
                listLibelleNature.add("Tout les Natures")  ;


                while ( rs.next() ) {

                    String CodeNatureRelation = rs.getString("CodeNatureRelation") ;
                    String Libelle =rs.getString("Libelle") ;



                    listNatureRelation.add(new NatureRelation( CodeNatureRelation  , Libelle))  ;
                    listLibelleNature.add(Libelle) ;



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

        SpinnerAdapter adapter = new SpinnerAdapter(activity  , listLibelleNature)  ;
        sp_nature.setAdapter(adapter);
        sp_nature.setSelection(0);

        sp_nature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("Relation_selected"  ,""+listNatureRelation.get(position).toString())  ;

                if (origine.equals("dialogSuivieRelationFournisseur"))
                {
                     StatSRMFragment.CodeNatureSelected = listNatureRelation.get(position).getCodeNatureRelation() ;

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
        );

    }


}
