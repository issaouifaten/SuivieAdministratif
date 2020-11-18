package com.example.suivieadministratif.module.achat;

import androidx.appcompat.app.AppCompatActivity;
import  com.example.suivieadministratif.R ;
import com.example.suivieadministratif.task.HistoriqueLBRAchatTask;
import com.example.suivieadministratif.task.HistoriqueLBRTask;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class LigneBonRetourAchatActivity extends AppCompatActivity {


    TextView txt_num_br, txt_date_bc, txt_client, txt_ttc;
    ListView lv_ligne_br;
    ProgressBar pb;
    DecimalFormat decF = new DecimalFormat("0.000");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ligne_bon_retour_achat);




        String NumeroBR = getIntent().getStringExtra("cle_numero_bon_ret_achat");
        setTitle(NumeroBR);

        String RaisonSociale = getIntent().getStringExtra("cle_raison_sociale");
        double ttc = getIntent().getDoubleExtra("cle_total_ttc", 0);
        String date_cmd = getIntent().getStringExtra("cle_date_bc");

        txt_num_br = (TextView) findViewById(R.id.txt_num_br);
        txt_date_bc = (TextView) findViewById(R.id.txt_date_bc);
        txt_client = (TextView) findViewById(R.id.txt_raison_client);
        txt_ttc = (TextView) findViewById(R.id.txt_prix_ttc);

        lv_ligne_br = (ListView) findViewById(R.id.lv_ligne_bon_retour);
        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.INVISIBLE);

        txt_client.setText(RaisonSociale);
        txt_ttc.setText(decF.format(ttc));
        txt_date_bc.setText(date_cmd);
        txt_num_br.setText(NumeroBR);

        HistoriqueLBRAchatTask historiqueLBRTask  = new HistoriqueLBRAchatTask (this  ,lv_ligne_br ,NumeroBR , pb) ;
        historiqueLBRTask.execute();



    }
}