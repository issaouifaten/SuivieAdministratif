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
import android.widget.Toast;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailLigneBonTransfert extends AppCompatActivity {

    String  NumeroBonTransfert="";
    ConnectionClass connectionClass;
    String CodeSociete, NomUtilisateur, CodeLivreur;
    final Context co = this;
    String user, password, base, ip;
    GridView gridEtat;

    ListView lv_ligne_bon_commande;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ligne_bon_transfert);

        Intent intent=getIntent();
        //{"NumeroBonTransfert", "DateCreation",   "DepotSource","TotalTTC","Etat","DepotDestination"};
        NumeroBonTransfert=intent.getStringExtra("NumeroBonTransfert");
        String    DateCreation=intent.getStringExtra("DateCreation");
        String    DepotDestination=intent.getStringExtra("DepotDestination");
        String    DepotSource=intent.getStringExtra("DepotSource");
        String    TotalTTC=intent.getStringExtra("TotalTTC");
        String    Etat=intent.getStringExtra("Etat");
        Toast.makeText(getApplicationContext(),""+NumeroBonTransfert,Toast.LENGTH_LONG).show();


        TextView txt_num_bc =(TextView)findViewById(R.id.txt_num_bc);
        TextView txt_prix_ttc =(TextView)findViewById(R.id.txt_prix_ttc);

        TextView txt_depot_destination =(TextView)findViewById(R.id.txt_depot_destination);
        TextView txt_depot_source =(TextView)findViewById(R.id.txt_depot_source);
        TextView txt_date_bc =(TextView)findViewById(R.id.txt_date_bc);
        txt_num_bc.setText( ""+NumeroBonTransfert);
        txt_prix_ttc.setText(TotalTTC);
        txt_depot_destination.setText(DepotDestination);

        txt_depot_source.setText(DepotSource);
        txt_date_bc.setText(DateCreation);




        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Detail Bon Transfert ");
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
            int[] views = {R.id.txt_article, R.id.txt_quantite, R.id.txt_prix_ttc};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_ligne_piece, from,
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


                    String queryTable = " select  CodeArticle,DesignationArticle, convert(numeric(18,0),Quantite)as Quantite ,MontantTTC  from LigneBonTransfert where  NumeroBonTransfert='"+ NumeroBonTransfert+"' ";

                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("queryDetailbontransfert", queryTable);

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
