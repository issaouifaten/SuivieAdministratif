package com.example.suivieadministratif.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.module.vente.HistoriqueLigneBonRetourActivity;
import com.example.suivieadministratif.adapter.BonRetourAdapter;
import com.example.suivieadministratif.model.BonRetourVente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoriqueBRTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv_hist_br;
    ProgressBar pb;
    SearchView search_bar_client;

    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String NomUtilisateur;
    Date  date_debut , date_fin  ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<BonRetourVente> listBonRetourVente = new ArrayList<>();

    public HistoriqueBRTask(Activity activity, Date  date_debut , Date date_fin  , ListView lv_hist_br, ProgressBar pb, SearchView search_bar_client) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.lv_hist_br = lv_hist_br;
        this.pb = pb;
        this.search_bar_client = search_bar_client;

        SharedPreferences prefe = activity.getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);

       /*
        SharedPreferences pref=activity.getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt=pref.edit();
        NomUtilisateur= pref.getString("NomUtilisateur",NomUtilisateur);
        */

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

                String queryHis_bc = "select   NumeroBonRetourVente ,RaisonSociale ,TotalTTC , DateBonRetourVente ,NumeroEtat " +
                        "  from BonRetourVente   \n" +
                        "    where CONVERT (Date  , DateBonRetourVente)  between  '"+df.format(date_debut)+"'  and  '"+df.format(date_fin)+"'\n" +
                        "    order by DateBonRetourVente desc  \n" +
                        "     ";


                Log.e("queryHis_br",""+queryHis_bc) ;
                PreparedStatement ps = con.prepareStatement(queryHis_bc);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    String NumeroBonRetourVente = rs.getString("NumeroBonRetourVente");
                    String RaisonSociale = rs.getString("RaisonSociale");
                    double TotalTTC = rs.getDouble("TotalTTC");
                    Date DateBonRetourVente = dtfSQL.parse(rs.getString("DateBonRetourVente"));
                    String NumeroEtat = rs.getString("NumeroEtat");

                    BonRetourVente bonRetourVente = new BonRetourVente(NumeroBonRetourVente, DateBonRetourVente, RaisonSociale, TotalTTC, NumeroEtat);
                    listBonRetourVente.add(bonRetourVente);

                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";

            Log.e("ERROR",""+ex.getMessage().toString()) ;
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);

        BonRetourAdapter bonRetourAdapter  = new BonRetourAdapter(activity  , listBonRetourVente)  ;
         lv_hist_br.setAdapter(bonRetourAdapter);

        listOnClick(listBonRetourVente);

        search_bar_client.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!search_bar_client.isIconified()) {
                    search_bar_client.setIconified(true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                final ArrayList<BonRetourVente> fitlerClientList = filterClientCMD(listBonRetourVente, query);

                BonRetourAdapter bonRetourAdapter1  = new BonRetourAdapter(activity  , fitlerClientList)  ;
                lv_hist_br.setAdapter(bonRetourAdapter1);
                listOnClick(fitlerClientList);

                return false;

            }
        });

    }

    public void listOnClick(final  ArrayList<BonRetourVente  > listBR ) {

        lv_hist_br.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                NumberFormat formatter = new DecimalFormat("0.000");

                final BonRetourVente bonRetourVente = listBR.get(position);

                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setIcon(R.drawable.i2s);
                alert.setTitle("Bon De Retour");
                alert.setMessage("Client : " + bonRetourVente.getRaisonSociale());


                alert.setNegativeButton("Détail",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface di, int i) {
                                //di.cancel();

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                                Intent toLigneBonLiv = new Intent(activity, HistoriqueLigneBonRetourActivity.class);
                                toLigneBonLiv.putExtra("cle_numero_bon_ret_vente", bonRetourVente.getNumeroBonRetourVente());
                                toLigneBonLiv.putExtra("cle_raison_sociale", bonRetourVente.getRaisonSociale());
                                toLigneBonLiv.putExtra("cle_total_ttc", bonRetourVente.getTotalTTC());
                                toLigneBonLiv.putExtra("cle_date_bc", sdf.format(bonRetourVente.getDateBonRetourVente()));
                                activity.startActivity(toLigneBonLiv);

                            }
                        });


                if (bonRetourVente.getNumeroEtat().equals("E00")) {

                    alert.setNeutralButton("Annulé", null);

                } else {

                    AlertDialog dd = alert.create();

                    dd.show();

                }
            }
        });

    }


    private ArrayList<BonRetourVente> filterClientCMD(ArrayList<BonRetourVente> listClientBR, String term) {

        term = term.toLowerCase();
        final ArrayList<BonRetourVente> filetrListClient = new ArrayList<>();

        for (BonRetourVente c : listClientBR) {
            final String txtRaisonSocial = c.getRaisonSociale().toLowerCase();


            if (txtRaisonSocial.contains(term)) {
                filetrListClient.add(c);
            }
        }
        return filetrListClient;

    }

}