package com.example.suivieadministratif.module.achat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.activity.HomeActivity;
import com.example.suivieadministratif.task.EcheanceFournisseurTask;
import com.example.suivieadministratif.task.ListFournisseurTaskForSearchableSpinner;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

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
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RapportEcheanceFournisseurActivity extends AppCompatActivity {

    public   static  ProgressBar pb;
    public   static   ListView lv_list_echeance_fournisseur  ;
    public TextView txt_date_debut, txt_date_fin;

    SearchableSpinner sp_fournisseur ;
    public   static   String  CodeFournisseurSelected = "" ;

    int id_DatePickerDialog = 0;
    Date currentDate = new Date();
    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;

    public static Date date_debut = null;
    public static Date date_fin   = null;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");

    public static TextView txt_tot_ht, txt_tot_tva, txt_tot_ttc;
    public static TextView txt_libelle_ht, txt_libelle_tva, txt_libelle_ttc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etat_achat);


        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Rapport Echéance Fournisseur") ;

        txt_date_debut = findViewById(R.id.txt_date_debut);
        txt_date_fin = findViewById(R.id.txt_date_fin);

        lv_list_echeance_fournisseur  = findViewById( R.id.lv_list ) ;


        sp_fournisseur = (SearchableSpinner) findViewById(R.id.sp_fournisseur);
        pb = (ProgressBar) findViewById(R.id.pb_bc);

        txt_libelle_ht = (TextView) findViewById(R.id.libelle_ht);
        txt_libelle_tva = (TextView) findViewById(R.id.libelle_tva);
        txt_libelle_ttc = (TextView) findViewById(R.id.libelle_ttc);


        txt_tot_ht = (TextView) findViewById(R.id.txt_tot_ht);
        txt_tot_tva = (TextView) findViewById(R.id.txt_total_tva);
        txt_tot_ttc = (TextView) findViewById(R.id.txt_total_ttc);


        txt_libelle_ht.setVisibility(View.INVISIBLE);
        txt_libelle_tva.setVisibility(View.INVISIBLE);

        txt_tot_ht.setVisibility(View.INVISIBLE);
        txt_tot_tva.setVisibility(View.INVISIBLE);
        txt_libelle_ttc.setText("Total Echéance");



        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        //cal1.add(Calendar.MONTH, -1);
        year_x1 = cal1.get(Calendar.YEAR);
        month_x1 = cal1.get(Calendar.MONTH);
        day_x1 = 1;


        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(currentDate);

        year_x2 = cal2.get(Calendar.YEAR);
        month_x2 = cal2.get(Calendar.MONTH);
        day_x2 = cal2.get(Calendar.DAY_OF_MONTH);

        DecimalFormat df_month = new DecimalFormat("00");
        DecimalFormat df_year = new DecimalFormat("0000");

        Log.e("date_debut ", "01/" + df_month.format(cal1.get(Calendar.MONTH) + 1) + "/" + df_year.format(cal1.get(Calendar.YEAR)));
        String _date_du = "01/" + df_month.format(cal1.get(Calendar.MONTH) + 1) + "/" + df_year.format(cal1.get(Calendar.YEAR));

        try {
            date_debut = df.parse(_date_du);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txt_date_debut.setText(_date_du);

        date_fin = cal2.getTime();
        String _date_au = df.format(cal2.getTime());
        txt_date_fin.setText(_date_au);




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


        ListFournisseurTaskForSearchableSpinner listFournisseurTaskForSearchableSpinner = new ListFournisseurTaskForSearchableSpinner(this , sp_fournisseur,"RapportEcheanceFournisseurActivity") ;
        listFournisseurTaskForSearchableSpinner.execute() ;

        updateData () ;


        txt_date_debut.setOnClickListener(new View.OnClickListener  ()  {
            @Override
            public void onClick(View v)  {

                id_DatePickerDialog = 0;
                Log.e("month_x1", "On picker  : " + month_x1);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RapportEcheanceFournisseurActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                                updateData () ;


                            } catch (Exception e) {
                                Log.e("Exception--", " " + e.getMessage());
                            }
                        }
                    }
                }, year_x1, month_x1, day_x1);
                datePickerDialog.show();
            }
        });



        txt_date_fin.setOnClickListener(new View.OnClickListener()  {

            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 1;

                DatePickerDialog datePickerDialog = new DatePickerDialog(RapportEcheanceFournisseurActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                                updateData () ;

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



    public   void   updateData ()
    {
        EcheanceFournisseurTask echeanceFournisseurTask  = new EcheanceFournisseurTask(this  ,date_debut ,date_fin , lv_list_echeance_fournisseur , pb ,CodeFournisseurSelected ) ;
        echeanceFournisseurTask.execute() ;
    }


}