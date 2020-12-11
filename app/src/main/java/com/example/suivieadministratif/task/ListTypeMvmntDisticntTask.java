package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.MvmentVenteServiceAdapterLV;
import com.example.suivieadministratif.adapter.TypeMvmntGVAdapter;
import com.example.suivieadministratif.model.TypeMouvementClick;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatCRMFragment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListTypeMvmntDisticntTask extends AsyncTask<String, String, String> {


    Activity activity;
    Date date_debut, date_fin;
    GridView gv_list_choix_type_mvmnt;
    ProgressBar pb;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");



    public ListTypeMvmntDisticntTask(Activity activity, Date date_debut, Date date_fin, GridView gv_list_choix_type_mvmnt, ProgressBar pb)  {
        this.activity = activity;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.gv_list_choix_type_mvmnt = gv_list_choix_type_mvmnt;
        this.pb = pb;

        SharedPreferences prefe = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);


        Log.e("BON_CMD", Param.PEF_SERVER + "-" + ip + "-" + base);
        connectionClass = new ConnectionClass();

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);
            if (con == null) {
                z = "Error in connection with SQL server";
            } else {

                String query_type_distinct = " select  distinct Type  , ISNULL ( SUM  (Montant )  ,0)  as total_mnt   from TypeMouvement \n" +
                        "where CONVERT (Date ,Date ) between '" + df.format(date_debut) + "' and '" + df.format(date_fin) + "'   \n " +
                        "    GROUP BY  Type \n ";


                Log.e("query_type_distinct", "" + query_type_distinct);
                PreparedStatement ps = con.prepareStatement(query_type_distinct);
                ResultSet rs = ps.executeQuery();

                StatCRMFragment.listType.clear();
                while (rs.next()) {

                    String Type = rs.getString("Type").replace("Type Mouvement","");
                    double TotalMontant  =  rs.getDouble("total_mnt") ;
                    StatCRMFragment.listType.add(new TypeMouvementClick(Type ,  0 , TotalMontant));

                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
            Log.e("ERROR_msv", "" + ex.getMessage().toString());
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);

        TypeMvmntGVAdapter  typeMvmntGVAdapter = new TypeMvmntGVAdapter(activity  , StatCRMFragment.listType)  ;
        gv_list_choix_type_mvmnt.setAdapter(typeMvmntGVAdapter);

    }

}