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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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

import javax.microedition.khronos.egl.EGLDisplay;

public class EtatJournalArticleVendu extends AppCompatActivity {
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
    ArrayList<String> data_CodeClient, data_NomClient;
    Spinner spinRespensable,spinClient;
    String condition="",conditionclient="",conditionArticle="";
    EditText edt_recherche;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etat_journal_article_vendu);

        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Journal Article Vendu");

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
        edt_recherche=(EditText)findViewById(R.id.edt_recherche) ;
        GetDataSpinner getDataSpinner=new GetDataSpinner();
        getDataSpinner.execute("");
        GetDataSpinnerClient getDataSpinnerClient=new GetDataSpinnerClient();
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
                if(position!=0)
                {
                    String codeclient=data_CodeClient.get(position);
                    conditionclient ="  and CodeClient='"+codeclient+"'";
                    FillList fillList=new FillList();
                    fillList.execute("");
                }else{
                    conditionclient="";
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
                    String CodeRepresentant=data_CodeRespensable.get(position);
                    condition ="  and ( CodeRepresentant='"+CodeRepresentant+"' or CodeRespensableAdministration='"+CodeRepresentant+"' ) ";
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
        edt_recherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(""))
                {
                    conditionArticle="where  DesignationArticle like'%"+s+"%' OR CodeArticle like'%"+s+"%' ";

                    FillList fillList=new FillList();
                    fillList.execute("");
                }else{
                    conditionArticle="";
                    FillList fillList=new FillList();
                    fillList.execute("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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

            String[] from = {"CodeClient", "RaisonSociale", "REPRESENTANT", "NumeroPiece", "MontantTTC","RAD","Quantite","CodeArticle","DesignationArticle"};
            int[] views = {R.id.txt_code, R.id.txt_designation, R.id.txt_nom_representant, R.id.tx_num_piece, R.id.txt_total_ttc,R.id.txt_nom_rad};
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
                    convertView = layoutInflater.inflate(R.layout.item_journal_vente_article, null);
                    final TextView txt_code = (TextView) convertView.findViewById(R.id.txt_code);
                    final TextView txt_designation = (TextView) convertView.findViewById(R.id.txt_designation);
                    final TextView txt_nom_representant = (TextView) convertView.findViewById(R.id.txt_nom_representant);
                    final TextView tx_num_piece = (TextView) convertView.findViewById(R.id.tx_num_piece);
                    final TextView txt_total_ttc = (TextView) convertView.findViewById(R.id.txt_total_ttc);
                    final TextView txt_nom_rad = (TextView) convertView.findViewById(R.id.txt_nom_rad);
                    final TextView txt_quantite_article = (TextView) convertView.findViewById(R.id.txt_quantite_article);
                    final TextView txt_codearticle = (TextView) convertView.findViewById(R.id.txt_codearticle);
                    final TextView txt_montant_cmd = (TextView) convertView.findViewById(R.id.montant_cmd);
                    final TextView txt_designation_article = (TextView) convertView.findViewById(R.id.txt_designation_article);
                    final CardView layout_vendeur = (CardView) convertView.findViewById(R.id.card_client_);
                    final HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);
                    String CodeClient = (String) obj.get("CodeClient");
                    String RaisonSociale = (String) obj.get("RaisonSociale");
                    String NomRepresentant = (String) obj.get("REPRESENTANT");

                    String NumeroPiece = (String) obj.get("NumeroPiece");
                    String MontantTTC = (String) obj.get("MontantTTC");
                    String DesignationArticle = (String) obj.get("DesignationArticle");
                    String NomRAD = (String) obj.get("RAD");
                    String Quantite = (String) obj.get("Quantite");
                    String CodeArticle = (String) obj.get("CodeArticle");

//  String[] from = {"CodeClient", "RaisonSociale" ,"TypeOperation","NumeroPiece","TotalTTC"};
//  final LinearLayout layout_vendeur = (LinearLayout) convertView.findViewById(R.id.layout_vendeur);

                    txt_codearticle.setText(CodeArticle);
                    txt_designation_article.setText(DesignationArticle);
                    txt_montant_cmd.setText(MontantTTC);
                    txt_quantite_article.setText(Quantite);
                    txt_code.setText(CodeClient);
                    txt_nom_representant.setText(NomRepresentant);
                    txt_designation.setText(RaisonSociale);
                    txt_nom_rad.setText(NomRAD);

                    tx_num_piece.setText(NumeroPiece);
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


                    String queryTable = "select * from (select Vue_ListeVenteGlobal.CodeArticle, CodeClient,RaisonSociale,MontantTTC ,NumeroPiece ," +
                            "convert(numeric(18,0),Quantite) as  Quantite " +
                            ",(select  Nom from Respensable where Respensable.CodeRespensable=Vue_ListeVenteGlobal.CodeRespensableAdministration)as RAD\n" +
                            ",(select  Nom from Respensable where Respensable.CodeRespensable=Vue_ListeVenteGlobal.CodeRepresentant)as REPRESENTANT \n" +
                            ",(select  Designation from Article where Article.CodeArticle=Vue_ListeVenteGlobal.CodeArticle)as DesignationArticle " +
                            " from Vue_ListeVenteGlobal " +
                            "where DatePiece in('"+datedebut+"','"+datefin+"') "+condition+conditionclient+")" +
                            " as t "+ conditionArticle+" order by NumeroPiece, CodeClient";

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
                        datanum.put("MontantTTC", rs.getString("MontantTTC"));
                        datanum.put("Quantite", rs.getString("Quantite"));
                        datanum.put("CodeArticle", rs.getString("CodeArticle"));
                        datanum.put("RAD", rs.getString("RAD"));
                        datanum.put("REPRESENTANT", rs.getString("REPRESENTANT"));
                        datanum.put("DesignationArticle", rs.getString("DesignationArticle"));
                        String nom = rs.getString("CodeClient").toUpperCase();
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
