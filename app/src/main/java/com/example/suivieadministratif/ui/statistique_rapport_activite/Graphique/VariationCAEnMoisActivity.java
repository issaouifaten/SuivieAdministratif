package com.example.suivieadministratif.ui.statistique_rapport_activite.Graphique;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.task.ListAnneeForSpinner;
import com.github.mikephil.charting.charts.BarChart;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VariationCAEnMoisActivity extends AppCompatActivity {






    public Spinner sp_annee_debut, sp_mois_debut;
    public Spinner sp_annee_fin, sp_mois_fin;

    Date currentDate = new Date();
    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;

    public static Date date_debut = null;
    public static Date date_fin = null;

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");
    public static TextView txt_tot_ht;


    public  static   String  annee_debut_selected ;
    public  static   String  mois_debut_selected ;

    public  static   String  annee_fin_selected ;
    public  static   String  mois_fin_selected ;


  public static   BarChart  barGraph  ;
    public static ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variation_c_a_en_mois);


        String param = getIntent().getStringExtra("param")  ;

       /*commercial
        comptable*/

        SharedPreferences pref = getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");


        setTitle(NomSociete + " : Variation CA "+param+" en Mois");



        barGraph = (BarChart)   findViewById(R.id.barGraph)  ;
        pb= (ProgressBar) findViewById(R.id.pb) ;

        sp_annee_debut = (Spinner)  findViewById(R.id.sp_annee_debut) ;
        sp_mois_debut= (Spinner)  findViewById(R.id.sp_mois_debut) ;

        sp_annee_fin = (Spinner)  findViewById(R.id.sp_annee_fin) ;
        sp_mois_fin= (Spinner)  findViewById(R.id.sp_mois_fin) ;

        ListAnneeForSpinner  listAnneeForSpinner   = new ListAnneeForSpinner(this  ,sp_annee_debut  ,sp_mois_debut , param ,"Debut") ;
        listAnneeForSpinner.execute() ;

        ListAnneeForSpinner  listAnneeFinForSpinner   = new ListAnneeForSpinner(this  ,sp_annee_fin  ,sp_mois_fin , param ,"Fin") ;
        listAnneeFinForSpinner.execute() ;




    }
}