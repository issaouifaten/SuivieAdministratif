package com.example.suivieadministratif.ui.statistique_rapport_activite.Stock;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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
import com.example.suivieadministratif.ui.statistique_rapport_activite.CRM.ListeCauseRetour;

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

public class Etat_indic_dispo_stock extends AppCompatActivity {




    ConnectionClass connectionClass;
    String CodeSociete, NomUtilisateur, CodeLivreur;
    final Context co = this;
    String user, password, base, ip;
    GridView gridSituation;
    TextView txt_datedebut, txt_datefin;
    String datedebut = "", datefin = "";
    DatePicker datePicker;
    GridView gridEtat;
    ProgressBar progressBar;
    ArrayList<String> data_CodeCause, data_NomCause;
    ArrayList<String> data_CodeReg, data_ModeReg;
    Spinner spinner;
    String condition="",conditionArticle="";
    EditText edt_recherche;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etat_indic_dispo_stock);

        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " :Indicateur Disponibilté stock");

        connectionClass = new ConnectionClass();

        SharedPreferences prefe = getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);



        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);
        edt_recherche=(EditText)findViewById(R.id.edt_recherche) ;
        txt_datedebut = (TextView) findViewById(R.id.txt_date_debut);
        txt_datefin = (TextView) findViewById(R.id.txt_date_fin);

        gridEtat = (GridView) findViewById(R.id.grid_article);
        spinner=(Spinner)findViewById(R.id.spinner) ;


        GetDataSpinner GetDataSpinnerModeReg=new GetDataSpinner();
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                {
                    String CodeCodeCompte=data_CodeCause.get(position);
                    condition ="  and  CodeFournisseur='"+CodeCodeCompte+"'   ";
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
        Button btadd=(Button)findViewById(R.id.btadd);


        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String oldText=edt_recherche.getText().toString();
                String newText=oldText+"%";
                edt_recherche.setText(newText);
                edt_recherche.setSelection(newText.length());


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
                    conditionArticle=" and  Designation like'%"+s+"%' OR CodeArticle like'%"+s+"%' ";

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


        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);



            String[] from = { "CodeFournisseur",   "RaisonSociale","DateBonRetourVente", "QteCommander","QteLivrer","Designation"};
            int[] views = {R.id.txt_code_client, R.id.txt_rs,  R.id.txt_date, R.id.txt_qt,R.id.txt_qtlivre,R.id.txt_article};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_list_cause_retour, from,
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
                    convertView = layoutInflater.inflate(R.layout.item_list_indic_dispo_stock, null);

                    final TextView txt_qtstasisfait = (TextView) convertView.findViewById(R.id.txt_qtstasisfait);
                    final TextView txt_qt = (TextView) convertView.findViewById(R.id.txt_qt);
                    final TextView txt_article = (TextView) convertView.findViewById(R.id.txt_article);
                    final TextView txt_code_client = (TextView) convertView.findViewById(R.id.txt_code_client);
                    final TextView txt_rs = (TextView) convertView.findViewById(R.id.txt_rs);

                    final CardView layout_vendeur = (CardView) convertView.findViewById(R.id.card_client_);

                    final HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);
                    //"CodeFournisseur",   "RaisonSociale","DateBonRetourVente", "QteCommander","QteLivrer","Designation"
                    String CodeFournisseur = (String) obj.get("CodeFournisseur");
                    String RaisonSociale = (String) obj.get("RaisonSociale");
                    String QteCommander = (String) obj.get("QteCommander");

                    String QteLivrer = (String) obj.get("QteLivrer");
                    String Designation = (String) obj.get("Designation");


                    txt_qtstasisfait.setText(QteLivrer);
                    txt_qt.setText(QteCommander);
                    txt_article.setText(Designation);
                    txt_code_client.setText(CodeFournisseur);
                    txt_rs.setText(RaisonSociale);



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


                    String queryTable = "select CodeFournisseur,RaisonSociale,CodeArticle,Designation,sum(QteLivrer) as QteLivrer,sum(QteCommander) as QteCommander from (select Article.CodeArticle, \n" +
                            "Designation,Article.CodeFournisseur,Fournisseur.RaisonSociale,\n" +
                            "sum(Quantite )as 'QteCommander',isnull((select sum(Quantite) from LigneBonLivraisonVente \n" +
                            "inner join BonLivraisonVente on LigneBonLivraisonVente.NumeroBonLivraisonVente = BonLivraisonVente.NumeroBonLivraisonVente \n" +
                            "where BonLivraisonVente.NumeroEtat != 'E00' and \n" +
                            "DateBonLivraisonVente  between '"+datedebut+"' and '"+datefin+"'\n" +
                            "and LigneBonLivraisonVente.CodeArticle = LigneBonCommandeVente.CodeArticle \n" +
                            "and LigneBonLivraisonVente.NumeroBonCommandeVente = LigneBonCommandeVente.NumeroBonCommandeVente),0) as 'QteLivrer' \n" +
                            "from BonCommandeVente\n" +
                            "inner join LigneBonCommandeVente on BonCommandeVente.NumeroBonCommandeVente = LigneBonCommandeVente.NumeroBonCommandeVente\n" +
                            "inner join Article on LigneBonCommandeVente.CodeArticle = Article.CodeArticle \n" +
                            "inner join Fournisseur on Article.CodeFournisseur = Fournisseur.CodeFournisseur where DateBonCommandeVente\n" +
                            " between '"+datedebut+"' and '"+datefin+"' and Article.CodeArticle not like '999%'\n" +
                            " \n" +
                            "group by Article.CodeFournisseur,Fournisseur.RaisonSociale,Article.CodeArticle,Designation,LigneBonCommandeVente.CodeArticle,LigneBonCommandeVente.NumeroBonCommandeVente\n" +
                            ")as a" +
                            " where  RaisonSociale!='' " +conditionArticle+condition+
                            " group by CodeFournisseur,RaisonSociale,CodeArticle,Designation ";

                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("query", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";
                    String ancien = "";
                    int posi = 0;
                    while (rs.next()) {

                        Map<String, String> datanum = new HashMap<String, String>();

                        datanum.put("CodeFournisseur", rs.getString("CodeFournisseur"));
                        datanum.put("QteCommander", rs.getString("QteCommander"));
                        datanum.put("QteLivrer", rs.getString("QteLivrer"));

                        datanum.put("Designation", rs.getString("Designation"));
                        datanum.put("CodeArticle", rs.getString("CodeArticle"));
                        datanum.put("RaisonSociale", rs.getString("RaisonSociale"));

                        String nom = rs.getString("CodeFournisseur").toUpperCase();
                        if (!ancien.equals(nom)) {
                            ancien = nom;

                            list.add(posi);
                        }
                        posi++;


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
                    R.layout.spinner, data_NomCause);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



            spinner.setAdapter(adapter);





        }
        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    PreparedStatement stmt, stmt2;
                    data_CodeCause = new ArrayList<String>();
                    data_NomCause = new ArrayList<String>();
                    stmt = con.prepareStatement(" select CodeFournisseur,RaisonSociale from Fournisseur");
                    ResultSet rsss = stmt.executeQuery();
                    data_CodeCause.add("");
                    data_NomCause.add("Tout");
                    while (rsss.next()) {
                        String id = rsss.getString("CodeFournisseur");
                        String designation = rsss.getString("RaisonSociale");
                        data_CodeCause.add(id);
                        data_NomCause.add(designation);

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
