package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.JournalArticleVenteExtensibleAdapter;
import com.example.suivieadministratif.adapter.JournalFactureVenteExtensibleAdapter;
import com.example.suivieadministratif.model.Article_BL_JAV;
import com.example.suivieadministratif.model.Bl_Client_JAV;
import com.example.suivieadministratif.model.ClientJAV;
import com.example.suivieadministratif.model.ClientJFV;
import com.example.suivieadministratif.model.FV_client_JFV;
import com.example.suivieadministratif.model.JournalArticleVendu;
import com.example.suivieadministratif.model.JournalFactureVente;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Vente.JournalBLVenteActivity;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Vente.JournalFactureVenteActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class JournalFactureventeTask extends AsyncTask<String, String, String> {


    Activity activity;
    ExpandableListView elv_jav;
    ProgressBar pb;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String NomUtilisateur;
    String  date_debut, date_fin;

    String  CodeClient ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<JournalFactureVente> list_jfv = new ArrayList<>();

    double tot_journal = 0;

    public JournalFactureventeTask(Activity activity, String date_debut, String date_fin , String  CodeClient  , ExpandableListView elv_jav, ProgressBar pb) {
        this.activity = activity;
        this.date_debut = date_debut;
        this.date_fin = date_fin;

        this.CodeClient=CodeClient ;
        this.elv_jav = elv_jav;
        this.pb = pb;

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


                String  CONDITION  = ""  ;

                if (CodeClient.equals(""))
                {
                    CONDITION  += ""  ;
                }
                else {
                    CONDITION  += " and  CodeClient  = '"+CodeClient+"' "  ;
                }


                String query_j_facture_vente = " select NumeroPiece,  DatePiece , CodeClient , RaisonSociale , TotalRemiseg,TotalHT, TotalTVA, \n" +
                        " TimbreFiscal,TotalTTC\n" +
                        " from Vue_ListeVenteJournalier where DatePiece between  '"+date_debut+"' and '"+date_fin+"'  \n" +CONDITION+"\n"+
                        " order by   CodeClient , DatePiece  desc ";


                Log.e("query_jfv", "" + query_j_facture_vente);
                PreparedStatement ps = con.prepareStatement(query_j_facture_vente);
                ResultSet rs = ps.executeQuery();

                list_jfv.clear();
                while (rs.next()) {


                    String NumeroPiece = rs.getString("NumeroPiece");
                    String CodeClient = rs.getString("CodeClient");
                    String RaisonSociale = rs.getString("RaisonSociale");


                    Date DatePiece = dtfSQL.parse(rs.getString("DatePiece"));
                    double TotalRemiseg = rs.getDouble("TotalRemiseg");
                    double TotalHT = rs.getDouble("TotalHT");
                    double TotalTVA = rs.getDouble("TotalTVA");
                    double TimbreFiscal = rs.getDouble("TimbreFiscal");
                    double TotalTTC = rs.getDouble("TotalTTC");

                    tot_journal += TotalTTC;

                    list_jfv.add(new JournalFactureVente(NumeroPiece,CodeClient,RaisonSociale,DatePiece , TotalRemiseg  ,TotalHT,TotalTVA,TimbreFiscal,TotalTTC));


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


        ArrayList<ClientJFV> listClientjfv = new ArrayList<>();
        ArrayList<String> client_distinct = new ArrayList<>();



        for (JournalFactureVente jfv : list_jfv) {
            if (!client_distinct.contains(jfv.getCodeClient()))
                client_distinct.add(jfv.getCodeClient());

        }



        for (String codeCLient : client_distinct) {

            ClientJFV clientJFV= new ClientJFV();
            ArrayList<FV_client_JFV>  list_Fv_client_jfvs  = new ArrayList<>()  ;
            double ttc_par_client = 0;

            for (JournalFactureVente  jfv : list_jfv) {

                if (codeCLient.equals(jfv.getCodeClient())) {

                    Log.e( "CLIENT "+ " "+jfv.getRaisonSociale(),jfv.getNumeroPiece()+"  "+jfv.getTotalTTC()) ;

                    clientJFV = new ClientJFV(jfv.getCodeClient(), jfv.getRaisonSociale() );
                    list_Fv_client_jfvs.add(new FV_client_JFV(jfv.getNumeroPiece(), jfv.getCodeClient(), jfv.getRaisonSociale(), jfv.getDatePiece() ,
                            jfv.getTotalRemiseg(),jfv.getTotalHT(),jfv.getTotalTVA(),jfv.getTimbreFiscal(),jfv.getTotalTTC())) ;
                    ttc_par_client = ttc_par_client+ jfv.getTotalTTC();

                }

            }


            clientJFV.setTotalTTC(ttc_par_client);
            clientJFV.setListe_facture_parèclient(list_Fv_client_jfvs);
            listClientjfv.add(clientJFV);
            Log.e("clientJFV", clientJFV.getRaisonSociale()+  " "+clientJFV.getTotalTTC())  ;


        }



        JournalFactureVenteExtensibleAdapter adapter = new JournalFactureVenteExtensibleAdapter(activity, listClientjfv);
        elv_jav.setAdapter(adapter);




        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);
        JournalFactureVenteActivity.txt_total_ttc.setText(instance.format(tot_journal));

    }
}