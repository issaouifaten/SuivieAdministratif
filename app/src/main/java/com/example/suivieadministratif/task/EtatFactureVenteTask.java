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
import com.example.suivieadministratif.module.vente.EtatDevisVente;
import com.example.suivieadministratif.module.vente.EtatFactureVente;
import com.example.suivieadministratif.module.vente.LigneDevisVente;
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

public class EtatFactureVenteTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv_list;
    ProgressBar pb;

    Date  date_debut  ; Date date_fin ;
    String CodeClientSelected ;


    String z = "";
    Boolean test = false;
    List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();

    double total_net_ht = 0;
    double total_tva = 0;
    double total_ttc = 0;

    ConnectionClass connectionClass;
    String user, password, base, ip;
    SimpleDateFormat  sdf  = new SimpleDateFormat("dd/MM/yyyy");


    public EtatFactureVenteTask(Activity activity, Date  date_debut , Date date_fin  , ListView lv_list, ProgressBar pb , String CodeClientSelected  ) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.lv_list = lv_list;
        this.pb = pb;
        this.CodeClientSelected=CodeClientSelected;


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
                if (!CodeClientSelected.equals(""))
                {
                    condition += "  and CodeClient=   '"+CodeClientSelected+"' "  ;
                }

            String    queryTable = " select NumeroFactureVente,DateFactureVente,NomUtilisateur , CodeClient, TotalNetHT , TotalTVA  ,  TotalTTC, Etat.Libelle as Etat\n" +
                    "     from FactureVente\n" +
                    "     inner join Etat on Etat.NumeroEtat=FactureVente.NumeroEtat\n" +condition+
                    "     where DateFactureVente between '" +sdf.format(date_debut)+"'and '" + sdf.format(date_fin)+"'    order by NumeroFactureVente desc";


                PreparedStatement ps = con.prepareStatement(queryTable);
                Log.e("queryfactureVente", queryTable);

                ResultSet rs = ps.executeQuery();
                z = "e";

                while (rs.next()) {

                    Map<String, String> datanum = new HashMap<String, String>();
                    datanum.put("NumeroFactureVente", rs.getString("NumeroFactureVente"));
                    datanum.put("NomUtilisateur", rs.getString("NomUtilisateur"));
                    datanum.put("CodeClient", rs.getString("CodeClient"));



                    final NumberFormat format  = NumberFormat.getNumberInstance(Locale.FRENCH);
                    format.setMinimumFractionDigits(3);
                    format.setMaximumFractionDigits(3);

                    datanum.put("TotalNetHT", format .format(rs.getDouble("TotalNetHT")));
                    datanum.put("TotalTVA"  , format .format(rs.getDouble("TotalTVA")));
                    datanum.put("TotalTTC"  , format .format(rs.getDouble("TotalTTC")));


                    datanum.put("Etat", rs.getString("Etat"));

                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                    datanum.put("DateFactureVente", df.format(rs.getDate("DateFactureVente")));

                    total_net_ht += rs.getDouble("TotalNetHT");
                    total_tva += rs.getDouble("TotalTVA");
                    total_ttc += rs.getDouble("TotalTTC");

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

        // TotalNetHT , TotalTVA  ,  TotalTTC
        String[] from = {"NumeroFactureVente", "DateFactureVente","NomUtilisateur" ,  "TotalNetHT", "TotalTVA", "TotalTTC", "Etat"};
        int[] views = {R.id.txt_num_piece, R.id.txt_date_bc, R.id.txt_etablie_par ,  R.id.txt_prix_net_ht, R.id.txt_prix_tva, R.id.txt_prix_ttc, R.id.txt_libelle_etat};
        final SimpleAdapter ADA = new SimpleAdapter(activity ,
                prolist, R.layout.item_etat_entete, from, views);


        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);


        EtatFactureVente. txt_tot_ht.setText(instance.format(total_net_ht) + "");
        EtatFactureVente. txt_tot_tva.setText(instance.format(total_tva) + "");
        EtatFactureVente.  txt_tot_ttc.setText(instance.format(total_ttc) + "");


        lv_list.setAdapter(ADA);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                        .getItem(position);

                String NumeroFactureVente = (String) obj.get("NumeroFactureVente");
                String DateCreation = (String) obj.get("NumeroFactureVente");
                String Etat = (String) obj.get("Etat");
                String TotalTTC = (String) obj.get("TotalTTC");


                Intent intent = new Intent(activity, LigneFactureVente.class);
                intent.putExtra("NumeroFactureVente", NumeroFactureVente);
                intent.putExtra("DateCreation", DateCreation);
                intent.putExtra("TotalTTC", TotalTTC);
                intent.putExtra("Etat", Etat);
                activity. startActivity(intent);


            }
        });


    }


}