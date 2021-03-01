package com.example.suivieadministratif.module.achat;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;

import com.example.suivieadministratif.activity.HomeActivity;
import com.example.suivieadministratif.module.reglementFournisseur.RapportEcheanceFournisseurActivity;
import com.example.suivieadministratif.module.reglementFournisseur.ReglementFournisseurActivity;
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

public class FactureAchat extends AppCompatActivity {
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



    String date_debut = "",date_fin="";

    ConnectionClass connectionClass;
    String CodeSociete, NomUtilisateur, CodeLivreur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facture_achat);

        //sql session
        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " :Facture Achat");
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
        search_bar_client = (SearchView) findViewById(R.id.search_bar_client);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        date_fin = sdf.format(calendar.getTime());
        calendar.add(Calendar.MONTH, -1);
        date_debut = sdf.format(calendar.getTime());
        txt_date_debut.setText(date_debut);
        txt_date_fin.setText(date_fin);
        TextView txt_gratuite =(TextView)findViewById(R.id.txt_gratuite);
        txt_gratuite.setText("Total Facture Achat");
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
        View headerView = nav_menu.getHeaderView(0);


        CardView btn_bon_liv_achat = (CardView) headerView. findViewById(R.id.btn_bon_livraison_achat) ;
        btn_bon_liv_achat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getApplicationContext() , BonLivraisonAchatActivity.class) ;
                startActivity(intent1);
            }
        });


        CardView  btn_bon_cmd_achat = (CardView)  headerView. findViewById(R.id.btn_bon_commande_achat) ;
        btn_bon_cmd_achat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent1 = new Intent(getApplicationContext() , BonCommandeAchatActivity.class) ;
                startActivity(intent1);
            }
        });



        CardView  btn_bon_retour_achat = (CardView) headerView. findViewById(R.id.btn_bon_retour_achat) ;
        btn_bon_retour_achat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent1 = new Intent(getApplicationContext() , BonRetourAchatActivity.class) ;
                startActivity(intent1);
            }
        });


        CardView btn_reglement_frns = (CardView)headerView. findViewById(R.id.btn_reg_frns)  ;

        btn_reglement_frns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getApplicationContext() , ReglementFournisseurActivity.class) ;
                startActivity(intent1);
            }
        });

        CardView   btn_echeance_fournisseur = (CardView) headerView.  findViewById(R.id.btn_echenace_fournisseur);
        btn_echeance_fournisseur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getApplicationContext() , RapportEcheanceFournisseurActivity.class) ;
                startActivity(intent1);

            }
        });
//btn_facture_achat
        CardView   btn_facture_achat = (CardView)  headerView. findViewById(R.id.btn_facture_achat);
        btn_facture_achat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getApplicationContext() , FactureAchat.class) ;
                startActivity(intent1);

            }
        });




        CardView   btn_home= (CardView) headerView.findViewById(R.id.btn_home );
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCaisseRecette  = new Intent(getApplicationContext() , HomeActivity.class) ;
                startActivity(toCaisseRecette);

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

// NumeroDevisAchat,DateCreation,NomUtilisateur,CodeFournisseur,RaisonSociale,TotalTTC,Etat.Libelle as Etat
            String[] from = {"NumeroFactureAchat", "DateCreation",   "RaisonSociale","TotalTTC" , "TotalRemise" ,"TotalHT"  , "TotalTVA"    ,"Etat"};
            int[] views = {R.id.txt_num_bc, R.id.txt_date_bc, R.id.txt_raison_client, R.id.txt_prix_ttc, R.id.txt_remise, R.id.txt_prix_ht, R.id.txt_prix_tva , R.id.txt_libelle_etat};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_bon_commande, from,
                    views);


            final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
            instance.setMinimumFractionDigits(3);
            instance.setMaximumFractionDigits(3);
            txt_tot_commande.setText(instance.format(total_devis));

            lv_list_historique_bc.setAdapter(ADA);
            lv_list_historique_bc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);

                    String  NumeroFactureAchat = (String) obj.get("NumeroFactureAchat");
                    String  DateCreation = (String) obj.get("DateCreation");
                    String  RaisonSociale = (String) obj.get("RaisonSociale");
                    String  Etat = (String) obj.get("Etat");
                    String  TotalTTC = (String) obj.get("TotalTTC");


                    Intent intent=new Intent(getApplicationContext(), DetailLigneFactureAchat.class);
                    intent.putExtra("NumeroFactureAchat",NumeroFactureAchat);
                    intent.putExtra("DateCreation",DateCreation);
                    intent.putExtra("RaisonSociale",RaisonSociale);
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


                    String queryTable = "select NumeroFactureAchat,RaisonSociale,CodeFournisseur,TotalTTC , TotalRemise ,TotalHT  , TotalTVA  , DateCreation ,Etat.Libelle as Etat,NomUtilisateur\n" +
                            ",Etat.NumeroEtat from FactureAchat \n" +
                            "inner join Etat on Etat.NumeroEtat=FactureAchat.NumeroEtat\n" +
                            "where DateCreation between '"+date_debut+"' and '"+date_fin+"'\n" +
                            "order by DateCreation desc";

                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("queryDevisAchat", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";

                    while (rs.next()) {

                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("NumeroFactureAchat", rs.getString("NumeroFactureAchat"));
                        datanum.put("NomUtilisateur", rs.getString("NomUtilisateur"));
                        datanum.put("CodeFournisseur", rs.getString("CodeFournisseur"));
                        datanum.put("RaisonSociale", rs.getString("RaisonSociale"));
                        datanum.put("TotalTTC", rs.getString("TotalTTC"));

                        datanum.put("TotalRemise", rs.getString("TotalRemise"));
                        datanum.put("TotalHT", rs.getString("TotalHT"));
                        datanum.put("TotalTVA", rs.getString("TotalTVA"));

                        datanum.put("Etat", rs.getString("Etat"));
                        datanum.put("NumeroEtat", rs.getString("NumeroEtat"));

                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                        datanum.put("DateCreation", df.format(rs.getDate("DateCreation")));

                        if(!(rs.getString("NumeroEtat").equals("E00")||rs.getString("NumeroEtat").equals("E40")))
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

