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
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetailLigneBonEntre extends AppCompatActivity {

    String  NumeroBonEntrer="";
    ConnectionClass connectionClass;
    String   NomUtilisateur ;
    final Context co = this;
    String user, password, base, ip;


    ListView lv_ligne_piece;
    ProgressBar  pb  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ligne_piece);

        Intent intent=getIntent();
        //" NumeroBonEntrer", "DateCreation",   "Depot","TotalTTC","Etat"
        NumeroBonEntrer=intent.getStringExtra("NumeroBonEntrer");
        String    DateCreation=intent.getStringExtra("DateCreation");
        String    Depot=intent.getStringExtra("Depot");
        String    TotalTTC=intent.getStringExtra("TotalTTC");
        String    Etat=intent.getStringExtra("Etat");
        Toast.makeText(getApplicationContext(),""+NumeroBonEntrer,Toast.LENGTH_LONG).show();


        TextView txt_num_piece  =(TextView)findViewById(R.id.txt_num_piece);
        TextView txt_prix_ttc =(TextView)findViewById(R.id.txt_prix_ttc);

        TextView txt_raison_client =(TextView)findViewById(R.id.txt_raison_client);
        TextView txt_date_bc =(TextView)findViewById(R.id.txt_date_bc);
        txt_num_piece.setText( ""+NumeroBonEntrer);
        txt_prix_ttc.setText(TotalTTC);
        txt_raison_client.setText(Depot);
        txt_date_bc.setText(DateCreation);




        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Détail Bon Entrée ");
        connectionClass = new ConnectionClass();
        SharedPreferences prefe = getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);
        lv_ligne_piece = (ListView)findViewById(R.id.lv_ligne_piece);
        pb= (ProgressBar)  findViewById(R.id.pb)  ;
        FillList fillList =new FillList();
        fillList.execute("");
    }

    public class FillList extends AsyncTask<String, String, String> {
        String z = "";
        Boolean test = false;


        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();



        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);


        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {
            pb.setVisibility(View.GONE);

            String[] from = {"DesignationArticle", "CodeArticle", "MontantHT", "MontantTVA", "MontantTTC", "Quantite", "MontantTTC", "Libelle_2"};
            int[] views = {R.id.txt_designation,R.id.txt_code_article  , R.id.txt_net_ht, R.id.txt_taux_remise, R.id.txt_mnt_ttc, R.id.txt_quantite, R.id.txt_prix_ttc,R.id.libelle_2};

            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_ligne_piece, from,
                    views);




            lv_ligne_piece.setAdapter(ADA);


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {


                    String queryTable = " select  CodeArticle,DesignationArticle, convert(numeric(18,0),Quantite)as Quantite ,  MontantHT , MontantTVA   , MontantTTC    " +
                            "   from LigneBonEntrer where  NumeroBonEntrer='"+ NumeroBonEntrer+"' ";

                    PreparedStatement  ps = con.prepareStatement(queryTable);
                    Log.e("LigneBonEntrer", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";

                    while (rs.next()) {

                        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
                        instance.setMinimumFractionDigits(3);
                        instance.setMaximumFractionDigits(3);

                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("CodeArticle", rs.getString("CodeArticle"));
                        datanum.put("DesignationArticle", rs.getString("DesignationArticle"));
                        datanum.put("Quantite", rs.getString("Quantite"));

                        datanum.put("MontantHT", instance.format(rs.getDouble("MontantHT")));
                        datanum.put("MontantTVA", instance.format(rs.getDouble("MontantTVA")) +"");
                        datanum.put("MontantTTC", instance.format(rs.getDouble("MontantTTC")));
                        datanum.put("Libelle_2",   "Mnt TVA");

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
