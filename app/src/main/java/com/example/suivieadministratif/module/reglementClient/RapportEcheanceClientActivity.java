package com.example.suivieadministratif.module.reglementClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.activity.HomeActivity;
import com.example.suivieadministratif.module.vente.EtatCommande;
import com.example.suivieadministratif.module.vente.EtatDevisVente;
import com.example.suivieadministratif.module.vente.EtatFactureVente;
import com.example.suivieadministratif.module.vente.EtatLivraisonActivity;
import com.example.suivieadministratif.module.vente.EtatRetourActivity;
import com.example.suivieadministratif.module.vente.MouvementVenteServiceActivity;
import com.example.suivieadministratif.task.EcheanceClientTask;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class RapportEcheanceClientActivity extends AppCompatActivity {


    ProgressBar pb;
    ListView lv_list_echeance_client;
    public TextView txt_date_debut, txt_date_fin;

    int id_DatePickerDialog = 0;
    Date currentDate = new Date();
    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;

    public static Date date_debut = null;
    public static Date date_fin = null;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");

    public static TextView txt_tot_echeance;

    FloatingActionButton fab_arrow;
    RelativeLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rappot_echeance_client);

        txt_date_debut = findViewById(R.id.txt_date_debut);
        txt_date_fin = findViewById(R.id.txt_date_fin);

        lv_list_echeance_client = findViewById(R.id.lv_list_echeance_client);

        pb = (ProgressBar) findViewById(R.id.pb);

        txt_tot_echeance = (TextView) findViewById(R.id.txt_tot_echeance_client);

        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        //cal1.add(Calendar.DAY_OF_YEAR, -7);
        year_x1 = cal1.get(Calendar.YEAR);
        month_x1 = cal1.get(Calendar.MONTH);
        day_x1 = cal1.get(Calendar.DAY_OF_MONTH);


        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(currentDate);
        //  cal2.add(Calendar.DAY_OF_YEAR, +7);
        year_x2 = cal2.get(Calendar.YEAR);
        month_x2 = cal2.get(Calendar.MONTH);
        day_x2 = cal2.get(Calendar.DAY_OF_MONTH);

        date_debut = cal1.getTime();
        String _date_du = df.format(cal1.getTime());
        txt_date_debut.setText(_date_du);

        date_fin = cal2.getTime();
        String _date_au = df.format(cal2.getTime());
        txt_date_fin.setText(_date_au);


        NavigationView nav_menu = findViewById(R.id.nav_view);
        View root = nav_menu.getHeaderView(0);


        CardView btn_devis_vente = (CardView) root.findViewById(R.id.btn_devis_vente);
        btn_devis_vente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getApplicationContext(), EtatDevisVente.class);
                startActivity(intent6);
            }
        });


        CardView btn_bon_livraison = (CardView) root.findViewById(R.id.btn_bon_livraison);
        btn_bon_livraison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getApplicationContext(), EtatLivraisonActivity.class);
                startActivity(intent6);
            }
        });


        CardView btn_bon_retour = (CardView) root.findViewById(R.id.btn_bon_retour);
        btn_bon_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(getApplicationContext(), EtatRetourActivity.class);
                startActivity(intent7);
            }
        });


        CardView btn_bon_commande = (CardView) root.findViewById(R.id.btn_bon_commande);
        btn_bon_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getApplicationContext(), EtatCommande.class);
                startActivity(intent5);

            }
        });
        CardView btn_facture_vente = (CardView) root.findViewById(R.id.btn_facture_vente);
        btn_facture_vente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getApplicationContext(), EtatFactureVente.class);
                startActivity(intent5);

            }
        });

        CardView btn_mvmnt_vente_service = (CardView) root.findViewById(R.id.btn_mvmnt_vente_service);
        btn_mvmnt_vente_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getApplicationContext(), MouvementVenteServiceActivity.class);
                startActivity(intent5);
            }
        });


        CardView btn_reglement_client = (CardView) root.findViewById(R.id.btn_reg_client);
        btn_reglement_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ReglementClientActivity.class);
                startActivity(intent1);
            }
        });


        CardView btn_echeance_client = (CardView) root.findViewById(R.id.btn_echeance_Client);
        btn_echeance_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), RapportEcheanceClientActivity.class);
                startActivity(intent1);
            }
        });

        CardView btn_home = (CardView) root.findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCaisseRecette = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(toCaisseRecette);

            }
        });


        updateData();

        txt_date_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 0;
                Log.e("month_x1", "On picker  : " + month_x1);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RapportEcheanceClientActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(RapportEcheanceClientActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        EcheanceClientTask echeanceClientTask = new EcheanceClientTask(this, date_debut, date_fin, lv_list_echeance_client, pb);
        echeanceClientTask.execute();

    }


}