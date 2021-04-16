package com.example.suivieadministratif.module.achat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suivieadministratif.R ;
import com.example.suivieadministratif.task.DetailLigneReglementClientTask;
import com.example.suivieadministratif.task.DetailLigneReglementFournisseurTask;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class DetailReglementFournisseurActivity extends AppCompatActivity {

    DecimalFormat formatter = new DecimalFormat("0.000");

    RecyclerView rv_ligne_reg_frns   ;
    RecyclerView rv_detail_reg_frns ;
    ProgressBar  pb_ligne  , pb_detail  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reglement_fournisseur);

        String NumRegFrns = getIntent().getStringExtra("cle_num_reg");
        String raison = getIntent().getStringExtra("cle_raison");
        String date_reg = getIntent().getStringExtra("cle_date_reg");
        String etablie_par = getIntent().getStringExtra("cle_etablie_par");
        double montant = getIntent().getDoubleExtra("cle_montant", 0);

        setTitle(NumRegFrns);

        TextView txt_raison = (TextView) findViewById(R.id.txt_raison_social);
        TextView txt_etablie_par  = (TextView)findViewById(R.id.txt_etablie_par);
        TextView  txt_total_montant  = (TextView)findViewById(R.id.txt_total_montant);
        TextView txt_date_reglement = (TextView)findViewById(R.id.txt_date_depense);
        pb_ligne  = (ProgressBar)   findViewById(R.id.pb_ligne)  ;
        pb_detail  = (ProgressBar)   findViewById(R.id.pb_detail)  ;


        rv_ligne_reg_frns   = (RecyclerView)   findViewById(R.id.rv_ligne_reglement)  ;
        rv_ligne_reg_frns.setHasFixedSize(true);
        rv_ligne_reg_frns.setLayoutManager(new LinearLayoutManager(this));


        rv_detail_reg_frns   = (RecyclerView)   findViewById(R.id.rv_detail_reglement);
        rv_detail_reg_frns.setHasFixedSize(true);
        rv_detail_reg_frns.setLayoutManager(new LinearLayoutManager(this));

        txt_raison.setText(raison);
        txt_date_reglement.setText(date_reg);
        txt_etablie_par.setText(etablie_par);
        txt_total_montant.setText(formatter.format(montant)+" Dt");

        DetailLigneReglementFournisseurTask detailLigneReglementFournisseurTask =  new DetailLigneReglementFournisseurTask(this ,NumRegFrns, rv_ligne_reg_frns , rv_detail_reg_frns ,pb_ligne , pb_detail) ;
        detailLigneReglementFournisseurTask.execute() ;


    }


}