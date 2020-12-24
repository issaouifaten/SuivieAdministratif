package com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.CommandeFrnsNonConformeTask;
import com.example.suivieadministratif.task.ListDepotTaskForSearchableSpinner;
import com.example.suivieadministratif.task.ListFournisseurTaskForSearchableSpinner;
import com.example.suivieadministratif.task.ListNatureArticleTaskForSearchableSpinner;
import com.example.suivieadministratif.task.ListRespAdministrationTaskForSearchableSpinner;
import com.example.suivieadministratif.task.SuivieCMD_FournisseurTask;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.nio.charset.CoderMalfunctionError;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommandeFournisseurNonConforme extends AppCompatActivity {


    public static RecyclerView rv_list_cmd_frns_nn_conforme;
    public static ProgressBar pb;


    public TextView txt_date_debut, txt_date_fin;

    int id_DatePickerDialog = 0;
    Date currentDate = new Date();
    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;

    public static Date date_debut = null;
    public static Date date_fin = null;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");

    public static TextView txt_tot_ht;

    FloatingActionButton fab_arrow;
    RelativeLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;

    public static SearchableSpinner sp_fournisseur;
    public static SearchableSpinner sp_resp_administration;


    public static String CodeFournisseurSelected = "";
    public static String CodeRespAdmin = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande_fournisseur_non_conforme);


        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : CMD Frns Non Conforme");


        txt_date_debut = findViewById(R.id.txt_date_debut);
        txt_date_fin = findViewById(R.id.txt_date_fin);
        txt_tot_ht = (TextView) findViewById(R.id.txt_tot_ht);

        rv_list_cmd_frns_nn_conforme = (RecyclerView) findViewById(R.id.rv_list_cmd_frns_nn_conforme);
        rv_list_cmd_frns_nn_conforme.setHasFixedSize(true);
        rv_list_cmd_frns_nn_conforme.setLayoutManager(new LinearLayoutManager(this));

        pb = (ProgressBar) findViewById(R.id.pb_bc);

        sp_fournisseur = (SearchableSpinner) findViewById(R.id.sp_fournisseur);
        sp_resp_administration = (SearchableSpinner) findViewById(R.id.sp_resp_admin);


        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        cal1.add(Calendar.MONTH, -3);
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

        ListFournisseurTaskForSearchableSpinner listFournisseurTaskForSearchableSpinner = new ListFournisseurTaskForSearchableSpinner(this, sp_fournisseur, "CommandeFournisseurNonConforme");
        listFournisseurTaskForSearchableSpinner.execute();

        ListRespAdministrationTaskForSearchableSpinner listRespAdministrationTaskForSearchableSpinner = new ListRespAdministrationTaskForSearchableSpinner(this, sp_resp_administration, "CommandeFournisseurNonConforme");
        listRespAdministrationTaskForSearchableSpinner.execute();

            updateData() ;

            txt_date_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 0;
                Log.e("month_x1", "On picker  : " + month_x1);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CommandeFournisseurNonConforme.this, new DatePickerDialog.OnDateSetListener() {
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(CommandeFournisseurNonConforme.this, new DatePickerDialog.OnDateSetListener() {
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


    public void updateData() {

            CommandeFrnsNonConformeTask commandeFrnsNonConformeTask = new CommandeFrnsNonConformeTask(this , rv_list_cmd_frns_nn_conforme ,pb ,date_debut,date_fin ,CodeFournisseurSelected , CodeRespAdmin) ;
            commandeFrnsNonConformeTask.execute() ;
    }
}