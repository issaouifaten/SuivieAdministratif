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
import android.widget.Toast;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.EtatCommande;
import com.example.suivieadministratif.HistoriqueLigneBonCommandeActivity;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.adapter.BonCommandeAdapter;
import com.example.suivieadministratif.model.BonCommandeVente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoriqueBCTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv_hist_bc;
    ProgressBar pb;
    SearchView search_bar_client;

    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String NomUtilisateur;
    Date  date_debut , date_fin  ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<BonCommandeVente> listBonCommandeVente = new ArrayList<>();

    public HistoriqueBCTask(Activity activity, Date  date_debut ,Date date_fin  , ListView lv_hist_bc, ProgressBar pb, SearchView search_bar_client) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.lv_hist_bc = lv_hist_bc;
        this.pb = pb;
        this.search_bar_client = search_bar_client;

        SharedPreferences prefe = activity.getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);



      /*  SharedPreferences pref=activity.getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt=pref.edit();
        NomUtilisateur= pref.getString("NomUtilisateur",NomUtilisateur);*/


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

                String queryHis_bc = "select *,client.RaisonSociale from BonCommandeVente   \n" +
                        "INNER JOIN Client  on Client.CodeClient=BonCommandeVente.CodeClient  \n" +
                        "where CONVERT (Date  , DateBonCommandeVente)  between  '"+df.format(date_debut)+"'  and  '"+df.format(date_fin)+"'\n" +
                        "order by DateBonCommandeVente desc  \n ";


                Log.e("queryHis_bc",""+queryHis_bc) ;
                PreparedStatement ps = con.prepareStatement(queryHis_bc);
                ResultSet rs = ps.executeQuery();


                while (rs.next()) {

                    String NumeroBonCommandeVente = rs.getString("NumeroBonCommandeVente");
                    String RaisonSociale = rs.getString("RaisonSociale");
                    double TotalTTC = rs.getDouble("TotalTTC");
                    Date DateBonCommandeVente = dtfSQL.parse(rs.getString("DateBonCommandeVente"));
                    String NumeroEtat = rs.getString("NumeroEtat");

                    BonCommandeVente bonCommandeVente = new BonCommandeVente(NumeroBonCommandeVente, DateBonCommandeVente, RaisonSociale, TotalTTC, NumeroEtat);
                    listBonCommandeVente.add(bonCommandeVente);

                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);


        EtatCommande.bcAdapter = new BonCommandeAdapter(activity, listBonCommandeVente);
        lv_hist_bc.setAdapter(EtatCommande.bcAdapter);

        listOnClick();

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

                final ArrayList<BonCommandeVente> fitlerClientList = filterClientCMD(listBonCommandeVente, query);


                EtatCommande.bcAdapter = new BonCommandeAdapter(activity, fitlerClientList);
                lv_hist_bc.setAdapter(EtatCommande.bcAdapter);
                listOnClick();
                return false;
            }
        });


    }

    public void listOnClick() {

        lv_hist_bc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                NumberFormat formatter = new DecimalFormat("0.000");

                final BonCommandeVente bonCommandeVente = listBonCommandeVente.get(position);

                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setIcon(R.drawable.i2s);
                alert.setTitle("Bon De Commande");
                alert.setMessage("Client : " + bonCommandeVente.getReferenceClient());


                alert.setNegativeButton("Détail",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface di, int i) {
                                //di.cancel();

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                Intent toLigneBonCommande = new Intent(activity, HistoriqueLigneBonCommandeActivity.class);
                                toLigneBonCommande.putExtra("cle_numero_bon_cmd_vente", bonCommandeVente.getNumeroBonCommandeVente());
                                toLigneBonCommande.putExtra("cle_raison_sociale", bonCommandeVente.getReferenceClient());
                                toLigneBonCommande.putExtra("cle_total_ttc", bonCommandeVente.getTotalTTC());
                                toLigneBonCommande.putExtra("cle_date_bc", sdf.format(bonCommandeVente.getDateBonCommandeVente()));
                                activity.startActivity(toLigneBonCommande);


                            }
                        });


                if (bonCommandeVente.getNumeroEtat().equals("E00")) {

                    alert.setNeutralButton("Annulé", null);

                } else {


                    AlertDialog dd = alert.create();

                    dd.show();

                }
            }
        });


    }

    private ArrayList<BonCommandeVente> filterClientCMD(ArrayList<BonCommandeVente> listClientCMD, String term) {

        term = term.toLowerCase();
        final ArrayList<BonCommandeVente> filetrListClient = new ArrayList<>();

        for (BonCommandeVente c : listClientCMD) {
            final String txtRaisonSocial = c.getReferenceClient().toLowerCase();


            if (txtRaisonSocial.contains(term)) {
                filetrListClient.add(c);
            }
        }
        return filetrListClient;

    }

}