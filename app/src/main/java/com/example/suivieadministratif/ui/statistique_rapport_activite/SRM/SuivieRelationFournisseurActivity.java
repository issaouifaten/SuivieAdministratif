package com.example.suivieadministratif.ui.statistique_rapport_activite.SRM;

import androidx.appcompat.app.AppCompatActivity;
import  com.example.suivieadministratif.R ;
import com.example.suivieadministratif.task.EtatSuivieRelationFournisseurTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

public class SuivieRelationFournisseurActivity extends AppCompatActivity {


    SimpleDateFormat   sdf  =  new SimpleDateFormat("dd/MM/yyyy") ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suivie_relation_fournisseur);

        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Suivie Relation Fournisseur");


        SearchView search_bar = (SearchView)  findViewById(R.id.search_bar) ;


        ExpandableListView elv_list_suivie_fournisseur = findViewById(R.id.elv_list_suivie_fournisseur);


        ProgressBar pb  = (ProgressBar)   findViewById(R.id.pb) ;


        String  _date_debut = getIntent().getStringExtra("cle_date_debut");
        String  _date_fin = getIntent().getStringExtra("cle_date_fin");


        Date  dateDebut = null  ;
        Date  dateFin = null  ;

        try {
               dateDebut  =  sdf.parse(_date_debut);
               dateFin   = sdf.parse(_date_fin)  ;

        } catch (ParseException e) {
            e.printStackTrace();
        }



        String code_frns  = getIntent().getStringExtra("cle_code_frns");
        String code_contact  = getIntent().getStringExtra("cle_code_contact");
        String code_responsable  = getIntent().getStringExtra("cle_code_responsable");
        String code_moyen_relation  = getIntent().getStringExtra("cle_code_moyen_relation");
        String code_nature_relation  = getIntent().getStringExtra("cle_code_nature_relation");


        EtatSuivieRelationFournisseurTask  etatSuivieRelationFournisseurTask = new EtatSuivieRelationFournisseurTask(this , elv_list_suivie_fournisseur,dateDebut,dateFin , code_frns , code_contact ,code_responsable , code_moyen_relation ,code_nature_relation, pb , search_bar) ;
        etatSuivieRelationFournisseurTask.execute() ;

    }

}