package com.example.suivieadministratif.ui.statistique_rapport_activite.importation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.ListDossierTaskForSearchableSpinner;
import com.example.suivieadministratif.task.ListFournisseurTaskForSearchableSpinner;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class SuivieDossierImportationActivity extends AppCompatActivity {

    public static SearchableSpinner sp_fournisseur;
    public static SearchableSpinner sp_dossier;

    public static String CodeFournisseurSelected = "";
    public static String CodeDossierSelected = "";
    public static  ListView lv_list_suivie_dossier ;

  public  static   int  cloture = 2  ;

    public static ProgressBar pb  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suivie_dossier_importation);

        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Suivie Dossier Importation");

        sp_fournisseur  = (SearchableSpinner) findViewById(R.id.sp_fournisseur);
        sp_dossier      = (SearchableSpinner) findViewById(R.id.sp_dossier);
        lv_list_suivie_dossier = (ListView) findViewById(R.id.lv_list_suivie_dossier);
        pb = (ProgressBar)  findViewById(R.id.pb) ;
        cloture = 2  ;

        final RadioButton rb_tt = (RadioButton)  findViewById(R.id.rb_tout);
        final RadioButton rb_cloure = (RadioButton)  findViewById(R.id.rb_dossier_cloture);
        final RadioButton rb_nn_cloture = (RadioButton)  findViewById(R.id.rb_dossier_non_cloture);


        ListFournisseurTaskForSearchableSpinner listFournisseurTaskForSearchableSpinner = new ListFournisseurTaskForSearchableSpinner(this, sp_fournisseur, "SuivieDossierImportationActivity");
        listFournisseurTaskForSearchableSpinner.execute();


        ListDossierTaskForSearchableSpinner listDossierTaskForSearchableSpinner = new ListDossierTaskForSearchableSpinner(this, sp_dossier, cloture,CodeFournisseurSelected ,"SuivieDossierImportationActivity");
        listDossierTaskForSearchableSpinner.execute();


        rb_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cloture = 2 ;
                ListDossierTaskForSearchableSpinner listDossierTaskForSearchableSpinner = new ListDossierTaskForSearchableSpinner(SuivieDossierImportationActivity.this, sp_dossier, cloture,CodeFournisseurSelected ,"SuivieDossierImportationActivity");
                listDossierTaskForSearchableSpinner.execute();
            }
        });
        rb_cloure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cloture = 1 ;
                ListDossierTaskForSearchableSpinner listDossierTaskForSearchableSpinner = new ListDossierTaskForSearchableSpinner(SuivieDossierImportationActivity.this, sp_dossier, cloture,CodeFournisseurSelected ,"SuivieDossierImportationActivity");
                listDossierTaskForSearchableSpinner.execute();
            }
        });

        rb_nn_cloture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cloture = 0 ;
                ListDossierTaskForSearchableSpinner listDossierTaskForSearchableSpinner = new ListDossierTaskForSearchableSpinner(SuivieDossierImportationActivity.this, sp_dossier, cloture,CodeFournisseurSelected ,"SuivieDossierImportationActivity");
                listDossierTaskForSearchableSpinner.execute();
            }
        });

    }


}