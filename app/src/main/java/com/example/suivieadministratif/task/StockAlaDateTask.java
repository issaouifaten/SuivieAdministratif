package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.CaisseAdapterRV;
import com.example.suivieadministratif.model.Caisse;
import com.example.suivieadministratif.module.caisse.CaisseRecetteActivity;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class StockAlaDateTask extends AsyncTask<String, String, String> {

    String res;

    Activity activity;


    Date date_input;
    String CodeDepot;
    String CodeArticle;
    TextView txt_stock_ala_date;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    int quantite_a_la_date  =0  ;


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


    public StockAlaDateTask(Activity activity, Date date_input, String CodeDepot ,String codeArticle, TextView txt_stock_ala_date) {
        this.activity = activity;
        this.date_input = date_input;
        this.CodeDepot = CodeDepot  ;
        CodeArticle = codeArticle;
        this.txt_stock_ala_date = txt_stock_ala_date;


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
        txt_stock_ala_date.setText("Calcul en cours  ...");
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);       // Connect to database
            Log.e("con", "" + con);
            if (con == null) {
                res = "Check Your Internet Access!";
            } else {

                String  query_delete   = "  delete from  StockCalculerAuDate  "  ;

                Statement stmt1 = con.createStatement();
                stmt1.executeUpdate(query_delete);




                String  query_proc    = "  DECLARE\t@return_value int\n" +
                        "\n" +
                        "EXEC\t@return_value = [dbo].[StockReel]\n" +
                        "\t\t@CodeDepot = N'"+CodeDepot+"',\n" +
                        "\t\t@DateDebut = N'"+sdf.format(date_input)+"'\n" +
                        "\n" +
                        "SELECT\t'Return Value' = @return_value\n "  ;



                Statement stmt2 = con.createStatement();
                stmt2.executeUpdate(query_proc);

                String query = "  select  Quantite  from  StockCalculerAuDate where CodeArticle = '"+CodeArticle+"'   ";

                Log.e("query_list_personnel", query);

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    quantite_a_la_date = rs.getInt("Quantite")  ;
                }

            }
            con.close();

        } catch (Exception ex) {

            res = ex.getMessage();
            Log.e("ERROR_stock_ala_date", res.toString());

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


        txt_stock_ala_date.setText(quantite_a_la_date+"");

    }


}
