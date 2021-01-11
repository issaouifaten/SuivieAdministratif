package com.example.suivieadministratif.ui.statistique_rapport_activite.Graphique;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.SuivieCommandeClient;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Stock.Etat_indic_dispo_stock;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.color.MaterialColors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Diagramme extends AppCompatActivity {
    String user, password, base, ip;
    String CodeSociete, NomUtilisateur ;
    String   CodeDepot = "", LibelleDepot = "";
    Spinner spinfrs;
    ConnectionClass connectionClass;
    BarChart chart;
    CheckBox bt_montantttc,bt_benificenet,bt_benifice,bt_montantremise;
    ProgressBar progressBar;
    TextView txt_datedebut, txt_datefin;
    String datedebut = "", datefin = "";
    DatePicker datePicker;
    final Context co = this;
    ArrayList<String> data_CodeRespensable, data_NomRespensable;
    ArrayList<String> data_CodeVille, data_LibelleVille;
    Spinner spinRespensable, spinVille;
    String conditionRep="",conditionVille="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagramme);




        SharedPreferences pref = getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " :Rapport Graphique");


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

//bt
        bt_benificenet=(CheckBox)findViewById(R.id.bt_benificenet);
        bt_benifice=(CheckBox)findViewById(R.id.bt_benifice);
        bt_montantttc=(CheckBox)findViewById(R.id.bt_montantttc);
        bt_montantremise=(CheckBox)findViewById(R.id.bt_montantremise);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.GONE);
        txt_datedebut = (TextView) findViewById(R.id.txt_date_debut);
        txt_datefin = (TextView) findViewById(R.id.txt_date_fin);

        spinRespensable = (Spinner) findViewById(R.id.spinnerrepresentant);
        spinVille= (Spinner) findViewById(R.id.spinnerville);


        chart = findViewById(R.id.barchart);
        CardView card_date_debut = (CardView) findViewById(R.id.card_date_debut);
        CardView card_date_fin = (CardView) findViewById(R.id.card_date_fin);

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
                                GetDataGraphe getDataGraphe=new GetDataGraphe();
                                getDataGraphe.execute("");


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
                                GetDataGraphe getDataGraphe=new GetDataGraphe();
                                getDataGraphe.execute("");
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


        GetDataSpinnerRep getDataSpinnerRep=new GetDataSpinnerRep();
        getDataSpinnerRep.execute( "");

        GetDataSpinnerVille getDataSpinnerVille=new GetDataSpinnerVille();
        getDataSpinnerVille.execute( "");
        bt_montantremise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GetDataGraphe getDataGraphe=new GetDataGraphe();
                getDataGraphe.execute("");
            }
        });

        // Custom colors to in the pie chart
        bt_benifice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GetDataGraphe getDataGraphe=new GetDataGraphe();
                getDataGraphe.execute("");
            }
        });
        bt_benificenet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GetDataGraphe getDataGraphe=new GetDataGraphe();
                getDataGraphe.execute("");
            }
        });
        bt_montantttc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GetDataGraphe getDataGraphe=new GetDataGraphe();
                getDataGraphe.execute("");
            }
        });
        spinVille.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String Codeville= data_CodeVille.get(position);
                    conditionVille = "  and ( CodeVille='" + Codeville + "'  ) ";
                    GetDataGraphe getDataGraphe=new GetDataGraphe();
                    getDataGraphe.execute("");
                } else {
                    conditionVille = "";
                    GetDataGraphe getDataGraphe=new GetDataGraphe();
                    getDataGraphe.execute("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinRespensable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String Coderep= data_CodeRespensable.get(position);
                    conditionRep = "  and ( CodeRepresentant='" + Coderep + "'  ) ";
                    GetDataGraphe getDataGraphe=new GetDataGraphe();
                    getDataGraphe.execute("");
                } else {
                    conditionRep = "";
                    GetDataGraphe getDataGraphe=new GetDataGraphe();
                    getDataGraphe.execute("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }









    public class GetDataGraphe extends AsyncTask<String, String, String> {
        String z = "  ";
        float MontantRemise=0;
        float BenificeNet=  0;
       float Benifice= 0;
        float MontantTTC= 0;
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

            //donnee
            ArrayList<BarEntry> arrayListData = new ArrayList();
            String label="";
            int longeur=0;
            int[] colors = {Color.rgb(204, 41, 0), Color.rgb(255, 162, 10),
                    Color.rgb(53, 194, 209),Color.rgb(157, 191, 255)};

            if(bt_benificenet.isChecked()) {
                bt_benificenet.setBackgroundColor(colors[longeur]);
                longeur++;
                arrayListData.add(new BarEntry( longeur, BenificeNet));
                label+=" BenificeNet="+BenificeNet+";";

            }else{
                bt_benificenet.setBackgroundColor(Color.rgb(255, 255, 255));

            }

            if(bt_benifice.isChecked()) {
                bt_benifice.setBackgroundColor(colors[longeur]);
                longeur++;
                arrayListData.add(new BarEntry( longeur,Benifice));
                label+=" Benifice="+Benifice+"; ";

            }else{
                bt_benifice.setBackgroundColor(Color.rgb(255, 255, 255));

            }

            if(bt_montantttc.isChecked()) {
                bt_montantttc.setBackgroundColor(colors[longeur]);
                longeur++;
                arrayListData.add(new BarEntry( longeur,MontantTTC));
                label+=" TTC="+MontantTTC+";";
            }else{
                bt_montantttc.setBackgroundColor(Color.rgb(255, 255, 255));

            }


            if(bt_montantremise.isChecked()) {
                bt_montantremise.setBackgroundColor(colors[longeur]);
                longeur++;
                arrayListData.add(new BarEntry( longeur,MontantRemise));
                label+=" Remise="+MontantRemise+";";
            }else{
                bt_montantremise.setBackgroundColor(Color.rgb(255, 255, 255));

            }




//couleur

            ArrayList<Integer> arrayListColor = new ArrayList<>();
            for (int c : colors) {
                arrayListColor.add(c);

            }

            BarDataSet bardataset = new BarDataSet(arrayListData, label);
            chart.animateY(1000);
            BarData data = new BarData(bardataset);
            bardataset.setColors(arrayListColor);
            //bardataset.setColors(ColorTemplate.createColors(colors));
            chart.setDrawBarShadow(true);
            chart.setData(data);


        }
        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    PreparedStatement stmt;


                    String querydepot=" select  sum (MontantRemise)as MontantRemise ,sum(MontantRemiseQT)as MontantRemiseQT,\n" +
                            " sum(MontantFodec)as MontantFodec ,sum(MontantTVA)as MontantTVA,sum(MontantTTC)as MontantTTC ,\n" +
                            " sum(MontantEtrange)as MontantEtrange,\n" +
                            " convert(numeric(18,3),sum (Benifice) )as Benifice \n" +
                            " ,convert(numeric(18,3),sum(BenificeNet) )as BenificeNet\n" +
                            "  ,convert(numeric(18,3),sum(PrixRevient ))as PrixRevient\n" +
                            " from Vue_ListeVenteGlobalDetailler where DatePiece between '"+datedebut+"' and '"+datefin+"'"+conditionRep+conditionVille;

                    stmt = con.prepareStatement(querydepot);
                    ResultSet rsss = stmt.executeQuery();
                    Log.e("spindata", querydepot);

                    while (rsss.next()) {
                          MontantRemise= rsss.getFloat("MontantRemise");
                          BenificeNet= rsss.getFloat("BenificeNet");
                          Benifice= rsss.getFloat("Benifice");
                          MontantTTC= rsss.getFloat("MontantTTC");


                    }


                }
            } catch (SQLException ex) {
                z = "list" + ex.toString();

            } catch (Exception e) {

            }
            return z;
        }
    }



    public class GetDataSpinnerRep extends AsyncTask<String, String, String> {
        String z = "  ";

        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();

        @Override
        protected void onPreExecute() {
            //  Log.e("frs", querylist);
            // pbbar.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);

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
                    stmt = con.prepareStatement("select  CodeRespensable,Nom from Respensable where Actif=1 order by Nom");
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



    public class GetDataSpinnerVille extends AsyncTask<String, String, String> {
        String z = "  ";

        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();

        @Override
        protected void onPreExecute() {
            //  Log.e("frs", querylist);
            // pbbar.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);

            ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getApplicationContext(),
                    R.layout.spinner, data_LibelleVille);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            spinVille.setAdapter(adapter);


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    PreparedStatement stmt, stmt2;
                    data_CodeVille = new ArrayList<String>();
                    data_LibelleVille= new ArrayList<String>();
                    stmt = con.prepareStatement("select  CodeVille,DesignationVille  from Ville order by  DesignationVille");
                    ResultSet rsss = stmt.executeQuery();
                    data_CodeVille.add("");
                    data_LibelleVille.add("Tout");
                    while (rsss.next()) {
                        String id = rsss.getString("CodeVille");
                        String designation = rsss.getString("DesignationVille");
                        data_CodeVille.add(id);
                        data_LibelleVille.add(designation);

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