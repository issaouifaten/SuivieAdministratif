package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.SpinnerAdapter;
import com.example.suivieadministratif.menu.MenuTresorerieFragment;
import com.example.suivieadministratif.model.Depot;
import com.example.suivieadministratif.model.Fournisseur;
import com.example.suivieadministratif.module.achat.BonCommandeAchatActivity;
import com.example.suivieadministratif.module.achat.BonLivraisonAchatActivity;
import com.example.suivieadministratif.module.achat.BonRetourAchatActivity;
import com.example.suivieadministratif.module.achat.FactureAchat;
import com.example.suivieadministratif.module.achat.RapportEcheanceFournisseurActivity;
import com.example.suivieadministratif.module.achat.ReglementFournisseurActivity;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.CommandeFournisseurNonConforme;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.PieceNonPayeFrs;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.SuivieCommandeFrs;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatArticleFragment;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatSRMFragment;
import com.example.suivieadministratif.ui.statistique_rapport_activite.importation.SuivieDossierImportationActivity;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListFournisseurTaskForSearchableSpinner extends AsyncTask<String,String,String> {

    Connection con;
    String res ;

    Activity activity  ;
    SearchableSpinner sp_fournisseur ;
    String origine  ;

    ArrayList<String> listRaison = new ArrayList<>() ;
    ArrayList<Fournisseur> listFournisseur  = new ArrayList<Fournisseur>() ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    public ListFournisseurTaskForSearchableSpinner(Activity activity , SearchableSpinner sp_fournisseur , String origine) {
        this.activity = activity  ;
        this.sp_fournisseur = sp_fournisseur  ;
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
       // txt_client.setHint("Patientez SVP ...");

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

                String  CONDIION= "  " ;

                if (origine .equals("SuivieDossierImportationActivity")) {

                    CONDIION = CONDIION+"  and  inactif=0  and Etrange = 1 " ;

                }

                String query = "  select  CodeFournisseur  , RaisonSociale  from  Fournisseur \n   where 1 =1  "+CONDIION ;

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query) ;


                listRaison.clear();


                    listFournisseur.add(new Fournisseur("" ,"Tout les fournisseurs")) ;
                    listRaison.add("Tout les fournisseurs")  ;


                while ( rs.next() ) {

                    String CodeFournisseur = rs.getString("CodeFournisseur") ;
                    String RaisonSociale =rs.getString("RaisonSociale") ;

                    Fournisseur  fournisseur  = new Fournisseur(CodeFournisseur ,RaisonSociale) ;
                    listFournisseur.add(fournisseur) ;
                    listRaison.add(fournisseur.getRaisonSocial())  ;
                    Log.e("Fournisseur ", fournisseur.getCodeFournisseur() + " - " +fournisseur.getRaisonSocial()  );

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
        sp_fournisseur.setAdapter(adapter);
        sp_fournisseur.setSelection(0);

        sp_fournisseur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("Depot_selected"  ,""+listFournisseur.get(position).toString())  ;

                if (origine .equals("CommandeFournisseurNonConforme"))
                {
                    CommandeFournisseurNonConforme.CodeFournisseurSelected = listFournisseur.get(position).getCodeFournisseur() ;

                    CommandeFrnsNonConformeTask commandeFrnsNonConformeTask = new CommandeFrnsNonConformeTask(activity ,  CommandeFournisseurNonConforme.rv_list_cmd_frns_nn_conforme , CommandeFournisseurNonConforme.pb , CommandeFournisseurNonConforme.date_debut, CommandeFournisseurNonConforme.date_fin , CommandeFournisseurNonConforme.CodeFournisseurSelected , CommandeFournisseurNonConforme. CodeRespAdmin,origine) ;
                    commandeFrnsNonConformeTask.execute() ;

                }


                else  if (origine .equals("SuivieDossierImportationActivity"))
                {
                    SuivieDossierImportationActivity.CodeFournisseurSelected = listFournisseur.get(position).getCodeFournisseur() ;


                    ListDossierTaskForSearchableSpinner listDossierTaskForSearchableSpinner = new ListDossierTaskForSearchableSpinner(activity,   SuivieDossierImportationActivity.sp_dossier,   SuivieDossierImportationActivity.cloture,  SuivieDossierImportationActivity.CodeFournisseurSelected ,"SuivieDossierImportationActivity");
                    listDossierTaskForSearchableSpinner.execute();


                }


                else  if(origine.equals("dialogSuivieRelationFournisseur"))
                {

                    StatSRMFragment.CodeFrns_selected =  listFournisseur.get(position).getCodeFournisseur() ;

                    //  contact   fournisseur
                    ListContactTaskForSearchableSpinner   listContactTaskForSearchableSpinner = new ListContactTaskForSearchableSpinner(activity  , StatSRMFragment.sp_contact, StatSRMFragment.CodeFrns_selected  ,"dialogSuivieRelationFournisseur");
                    listContactTaskForSearchableSpinner.execute() ;


                }

                else  if(origine.equals("PieceNonPayeFrs"))
                {
                    PieceNonPayeFrs.CodeFournisseurSelected =  listFournisseur.get(position).getCodeFournisseur() ;

                    PieceNonPayeFournisseurTask pieceNonPayeFournisseurTask = new PieceNonPayeFournisseurTask(activity , PieceNonPayeFrs.date_debut ,PieceNonPayeFrs.date_fin ,PieceNonPayeFrs.lv_list_historique_bc ,PieceNonPayeFrs.progressBar ,PieceNonPayeFrs.CodeFournisseurSelected)  ;
                    pieceNonPayeFournisseurTask.execute() ;
                }




                          else  if(origine.equals("FactureAchat"))
                {
                    FactureAchat.CodeFournisseurSelected =  listFournisseur.get(position).getCodeFournisseur() ;

                    EtatFactureAchatTask  etatFactureAchatTask  = new EtatFactureAchatTask(activity ,  FactureAchat.date_debut  , FactureAchat.date_fin  ,  FactureAchat.lv_list_historique_bc , FactureAchat.progressBar , FactureAchat.CodeFournisseurSelected) ;
                    etatFactureAchatTask.execute() ;

                }

                else  if(origine.equals("BonCommandeAchatActivity"))
                {
                    BonCommandeAchatActivity.CodeFournisseurSelected =  listFournisseur.get(position).getCodeFournisseur() ;

                    HistoriqueBCAchatTask historiqueBCTask = new HistoriqueBCAchatTask(activity,BonCommandeAchatActivity. date_debut, BonCommandeAchatActivity.date_fin, BonCommandeAchatActivity.lv_list_historique_bc, BonCommandeAchatActivity.pb_bc, BonCommandeAchatActivity.CodeFournisseurSelected);
                    historiqueBCTask.execute();
                }



                else  if(origine.equals("BonLivraisonAchatActivity"))
                {
                    BonLivraisonAchatActivity.CodeFournisseurSelected =  listFournisseur.get(position).getCodeFournisseur() ;

                    HistoriqueBLAchatTask historiqueBLTask  = new HistoriqueBLAchatTask(activity ,BonLivraisonAchatActivity.date_debut ,BonLivraisonAchatActivity.date_fin,BonLivraisonAchatActivity.lv_list_historique_bl  ,BonLivraisonAchatActivity. pb_bc,BonLivraisonAchatActivity.CodeFournisseurSelected) ;
                    historiqueBLTask.execute() ;
                }


                else  if(origine.equals("BonRetourAchatActivity"))
                {
                    BonRetourAchatActivity.CodeFournisseurSelected =  listFournisseur.get(position).getCodeFournisseur() ;

                    HistoriqueBRAchatTask historiqueBRTask  = new HistoriqueBRAchatTask(activity ,BonRetourAchatActivity.date_debut ,BonRetourAchatActivity.date_fin,BonRetourAchatActivity.lv_list_historique_br  , BonRetourAchatActivity.pb_bc,BonRetourAchatActivity.CodeFournisseurSelected) ;
                    historiqueBRTask.execute() ;

                }



                else  if(origine.equals("ReglementFournisseurActivity"))
                {
                    ReglementFournisseurActivity.CodeFournisseurSelected =  listFournisseur.get(position).getCodeFournisseur() ;

                    ListeReglementFournisseurTask listeReglementFournisseurTask  = new ListeReglementFournisseurTask(activity ,ReglementFournisseurActivity.lv_list_reg_fournisseur ,ReglementFournisseurActivity. pb ,ReglementFournisseurActivity.date_debut ,ReglementFournisseurActivity.date_fin ,  ReglementFournisseurActivity.CodeFournisseurSelected ) ;
                    listeReglementFournisseurTask.execute() ;

                }


                else  if(origine.equals("RapportEcheanceFournisseurActivity"))
                {
                    RapportEcheanceFournisseurActivity.CodeFournisseurSelected =  listFournisseur.get(position).getCodeFournisseur() ;

                    EcheanceFournisseurTask echeanceFournisseurTask  = new EcheanceFournisseurTask(activity  , RapportEcheanceFournisseurActivity.date_debut , RapportEcheanceFournisseurActivity.date_fin ,  RapportEcheanceFournisseurActivity.lv_list_echeance_fournisseur ,  RapportEcheanceFournisseurActivity.pb , RapportEcheanceFournisseurActivity.CodeFournisseurSelected ) ;
                    echeanceFournisseurTask.execute() ;

                }

                else  if(origine.equals("MenuTresorerieFragment"))
                {
                    MenuTresorerieFragment.CodeFournissseurSelected =  listFournisseur.get(position).getCodeFournisseur() ;
                    MenuTresorerieFragment.FournisseurSelected =  listFournisseur.get(position).getRaisonSocial() ;
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
        );

    }


}
