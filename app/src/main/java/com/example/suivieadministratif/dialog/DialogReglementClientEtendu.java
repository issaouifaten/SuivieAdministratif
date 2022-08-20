package com.example.suivieadministratif.dialog;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.EtatRecouvrementEtenduParClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class DialogReglementClientEtendu extends DialogFragment {


    ConnectionClass connectionClass;
    String user, password, base, ip;

    ProgressBar pb;

    public static ExpandableListView elv_list;



    String CodeClient;
    String RaisonSociale;
    String date_debut  ;
    String date_fin   ;

    public void setCodeClient(String codeClient) {
        CodeClient = codeClient;
    }

    public void setRaisonSociale(String raisonSociale) {
        RaisonSociale = raisonSociale;
    }


    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    TextView txt_raison_client;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_reg_client_etendu, container);

        elv_list = (ExpandableListView) rootView.findViewById(R.id.elv_list);
        pb = (ProgressBar) rootView.findViewById(R.id.pb);
        pb.setVisibility(View.INVISIBLE);


        txt_raison_client = (TextView) rootView.findViewById(R.id.txt_raison_client);
        txt_raison_client.setText(RaisonSociale);

        SharedPreferences pref = getActivity().getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        connectionClass = new ConnectionClass();
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);


      new   EtatRecouvrementEtenduParClient (getActivity() ,date_debut ,date_fin ,elv_list ,pb ,CodeClient) .execute()  ;

        return rootView;

    }



}
