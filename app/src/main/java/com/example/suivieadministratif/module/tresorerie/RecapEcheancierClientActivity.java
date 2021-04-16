package com.example.suivieadministratif.module.tresorerie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.ListeRecapEcheanceClientTask;

public class RecapEcheancierClientActivity extends AppCompatActivity {

    ListView lvlist_recap;
    ProgressBar pb;

 public   static    TextView txt_total;
    TextView txt_mode_reg;
    TextView txt_date_debut;
    TextView txt_date_fin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap_echeancier_client);

        String ModeRegSeleted = getIntent().getStringExtra("cle_mode_reglement");
        //sql session
        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Récap. d'engagement " + ModeRegSeleted);

        String CodeModeRegSeleted = getIntent().getStringExtra("cle_code_mode_reglement");
        String date_debut = getIntent().getStringExtra("cle_date_debut");
        String date_fin = getIntent().getStringExtra("cle_date_fin");

        txt_mode_reg =  findViewById(R.id.txt_mode_reg) ;
        txt_date_debut =  findViewById(R.id.txt_date_debut) ;
        txt_date_fin =  findViewById(R.id.txt_date_fin) ;


        txt_mode_reg.setText(ModeRegSeleted);
        txt_date_debut.setText(date_debut);
        txt_date_fin.setText(date_fin);

        lvlist_recap = (ListView) findViewById(R.id.list_recap);
        pb = (ProgressBar) findViewById(R.id.pb);

        txt_total = (TextView) findViewById(R.id.txt_total);

        new ListeRecapEcheanceClientTask(this, date_debut, date_fin, CodeModeRegSeleted, lvlist_recap, pb).execute();

    }
}