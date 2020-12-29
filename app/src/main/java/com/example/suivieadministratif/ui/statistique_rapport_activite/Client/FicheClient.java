package com.example.suivieadministratif.ui.statistique_rapport_activite.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FicheClient extends AppCompatActivity {
    ConnectionClass connectionClass;

    String user, password, base, ip;
    ListView lstpro, lstfiche;

    final Context co = this;

    double totalcredit = 0.0, totaldebit;
    TextView txttotal;
    String datedebut = "", datefin = "";
    DatePicker datePicker;
    String codeclient = "", libellefiche, rsclient;
    TextView txtdebit, txtcredit, txtsoldeencour;
    TextView txtdatedebut, txtdatefin, txtclient, txtimpaye, txtpreavis;
    ProgressBar progressBar;
    ArrayList<String> datalibelle, datacode, dataLibelleClient, datacodeClient;
    Spinner spinClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_client);

        connectionClass = new ConnectionClass();
        SharedPreferences prefe = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);
        progressBar = (ProgressBar) findViewById(R.id.pbbar);
        progressBar.setVisibility(View.GONE);
        spinClient = (Spinner) findViewById(R.id.spin_client);

        final ImageView btdatedebut = (ImageView) findViewById(R.id.btdatedebut);
        final ImageView btdatefin = (ImageView) findViewById(R.id.btdatefin);


        txtdatedebut = (TextView) findViewById(R.id.edtdatedebut);

        txtdatefin = (TextView) findViewById(R.id.edtdatefin);
        txtclient = (TextView) findViewById(R.id.rsclient);
        txtimpaye = (TextView) findViewById(R.id.txtimpaye);
        txtpreavis = (TextView) findViewById(R.id.txtpreavis);
        txttotal = (TextView) findViewById(R.id.total);
        lstfiche = (ListView) findViewById(R.id.listfiche);
        txtsoldeencour = (TextView) findViewById(R.id.soldeencour);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Aujourd'hui, nous sommes le: " + sdf.format(calendar.getTime()));
        datefin = sdf.format(calendar.getTime());
        calendar.add(Calendar.MONTH, -3);
        System.out.println("Il y a 3 mois, nous étions le: " + sdf.format(calendar.getTime()));
        datedebut = sdf.format(calendar.getTime());

        txtdatedebut.setText(datedebut);
        txtdatefin.setText(datefin);

        GetDataSpinnerClient getDataSpinnerClient=new GetDataSpinnerClient();
        getDataSpinnerClient.execute("");
        spinClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             if(position!=0)
             {  codeclient = datacodeClient.get(position);
                rsclient = dataLibelleClient.get(position);
                txtclient.setText(rsclient);
                FicheClients ficheClients=new FicheClients();
                ficheClients.execute("");
                TestImpaye testImpaye = new TestImpaye();
                testImpaye.execute("");
                TestPreavis testPreavis = new TestPreavis();
                testPreavis.execute("");

                CalculSoldeEncour calculSoldeEncour = new CalculSoldeEncour();
                calculSoldeEncour.execute();}


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });








        btdatedebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                                txtdatedebut.setText(datedebut);
                                FicheClients ficheClients = new FicheClients();
                                ficheClients.execute("");


                            }
                        });

                AlertDialog dd = alt.create();
                dd.show();


            }
        });

        btdatefin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                                txtdatefin.setText(datefin);
                                FicheClients ficheClients = new FicheClients();
                                ficheClients.execute("");


                            }
                        });

                AlertDialog dd = alt.create();
                dd.show();


            }
        });


    }

    /////////////********************************************************/////////////
    /////////////********************************************************/////////////


    public class FicheClients extends AsyncTask<String, String, String> {
        String z = "";
        Boolean test = false;
        String tcredit, tdebit;

        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();

        SimpleDateFormat sdfSQL = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        @Override
        protected void onPreExecute() {
            totaldebit = 0;
            totalcredit = 0;
            progressBar.setVisibility(View.VISIBLE);

        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            //   Toast.makeText(getApplicationContext(), r, Toast.LENGTH_SHORT).show();
            if (test) {

                String[] from = {"A", "B", "C", "S", "E", "NumeroPiece"};
                int[] views = {R.id.libelle, R.id.txtdebit, R.id.txtcredit, R.id.txtsolde, R.id.date, R.id.txt_numero_piece};
                final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                        prolist, R.layout.listfiche, from,
                        views);
                lstfiche.setAdapter(ADA);
                try {
                    double solde = totaldebit - totalcredit;
                    DecimalFormat df = new DecimalFormat("#######0.000");
                    String str = df.format(solde);
                    txttotal.setText("Solde = " + str);
                } catch (Exception e) {
                }

            }
            if (r.equals("e")) {

            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    PreparedStatement ps = con.prepareStatement(
                            "DECLARE @return_value int " +

                                    "EXEC @return_value = [dbo].[FicheClient]" +
                                    "@CodeClient = N'" + codeclient + "'," +
                                    "@DateDebut = '" + datedebut + "'," +
                                    "@DateFin = '" + datefin + "'" +

                                    "SELECT\t'Return Value' = @return_value");

                    Log.e("fiche", "DECLARE @return_value int " +

                            "EXEC @return_value = [dbo].[FicheClient]" +
                            "@CodeClient = N'" + codeclient + "'," +
                            "@DateDebut = '" + datedebut + "'," +
                            "@DateFin = '" + datefin + "'" +

                            "SELECT\t'Return Value' = @return_value");
                    ResultSet rs = ps.executeQuery();
                    z = "e";

                    while (rs.next()) {

                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("A", rs.getString("Libelle"));

                        datanum.put("B", rs.getString("Debit"));
                        datanum.put("C", rs.getString("Credit"));
                        String date = rs.getString("DatePiece");

                        datanum.put("NumeroPiece", rs.getString("NumeroPiece"));

                        Date DatePiece = null;

                        try {
                            DatePiece = sdfSQL.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        String _date = sdf.format(DatePiece);

                        datanum.put("E", _date);
                        float so = Float.parseFloat(rs.getString("Debit")) - Float.parseFloat(rs.getString("Credit"));
                        datanum.put("D", String.valueOf(so));


                        totaldebit += Float.parseFloat(rs.getString("Debit"));
                        totalcredit += Float.parseFloat(rs.getString("Credit"));
                        float soldeligne = (float) (totaldebit - totalcredit);
                        DecimalFormat df = new DecimalFormat("#######0.000");
                        String str = df.format(soldeligne);

                        datanum.put("S", str);
                        prolist.add(datanum);


                        test = true;


                        z = "succees";
                    }


                }
            } catch (SQLException ex) {
                z = "etape" + ex.toString();

            }
            return z;
        }
    }


    /////////////********************************************************/////////////
    /////////////********************************************************/////////////


    public class TestImpaye extends AsyncTask<String, String, String> {
        String z = "";
        String montantimpaye = "0";
        Boolean testimpaye = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            txtimpaye.setText(montantimpaye);
            if (testimpaye == true) {
                txttotal.setBackgroundColor(Color.parseColor("#e9e9e9"));

            }
            // Toast.makeText(getApplicationContext(),datefin+ r, Toast.LENGTH_SHORT).show();
        }


        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {
                    Connection connect = connectionClass.CONN(ip, password, user, base);
                    PreparedStatement stmt;
                    String query = "select (sum(MontantImpayeClient))as Montant from ImpayeClient where CodeClient='" + codeclient + "'";
                    stmt = connect.prepareStatement(query);
                    ResultSet rs = stmt.executeQuery();
                    Log.e("impaye", query);

                    while (rs.next()) {
                        montantimpaye = rs.getString("Montant");
                        testimpaye = true;


                        z = "succee";
                    }
                    z = "e";


                }
            } catch (SQLException ex) {
                z = ex.toString();

            }
            return z;
        }
    }


    public class CalculSoldeEncour extends AsyncTask<String, String, String> {
        String z = "";
        String solde = "0.000";
        float soldf = 0;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            solde = "" + soldf;
            txtsoldeencour.setText(solde);


            ;

            // Toast.makeText(getApplicationContext(), txtsoldeencour.getText().toString()+ solde, Toast.LENGTH_LONG).show();
        }


        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {
                    Connection connect = connectionClass.CONN(ip, password, user, base);
                    PreparedStatement stmt, st;
                    String query_proc = "DECLARE\t@return_value int\n" +

                            "EXEC\t@return_value = [dbo].[SoldeClients]\n" +
                            "\t\t@DateAu = '" + datefin + "'";

                    st = connect.prepareStatement(query_proc);
                    ResultSet r = st.executeQuery();

                    String query = "select * from Vue_EncourClient where CodeClient='" + codeclient + "'";
                    stmt = connect.prepareStatement(query);
                    ResultSet rs = stmt.executeQuery();

                    while (rs.next()) {

                        soldf = soldf + Float.parseFloat(rs.getString("MontantRecu"));


                        z = "succee";
                    }
                    z = "e";


                }
            } catch (SQLException ex) {
                z = ex.toString();

            }
            return z;
        }
    }


    public class TestPreavis extends AsyncTask<String, String, String> {
        String z = "";
        String montantpreavis = "0";
        Boolean testpreavis = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            txtpreavis.setText(montantpreavis);

            if (testpreavis == true) {
                txttotal.setBackgroundColor(Color.parseColor("#e9e9e9"));

            }


            ;

            //  Toast.makeText(getApplicationContext(), r, Toast.LENGTH_SHORT).show();
        }


        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {
                    Connection connect = connectionClass.CONN(ip, password, user, base);
                    PreparedStatement stmt;
                    String query = "select  SUM( MontantPreavisClient)as Montant from  PreavisClient\t\n" +
                            "where CodeClient='" + codeclient + "' and NumeroEtat='E01'\n" +
                            "group by CodeClient,MontantPreavisClient";
                    stmt = connect.prepareStatement(query);
                    ResultSet rs = stmt.executeQuery();
                    Log.e("preavis", query);

                    while (rs.next()) {
                        montantpreavis = rs.getString("Montant");
                        testpreavis = true;


                        z = "succee";
                    }
                    z = "e";


                }
            } catch (SQLException ex) {
                z = ex.toString();
                Log.e("erreur preavis", "select * ,(sum(MontantPreavisClient))as Montant from PreavisClient where PreavisClient='\"+codeclient+\"' and NumereauEtat='E01'" + z);

            }
            return z;
        }
    }


    /////////////***************************************************/////////////


    public class TestClient extends AsyncTask<String, String, String> {
        String z = "", blocage = "";
        float soldeencour = 0, seuilencour = 0;

        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();

        @Override
        protected void onPreExecute() {
            // progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(String r) {

            //  Toast.makeText(getApplicationContext(),r+blocage+" \n solde="+soldeencour+"\n seuil = "+seuilencour,Toast.LENGTH_LONG).show();
            //  progressBar.setVisibility(View.GONE);
            if (blocage.equals("1")) {
                //Toast.makeText(getApplicationContext(),"bloquer",Toast.LENGTH_SHORT).show();

                AlertDialog.Builder alt = new AlertDialog.Builder(co);


                alt.setIcon(R.drawable.i2s);
                alt.setTitle(" Code Client :  " + codeclient);
                LayoutInflater li = LayoutInflater.from(co);
                View px = li.inflate(R.layout.diagalertdepassement, null);
                alt.setView(px);
                alt.setPositiveButton("Fermer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog d = alt.create();
                d.show();


            }
            if (soldeencour > seuilencour) {
                //  Toast.makeText(getApplicationContext(),"depassement solde",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alt = new AlertDialog.Builder(co);


                alt.setIcon(R.drawable.i2s);
                alt.setTitle(" Code Client :  " + codeclient);
                LayoutInflater li = LayoutInflater.from(co);
                View px = li.inflate(R.layout.diagalertedepassement, null);
                alt.setView(px);


                alt.setPositiveButton("Fermer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog d = alt.create();
                d.show();
            }


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    PreparedStatement ps = con.prepareStatement("select BlocageFacturation,SoldeCredit,SeuilCredit from Client  where CodeClient='" + codeclient + "' ");
                    ResultSet rs = ps.executeQuery();

                    ArrayList data1 = new ArrayList();
                    while (rs.next()) {
                        Map<String, String> datanum = new HashMap<String, String>();
                        blocage = rs.getString("BlocageFacturation");
                        seuilencour = Float.parseFloat(rs.getString("SeuilCredit"));

                        String dates = new SimpleDateFormat("dd/MM/yyyy ", Locale.FRENCH)
                                .format(Calendar.getInstance().getTime());

                        PreparedStatement p = con.prepareStatement("" +
                                "DECLARE\t@return_value int\n" +
                                "\n" +
                                "EXEC\t@return_value = [dbo].[FicheClient]\n" +
                                "\t\t@CodeClient = N'" + codeclient + "',\n" +
                                "\t\t@DateDebut = '" + datedebut + "',\n" +
                                "\t\t@DateFin = '" + dates + "'\n" +
                                "\n" +
                                "SELECT\t'Return Value' = @return_value ");
                        ResultSet r = p.executeQuery();

                        float d = 0;
                        float c = 0;
                        while (r.next()) {
                            d = d + Float.parseFloat(r.getString("Debit"));
                            c = c + Float.parseFloat(r.getString("Credit"));
                        }

                        soldeencour = d - c;


                        prolist.add(datanum);
                        z = "Success";
                    }


                }
            } catch (SQLException ex) {
                z = "list" + ex.toString();

            }
            return z;
        }
    }


    /////////////******************** fin Affichage list ***********************/////////////
    /////////////********************************************************/////////////
    public class GetDataSpinnerClient extends AsyncTask<String, String, String> {
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
            String[] array = datacodeClient.toArray(new String[0]);


            ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getApplicationContext(),
                    R.layout.spinner, dataLibelleClient);
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

                    PreparedStatement stmt;
                    dataLibelleClient = new ArrayList<String>();
                    datacodeClient = new ArrayList<String>();

                    String querydepot = "select RaisonSociale, CodeClient from Client";

                    stmt = con.prepareStatement(querydepot);
                    ResultSet rsss = stmt.executeQuery();
                    Log.e("spincl", querydepot);
                    dataLibelleClient.add("RechercherClient");
                    datacodeClient.add("");
                    while (rsss.next()) {
                        String libelle = rsss.getString("RaisonSociale");
                        dataLibelleClient.add(libelle);
                        String CodeClient = rsss.getString("CodeClient");
                        datacodeClient.add(CodeClient);

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
