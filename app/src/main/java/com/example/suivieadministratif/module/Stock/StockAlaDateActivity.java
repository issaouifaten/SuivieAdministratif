package com.example.suivieadministratif.module.Stock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.module.vente.EtatFactureVente;
import com.example.suivieadministratif.task.StockAlaDateTask;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StockAlaDateActivity extends AppCompatActivity {


    int id_DatePickerDialog = 0;
    Date currentDate = new Date();
    public static int year_x1, month_x1, day_x1;


    public static Date date_debut = null;

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");

    public TextView txt_date_debut;

    String CodeDepot, CodeArticle;


    TextView txt_code_article, txt_designation, txt_stock_reel, txt_stock_a_la_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_ala_date);


        CodeDepot = getIntent().getStringExtra("CodeDepot");
        String LibelleDepot = getIntent().getStringExtra("LibelleDepot");
        CodeArticle = getIntent().getStringExtra("CodeArticle");
        String DesignArticle = getIntent().getStringExtra("DesignArticle");
        double prix_achat_ht = getIntent().getDoubleExtra("prix_achat_ht", 0);
        double prix_mnt_tva = getIntent().getDoubleExtra("prix_mnt_tva", 0);
        double prix_vente_ttc = getIntent().getDoubleExtra("prix_vente_ttc", 0);
        double prix_montant_ttc = getIntent().getDoubleExtra("prix_montant_ttc", 0);
        int qunatite = getIntent().getIntExtra("qunatite", 0);


        txt_date_debut = findViewById(R.id.txt_date_debut);
        txt_stock_a_la_date = findViewById(R.id.txt_stock_a_la_date);
        txt_stock_reel = findViewById(R.id.txt_stock_reel);
        txt_designation = findViewById(R.id.txt_designation);
        txt_code_article = findViewById(R.id.txt_code_article);


        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        //cal1.add(Calendar.MONTH, -1);
        year_x1 = cal1.get(Calendar.YEAR);
        month_x1 = cal1.get(Calendar.MONTH);
        day_x1 = cal1.get(Calendar.DAY_OF_MONTH);


        String _date_du = df.format(cal1.getTime());

        try {
            date_debut = df.parse(_date_du);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txt_date_debut.setText(_date_du);

        txt_stock_a_la_date.setText("---");
        txt_stock_reel.setText(qunatite + "");
        txt_designation.setText(DesignArticle);
        txt_code_article.setText(CodeArticle);


        new StockAlaDateTask(this, date_debut, CodeDepot, CodeArticle, txt_stock_a_la_date).execute();


        txt_date_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 0;
                Log.e("month_x1", "On picker  : " + month_x1);
                DatePickerDialog datePickerDialog = new DatePickerDialog(StockAlaDateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        if (id_DatePickerDialog == 0) {
                            year_x1 = year;
                            month_x1 = monthOfYear;
                            day_x1 = dayOfMonth;

                            txt_date_debut.setText("" + formatter.format(day_x1) + "/" + formatter.format(month_x1 + 1) + "/" + year_x1);

                            String _date_du = formatter.format(day_x1) + "/" + formatter.format(month_x1 + 1) + "/" + year_x1 + " ";



                            try {
                                date_debut = df.parse(_date_du);

                                new StockAlaDateTask(StockAlaDateActivity.this, date_debut, CodeDepot, CodeArticle, txt_stock_a_la_date).execute();

                            } catch (Exception e) {
                                Log.e("Exception--", " " + e.getMessage());
                            }
                        }
                    }
                }, year_x1, month_x1, day_x1);
                datePickerDialog.show();
            }
        });



    }


}