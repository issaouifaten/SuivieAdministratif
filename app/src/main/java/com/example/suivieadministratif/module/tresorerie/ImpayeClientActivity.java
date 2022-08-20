package com.example.suivieadministratif.module.tresorerie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.ImpayeClientTask;

public class ImpayeClientActivity extends AppCompatActivity {


    public static ProgressBar pb;
    public static ExpandableListView elv_list;

    public static TextView txt_total_impaye, txt_reste_a_payer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impaye_client);

        //sql session
        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Impayé Client ");

        elv_list        = findViewById(R.id.elv_list);
        pb              = (ProgressBar) findViewById(R.id.pb_bc);

        txt_total_impaye = (TextView) findViewById(R.id.txt_total_impaye);
        txt_reste_a_payer = (TextView) findViewById(R.id.txt_reste_a_payer);

        String date_debut = getIntent().getStringExtra("cle_date_debut");
        String date_fin = getIntent().getStringExtra("cle_date_fin");

        String code_client = getIntent().getStringExtra("cle_code_client");
        String raison_sociale = getIntent().getStringExtra("cle_raison_sociale");

        String code_banque = getIntent().getStringExtra("cle_code_banque");
        String banque = getIntent().getStringExtra("cle_banque");

        String mode_reglement = getIntent().getStringExtra("cle_code_mode_reglement");
        String libelle_reglement = getIntent().getStringExtra("cle_mode_reglement");

        String condition_etat = getIntent().getStringExtra("cle_condition_etat");

        new ImpayeClientTask(this, date_debut, date_fin, code_client, code_banque, mode_reglement, condition_etat, elv_list, pb).execute();


    }

}