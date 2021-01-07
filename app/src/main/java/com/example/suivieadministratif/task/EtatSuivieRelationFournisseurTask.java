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
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.adapter.BonCommandeAdapter;
import com.example.suivieadministratif.adapter.SuivieFournisseurExpandableListAdapter;
import com.example.suivieadministratif.model.BonCommandeVente;
import com.example.suivieadministratif.model.EnteteRelationFournisseur;
import com.example.suivieadministratif.model.RelationFournisseur;
import com.example.suivieadministratif.module.vente.EtatCommande;
import com.example.suivieadministratif.module.vente.HistoriqueLigneBonCommandeActivity;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EtatSuivieRelationFournisseurTask extends AsyncTask<String, String, String> {


    Activity activity;

    ExpandableListView elv_list_suivie_fournisseur ;

    Date date_debut, date_fin;
    String CodeFournisseur, CodeContact, CodeRepresentant, CodeMoyenRelation, CodeNature;
    ProgressBar pb;
    SearchView search_bar;

    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;


    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    ArrayList<EnteteRelationFournisseur> listEnteteRelationFournisseur = new ArrayList<>();


    public EtatSuivieRelationFournisseurTask(Activity activity, ExpandableListView elv_list_suivie_fournisseur, Date date_debut, Date date_fin, String codeFournisseur, String codeContact, String codeRepresentant, String codeMoyenRelation, String codeNature, ProgressBar pb, SearchView search_bar) {
        this.activity = activity;
        this.elv_list_suivie_fournisseur = elv_list_suivie_fournisseur;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        CodeFournisseur = codeFournisseur;
        CodeContact = codeContact;
        CodeRepresentant = codeRepresentant;
        CodeMoyenRelation = codeMoyenRelation;
        CodeNature = codeNature;
        this.pb = pb;
        this.search_bar = search_bar;


        SharedPreferences prefe = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);


        Log.e("BON_CMD", Param.PEF_SERVER + "-" + ip + "-" + base);
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


                String CONDITION = "";


                if (CodeFournisseur.equals("")) {
                    CONDITION = CONDITION + "";
                } else {
                    CONDITION = CONDITION + "\n  and  CodeFournisseur = '" + CodeFournisseur + "' ";
                }


                if (CodeContact.equals("")) {
                    CONDITION = CONDITION + "";
                } else {
                    CONDITION = CONDITION + "\n  and   CodeContact = '" + CodeContact + "' ";
                }


                if (CodeRepresentant.equals("")) {
                    CONDITION = CONDITION + "";
                } else {
                    CONDITION = CONDITION + "\n  and   CodeRespensable = '" + CodeRepresentant + "'";
                }


                if (CodeMoyenRelation.equals("")) {
                    CONDITION = CONDITION + "";
                } else {
                    CONDITION = CONDITION + "\n  and Moyen =  '" + CodeMoyenRelation + "'";
                }


                if (CodeNature.equals("")) {
                    CONDITION = CONDITION + "";
                } else {
                    CONDITION = CONDITION + "\n  and CodeNatureRelation =  '" + CodeNature + "'";
                }


                String query_entete_suivie_relation = " select  CodeFournisseur  , RaisonSociale  from  Vue_ListeRelationFournisseur \n" +
                        "where " +
                        "1= 1   " +
                        CONDITION +
                        "\nand DatePiece  between '" + df.format(date_debut) + "' and  '" + df.format(date_fin) + "' \n" +
                        "group   by   CodeFournisseur  , RaisonSociale  \n ";


                Log.e("query_entete", "" + query_entete_suivie_relation);
                PreparedStatement ps = con.prepareStatement(query_entete_suivie_relation);
                ResultSet rs = ps.executeQuery();


                while (rs.next()) {

                    String CodeFournisseur = rs.getString("CodeFournisseur");
                    String RaisonSociale = rs.getString("RaisonSociale");

                    EnteteRelationFournisseur enteteRelationFournisseur = new EnteteRelationFournisseur(CodeFournisseur, RaisonSociale);


                    String query_Suivie_rel_fournisseur = " select  DatePiece , NumeroRelation , Contact , Nom  , Moyen ,Etat  , Nature  , Object ,objet    from  Vue_ListeRelationFournisseur \n" +
                            "where  CodeFournisseur = '" + CodeFournisseur + "'\n" +
                            CONDITION+
                            "and  DatePiece  between '" + df.format(date_debut) + "'  and  '" + df.format(date_fin) + "' ";



                    Log.e("query_srm" , query_Suivie_rel_fournisseur)  ;
                    PreparedStatement ps_ligne = con.prepareStatement(query_Suivie_rel_fournisseur);
                    ResultSet rs_ligne = ps_ligne.executeQuery();


                    ArrayList<RelationFournisseur> listRelationFournisseur = new ArrayList<>();
                    while (rs_ligne.next()) {


                        Date datePiece = dtfSQL.parse(rs_ligne.getString("DatePiece"));
                        String NumeroRelation = rs_ligne.getString("NumeroRelation");
                        String Contact = rs_ligne.getString("Contact");
                        String Nom = rs_ligne.getString("Nom");
                        String Moyen = rs_ligne.getString("Moyen");
                        String Etat = rs_ligne.getString("Etat");
                        String Nature = rs_ligne.getString("Nature");
                        String Object = rs_ligne.getString("Object");
                        String objet = rs_ligne.getString("objet");


                        RelationFournisseur relationFournisseur = new RelationFournisseur(datePiece, NumeroRelation, Contact, Nom, Moyen, Nature, Etat, Object, objet);
                        listRelationFournisseur.add(relationFournisseur);

                    }
                    enteteRelationFournisseur.setListRelationFournisseur(listRelationFournisseur);
                    Log.e("rel_frns" , enteteRelationFournisseur.toString()) ;

                    listEnteteRelationFournisseur.add(enteteRelationFournisseur);

                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
            Log.e("ERROR_rel_frns" , ex.getMessage().toString()) ;
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);

        // adapter  here


        SuivieFournisseurExpandableListAdapter  adapter  = new SuivieFournisseurExpandableListAdapter(activity ,listEnteteRelationFournisseur) ;
        elv_list_suivie_fournisseur.setAdapter(adapter);




        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!search_bar.isIconified()) {
                    search_bar.setIconified(true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                final ArrayList<EnteteRelationFournisseur> fitlerEnteteRelationFournisseur = filterEnteteRelationFournisseur(listEnteteRelationFournisseur, query);

                SuivieFournisseurExpandableListAdapter  adapter  = new SuivieFournisseurExpandableListAdapter(activity ,fitlerEnteteRelationFournisseur) ;
                elv_list_suivie_fournisseur.setAdapter(adapter);


                return false;

            }
        });



    }


    private ArrayList<EnteteRelationFournisseur> filterEnteteRelationFournisseur  (ArrayList<EnteteRelationFournisseur> listEnteteRelFrns, String term) {

        term = term.toLowerCase();
        final ArrayList<EnteteRelationFournisseur> filetrEnteteRelationFournisseur= new ArrayList<>();

        for (EnteteRelationFournisseur c : listEnteteRelFrns) {
            final String txtRaisonSocial = c.getRaisonSocialFournisseur().toLowerCase();

            if (txtRaisonSocial.contains(term)) {
                filetrEnteteRelationFournisseur.add(c);


            }
        }


        return filetrEnteteRelationFournisseur;

    }
}