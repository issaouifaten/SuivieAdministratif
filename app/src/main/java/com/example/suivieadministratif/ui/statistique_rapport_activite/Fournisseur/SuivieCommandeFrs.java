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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.adapter.BonLivraisonAdapter;
import com.example.suivieadministratif.model.BonLivraisonVente;
import com.example.suivieadministratif.module.vente.EtatLivraisonActivity;
import com.example.suivieadministratif.task.ListArticleTaskForSearchableSpinner;
import com.example.suivieadministratif.task.ListDepotTaskForSearchableSpinner;
import com.example.suivieadministratif.task.ListNatureArticleTaskForSearchableSpinner;
import com.example.suivieadministratif.task.SuivieCMD_FournisseurTask;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SuivieCommandeFrs extends AppCompatActivity {


    public static  RecyclerView rv_list_suivi_cmd_frns;
    public static  ProgressBar pb;


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

    FloatingActionButton fab_arrow   ;
    RelativeLayout layoutBottomSheet ;
    BottomSheetBehavior sheetBehavior;

    SearchView search_bar_article;

   // public static SearchableSpinner sp_article ;

    public   static   String  CodeDepotSelected  = "" ;
    public   static   String  DepotSelected  = "" ;
     public   static   String  term_rech_art  = "" ;

    public   static   String  CodeNatureArticleSelected  = "" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suivie_commande_frs);


        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Suiv. Cmd. Fournisseurs");


        txt_date_debut = findViewById(R.id.txt_date_debut);
        txt_date_fin = findViewById(R.id.txt_date_fin);
        txt_tot_ht = (TextView) findViewById(R.id.txt_tot_ht);
        search_bar_article = (SearchView) findViewById(R.id.search_bar_article);

        rv_list_suivi_cmd_frns = (RecyclerView) findViewById(R.id.rv_list_suivi_cmd_frns);
        rv_list_suivi_cmd_frns.setHasFixedSize(true);
        rv_list_suivi_cmd_frns.setLayoutManager(new LinearLayoutManager(this));

        pb = (ProgressBar) findViewById(R.id.pb_bc);
        term_rech_art="" ;
        SearchableSpinner sp_depot = (SearchableSpinner)  findViewById(R.id.sp_depot);
        //sp_article = (SearchableSpinner)  findViewById(R.id.sp_article);
        SearchableSpinner sp_nature_article = (SearchableSpinner)  findViewById(R.id.sp_nature_article);

        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        cal1.add(Calendar.MONTH, -3);
        year_x1  = cal1.get(Calendar.YEAR);
        month_x1 = cal1.get(Calendar.MONTH);
        day_x1   = cal1.get(Calendar.DAY_OF_MONTH);


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

        ListDepotTaskForSearchableSpinner listDepotTaskForSearchableSpinner = new ListDepotTaskForSearchableSpinner(this, sp_depot , "SuivieCommandeFrs");
        listDepotTaskForSearchableSpinner.execute();

        ListNatureArticleTaskForSearchableSpinner  listNatureArticleTaskForSearchableSpinner  = new ListNatureArticleTaskForSearchableSpinner(this, sp_nature_article , "SuivieCommandeFrs");
        listNatureArticleTaskForSearchableSpinner.execute();


        txt_date_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 0;
                Log.e("month_x1", "On picker  : " + month_x1);
                DatePickerDialog datePickerDialog = new DatePickerDialog(SuivieCommandeFrs.this, new DatePickerDialog.OnDateSetListener() {
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(SuivieCommandeFrs.this, new DatePickerDialog.OnDateSetListener() {
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


      Button btn_pourcent = (Button)  findViewById(R.id.btn_pourcent) ;
      btn_pourcent.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {


              String  last_query  =  search_bar_article.getQuery().toString() ;
              String new_query  = last_query+"%" ;
              search_bar_article.setQuery(new_query , false);

          }
      });


        search_bar_article.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!search_bar_article.isIconified()) {
                    search_bar_article.setIconified(true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String term) {

                term_rech_art = term ;

                updateData();

                return false;

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

    public  void  updateData()  {

        SuivieCMD_FournisseurTask  suivieCMD_fournisseurTask = new SuivieCMD_FournisseurTask(this , rv_list_suivi_cmd_frns ,pb ,date_debut,date_fin , CodeDepotSelected , term_rech_art ,CodeNatureArticleSelected, "SuivieCommandeFrs") ;
        suivieCMD_fournisseurTask.execute() ;

    }







}