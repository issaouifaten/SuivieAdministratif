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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EtatArticlePerime extends AppCompatActivity {

    String user, password, base, ip;
    String CodeSociete, NomUtilisateur ;
    String   CodeDepot = "", LibelleDepot = "";
    Spinner spinfrs;
    ConnectionClass connectionClass;
    GridView gridArticle;
    EditText edtRecherche;
    String querysearch="";
    String Frs="Tout";
    ArrayList<String> datalibelle,datacode,dataLibelleFRS,datacodeFRS;
    Spinner  spindepot;
    ProgressBar progressBar;
    String date_debut = "",date_fin="";
    public TextView txt_date_debut, txt_date_fin;
    DatePicker datePicker;
    final Context co = this;
    String conditionFrs="",conditionDepot="",conditionArticle="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etat_article_perime);

        SharedPreferences pref = getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Etat Article  Périmé");


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

        spindepot=(Spinner)findViewById(R.id.spin_depot);
        GetDataSpinner getDataSpinner=new GetDataSpinner();
        getDataSpinner.execute("");

        txt_date_debut = findViewById(R.id.txt_date_debut);
        txt_date_fin = findViewById(R.id.txt_date_fin);
        spinfrs = (Spinner) findViewById(R.id.spinner);
        edtRecherche=(EditText)findViewById(R.id.edt_recherche) ;
        gridArticle=(GridView)findViewById(R.id.grid_article) ;
        Button btadd=(Button)findViewById(R.id.btadd);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
         date_debut= sdf.format(calendar.getTime());
        calendar.add(Calendar.MONTH, +1);
        date_fin = sdf.format(calendar.getTime());
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
                                FillList fillList = new FillList();
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


                                FillList fillList = new FillList();
                                fillList.execute("");
                            }
                        });

                AlertDialog dd = alt.create();
                dd.show();

            }
        });






        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String oldText=edtRecherche.getText().toString();
                String newText=oldText+"%";
                edtRecherche.setText(newText);
                edtRecherche.setSelection(newText.length());
                FillList fillList = new FillList();
                fillList.execute("");

            }
        });

        ////////////////////////////////////////////////////
        GetDataSpinnerFRS getDataSpinnerFRS=new GetDataSpinnerFRS();
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


                FillList fillList = new FillList();
                fillList.execute("");


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });




        edtRecherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if( charSequence.length()>1)
                {
                    conditionArticle=" and (CodeArticle  like'%"+charSequence+"%'  or  DesignationArticle like'%"+charSequence+"%')";

                }else{
                    conditionArticle="";
                }
                FillList fillList = new FillList();
                fillList.execute("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        spindepot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i!=0) {
                    CodeDepot = datacode.get(i);
                    LibelleDepot = datalibelle.get(i);


                    conditionDepot=" and CodeDepot='"+CodeDepot+"'" ;




                }else{
                    conditionDepot=" ";
                }
                FillList fillList = new FillList();
                fillList.execute("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }










    public class FillList extends AsyncTask<String, String, String> {
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

            String[] from = {"CodeArticle", "Quantite",    "DateLimiteConsommation","QTRestante","TotalNetHT","Designation","NumeroLot"};
            int[] views = {R.id.txt_code_article, R.id.txt_qt, R.id.txt_date_limite, R.id.txt_qt, R.id.txt_tot_ht,R.id.txt_designation_article,R.id.txt_num_lot};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_article_perime, from,
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
                    querysearch=" select CodeArticle,DesignationArticle,NumeroLot,\n" +
                            "convert(date,DateLimiteConsommation,103) as DateLimiteConsommation ,\n" +
                            "convert(numeric(18,2),QTRestante)as QTRestante,convert(numeric(18,3),NetHT*QTRestante) as TotalNetHT\n" +
                            "from Vue_ListeArticlePrime\n" +
                            "where DateLimiteConsommation \n" +
                            "between '"+date_debut+"' and '"+date_fin+"' " +conditionArticle+conditionDepot+conditionFrs+
                            " ";

                    PreparedStatement ps = con.prepareStatement(querysearch);
                    Log.e("querysearch",querysearch);
                    ResultSet rs = ps.executeQuery();

                    ArrayList data1 = new ArrayList();
                    while (rs.next()) {
                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("CodeArticle", rs.getString("CodeArticle"));
                        datanum.put("Designation", rs.getString("DesignationArticle"));
                        datanum.put("NumeroLot", rs.getString("NumeroLot"));
                        datanum.put("DateLimiteConsommation", rs.getString("DateLimiteConsommation"));
                        datanum.put("QTRestante", rs.getString("QTRestante"));
                        datanum.put("TotalNetHT", rs.getString("TotalNetHT"));



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
                    R.layout.spinner, datalibelle);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spindepot.setAdapter(adapter);






        }
        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    PreparedStatement stmt;
                    datalibelle = new ArrayList<String>();
                    datacode = new ArrayList<String>();

                    String querydepot="select * from Depot where CodeNature=1";

                    stmt = con.prepareStatement(querydepot);
                    ResultSet rsss = stmt.executeQuery();
                    Log.e("spindepot", querydepot);
                    datalibelle.add("Tout Depot");
                    datacode.add("");
                    while (rsss.next()) {
                        String libelle= rsss.getString("Libelle");
                        datalibelle.add(libelle);
                        String CodeDepot= rsss.getString("CodeDepot");
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
