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
import com.example.suivieadministratif.adapter.RappelPayementAdapterRV;
import com.example.suivieadministratif.model.FicheClient;
import com.example.suivieadministratif.model.RappelPayement;
import com.example.suivieadministratif.module.vente.RappelPayementActivity;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class RappelPayementTask extends AsyncTask<String, String, String> {

    String res;

    Activity activity;

    RecyclerView rv_list;

    ProgressBar pb_chargement;

    String   CodeClient;
    String   TypeRappel ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    ArrayList<RappelPayement> listRappelPayement = new ArrayList<>()  ;


    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    SimpleDateFormat  sdf  = new SimpleDateFormat("dd/MM/yyyy");

    int  full = 0  ;

    double  Total  =0  ;

    public RappelPayementTask(Activity activity, RecyclerView rv_list  , String CodeClient  , String   TypeRappel ,  ProgressBar pb_chargement ) {
        this.activity = activity;
        this.rv_list = rv_list ;
        this.TypeRappel=TypeRappel  ;
        this.CodeClient = CodeClient ;
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
                String query = "DECLARE\t@return_value int\n" +
                            "\n" +
                            "EXEC\t@return_value = [dbo].[RappelPaiement]\n" +
                            "\t\t@CodeClient = N'"+CodeClient+"'\n" +
                            "\n" +
                            "SELECT\t'Return Value' = @return_value\n"  ;

                    //   else if (TypeRappel.equals(""))


                Log.e("query_rappel_payement", query);
                listRappelPayement.clear();

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                double solde_en_cours = 0;
                Total =0 ;
                while (rs.next()) {

                    String TypePiece  = rs.getString("TypePiece");
                    String NumeroPiece  = rs.getString("NumeroPiece");
                    Date datePiece = dtfSQL.parse(rs.getString("datePiece"));

                    double Debit  = rs.getDouble("Debit");
                    double Credit  = rs.getDouble("Credit");

                    double  Montant  = Debit  ;
                    double Restant  = Debit-Credit ;

                    solde_en_cours = solde_en_cours + Restant ;

                    if (TypeRappel.equals("Commertiale"))
                    {

                        Total+= Restant ;
                        RappelPayement  rappelPayement  =  new RappelPayement(TypePiece  , NumeroPiece  ,datePiece ,Montant ,Restant) ;
                        listRappelPayement.add(rappelPayement) ;

                    }
                    else  if (TypeRappel.equals("Comptable"))
                     {
                         if ( TypePiece.equals("BL") ||  TypePiece.equals("RV"))
                         {


                     }
                         else{
                             Total+= Restant ;
                             RappelPayement  rappelPayement  =  new RappelPayement(TypePiece  , NumeroPiece  ,datePiece ,Montant ,Restant) ;
                             listRappelPayement.add(rappelPayement) ;
                         }
                     }


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

        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);

          RappelPayementActivity. txt_total.setText(instance.format(Total)+" DT ");
          RappelPayementAdapterRV adapterRV = new RappelPayementAdapterRV(activity, listRappelPayement);
          rv_list.setAdapter(adapterRV);

    }
}







