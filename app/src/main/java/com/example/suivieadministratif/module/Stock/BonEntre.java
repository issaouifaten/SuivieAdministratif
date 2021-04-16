package com.example.suivieadministratif.module.Stock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.activity.HomeActivity;

import com.example.suivieadministratif.adapter.BonCommandeAdapter;
import com.example.suivieadministratif.model.BonCommandeVente;
import com.example.suivieadministratif.module.vente.EtatCommande;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BonEntre extends AppCompatActivity {

    ListView lv_list ;
    ProgressBar progressBar;
    SearchView search_bar_client;

    public TextView txt_date_debut, txt_date_fin;
    DatePicker datePicker;
    final Context co = this;
    String user, password, base, ip;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");
    public static TextView txt_tot;


    String condition="";



    String date_debut = "",date_fin="";

    ConnectionClass connectionClass;
    String  NomUtilisateur ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etat_stock);

        //sql session
        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " :Bon Entrée");
        connectionClass = new ConnectionClass();

        SharedPreferences prefe = getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);

        // SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);

        txt_tot = (TextView) findViewById(R.id.txt_total_ttc);
        txt_date_debut = findViewById(R.id.txt_date_debut);
        txt_date_fin = findViewById(R.id.txt_date_fin);


        lv_list = (ListView) findViewById(R.id.lv_list);
        progressBar = (ProgressBar) findViewById(R.id.pb_bc);

        SearchView searchView= findViewById(R.id.search_bar_client) ;
        searchView  . setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!search_bar_client.isIconified()) {
                    search_bar_client.setIconified(true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if( s.length() > 0) {
                    condition = " and( NumeroBonEntrer like'%" + s + "%'  or  Depot.Libelle  like'%" + s + "%' ) ";

                }else{
                    condition="";
                }

                FillList fillList = new  FillList();
                fillList.execute("");


                return false;

            }
        });




        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        date_fin = sdf.format(calendar.getTime());
        calendar.add(Calendar.MONTH, -1);
        date_debut = sdf.format(calendar.getTime());
        txt_date_debut.setText(date_debut);
        txt_date_fin.setText(date_fin);


     /*   TextView txt_gratuite =(TextView)findViewById(R.id.txt_gratuite);
        txt_gratuite.setText("Total Entre");*/



        FillList fillList = new FillList();
        fillList.execute("");

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



        NavigationView nav_menu=findViewById(R.id.nav_view);
        View root = nav_menu.getHeaderView(0);

         CardView btn_etat_de_stock = (CardView) root.findViewById(R.id.btn_etat_de_stock) ;
       btn_etat_de_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), EtatDeStockActivity.class);
                startActivity(intent2);
            }
        });

        //btn_bon_entree
        CardView  btn_bon_entree = (CardView) root.findViewById(R.id.btn_bon_entree) ;
        btn_bon_entree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), BonEntre.class);
                startActivity(intent2);
            }
        });
        //btn_bonsortie_de_stock
        CardView  btn_bonsortie_de_stock = (CardView) root.findViewById(R.id.btn_bonsortie_de_stock) ;
        btn_bonsortie_de_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), BonSortie.class);
                startActivity(intent2);
            }
        });
        //btn_passation_bon_transfert

        CardView  btn_passation_bon_transfert = (CardView) root.findViewById(R.id.btn_passation_bon_transfert) ;
        btn_passation_bon_transfert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), BonTransfertStock.class);
                startActivity(intent2);
            }
        });
        CardView   btn_home= (CardView) root.findViewById(R.id.btn_home );
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCaisseRecette  = new Intent(getApplicationContext() , HomeActivity.class) ;
                startActivity(toCaisseRecette);

            }
        });
//btn_bon_redressement
        CardView   btn_bon_redressement= (CardView) root.findViewById(R.id.btn_bon_redressement );
        btn_bon_redressement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBonRedressement = new Intent(getApplicationContext() , BonRedressement.class) ;
                startActivity(toBonRedressement);

            }
        });
    }

    public class FillList extends AsyncTask<String, String, String> {
        String z = "";
        Boolean test = false;


        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();
        float total_ttc=0;


        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);


        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);


            String[] from = {"NumeroBonEntrer", "DateCreation",   "Depot", "TotalHT", "TotalTVA", "TotalTTC",  "Etat"};
            int[] views = {R.id.txt_num_piece, R.id.txt_date_bc, R.id.txt_raison_client, R.id.txt_prix_net_ht, R.id.txt_prix_tva,  R.id.txt_prix_ttc, R.id.txt_libelle_etat};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_etat_entete, from,  views);

            txt_tot.setText(""+total_ttc);
            
            lv_list.setAdapter(ADA);
            lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);

                    String  NumeroBonEntrer = (String) obj.get("NumeroBonEntrer");
                    String  DateCreation = (String) obj.get("DateCreation");
                    String  Depot = (String) obj.get("Depot");
                    String  Etat = (String) obj.get("Etat");
                    String  TotalTTC = (String) obj.get("TotalTTC");


                    Intent intent=new Intent(getApplicationContext(), DetailLigneBonEntre.class);
                    intent.putExtra("NumeroBonEntrer",NumeroBonEntrer);
                    intent.putExtra("DateCreation",DateCreation);
                    intent.putExtra("Depot",Depot);
                    intent.putExtra("TotalTTC",TotalTTC);
                    intent.putExtra("Etat",Etat);
                    startActivity(intent);



                }
            });


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {


                    String queryTable = "select NumeroBonEntrer,Depot.Libelle as Depot ,BonEntrer.CodeDepot ,  TotalHT  , TotalTVA    ,TotalTTC,DateCreation ,Etat.Libelle as Etat,NomUtilisateur\n" +
                            "from BonEntrer \n" +
                            "inner join Etat on Etat.NumeroEtat=BonEntrer.NumeroEtat\n" +
                            "inner join Depot on Depot.CodeDepot=BonEntrer.CodeDepot   " +
                            "where DateCreation between '"+date_debut+"' and '"+date_fin+"'\n" +condition+
                            "order by DateCreation desc";

                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("queryEtreeStock", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";

                    while (rs.next()) {

                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("NumeroBonEntrer", rs.getString("NumeroBonEntrer"));
                        datanum.put("NomUtilisateur", rs.getString("NomUtilisateur"));
                        datanum.put("CodeDepot", rs.getString("CodeDepot"));
                        datanum.put("Depot", rs.getString("Depot"));
                        datanum.put("TotalHT", rs.getString("TotalHT"));
                        datanum.put("TotalTVA", rs.getString("TotalTVA"));
                        datanum.put("TotalTTC", rs.getString("TotalTTC"));

                        datanum.put("Etat", rs.getString("Etat"));

                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                        datanum.put("DateCreation", df.format(rs.getDate("DateCreation")));
                        total_ttc+=rs.getFloat("TotalTTC");
                        prolist.add(datanum);

                        test = true;
                        z = "succees";
                    }


                }
            }
            catch (SQLException ex) {

                z = "tablelist" + ex.toString();
                Log.e("erreur", z);

            }
            return z;
        }
    }


}
