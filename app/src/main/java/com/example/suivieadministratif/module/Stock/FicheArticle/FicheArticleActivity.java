package com.example.suivieadministratif.module.Stock.FicheArticle;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.suivieadministratif.model.Depot;
import com.example.suivieadministratif.module.Stock.FicheArticle.ui.main.FicheArticleFragment;
import com.example.suivieadministratif.module.Stock.FicheArticle.ui.main.FicheArticlePagerAdapter;
import com.example.suivieadministratif.task.FicheArticlekParDepotParArticleTask;
import com.example.suivieadministratif.task.ListArticleParDepotTask;
import com.example.suivieadministratif.task.ListDepotTask;
import com.google.android.material.tabs.TabLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.suivieadministratif.R ;

public class FicheArticleActivity extends AppCompatActivity {


    public TextView txt_date_debut, txt_date_fin ;
    TabLayout tab_rapport;


    int id_DatePickerDialog = 0;
    Date currentDate = new Date();
    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;

    public static Date date_debut = null;
    public static Date date_fin = null;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");

    public   static   String  CodeArticleSelected = "" ;
    public   static   String  CodeDepotSelected = "" ;
    public   static   int  index_tab_selected =0 ;

    public  static TabLayout tab_depot  ;
    public   static ArrayList<Depot> listDepot ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_article);
        //SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        final ViewPager viewPager = findViewById(R.id.view_pager);
        // viewPager.setAdapter(sectionsPagerAdapter);
        tab_depot = findViewById(R.id.tab_depot);
        Spinner sp_article = findViewById(R.id.sp_article);

        txt_date_debut =  findViewById(R.id.txt_date_debut) ;
        txt_date_fin    =    findViewById(R.id.txt_date_fin) ;
        //tab_depot.setupWithViewPager(viewPager);

        listDepot= new ArrayList<>() ;
        CodeDepotSelected = "" ;
        index_tab_selected =0 ;


        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
         //cal1.add(Calendar.DAY_OF_YEAR, -1);
        year_x1  = cal1.get(Calendar.YEAR);
        month_x1 = cal1.get(Calendar.MONTH);
        day_x1   = cal1.get(Calendar.DAY_OF_MONTH);


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


        ListDepotTask listDepotTask = new ListDepotTask(this, tab_depot , sp_article, viewPager, "FicheArticleActivity");
        listDepotTask.execute();


      //  CodeDepotSelected = listDepot.get(0).getCodeDepot();

        ListArticleParDepotTask listArticleParDepotTask = new ListArticleParDepotTask (this,sp_article,"01",viewPager ,"FicheArticleActivity");
        listArticleParDepotTask.execute();



        txt_date_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 0;
                Log.e("month_x1", "On picker  : " + month_x1);
                DatePickerDialog datePickerDialog = new DatePickerDialog(FicheArticleActivity.this, new DatePickerDialog.OnDateSetListener() {
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


                                Log.e("tab_depot_slected" , ""+tab_depot.getSelectedTabPosition());
                                CodeDepotSelected = listDepot.get(tab_depot.getSelectedTabPosition()).getCodeDepot();
                                index_tab_selected=tab_depot.getSelectedTabPosition() ;

                                FicheArticlekParDepotParArticleTask ficheArticlekParDepotParArticleTask = new FicheArticlekParDepotParArticleTask(FicheArticleActivity.this, FicheArticleFragment. rv_list_fiche_article ,  CodeDepotSelected,  FicheArticleActivity. CodeArticleSelected , FicheArticleActivity.date_debut , FicheArticleActivity.date_fin , FicheArticleFragment. progressBar );
                                ficheArticlekParDepotParArticleTask.execute();


                                 FicheArticlePagerAdapter sectionsPagerAdapter = new FicheArticlePagerAdapter(FicheArticleActivity.this  , getSupportFragmentManager() , listDepot,tab_depot.getSelectedTabPosition());
                                 viewPager.setAdapter(sectionsPagerAdapter);
                               // tab_depot.setupWithViewPager(viewPager);
                               // sectionsPagerAdapter.notifyDataSetChanged();


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

                DatePickerDialog datePickerDialog = new DatePickerDialog(FicheArticleActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                                CodeDepotSelected = listDepot.get(tab_depot.getSelectedTabPosition()).getCodeDepot();
                                Log.e("tab_depot_slected" , ""+tab_depot.getSelectedTabPosition());
                                index_tab_selected=tab_depot.getSelectedTabPosition() ;

                                FicheArticlekParDepotParArticleTask ficheArticlekParDepotParArticleTask = new FicheArticlekParDepotParArticleTask(FicheArticleActivity.this, FicheArticleFragment. rv_list_fiche_article,CodeDepotSelected,      FicheArticleActivity. CodeArticleSelected ,FicheArticleActivity.date_debut , FicheArticleActivity.date_fin ,FicheArticleFragment. progressBar   );
                                ficheArticlekParDepotParArticleTask.execute();


                                FicheArticlePagerAdapter   sectionsPagerAdapter = new FicheArticlePagerAdapter(FicheArticleActivity.this  , getSupportFragmentManager() ,FicheArticleActivity.listDepot,tab_depot.getSelectedTabPosition());
                                viewPager.setAdapter(sectionsPagerAdapter);

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