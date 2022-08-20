package com.example.suivieadministratif.ui.statistique_rapport_activite.Vente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.JournalBLArticleVenduTask;
import com.example.suivieadministratif.task.JournalFactureventeTask;

public class JournalFactureVenteActivity extends AppCompatActivity {


    ExpandableListView elv_jav;
    public static TextView txt_total_ttc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_facture_vente2);

        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Journal Facture Vente");


        elv_jav = (ExpandableListView) findViewById(R.id.elv_jav);
        ProgressBar pb = findViewById(R.id.pb_bc);

        txt_total_ttc   = (TextView)   findViewById(R.id.txt_total_ttc) ;

        String _date_debut = getIntent().getStringExtra("cle_date_debut");
        String _date_fin = getIntent().getStringExtra("cle_date_fin");
        String _code_client = getIntent().getStringExtra("cle_code_client");


         new JournalFactureventeTask(this, _date_debut, _date_fin, _code_client, elv_jav, pb).execute();

    }
}