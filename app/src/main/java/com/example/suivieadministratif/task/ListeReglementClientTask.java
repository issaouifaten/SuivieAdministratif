package com.example.suivieadministratif.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.adapter.BonLivraisonAdapter;
import com.example.suivieadministratif.adapter.ReglementClientAdapterLV;
import com.example.suivieadministratif.model.BonLivraisonVente;
import com.example.suivieadministratif.model.ReglementClient;
import com.example.suivieadministratif.module.reglementClient.DetailReglementClientActivity;
import com.example.suivieadministratif.module.reglementClient.ReglementClientActivity;
import com.example.suivieadministratif.module.vente.HistoriqueLigneBonLivraisonActivity;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;





public class ListeReglementClientTask extends AsyncTask<String, String, String> {

    String res;
    Activity activity;
    ListView lv_reglement_client ;
    ProgressBar pb_chargement ;
    SearchView search_bar_client ;

    Date  date_debut  , date_fin ;
    ConnectionClass connectionClass ;
    String user, password, base, ip;

    SimpleDateFormat  sdf  = new SimpleDateFormat("dd/MM/yyyy") ;
    DateFormat dtfSQL    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ArrayList <ReglementClient>   listReglementClient = new ArrayList<>() ;

    double total_reglement  =0 ;


    public ListeReglementClientTask(Activity activity  , ListView lv_reglement_client , ProgressBar pb_chargement , Date  date_debut  , Date date_fin ,    SearchView search_bar_client  ) {
        this.activity  = activity  ;
        this.lv_reglement_client = lv_reglement_client  ;
        this.pb_chargement  = pb_chargement   ;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.search_bar_client=search_bar_client ;


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


                String query = " select NumeroReglementClient ,DateReglement  , RaisonSociale,TotalPayement ,NomUtilisateur  , HeureCreation    \n" +
                        " from  ReglementClient\n" +
                        " where DateReglement between  '"+sdf.format(date_debut)+"' and '"+sdf.format(date_fin)+"' \n" ;

                Log.e("query_reg_client", query);

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {


                    String NumeroReglementClient  = rs.getString("NumeroReglementClient");
                    Date DateReglement  =dtfSQL.parse( rs.getString("DateReglement"));
                    Date HeureCreation  =dtfSQL.parse( rs.getString("HeureCreation"));

                    String RaisonSociale  = rs.getString("RaisonSociale");
                    Double TotalPayer  = rs.getDouble("TotalPayement");
                    String NomUtilisateur  = rs.getString("NomUtilisateur");


                    total_reglement  =  total_reglement + TotalPayer ;

                    ReglementClient reglementClient  = new  ReglementClient (NumeroReglementClient ,DateReglement ,HeureCreation ,RaisonSociale ,TotalPayer ,NomUtilisateur ) ;
                    listReglementClient.add(reglementClient) ;


                }
            }
            con.close();

        } catch (Exception ex) {

            res = ex.getMessage();
            Log.e("ERROR_listRDV", res.toString());

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

        ReglementClientAdapterLV adapterLV = new ReglementClientAdapterLV(activity , listReglementClient) ;
        lv_reglement_client.setAdapter(adapterLV);
        ReglementClientActivity.txt_tot_reglement.setText(decF.format(total_reglement) +" Dt");
        listOnClick(listReglementClient);


        search_bar_client.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!search_bar_client.isIconified()) {
                    search_bar_client.setIconified(true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                final ArrayList<ReglementClient> fitlerReglementClient = filterReglementClient(listReglementClient, query);

                ReglementClientAdapterLV adapterLV = new ReglementClientAdapterLV(activity , fitlerReglementClient) ;
                lv_reglement_client.setAdapter(adapterLV);


                listOnClick(fitlerReglementClient);

                return false;

            }
        });






    }


    public void listOnClick(final  ArrayList<ReglementClient> listReglementClient ) {

        lv_reglement_client.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ReglementClient reg_selected  = listReglementClient.get(position) ;
                Intent  intent1 = new Intent(activity , DetailReglementClientActivity.class)  ;
                intent1.putExtra("cle_num_reg",reg_selected.getNumeroReglementClient());

                SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("dd/MM/yyyy") ;
                intent1.putExtra("cle_raison",reg_selected.getRaisonSociale());
                intent1.putExtra("cle_date_reg",simpleDateFormat.format( reg_selected.getDateReglementClient() ));
                intent1.putExtra("cle_etablie_par",reg_selected.getNomUtilisateur());
                intent1.putExtra("cle_montant",reg_selected.getTotalPayement());

                activity.startActivity(intent1);
            }
        });

    }





    private ArrayList<ReglementClient> filterReglementClient(ArrayList<ReglementClient> listClientReg, String term) {

        term = term.toLowerCase();
        final ArrayList<ReglementClient> filetrReglementClient = new ArrayList<>();

        double mnt_reg_client  =0 ;
        for (ReglementClient c : listClientReg) {
            final String txtRaisonSocial = c.getRaisonSociale().toLowerCase();


            if (txtRaisonSocial.contains(term)) {
                filetrReglementClient.add(c);
                mnt_reg_client=mnt_reg_client+ c.getTotalPayement()  ;
            }
        }
        DecimalFormat  decF  = new DecimalFormat("0.000") ;
        ReglementClientActivity.txt_tot_reglement.setText(decF.format(mnt_reg_client) +" Dt");
        return filetrReglementClient;

    }


}
