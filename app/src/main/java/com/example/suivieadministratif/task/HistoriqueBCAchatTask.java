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
import com.example.suivieadministratif.adapter.BonCommandeAdapter;
import com.example.suivieadministratif.model.BonCommandeVente;
import com.example.suivieadministratif.module.achat.BonCommandeAchatActivity;
import com.example.suivieadministratif.module.achat.LigneBonCommandeAchatActivity;

import com.example.suivieadministratif.module.vente.MouvementVenteServiceActivity;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HistoriqueBCAchatTask extends AsyncTask<String, String, String> {


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
float total=0;
    ArrayList<BonCommandeVente> listBonCommandeVente = new ArrayList<>();

    public HistoriqueBCAchatTask(Activity activity, Date  date_debut , Date date_fin  , ListView lv_hist_bc, ProgressBar pb, SearchView search_bar_client) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.lv_hist_bc = lv_hist_bc;
        this.pb = pb;
        this.search_bar_client = search_bar_client;


        SharedPreferences prefe = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);


        Log.e("BON_CMD" ,Param.PEF_SERVER +"-"+ip+"-"+base) ;

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

                String queryHis_bc = "  select NumeroBonCommandeAchat  ,RaisonSociale  ,TotalTTC , TotalRemise ,TotalHT  , TotalTVA  ,  Etat.NumeroEtat ,Etat.Libelle  , DateBonCommandeAchat   from BonCommandeAchat  \n" +
                        "inner JOIN Etat  on Etat.NumeroEtat =  BonCommandeAchat.NumeroEtat    \n" +
                        "where CONVERT (Date  , DateBonCommandeAchat)  between  '"+df.format(date_debut)+"'  and  '"+df.format(date_fin)+"'\n" +
                        "order by DateBonCommandeAchat desc  \n ";

                Log.e("queryHis_bc",""+queryHis_bc) ;
                PreparedStatement ps = con.prepareStatement(queryHis_bc);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    String NumeroBonCommandeVente = rs.getString("NumeroBonCommandeAchat");
                    String RaisonSociale = rs.getString("RaisonSociale");

                    double TotalRemise = rs.getDouble("TotalRemise");
                    double TotalHT = rs.getDouble("TotalHT");
                    double TotalTVA = rs.getDouble("TotalTVA");
                    double TotalTTC = rs.getDouble("TotalTTC");
                    Date DateBonCommandeVente = dtfSQL.parse(rs.getString("DateBonCommandeAchat"));
                    String NumeroEtat = rs.getString("NumeroEtat");
                    String Libelle = rs.getString("Libelle");
                    total+=TotalTTC;

                    BonCommandeVente bonCommandeVente = new BonCommandeVente(NumeroBonCommandeVente, DateBonCommandeVente, RaisonSociale, TotalTTC,TotalRemise ,   TotalHT  ,    TotalTVA, NumeroEtat,Libelle);
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


        BonCommandeAchatActivity.bcAdapter = new BonCommandeAdapter(activity, listBonCommandeVente);
        lv_hist_bc.setAdapter(BonCommandeAchatActivity.bcAdapter);

        listOnClick(listBonCommandeVente);

        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);
        BonCommandeAchatActivity.txt_tot.setText(instance.format(total));
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
                BonCommandeAchatActivity.bcAdapter = new BonCommandeAdapter(activity, fitlerClientList);
                lv_hist_bc.setAdapter(BonCommandeAchatActivity.bcAdapter);
                listOnClick(fitlerClientList);

                return false;
            }
        });

    }

    public void listOnClick(final  ArrayList<BonCommandeVente>  listBC) {

        lv_hist_bc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                NumberFormat formatter = new DecimalFormat("0.000");

                final BonCommandeVente bonCommandeVente = listBC.get(position);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Intent toLigneBonCommande = new Intent(activity, LigneBonCommandeAchatActivity.class);
                toLigneBonCommande.putExtra("cle_numero_bon_cmd_achat", bonCommandeVente.getNumeroBonCommandeVente());
                toLigneBonCommande.putExtra("cle_raison_sociale", bonCommandeVente.getReferenceClient());
                toLigneBonCommande.putExtra("cle_total_ttc", bonCommandeVente.getTotalTTC());
                toLigneBonCommande.putExtra("cle_date_bc", sdf.format(bonCommandeVente.getDateBonCommandeVente()));
                activity.startActivity(toLigneBonCommande);


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