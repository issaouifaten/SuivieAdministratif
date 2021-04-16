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
import com.example.suivieadministratif.module.vente.EtatCommande;
import com.example.suivieadministratif.module.vente.EtatLivraisonActivity;
import com.example.suivieadministratif.module.vente.HistoriqueLigneBonLivraisonActivity;
import com.example.suivieadministratif.adapter.BonLivraisonAdapter;
import com.example.suivieadministratif.model.BonLivraisonVente;

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

public class HistoriqueBLTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv_hist_bc;
    ProgressBar pb;
    String  CodeClient;

    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String NomUtilisateur;
    Date  date_debut , date_fin  ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<BonLivraisonVente> listBonLivraisonVentes = new ArrayList<>();


    double total_net_ht = 0;
    double total_tva = 0;
    double total_ttc = 0;

    public HistoriqueBLTask(Activity activity, Date  date_debut , Date date_fin  , ListView lv_hist_bc, ProgressBar pb, String CodeClient) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.lv_hist_bc = lv_hist_bc;
        this.pb = pb;
        this.CodeClient = CodeClient  ;


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

                String  condition  = "" ;
                if (!CodeClient.equals(""))
                {
                    condition += "  and CodeClient=   '"+CodeClient+"' "  ;
                }


                String queryHis_bc = " select  NumeroBonLivraisonVente  ,RaisonSociale  ,NomUtilisateur , TotalNetHT , TotalTVA  ,  TotalTTC  ,\n  Etat.NumeroEtat ,Etat.Libelle  , DateBonLivraisonVente  from BonLivraisonVente   \n" +
                        "   inner JOIN Etat  on Etat.NumeroEtat =  BonLivraisonVente.NumeroEtat  \n" +
                        "   where CONVERT (Date  , DateBonLivraisonVente)  between  '"+df.format(date_debut)+"'  and  '"+df.format(date_fin)+"'\n" +condition+
                        "   order by NumeroBonLivraisonVente  desc \n" +
                        "     ";


                Log.e("queryHis_bl",""+queryHis_bc) ;
                PreparedStatement ps = con.prepareStatement(queryHis_bc);
                ResultSet rs = ps.executeQuery();


                  total_net_ht = 0;
                  total_tva = 0;
                  total_ttc = 0;


                while (rs.next()) {

                    String NumeroBonLivraisonVente = rs.getString("NumeroBonLivraisonVente");
                    String RaisonSociale = rs.getString("RaisonSociale");
                    String NomUtilisateur = rs.getString("NomUtilisateur");


                    double TotalNetHT = rs.getDouble("TotalNetHT");
                    double TotalTVA = rs.getDouble("TotalTVA");
                    double TotalTTC = rs.getDouble("TotalTTC");

                    Date DateBonLivraisonVente = dtfSQL.parse(rs.getString("DateBonLivraisonVente"));
                    String NumeroEtat = rs.getString("NumeroEtat");
                    String LibelleEtat = rs.getString("Libelle");

                    if (!NumeroEtat.equals("E00"))
                    {
                        total_net_ht += TotalNetHT;
                        total_tva += TotalTVA;
                        total_ttc += TotalTTC;

                    }

                    BonLivraisonVente bonLivraisonVente = new BonLivraisonVente(NumeroBonLivraisonVente, DateBonLivraisonVente, NomUtilisateur ,RaisonSociale, TotalNetHT ,    TotalTVA  ,  TotalTTC , NumeroEtat, LibelleEtat);
                    listBonLivraisonVentes.add(bonLivraisonVente);


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

        BonLivraisonAdapter bonLivraisonAdapter  = new BonLivraisonAdapter(activity  , listBonLivraisonVentes)  ;
        lv_hist_bc.setAdapter(bonLivraisonAdapter);



        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);


        EtatLivraisonActivity.  txt_tot_ht.setText(instance.format(total_net_ht)+"");
        EtatLivraisonActivity. txt_tot_tva.setText(instance.format(total_tva)+"");
        EtatLivraisonActivity. txt_tot_ttc.setText(instance.format(total_ttc)+"");

        listOnClick(listBonLivraisonVentes);



    }

    public void listOnClick(final  ArrayList<BonLivraisonVente> listBL ) {

        lv_hist_bc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                NumberFormat formatter = new DecimalFormat("0.000");

                final BonLivraisonVente bonLivraisonVente = listBL.get(position);


                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                Intent toLigneBonLiv = new Intent(activity, HistoriqueLigneBonLivraisonActivity.class);
                toLigneBonLiv.putExtra("cle_numero_bon_liv_vente", bonLivraisonVente.getNumeroBonLivraisonVente());
                toLigneBonLiv.putExtra("cle_raison_sociale", bonLivraisonVente.getRaisonSociale());
                toLigneBonLiv.putExtra("cle_total_ttc", bonLivraisonVente.getTotalTTC());
                toLigneBonLiv.putExtra("cle_date_bc", sdf.format(bonLivraisonVente.getDateBonLivraisonVente()));
                activity.startActivity(toLigneBonLiv);



            }
        });

    }

    private ArrayList<BonLivraisonVente> filterClientCMD (ArrayList<BonLivraisonVente> listClientBL, String term) {

        term = term.toLowerCase();
        final ArrayList<BonLivraisonVente> filetrListClient = new ArrayList<>();

        for (BonLivraisonVente c : listClientBL) {
            final String txtRaisonSocial = c.getRaisonSociale().toLowerCase();

            if (txtRaisonSocial.contains(term)) {

                filetrListClient.add(c);


            }
        }


        return filetrListClient;

    }

}