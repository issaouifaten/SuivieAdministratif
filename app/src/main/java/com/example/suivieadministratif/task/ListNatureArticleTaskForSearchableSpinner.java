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
import com.example.suivieadministratif.model.Article;
import com.example.suivieadministratif.model.NatureArticle;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.SuivieCommandeFrs;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListNatureArticleTaskForSearchableSpinner extends AsyncTask<String,String,String> {

    Connection con;
    String res ;

    Activity activity  ;
    SearchableSpinner sp_nature_article ;
    String origine  ;

    ArrayList<String> listLibelle = new ArrayList<>() ;
    ArrayList<NatureArticle> listNatureArticle = new ArrayList<NatureArticle>() ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    public ListNatureArticleTaskForSearchableSpinner(Activity activity , SearchableSpinner sp_nature_article , String origine) {
        this.activity = activity  ;
        this.sp_nature_article = sp_nature_article  ;
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


                String query = " select  CodeNature , Libelle  from  NatureArticle " ;

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query) ;



                while ( rs.next() ) {

                    String CodeNature = rs.getString("CodeNature") ;
                    String Libelle =rs.getString("Libelle") ;

                    NatureArticle  natureArticle  = new NatureArticle(CodeNature ,Libelle) ;
                    listNatureArticle.add(natureArticle) ;
                    listLibelle.add(Libelle) ;

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
        sp_nature_article.setAdapter(adapter);
        sp_nature_article.setSelection(0);
        SuivieCommandeFrs.CodeNatureArticleSelected= listNatureArticle.get(0).getCodeNature() ;


        ListArticleTaskForSearchableSpinner listArticleTaskForSearchableSpinner = new ListArticleTaskForSearchableSpinner(activity,  SuivieCommandeFrs.sp_article ,  SuivieCommandeFrs.CodeNatureArticleSelected ,"SuivieCommandeFrs");
        listArticleTaskForSearchableSpinner.execute();


        SuivieCMD_FournisseurTask  suivieCMD_fournisseurTask = new SuivieCMD_FournisseurTask(activity , SuivieCommandeFrs.rv_list_suivi_cmd_frns ,SuivieCommandeFrs.pb ,SuivieCommandeFrs.date_debut,SuivieCommandeFrs.date_fin ,SuivieCommandeFrs.CodeDepotSelected ,SuivieCommandeFrs.CodeArticleSelected ,SuivieCommandeFrs.CodeNatureArticleSelected) ;
        suivieCMD_fournisseurTask.execute() ;


        sp_nature_article.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("Article_selected"  ,""+listNatureArticle.get(position).toString())  ;

                 if (origine .equals("SuivieCommandeFrs"))
                {
                    SuivieCommandeFrs.CodeNatureArticleSelected= listNatureArticle.get(position).getCodeNature() ;
                  //  SuivieCommandeFrs.DepotSelected  = listArticle.get(position).getDesignationArticle() ;

                    SuivieCMD_FournisseurTask  suivieCMD_fournisseurTask = new SuivieCMD_FournisseurTask(activity , SuivieCommandeFrs.rv_list_suivi_cmd_frns ,SuivieCommandeFrs.pb ,SuivieCommandeFrs.date_debut,SuivieCommandeFrs.date_fin ,SuivieCommandeFrs.CodeDepotSelected ,SuivieCommandeFrs.CodeArticleSelected ,SuivieCommandeFrs.CodeNatureArticleSelected) ;
                    suivieCMD_fournisseurTask.execute() ;

                    ListArticleTaskForSearchableSpinner listArticleTaskForSearchableSpinner = new ListArticleTaskForSearchableSpinner(activity,  SuivieCommandeFrs.sp_article ,  SuivieCommandeFrs.CodeNatureArticleSelected ,"SuivieCommandeFrs");
                    listArticleTaskForSearchableSpinner.execute();

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
        );


    }


}
