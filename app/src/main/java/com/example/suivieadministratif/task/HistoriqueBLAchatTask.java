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
import com.example.suivieadministratif.adapter.BonLivraisonAdapter;
import com.example.suivieadministratif.model.BonLivraisonVente;
import com.example.suivieadministratif.model.LigneBonLivraisonVente;
import com.example.suivieadministratif.module.achat.BonCommandeAchatActivity;
import com.example.suivieadministratif.module.achat.BonLivraisonAchatActivity;
import com.example.suivieadministratif.module.achat.LigneBonLivraisonAchatActivity;
import com.example.suivieadministratif.module.vente.EtatLivraisonActivity;
import com.example.suivieadministratif.module.vente.HistoriqueLigneBonLivraisonActivity;

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

public class HistoriqueBLAchatTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv_hist_bc;
    ProgressBar pb;
    String CodeFournisseurSelected ;

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

    public HistoriqueBLAchatTask(Activity activity, Date  date_debut , Date date_fin  , ListView lv_hist_bc, ProgressBar pb,   String CodeFournisseurSelected  ) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.lv_hist_bc = lv_hist_bc;
        this.pb = pb;
        this.CodeFournisseurSelected = CodeFournisseurSelected;

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
                if (!CodeFournisseurSelected.equals(""))
                {
                    condition += "  and CodeFournisseur=   '"+CodeFournisseurSelected+"' "  ;
                }


                String queryHis_bc = "select   NumeroBonLivraisonAchat  ,RaisonSociale , NomUtilisateur , TotalNetHT , TotalTVA  ,  TotalTTC  , Etat.NumeroEtat ,Etat.Libelle  , DateBonLivraisonAchat    \n" +

                        "from BonLivraisonAchat   \n" +
                        "inner JOIN Etat  on Etat.NumeroEtat =  BonLivraisonAchat.NumeroEtat  " +
                        "where CONVERT (Date  , DateBonLivraisonAchat)  between  '"+df.format(date_debut)+"'  and  '"+df.format(date_fin)+"'\n" +condition+
                        "order by DateBonLivraisonAchat desc  \n" +
                        "     ";


                Log.e("queryHis_bl_achat",""+queryHis_bc) ;
                PreparedStatement ps = con.prepareStatement(queryHis_bc);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    String NumeroBonLivraisonVente = rs.getString("NumeroBonLivraisonAchat");
                    String RaisonSociale = rs.getString("RaisonSociale");
                    String NomUtilisateur = rs.getString("NomUtilisateur");

                    double TotalNetHT = rs.getDouble("TotalNetHT");
                    double TotalTVA = rs.getDouble("TotalTVA");
                    double TotalTTC = rs.getDouble("TotalTTC");

                    Date DateBonLivraisonVente = dtfSQL.parse(rs.getString("DateBonLivraisonAchat"));
                    String NumeroEtat = rs.getString("NumeroEtat");
                    String LibelleEtat = rs.getString("Libelle");


                     if ( !NumeroEtat.equals("E00"))
                        {
                            total_net_ht += TotalNetHT;
                            total_tva    += TotalTVA;
                            total_ttc    += TotalTTC;

                        }

                    BonLivraisonVente bonLivraisonVente = new BonLivraisonVente(NumeroBonLivraisonVente, DateBonLivraisonVente,NomUtilisateur  ,RaisonSociale   , TotalNetHT , TotalTVA  ,  TotalTTC  ,    NumeroEtat, LibelleEtat);
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

        BonLivraisonAchatActivity. txt_tot_ht.setText(instance.format(total_net_ht)+"");
        BonLivraisonAchatActivity. txt_tot_tva.setText(instance.format(total_tva)+"");
        BonLivraisonAchatActivity. txt_tot_ttc.setText(instance.format(total_ttc)+"");

        listOnClick(listBonLivraisonVentes);

    }

    public void listOnClick(final  ArrayList<BonLivraisonVente> listBL ) {

        lv_hist_bc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                final BonLivraisonVente bonLivraisonVente = listBL.get(position);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Intent toLigneBonLiv = new Intent(activity, LigneBonLivraisonAchatActivity.class);
                toLigneBonLiv.putExtra("cle_numero_bon_liv_achat", bonLivraisonVente.getNumeroBonLivraisonVente());
                toLigneBonLiv.putExtra("cle_raison_sociale", bonLivraisonVente.getRaisonSociale());
                toLigneBonLiv.putExtra("cle_total_ttc", bonLivraisonVente.getTotalTTC());
                toLigneBonLiv.putExtra("cle_date_bc", sdf.format(bonLivraisonVente.getDateBonLivraisonVente()));
                activity.startActivity(toLigneBonLiv);
            }
        });

    }

}