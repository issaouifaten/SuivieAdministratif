package com.example.suivieadministratif.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.activity.HomeActivity;
import com.example.suivieadministratif.activity.LoginActivity;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuServeur extends AppCompatActivity {
    ConnectionClass connectionClass;
    String CodeSociete, NomUtilisateur, CodeLivreur;
    final Context co = this;
    String user, password, base, ip;
    GridView gridSituation;

    GridView gridServeur;
    ProgressBar progressBar;

    public   static Activity  fa  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_serveur);
        connectionClass = new ConnectionClass();

        fa =  this  ;

        SharedPreferences prefe = getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);

        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);
        gridServeur=(GridView)findViewById(R.id.grid_serveur);

        FillList fillList =new FillList();
        fillList.execute("");


    }




    public class FillList extends AsyncTask<String, String, String> {
        String z = "";
        Boolean test = false;


        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();
        float total_gloabl=0;

        @Override
        protected void onPreExecute() {
          //  progressBar.setVisibility(View.VISIBLE);

            total_gloabl=0;
        }


        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    String queryTable = " select  *  from ListeSociete";

                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("query_list_soc", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";

                    while (rs.next()) {

                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("CodeSociete", rs.getString("CodeSociete"));
                        datanum.put("NomSociete", rs.getString("NomSociete"));
                        datanum.put("IP", rs.getString("IP"));
                        datanum.put("NomBase", rs.getString("NomBase"));


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
            //    progressBar.setVisibility(View.GONE);


            String[] from = {"NomSociete", "CodeSociete", "IP", "NomBase" };
             int[] views = {R.id.txt_code, R.id.txt_designation, R.id.txt_nom_representant, R.id.tx_num_piece, R.id.txt_total_ttc,R.id.txt_nom_rad};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_chiffre_affaire_global, from, views);



            final BaseAdapter baseAdapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return prolist.size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }


                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    final LayoutInflater layoutInflater = LayoutInflater.from(co);
                    convertView = layoutInflater.inflate(R.layout.item_serveur, null);

                    final ImageView img_societe = (ImageView) convertView.findViewById(R.id.img_societe);
                    final TextView txt_nomsociete = (TextView) convertView.findViewById(R.id.txt_nomsociete);
                    final CardView btn_login = (CardView) convertView.findViewById(R.id.btn_login);



                    final HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);
                    final   String NomSociete = (String) obj.get("NomSociete");
                    final  String NomBase = (String) obj.get("NomBase");
                    final  String IP = (String) obj.get("IP");


//  String[] from = {"CodeClient", "RaisonSociale" ,"TypeOperation","NumeroPiece","TotalTTC"};
//

                    txt_nomsociete.setText(NomSociete);

                    if (NomSociete.equals("CCM"))
                    {
                        img_societe.setImageResource(R.drawable.ic_logo_ccm);
                    }
                    else  if (NomSociete.equals("GMT"))
                    {
                        img_societe.setImageResource(R.drawable.ic_logo_gmt);
                    }
                    else  if (NomSociete.contains("I2S"))
                    {
                        img_societe.setImageResource(R.drawable.i2s);
                    }
                    else  if (NomSociete.contains("CMVI"))
                    {
                        img_societe.setImageResource(R.drawable.cmvi_logo);
                    }

                    else  if (NomSociete.contains("MTD"))
                    {
                        img_societe.setImageResource(R.drawable.mtd_logo_transportatio);
                    }
                    btn_login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences pref = getSharedPreferences(Param.PEF_SERVER , Context.MODE_PRIVATE);
                            SharedPreferences.Editor edt = pref.edit();
                            edt.putString("base", NomBase);
                            edt.putString("ip", IP);
                            edt.putString("NomSociete", NomSociete);

                            edt.commit();

                            Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);

                        }
                    });







                    return convertView;
                }
            };


            gridServeur.setAdapter(baseAdapter);


        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menudeconnecter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences pref = getSharedPreferences(Param.PREF_USER, Context.MODE_PRIVATE);
            SharedPreferences.Editor edt = pref.edit();
            edt.putBoolean("etat", false);
            edt.commit();

            finish();
            Intent inte = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(inte);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
