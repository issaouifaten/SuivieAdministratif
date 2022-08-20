package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.EcheanceClientAdapterLV;
import com.example.suivieadministratif.adapter.JournalArticleVenteExtensibleAdapter;
import com.example.suivieadministratif.model.Article_BL_JAV;
import com.example.suivieadministratif.model.Bl_Client_JAV;
import com.example.suivieadministratif.model.ClientJAV;
import com.example.suivieadministratif.model.EcheanceClient;
import com.example.suivieadministratif.model.JournalArticleVendu;
import com.example.suivieadministratif.module.vente.RapportEcheanceClientActivity;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.article.EtatJournalArticleVendu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class JournalArticleVenduTask extends AsyncTask<String, String, String> {


    Activity activity;
    ExpandableListView elv_jav;
    ProgressBar pb;
    String CodeClientSelected;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String NomUtilisateur;
    Date date_debut, date_fin;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<JournalArticleVendu> list_jav = new ArrayList<>();

    double tot_journal = 0;

    public JournalArticleVenduTask(Activity activity, Date date_debut, Date date_fin, ExpandableListView elv_jav, ProgressBar pb, String CodeClientSelected) {
        this.activity = activity;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.elv_jav = elv_jav;
        this.pb = pb;
        this.CodeClientSelected = CodeClientSelected;

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


                String  condition  = "" ;
                if (!CodeClientSelected.equals(""))
                {
                    condition += "  and CodeClient =  '"+CodeClientSelected+"' "  ;
                }


                String query_echeance_client = " select * from \n" +
                        "(select Vue_ListeVenteGlobal.CodeArticle, CodeClient,RaisonSociale,MontantTTC ,NumeroPiece ,convert(numeric(18,0),Quantite) as  Quantite ,\n" +
                        "(select  Nom from Respensable where Respensable.CodeRespensable=Vue_ListeVenteGlobal.CodeRespensableAdministration)as RAD\n" +
                        ",(select  Nom from Respensable where Respensable.CodeRespensable=Vue_ListeVenteGlobal.CodeRepresentant)as REPRESENTANT \n" +
                        ",(select  Designation from Article where Article.CodeArticle=Vue_ListeVenteGlobal.CodeArticle)as DesignationArticle  \n" +
                        "from Vue_ListeVenteGlobal where DatePiece between '" + df.format(date_debut) + "' and '" + df.format(date_fin) + "'  "+condition+") as t  \n" +
                        "order by NumeroPiece, CodeClient\n  ";


                Log.e("query_jav", "" + query_echeance_client);
                PreparedStatement ps = con.prepareStatement(query_echeance_client);
                ResultSet rs = ps.executeQuery();

                list_jav.clear();
                while (rs.next()) {

                    String CodeClient = rs.getString("CodeClient");
                    String RaisonSociale = rs.getString("RaisonSociale");

                    String RAD = rs.getString("RAD");
                    String REPRESENTANT = rs.getString("REPRESENTANT");


                    String NumeroPiece = rs.getString("NumeroPiece");

                    String CodeArticle = rs.getString("CodeArticle");
                    String DesignationArticle = rs.getString("DesignationArticle");
                    double MontantTTC = rs.getDouble("MontantTTC");

                    int Quantite = rs.getInt("Quantite");

                    tot_journal += MontantTTC;

                    JournalArticleVendu journalArticleVendu = new JournalArticleVendu(CodeClient, RaisonSociale, RAD, REPRESENTANT, NumeroPiece, CodeArticle, DesignationArticle, MontantTTC, Quantite);
                    list_jav.add(journalArticleVendu);

                    Log.e("J.A.V", journalArticleVendu.toString());

                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
            Log.e("ERROR_msv", "" + ex.getMessage().toString());
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);


        ArrayList<ClientJAV> listClientjav = new ArrayList<>();
        ArrayList<String> client_distinct = new ArrayList<>();
        ArrayList<String> bl_distinct = new ArrayList<>();


        for (JournalArticleVendu jav : list_jav) {
            if (!client_distinct.contains(jav.getCodeClient()))
                client_distinct.add(jav.getCodeClient());


            if (!bl_distinct.contains(jav.getNumeroPiece()))
                bl_distinct.add(jav.getNumeroPiece().trim());
        }

        Log.e("bl_distinct", bl_distinct.toString());

        for (String codeCLient : client_distinct) {

            ClientJAV clientJAV = new ClientJAV();
            ArrayList<Bl_Client_JAV> list_bl_par_client = new ArrayList<>();
            double ttc_par_client = 0;

            for (JournalArticleVendu jav : list_jav) {

                if (codeCLient.equals(jav.getCodeClient())) {

                    Log.e("CLIENT " + " " + jav.getRaisonSociale(), jav.getCodeArticle() + "  " + jav.getMontantTTC());
                    clientJAV = new ClientJAV(jav.getCodeClient(), jav.getRaisonSociale(), jav.getRAD(), jav.getREPRESENTANT());
                    ttc_par_client = ttc_par_client + jav.getMontantTTC();

                }

            }


            clientJAV.setTotalTTC(ttc_par_client);
            clientJAV.setList_bl_par_client(list_bl_par_client);
            listClientjav.add(clientJAV);
            Log.e("CLIENT_jav", clientJAV.getRaisonSociale() + " " + clientJAV.getTotalTTC());


        }


        Bl_Client_JAV bl_client_jav = new Bl_Client_JAV();


        /// génération des bl  distinct
        ArrayList<Bl_Client_JAV> list_bl_distinct = new ArrayList<>();
        for (String bl : bl_distinct) {
            ArrayList<Article_BL_JAV> list_art_par_bl = new ArrayList<>();
            double ttc_par_bl = 0;
            for (JournalArticleVendu jav : list_jav) {
                if (bl.equals(jav.getNumeroPiece())) {
                    Log.e("Client_bl  :" + jav.getRaisonSociale(), "" + jav.getNumeroPiece());
                    bl_client_jav = new Bl_Client_JAV(jav.getCodeClient(), jav.getRaisonSociale(), jav.getNumeroPiece());
                    ttc_par_bl += jav.getMontantTTC();

                    list_art_par_bl.add(new Article_BL_JAV(jav.getNumeroPiece(), jav.getCodeArticle(), jav.getDesignationArticle(), jav.getMontantTTC(), jav.getQuantite()));

                }
            }

            bl_client_jav.setList_art_par_bl(list_art_par_bl);
            bl_client_jav.setMontantTTC(ttc_par_bl);
            list_bl_distinct.add(bl_client_jav);
        }


        ///affectation  bl sur  client
        for (ClientJAV clientJAV : listClientjav) {
            ArrayList<Bl_Client_JAV> listBL_par_client = new ArrayList<>();
            for (Bl_Client_JAV bl_client_jav1 : list_bl_distinct) {
                if (bl_client_jav1.getCodeClient().equals(clientJAV.getCodeClient())) {
                    listBL_par_client.add(bl_client_jav1);
                }
            }
            clientJAV.setList_bl_par_client(listBL_par_client);
        }


        JournalArticleVenteExtensibleAdapter adapter = new JournalArticleVenteExtensibleAdapter(activity, listClientjav);
        elv_jav.setAdapter(adapter);

        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);
        EtatJournalArticleVendu.txt_tot_ttc.setText(instance.format(tot_journal));

    }
}