package com.example.suivieadministratif.module.achat;

import androidx.appcompat.app.AppCompatActivity;
import  com.example.suivieadministratif.R ;
import com.example.suivieadministratif.task.HistoriqueLBLAchatTask;
import com.example.suivieadministratif.task.HistoriqueLBLTask;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class LigneBonLivraisonAchatActivity extends AppCompatActivity {


    TextView txt_num_bl, txt_date_bc, txt_client, txt_ttc;
    ListView lv_ligne_bc;
    ProgressBar pb;
    DecimalFormat decF = new DecimalFormat("0.000");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ligne_bon_livraison_achat);


        String NumeroBL = getIntent().getStringExtra("cle_numero_bon_liv_achat");
        setTitle(NumeroBL);

        String RaisonSociale = getIntent().getStringExtra("cle_raison_sociale");
        double ttc = getIntent().getDoubleExtra("cle_total_ttc", 0);
        String date_cmd = getIntent().getStringExtra("cle_date_bc");

        txt_num_bl = (TextView) findViewById(R.id.txt_num_bl);
        txt_date_bc = (TextView) findViewById(R.id.txt_date_bc);
        txt_client = (TextView) findViewById(R.id.txt_raison_client);
        txt_ttc = (TextView) findViewById(R.id.txt_prix_ttc);

        lv_ligne_bc = (ListView) findViewById(R.id.lv_ligne_bon_commande);
        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.INVISIBLE);

        txt_client.setText(RaisonSociale);
        txt_ttc.setText(decF.format(ttc));
        txt_date_bc.setText(date_cmd);
        txt_num_bl.setText(NumeroBL);

        HistoriqueLBLAchatTask historiqueLBLTask  = new HistoriqueLBLAchatTask (this  ,lv_ligne_bc ,NumeroBL , pb) ;
        historiqueLBLTask.execute();

    }
}