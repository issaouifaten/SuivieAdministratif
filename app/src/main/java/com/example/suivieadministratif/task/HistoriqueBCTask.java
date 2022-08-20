package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
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
import com.example.suivieadministratif.module.vente.EtatCommande;
import com.example.suivieadministratif.module.vente.LigneBonCommandeActivity;
import com.example.suivieadministratif.adapter.BonCommandeAdapter;
import com.example.suivieadministratif.model.BonCommandeVente;
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

public class HistoriqueBCTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv_hist_bc;
    ProgressBar pb;
    String  CodeClientSelected ;

    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String NomUtilisateur;
    Date  date_debut , date_fin  ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<BonCommandeVente> listBonCommandeVente = new ArrayList<>();

    double total_net_ht = 0;
    double total_tva = 0;
    double total_ttc = 0;

    public HistoriqueBCTask(Activity activity, Date  date_debut ,Date date_fin  , ListView lv_hist_bc, ProgressBar pb, String  CodeClientSelected) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.lv_hist_bc = lv_hist_bc;
        this.pb = pb;
        this.CodeClientSelected = CodeClientSelected;


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

        total_net_ht = 0;
        total_tva = 0;
        total_ttc = 0;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);
            if (con == null) {
                z = "Error in connection with SQL server";
            } else {


                String  condition  = "" ;
                if (!CodeClientSelected.equals(""))
                {
                    condition += "  and CodeClient=   '"+CodeClientSelected+"' "  ;
                }

                String queryHis_bc = " select   NumeroBonCommandeVente  , NomUtilisateur   ,TotalNetHT , TotalTVA  ,  TotalTTC  , Etat.NumeroEtat ,Etat.Libelle  , DateBonCommandeVente   from BonCommandeVente   \n" +
                        "    inner JOIN Etat  on Etat.NumeroEtat =  BonCommandeVente.NumeroEtat   \n" +
                        "where CONVERT (Date  , DateBonCommandeVente)  between  '"+df.format(date_debut)+"'  and  '"+df.format(date_fin)+"'\n" +condition+
                        "order by DateBonCommandeVente desc  \n ";


                Log.e("queryHis_bc",""+queryHis_bc) ;
                PreparedStatement ps = con.prepareStatement(queryHis_bc);
                ResultSet rs = ps.executeQuery();

                total_net_ht = 0;
                total_tva = 0;
                total_ttc = 0;
                while (rs.next()) {

                    String NumeroBonCommandeVente = rs.getString("NumeroBonCommandeVente");
                    String RaisonSociale = rs.getString("RaisonSociale");

                    String NomUtilisateur = rs.getString("NomUtilisateur");
                    //
                    double TotalNetHT = rs.getDouble("TotalNetHT");
                    double TotalTVA = rs.getDouble("TotalTVA");
                    double TotalTTC = rs.getDouble("TotalTTC");


                    Date   DateBonCommandeVente = dtfSQL.parse(rs.getString("DateBonCommandeVente"));
                    String NumeroEtat = rs.getString("NumeroEtat");
                    String LibelleEtat = rs.getString("Libelle");


                  if ( !NumeroEtat.equals("E00"))
                  {
                      total_net_ht += TotalNetHT;
                      total_tva += TotalTVA;
                      total_ttc += TotalTTC;


                  }

                    BonCommandeVente bonCommandeVente = new BonCommandeVente(NumeroBonCommandeVente, DateBonCommandeVente, NomUtilisateur  ,RaisonSociale, TotalNetHT,  TotalTVA,   TotalTTC   , NumeroEtat , LibelleEtat );
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

        DecimalFormat  decF  = new DecimalFormat("0.000") ;
      //  EtatCommande.txt_tot_commande.setText(decF.format(total_bc)+" Dt");
        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);



        EtatCommande.  txt_tot_ht.setText(instance.format(total_net_ht)+"");
        EtatCommande. txt_tot_tva.setText(instance.format(total_tva)+"");
        EtatCommande. txt_tot_ttc.setText(instance.format(total_ttc)+"");

        listOnClick(listBonCommandeVente);


    }

    public void listOnClick(final  ArrayList<BonCommandeVente>  listBC) {

        lv_hist_bc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                NumberFormat formatter = new DecimalFormat("0.000");

                final BonCommandeVente bonCommandeVente = listBC.get(position);



                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Intent toLigneBonCommande = new Intent(activity, LigneBonCommandeActivity.class);
                toLigneBonCommande.putExtra("cle_numero_bon_cmd_vente", bonCommandeVente.getNumeroBonCommandeVente());
                toLigneBonCommande.putExtra("cle_raison_sociale", bonCommandeVente.getReferenceClient());
                toLigneBonCommande.putExtra("cle_total_ttc", bonCommandeVente.getTotalTTC());
                toLigneBonCommande.putExtra("cle_date_bc", sdf.format(bonCommandeVente.getDateBonCommandeVente()));
                activity.startActivity(toLigneBonCommande);



            }
        });

    }




}