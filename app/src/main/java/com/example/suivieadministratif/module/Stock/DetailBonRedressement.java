package com.example.suivieadministratif.module.Stock;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.module.vente.DetailLigneDevisVente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailBonRedressement extends AppCompatActivity {
    String NumeroBonRedressement="";
    ConnectionClass connectionClass;
    String CodeSociete, NomUtilisateur, CodeLivreur;
    final Context co = this;
    String user, password, base, ip;
    GridView gridEtat;

    ListView lv_ligne_bon_commande;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bon_redressement);
        Intent intent=getIntent();
        //"NumeroBonRedressement", "DateCreation",   "Depot","TotalTTC","Etat"
        NumeroBonRedressement=intent.getStringExtra("NumeroBonRedressement");
        String    DateCreation=intent.getStringExtra("DateBonRedressement");
        String    Depot=intent.getStringExtra("Depot");
        String    TotalTTC=intent.getStringExtra("TotalTTC");
        String    Etat=intent.getStringExtra("Etat");



        TextView txt_num_bc =(TextView)findViewById(R.id.txt_num_bc);
        TextView txt_prix_ttc =(TextView)findViewById(R.id.txt_prix_ttc);
        TextView txt_raison_client =(TextView)findViewById(R.id.txt_raison_client);
        TextView txt_date_bc =(TextView)findViewById(R.id.txt_date_bc);
        txt_num_bc.setText(NumeroBonRedressement);
        txt_prix_ttc.setText(TotalTTC);
        txt_raison_client.setText(Depot);
        txt_date_bc.setText(DateCreation);



        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Detail Devis");
        connectionClass = new ConnectionClass();
        SharedPreferences prefe = getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);
        lv_ligne_bon_commande=(ListView)findViewById(R.id.lv_ligne_bon_commande);
        FillList fillList =new FillList();
        fillList.execute("");
    }

    public class FillList extends AsyncTask<String, String, String> {
        String z = "";
        Boolean test = false;


        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();



        @Override
        protected void onPreExecute() {
            //  progressBar.setVisibility(View.VISIBLE);


        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {
            //  progressBar.setVisibility(View.GONE);

            String[] from = {"DesignationArticle", "Quantite",   "MontantTTC" };
            int[] views = {R.id.txt_article, R.id.txt_qt_article, R.id.txt_prix_ttc};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_lbc, from,
                    views);




            lv_ligne_bon_commande.setAdapter(ADA);


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {


                    String queryTable = "select  NumeroBonRedressement,CodeArticle,DesignationArticle,MontantTTC,Quantite  " +
                            " from LigneBonRedressement where NumeroBonRedressement='"+NumeroBonRedressement+"'";

                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("querydetailBonRed", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";

                    while (rs.next()) {

                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("CodeArticle", rs.getString("CodeArticle"));
                        datanum.put("DesignationArticle", rs.getString("DesignationArticle"));
                        datanum.put("Quantite", rs.getString("Quantite"));
                        datanum.put("MontantTTC", rs.getString("MontantTTC"));


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
    }







}
