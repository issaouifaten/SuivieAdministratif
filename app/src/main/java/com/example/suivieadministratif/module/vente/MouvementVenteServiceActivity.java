package com.example.suivieadministratif.module.vente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.activity.HomeActivity;
import com.example.suivieadministratif.adapter.BonCommandeAdapter;
import com.example.suivieadministratif.module.reglementClient.RapportEcheanceClientActivity;
import com.example.suivieadministratif.module.reglementClient.ReglementClientActivity;
import com.example.suivieadministratif.task.HistoriqueBCTask;
import com.example.suivieadministratif.task.MouvementVenteServiceTask;
import com.google.android.material.navigation.NavigationView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MouvementVenteServiceActivity extends AppCompatActivity {


    ListView lv_list_mvmnt_service;
    ProgressBar pb_bc;
    SearchView search_bar_client;

    public TextView txt_date_debut, txt_date_fin ;
    public static TextView txt_tot_mvmnt_vente_service;

    final Context co = this;
    String user, password, base, ip;

    //  public static BonCommandeAdapter bcAdapter;
    int id_DatePickerDialog = 0;
    Date currentDate = new Date();
    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;

    public static Date date_debut = null;
    public static Date date_fin = null;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouvement_vente_service);


        //sql session
        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Mouvement de vente service");

        NavigationView nav_menu=findViewById(R.id.nav_view);
        View root = nav_menu.getHeaderView(0);


        CardView btn_devis_vente = (CardView) root.findViewById(R.id.btn_devis_vente)  ;
        btn_devis_vente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getApplicationContext(), EtatDevisVente.class);
                startActivity(intent6);
            }
        });




        CardView   btn_bon_livraison = (CardView) root.findViewById(R.id.btn_bon_livraison)  ;
        btn_bon_livraison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getApplicationContext(), EtatLivraisonActivity.class);
                startActivity(intent6);
            }
        });


        CardView btn_bon_retour = (CardView) root.findViewById(R.id.btn_bon_retour)  ;
        btn_bon_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(getApplicationContext(), EtatRetourActivity.class);
                startActivity(intent7);
            }
        });


        CardView  btn_bon_commande = (CardView) root.findViewById(R.id.btn_bon_commande) ;
        btn_bon_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getApplicationContext(), EtatCommande.class);
                startActivity(intent5);

            }
        });
        CardView  btn_facture_vente = (CardView) root.findViewById(R.id.btn_facture_vente) ;
        btn_facture_vente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getApplicationContext(), EtatFactureVente.class);
                startActivity(intent5);

            }
        });

        CardView  btn_mvmnt_vente_service= (CardView) root.findViewById(R.id.btn_mvmnt_vente_service) ;
        btn_mvmnt_vente_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getApplicationContext(), MouvementVenteServiceActivity.class);
                startActivity(intent5);
            }
        });


        CardView   btn_reglement_client = (CardView)  root.findViewById(R.id.btn_reg_client)  ;
        btn_reglement_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent1 = new Intent(getApplicationContext() , ReglementClientActivity.class) ;
                startActivity(intent1);
            }
        });


        CardView   btn_echeance_client = (CardView)   root.findViewById(R.id.btn_echeance_Client);
        btn_echeance_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent1 = new Intent(getApplicationContext() , RapportEcheanceClientActivity.class) ;
                startActivity(intent1);
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


        txt_date_debut = findViewById(R.id.txt_date_debut);
        txt_date_fin = findViewById(R.id.txt_date_fin);
        txt_tot_mvmnt_vente_service = findViewById(R.id.txt_tot_mvmnt_vente_service);

        lv_list_mvmnt_service = (ListView) findViewById(R.id.lv_list_mvmnt_vente_service);
        pb_bc = (ProgressBar) findViewById(R.id.pb_bc);
        search_bar_client = (SearchView) findViewById(R.id.search_bar_client);

        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        cal1.add(Calendar.MONTH, -1);
        year_x1 = cal1.get(Calendar.YEAR);
        month_x1 = cal1.get(Calendar.MONTH);
        day_x1 = cal1.get(Calendar.DAY_OF_MONTH);


        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(currentDate);

        year_x2 = cal2.get(Calendar.YEAR);
        month_x2 = cal2.get(Calendar.MONTH);
        day_x2 = cal2.get(Calendar.DAY_OF_MONTH);

        date_debut = cal1.getTime();
        String _date_du = df.format(cal1.getTime());
        txt_date_debut.setText(_date_du);

        date_fin = cal2.getTime();
        String _date_au = df.format(cal2.getTime());
        txt_date_fin.setText(_date_au);


        updateData();

        txt_date_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 0;
                Log.e("month_x1", "On picker  : " + month_x1);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MouvementVenteServiceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        if (id_DatePickerDialog == 0) {
                            year_x1 = year;
                            month_x1 = monthOfYear;
                            day_x1 = dayOfMonth;

                            txt_date_debut.setText("" + formatter.format(day_x1) + "/" + formatter.format(month_x1 + 1) + "/" + year_x1);

                            String _date_du = formatter.format(day_x1) + "/" + formatter.format(month_x1 + 1) + "/" + year_x1 + " ";
                            String _date_au = txt_date_fin.getText().toString();


                            try {
                                date_debut = df.parse(_date_du);
                                date_fin = df.parse(_date_au);

                                updateData();


                            } catch (Exception e) {
                                Log.e("Exception--", " " + e.getMessage());
                            }
                        }
                    }
                }, year_x1, month_x1, day_x1);
                datePickerDialog.show();
            }
        });


        txt_date_fin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 1;

                DatePickerDialog datePickerDialog = new DatePickerDialog(MouvementVenteServiceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (id_DatePickerDialog == 1) {

                            year_x2 = year;
                            month_x2 = monthOfYear;
                            day_x2 = dayOfMonth;

                            txt_date_fin.setText("" + formatter.format(day_x2) + "/" + formatter.format(month_x2 + 1) + "/" + year_x2);

                            String _date_au = "" + formatter.format(day_x2) + "/" + formatter.format(month_x2 + 1) + "/" + year_x2;
                            String _date_du = txt_date_debut.getText().toString();

                            try {
                                date_debut = df.parse(_date_du);
                                date_fin = df.parse(_date_au);

                                updateData();

                            } catch (Exception e) {
                                Log.e("Exception --", " " + e.getMessage());
                            }

                        }
                    }
                }, year_x2, month_x2, day_x2);
                datePickerDialog.show();
            }
        });

    }

    public void updateData() {

        MouvementVenteServiceTask mouvementVenteServiceTask = new MouvementVenteServiceTask(this, date_debut, date_fin, lv_list_mvmnt_service, pb_bc, search_bar_client);
        mouvementVenteServiceTask.execute();

    }


}