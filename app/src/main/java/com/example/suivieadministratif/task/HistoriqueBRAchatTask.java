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
import com.example.suivieadministratif.adapter.BonRetourAdapter;
import com.example.suivieadministratif.model.BonRetourVente;
import com.example.suivieadministratif.module.achat.BonRetourAchatActivity;
import com.example.suivieadministratif.module.achat.LigneBonRetourAchatActivity;
import com.example.suivieadministratif.module.vente.EtatRetourActivity;
import com.example.suivieadministratif.module.vente.HistoriqueLigneBonRetourActivity;

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

public class HistoriqueBRAchatTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv_hist_br;
    ProgressBar pb;
    SearchView search_bar_client;

    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;


    Date  date_debut , date_fin  ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<BonRetourVente> listBonRetourVente = new ArrayList<>();

    double  total= 0 ;

    public HistoriqueBRAchatTask(Activity activity, Date  date_debut , Date date_fin  , ListView lv_hist_br, ProgressBar pb, SearchView search_bar_client) {
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

                String queryHis_bc = "select   NumeroBonRetourAchat ,RaisonSociale ,TotalTTC , TotalRemise ,TotalHT  , TotalTVA ,  DateBonRetourAchat ,Etat.NumeroEtat ,Etat.Libelle  " +
                        "    from BonRetourAchat  \n " +
                        "    inner JOIN Etat  on Etat.NumeroEtat =  BonRetourAchat.NumeroEtat \n" +
                        "    where CONVERT (Date  , DateBonRetourAchat)  between  '"+df.format(date_debut)+"'  and  '"+df.format(date_fin)+"'\n" +
                        "    order by DateBonRetourAchat desc  \n" +
                        "     ";


                Log.e("queryHis_br",""+queryHis_bc) ;
                PreparedStatement ps = con.prepareStatement(queryHis_bc);
                ResultSet rs = ps.executeQuery();
                total= 0 ;
                while (rs.next()) {

                    String NumeroBonRetourVente = rs.getString("NumeroBonRetourAchat");
                    String RaisonSociale = rs.getString("RaisonSociale");

                    double TotalRemise = rs.getDouble("TotalRemise");
                    double TotalHT = rs.getDouble("TotalHT");
                    double TotalTVA = rs.getDouble("TotalTVA");
                    double TotalTTC = rs.getDouble("TotalTTC");
                    Date DateBonRetourVente = dtfSQL.parse(rs.getString("DateBonRetourAchat"));
                    String NumeroEtat = rs.getString("NumeroEtat");
                    String NumeroLibelle = rs.getString("Libelle");

                    if (NumeroEtat!="E00")
                    {
                        total+= TotalTTC ;
                    }


                    BonRetourVente bonRetourVente = new BonRetourVente(NumeroBonRetourVente, DateBonRetourVente, RaisonSociale, TotalTTC,    TotalRemise ,TotalHT  , TotalTVA  ,NumeroEtat,NumeroLibelle);
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

        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);
        BonRetourAchatActivity.txt_tot_retour.setText(instance.format(total));


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


                final BonRetourVente bonRetourVente = listBR.get(position);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                Intent toLigneBonLiv = new Intent(activity, LigneBonRetourAchatActivity.class);
                toLigneBonLiv.putExtra("cle_numero_bon_ret_achat", bonRetourVente.getNumeroBonRetourVente());
                toLigneBonLiv.putExtra("cle_raison_sociale", bonRetourVente.getRaisonSociale());
                toLigneBonLiv.putExtra("cle_total_ttc", bonRetourVente.getTotalTTC());
                toLigneBonLiv.putExtra("cle_date_bc", sdf.format(bonRetourVente.getDateBonRetourVente()));
                activity.startActivity(toLigneBonLiv);



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