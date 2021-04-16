package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.BonAchatSuiviCMDRVAdapter;
import com.example.suivieadministratif.model.LigneCMDFrnsNonConforme;
import com.example.suivieadministratif.model.LigneSuiviCMD_frns;
import com.example.suivieadministratif.model.SuiviCMD_frns;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.CommandeFournisseurNonConforme;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.SuivieCommandeFrs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommandeFrnsNonConformeTask extends AsyncTask<String, String, String> {


    Activity activity;

    RecyclerView rv_suivie_cmd_frns;
    ProgressBar pb;

    Date date_debut, date_fin;

    String CodeFournisseur;
    String CodeResponsable;
    String  Origine  ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<SuiviCMD_frns> listSuiviCMD_frns = new ArrayList<>();

    double total_ = 0;

    String  res  = "" ;

    public CommandeFrnsNonConformeTask(Activity activity, RecyclerView rv_suivie_cmd_frns, ProgressBar pb, Date date_debut, Date date_fin, String CodeFournisseur, String CodeResponsable ,String  Origine ) {
        this.activity = activity;
        this.rv_suivie_cmd_frns = rv_suivie_cmd_frns;
        this.pb = pb;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.CodeFournisseur=CodeFournisseur  ;
        this.CodeResponsable=CodeResponsable ;
        this.Origine=Origine;


        SharedPreferences prefe = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);
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
                res = "Error in connection with SQL server";
            } else {

                String  CONDITION = "  " ;

               if (!CodeFournisseur.equals(""))
                {
                    CONDITION =  CONDITION + "   and Fournisseur.CodeFournisseur = '"+CodeFournisseur+"' " ;
                }
                 if (!CodeResponsable.equals(""))
                {
                    CONDITION =  CONDITION + "   and Fournisseur.CodeRAD =    '"+CodeResponsable+"'  " ;
                }

             /*   if (!CodeNatureArticle.equals(""))
                {
                    CONDITION =  CONDITION + "  and Article.CodeNature = '"+CodeNatureArticle+"'  " ;
                }
*/

                String query_suivie_cmd_frns = "  SELECT \n" +
                        "        BonCommandeAchat . NumeroBonCommandeAchat  ,\n" +
                        "        BonCommandeAchat . DateBonCommandeAchat , \n" +
                        "        BonLivraisonAchat . CodeFournisseur   , \n" +
                        "        BonLivraisonAchat . RaisonSociale ,  \n" +
                        "        BonCommandeAchat . TotalHT ,  \n" +
                        "        BonLivraisonAchat . DateBonLivraisonAchat  \n" +
                        "         \n" +
                        "     FROM   (   LigneBonCommandeAchat   LigneBonCommandeAchat \n" +
                        "      LEFT OUTER JOIN ((   Stock   Stock  INNER JOIN    LigneBonLivraisonAchat   LigneBonLivraisonAchat  ON  Stock . CodeArticle = LigneBonLivraisonAchat . CodeArticle ) \n" +
                        "      \n" +
                        "      INNER JOIN    BonLivraisonAchat   BonLivraisonAchat  ON ( Stock . CodeDepot = BonLivraisonAchat . CodeDepot ) \n" +
                        "      AND ( LigneBonLivraisonAchat . NumeroBonLivraisonAchat = BonLivraisonAchat . NumeroBonLivraisonAchat )) \n" +
                        "      ON ( LigneBonCommandeAchat . CodeArticle = LigneBonLivraisonAchat . CodeArticle ) \n" +
                        "      AND ( LigneBonCommandeAchat . NumeroBonCommandeAchat = LigneBonLivraisonAchat . NumeroBonCommandeAchat )) \n" +
                        "      INNER JOIN    BonCommandeAchat   BonCommandeAchat  ON  LigneBonLivraisonAchat . NumeroBonCommandeAchat = BonCommandeAchat . NumeroBonCommandeAchat \n" +
                        "    \n" +
                        "    INNER  JOIN Fournisseur on Fournisseur.CodeFournisseur= BonCommandeAchat.CodeFournisseur\n" +
                        "     WHERE  ( BonCommandeAchat . DateBonCommandeAchat  between  '"+df.format(date_debut)+"' and  '"+df.format(date_fin)+"' )\n" +
                        "        \n" +

                        "         \n" +
                        "        and    LigneBonLivraisonAchat.Quantite <> LigneBonCommandeAchat.Quantite \n" +
                        "        \n" +CONDITION+
                        "           \n " +
                        "" +
                        "            group BY  BonCommandeAchat . NumeroBonCommandeAchat   , BonCommandeAchat . DateBonCommandeAchat , \n" +
                        "            BonLivraisonAchat . CodeFournisseur   , \n" +
                        "            BonLivraisonAchat . RaisonSociale ,  \n" +
                        "            BonCommandeAchat . TotalHT ,  \n" +
                        "            BonLivraisonAchat . DateBonLivraisonAchat \n \n" +
                        "\n"  ;


                Log.e("query_suivie_cmd_frns", "" + query_suivie_cmd_frns);
                PreparedStatement ps = con.prepareStatement(query_suivie_cmd_frns);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    String NumeroBonCommandeAchat = rs.getString("NumeroBonCommandeAchat");
                    String CodeFournisseur = rs.getString("CodeFournisseur");
                    String RaisonSociale = rs.getString("RaisonSociale");
                    double TotalHT =0;//= rs.getDouble("TotalHT");
                    Date DateBonCommandeAchat = dtfSQL.parse(rs.getString("DateBonCommandeAchat"));




                    SuiviCMD_frns suiviCMD_frns = new SuiviCMD_frns(NumeroBonCommandeAchat ,DateBonCommandeAchat,CodeFournisseur ,RaisonSociale ,TotalHT ) ;



                    String query_detail_suivie_cmd_frns = "  SELECT     LigneBonCommandeAchat . CodeArticle ,\n" +
                            "\t LigneBonCommandeAchat . DesignationArticle , \n" +
                            "            LigneBonLivraisonAchat . Quantite  as qtLivrer ,  \n" +
                            "            LigneBonCommandeAchat . Quantite as qtCMD ,  \n" +
                            "             Stock . Quantite as qtStock ,   \n" +
                            "             LigneBonCommandeAchat . MontantHT , \n" +
                            "             LigneBonCommandeAchat . PrixAchatHT    \n" +
                            "             \n" +
                            "             \n" +
                            " FROM   (  LigneBonCommandeAchat   LigneBonCommandeAchat  LEFT OUTER JOIN ((  Stock   Stock  INNER JOIN   LigneBonLivraisonAchat   LigneBonLivraisonAchat  ON  Stock . CodeArticle = LigneBonLivraisonAchat . CodeArticle ) INNER JOIN   BonLivraisonAchat   BonLivraisonAchat  ON ( Stock . CodeDepot = BonLivraisonAchat . CodeDepot ) AND ( LigneBonLivraisonAchat . NumeroBonLivraisonAchat = BonLivraisonAchat . NumeroBonLivraisonAchat )) ON ( LigneBonCommandeAchat . CodeArticle = LigneBonLivraisonAchat . CodeArticle ) AND ( LigneBonCommandeAchat . NumeroBonCommandeAchat = LigneBonLivraisonAchat . NumeroBonCommandeAchat )) INNER JOIN   BonCommandeAchat   BonCommandeAchat  ON  LigneBonLivraisonAchat . NumeroBonCommandeAchat = BonCommandeAchat . NumeroBonCommandeAchat \n" +
                            "\n" +
                            "\n" +
                            " WHERE   LigneBonLivraisonAchat . NumeroBonCommandeAchat = '"+NumeroBonCommandeAchat+"'\n" +
                            " \n" +
                            " and LigneBonLivraisonAchat.Quantite<>LigneBonCommandeAchat.Quantite  ";


                    Log.e("query_detail_livraison", query_detail_suivie_cmd_frns);

                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(query_detail_suivie_cmd_frns);
                    ArrayList<LigneCMDFrnsNonConforme> listLigneCMDFrnsNonConformes = new ArrayList<>();
                    double  montant_HT =0 ;
                    while (rs2.next()) {

                        String CodeArticle = rs2.getString("CodeArticle");
                        String DesignationArticle = rs2.getString("DesignationArticle");
                        int qtLivrer = rs2.getInt("qtLivrer");
                        int qtCMD = rs2.getInt("qtCMD");
                        int qtStock = rs2.getInt("qtStock");

                        double  MontantHT = rs2.getDouble("MontantHT");
                        double  PrixAchatHT = rs2.getDouble("PrixAchatHT");


                        LigneCMDFrnsNonConforme ligneSuiviCMD_frns = new LigneCMDFrnsNonConforme(CodeArticle, DesignationArticle,qtLivrer, qtCMD,qtStock,MontantHT ,PrixAchatHT ) ;
                        listLigneCMDFrnsNonConformes.add(ligneSuiviCMD_frns) ;


                        if(qtLivrer == qtCMD)
                        {
                            montant_HT=0 ;
                        }
                        else {
                            montant_HT = (qtCMD-qtLivrer ) * PrixAchatHT ;
                        }

                        TotalHT=TotalHT+montant_HT ;

                    }

                    suiviCMD_frns.setTotalHT(TotalHT);
                    suiviCMD_frns.setListCmdFrnsNonConformes(listLigneCMDFrnsNonConformes);
                    listSuiviCMD_frns.add(suiviCMD_frns);
                    total_ = total_ + suiviCMD_frns.getTotalHT() ;
                }

                res = "Success";
            }
        } catch (Exception ex) {
            res = "Error retrieving data from table";
        }
        return res;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);

        BonAchatSuiviCMDRVAdapter  adapter = new BonAchatSuiviCMDRVAdapter(activity, listSuiviCMD_frns,Origine);
        rv_suivie_cmd_frns.setAdapter(adapter);

        DecimalFormat decF = new DecimalFormat("0.000");
        CommandeFournisseurNonConforme.txt_total_ht.setText(decF.format(total_) + " Dt");

    }

}