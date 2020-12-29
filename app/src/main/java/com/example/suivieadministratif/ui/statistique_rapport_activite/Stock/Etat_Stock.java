package com.example.suivieadministratif.ui.statistique_rapport_activite.Stock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Etat_Stock extends AppCompatActivity {
    RadioButton bt_achatht,bt_achattc,bt_venteht,bt_ventettc,bt_valeurmin,bt_valeurmax;
    String query_list="",query_list_min_max="";

    ConnectionClass connectionClass;
    String CodeSociete, NomUtilisateur, CodeLivreur;
    final Context co = this;
    String user, password, base, ip;

    GridView gridEtat;
    ProgressBar progressBar;

    String condition="",conditionDepense="",conditionArticle="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(  R.layout.activity_etat__stock);
        bt_achatht=(RadioButton)findViewById(R.id.bt_prix_achatht);
        bt_achattc=(RadioButton)findViewById(R.id.bt_prix_achatttc);
        bt_venteht=(RadioButton)findViewById(R.id.bt_prix_venteht);
        bt_ventettc=(RadioButton)findViewById(R.id.bt_prix_ventettc);
        bt_valeurmax=(RadioButton)findViewById(R.id.bt_valeurmax);
        bt_valeurmin=(RadioButton)findViewById(R.id.bt_valeurmin);
        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " :Etat Stock");

        connectionClass = new ConnectionClass();

        SharedPreferences prefe = getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);



        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);
        gridEtat=(GridView)findViewById(R.id.grid_etat) ;
        progressBar=(ProgressBar)findViewById(R.id.progress_bar) ;

        bt_achatht.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    query_list="  SELECT Article.CodeArticle, Article.Designation, Stock.Quantite, Depot.Libelle, \n" +
                            " Article.CodeTVA, Article.PrixAchatHT,convert(numeric(18,3),(TVA.TauxTVA+100)/100*Article.PrixAchatHT)as Prix ,\n" +
                            " convert(numeric(18,3),(TVA.TauxTVA+100)/100*Article.PrixAchatHT*Stock.Quantite)as Montant \n" +
                            " FROM   Article Article \n" +
                            " INNER JOIN (Depot Depot \n" +
                            " INNER JOIN Stock Stock ON Depot.CodeDepot=Stock.CodeDepot) \n" +
                            " ON Article.CodeArticle=Stock.CodeArticle\n" +
                            "  inner join TVA ON TVA.CodeTVA=Article.CodeTVA\n" +
                            "where Stock.Quantite!=0";
                    FillList fillList=new FillList();
                    fillList.execute();

                }
            }
        });

        bt_achattc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    query_list="  SELECT Article.CodeArticle, Article.Designation, Article.PrixAchatHT, Stock.Quantite, Depot.Libelle\n" +
                            " ,convert(numeric(18,3),Article.PrixAchatHT*Stock.Quantite )as Montant\n" +
                            "  ,convert(numeric(18,3),Article.PrixAchatHT )as Prix\n" +
                            " FROM   Article Article INNER JOIN (Depot Depot INNER JOIN Stock Stock ON Depot.CodeDepot=Stock.CodeDepot) \n" +
                            " ON Article.CodeArticle=Stock.CodeArticle\n" +
                            " where Stock.Quantite!=0";
                    FillList fillList=new FillList();
                    fillList.execute();

                }
            }
        });
        bt_venteht.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    query_list=" SELECT Article.CodeArticle, Article.Designation, Stock.Quantite, \n" +
                            "Depot.Libelle,convert(numeric(18,3), Article.PrixVenteHT) as Prix,\n" +
                            "convert(numeric(18,3), Article.PrixVenteHT*Stock.Quantite )as Montant\n" +
                            "FROM   Article Article \n" +
                            "INNER JOIN (Depot Depot INNER JOIN Stock Stock ON Depot.CodeDepot=Stock.CodeDepot) ON Article.CodeArticle=Stock.CodeArticle\n" +
                            "where Quantite!=0";
                    FillList fillList=new FillList();
                    fillList.execute();
                }
            }
        });

        bt_ventettc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    query_list="SELECT Article.CodeArticle, Article.Designation, Stock.Quantite, Depot.Libelle, convert(numeric(18,3),Article.PrixVenteTTC) as Prix\n" +
                            ",convert(numeric(18,3),Article.PrixVenteTTC* Stock.Quantite)as Montant \n" +
                            "FROM   Article Article\n" +
                            "INNER JOIN (Depot Depot INNER JOIN Stock Stock ON Depot.CodeDepot=Stock.CodeDepot)\n" +
                            "ON Article.CodeArticle=Stock.CodeArticle\n" +
                            "where Quantite!=0";
                    FillList fillList=new FillList();
                    fillList.execute();

                }
            }
        });


        bt_valeurmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    query_list_min_max="SELECT Article.CodeFamille, Article.CodeSousFamille, Stock.CodeArticle, Article.Designation, \n" +
                            "convert(numeric(18,3),Stock.PrixUnitaireAchat) as Prix , Stock.CodeDepot, Depot.Libelle as Depot, FamilleArticle.Libelle as FamilleArticle,\n" +
                            " SousFamilleArticle.Libelle as SousFamilleArticle ,\n" +
                            " convert(numeric(18,0),Stock.QuaniteMinimale)  as Quantite,convert(numeric(18,3),Stock.QuaniteMinimale*Stock.PrixUnitaireAchat) as Montant,\n" +
                            "  UniteArticle.NombreDecimal\n" +
                            "FROM   ((((Stock Stock INNER JOIN Article Article ON Stock.CodeArticle=Article.CodeArticle) \n" +
                            "INNER JOIN Depot Depot ON Stock.CodeDepot=Depot.CodeDepot) \n" +
                            "INNER JOIN SousFamilleArticle SousFamilleArticle ON (Article.CodeSousFamille=SousFamilleArticle.CodeSousFamille) \n" +
                            "AND (Article.CodeFamille=SousFamilleArticle.CodeFamille))\n" +
                            "INNER JOIN UniteArticle UniteArticle ON Article.CodeUnite=UniteArticle.CodeUnite)\n" +
                            "INNER JOIN FamilleArticle FamilleArticle ON SousFamilleArticle.CodeFamille=FamilleArticle.CodeFamille\n" +
                            "ORDER BY Stock.CodeDepot, Article.CodeFamille, Article.CodeSousFamille\n";
                    FillList_Min_Max fillList=new FillList_Min_Max();
                    fillList.execute();

                }
            }
        });

        bt_valeurmax.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    query_list_min_max="SELECT Article.CodeFamille, Article.CodeSousFamille, Stock.CodeArticle, Article.Designation, \n" +
                            "convert(numeric(18,3),Stock.PrixUnitaireAchat) as Prix, Stock.CodeDepot, Depot.Libelle as Depot, FamilleArticle.Libelle as FamilleArticle,\n" +
                            " SousFamilleArticle.Libelle as SousFamilleArticle ,\n" +
                            " convert(numeric(18,0),Stock.QuantiteMaximale)  as Quantite ,convert(numeric(18,3),Stock.QuantiteMaximale*Stock.PrixUnitaireAchat) as Montant,\n" +
                            "  UniteArticle.NombreDecimal\n" +
                            "FROM   ((((Stock Stock INNER JOIN Article Article ON Stock.CodeArticle=Article.CodeArticle) \n" +
                            "INNER JOIN Depot Depot ON Stock.CodeDepot=Depot.CodeDepot) \n" +
                            "INNER JOIN SousFamilleArticle SousFamilleArticle ON (Article.CodeSousFamille=SousFamilleArticle.CodeSousFamille) \n" +
                            "AND (Article.CodeFamille=SousFamilleArticle.CodeFamille))\n" +
                            "INNER JOIN UniteArticle UniteArticle ON Article.CodeUnite=UniteArticle.CodeUnite)\n" +
                            "INNER JOIN FamilleArticle FamilleArticle ON SousFamilleArticle.CodeFamille=FamilleArticle.CodeFamille\n" +
                            "ORDER BY Stock.CodeDepot, Article.CodeFamille, Article.CodeSousFamille";
                    FillList_Min_Max fillList=new FillList_Min_Max();
                    fillList.execute();

                }
            }
        });
        bt_achatht.setChecked(true);




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


            String[] from = { "CodeArticle", "Libelle", "Prix", "Montant", "Designation" ,"Quantite"};
            int[] views = {R.id.txt_code, R.id.txt_depot,  R.id.txt_prix, R.id.txt_montant,R.id.txt_designation,R.id.txt_qt};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_etat_stock, from,
                    views);




            gridEtat.setAdapter(ADA);


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {



                    PreparedStatement ps = con.prepareStatement(query_list);
                    Log.e("query_list", query_list);

                    ResultSet rs = ps.executeQuery();
                    z = "e";
                    String ancien = "";
                    int posi = 0;
                    while (rs.next()) {

                        Map<String, String> datanum = new HashMap<String, String>();

                        datanum.put("CodeArticle", rs.getString("CodeArticle"));

                        datanum.put("Prix", rs.getString("Prix"));
                        datanum.put("Montant", rs.getString("Montant"));
                        datanum.put("Libelle", rs.getString("Libelle"));
                        datanum.put("Quantite", rs.getString("Quantite"));

                        datanum.put("Designation", rs.getString("Designation"));

                

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





    public class FillList_Min_Max extends AsyncTask<String, String, String> {
        String z = "";
        Boolean test = false;

        ArrayList<Integer> list = new ArrayList<Integer>();
        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();


        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            list.clear();

        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);


            String[] from = { "Depot", "SousFamilleArticle", "FamilleArticle", "Montant", "Designation" ,"Quantite","Prix"};
            int[] views = {R.id.txt_code, R.id.txt_depot,  R.id.txt_prix, R.id.txt_montant,R.id.txt_designation,R.id.txt_qt};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_etat_stock, from,
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
                    convertView = layoutInflater.inflate(R.layout.item_etat_stock_min_max, null);

                   // final TextView txt_codedepot = (TextView) convertView.findViewById(R.id.txt_codedepot);
                    final TextView txt_qt = (TextView) convertView.findViewById(R.id.txt_qt);
                    final TextView txt_designation_article = (TextView) convertView.findViewById(R.id.txt_designation_article);
                    final TextView txt_depot = (TextView) convertView.findViewById(R.id.txt_depot);
                    final TextView txt_famille = (TextView) convertView.findViewById(R.id.txt_famille);
                    final TextView txt_sous_famille = (TextView) convertView.findViewById(R.id.txt_sous_famille);
                    final TextView txt_prix = (TextView) convertView.findViewById(R.id.txt_prix);
                    final TextView txt_montant = (TextView) convertView.findViewById(R.id.txt_montant);
                    final LinearLayout l_sousfamille = (LinearLayout) convertView.findViewById(R.id.l_sousfamille);

                    final HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);
                    // String[] from = { "Depot", "SousFamilleArticle", "FamilleArticle", "Montant", "Designation" ,"Quantite","Prix"};
                    String Depot = (String) obj.get("Depot");
                    String SousFamilleArticle = (String) obj.get("SousFamilleArticle");
                    String Quantite = (String) obj.get("Quantite");
                    String Montant = (String) obj.get("Montant");
                    String Prix = (String) obj.get("Prix");

                    String FamilleArticle = (String) obj.get("FamilleArticle");
                    String Designation = (String) obj.get("Designation");

                   txt_qt.setText(Quantite);
                    txt_designation_article.setText(Designation);
                    txt_depot.setText(Depot);
                    txt_prix.setText(Prix);
                    txt_famille.setText(FamilleArticle);
                    txt_montant.setText(Montant);
                    txt_sous_famille.setText(SousFamilleArticle);



                    if (list.contains(position)) {
                        l_sousfamille.setVisibility(View.VISIBLE);
                    } else {
                        l_sousfamille.setVisibility(View.GONE);
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



                    PreparedStatement ps = con.prepareStatement(query_list_min_max);
                    Log.e("query_list_min_max", query_list_min_max);

                    ResultSet rs = ps.executeQuery();
                    z = "e";
                    String ancien = "";
                    int posi = 0;
                    while (rs.next()) {

                        Map<String, String> datanum = new HashMap<String, String>();

                        datanum.put("CodeArticle", rs.getString("CodeArticle"));
                        datanum.put("Designation", rs.getString("Designation"));
                        datanum.put("FamilleArticle", rs.getString("FamilleArticle"));
                        datanum.put("Depot", rs.getString("Depot"));
                        datanum.put("Prix", rs.getString("Prix"));
                        datanum.put("Montant", rs.getString("Montant"));
                        datanum.put("Quantite", rs.getString("Quantite"));
                        datanum.put("SousFamilleArticle", rs.getString("SousFamilleArticle"));

                        String nom = rs.getString("SousFamilleArticle").toUpperCase();
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




}