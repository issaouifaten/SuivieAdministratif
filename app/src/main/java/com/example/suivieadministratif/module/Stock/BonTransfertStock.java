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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.activity.HomeActivity;
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

public class BonTransfertStock extends AppCompatActivity {
    GridView lv_list_historique_bc;
    ProgressBar progressBar;
    SearchView search_bar_client;

    public TextView txt_date_debut, txt_date_fin;
    DatePicker datePicker;
    final Context co = this;
    String user, password, base, ip;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");
    public static TextView txt_tot_commande;

    FloatingActionButton fab_arrow;
    RelativeLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;



    String date_debut = "",date_fin="",condition="";

    ConnectionClass connectionClass;
    String CodeSociete, NomUtilisateur, CodeLivreur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bon_transfert_stock);

        //sql session
        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " :Bon Transfert");
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

        txt_tot_commande = (TextView) findViewById(R.id.txt_tot_commande);
        txt_date_debut = findViewById(R.id.txt_date_debut);
        txt_date_fin = findViewById(R.id.txt_date_fin);


        lv_list_historique_bc = (GridView) findViewById(R.id.lv_list_historique_bc);
        progressBar = (ProgressBar) findViewById(R.id.pb_bc);
        EditText editText=(EditText)findViewById(R.id.search_bar_client) ;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if( s.length()>1) {
                    condition = " and( NumeroBonTransfert like'%" + s + "%'  ) ";

                }else{
                    condition="";
                }
                 FillList fillList = new  FillList();
                fillList.execute("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        date_fin = sdf.format(calendar.getTime());
        calendar.add(Calendar.MONTH, -1);
        date_debut = sdf.format(calendar.getTime());
        txt_date_debut.setText(date_debut);
        txt_date_fin.setText(date_fin);
        TextView txt_gratuite =(TextView)findViewById(R.id.txt_gratuite);
        txt_gratuite.setText("Total Bon Transfert");
      FillList fillList = new  FillList();
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
                alt.setPositiveButton("ok",                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface di, int i) {

                                Date d = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
                                date_debut = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH)
                                        .format(d);

                                txt_date_debut.setText(date_debut);
                                 FillList fillList = new  FillList();
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


                                 FillList fillList = new  FillList();
                                fillList.execute("");
                            }
                        });

                AlertDialog dd = alt.create();
                dd.show();

            }
        });


        layoutBottomSheet = (RelativeLayout) findViewById(R.id.bottom_sheet);
        fab_arrow = (FloatingActionButton) findViewById(R.id.fab_arrow);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setHideable(false);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        // Toast.makeText(getActivity() , "Close Sheet" ,Toast.LENGTH_LONG).show();
                        fab_arrow.setImageResource(R.drawable.ic_arrow_down);
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        // Toast.makeText(getActivity() , "Expand Sheet" ,Toast.LENGTH_LONG).show();
                        fab_arrow.setImageResource(R.drawable.ic_arrow_up);
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        NavigationView nav_menu=findViewById(R.id.nav_view);
        View root = nav_menu.getHeaderView(0);

        CardView btn_etat_de_stock = (CardView) root.findViewById(R.id.btn_etat_de_stock) ;
        btn_etat_de_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), StockArticle.class);
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
        float total_devis=0;


        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);


        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);

// NumeroDevisVente,DateCreation,NomUtilisateur,DepotDestination,DepotSource,TotalTTC,Etat.Libelle as Etat
            String[] from = {"NumeroBonTransfert", "DateCreation",   "DepotSource","TotalTTC","Etat","DepotDestination"};
            int[] views = {R.id.txt_num_bc, R.id.txt_date_bc, R.id.txt_depot_source, R.id.txt_prix_ttc, R.id.txt_libelle_etat,R.id.txt_depot_destination};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_bon_transfert, from,
                    views);



            txt_tot_commande.setText(""+total_devis);
            lv_list_historique_bc.setAdapter(ADA);
            lv_list_historique_bc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);

                    String  NumeroBonTransfert = (String) obj.get("NumeroBonTransfert");
                    String  DateCreation = (String) obj.get("DateCreation");
                    String  DepotSource = (String) obj.get("DepotSource");
                    String  Etat = (String) obj.get("Etat");
                    String  TotalTTC = (String) obj.get("TotalTTC");
                    String  DepotDestination = (String) obj.get("DepotDestination");


                    Intent intent=new Intent(getApplicationContext(), DetailLigneBonTransfert.class);
                    intent.putExtra("NumeroBonTransfert",NumeroBonTransfert);
                    intent.putExtra("DateCreation",DateCreation);
                    intent.putExtra("DepotDestination",DepotDestination);
                    intent.putExtra("DepotSource",DepotSource);
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


                    String queryTable = " select NumeroBonTransfert,TotalTTC,NomUtilisateur ,DateCreation,\n" +
                            "(select Libelle from Depot where Depot.CodeDepot=BonTransfert.CodeDepotDestination) AS  DepotDestination,\n" +
                            "(select Libelle from Depot where Depot.CodeDepot=BonTransfert.CodeDepotSource) AS  DepotSource\n" +
                            ",Etat.Libelle as Etat\n" +
                            "from BonTransfert\n" +
                            "inner join Etat on Etat.NumeroEtat=BonTransfert.NumeroEtat " +
                            "where DateCreation between '"+date_debut+"' and '"+date_fin+"'\n" +condition+
                            "order by DateCreation desc";

                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("queryDevisVente", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";

                    while (rs.next()) {

                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("NumeroBonTransfert", rs.getString("NumeroBonTransfert"));
                        datanum.put("NomUtilisateur", rs.getString("NomUtilisateur"));
                        datanum.put("DepotDestination", rs.getString("DepotDestination"));
                        datanum.put("DepotSource", rs.getString("DepotSource"));
                        datanum.put("TotalTTC", rs.getString("TotalTTC"));
                        datanum.put("Etat", rs.getString("Etat"));

                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                        datanum.put("DateCreation", df.format(rs.getDate("DateCreation")));
                        total_devis+=rs.getFloat("TotalTTC");
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
