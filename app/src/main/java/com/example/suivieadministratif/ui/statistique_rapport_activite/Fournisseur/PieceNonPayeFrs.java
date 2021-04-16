package com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ProgressBar;

import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.ListFournisseurTaskForSearchableSpinner;
import com.example.suivieadministratif.task.PieceNonPayeFournisseurTask;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PieceNonPayeFrs extends AppCompatActivity {

   public  static GridView lv_list_historique_bc;
    public  static  ProgressBar progressBar;


    String user, password, base, ip;

    public static TextView txt_tot_mnt ,txt_total_restant;

    ConnectionClass connectionClass;
    String   NomUtilisateur ;

    int id_DatePickerDialog = 0;
    Date currentDate = new Date();
    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;

    public static Date date_debut = null;
    public static Date date_fin = null;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");

    public TextView txt_date_debut, txt_date_fin ;

    SearchableSpinner sp_fournisseur ;

    public   static   String  CodeFournisseurSelected = "" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piece_non_paye_frs);

        //sql session
        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Pièces non payées Fournisseur");
        connectionClass = new ConnectionClass();

        SharedPreferences prefe = getSharedPreferences("usersession", Context.MODE_PRIVATE);
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);

        sp_fournisseur  = (SearchableSpinner)   findViewById(R.id.sp_client)   ;

        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);

        txt_date_debut = findViewById(R.id.txt_date_debut);
        txt_date_fin = findViewById(R.id.txt_date_fin);

        txt_tot_mnt = (TextView) findViewById(R.id.txt_tot_mnt);
        txt_total_restant = findViewById(R.id.txt_total_restant);

        txt_date_debut = findViewById(R.id.txt_date_debut);
        txt_date_fin = findViewById(R.id.txt_date_fin);

        lv_list_historique_bc = (GridView) findViewById(R.id.lv_list_historique_bc);
        progressBar = (ProgressBar) findViewById(R.id.pb_bc);


        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        //cal1.add(Calendar.MONTH, -1);
        year_x1 = cal1.get(Calendar.YEAR);
        month_x1 = cal1.get(Calendar.MONTH);
        day_x1 = 1 ;


        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(currentDate);

        year_x2 = cal2.get(Calendar.YEAR);
        month_x2 = cal2.get(Calendar.MONTH);
        day_x2 = cal2.get(Calendar.DAY_OF_MONTH);

        DecimalFormat  df_month = new DecimalFormat("00") ;
        DecimalFormat  df_year  = new DecimalFormat("0000") ;


        Log.e("date_debut ", "01/"+ df_month.format(cal1.get(Calendar.MONTH) +1)+"/"+df_year.format(cal1.get(Calendar.YEAR) ) ) ;
        String _date_du =  "01/"+ df_month.format(cal1.get(Calendar.MONTH) +1)+"/"+df_year.format(cal1.get(Calendar.YEAR) )  ;

        try {
            date_debut =  df .parse(_date_du);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txt_date_debut.setText(_date_du);

        date_fin = cal2.getTime();
        String _date_au = df.format(cal2.getTime());
        txt_date_fin.setText(_date_au);


        PieceNonPayeFournisseurTask pieceNonPayeFournisseurTask = new PieceNonPayeFournisseurTask(this , date_debut ,date_fin ,lv_list_historique_bc ,progressBar ,CodeFournisseurSelected)  ;
        pieceNonPayeFournisseurTask.execute() ;

        ListFournisseurTaskForSearchableSpinner listFournisseurTaskForSearchableSpinner = new ListFournisseurTaskForSearchableSpinner(this  , sp_fournisseur  , "PieceNonPayeFrs")  ;
        listFournisseurTaskForSearchableSpinner.execute() ;



        txt_date_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 0;
                Log.e("month_x1", "On picker  : " + month_x1);
                DatePickerDialog datePickerDialog = new DatePickerDialog(PieceNonPayeFrs.this, new DatePickerDialog.OnDateSetListener() {
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

                                PieceNonPayeFournisseurTask pieceNonPayeFournisseurTask = new PieceNonPayeFournisseurTask(PieceNonPayeFrs.this , date_debut ,date_fin ,lv_list_historique_bc ,progressBar ,CodeFournisseurSelected)  ;
                                pieceNonPayeFournisseurTask.execute() ;


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

                DatePickerDialog datePickerDialog = new DatePickerDialog(PieceNonPayeFrs.this, new DatePickerDialog.OnDateSetListener() {
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

                                PieceNonPayeFournisseurTask pieceNonPayeFournisseurTask = new PieceNonPayeFournisseurTask(PieceNonPayeFrs.this , date_debut ,date_fin ,lv_list_historique_bc ,progressBar ,CodeFournisseurSelected)  ;
                                pieceNonPayeFournisseurTask.execute() ;

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




}
