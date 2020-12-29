package com.example.suivieadministratif.ui.statistique_rapport_activite.Client;

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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;

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

public class MoyenReglementClient extends AppCompatActivity {

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
    ArrayList<String> data_CodeClient, data_NomClient;
    Spinner spinRespensable, spinClient;
    String condition = "", conditionclient = "", conditionArticle = "";
    EditText edt_recherche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moyen_reglement_client);

        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Moyen Reglement Client");

        connectionClass = new ConnectionClass();

        SharedPreferences prefe = getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);


        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);

        txt_datedebut = (TextView) findViewById(R.id.txt_date_debut);
        txt_datefin = (TextView) findViewById(R.id.txt_date_fin);
        txt_total = (TextView) findViewById(R.id.txt_total);
        gridEtat = (GridView) findViewById(R.id.grid_detail);

        spinClient = (Spinner) findViewById(R.id.spinnerclient);

        GetDataSpinnerClient getDataSpinnerClient = new GetDataSpinnerClient();
        getDataSpinnerClient.execute("");

        CardView card_date_debut = (CardView) findViewById(R.id.card_date_debut);
        CardView card_date_fin = (CardView) findViewById(R.id.card_date_fin);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
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
        calendar.add(Calendar.DATE, -1);
        datedebut = sdf.format(calendar.getTime());
        txt_datedebut.setText(datedebut);
        txt_datefin.setText(datefin);
        spinClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String codeclient = data_CodeClient.get(position);
                    conditionclient = "  and CodeClient='" + codeclient + "'";
                    FillList fillList = new FillList();
                    fillList.execute("");
                } else {
                    conditionclient = "";
                    FillList fillList = new FillList();
                    fillList.execute("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    public class FillList extends AsyncTask<String, String, String> {
        String z = "";
        Boolean test = false;

        ArrayList<Integer> list = new ArrayList<Integer>();
        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();
        float total_gloabl = 0;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            list.clear();
            total_gloabl = 0;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            condition = "";
            txt_total.setText("" + total_gloabl);

            String[] from = {"CodeClient", "RaisonSociale", "NbJour", "CodeModeReglement","NumeroBonLivraisonVente","DatePiece","mnt","Observation",
                    "NumeroPiece", "MontantLettrer", "CodeForceVente", "TotalTTC", "Echeance", "DesignationArticle", "NbJourParPourcentage"};
            int[] views = {R.id.txt_code, R.id.txt_designation, R.id.txt_nom_representant, R.id.tx_num_piece, R.id.txt_total_ttc, R.id.txt_nom_rad};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_suivie_commande_client, from,
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
                    convertView = layoutInflater.inflate(R.layout.item_moyen_reglement_client, null);
                    final TextView txt_code = (TextView) convertView.findViewById(R.id.txt_code);
                    final TextView txt_designation = (TextView) convertView.findViewById(R.id.txt_designation);
                    final TextView tx_num_piece = (TextView) convertView.findViewById(R.id.tx_num_piece);
                    final TextView txt_date_piece = (TextView) convertView.findViewById(R.id.txt_date_piece);
                    final TextView txt_Echeance = (TextView) convertView.findViewById(R.id.txt_Echeance);
                    final TextView txt_code_reg = (TextView) convertView.findViewById(R.id.txt_code_reg);
                    final TextView txt_montant_lettre = (TextView) convertView.findViewById(R.id.txt_montant_lettre);
                    final TextView txt_nbj_per = (TextView) convertView.findViewById(R.id.txt_nbj_per);
                    final TextView qt_nbj = (TextView) convertView.findViewById(R.id.qt_nbj);
                    final TextView txt_montant_cmd = (TextView) convertView.findViewById(R.id.montant_cmd);

                    final CardView layout_vendeur = (CardView) convertView.findViewById(R.id.card_client_);

                    final HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);
                    String CodeClient = (String) obj.get("CodeClient");
                    String RaisonSociale = (String) obj.get("RaisonSociale");
                    String NumeroPiece = (String) obj.get("NumeroPiece");
                    String MontantLettrer = (String) obj.get("MontantLettrer");


                    String TotalTTC = (String) obj.get("TotalTTC");
                    String Echeance = (String) obj.get("Echeance");
                    String NbJour = (String) obj.get("NbJour");
                    String CodeModeReglement = (String) obj.get("CodeModeReglement");

                    String NbJourParPourcentage = (String) obj.get("NbJourParPourcentage");
                    String DatePiece = (String) obj.get("DatePiece");



                    txt_Echeance.setText(Echeance);
                    tx_num_piece.setText(NumeroPiece);
                    txt_code.setText(CodeClient);
                    txt_date_piece.setText(DatePiece);
                    txt_designation.setText(RaisonSociale);
                    txt_code_reg.setText(CodeModeReglement);
                    txt_montant_lettre.setText(MontantLettrer);
                    txt_nbj_per.setText(NbJourParPourcentage);
                    qt_nbj.setText(NbJour);
                    txt_montant_cmd.setText(TotalTTC);


                    if (list.contains(position)) {
                        layout_vendeur.setVisibility(View.VISIBLE);
                    } else {
                        layout_vendeur.setVisibility(View.GONE);
                    }

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


                    String queryTable = "select CodeClient,RaisonSociale,NumeroPiece,CONVERT(date, DatePiece,103) as DatePiece ," +
                            "MontantLettrer,TotalTTC,\n" +
                            "CONVERT(date, Echeance,103) as Echeance,\n" +
                            "CodeModeReglement,NbJour ,convert(numeric(18,2),NbJour*(MontantLettrer/TotalTTC) )as NbJourParPourcentage\n" +
                            "from\tLettrageReglementClient\n" +
                            "where DatePiece between '"+datedebut+"' and '"+datefin+"'\n"+conditionclient;

                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("query", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";
                    String ancien = "";
                    int posi = 0;
                    while (rs.next()) {

                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("CodeClient", rs.getString("CodeClient"));
                        datanum.put("RaisonSociale", rs.getString("RaisonSociale"));
                        datanum.put("NumeroPiece", rs.getString("NumeroPiece"));
                        datanum.put("DatePiece", rs.getString("DatePiece"));
                        datanum.put("MontantLettrer", rs.getString("MontantLettrer"));
                        datanum.put("TotalTTC", rs.getString("TotalTTC"));
                        datanum.put("Echeance", rs.getString("Echeance"));

                        datanum.put("CodeModeReglement", rs.getString("CodeModeReglement"));
                        datanum.put("NbJour", rs.getString("NbJour"));
                        datanum.put("NbJourParPourcentage", rs.getString("NbJourParPourcentage"));

                        String nom = rs.getString("NumeroPiece").toUpperCase();
                        if (!ancien.equals(nom)) {
                            ancien = nom;

                            list.add(posi);
                        }
                        posi++;

                        total_gloabl += rs.getFloat("MontantLettrer");
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
            // pbbar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(String r) {


            ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getApplicationContext(),
                    R.layout.spinner, data_NomRespensable);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            spinRespensable.setAdapter(adapter);


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    PreparedStatement stmt, stmt2;
                    data_CodeRespensable = new ArrayList<String>();
                    data_NomRespensable = new ArrayList<String>();
                    stmt = con.prepareStatement("select  CodeRespensable,Nom from Respensable where Actif=1");
                    ResultSet rsss = stmt.executeQuery();
                    data_CodeRespensable.add("");
                    data_NomRespensable.add("Tout");
                    while (rsss.next()) {
                        String id = rsss.getString("CodeRespensable");
                        String designation = rsss.getString("Nom");
                        data_CodeRespensable.add(id);
                        data_NomRespensable.add(designation);

                    }


                }
            } catch (SQLException ex) {
                z = "list" + ex.toString();

            } catch (Exception e) {

            }
            return z;
        }
    }


    public class GetDataSpinnerClient extends AsyncTask<String, String, String> {
        String z = "  ";

        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();

        @Override
        protected void onPreExecute() {
            //  Log.e("frs", querylist);
            // pbbar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(String r) {


            ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getApplicationContext(),
                    R.layout.spinner, data_NomClient);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            spinClient.setAdapter(adapter);


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    PreparedStatement stmt, stmt2;
                    data_CodeClient = new ArrayList<String>();
                    data_NomClient = new ArrayList<String>();
                    stmt = con.prepareStatement("select  CodeClient,RaisonSociale  from Client where Prospect=0");
                    ResultSet rsss = stmt.executeQuery();
                    data_CodeClient.add("");
                    data_NomClient.add("Tout");
                    while (rsss.next()) {
                        String id = rsss.getString("CodeClient");
                        String designation = rsss.getString("RaisonSociale");
                        data_CodeClient.add(id);
                        data_NomClient.add(designation);

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
