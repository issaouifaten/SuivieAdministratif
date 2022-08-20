package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.SpinnerAdapter;
import com.example.suivieadministratif.menu.MenuTresorerieFragment;
import com.example.suivieadministratif.model.FamilleArticle;
import com.example.suivieadministratif.model.ModeReglement;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatArticleFragment;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListModeReglementTaskForSearchableSpinner extends AsyncTask<String,String,String> {

    Connection con;
    String res ;

    Activity activity  ;
    SearchableSpinner sp_mode ;


    ArrayList<String> listLibelle = new ArrayList<>() ;
    ArrayList<ModeReglement> listModeReg = new ArrayList<ModeReglement>() ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    public ListModeReglementTaskForSearchableSpinner(Activity activity , SearchableSpinner sp_mode   ) {
        this.activity = activity;
        this.sp_mode = sp_mode   ;


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
                String query = " select CodeModeReglement,Libelle,1 as NumeroOrdre from ModeReglement \n" +
                        "where (CodeModeReglement ='C') or (CodeModeReglement='T') \n" +
                        "union all select 'C+T' as CodeModeReglement,'CHEQUE ET TRAITE' as Libelle,2 as NumeroOrdre from ModeReglement \n" +
                        "where (CodeModeReglement ='C') order by NumeroOrdre  desc" ;

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query) ;


                /*listLibelle.clear();
                listLibelle.add("Tout les familles d'Article");

                listFamilleArticle .add(new FamilleArticle("","Tout les familles d'Article"))  ;*/

                while ( rs.next() ) {


                    String CodeModeReglement = rs.getString("CodeModeReglement") ;
                    String Libelle =rs.getString("Libelle") ;


                     ModeReglement  modeReglement  = new ModeReglement(CodeModeReglement ,Libelle) ;
                     listModeReg.add(modeReglement) ;
                     listLibelle.add(modeReglement.getLibelle())  ;
                     Log.e("modeReglement", modeReglement.getCodeMode() + " - " +modeReglement.getLibelle() );


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


        SpinnerAdapter adapter = new SpinnerAdapter(activity  , listLibelle)  ;
        sp_mode.setAdapter(adapter);


        sp_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("sp_mode_selected"  ,""+listModeReg.get(position).toString())  ;

                MenuTresorerieFragment.CodeModeRegSeleted = listModeReg.get(position).getCodeMode() ;
                MenuTresorerieFragment.ModeRegSeleted = listModeReg.get(position).getLibelle() ;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
        );


    }


}
