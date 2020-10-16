package com.example.suivieadministratif;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Globale extends AppCompatActivity {
    ConnectionClass connectionClass;
    String CodeSociete, NomUtilisateur, CodeLivreur;
    final Context co = this;
    String user, password, base, ip;
    GridView gridSituation;
    TextView txt_datedebut, txt_datefin, txt_total;
    String datedebut = "", datefin = "";
    DatePicker datePicker;
    GridView gridEtat;
    ProgressBar progressBar;
    ArrayList<String> data_CodeRespensable, data_NomRespensable;
    Spinner spinRespensable;
    String condition = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_globale);
        connectionClass = new ConnectionClass();
        SharedPreferences prefe = getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);

        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);

        txt_datedebut = (TextView) findViewById(R.id.txt_date_debut);
        txt_datefin = (TextView) findViewById(R.id.txt_date_fin);
        txt_total = (TextView) findViewById(R.id.txt_total);
        gridEtat = (GridView) findViewById(R.id.grid_detail);

        CardView card_date_debut = (CardView) findViewById(R.id.card_date_debut);
        CardView card_date_fin = (CardView) findViewById(R.id.card_date_fin);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        card_date_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(co);
                View px = li.inflate(R.layout.diagcalend, null);
                AlertDialog.Builder alt = new AlertDialog.Builder(co);
                alt.setIcon(R.drawable.i2s);
                alt.setView(px);
                alt.setTitle("date");
                datePicker = (DatePicker) px.findViewById(R.id.datedebut);
                alt.setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface di, int i) {

                                Date d = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
                                datedebut = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH)
                                        .format(d);

                                txt_datedebut.setText(datedebut);
                                FillList fillList = new FillList();
                                fillList.execute("");


                            }
                        });

                AlertDialog dd = alt.create();
                dd.show();
            }
        });


        card_date_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(co);
                View px = li.inflate(R.layout.diagcalend, null);
                AlertDialog.Builder alt = new AlertDialog.Builder(co);
                alt.setIcon(R.drawable.i2s);
                alt.setView(px);
                alt.setTitle("date");
                datePicker = (DatePicker) px.findViewById(R.id.datedebut);
                alt.setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface di, int i) {

                                Date d = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
                                datefin = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH)
                                        .format(d);

                                txt_datefin.setText(datefin);


                                FillList fillList = new FillList();
                                fillList.execute("");
                            }
                        });

                AlertDialog dd = alt.create();
                dd.show();
            }
        });


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        datefin = sdf.format(calendar.getTime());
        calendar.add(Calendar.MONTH, -2);
        datedebut = sdf.format(calendar.getTime());
        txt_datedebut.setText(datedebut);
        txt_datefin.setText(datefin);
        FillList fillList = new FillList();
        fillList.execute("");


    }


    public class FillList extends AsyncTask<String, String, String> {
        String z = "";
        Boolean test = false;


        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();
        float total_gloabl = 0;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);

            total_gloabl = 0;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            condition = "";
            txt_total.setText("" + total_gloabl);

            String[] from = {"CodeClient", "RaisonSociale", "NomRepresentant", "NumeroPiece", "TotalTTC", "NomRAD","NbSociete","NomSociete"};
            int[] views = {R.id.txt_code, R.id.txt_designation, R.id.txt_nom_representant, R.id.tx_num_piece, R.id.txt_total_ttc, R.id.txt_nom_rad};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_chiffre_affaire_global, from,
                    views);


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
                    convertView = layoutInflater.inflate(R.layout.item_chiffre_affaire_global_societe, null);
                    final TextView txt_code = (TextView) convertView.findViewById(R.id.txt_code);
                    final TextView txt_designation = (TextView) convertView.findViewById(R.id.txt_designation);
                    final TextView txt_nom_representant = (TextView) convertView.findViewById(R.id.txt_nom_representant);
                    final TextView tx_num_piece = (TextView) convertView.findViewById(R.id.tx_num_piece);
                    final TextView txt_total_ttc = (TextView) convertView.findViewById(R.id.txt_total_ttc);
                    final TextView txt_nom_rad = (TextView) convertView.findViewById(R.id.txt_nom_rad);
                    final TextView txt_nom_ste = (TextView) convertView.findViewById(R.id.txt_nom_ste);

                    final HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);
                    String CodeClient = (String) obj.get("CodeClient");
                    String RaisonSociale = (String) obj.get("RaisonSociale");
                    String NomRepresentant = (String) obj.get("NomRepresentant");

                    String NumeroPiece = (String) obj.get("NumeroPiece");
                    String TotalTTC = (String) obj.get("TotalTTC");
                    String NomRAD = (String) obj.get("NomRAD");
                    String NbSociete = (String) obj.get("NbSociete");
                    String NomSociete = (String) obj.get("NomSociete");

//  String[] from = {"CodeClient", "RaisonSociale" ,"TypeOperation","NumeroPiece","TotalTTC"};
//

                    txt_code.setText(CodeClient);
                    txt_nom_ste.setText(NomSociete);

                    txt_nom_representant.setText(NomRepresentant);
                    txt_designation.setText(RaisonSociale);
                    txt_nom_rad.setText(NomRAD);

                    tx_num_piece.setText(NumeroPiece);
                    txt_total_ttc.setText(TotalTTC);


                    return convertView;
                }
            };


            gridEtat.setAdapter(baseAdapter);


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    String queryListServeur = "select * from ListeSociete";

                    PreparedStatement ps_ = con.prepareStatement(queryListServeur);
                    Log.e("queryListServeur", queryListServeur);

                    ResultSet rs_list = ps_.executeQuery();
                    z = "e";
                    int nb_ste = 1;
                    while (rs_list.next()) {
                        String ip_list = rs_list.getString("IP");
                        String base_list = rs_list.getString("NomBase");
                        final String nom_list = rs_list.getString("NomSociete");


                        Connection con_local = connectionClass.CONN(ip_list, password, user, base_list);


                        ///////////////////////////////////////


                        String queryTable = "select CodeClient,RaisonSociale,TotalTTC,NumeroFactureVente,\n" +
                                "(select Nom from Respensable where Respensable.CodeRespensable=Vue_GlobalVente.CodeRepresentant)\n" +
                                " as NomRepresentant ," +
                                " (select Nom from Respensable where Respensable.CodeRespensable=Vue_GlobalVente.CodeRespensableAdministration) as NomRAD  \n" +
                                " from Vue_GlobalVente  where DateFactureVente IN('" + datedebut + "','" + datefin + "') " + condition;

                        PreparedStatement ps = con_local.prepareStatement(queryTable);
                        Log.e("query", queryTable);

                        ResultSet rs = ps.executeQuery();
                        z = "e";


                        while (rs.next()) {

                            Map<String, String> datanum = new HashMap<String, String>();
                            datanum.put("CodeClient", rs.getString("CodeClient"));
                            datanum.put("RaisonSociale", rs.getString("RaisonSociale"));
                            datanum.put("NumeroPiece", rs.getString("NumeroFactureVente"));
                            datanum.put("TotalTTC", rs.getString("TotalTTC"));

                            datanum.put("NomRepresentant", rs.getString("NomRepresentant"));
                            datanum.put("NomRAD", rs.getString("NomRAD"));
                            datanum.put("NbSociete", nb_ste + "");
                            datanum.put("NomSociete", nom_list);

                            total_gloabl += rs.getFloat("TotalTTC");
                            prolist.add(datanum);


                            test = true;


                            z = "succees";
                        }

                        nb_ste++;
                        //////////////////////////////


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
