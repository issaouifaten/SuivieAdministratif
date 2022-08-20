package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.FicheClientAdapterRV;
import com.example.suivieadministratif.adapter.FicheFournisseurAdapterRV;
import com.example.suivieadministratif.model.FicheClient;
import com.example.suivieadministratif.model.FicheFournisseur;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FicheClientTask extends AsyncTask<String, String, String> {

    String res;

    Activity activity;

    RecyclerView rv_list;

    ProgressBar pb_chargement;

    String   CodeClient;
    String   TypeFiche ;
    Date  date_debut  ; Date date_fin ;
    ConnectionClass connectionClass;
    String user, password, base, ip;

    ArrayList<FicheClient> listFicheClient = new ArrayList<>()  ;


    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    SimpleDateFormat  sdf  = new SimpleDateFormat("dd/MM/yyyy");

    int  full = 0  ;

    public FicheClientTask (Activity activity, RecyclerView rv_list  , String CodeClient  , String   TypeFiche , Date  date_debut  , Date date_fin , ProgressBar pb_chargement ) {
        this.activity = activity;
        this.rv_list = rv_list ;
        this.TypeFiche=TypeFiche  ;
        this.CodeClient = CodeClient ;
        this.CodeClient = CodeClient ;
        this.date_debut  = date_debut  ;
        this.date_fin= date_fin  ;
        this.pb_chargement =pb_chargement ;


        SharedPreferences pref = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);
        connectionClass = new ConnectionClass();


    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
          pb_chargement.setVisibility(View.VISIBLE);
         full = 0  ;

    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);       // Connect to database
            Log.e("con", "" + con);
            if (con == null) {
                res = "Check Your Internet Access!";
            } else {
                String query = ""  ;

                if (TypeFiche.equals("Commertiale"))
                {
                    query = "DECLARE\t@return_value int\n" +
                            "\n" +
                            "EXEC\t@return_value = [dbo].[FicheClient]\n" +
                            "\t\t@CodeClient = N'"+CodeClient+"',\n" +
                            "\t\t@DateDebut = '"+sdf.format(date_debut)+"',\n" +
                            "\t\t@DateFin = '"+sdf.format(date_fin)+"'\n" +
                            "\n" +
                            "SELECT\t'Return Value' = @return_value"  ;
                }
                else if (TypeFiche.equals("Comptable"))
                {
                    query = "\n" +
                            "DECLARE\t@return_value int\n" +
                            "\n" +
                            "EXEC\t@return_value = [dbo].[FicheClientSurFacture]\n" +
                            "\t\t@CodeClient = N'"+CodeClient+"',\n" +
                            "\t\t@DateDebut = '"+sdf.format(date_debut)+"',\n" +
                            "\t\t@DateFin = '"+sdf.format(date_fin)+"'\n" +
                            "\n" +
                            "SELECT\t'Return Value' = @return_value"  ;
                }





                Log.e("query_fiche_client", query);
                listFicheClient.clear();

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                double solde_en_cours = 0;

                while (rs.next()) {


                    Date datePiece = dtfSQL.parse(rs.getString("datePiece"));

                    String Operation  = rs.getString("Libelle");
                    String NumeroPiece  = rs.getString("NumeroPiece");


                    double Debit  = rs.getDouble("Debit");
                    double Credit  = rs.getDouble("Credit");
                    double solde  = Debit-Credit ;

                    solde_en_cours = solde_en_cours + solde ;

                    FicheClient ficheClient  =  new FicheClient(datePiece,Operation,NumeroPiece,Debit , Credit ,solde_en_cours) ;
                    listFicheClient.add(ficheClient) ;

                    full = 1 ;
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

        pb_chargement.setVisibility(View.INVISIBLE);

         FicheClientAdapterRV ficheArticleAdapterRV = new FicheClientAdapterRV(activity, listFicheClient);
        rv_list.setAdapter(ficheArticleAdapterRV);

    }
}







