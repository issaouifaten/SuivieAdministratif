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
import com.example.suivieadministratif.model.Depot;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.SuivieCommandeFrs;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatArticleFragment;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListArticleTaskForSearchableSpinner extends AsyncTask<String,String,String> {

    Connection con;
    String res ;

    Activity activity  ;
    SearchableSpinner sp_article ;
    String  CodeNature  ;
    String origine  ;

    ArrayList<String> listDesignation = new ArrayList<>() ;
    ArrayList<Article> listArticle = new ArrayList<Article>() ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    public ListArticleTaskForSearchableSpinner(Activity activity , SearchableSpinner sp_article, String  CodeNature  , String origine) {
        this.activity = activity  ;
        this.sp_article = sp_article  ;
        this.CodeNature = CodeNature ;
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

                if (  origine.equals("SuivieCommandeFrs")   )
                {
                    CONDITION = CONDITION + " and   CodeNature='"+CodeNature+"' " ;
                }
                else {

                }
                String query = " select  CodeArticle , Designation  from  Article    where 1 =1 " + CONDITION ;

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query) ;


                listDesignation.clear();

                if (origine.equals("SuivieCommandeFrs"))
                {
                    listArticle.add(new Article("" ,"Tout les Articles")) ;
                    listDesignation.add("Tout les Articles")  ;
                }

                while ( rs.next() ) {

                    String CodeArticle = rs.getString("CodeArticle") ;
                    String Designation =rs.getString("Designation") ;

                    Article  article  = new Article(CodeArticle ,Designation) ;
                    listArticle.add(article) ;
                    listDesignation.add(article.getDesignationArticle())  ;
                    Log.e("Depot", article.getCodeArticle() + " - " +article.getDesignationArticle() );


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

        SpinnerAdapter adapter = new SpinnerAdapter(activity  , listDesignation)  ;
        sp_article.setAdapter(adapter);
        sp_article.setSelection(0);


        sp_article.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("Article_selected"  ,""+listArticle.get(position).toString())  ;

                 if (origine .equals("SuivieCommandeFrs"))
                {



                    SuivieCMD_FournisseurTask  suivieCMD_fournisseurTask = new SuivieCMD_FournisseurTask(activity , SuivieCommandeFrs.rv_list_suivi_cmd_frns ,SuivieCommandeFrs.pb ,SuivieCommandeFrs.date_debut,SuivieCommandeFrs.date_fin ,SuivieCommandeFrs.CodeDepotSelected ,SuivieCommandeFrs.term_rech_art ,SuivieCommandeFrs.CodeNatureArticleSelected) ;
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
