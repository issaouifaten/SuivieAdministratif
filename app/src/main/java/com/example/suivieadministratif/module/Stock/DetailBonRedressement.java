package com.example.suivieadministratif.module.Stock;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.LigneBonRedressementTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailBonRedressement extends AppCompatActivity {
    String NumeroBonRedressement="";
    ConnectionClass connectionClass;
    String CodeSociete, NomUtilisateur, CodeLivreur;
    final Context co = this;
    String user, password, base, ip;


    ListView lv_ligne;

    ProgressBar  pb  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bon_redressement);
        Intent intent=getIntent();

        //"NumeroBonRedressement", "DateCreation",   "Depot","TotalTTC","Etat"
        NumeroBonRedressement=intent.getStringExtra("NumeroBonRedressement");
        String    DateCreation=intent.getStringExtra("DateBonRedressement");
        String    Depot=intent.getStringExtra("Depot");
        String    TotalTTC=intent.getStringExtra("TotalTTC");
        String    Etat=intent.getStringExtra("Etat");




        TextView txt_num_bc =(TextView)findViewById(R.id.txt_num_bc);
        TextView txt_prix_ttc =(TextView)findViewById(R.id.txt_prix_ttc);
        TextView txt_raison_client =(TextView)findViewById(R.id.txt_raison_client);
        TextView txt_date_bc =(TextView)findViewById(R.id.txt_date_bc);
        txt_num_bc.setText(NumeroBonRedressement);
        txt_prix_ttc.setText(TotalTTC);
        txt_raison_client.setText(Depot);
        txt_date_bc.setText(DateCreation);



        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Détail Redressement");


        lv_ligne=(ListView)findViewById(R.id.lv_ligne);

        pb = (ProgressBar)   findViewById(R.id.pb) ;

        LigneBonRedressementTask  ligneBonRedressementTask = new LigneBonRedressementTask(this  , lv_ligne ,NumeroBonRedressement ,pb) ;
        ligneBonRedressementTask.execute()  ;




    }



}
