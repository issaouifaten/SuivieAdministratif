package com.example.suivieadministratif.ui.statistique_rapport_activite.Achat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.param.Param;

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

public class JournalFactureAchat extends AppCompatActivity {

    String user, password, base, ip;
    String CodeSociete, NomUtilisateur ;
    String   CodeDepot = "", LibelleDepot = "";
    Spinner spinfrs;
    ConnectionClass connectionClass;
    GridView gridArticle;

    String querysearch="";
    String Frs="Tout";
    ArrayList<String> datalibelle,datacode,dataLibelleFRS,datacodeFRS;
    Spinner  spindepot;
    ProgressBar progressBar;
    String date_debut = "",date_fin="";
    public TextView txt_date_debut, txt_date_fin ; //,txt_tot_commande;
    DatePicker datePicker;
    final Context co = this;
    String conditionFrs="",conditionDepot="",conditionArticle="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_facture_achat);

        SharedPreferences pref = getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Journal Facture Achat ");


        /// CONNECTION BASE


        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);
        ///////////////////////////////////////

        connectionClass = new ConnectionClass();
        ////SESSION UTILISATEUR
        SharedPreferences prefe = getSharedPreferences(Param.PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);
        CodeSociete = prefe.getString("CodeSociete", CodeSociete);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);



        txt_date_debut = findViewById(R.id.txt_date_debut);
        txt_date_fin = findViewById(R.id.txt_date_fin);
        spinfrs = (Spinner) findViewById(R.id.spinner);
       // txt_tot_commande = (TextView) findViewById(R.id.txt_tot_commande);
        /*TextView txt_label = (TextView) findViewById(R.id.txt_gratuite);
        txt_label.setText("Total TTC");*/
        gridArticle=(GridView)findViewById(R.id.grid_article) ;
        Button btadd=(Button)findViewById(R.id.btadd);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        date_fin = sdf.format(calendar.getTime());
        calendar.add(Calendar.MONTH, -1);
        date_debut = sdf.format(calendar.getTime());
        txt_date_debut.setText(date_debut);
        txt_date_fin.setText(date_fin);


        txt_date_debut.setOnClickListener(new View.OnClickListener() {
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
                                date_debut = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH)
                                        .format(d);

                                txt_date_debut.setText(date_debut);
                                 FillList fillList = new  FillList();
                                fillList.execute("");


                            }
                        });

                AlertDialog dd = alt.create();
                dd.show();
            }
        });


        txt_date_fin.setOnClickListener(new View.OnClickListener() {

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
                                date_fin = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH)
                                        .format(d);

                                txt_date_fin.setText(date_fin);


                                 FillList fillList = new  FillList();
                                fillList.execute("");
                            }
                        });

                AlertDialog dd = alt.create();
                dd.show();

            }
        });







        ////////////////////////////////////////////////////
         GetDataSpinnerFRS getDataSpinnerFRS=new  GetDataSpinnerFRS();
        getDataSpinnerFRS.execute("");
        spinfrs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {



                Frs = datacodeFRS.get(position).toString();

                if(position==0) {
                    conditionFrs=" ";

                }
                else {
                    conditionFrs=" and CodeFournisseur='"+ Frs+ "'";
                }


                 FillList fillList = new  FillList();
                fillList.execute("");


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });







    }










    public class FillList extends AsyncTask<String, String, String> {
        String z = "  ";

        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();
float total=0;
        @Override
        protected void onPreExecute() {
            //  Log.e("frs", querylist);
            progressBar.setVisibility(View.VISIBLE);


        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            //   Toast.makeText(getApplicationContext(), r, Toast.LENGTH_SHORT).show();

            final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
            instance.setMinimumFractionDigits(3);
            instance.setMaximumFractionDigits(3);



           // txt_tot_commande.setText(instance.format(total));
            String[] from = {"NumeroPiece", "TotalTVA",    "TotalRemise","TotalHT","TotalFodec","DatePiece","RaisonSociale","TotalTTC","TimbreFiscal"};
            int[] views = {R.id.txt_num_piece, R.id.txt_qt, R.id.txt_remise, R.id.txt_ht, R.id.txt_fodec,R.id.txt_date_piece,R.id.txt_rs,R.id.txt_ttc,R.id.txt_fiscal};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_journal_facture_achat, from,
                    views);
            gridArticle.setAdapter(ADA);


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {
                    querysearch="select NumeroPiece,convert(date,DatePiece,103) as DatePiece ,RaisonSociale,TotalRemise,TotalHT,TotalFodec,TotalTVA,TimbreFiscal,TotalTTC" +
                            " from Vue_ListeAchatJournalier where DatePiece between '"+date_debut+"' and '"+date_fin+"'"+conditionFrs +
                            " ";

                    PreparedStatement ps = con.prepareStatement(querysearch);
                    Log.e("querysearch",querysearch);
                    ResultSet rs = ps.executeQuery();

                    ArrayList data1 = new ArrayList();
                    while (rs.next()) {
                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("NumeroPiece", rs.getString("NumeroPiece"));
                        datanum.put("DatePiece", rs.getString("DatePiece"));
                        datanum.put("RaisonSociale", rs.getString("RaisonSociale"));
                        datanum.put("TotalRemise", rs.getString("TotalRemise"));
                        datanum.put("TotalHT", rs.getString("TotalHT"));
                        datanum.put("TotalFodec", rs.getString("TotalFodec"));
                        datanum.put("TotalTVA", rs.getString("TotalTVA"));
                        datanum.put("TimbreFiscal", rs.getString("TimbreFiscal"));
                        datanum.put("TotalTTC", rs.getString("TotalTTC"));

                        total+=rs.getFloat("TotalTTC");
                        prolist.add(datanum);
                        z = "Success";
                    }


                }
            } catch (SQLException ex) {
                z = "list" + ex.toString();

            } catch (Exception e) {

            }
            return z;
        }
    }











    public class GetDataSpinnerFRS extends AsyncTask<String, String, String> {
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
            String[] array = datacodeFRS.toArray(new String[0]);


            ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getApplicationContext(),
                    R.layout.spinner, dataLibelleFRS);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinfrs.setAdapter(adapter);






        }
        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    PreparedStatement stmt;
                    dataLibelleFRS = new ArrayList<String>();
                    datacodeFRS = new ArrayList<String>();

                    String querydepot="select RaisonSociale, CodeFournisseur from Fournisseur";

                    stmt = con.prepareStatement(querydepot);
                    ResultSet rsss = stmt.executeQuery();
                    Log.e("spinFRS", querydepot);
                    dataLibelleFRS.add("Tout");
                    datacodeFRS.add("");
                    while (rsss.next()) {
                        String libelle= rsss.getString("RaisonSociale");
                        dataLibelleFRS.add(libelle);
                        String CodeFournisseur= rsss.getString("CodeFournisseur");
                        datacodeFRS.add(CodeFournisseur);

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
