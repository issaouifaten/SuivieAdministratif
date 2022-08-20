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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.module.vente.LigneFactureVente;

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


public class DialogDetailFactureVente extends DialogFragment {


    ConnectionClass connectionClass;
    String   NomUtilisateur ;
    String user, password, base, ip;
    ListView lv_ligne;
    ProgressBar pb ;


    ListView  lv_detail   ;
    ProgressBar  progressBar;
    String NumeroFacture  ;

    public void setNumeroFacture(String numeroFacture) {
        NumeroFacture = numeroFacture;
    }

   TextView txt_num_facture ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_detail_facture_vente, container);

        lv_detail = (ListView) rootView.findViewById(R.id.lv_ligne_piece);
        progressBar = (ProgressBar) rootView.findViewById(R.id.pb);
        progressBar.setVisibility(View.INVISIBLE);
        txt_num_facture = (TextView)  rootView.findViewById(R.id.txt_num_facture);

        txt_num_facture.setText(NumeroFacture);

        SharedPreferences pref = getActivity().getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        connectionClass = new ConnectionClass();
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);

        FillList fillList =new  FillList();
        fillList.execute("");

        return rootView;

    }

    public class FillList extends AsyncTask<String, String, String> {
        String z = "";
        Boolean test = false;

        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);


        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);

            String[] from = {"DesignationArticle", "CodeArticle", "NetHT", "TauxRemise", "MontantTTC", "Quantite", "MontantTTC"};
            int[] views = {R.id.txt_designation,R.id.txt_code_article  , R.id.txt_net_ht, R.id.txt_taux_remise, R.id.txt_mnt_ttc, R.id.txt_quantite, R.id.txt_prix_ttc};
            final SimpleAdapter ADA = new SimpleAdapter(getActivity(),
                    prolist, R.layout.item_ligne_piece, from,
                    views);




            lv_detail.setAdapter(ADA);


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    String queryTable ="";
                    if (NumeroFacture.contains("FV"))
                    {
                          queryTable = " select  CodeArticle,DesignationArticle, convert(numeric(18,0),Quantite)as Quantite , NetHT , TauxRemise  , MontantTTC     from LigneFactureVente where  NumeroFactureVente='"+ NumeroFacture+"' ";

                    }
                    else  if (NumeroFacture.contains("AV"))
                    {
                          queryTable = " select  CodeArticle,DesignationArticle, convert(numeric(18,0),Quantite)as Quantite , NetHT , TauxRemise  , MontantTTC     from LigneAvoirVente where  NumeroAvoirVente = '"+ NumeroFacture+"' ";

                    }
                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("queryDetailFacture", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";

                    while (rs.next()) {

                        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
                        instance.setMinimumFractionDigits(3);
                        instance.setMaximumFractionDigits(3);

                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("CodeArticle", rs.getString("CodeArticle"));
                        datanum.put("DesignationArticle", rs.getString("DesignationArticle"));

                        datanum.put("NetHT", instance.format(rs.getDouble("NetHT")));
                        datanum.put("TauxRemise", rs.getInt("TauxRemise") + " %");
                        datanum.put("MontantTTC", instance.format(rs.getDouble("MontantTTC")));
                        datanum.put("Quantite", rs.getInt("Quantite") + "");

                        prolist.add(datanum);
                        test = true;
                        z = "succees";
                    }
                }
            } catch (SQLException ex) {
                z = "tablelist" + ex.toString();
                Log.e("erreur", z);
            }
            return z;
        }
    }

}
