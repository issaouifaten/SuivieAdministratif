package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.SuivieImportationAdapter;
import com.example.suivieadministratif.model.SuivieImportationDossier;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SuivieDossierImportationTask extends AsyncTask<String, String, String> {


    Activity activity;
    String NumeroDossier;
    ListView lv_suivie_importation;
    ProgressBar pb;

    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String NomUtilisateur;
    Date date_debut, date_fin ;

    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<SuivieImportationDossier> listSuivieImportationDossier = new ArrayList<>();

    public SuivieDossierImportationTask(Activity activity, String NumeroDossier, ListView lv_suivie_importation, ProgressBar pb) {
        this.activity = activity;
        this.NumeroDossier = NumeroDossier;
        this.lv_suivie_importation = lv_suivie_importation;
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

                String queryHis_bc = "DECLARE\t@return_value int\n" +
                        "\n" +
                        "EXEC\t@return_value = [dbo].[ChargeParImportation]\n" +
                        "\t\t@NumeroImportation = N'"+NumeroDossier+ "',\n" +
                        "\t\t@DateDebut = '01/01/2011',\n" +
                        "\t\t@DateFin = '"+df.format(new Date())+"'\n" +
                        "\n" +
                        "SELECT\t'Return Value' = @return_value\n ";


                Log.e("queryHis_bc", "" + queryHis_bc);
                PreparedStatement ps = con.prepareStatement(queryHis_bc);
                ResultSet rs = ps.executeQuery();


                while (rs.next()) {

                    String NumeroFactureAchat = rs.getString("NumeroFactureAchat");
                    String RaisonSociale = rs.getString("RaisonSociale");
                    Date DateAchatDiver = dtfSQL.parse(rs.getString("DateAchatDiver"));
                    double TotalNetHT = rs.getDouble("TotalNetHT");
                    double TotalTVA = rs.getDouble("TotalTVA");
                    double TimbreFiscal = rs.getDouble("TimbreFiscal");
                    double TotalTTC = rs.getDouble("TotalTTC");

                    SuivieImportationDossier  suivieImportationDossier = new SuivieImportationDossier(NumeroFactureAchat ,RaisonSociale ,DateAchatDiver ,TotalNetHT ,TotalTVA ,TimbreFiscal,TotalTTC) ;
                    listSuivieImportationDossier.add(suivieImportationDossier) ;

                }

                z = "Success";
            }
        } catch (Exception ex)  {

            z = " Error retrieving data from table "  ;

            Log.e("ERROR_s_dossier" ,ex.getMessage().toString()) ;

        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);


        SuivieImportationAdapter suivieImportationAdapter   = new SuivieImportationAdapter(activity , listSuivieImportationDossier )  ;
        lv_suivie_importation.setAdapter(suivieImportationAdapter);



    }

}