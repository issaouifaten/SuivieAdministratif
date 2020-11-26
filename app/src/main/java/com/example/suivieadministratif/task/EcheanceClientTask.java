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
import com.example.suivieadministratif.adapter.EcheanceClientAdapterLV;
import com.example.suivieadministratif.model.EcheanceClient;
import com.example.suivieadministratif.module.reglementClient.RapportEcheanceClientActivity;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EcheanceClientTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv_echeance_client;
    ProgressBar pb;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String NomUtilisateur;
    Date  date_debut , date_fin  ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<EcheanceClient> listEcheanceClient = new ArrayList<>();

    double   tot_echeance =0  ;

    public EcheanceClientTask(Activity activity, Date  date_debut , Date date_fin  , ListView lv_echeance_client, ProgressBar pb ) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.lv_echeance_client = lv_echeance_client;
        this.pb = pb;
       // this.search_bar_client = search_bar_client;


        SharedPreferences prefe = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);


        Log.e("BON_CMD" ,Param.PEF_SERVER +"-"+ip+"-"+base) ;

        /*SharedPreferences pref=activity.getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt=pref.edit();
        NomUtilisateur= pref.getString("NomUtilisateur",NomUtilisateur);*/

        connectionClass = new ConnectionClass();

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);
            if (con == null) {
                z = "Error in connection with SQL server";
            } else {

                String query_echeance_client = " select CodeClient  , RaisonSociale , Libelle   ,Porteur  ,NumeroReglementClient  ,Reference  , MontantRecu , Echeance , NomUtilisateurActuel  \n" +
                        "from     Vue_EcheancierClient  \n" +
                        "where Echeance between  '"+df.format(date_debut)+"' and '"+df.format(date_fin)+"'   ";


                Log.e("query_echeance_client",""+query_echeance_client) ;
                PreparedStatement ps = con.prepareStatement(query_echeance_client);
                ResultSet rs = ps.executeQuery();


                while (rs.next()) {

                    String CodeClient = rs.getString("CodeClient");
                    String RaisonSociale = rs.getString("RaisonSociale");
                    String Libelle = rs.getString("Libelle");
                    String Porteur = rs.getString("Porteur");
                    String NumeroReglementClient = rs.getString("NumeroReglementClient");
                    String Reference = rs.getString("Reference");
                    double MontantRecu = rs.getDouble("MontantRecu");
                    Date Echeance = dtfSQL.parse(rs.getString("Echeance"));
                    String NomUtilisateurActuel = rs.getString("NomUtilisateurActuel");

                    EcheanceClient  echeanceClient  = new EcheanceClient(CodeClient  ,RaisonSociale ,Libelle ,Porteur ,NumeroReglementClient ,Reference ,MontantRecu ,Echeance ,NomUtilisateurActuel) ;
                    listEcheanceClient.add(echeanceClient) ;

                    tot_echeance =tot_echeance+MontantRecu ;

                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
            Log.e("ERROR_msv" ,""+ex.getMessage().toString()) ;
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);

        DecimalFormat   decF  = new DecimalFormat("0.000") ;
        RapportEcheanceClientActivity.txt_tot_echeance.setText(decF.format(tot_echeance) +" DT");
        EcheanceClientAdapterLV   adapterLV =  new EcheanceClientAdapterLV(activity  , listEcheanceClient) ;
        lv_echeance_client.setAdapter(adapterLV);

    /*    MvmentVenteServiceAdapterLV mvmentVenteServiceAdapterLV  = new MvmentVenteServiceAdapterLV(activity, listMouvementServiceVente);
        lv_mvmnt_vente_service.setAdapter(mvmentVenteServiceAdapterLV);*/
        //listOnClick(listBonCommandeVente);

     /*   search_bar_client.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!search_bar_client.isIconified()) {
                    search_bar_client.setIconified(true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                final ArrayList<BonCommandeVente> fitlerClientList = filterClientCMD(listBonCommandeVente, query);

                EtatCommande.bcAdapter = new BonCommandeAdapter(activity, fitlerClientList);
                lv_hist_bc.setAdapter(EtatCommande.bcAdapter);
                listOnClick(fitlerClientList);

                return false;

            }
        });*/

    }

   /* public void listOnClick(final  ArrayList<BonCommandeVente>  listBC) {

        lv_hist_bc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                NumberFormat formatter = new DecimalFormat("0.000");

                final BonCommandeVente bonCommandeVente = listBC.get(position);

                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setIcon(R.drawable.i2s);
                alert.setTitle("Bon De Commande");
                alert.setMessage("Client : " + bonCommandeVente.getReferenceClient());


                alert.setNegativeButton("Détail",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface di, int i) {
                                //di.cancel();

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                Intent toLigneBonCommande = new Intent(activity, HistoriqueLigneBonCommandeActivity.class);
                                toLigneBonCommande.putExtra("cle_numero_bon_cmd_vente", bonCommandeVente.getNumeroBonCommandeVente());
                                toLigneBonCommande.putExtra("cle_raison_sociale", bonCommandeVente.getReferenceClient());
                                toLigneBonCommande.putExtra("cle_total_ttc", bonCommandeVente.getTotalTTC());
                                toLigneBonCommande.putExtra("cle_date_bc", sdf.format(bonCommandeVente.getDateBonCommandeVente()));
                                activity.startActivity(toLigneBonCommande);

                            }
                        });


                if (bonCommandeVente.getNumeroEtat().equals("E00")) {

                    alert.setNeutralButton("Annulé", null);

                } else {

                    AlertDialog dd = alert.create();

                    dd.show();

                }
            }
        });

    }

    private ArrayList<BonCommandeVente> filterClientCMD(ArrayList<BonCommandeVente> listClientCMD, String term) {

        term = term.toLowerCase();
        final ArrayList<BonCommandeVente> filetrListClient = new ArrayList<>();

        for (BonCommandeVente c : listClientCMD) {
            final String txtRaisonSocial = c.getReferenceClient().toLowerCase();

            if (txtRaisonSocial.contains(term)) {
                filetrListClient.add(c);
            }
        }
        return filetrListClient;

    }*/

}