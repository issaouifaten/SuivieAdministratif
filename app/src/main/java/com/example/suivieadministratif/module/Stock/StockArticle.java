package com.example.suivieadministratif.module.Stock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockArticle extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_article);

        SharedPreferences pref = getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Etat de Stock");


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




        spinfrs = (Spinner) findViewById(R.id.spinner);
        edtRecherche=(EditText)findViewById(R.id.edt_recherche) ;
        gridArticle=(GridView)findViewById(R.id.grid_article) ;
        Button btadd=(Button)findViewById(R.id.btadd);


        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String oldText=edtRecherche.getText().toString();
                String newText=oldText+"%";
                edtRecherche.setText(newText);
                edtRecherche.setSelection(newText.length());


            }
        });

        ////////////////////////////////////////////////////
 GetDataSpinnerFRS getDataSpinnerFRS=new GetDataSpinnerFRS();
 getDataSpinnerFRS.execute("");
        spinfrs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                edtRecherche.setText("");


                Frs = spinfrs.getSelectedItem().toString();


                if (!Frs.equals("Tout")) {

                    querysearch="SELECT TVA.TauxTVA, Article.CodeArticle,Article.Designation,Stock.Quantite,(\n" +
                            "Select colissage from UniteParArticle where UniteParArticle.CodeArticle=Article.CodeArticle and type='v')AS Colisage ,Fournisseur.RaisonSociale \n" +
                            "   ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                            "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=1)AS Classique\n" +
                            "            ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                            "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=2)AS Comptant\n" +
                            "             ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                            "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=3)AS Promo\n" +
                            "                 ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                            "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=4)AS PrixUsine \n" +
                            "         \n" +
                            "          ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                            "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=1)AS ClassiqueHT\n" +
                            "            ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                            "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=2)AS ComptantHT\n" +
                            "             ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                            "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=3)AS PromoHT\n" +
                            "                 ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                            "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=4)AS PrixUsineHT \n" +
                            "         " +
                            " FROM Stock  INNER JOIN Article ON Article.CodeArticle=Stock.CodeArticle\n" +
                            "INNER JOIN dbo.Fournisseur ON dbo.Fournisseur.CodeFournisseur=Article.CodeFournisseur " +
                            "    INNER JOIN TVA ON TVA.CodeTVA=Article.CodeTVA\n" +
                            " WHERE   Fournisseur.RaisonSociale='"+Frs+"'  and CodeDepot='"+CodeDepot+"'";

                }
                else {
                    querysearch="SELECT   TVA.TauxTVA, Article.CodeArticle,Article.Designation,Stock.Quantite,(\n" +
                            "Select colissage from UniteParArticle where UniteParArticle.CodeArticle=Article.CodeArticle and type='v')AS Colisage ,Fournisseur.RaisonSociale  \n" +
                            "  ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                            "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=1)AS Classique\n" +
                            "            ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                            "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=2)AS Comptant\n" +
                            "             ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                            "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=3)AS Promo\n" +
                            "                 ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                            "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=4)AS PrixUsine \n" +
                            "         \n" +
                            "          ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                            "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=1)AS ClassiqueHT\n" +
                            "            ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                            "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=2)AS ComptantHT\n" +
                            "             ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                            "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=3)AS PromoHT\n" +
                            "                 ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                            "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=4)AS PrixUsineHT \n" +
                            "         " +
                            " FROM Stock INNER JOIN Article ON Article.CodeArticle=Stock.CodeArticle\n" +
                            "INNER JOIN dbo.Fournisseur ON dbo.Fournisseur.CodeFournisseur=Article.CodeFournisseur " +
                            "    INNER JOIN TVA ON TVA.CodeTVA=Article.CodeTVA\n " +
                            "where CodeDepot  ='"+CodeDepot+"'" ;
                }


                if(!CodeDepot.equals(""))


                {
                    Log.e("frsspinfrs", querysearch);
                    FillList fillList=new FillList();
                    fillList.execute("");
                }



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


                    if (Frs.equals("Tout")) {


                        querysearch = "SELECT   TVA.TauxTVA, Article.CodeArticle,Article.Designation,Stock.Quantite,(\n" +
                                "Select colissage from UniteParArticle where UniteParArticle.CodeArticle=Article.CodeArticle and type='v')AS Colisage ,Fournisseur.RaisonSociale  \n" +
                                "   ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                                "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=1)AS Classique\n" +
                                "            ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                                "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=2)AS Comptant\n" +
                                "             ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                                "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=3)AS Promo\n" +
                                "                 ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                                "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=4)AS PrixUsine \n" +
                                "         \n" +
                                "          ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                                "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=1)AS ClassiqueHT\n" +
                                "            ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                                "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=2)AS ComptantHT\n" +
                                "             ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                                "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=3)AS PromoHT\n" +
                                "                 ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                                "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=4)AS PrixUsineHT \n" +
                                "         " +
                                " FROM Stock INNER JOIN Article ON Article.CodeArticle=Stock.CodeArticle\n" +
                                "INNER JOIN dbo.Fournisseur ON dbo.Fournisseur.CodeFournisseur=Article.CodeFournisseur" +
                                "    INNER JOIN TVA ON TVA.CodeTVA=Article.CodeTVA\n " +
                                "where CodeDepot  ='"+CodeDepot+"'"+
                                "and (Article.CodeArticle like '%" + charSequence + "%' or Article.Designation like '%" + charSequence + "%')";
                    }
                    else{


                        querysearch = "SELECT TVA.TauxTVA,Article.CodeArticle,Article.Designation,Stock.Quantite,(\n" +
                                "Select colissage from UniteParArticle where UniteParArticle.CodeArticle=Article.CodeArticle and type='v')AS Colisage ,Fournisseur.RaisonSociale   \n" +
                                "   ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                                "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=1)AS Classique\n" +
                                "            ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                                "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=2)AS Comptant\n" +
                                "             ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                                "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=3)AS Promo\n" +
                                "                 ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                                "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=4)AS PrixUsine \n" +
                                "         \n" +
                                "          ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                                "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=1)AS ClassiqueHT\n" +
                                "            ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                                "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=2)AS ComptantHT\n" +
                                "             ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                                "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=3)AS PromoHT\n" +
                                "                 ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                                "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=4)AS PrixUsineHT \n" +
                                "         " +
                                " FROM Stock INNER JOIN Article ON Article.CodeArticle=Stock.CodeArticle\n" +
                                "INNER JOIN dbo.Fournisseur ON dbo.Fournisseur.CodeFournisseur=Article.CodeFournisseur" +
                                "    INNER JOIN TVA ON TVA.CodeTVA=Article.CodeTVA\n " +
                                "where CodeDepot  ='"+CodeDepot+"' " +
                                "and (Article.CodeArticle like '%" + charSequence + "%' or Article.Designation like '%"
                                + charSequence + "%')and Fournisseur.RaisonSociale='"+Frs+"'  " ;

                    }


                    if(!CodeDepot.equals(""))
                    { FillList fillList=new FillList();
                    fillList.execute("");
                    }

                }

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
                   edtRecherche.setText("");


                   Frs = spinfrs.getSelectedItem().toString();


                   if (!Frs.equals("Tout")) {

                       querysearch="SELECT TVA.TauxTVA, Article.CodeArticle,Article.Designation,Stock.Quantite,(\n" +
                               "Select colissage from UniteParArticle where UniteParArticle.CodeArticle=Article.CodeArticle and type='v')AS Colisage ,Fournisseur.RaisonSociale \n" +
                               "   ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                               "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=1)AS Classique\n" +
                               "            ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                               "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=2)AS Comptant\n" +
                               "             ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                               "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=3)AS Promo\n" +
                               "                 ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                               "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=4)AS PrixUsine \n" +
                               "         \n" +
                               "          ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                               "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=1)AS ClassiqueHT\n" +
                               "            ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                               "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=2)AS ComptantHT\n" +
                               "             ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                               "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=3)AS PromoHT\n" +
                               "                 ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                               "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=4)AS PrixUsineHT \n" +
                               "         " +
                               " FROM Stock  INNER JOIN Article ON Article.CodeArticle=Stock.CodeArticle\n" +
                               "INNER JOIN dbo.Fournisseur ON dbo.Fournisseur.CodeFournisseur=Article.CodeFournisseur " +
                               "    INNER JOIN TVA ON TVA.CodeTVA=Article.CodeTVA\n" +
                               " WHERE   Fournisseur.RaisonSociale='"+Frs+"'  and CodeDepot='"+CodeDepot+"'";

                   }
                   else {
                       querysearch="SELECT   TVA.TauxTVA, Article.CodeArticle,Article.Designation,Stock.Quantite,(\n" +
                               "Select colissage from UniteParArticle where UniteParArticle.CodeArticle=Article.CodeArticle and type='v')AS Colisage ,Fournisseur.RaisonSociale  \n" +
                               "  ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                               "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=1)AS Classique\n" +
                               "            ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                               "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=2)AS Comptant\n" +
                               "             ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                               "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=3)AS Promo\n" +
                               "                 ,( SELECT dbo.MargeArticle.PrixVenteTTC FROM dbo.MargeArticle WHERE\n" +
                               "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=4)AS PrixUsine \n" +
                               "         \n" +
                               "          ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                               "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=1)AS ClassiqueHT\n" +
                               "            ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                               "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=2)AS ComptantHT\n" +
                               "             ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                               "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=3)AS PromoHT\n" +
                               "                 ,( SELECT dbo.MargeArticle.PrixVenteHT FROM dbo.MargeArticle WHERE\n" +
                               "         dbo.MargeArticle.CodeArticle=Stock.CodeArticle AND CodeTypeVente=4)AS PrixUsineHT \n" +
                               "         " +
                               " FROM Stock INNER JOIN Article ON Article.CodeArticle=Stock.CodeArticle\n" +
                               "INNER JOIN dbo.Fournisseur ON dbo.Fournisseur.CodeFournisseur=Article.CodeFournisseur " +
                               "    INNER JOIN TVA ON TVA.CodeTVA=Article.CodeTVA\n " +
                               "where CodeDepot  ='"+CodeDepot+"'" ;
                   }



                   Log.e("frsspindepot", querysearch);
                   FillList fillList = new FillList();
                   fillList.execute("");

               }
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

            String[] from = {"A", "B", "C", "D","Promo","PromoHT","TauxTVA"};
            int[] views = {R.id.codarticle, R.id.designation, R.id.colissage, R.id.quantite, R.id.prixpromo,R.id.prixpromoht,R.id.tva4};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.listarticle_stock, from,
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

                    PreparedStatement ps = con.prepareStatement(querysearch);
                    Log.e("querysearch",querysearch);
                    ResultSet rs = ps.executeQuery();

                    ArrayList data1 = new ArrayList();
                    while (rs.next()) {
                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("A", rs.getString("CodeArticle"));
                        datanum.put("B", rs.getString("Designation"));
                        String quant = rs.getString("Quantite");
                        datanum.put("D", quant.substring(0, quant.length() - 4));

                        String t = rs.getString("Colisage");
                        datanum.put("C", t.substring(0, t.length() - 4));
                        ;datanum.put("ClassiqueHT", rs.getString("ClassiqueHT"));
                        ;datanum.put("Classique", rs.getString("Classique"));
                        ;datanum.put("Comptant", rs.getString("Comptant"));
                        ;datanum.put("ComptantHT", rs.getString("ComptantHT"));
                        ;datanum.put("Promo", rs.getString("Promo"));
                        ;datanum.put("PromoHT", rs.getString("PromoHT"));
                        ;datanum.put("PrixUsine", rs.getString("PrixUsine"));
                        ;datanum.put("PrixUsineHT", rs.getString("PrixUsineHT"));
                        ;datanum.put("TauxTVA", rs.getString("TauxTVA"));
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
                    datalibelle.add("Rechercher Depot");
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
