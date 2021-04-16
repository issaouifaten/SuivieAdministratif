package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.RetenuClientAdapter;
import com.example.suivieadministratif.model.BonCommandeVente;
import com.example.suivieadministratif.model.RetenuClientFournisseur;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.ListRetenuClientActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ListeRetenuClientTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv;
    ProgressBar pb;
    SearchView search_bar_client;

    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String NomUtilisateur;
    Date  date_debut , date_fin  ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<RetenuClientFournisseur> listRetenuClient= new ArrayList<>();

    double total_retenu = 0;
    double total_brut = 0;
    double total_net = 0;

    public ListeRetenuClientTask(Activity activity, Date  date_debut , Date date_fin  , ListView lv, ProgressBar pb, SearchView search_bar_client) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.lv = lv;
        this.pb = pb;
        this.search_bar_client = search_bar_client;


        SharedPreferences prefe = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);


        Log.e("RETENU" ,Param.PEF_SERVER +"-"+ip+"-"+base) ;


        connectionClass = new ConnectionClass();

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb.setVisibility(View.VISIBLE);

          total_retenu = 0;
          total_brut = 0;
          total_net = 0;


        ListRetenuClientActivity.  txt_tot_retenu.setText( "---.---");
        ListRetenuClientActivity.  txt_tot_brut.setText( "---.---");
        ListRetenuClientActivity.  txt_tot_net.setText( "---.---");
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);
            if (con == null) {
                z = "Error in connection with SQL server";
            } else {

                String queryHis_bc = "  SELECT RetenuFactureVente.DateRetenuVente, LigneRetenuFactureVente.TauxRetenu ,\n" +
                        "   Client.CodeClient, LigneRetenuFactureVente.CodeRetenu, RetenuFactureVente.RaisonSociale, \n" +
                        "  SUM( LigneRetenuFactureVente.MontantRetenu) as Retenu,SUM( LigneRetenuFactureVente.MontantTTC)as Brut,\n" +
                        "  SUM( LigneRetenuFactureVente.MontantTTC)- SUM( LigneRetenuFactureVente.MontantRetenu) as NET \n" +
                        "  FROM   (LigneRetenuFactureVente LigneRetenuFactureVente  \n" +
                        " INNER JOIN RetenuFactureVente RetenuFactureVente ON \n" +
                        "  LigneRetenuFactureVente.NumeroRetenu=RetenuFactureVente.NumeroRetenu) \n" +
                        "    INNER JOIN Client Client ON RetenuFactureVente.CodeClient=Client.CodeClient \n" +
                        " WHERE  (RetenuFactureVente.DateRetenuVente>='"+df.format(date_debut)+"'  \n" +
                        " AND RetenuFactureVente.DateRetenuVente< '"+df.format(date_fin)+"') \n" +
                        "  group by Client.CodeClient, RetenuFactureVente.RaisonSociale,LigneRetenuFactureVente.TauxRetenu, RetenuFactureVente.DateRetenuVente,LigneRetenuFactureVente.CodeRetenu \n" +
                        "  ORDER BY   RetenuFactureVente.DateRetenuVente   desc ";


                Log.e("query_retenu",""+queryHis_bc) ;
                PreparedStatement ps = con.prepareStatement(queryHis_bc);
                ResultSet rs = ps.executeQuery();

                total_retenu = 0;
                total_brut = 0;
                total_net = 0;

                while (rs.next()) {

                    Date   DateRetenuVente = dtfSQL.parse(rs.getString("DateRetenuVente"));

                    int CodeRetenu = rs.getInt("CodeRetenu");
                    double TauxRetenu = rs.getDouble("TauxRetenu");

                    String CodeClient = rs.getString("CodeClient");
                    String RaisonSociale = rs.getString("RaisonSociale");



                    double Retenu = rs.getDouble("Retenu");
                    double Brut = rs.getDouble("Brut");
                    double NET = rs.getDouble("NET");


                    total_retenu += Retenu;
                    total_brut += Brut;
                    total_net += NET;

                    RetenuClientFournisseur retenuClient  = new RetenuClientFournisseur( DateRetenuVente ,CodeRetenu , TauxRetenu  , CodeClient , RaisonSociale  ,Retenu ,Brut  ,NET  ) ;
                    listRetenuClient.add(retenuClient) ;



                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);


        RetenuClientAdapter   retenuClientAdapter = new RetenuClientAdapter(activity    , listRetenuClient)  ;
        lv.setAdapter(retenuClientAdapter);



        DecimalFormat  decF  = new DecimalFormat("0.000") ;
      //  EtatCommande.txt_tot_commande.setText(decF.format(total_bc)+" Dt");
        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);



        ListRetenuClientActivity.  txt_tot_retenu.setText(instance.format(total_retenu)+"");
        ListRetenuClientActivity.  txt_tot_brut.setText(instance.format(total_brut)+"");
        ListRetenuClientActivity.  txt_tot_net.setText(instance.format(total_net)+"");


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


                final ArrayList<RetenuClientFournisseur> listeRetenuFiltre = filterRetenu(listRetenuClient, query);

                RetenuClientAdapter   retenuClientAdapter = new RetenuClientAdapter(activity  , listeRetenuFiltre)  ;
                lv.setAdapter(retenuClientAdapter);
                return false;

            }
        });

    }



    private ArrayList<RetenuClientFournisseur> filterRetenu(ArrayList<RetenuClientFournisseur> listeRetenu, String term) {

        term = term.toLowerCase();
        final ArrayList<RetenuClientFournisseur> filetrListRetenu= new ArrayList<>();

        for (RetenuClientFournisseur c : listeRetenu) {
            final String txtRaisonSocial = c.getRaisonSociale().toLowerCase();

            if (txtRaisonSocial.contains(term)) {
                filetrListRetenu.add(c);
            }
        }

        return filetrListRetenu;

    }

}