package com.example.suivieadministratif.task;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.module.achat.FactureAchat;
import com.example.suivieadministratif.module.achat.LigneFactureAchat;
import com.example.suivieadministratif.module.vente.EtatFactureVente;
import com.example.suivieadministratif.module.vente.LigneFactureVente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EtatFactureAchatTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv_list;
    ProgressBar pb;

    Date  date_debut  ; Date date_fin ;
    String CodeFournisseurSelected ;


    String z = "";
    Boolean test = false;
    List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();

    double total_net_ht = 0;
    double total_tva = 0;
    double total_ttc = 0;

    ConnectionClass connectionClass;
    String user, password, base, ip;
    SimpleDateFormat  sdf  = new SimpleDateFormat("dd/MM/yyyy");


    public EtatFactureAchatTask(Activity activity, Date  date_debut , Date date_fin  , ListView lv_list, ProgressBar pb , String CodeFournisseurSelected  ) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.lv_list = lv_list;
        this.pb = pb;
        this.CodeFournisseurSelected=CodeFournisseurSelected;


        SharedPreferences prefe = activity.getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
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


                String queryTable = "select NumeroFactureAchat,RaisonSociale , NomUtilisateur  ,CodeFournisseur  , TotalNetHT , TotalTVA  ,  TotalTTC  , DateReferenceFournisseur ,Etat.Libelle as Etat,NomUtilisateur\n" +
                        ",Etat.NumeroEtat from FactureAchat \n" +
                        "inner join Etat on Etat.NumeroEtat=FactureAchat.NumeroEtat\n" +
                        "where DateReferenceFournisseur between '"+sdf.format(date_debut)+"' and '"+sdf.format(date_fin)+"'\n" +condition+
                        "order by NumeroFactureAchat desc";

                PreparedStatement ps = con.prepareStatement(queryTable);
                Log.e("queryfactureAchat", queryTable);

                ResultSet rs = ps.executeQuery();
                z = "e";

                while (rs.next()) {

                    Map<String, String> datanum = new HashMap<String, String>();
                    datanum.put("NumeroFactureAchat", rs.getString("NumeroFactureAchat"));
                    datanum.put("NomUtilisateur", rs.getString("NomUtilisateur"));
                    datanum.put("CodeFournisseur", rs.getString("CodeFournisseur"));
                    datanum.put("RaisonSociale", rs.getString("RaisonSociale"));

                    datanum.put("TotalNetHT", rs.getString("TotalNetHT"));
                    datanum.put("TotalTVA", rs.getString("TotalTVA"));
                    datanum.put("TotalTTC", rs.getString("TotalTTC"));

                    datanum.put("Etat", rs.getString("Etat"));
                    datanum.put("NumeroEtat", rs.getString("NumeroEtat"));

                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                    datanum.put("DateReferenceFournisseur", df.format(rs.getDate("DateReferenceFournisseur")));

                    if(!(rs.getString("NumeroEtat").equals("E00")||rs.getString("NumeroEtat").equals("E40")))
                    {
                        total_net_ht += rs.getDouble("TotalNetHT");
                        total_tva    += rs.getDouble("TotalTVA");
                        total_ttc    += rs.getDouble("TotalTTC");
                    }


                    prolist.add(datanum);


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


        String[] from = {"NumeroFactureAchat", "DateReferenceFournisseur", "NomUtilisateur"  , "RaisonSociale", "TotalNetHT", "TotalTVA", "TotalTTC","Etat"};
        int[] views = {R.id.txt_num_piece, R.id.txt_date_bc, R.id.txt_etablie_par , R.id.txt_raison_client, R.id.txt_prix_net_ht, R.id.txt_prix_tva, R.id.txt_prix_ttc,  R.id.txt_libelle_etat};
        final SimpleAdapter ADA = new SimpleAdapter(activity,
                prolist, R.layout.item_etat_entete, from,
                views);


        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);

        FactureAchat.txt_tot_ht.setText(instance.format(total_net_ht)+"");
        FactureAchat.txt_tot_tva.setText(instance.format(total_tva)+"");
        FactureAchat.txt_tot_ttc.setText(instance.format(total_ttc)+"");


        lv_list.setAdapter(ADA);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                        .getItem(position);

                String  NumeroFactureAchat = (String) obj.get("NumeroFactureAchat");
                String  DateReferenceFournisseur = (String) obj.get("DateReferenceFournisseur");
                String  RaisonSociale = (String) obj.get("RaisonSociale");
                String  Etat = (String) obj.get("Etat");
                String  TotalTTC = (String) obj.get("TotalTTC");

                Intent intent=new Intent(activity, LigneFactureAchat.class);
                intent.putExtra("NumeroFactureAchat",NumeroFactureAchat);
                intent.putExtra("DateReferenceFournisseur",DateReferenceFournisseur);
                intent.putExtra("RaisonSociale",RaisonSociale);
                intent.putExtra("TotalTTC",TotalTTC);
                intent.putExtra("Etat",Etat);
                activity.startActivity(intent);

            }
        });


    }


}