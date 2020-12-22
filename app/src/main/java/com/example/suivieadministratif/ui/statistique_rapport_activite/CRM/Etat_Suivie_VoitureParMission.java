package com.example.suivieadministratif.ui.statistique_rapport_activite.CRM;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.ui.statistique_rapport_activite.article.EtatJournalArticleVendu;

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

public class Etat_Suivie_VoitureParMission extends AppCompatActivity {

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
    ArrayList<String> data_CodeRespensable, data_NomRespensable;
    ArrayList<String> data_CodeVoiture, data_NomVoiture;
    Spinner spinRespensable,spinClient;
    String condition="",conditionvoiture="",conditionArticle="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etat__suivie__voiture_par_mission);

        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Suivie Voiture");

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
        spinRespensable=(Spinner)findViewById(R.id.spinnerrepresentant) ;
        spinClient=(Spinner)findViewById(R.id.spinnerclient) ;

        GetDataSpinner getDataSpinner=new GetDataSpinner();
        getDataSpinner.execute("");
        GetDataSpinnerVoiture GetDataSpinnerVoiture=new GetDataSpinnerVoiture();
        GetDataSpinnerVoiture.execute("");

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
                if(position!=0)
                {
                    String CodeVoiture=data_CodeVoiture.get(position);
                    conditionvoiture ="  and CodeVehicule='"+CodeVoiture+"'";
                    FillList fillList=new FillList();
                    fillList.execute("");
                }else{
                    conditionvoiture="";
                    FillList fillList=new FillList();
                    fillList.execute("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinRespensable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                {
                    String CodeChauffeur=data_CodeRespensable.get(position);
                    condition ="  and  CodeChauffeur='"+CodeChauffeur+"'   ";
                    FillList fillList=new FillList();
                    fillList.execute("");
                }else{
                    condition="";
                    FillList fillList=new FillList();
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

            String[] from = {"CodeVoiture", "DesignationVoiture", "Chauffeur", "NumeroBonLivraisonVente", "MontantTTC","RaisonSociale","DateMission",
                    "NumeroMission",};
            int[] views = {R.id.txt_code, R.id.txt_designation, R.id.txt_nom_Chauffeur, R.id.tx_num_piece, R.id.txt_total_ttc,R.id.txt_nom_rad};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_journal_vente_article, from,
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
                    convertView = layoutInflater.inflate(R.layout.item_suivie_voiture, null);
                    final TextView txt_num_mission = (TextView) convertView.findViewById(R.id.txt_num_mission);
                    final TextView txt_designation = (TextView) convertView.findViewById(R.id.txt_designation);
                    final TextView txt_nom_Chauffeur = (TextView) convertView.findViewById(R.id.txt_nom_Chauffeur);
                    final TextView tx_num_piece = (TextView) convertView.findViewById(R.id.tx_num_piece);
                    final TextView txt_total_ttc = (TextView) convertView.findViewById(R.id.txt_total_montant);
                    final TextView txt_raison_social = (TextView) convertView.findViewById(R.id.txt_raison_social);
                    final TextView txt_date_mission = (TextView) convertView.findViewById(R.id.txt_date_mission);

                    final CardView layout_vendeur = (CardView) convertView.findViewById(R.id.l_mission);
                    final HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);
                    String CodeVoiture = (String) obj.get("CodeVoiture");
                    String DesignationVoiture = (String) obj.get("DesignationVoiture");
                    String NomChauffeur = (String) obj.get("Chauffeur");
                    String NumeroBonLivraisonVente = (String) obj.get("NumeroBonLivraisonVente");
                    String MontantTTC = (String) obj.get("MontantTTC");
                    String NumeroMission = (String) obj.get("NumeroMission");
                    String RaisonSociale = (String) obj.get("RaisonSociale");
                    String DateMission = (String) obj.get("DateMission");


                    txt_date_mission.setText(DateMission);
                    txt_num_mission.setText(NumeroMission);
                    txt_raison_social.setText(RaisonSociale);
                    txt_nom_Chauffeur.setText(NomChauffeur);
                    txt_designation.setText(DesignationVoiture);
                    tx_num_piece.setText(NumeroBonLivraisonVente);
                    txt_total_ttc.setText(MontantTTC);

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


                    String queryTable = "select NumeroBonLivraisonVente,NumeroMission,Respensable.Nom as Chauffeur\n" +
                            ",MontantTTC,Voiture.DesignationVoiture,Client.RaisonSociale,convert(date,DateMission,13) as DateMission \n" +
                            "from Vue_SuivieVoitureParMission\n" +
                            "inner join Respensable on Respensable.CodeRespensable=Vue_SuivieVoitureParMission.CodeChauffeur\n" +
                            "inner join Voiture on Voiture.CodeVoiture=Vue_SuivieVoitureParMission.CodeVehicule\n" +
                            "inner join Client on Client.CodeClient=Vue_SuivieVoitureParMission.CodeClient\n" +
                            " where DateMission between '"+datedebut+"'and '"+datefin+"'\n" + conditionvoiture+condition+
                            " order by DateMission,NumeroMission";

                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("query", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";
                    String ancien = "";
                    int posi = 0;
                    while (rs.next()) {

                        Map<String, String> datanum = new HashMap<String, String>();

                        datanum.put("DesignationVoiture", rs.getString("DesignationVoiture"));
                        datanum.put("NumeroBonLivraisonVente", rs.getString("NumeroBonLivraisonVente"));
                        datanum.put("MontantTTC", rs.getString("MontantTTC"));

                        datanum.put("Chauffeur", rs.getString("Chauffeur"));
                        datanum.put("RaisonSociale", rs.getString("RaisonSociale"));
                        datanum.put("NumeroMission", rs.getString("NumeroMission"));
                        datanum.put("DateMission", rs.getString("DateMission"));

                        String nom = rs.getString("NumeroMission").toUpperCase();
                        if (!ancien.equals(nom)) {
                            ancien = nom;

                            list.add(posi);
                        }
                        posi++;

                        total_gloabl+= rs.getFloat("MontantTTC");
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






    public class GetDataSpinnerVoiture extends AsyncTask<String, String, String> {
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
                    R.layout.spinner, data_NomVoiture);
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
                    data_CodeVoiture = new ArrayList<String>();
                    data_NomVoiture = new ArrayList<String>();
                    stmt = con.prepareStatement("select  CodeVoiture,DesignationVoiture  from Voiture");
                    ResultSet rsss = stmt.executeQuery();
                    data_CodeVoiture.add("");
                    data_NomVoiture.add("Tout");
                    while (rsss.next()) {
                        String id = rsss.getString("CodeVoiture");
                        String designation = rsss.getString("DesignationVoiture");
                        data_CodeVoiture.add(id);
                        data_NomVoiture.add(designation);

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
