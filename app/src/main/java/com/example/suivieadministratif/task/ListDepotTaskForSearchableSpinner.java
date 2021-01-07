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
import com.example.suivieadministratif.model.FamilleArticle;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.SuivieCommandeFrs;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatArticleFragment;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListDepotTaskForSearchableSpinner extends AsyncTask<String,String,String> {

    Connection con;
    String res ;

    Activity activity  ;
    SearchableSpinner sp_depot ;
    String origine  ;

    ArrayList<String> listLibelle = new ArrayList<>() ;
    ArrayList<Depot> listDepot = new ArrayList<Depot>() ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    public ListDepotTaskForSearchableSpinner(Activity activity , SearchableSpinner sp_depot , String origine) {
        this.activity = activity  ;
        this.sp_depot = sp_depot  ;
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

                String  CONDITION  = "" ;

                if (origine .equals("dialogArticleNonMouvemente")  || origine .equals("SuivieCommandeFrs")  )
                {
                    CONDITION = CONDITION + " and   CodeNature='1' " ;
                }
                else {

                }
                String query = "select   CodeDepot  , Libelle  from  Depot   where 1 =1 " + CONDITION ;

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query) ;


                listLibelle.clear();

                if (origine.equals("SuivieCommandeFrs"))
                {
                    listDepot.add(new Depot("" ,"Tout les depots")) ;
                    listLibelle.add("Tout les depots")  ;
                }

                while ( rs.next() ) {

                    String CodeDepot = rs.getString("CodeDepot") ;
                    String Libelle =rs.getString("Libelle") ;

                    Depot  depot  = new Depot(CodeDepot ,Libelle) ;
                    listDepot.add(depot) ;
                    listLibelle.add(depot.getLibelle())  ;
                    Log.e("Depot", depot.getCodeDepot() + " - " +depot.getLibelle() );

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
        sp_depot.setAdapter(adapter);
        sp_depot.setSelection(0);

        sp_depot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("Depot_selected"  ,""+listDepot.get(position).toString())  ;

                if (origine .equals("dialogArticleNonMouvemente"))
                {

                    StatArticleFragment.CodeDepot_selected = listDepot.get(position).getCodeDepot() ;
                    StatArticleFragment.LibelleDepot_selected  = listDepot.get(position).getLibelle()  ;
                }


                else if (origine .equals("SuivieCommandeFrs"))
                {
                    SuivieCommandeFrs.CodeDepotSelected = listDepot.get(position).getCodeDepot() ;
                    SuivieCommandeFrs.DepotSelected  = listDepot.get(position).getLibelle() ;

                    SuivieCMD_FournisseurTask  suivieCMD_fournisseurTask = new SuivieCMD_FournisseurTask(activity , SuivieCommandeFrs.rv_list_suivi_cmd_frns ,SuivieCommandeFrs.pb ,SuivieCommandeFrs.date_debut,SuivieCommandeFrs.date_fin ,SuivieCommandeFrs.CodeDepotSelected ,SuivieCommandeFrs.term_rech_art ,SuivieCommandeFrs.CodeNatureArticleSelected,origine) ;
                    suivieCMD_fournisseurTask.execute() ;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
        );

    }


}
