package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.CaisseDepenseAdapterRV;
import com.example.suivieadministratif.model.CaisseDepense;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.recyclerview.widget.RecyclerView;


public class EtatDeCaisseDepenseTask extends AsyncTask<String, String, String> {

    String res;

    Activity activity;
    RecyclerView rv_list_caisse ;
    ProgressBar  pb  ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    ArrayList<CaisseDepense> listCaisseDepense = new ArrayList<>()  ;

    SimpleDateFormat  sdf  = new SimpleDateFormat("dd/MM/yyyy") ;



    public EtatDeCaisseDepenseTask(Activity activity  , RecyclerView rv_list_caisse , ProgressBar  pb   ) {
        this.activity = activity;
        this.rv_list_caisse = rv_list_caisse ;
        this.pb = pb  ;


        SharedPreferences pref = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        base = pref.getString("base", base);
        password = pref.getString("password", password);

        connectionClass = new ConnectionClass();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
         pb.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);       // Connect to database
            Log.e("con", "" + con);
            if (con == null) {
                res = "Check Your Internet Access!";
            } else {

                String query =
                        "         SELECT    Liste.Libelle, Liste.Solde     \n" +
                                " FROM (SELECT  dbo.Compte.CodeCompte, dbo.Compte.Libelle, SUM(dbo.Vue_CaisseDepense.Debit - dbo.Vue_CaisseDepense.Credit) AS Solde\n" +
                                " FROM dbo.Vue_CaisseDepense INNER JOIN\n" +
                                " dbo.Compte ON dbo.Compte.CodeCompte = dbo.Vue_CaisseDepense.CodeCompte\n" +
                                " WHERE (dbo.Compte.CodeTypeCompte = 'D')\n" +
                                " GROUP BY dbo.Compte.CodeCompte, dbo.Compte.Libelle) AS Liste INNER JOIN\n" +
                                " dbo.AccesCaisse ON Liste.CodeCompte = dbo.AccesCaisse.CodeCompte\n" +
                                " WHERE  (dbo.AccesCaisse.NomUtilisateur = 'Admin')\n" +
                                "     \n" +
                                " union all \n" +
                                " \n" +
                                " \n" +
                                "select 'TOTAL DEPENSE 1' as  Libelle  , SUM (TotalPayer) as  Solde  \n" +
                                "from  MouvementCaisseDepense  \n" +
                                "INNER JOIN   Compte  on Compte .CodeCompte =  MouvementCaisseDepense.CodeCompte\n" +
                                "where MouvementCaisseDepense.DateMouvement  = '"+sdf.format(new Date())+"'\n" +
                                "GROUP  BY  Compte.Libelle"  ;


                Log.e("query_caisse_depence", query);


                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);



                while (rs.next()) {


                    String NomCompte  = rs.getString("Libelle");
                    Double Solde  = rs.getDouble("Solde");


                    CaisseDepense caisseDepense  = new CaisseDepense( NomCompte ,Solde   ) ;
                    listCaisseDepense.add(caisseDepense) ;

                }
            }
            con.close();

        } catch (Exception ex) {

            res = ex.getMessage();
            Log.e("ERROR_listRDV", res.toString());

        }
        return null;

    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);


        pb.setVisibility(View.INVISIBLE);


        CaisseDepenseAdapterRV adapterRV   = new CaisseDepenseAdapterRV(activity  , listCaisseDepense) ;
        rv_list_caisse.setAdapter(adapterRV);


      /*  DecimalFormat  fo  = new DecimalFormat("0.000") ;
        CaisseFragment.txt_tot_caisse_user.setText(fo.format(CaisseFragment.tot_caisse_user));
        CaisseFragment.txt_tot_caisse_compte.setText(fo.format(CaisseFragment.tot_caisse_compte));
*/

    }







}
