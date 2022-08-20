package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.SpinnerAdapter;
import com.example.suivieadministratif.menu.MenuTresorerieFragment;
import com.example.suivieadministratif.model.Client;
import com.example.suivieadministratif.module.tresorerie.PortFeuilleChequeActivity;
import com.example.suivieadministratif.module.tresorerie.PortFeuilleTraiteActvity;
import com.example.suivieadministratif.module.vente.EtatCommande;
import com.example.suivieadministratif.module.vente.EtatDevisVente;
import com.example.suivieadministratif.module.vente.EtatFactureVente;
import com.example.suivieadministratif.module.vente.EtatLivraisonActivity;
import com.example.suivieadministratif.module.vente.EtatRetourActivity;
import com.example.suivieadministratif.module.vente.RapportEcheanceClientActivity;
import com.example.suivieadministratif.module.vente.ReglementClientActivity;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.PieceNonPayeClient;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatVenteFragment;
import com.example.suivieadministratif.ui.statistique_rapport_activite.article.EtatJournalArticleVendu;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListClientTaskForSearchSpinner extends AsyncTask<String,String,String> {

    Connection con;
    String res ;

    Activity activity  ;
    SearchableSpinner sp_client ;

    String origine  ;

    ArrayList<String> listRaison = new ArrayList<>() ;
    ArrayList<Client> listClient = new ArrayList<Client>() ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    public ListClientTaskForSearchSpinner(Activity activity , SearchableSpinner sp_client   , String origine) {
        this.activity = activity  ;
        this.sp_client = sp_client  ;

        this.origine=origine ;


        SharedPreferences pref = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip   = pref.getString("ip", ip);
        base = pref.getString("base", base);
        password = pref.getString("password", password);

        connectionClass = new ConnectionClass();



    }


    //  lancement  de progress dialog
    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }




    //  donwnload  data
    @Override
    protected String doInBackground(String... strings) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);       // Connect to database
            Log.e("con", "" + con);
            if (con == null) {
                res = "Check Your Internet Access!";
            } else {

                String  CONDITION  = "" ;


                String query = "select  CodeClient    from  Client   where 1 =1 " + CONDITION ;

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query) ;


                listRaison.clear();

                if (origine.equals("dialogChoixJournalBLVente")  ||  origine.equals("PieceNonPayeClient")  ||  origine.equals("EtatDevisVente")
                ||     origine.equals("EtatCommande")   ||   origine.equals("EtatLivraisonActivity")   ||  origine.equals("EtatRetourActivity")
                ||   origine.equals("EtatFactureVente")  ||   origine.equals("ReglementClientActivity")   ||   origine.equals("RapportEcheanceClientActivity")
                ||   origine.equals("PortFeuilleChequeActivity")  ||    origine.equals("PortFeuilleTraiteActvity")   ||  origine.equals("EtatJournalArticleVendu")  ||  origine.equals("MenuTresorerieFragment")   )

                {
                    listClient.add(new Client("" ,"Tout les Clients")) ;
                    listRaison.add("Tout les Clients")  ;
                }

                while ( rs.next() ) {

                    String CodeClient = rs.getString("CodeClient") ;
                    String RaisonSociale =rs.getString("RaisonSociale") ;

                    Client   client  = new Client(CodeClient ,RaisonSociale) ;
                    listClient.add(client) ;
                    listRaison.add(client.getRaisonSociale())  ;
                    Log.e("Client", client.getCodeClient() + " - " +client.getRaisonSociale() );

                }
            }

            con.close();

        } catch (Exception ex) {

            res = ex.getMessage();
            Log.e("ERROR", res) ;

        }

        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        SpinnerAdapter adapter = new SpinnerAdapter(activity  , listRaison)  ;
        sp_client.setAdapter(adapter);
        sp_client.setSelection(0);

        if (origine .equals("dialogChoixJournalBLVente")) {
            StatVenteFragment.CodeClient_selected = listClient.get(0).getCodeClient();
            StatVenteFragment.RaisonClient_selected = listClient.get(0).getRaisonSociale();
        }

        sp_client.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("Client_selected"  ,""+listClient.get(position).toString())  ;

           if (origine .equals("dialogChoixJournalBLVente"))
                {
                    StatVenteFragment.CodeClient_selected = listClient.get(position).getCodeClient() ;
                    StatVenteFragment.RaisonClient_selected  = listClient.get(position).getRaisonSociale() ;

                }

                if (origine .equals("PieceNonPayeClient"))
                {
                    PieceNonPayeClient.CodeClientSelected = listClient.get(position).getCodeClient() ;

                    PieceNonPayeClientTask  pieceNonPayeClientTask  = new PieceNonPayeClientTask(activity  ,PieceNonPayeClient.date_debut ,PieceNonPayeClient.date_fin , PieceNonPayeClient.lv_list_historique_bc  ,PieceNonPayeClient.progressBar ,PieceNonPayeClient.CodeClientSelected)  ;
                    pieceNonPayeClientTask.execute() ;

                }


                if (origine .equals("EtatDevisVente"))
                {
                    EtatDevisVente.CodeClientSelected = listClient.get(position).getCodeClient() ;

                    EtatDevisTask etatDevisTask = new EtatDevisTask(activity  ,EtatDevisVente.date_debut ,EtatDevisVente.date_fin, EtatDevisVente.lv_list_historique_bc, EtatDevisVente.progressBar, EtatDevisVente.CodeClientSelected);
                    etatDevisTask.execute();

                }


                if (origine .equals("EtatCommande"))
                {
                    EtatCommande.CodeClientSelected = listClient.get(position).getCodeClient() ;

                    HistoriqueBCTask historiqueBCTask = new HistoriqueBCTask(activity  ,EtatCommande.date_debut ,EtatCommande.date_fin, EtatCommande.lv_list_historique_bc, EtatCommande.pb_bc, EtatCommande.CodeClientSelected);
                    historiqueBCTask.execute();

                }
                if (origine .equals("EtatLivraisonActivity"))
                {
                    EtatLivraisonActivity.CodeClientSelected = listClient.get(position).getCodeClient() ;

                    HistoriqueBLTask  historiqueBLTask = new HistoriqueBLTask(activity  ,EtatLivraisonActivity.date_debut ,EtatLivraisonActivity.date_fin, EtatLivraisonActivity.lv_list_historique_bl, EtatLivraisonActivity.pb_bc, EtatLivraisonActivity.CodeClientSelected);
                    historiqueBLTask.execute();

                }

                if (origine .equals("EtatRetourActivity"))
                {
                    EtatRetourActivity.CodeClientSelected = listClient.get(position).getCodeClient() ;

                    HistoriqueBRTask historiqueBRTask = new HistoriqueBRTask(activity,EtatRetourActivity. date_debut, EtatRetourActivity.date_fin, EtatRetourActivity.lv_list_historique_br,EtatRetourActivity.pb_bc,EtatRetourActivity. CodeClientSelected);
                    historiqueBRTask.execute();

                }

                if (origine .equals("EtatFactureVente"))
                {
                    EtatFactureVente.CodeClientSelected = listClient.get(position).getCodeClient() ;

                    EtatFactureVenteTask etatFactureVenteTask = new EtatFactureVenteTask(activity,EtatFactureVente. date_debut, EtatFactureVente.date_fin, EtatFactureVente.lv_list_historique_bc,EtatFactureVente.progressBar ,EtatFactureVente. CodeClientSelected);
                    etatFactureVenteTask.execute();

                }
                if (origine .equals("ReglementClientActivity"))
                {
                    ReglementClientActivity.CodeClientSelected = listClient.get(position).getCodeClient() ;

                    ListeReglementClientTask listeReglementClientTask = new ListeReglementClientTask(activity, ReglementClientActivity.lv_list_reg_client,ReglementClientActivity. pb, ReglementClientActivity.date_debut, ReglementClientActivity.date_fin, ReglementClientActivity.CodeClientSelected);
                    listeReglementClientTask.execute();

                }

                if (origine .equals("RapportEcheanceClientActivity"))
                {
                    RapportEcheanceClientActivity.CodeClientSelected = listClient.get(position).getCodeClient() ;

                    EcheanceClientTask echeanceClientTask = new EcheanceClientTask(activity, RapportEcheanceClientActivity.date_debut, RapportEcheanceClientActivity.date_fin, RapportEcheanceClientActivity.lv_list_echeance_client, RapportEcheanceClientActivity.pb, RapportEcheanceClientActivity.CodeClientSelected);
                    echeanceClientTask.execute();

                }



                if (origine .equals("PortFeuilleChequeActivity"))
                {
                    PortFeuilleChequeActivity.CodeClientSelected = listClient.get(position).getCodeClient() ;

                    new PortfeuilleClientChequeTask(activity , PortFeuilleChequeActivity.lv_list , PortFeuilleChequeActivity.pb , PortFeuilleChequeActivity.CodeClientSelected) .execute() ;

                }

                if (origine .equals("PortFeuilleTraiteActvity"))
                {
                    PortFeuilleTraiteActvity.CodeClientSelected = listClient.get(position).getCodeClient() ;

                    new PortfeuilleClientTraiteTask(activity, PortFeuilleTraiteActvity.lv_list, PortFeuilleTraiteActvity.pb, PortFeuilleTraiteActvity.CodeClientSelected).execute();
                }



                if (origine .equals("EtatJournalArticleVendu"))
                {
                    EtatJournalArticleVendu.CodeClientSelected = listClient.get(position).getCodeClient() ;

                    new JournalArticleVenduTask(activity ,   EtatJournalArticleVendu.date_debut ,   EtatJournalArticleVendu.date_fin,   EtatJournalArticleVendu.elv_jav,   EtatJournalArticleVendu.pb_bc,EtatJournalArticleVendu.CodeClientSelected  ).execute() ;
                }

                if (origine .equals("MenuTresorerieFragment"))
                {
                    MenuTresorerieFragment.CodeClientSelected = listClient.get(position).getCodeClient() ;
                    MenuTresorerieFragment.ClientSelected = listClient.get(position).getRaisonSociale() ;


                }



//


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
        );

    }


}
