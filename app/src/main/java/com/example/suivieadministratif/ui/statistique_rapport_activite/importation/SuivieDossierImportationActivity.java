package com.example.suivieadministratif.ui.statistique_rapport_activite.importation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.ListFournisseurTaskForSearchableSpinner;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class SuivieDossierImportationActivity extends AppCompatActivity {

    public static SearchableSpinner sp_fournisseur;
    public static SearchableSpinner sp_dossier;

    public static String CodeFournisseurSelected = "";
    public static String CodeDossierSelected = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suivie_dossier_importation);

        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Suivie Dossier Importation");

        sp_fournisseur  = (SearchableSpinner) findViewById(R.id.sp_fournisseur);
        sp_dossier      = (SearchableSpinner) findViewById(R.id.sp_dossier);


        ListFournisseurTaskForSearchableSpinner listFournisseurTaskForSearchableSpinner = new ListFournisseurTaskForSearchableSpinner(this, sp_fournisseur, "SuivieDossierImportationActivity");
        listFournisseurTaskForSearchableSpinner.execute();


    }


}