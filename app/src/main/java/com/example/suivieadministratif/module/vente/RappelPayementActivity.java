package com.example.suivieadministratif.module.vente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.RappelPayementTask;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RappelPayementActivity extends AppCompatActivity {


    RecyclerView rv_list_rappel ;
    ProgressBar progressBar;


    String  CodeClient ;
    String  TypeRappel ;

   public  static   TextView  txt_total ;
    TextView    txt_raison_social ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rappel_payement);


        CodeClient = getIntent().getStringExtra("CodeClient") ;
        String  RaisonSociale = getIntent().getStringExtra("RaisonSociale") ;
        TypeRappel= getIntent().getStringExtra("TypeRappel") ;

        txt_raison_social = (TextView)  findViewById(R.id.txt_raison_social)  ;
        txt_raison_social.setText("  " +RaisonSociale);

        //sql session
        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Rappel. "+TypeRappel +" : "+RaisonSociale);


        txt_total = (TextView)  findViewById(R.id.txt_total) ;
        rv_list_rappel = (RecyclerView) findViewById(R.id.lv_list);

        rv_list_rappel.setHasFixedSize(true);
        rv_list_rappel.setLayoutManager(new LinearLayoutManager(this));

        progressBar = (ProgressBar) findViewById(R.id.pb_bc);


        // task  here

       new RappelPayementTask(this  ,rv_list_rappel ,CodeClient,TypeRappel, progressBar).execute() ;

    }
}