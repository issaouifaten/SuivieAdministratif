package com.example.suivieadministratif.ui.statistique_rapport_activite.Achat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import android.widget.SearchView;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.module.Stock.BonRedressement;
import com.example.suivieadministratif.module.Stock.EtatDeStockActivity;
import com.example.suivieadministratif.module.vente.EtatFactureVente;
import com.example.suivieadministratif.task.ArticleAcheteNonVenduTask;
import com.example.suivieadministratif.task.EtatFactureVenteTask;
import com.example.suivieadministratif.task.ListClientTaskForSearchSpinner;
import com.example.suivieadministratif.task.StockArticleTask;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ArticleAcheteNonVenduActivity extends AppCompatActivity {



    public   static RecyclerView rv_list;



    String user, password, base, ip;

    ConnectionClass connectionClass;

    int id_DatePickerDialog = 0;
    Date currentDate = new Date();
    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;

    public static Date date_debut = null;
    public static Date date_fin = null;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");
    public TextView txt_date_debut, txt_date_fin;


    public   static ProgressBar progressBar;
    public   static TextView  txt_recherche ;
    CardView  btn_recherche   ;

    public static String termRecherche = "";


    public static  TextView  txt_total_ttc , txt_tot_quantite_achat ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_achete_non_vendu);

        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Article acheté non vendu ");
        connectionClass = new ConnectionClass();

        SharedPreferences prefe = getSharedPreferences("usersession", Context.MODE_PRIVATE);

        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);

        txt_total_ttc  = (TextView)  findViewById(R.id.txt_total_ttc)  ;
        txt_tot_quantite_achat= (TextView)  findViewById(R.id.txt_tot_quantite_achat)  ;


        txt_date_debut = findViewById(R.id.txt_date_debut);
        txt_date_fin = findViewById(R.id.txt_date_fin);

        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setHasFixedSize(true);
        rv_list.setLayoutManager(new LinearLayoutManager(this));




        progressBar = (ProgressBar) findViewById(R.id.pb_bc);
        txt_recherche = (TextView)findViewById(R.id.txt_recherche);
        btn_recherche  = (CardView)  findViewById(R.id.btn_rechrche) ;


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


        updateData();



        txt_date_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 0;
                Log.e("month_x1", "On picker  : " + month_x1);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ArticleAcheteNonVenduActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(ArticleAcheteNonVenduActivity.this, new DatePickerDialog.OnDateSetListener() {
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


        final SearchView search_bar_article;
        search_bar_article = (SearchView) findViewById(R.id.search_bar_article);

        Button btn_pourcent = (Button) findViewById(R.id.btn_pourcent);
        btn_pourcent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String last_query = search_bar_article.getQuery().toString();
                String new_query = last_query + "%" ;
                search_bar_article.setQuery(new_query, false);


            }
        });


        search_bar_article.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!search_bar_article.isIconified()) {
                    search_bar_article.setIconified(true);
                }

                txt_recherche.setText("Chercher ici");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txt_recherche.setText("Chercher ici");
                return false;

            }
        });


        btn_recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                termRecherche  = search_bar_article.getQuery().toString();
                new ArticleAcheteNonVenduTask(ArticleAcheteNonVenduActivity.this , date_debut ,date_fin ,rv_list, progressBar ,termRecherche) .execute();
            }
        });


    }


    public void updateData() {

    new ArticleAcheteNonVenduTask(this ,date_debut ,date_fin ,rv_list, progressBar ,"") .execute();

    }

}