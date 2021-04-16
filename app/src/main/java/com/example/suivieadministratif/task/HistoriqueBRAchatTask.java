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
import com.example.suivieadministratif.module.achat.BonLivraisonAchatActivity;
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


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String CodeFournisseurSelected  ;


    Date  date_debut , date_fin  ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<BonRetourVente> listBonRetourVente = new ArrayList<>();

    double total_net_ht = 0;
    double total_tva = 0;
    double total_ttc = 0;

    public HistoriqueBRAchatTask(Activity activity, Date  date_debut , Date date_fin  , ListView lv_hist_br, ProgressBar pb,     String CodeFournisseurSelected ) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.lv_hist_br = lv_hist_br;
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


                String queryHis_bc = "select   NumeroBonRetourAchat ,RaisonSociale , NomUtilisateur  , TotalNetHT , TotalTVA  ,  TotalTTC  , DateBonRetourAchat ,Etat.NumeroEtat ,Etat.Libelle  " +
                        "    from BonRetourAchat  \n " +
                        "    inner JOIN Etat  on Etat.NumeroEtat =  BonRetourAchat.NumeroEtat \n" +
                        "    where CONVERT (Date  , DateBonRetourAchat)  between  '"+df.format(date_debut)+"'  and  '"+df.format(date_fin)+"'\n" +condition+
                        "    order by DateBonRetourAchat desc  \n" +
                        "     ";


                Log.e("queryHis_br",""+queryHis_bc) ;
                PreparedStatement ps = con.prepareStatement(queryHis_bc);
                ResultSet rs = ps.executeQuery();


                  total_net_ht = 0;
                  total_tva = 0;
                  total_ttc = 0;

                while (rs.next()) {

                    String NumeroBonRetourVente = rs.getString("NumeroBonRetourAchat");
                    String RaisonSociale = rs.getString("RaisonSociale");
                    String NomUtilisateur = rs.getString("NomUtilisateur");



                    double TotalNetHT = rs.getDouble("TotalNetHT");
                    double TotalTVA = rs.getDouble("TotalTVA");
                    double TotalTTC = rs.getDouble("TotalTTC");
                    Date DateBonRetourVente = dtfSQL.parse(rs.getString("DateBonRetourAchat"));
                    String NumeroEtat = rs.getString("NumeroEtat");
                    String NumeroLibelle = rs.getString("Libelle");

                    if (NumeroEtat!="E00")
                    {
                        total_net_ht += TotalNetHT;
                        total_tva += TotalTVA;
                        total_ttc += TotalTTC;
                    }


                    BonRetourVente bonRetourVente = new BonRetourVente(NumeroBonRetourVente, DateBonRetourVente,NomUtilisateur , RaisonSociale  , TotalNetHT , TotalTVA  ,  TotalTTC  ,  NumeroEtat,NumeroLibelle);
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


        BonRetourAchatActivity.  txt_tot_ht.setText(instance.format(total_net_ht)+"");
        BonRetourAchatActivity. txt_tot_tva.setText(instance.format(total_tva)+"");
        BonRetourAchatActivity. txt_tot_ttc.setText(instance.format(total_ttc)+"");

        BonRetourAdapter bonRetourAdapter  = new BonRetourAdapter(activity  , listBonRetourVente)  ;
         lv_hist_br.setAdapter(bonRetourAdapter);

        listOnClick(listBonRetourVente);



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




}