package com.example.suivieadministratif.module.tresorerie;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.module.Stock.BonSortie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PortfeuilCheque extends AppCompatActivity {
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
    Spinner spinArticle;
    String condition = "",conditionClient="";
    ArrayList<String> dataRaisonSociale,datacode,dataRaisonSocialeFRS,datacodeFRS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfeuil_cheque);


        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " :Portfeuil cheque ");


        connectionClass = new ConnectionClass();

        SharedPreferences prefe = getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);

        // SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);

        txt_datedebut = (TextView) findViewById(R.id.txt_date_debut);
        txt_datefin = (TextView) findViewById(R.id.txt_date_fin);
        txt_total = (TextView) findViewById(R.id.txt_total);
        gridEtat = (GridView) findViewById(R.id.grid_detail);
        spinArticle = (Spinner) findViewById(R.id.spinner);
        CardView card_date_debut = (CardView) findViewById(R.id.card_date_debut);
        CardView card_date_fin = (CardView) findViewById(R.id.card_date_fin);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        EditText search_bar_client=(EditText) findViewById(R.id.search_bar_client);
        search_bar_client.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if( s.length()>1) {
                    condition = " and(  Reference  like'%" + s + "%'  or Banque.RaisonSociale like'%"+s+"%'  ) ";

                }else{
                    condition="";
                }
            FillList fillList = new FillList();
                fillList.execute("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

        GetDataSpinner getDataSpinner=new GetDataSpinner();
        getDataSpinner.execute("");

        spinArticle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ;
                String st_code = datacode.get(position);
                if (position == 0) {
                    conditionClient = "";
                } else {
                    conditionClient = " and CodeTiers='"+st_code+"'";
                }

                FillList fillList = new FillList();
                fillList.execute("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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


            final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
            instance.setMinimumFractionDigits(3);
            instance.setMaximumFractionDigits(3);
            txt_total.setText(instance.format(total_gloabl));


            String[] from = {"DatePiece", "RaisonSociale", "NumeroPiece",  "Solde", "Reference","Banque"};
            int[] views = {R.id.txt_nom_tiers, R.id.txt_libelle, R.id.txt_numero_operation, R.id.txt_debit, R.id.txt_reference,R.id.txt_banque};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_portfeuil, from,
                    views);





            gridEtat.setAdapter(ADA);


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {


                    String queryTable = "select DatePiece,NumeroPiece,Reference,Solde,Porteur," +
                            "Vue_CaisseRecette.RaisonSociale,CodeTiers ,Banque.RaisonSociale as Banque\n" +
                            "from Vue_CaisseRecette\n" +
                            "inner join Banque on Banque.CodeBanque=Vue_CaisseRecette.CodeBanque\n" +
                            " where  Vue_CaisseRecette.CodeModeReglement='C' and Vue_CaisseRecette.Libelle='Reception Regelement'\n" +
                            " and DatePiece between '"+datedebut+"' and '"+datefin+"'"+conditionClient+condition;


                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("query", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";

                    while (rs.next()) {

                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("CodeTiers", rs.getString("CodeTiers"));
                        datanum.put("RaisonSociale", rs.getString("RaisonSociale"));
                        datanum.put("Banque", rs.getString("Banque"));
                        datanum.put("NumeroPiece", rs.getString("NumeroPiece"));
                        datanum.put("Porteur", rs.getString("Porteur"));
                        datanum.put("DatePiece", ""+rs.getDate("DatePiece"));

                        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
                        instance.setMinimumFractionDigits(3);
                        instance.setMaximumFractionDigits(3);

                        datanum.put("Solde", instance.format( rs.getFloat("Solde")));
                        datanum.put("Reference",  rs.getString("Reference"));



                        total_gloabl += rs.getFloat("Solde") ;
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



    public class GetDataSpinner extends AsyncTask<String, String, String> {
        String z = "  ";

        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();

        @Override
        protected void onPreExecute() {
            //  Log.e("frs", querylist);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            //   Toast.makeText(getApplicationContext(), r, Toast.LENGTH_SHORT).show();
            //   Toast.makeText(getApplicationContext(), r, Toast.LENGTH_SHORT).show();
            String[] array = datacode.toArray(new String[0]);


            ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getApplicationContext(),
                    R.layout.spinner, dataRaisonSociale);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinArticle.setAdapter(adapter);






        }
        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    PreparedStatement stmt;
                    dataRaisonSociale = new ArrayList<String>();
                    datacode = new ArrayList<String>();

                    String querydepot="select CodeClient,RaisonSociale from Client ";

                    stmt = con.prepareStatement(querydepot);
                    ResultSet rsss = stmt.executeQuery();
                    Log.e("spindepot", querydepot);
                    dataRaisonSociale.add("Tout les  Client");
                    datacode.add("");
                    while (rsss.next()) {
                        String RaisonSociale= rsss.getString("RaisonSociale");
                        dataRaisonSociale.add(RaisonSociale);
                        String CodeDepot= rsss.getString("CodeClient");
                        datacode.add(CodeDepot);

                    }


                }
            } catch (SQLException ex) {
                z = "list" + ex.toString();

            } catch (Exception e) {

            }
            return z;
        }
    }


}
