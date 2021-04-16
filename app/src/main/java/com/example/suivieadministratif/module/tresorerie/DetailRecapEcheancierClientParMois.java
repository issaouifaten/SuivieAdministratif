package com.example.suivieadministratif.module.tresorerie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.suivieadministratif.R;

public class DetailRecapEcheancierClientParMois extends AppCompatActivity {

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




    }
}