package com.example.suivieadministratif.module.Stock;

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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.module.vente.EtatFactureVente;
import com.example.suivieadministratif.task.FicheArticlekParDepotParArticleTask;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FicheArticleActivity extends AppCompatActivity {


    RecyclerView lv_list_fiche_article;
    ProgressBar progressBar;


    public TextView txt_date_debut, txt_date_fin;

    int id_DatePickerDialog = 0;
    Date currentDate = new Date();
    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;

    public static Date date_debut = null;
    public static Date date_fin = null;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");

    String CodeDepot, CodeArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_article2);

        TextView txt_designation_article = (TextView) findViewById(R.id.txt_designation);
        TextView txt_code_article = (TextView) findViewById(R.id.txt_code_article);
        TextView txt_prix_achat_ht = (TextView) findViewById(R.id.txt_prix_achat_ht);
        TextView txt_mnt_tva = (TextView) findViewById(R.id.txt_mnt_tva);
        TextView txt_prix_ttc = (TextView) findViewById(R.id.txt_prix_ttc);
        TextView txt_quantite = (TextView) findViewById(R.id.txt_quantite);


        txt_date_debut = findViewById(R.id.txt_date_debut);
        txt_date_fin = findViewById(R.id.txt_date_fin);

        CodeDepot = getIntent().getStringExtra("CodeDepot");
        String LibelleDepot = getIntent().getStringExtra("LibelleDepot");
        CodeArticle = getIntent().getStringExtra("CodeArticle");
        String DesignArticle = getIntent().getStringExtra("DesignArticle");
        double prix_achat_ht = getIntent().getDoubleExtra("prix_achat_ht", 0);
        double prix_mnt_tva = getIntent().getDoubleExtra("prix_mnt_tva", 0);
        double prix_vente_ttc = getIntent().getDoubleExtra("prix_vente_ttc", 0);
        double prix_montant_ttc = getIntent().getDoubleExtra("prix_montant_ttc", 0);
        int qunatite = getIntent().getIntExtra("qunatite", 0);


        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Fiche Article " + DesignArticle);


        final NumberFormat format = NumberFormat.getNumberInstance(Locale.FRENCH);
        format.setMinimumFractionDigits(3);
        format.setMaximumFractionDigits(3);


        txt_code_article.setText(CodeArticle);
        txt_designation_article.setText(DesignArticle);

        txt_prix_achat_ht.setText(format.format(prix_achat_ht));
        txt_mnt_tva.setText(format.format(prix_mnt_tva));
        txt_prix_ttc.setText(format.format(prix_montant_ttc));
        txt_quantite.setText(qunatite + "");


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


        lv_list_fiche_article = (RecyclerView) findViewById(R.id.lv_list);

        lv_list_fiche_article.setHasFixedSize(true);
        lv_list_fiche_article.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.pb_bc);


        FicheArticlekParDepotParArticleTask ficheArticlekParDepotParArticleTask = new FicheArticlekParDepotParArticleTask(this, lv_list_fiche_article, CodeDepot, CodeArticle, date_debut, date_fin, progressBar);
        ficheArticlekParDepotParArticleTask.execute();


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


                                FicheArticlekParDepotParArticleTask ficheArticlekParDepotParArticleTask = new FicheArticlekParDepotParArticleTask(FicheArticleActivity.this, lv_list_fiche_article, CodeDepot, CodeArticle, date_debut, date_fin, progressBar);
                                ficheArticlekParDepotParArticleTask.execute();


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


                                FicheArticlekParDepotParArticleTask ficheArticlekParDepotParArticleTask = new FicheArticlekParDepotParArticleTask(FicheArticleActivity.this, lv_list_fiche_article, CodeDepot, CodeArticle, date_debut, date_fin, progressBar);
                                ficheArticlekParDepotParArticleTask.execute();


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