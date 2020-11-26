package com.example.suivieadministratif.module.reglementClient;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.DetailLigneReglementClientTask;
import java.text.DecimalFormat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailReglementClientActivity extends AppCompatActivity {

    DecimalFormat formatter = new DecimalFormat("0.000");

    RecyclerView  rv_ligne_reg_client  ;
    RecyclerView  rv_detail_reg_client  ;

    ProgressBar  pb_ligne  , pb_detail ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reglement_client);


        String NumRegClient = getIntent().getStringExtra("cle_num_reg");
        String raison = getIntent().getStringExtra("cle_raison");
        String date_reg = getIntent().getStringExtra("cle_date_reg");
        String etablie_par = getIntent().getStringExtra("cle_etablie_par");
        double montant = getIntent().getDoubleExtra("cle_montant", 0);

        
        setTitle(NumRegClient);

        TextView txt_raison = (TextView) findViewById(R.id.txt_raison_social);
        TextView txt_etablie_par  = (TextView)findViewById(R.id.txt_etablie_par);
        TextView  txt_total_montant  = (TextView)findViewById(R.id.txt_total_montant);
        TextView txt_date_reglement = (TextView)findViewById(R.id.txt_date_depense);
           pb_ligne  = (ProgressBar)   findViewById(R.id.pb_ligne)  ;
           pb_detail  = (ProgressBar)   findViewById(R.id.pb_detail)  ;

           rv_ligne_reg_client   = (RecyclerView)   findViewById(R.id.rv_ligne_reglement)  ;
        rv_ligne_reg_client.setHasFixedSize(true);
        rv_ligne_reg_client.setLayoutManager(new LinearLayoutManager(this));


           rv_detail_reg_client   = (RecyclerView)   findViewById(R.id.rv_detail_reglement);
        rv_detail_reg_client.setHasFixedSize(true);
        rv_detail_reg_client.setLayoutManager(new LinearLayoutManager(this));


        txt_raison.setText(raison);
        txt_date_reglement.setText(date_reg);
        txt_etablie_par.setText(etablie_par);
        txt_total_montant.setText(formatter.format(montant)+" Dt");


        DetailLigneReglementClientTask detailLigneReglementClientTask = new DetailLigneReglementClientTask(this ,NumRegClient, rv_ligne_reg_client , rv_detail_reg_client ,pb_ligne , pb_detail) ;
        detailLigneReglementClientTask.execute() ;


    }
}