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
import com.example.suivieadministratif.menu.MenuTresorerieFragment;
import com.example.suivieadministratif.model.Banque;
import com.example.suivieadministratif.model.Client;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatVenteFragment;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListBanqueTaskForSearchableSpinner extends AsyncTask<String,String,String> {

    Connection con;
    String res ;

    Activity activity  ;
    SearchableSpinner sp_banque ;

    String origine  ;

    ArrayList<String> listLibelleBanque = new ArrayList<>() ;
    ArrayList<Banque> listBanque = new ArrayList<Banque>() ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    public ListBanqueTaskForSearchableSpinner(Activity activity , SearchableSpinner sp_banque ,    String origine) {
        this.activity = activity  ;
        this.sp_banque = sp_banque  ;

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


                String query = "select  CodeBanque  , Libelle  from  BanqueSociete   where 1 =1 " + CONDITION ;

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query) ;

                listLibelleBanque.clear();

                if (origine.equals("MenuTresorerieFragment"))
                {
                    listBanque.add(new Banque("" ,"Tout les Banques")) ;
                    listLibelleBanque.add("Tout les Banques")  ;
                }

                while ( rs.next() ) {

                    String CodeBanque = rs.getString("CodeBanque") ;
                    String Libelle =rs.getString("Libelle") ;

                    Banque   banque  = new Banque(CodeBanque  ,Libelle) ;
                    listBanque.add(banque) ;
                    listLibelleBanque.add(banque.getLibelle())  ;
                    Log.e("Banque", banque.getCodeBanque() + " - " +banque.getLibelle() );

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



        SpinnerAdapter adapter = new SpinnerAdapter(activity  , listLibelleBanque)  ;
        sp_banque.setAdapter(adapter);
        sp_banque.setSelection(0);

        if (origine .equals("MenuTresorerieFragment")) {

            MenuTresorerieFragment.CodeBanqueSelected = listBanque.get(0).getCodeBanque();
            MenuTresorerieFragment.BanqueSelected = listBanque.get(0).getLibelle();
        }

        sp_banque.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("banque_selected"  ,""+listBanque.get(position).toString())  ;

           if (origine .equals("MenuTresorerieFragment"))
                {

                    MenuTresorerieFragment.CodeBanqueSelected = listBanque.get(position).getCodeBanque();
                    MenuTresorerieFragment.BanqueSelected = listBanque.get(position).getLibelle();

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
        );

    }


}
