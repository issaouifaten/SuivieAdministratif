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
import com.example.suivieadministratif.model.FamilleArticle;
import com.example.suivieadministratif.model.Fournisseur;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatArticleFragment;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListFamilleTaskForSearchableSpinner extends AsyncTask<String,String,String> {

    Connection con;
    String res ;

    Activity activity  ;
    SearchableSpinner sp_famille ;

    ArrayList<String> listLibelle = new ArrayList<>() ;
    ArrayList<FamilleArticle> listFamilleArticle = new ArrayList<FamilleArticle>() ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    public ListFamilleTaskForSearchableSpinner(Activity activity , SearchableSpinner sp_famille ) {
        this.activity = activity;
        this.sp_famille = sp_famille   ;


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
                String query = " select  CodeFamille  , Libelle  from  FamilleArticle " ;

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query) ;


                listLibelle.clear();
                listLibelle.add("Tout les familles d'Article");

                listFamilleArticle .add(new FamilleArticle("","Tout les familles d'Article"))  ;

                while ( rs.next() ) {


                    String CodeFamille = rs.getString("CodeFamille") ;
                    String Libelle =rs.getString("Libelle") ;


                     FamilleArticle  familleArticle  = new FamilleArticle(CodeFamille ,Libelle) ;
                     listFamilleArticle.add(familleArticle) ;
                     listLibelle.add(familleArticle.getLibelle())  ;
                     Log.e("FamilleArticle", familleArticle.getCodeFamille() + " - " +familleArticle.getLibelle() );


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
        sp_famille.setAdapter(adapter);


        sp_famille.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("Famille_selected"  ,""+listFamilleArticle.get(position).toString())  ;

                StatArticleFragment.CodeFamille_selected = listFamilleArticle.get(position).getCodeFamille() ;
                StatArticleFragment.LibelleFamille_selected  = listFamilleArticle.get(position).getLibelle()  ;

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
        );


    }


}
