package com.example.suivieadministratif.module.tresorerie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.DetailRecapEcheanceClientParMoisTask;
import com.example.suivieadministratif.task.DetailRecapEcheanceFournisseurParMoisTask;

public class DetailRecapEcheancierClientParMois extends AppCompatActivity {

    ListView  lv_list   ;
    ProgressBar  pb ;


    public   static    TextView txt_total;
    TextView txt_mode_reg;
    TextView txt_date_debut;
    TextView txt_date_fin;
  ImageView img  ;

    TextView txt_mois ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recap_echeancier_client_par_mois);

        String  annee  = getIntent().getStringExtra("annee") ;
        String  mois   = getIntent().getStringExtra("mois") ;
        String  libelle_mois   = getIntent().getStringExtra("libelle_mois") ;
        String  date_debut   = getIntent().getStringExtra("date_debut") ;
        String  date_fin   = getIntent().getStringExtra("date_fin") ;
        String  mode_reglement   = getIntent().getStringExtra("mode_reglement") ;
        String  libelle_reglement   = getIntent().getStringExtra("libelle_reglement") ;

        img = findViewById(R.id.img) ;

        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete +  " : "+libelle_reglement +" " + libelle_mois +" "+annee);

        txt_mode_reg =  findViewById(R.id.txt_mode_reg) ;
        txt_date_debut =  findViewById(R.id.txt_date_debut) ;
        txt_date_fin =  findViewById(R.id.txt_date_fin) ;
        txt_mois =  findViewById(R.id.txt_mois) ;

        txt_mode_reg.setText("Client "+libelle_reglement);
        img.setImageResource(R.drawable.ic_customer);
        txt_date_debut.setText(date_debut);
        txt_date_fin.setText(date_fin);
        txt_mois.setText(libelle_mois +" "+annee);

        lv_list = (ListView) findViewById(R.id.lv_list);
        pb = (ProgressBar) findViewById(R.id.pb_bc);

        txt_total = (TextView) findViewById(R.id.txt_total);



       new DetailRecapEcheanceClientParMoisTask(this ,annee,mois,date_debut ,date_fin  , mode_reglement , lv_list ,pb ) .execute();


    }
}