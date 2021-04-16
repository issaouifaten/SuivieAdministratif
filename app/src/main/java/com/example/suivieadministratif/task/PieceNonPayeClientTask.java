package com.example.suivieadministratif.task;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.PieceNonPayeClient;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.PieceNonPayeFrs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PieceNonPayeClientTask extends AsyncTask<String, String, String> {


    Activity activity;

    GridView lv_list_piece_nn_paye;
    ProgressBar pb;
    String CodeClientSelected;

    ConnectionClass connectionClass;
    String user, password, base, ip;
    Date date_debut, date_fin;

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");


    String z = "";
    Boolean test = false;


    List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();
    float totalMontant = 0, totalrestant = 0;


    public PieceNonPayeClientTask(Activity activity, Date date_debut, Date date_fin, GridView lv_list_piece_nn_paye, ProgressBar pb, String CodeClientSelected) {
        this.activity = activity;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.lv_list_piece_nn_paye = lv_list_piece_nn_paye;
        this.pb = pb;
        this.CodeClientSelected = CodeClientSelected;


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


                String queryTable = " RappelPaiementClientGlobal N'" + df.format(date_debut) + "', N'" + df.format(date_fin) + "'";

                PreparedStatement ps = con.prepareStatement(queryTable);
                Log.e("piecenonpayefrs", queryTable);

                ResultSet rs = ps.executeQuery();
                z = "e";

                while (rs.next()) {

                    if (CodeClientSelected.equals(rs.getString("CodeClient"))) {
                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("NumeroPiece", rs.getString("NumeroPiece"));

                        datanum.put("CodeClient", rs.getString("CodeClient"));
                        datanum.put("RaisonSociale", rs.getString("RaisonSociale"));
                        datanum.put("Montant", rs.getString("Debit"));
                        float restant = rs.getFloat("Debit") - rs.getFloat("Credit");

                        datanum.put("Restant", "" + restant);

                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                        datanum.put("DatePiece", df.format(rs.getDate("DatePiece")));

                        DateFormat dfSQl = new SimpleDateFormat("yyyy-MM-dd");

                        Date datePiece = new Date();
                        try {
                            datePiece = dfSQl.parse(rs.getString("DatePiece"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        if ((rs.getFloat("Debit") - rs.getFloat("Credit") != 0) && (datePiece.after(date_debut) || datePiece.equals(date_debut)) && (datePiece.before(date_fin) || datePiece.equals(date_fin))) {
                            totalrestant += restant;
                            totalMontant += rs.getFloat("Debit");
                            prolist.add(datanum);
                        }

                    } else if ( CodeClientSelected.equals("")){

                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("NumeroPiece", rs.getString("NumeroPiece"));

                        datanum.put("CodeClient", rs.getString("CodeClient"));
                        datanum.put("RaisonSociale", rs.getString("RaisonSociale"));
                        datanum.put("Montant", rs.getString("Debit"));
                        float restant = rs.getFloat("Debit") - rs.getFloat("Credit");

                        datanum.put("Restant", "" + restant);

                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                        datanum.put("DatePiece", df.format(rs.getDate("DatePiece")));

                        DateFormat dfSQl = new SimpleDateFormat("yyyy-MM-dd");

                        Date datePiece = new Date();
                        try {
                            datePiece = dfSQl.parse(rs.getString("DatePiece"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        if ((rs.getFloat("Debit") - rs.getFloat("Credit") != 0) && (datePiece.after(date_debut) || datePiece.equals(date_debut)) && (datePiece.before(date_fin) || datePiece.equals(date_fin))) {
                            totalrestant += restant;
                            totalMontant += rs.getFloat("Debit");
                            prolist.add(datanum);
                        }

                    }


                    test = true;
                    z = "succees";

                }


            }
        } catch (SQLException ex) {
            z = "tablelist" + ex.toString();
            Log.e("erreur", z);

        }
        return z;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.GONE);

// NumeroDevisVente,DatePiece,NomUtilisateur,CodeClient,RaisonSociale,Montant,Etat.Libelle as Etat
        String[] from = {"NumeroPiece", "DatePiece", "RaisonSociale", "Montant", "Restant"};
        int[] views = {R.id.txt_num_bc, R.id.txt_date_bc, R.id.txt_raison_client, R.id.txt_montant, R.id.txt_restant};
        final SimpleAdapter ADA = new SimpleAdapter(activity,
                prolist, R.layout.item_piece_non_paye_frs, from,
                views);


        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);


        PieceNonPayeClient.txt_tot_mnt.setText(instance.format(totalMontant));
        PieceNonPayeClient.txt_total_restant.setText(instance.format(totalrestant));

        lv_list_piece_nn_paye.setAdapter(ADA);

    }


}