package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.ReglementClientAdapterLV;
import com.example.suivieadministratif.adapter.ReglementFournisseurAdapterLV;
import com.example.suivieadministratif.model.ReglementClient;
import com.example.suivieadministratif.model.ReglementFournisseur;
import com.example.suivieadministratif.module.reglementClient.DetailReglementClientActivity;
import com.example.suivieadministratif.module.reglementClient.ReglementClientActivity;
import com.example.suivieadministratif.module.reglementFournisseur.DetailReglementFournisseurActivity;
import com.example.suivieadministratif.module.reglementFournisseur.ReglementFournisseurActivity;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ListeReglementFournisseurTask extends AsyncTask<String, String, String> {

    String res;
    Activity activity;
    ListView lv_reglement_fournisseur ;
    ProgressBar pb_chargement ;
    SearchView search_bar_frns  ;

    Date  date_debut  , date_fin ;
    ConnectionClass connectionClass ;
    String user, password, base, ip;

    SimpleDateFormat  sdf  = new SimpleDateFormat("dd/MM/yyyy") ;
    DateFormat dtfSQL    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ArrayList <ReglementFournisseur>   listReglementFournisseur = new ArrayList<>() ;

    double total_reglement  =0 ;


    public ListeReglementFournisseurTask(Activity activity  , ListView lv_reglement_fournisseur , ProgressBar pb_chargement , Date  date_debut  , Date date_fin,SearchView search_bar_frns   ) {
        this.activity  = activity  ;
        this.lv_reglement_fournisseur = lv_reglement_fournisseur  ;
        this.pb_chargement  = pb_chargement   ;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.search_bar_frns=search_bar_frns ;


        SharedPreferences pref = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip   = pref.getString("ip", ip);
        base = pref.getString("base", base);
        password = pref.getString("password", password);

        connectionClass = new ConnectionClass();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
         pb_chargement.setVisibility(View.VISIBLE);
        total_reglement  =0 ;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);       // Connect to database
            Log.e("con", "" + con);
            if (con == null) {
                res = "Check Your Internet Access!";
            } else {


                String query = "select NumeroReglementFournisseur ,DateReglement  , RaisonSociale,TotalPayement ,NomUtilisateur  \n" +
                        "from  ReglementFournisseur\n" +
                        " where DateReglement between  '"+sdf.format(date_debut)+"' and '"+sdf.format(date_fin)+"' \n" ;

                Log.e("query_reg_frns", query);

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {


                    String NumeroReglementFournisseur  = rs.getString("NumeroReglementFournisseur");
                    Date DateReglement  =dtfSQL.parse( rs.getString("DateReglement"));
                    String RaisonSociale  = rs.getString("RaisonSociale");
                    Double TotalPayer  = rs.getDouble("TotalPayement");
                    String NomUtilisateur  = rs.getString("NomUtilisateur");


                    total_reglement  =  total_reglement + TotalPayer ;

                    ReglementFournisseur reglementFournisseur  = new  ReglementFournisseur (NumeroReglementFournisseur ,DateReglement ,RaisonSociale ,TotalPayer ,NomUtilisateur ) ;
                    listReglementFournisseur.add(reglementFournisseur) ;


                }
            }
            con.close();

        } catch (Exception ex) {

            res = ex.getMessage();
            Log.e("ERROR_frns", res.toString());

        }
        return null;

    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        pb_chargement.setVisibility(View.INVISIBLE);

        DecimalFormat  decF  = new DecimalFormat("0.000") ;

        ReglementFournisseurAdapterLV adapterLV = new ReglementFournisseurAdapterLV(activity , listReglementFournisseur) ;
        lv_reglement_fournisseur.setAdapter(adapterLV);
        listOnClick(listReglementFournisseur);


        search_bar_frns.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!search_bar_frns.isIconified()) {
                    search_bar_frns.setIconified(true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                final ArrayList<ReglementFournisseur> filterReglementFournisseur = filterReglementFournisseur(listReglementFournisseur, query);

                ReglementFournisseurAdapterLV adapterLV = new ReglementFournisseurAdapterLV(activity , filterReglementFournisseur) ;
                lv_reglement_fournisseur.setAdapter(adapterLV);

                listOnClick(filterReglementFournisseur);

                return false;

            }
        });



        ReglementFournisseurActivity.txt_tot_reglement.setText(decF.format(total_reglement) +" Dt");




    }


    public void listOnClick(final  ArrayList<ReglementFournisseur> listReglementFournisseur ) {

        lv_reglement_fournisseur.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ReglementFournisseur reg_selected  = listReglementFournisseur.get(position) ;
                Intent intent1 = new Intent(activity , DetailReglementFournisseurActivity.class)  ;
                intent1.putExtra("cle_num_reg",reg_selected.getNumeroReglementFournisseur());

                SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("dd/MM/yyyy") ;

                intent1.putExtra("cle_raison",reg_selected.getRaisonSociale());
                intent1.putExtra("cle_date_reg",simpleDateFormat.format( reg_selected.getDateReglementFournisseur() ));
                intent1.putExtra("cle_etablie_par",reg_selected.getNomUtilisateur());
                intent1.putExtra("cle_montant",reg_selected.getTotalPayement());

                activity.startActivity(intent1);
            }
        });

    }



    private ArrayList<ReglementFournisseur> filterReglementFournisseur(ArrayList<ReglementFournisseur> listFrnsReg, String term) {

        term = term.toLowerCase();
        final ArrayList<ReglementFournisseur> filetrReglementFournisseur = new ArrayList<>();

        double mnt_reg_fournisseur  =0 ;
        for (ReglementFournisseur f : listFrnsReg) {
            final String txtRaisonSocial = f.getRaisonSociale().toLowerCase();


            if (txtRaisonSocial.contains(term)) {
                filetrReglementFournisseur.add(f);
                mnt_reg_fournisseur=mnt_reg_fournisseur+ f.getTotalPayement()  ;
            }
        }
        DecimalFormat  decF  = new DecimalFormat("0.000") ;
        ReglementFournisseurActivity.txt_tot_reglement.setText(decF.format(mnt_reg_fournisseur) +" Dt");
        return filetrReglementFournisseur;

    }


}
