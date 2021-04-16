package com.example.suivieadministratif.module.tresorerie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.ListClientTaskForSearchSpinner;
import com.example.suivieadministratif.task.PortfeuilleClientChequeTask;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PortFeuilleChequeActivity extends AppCompatActivity {


    public static ProgressBar pb;
    public static ListView lv_list;


    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");

    public static TextView txt_tot_portfeuille;


    SearchableSpinner sp_client;
    public static String CodeClientSelected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port_feuille_cheque);


        //sql session
        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Portefeuille  Chèque Client ");


        lv_list = findViewById(R.id.lv_list);
        pb = (ProgressBar) findViewById(R.id.pb_bc);
        sp_client = (SearchableSpinner) findViewById(R.id.sp_client);
        txt_tot_portfeuille = (TextView) findViewById(R.id.txt_total_ttc);

        updateData();

        ListClientTaskForSearchSpinner listClientTaskForSearchableSpinner = new ListClientTaskForSearchSpinner(this, sp_client, "PortFeuilleChequeActivity");
        listClientTaskForSearchableSpinner.execute();

    }


    public void updateData() {


        new PortfeuilleClientChequeTask(this, lv_list, pb, CodeClientSelected).execute();

    }

}