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
import com.example.suivieadministratif.task.ImpayeFournisseurTask;

public class ImpayeFournisseurActivity extends AppCompatActivity {

    public static ProgressBar pb;
    public static ExpandableListView elv_list;

    public static TextView txt_total_impaye   ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impaye_fournisseur);

        //sql session
        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Impayé Fournisseur ");

        elv_list = findViewById(R.id.elv_list);
        pb = (ProgressBar) findViewById(R.id.pb_bc);

        txt_total_impaye  = (TextView) findViewById(R.id.txt_total_impaye);

        String  date_debut   = getIntent().getStringExtra("cle_date_debut") ;
        String  date_fin   = getIntent().getStringExtra("cle_date_fin") ;

        String  code_fournisseur  = getIntent().getStringExtra("cle_code_fournisseur") ;
        String  raison_sociale   = getIntent().getStringExtra("cle_raison_sociale") ;

        String  condition_etat   = getIntent().getStringExtra("cle_condition_etat") ;

        String  mnt_min_max   = getIntent().getStringExtra("cle_mnt_min_max") ;
        double  mnt_min =0 ;
        double  mnt_max =0 ;

        if (mnt_min_max.equals("1"))
        {
               mnt_min   = getIntent().getDoubleExtra("cle_mnt_min" ,0) ;
               mnt_max   = getIntent().getDoubleExtra("cle_mnt_max" ,0) ;
        }


        new ImpayeFournisseurTask(this ,   date_debut,   date_fin,   code_fournisseur,   mnt_min_max,   mnt_min,   mnt_max,   condition_etat,   elv_list,   pb).execute();



    }
}