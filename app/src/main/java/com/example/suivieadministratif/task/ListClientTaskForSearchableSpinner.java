package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.SpinnerAdapter;
import com.example.suivieadministratif.model.Client;
import com.example.suivieadministratif.model.Depot;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.SuivieCommandeFrs;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatArticleFragment;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatClientFragment;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatVenteFragment;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListClientTaskForSearchableSpinner extends AsyncTask<String,String,String> {

    Connection con;
    String res ;

    Activity activity  ;
    SearchableSpinner sp_client ;
    LinearLayout ll_chargement_client   ;
    String origine  ;

    ArrayList<String> listRaison = new ArrayList<>() ;
    ArrayList<Client> listClient = new ArrayList<Client>() ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    public ListClientTaskForSearchableSpinner(Activity activity , SearchableSpinner sp_client ,  LinearLayout ll_chargement_client  , String origine) {
        this.activity = activity  ;
        this.sp_client = sp_client  ;
        this.ll_chargement_client = ll_chargement_client  ;
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
        ll_chargement_client.setVisibility(View.VISIBLE);

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


                String query = "select  CodeClient    from  Client   where 1 =1 " + CONDITION ;

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query) ;


                listRaison.clear();

                if (origine.equals("dialogChoixJournalBLVente") ||  origine.equals("dialogChoixRecouvrementClientEtendu"))
                {
                    listClient.add(new Client("" ,"Tout les Clients")) ;
                    listRaison.add("Tout les Clients")  ;
                }

                while ( rs.next() ) {

                    String CodeClient = rs.getString("CodeClient") ;
                    Client   client  = new Client(CodeClient ) ;
                    listClient.add(client) ;


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


        ll_chargement_client.setVisibility(View.INVISIBLE);
        SpinnerAdapter adapter = new SpinnerAdapter(activity  , listRaison)  ;
        sp_client.setAdapter(adapter);
        sp_client.setSelection(0);

        if (origine .equals("dialogChoixJournalBLVente")) {
            StatVenteFragment.CodeClient_selected = listClient.get(0).getCodeClient();
        }

        if (origine .equals("dialogChoixRecouvrementClientEtendu"))
        {
            StatClientFragment.CodeClient_selected = listClient.get(0).getCodeClient() ;

        }

        sp_client.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("Client_selected"  ,""+listClient.get(position).toString())  ;

           if (origine .equals("dialogChoixJournalBLVente"))
                {
                    StatVenteFragment.CodeClient_selected = listClient.get(position).getCodeClient() ;

                }


                if (origine .equals("dialogChoixRecouvrementClientEtendu"))
                {
                    StatClientFragment.CodeClient_selected = listClient.get(position).getCodeClient() ;


                }

                //

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
        );

    }


}
