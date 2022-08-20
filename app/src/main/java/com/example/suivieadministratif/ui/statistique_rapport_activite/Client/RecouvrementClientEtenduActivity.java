package com.example.suivieadministratif.ui.statistique_rapport_activite.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.EtatClientPourRecouvrementEtendu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecouvrementClientEtenduActivity extends AppCompatActivity {

    public static TextView txt_total_ttc;
    ListView lv_list_client;

    SimpleDateFormat  sdf  = new SimpleDateFormat("dd/MM/yyyy") ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recouvrement_client_etendu);


        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Recouvrement Client étendu ");


        lv_list_client = (ListView) findViewById(R.id.lv_list_client);
        ProgressBar pb = findViewById(R.id.pb_bc);

        txt_total_ttc = (TextView) findViewById(R.id.txt_total_ttc);

        String _date_debut = getIntent().getStringExtra("cle_date_debut");
        String _date_fin = getIntent().getStringExtra("cle_date_fin");
        String _code_client = getIntent().getStringExtra("cle_code_client");


        try {

            Date dateDebut  =  sdf.parse(_date_debut) ;
            Date dateFin  =  sdf.parse(_date_fin) ;

            new EtatClientPourRecouvrementEtendu(this, dateDebut, dateFin, lv_list_client, pb, _code_client).execute();

        } catch (ParseException e) {
            e.printStackTrace();
        }




    }
}