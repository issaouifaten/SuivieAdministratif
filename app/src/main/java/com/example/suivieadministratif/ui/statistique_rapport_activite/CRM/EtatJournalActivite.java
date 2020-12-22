package com.example.suivieadministratif.ui.statistique_rapport_activite.CRM;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Compte.SuivieArretCaisse;

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

public class EtatJournalActivite extends AppCompatActivity {


    ConnectionClass connectionClass;
    String CodeSociete, NomUtilisateur, CodeLivreur;
    final Context co = this;
    String user, password, base, ip;
    GridView gridSituation;
    TextView txt_datedebut, txt_datefin,txt_total;
    String datedebut = "", datefin = "";
    DatePicker datePicker;
    GridView gridEtat;
    ProgressBar progressBar;
    ArrayList<String> data_CodeCompte, data_NomCompte;
    ArrayList<String> data_CodeReg, data_ModeReg;
    Spinner spinRespensable,spinReg;
    String condition="",conditionReg="",conditionArticle="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etat_journal_activite);

        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " :Suivie Arret Caisse");

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
            spinReg=(Spinner)findViewById(R.id.spinnerclient) ;


         GetDataSpinnerModeReg GetDataSpinnerModeReg=new  GetDataSpinnerModeReg();
        GetDataSpinnerModeReg.execute("");

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
                                 FillList fillList = new  FillList();
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


                                 FillList fillList = new  FillList();
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
        spinReg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                {
                    String CodeVoiture=data_CodeReg.get(position);
                    conditionReg ="  and Type='"+CodeVoiture+"'";
                     FillList fillList=new  FillList();
                    fillList.execute("");
                }else{
                    conditionReg="";
                     FillList fillList=new  FillList();
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
        float total_gloabl=0;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            list.clear();
            total_gloabl=0;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            condition="";
            txt_total.setText(""+total_gloabl);

            String[] from = {"Nom", "Type", "Numero", "Date", "Montant" };
            int[] views = {R.id.txt_code, R.id.txt_designation,  R.id.tx_num_piece, R.id.txt_total_ttc,R.id.txt_nom_rad};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_suivie_arret_caisse, from,
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
                    convertView = layoutInflater.inflate(R.layout.item_journal_activite, null);
                    final TextView txt_nom = (TextView) convertView.findViewById(R.id.txt_nom);
                    final TextView txt_montant = (TextView) convertView.findViewById(R.id.txt_montant);
                    final TextView txt_date = (TextView) convertView.findViewById(R.id.txt_date);
                    final TextView txt_type = (TextView) convertView.findViewById(R.id.txt_type);
                    final TextView txt_numero = (TextView) convertView.findViewById(R.id.txt_numero);



                    final CardView card = (CardView) convertView.findViewById(R.id.card);
                    final HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);

                    String Type = (String) obj.get("Type");
                    String Numero = (String) obj.get("Numero");
                    String Date = (String) obj.get("Date");
                    String Montant = (String) obj.get("Montant");
                    String Nom = (String) obj.get("Nom");


                    txt_nom.setText(Nom);
                    txt_type.setText(Type);
                    txt_date.setText(Date);
                    txt_numero.setText(Numero);
                    txt_montant.setText(Montant);




                    if (list.contains(position)) {
                        card.setVisibility(View.VISIBLE);
                    } else {
                       card.setVisibility(View.GONE);
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


                    String queryTable = " SELECT *,convert (date,Date,103) as d " +
                            " FROM TypeMouvement where Date between '"+datedebut+"' and '"+datefin+"'\n" + conditionReg+
                            " order by Type";

                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("query", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";
                    String ancien = "";
                    int posi = 0;
                    while (rs.next()) {

                        Map<String, String> datanum = new HashMap<String, String>();

                        datanum.put("Type", rs.getString("Type"));
                        datanum.put("Date", rs.getString("d"));
                        datanum.put("Montant", rs.getString("Montant"));
                        datanum.put("Numero", rs.getString("Numero"));
                        datanum.put("Nom", rs.getString("Nom"));


                        String nom = rs.getString("Type").toUpperCase();
                        if (!ancien.equals(nom)) {
                            ancien = nom;

                            list.add(posi);
                        }
                        posi++;

                        total_gloabl+= rs.getFloat("Montant");
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













    public class GetDataSpinnerModeReg extends AsyncTask<String, String, String> {
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
                    R.layout.spinner, data_ModeReg);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



            spinReg.setAdapter(adapter);





        }
        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    PreparedStatement stmt, stmt2;
                    data_CodeReg = new ArrayList<String>();
                    data_ModeReg = new ArrayList<String>();
                    stmt = con.prepareStatement("select TypeRapport from ParametreRapportActivite order by NumeroOrdre");
                    ResultSet rsss = stmt.executeQuery();
                    data_CodeReg.add("");
                    data_ModeReg.add("Tout");
                    while (rsss.next()) {
                        String id = rsss.getString("TypeRapport");
                        String designation = rsss.getString("TypeRapport");
                        data_CodeReg.add(id);
                        data_ModeReg.add(designation);

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
