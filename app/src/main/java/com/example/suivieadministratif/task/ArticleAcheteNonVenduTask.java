package com.example.suivieadministratif.task;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import androidx.recyclerview.widget.RecyclerView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.adapter.ArticleAcheteNonVenduAdapterRV;
import com.example.suivieadministratif.model.ArticleAcheteNonVendu;
import com.example.suivieadministratif.module.achat.FactureAchat;
import com.example.suivieadministratif.module.achat.LigneFactureAchat;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Achat.ArticleAcheteNonVenduActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ArticleAcheteNonVenduTask extends AsyncTask<String, String, String> {

    Activity activity;

    RecyclerView rv_list;
    ProgressBar pb;

    String  term_recherche ;
    Date  date_debut  ; Date date_fin ;



    String  z= "" ;


    int  qt_achat  = 0;
    double total_achat_ttc = 0;

    int   nbr_article =0 ;

    ConnectionClass connectionClass;
    String user, password, base, ip;
    SimpleDateFormat  sdf  = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<ArticleAcheteNonVendu> listArt_achete_nn_vendu  = new ArrayList<>() ;


    public ArticleAcheteNonVenduTask(Activity activity, Date  date_debut , Date date_fin  , RecyclerView rv_list, ProgressBar pb ,String term_recherche   ) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.rv_list = rv_list ;
        this.pb = pb;
        this.term_recherche=term_recherche  ;



        SharedPreferences prefe = activity.getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
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

        if (term_recherche.equals(""))
        {
            ArticleAcheteNonVenduActivity.txt_recherche.setText("Chargement en cours ...");
        }
        else {
            ArticleAcheteNonVenduActivity.txt_recherche.setText("Recherche en cours ...");
        }

        ArticleAcheteNonVenduActivity.txt_total_ttc.setText("---.---");
        ArticleAcheteNonVenduActivity.txt_tot_quantite_achat.setText("---");


    }

    @Override
    protected String doInBackground(String... params) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);
            if (con == null) {
                z = "Error in connection with SQL server";
            } else {


                String query_stock_a_la_date  =   "  DECLARE\t@return_value int\n" +
                        "\n" +
                        "EXEC\t@return_value = [dbo].[StockReel]\n" +
                        "\t\t@CodeDepot = N'01',\n" +
                        "\t\t@DateDebut = N'"+sdf.format(date_fin)+"'\n" +
                        "\n" +
                        "SELECT\t'Return Value' = @return_value\n "  ;

                PreparedStatement ps2 = con.prepareStatement(query_stock_a_la_date);
                ps2.executeQuery();



                String  Condition  = ""  ;

                if (term_recherche.equals(""))
                {
                    Condition  = ""  ;
                }
                else {
                    Condition  = "and ( RES.CodeArticle like  '%"+term_recherche+"%'  or  RES.DesignationArticle like '%"+term_recherche+"%' )"  ;
                }


                String query = "select  * from  (" +
                        "select      CodeDepot , CodeArticle,DesignationArticle,PrixAchatHT ,SUM(Quantite) as Quantite,\n" +
                        "( select  Quantite  from  StockCalculerAuDate where CodeArticle = Liste.CodeArticle ) as QTCalculerAlalaDate  ," +
                        "( select  Quantite  from  Stock where CodeArticle = Liste.CodeArticle ) as QTStock  ," +
                        " sum (MontantTVA) as MontantTVA  ," +
                        "sum (MontantTTC) as MontantTTC from( select * from (\n" +
                        "select BonLivraisonAchat.NumeroBonLivraisonAchat as NumeroPiece,  CodeDepot ,BonLivraisonAchat.CodeFournisseur,RaisonSociale,\n" +
                        "NumeroEtat,LigneBonLivraisonAchat.CodeArticle,DesignationArticle,Article.PrixAchatHT,Quantite,MontantTVA ,MontantTTC,\n" +
                        "DateBonLivraisonAchat as DatePiece from LigneBonLivraisonAchat inner join BonLivraisonAchat on \n" +
                        "LigneBonLivraisonAchat.NumeroBonLivraisonAchat=BonLivraisonAchat.NumeroBonLivraisonAchat \n" +
                        "inner join Article on LigneBonLivraisonAchat.CodeArticle=Article.CodeArticle where Article.Stockable=1 \n" +
                        "\n" +
                        " union all\n" +
                        " \n" +
                        "select FactureAchat.NumeroFactureAchat as NumeroPiece,  CodeDepot ,FactureAchat.CodeFournisseur,RaisonSociale,NumeroEtat,LigneFactureAchat.CodeArticle,\n" +
                        "DesignationArticle,Article.PrixAchatHT,Quantite,MontantTVA ,MontantTTC  ,DateFactureAchat as DatePiece\n" +
                        "from LigneFactureAchat inner join FactureAchat on LigneFactureAchat.NumeroFactureAchat=FactureAchat.NumeroFactureAchat \n" +
                        "inner join Article on LigneFactureAchat.CodeArticle=Article.CodeArticle where Article.Stockable=1    \n" +
                        "and FactureAchat.NumeroFactureAchat not in (select BonLivraisonAchat.NumeroFactureAchat   from BonLivraisonAchat \n" +
                        "where BonLivraisonAchat.NumeroBonLivraisonAchat=LigneFactureAchat.NumeroBonLivraisonAchat) \n" +
                        "and FactureAchat.NumeroFactureAchat not in (select BonRetourAchat.NumeroAvoirAchat from BonRetourAchat \n" +
                        "where BonRetourAchat.NumeroAvoirAchat=LigneFactureAchat.NumeroFactureAchat )\n" +
                        "and BonLivraisonAchatRelatif not like 'BA%'\n" +
                        "   \n" +
                        "   \n" +
                        "union all\n" +
                        "    \n" +
                        "    \n" +
                        "select  BonRetourAchat.NumeroBonRetourAchat as NumeroPiece,  CodeDepot ,BonRetourAchat.CodeFournisseur,RaisonSociale,NumeroEtat,LigneBonRetourAchat.CodeArticle,\n" +
                        "DesignationArticle,Article.PrixAchatHT,-Quantite,-MontantTVA ,-MontantTTC,DateBonRetourAchat as DatePiece from \n" +
                        "LigneBonRetourAchat inner join BonRetourAchat on LigneBonRetourAchat.NumeroBonRetourAchat=\n" +
                        "BonRetourAchat.NumeroBonRetourAchat inner join Article on LigneBonRetourAchat.CodeArticle=Article.CodeArticle \n" +
                        "where Article.Stockable=1 \n" +
                        "\n" +
                        "\n" +
                        "union all \n" +
                        "\n" +
                        "\n" +
                        "select AvoirAchat.NumeroAvoirAchat as NumeroPiece,  CodeDepot ,AvoirAchat.CodeFournisseur,RaisonSociale,NumeroEtat,\n" +
                        "LigneAvoirAchat.CodeArticle,DesignationArticle,Article.PrixAchatHT,Quantite,MontantTVA ,MontantTTC  ,\n" +
                        "DateAvoirAchat as DatePiece from LigneAvoirAchat inner join AvoirAchat on \n" +
                        "LigneAvoirAchat.NumeroAvoirAchat=AvoirAchat.NumeroAvoirAchat \n" +
                        "inner join Article on LigneAvoirAchat.CodeArticle=Article.CodeArticle \n" +
                        "where Article.Stockable=1   and AvoirAchat.NumeroAvoirAchat \n" +
                        "not in (select BonRetourAchat.NumeroAvoirAchat from BonRetourAchat \n" +
                        "where BonRetourAchat.NumeroAvoirAchat=  LigneAvoirAchat.NumeroAvoirAchat )) as T\n" +
                        "where DatePiece between  '"+sdf.format(date_debut)+"' and   '"+sdf.format(date_fin)+"'  and NumeroEtat!='E00' \n" +
                        "and NumeroEtat!='E40' and CodeArticle not in(select CodeArticle from LigneFactureVente \n" +
                        "inner join FactureVente on LigneFactureVente.NumeroFactureVente=FactureVente.NumeroFactureVente \n" +
                        "where LigneFactureVente.CodeArticle=T.CodeArticle and  DateFactureVente\n" +
                        "between   '"+sdf.format(date_debut)+"' and   '"+sdf.format(date_fin)+"' ) and CodeArticle\n" +
                        "not in(select CodeArticle from LigneBonLivraisonVente \n" +
                        "inner join BonLivraisonVente on  LigneBonLivraisonVente.\n" +
                        "NumeroBonLivraisonVente=BonLivraisonVente.NumeroBonLivraisonVente\n" +
                        "where LigneBonLivraisonVente.CodeArticle=T.CodeArticle and \n" +
                        "DateBonLivraisonVente  between  '"+sdf.format(date_debut)+"' and   '"+sdf.format(date_fin)+"' ))\n" +
                        " as Liste  \n" +
                        "where  CodeArticle not like '%Diver%'  \n" +
                        "group by     CodeDepot ,\n" +
                        "CodeArticle,DesignationArticle,PrixAchatHT \n" +
                        "having SUM(quantite)!=0\n" +
                        " ) as  RES where QTStock > 0  \n" +Condition+
                        "  " ;

                PreparedStatement ps = con.prepareStatement(query);
                Log.e("qu_art_achete_nn_vendu", query);

                ResultSet rs = ps.executeQuery();
                z = "e";
                nbr_article = 0  ;
                while (rs.next()) {


                    String CodeDepot = rs.getString("CodeDepot");
                    String CodeArticle = rs.getString("CodeArticle");
                    String DesignationArticle = rs.getString("DesignationArticle");

                    double PrixAchatHT = rs.getDouble("PrixAchatHT");
                    int QuantiteAchat = rs.getInt("Quantite");

                    double MontantTVA = rs.getDouble("MontantTVA");
                    double MontantTTC = rs.getDouble("MontantTTC");
                    int quantite_a_la_date = rs.getInt("QTCalculerAlalaDate");
                    int QTStock = rs.getInt("QTStock");

                    nbr_article++  ;

                   qt_achat  += QuantiteAchat;
                   total_achat_ttc += MontantTTC;

                    ArticleAcheteNonVendu  articleAcheteNonVendu  = new ArticleAcheteNonVendu(CodeDepot ,CodeArticle ,DesignationArticle  ,PrixAchatHT,MontantTVA ,MontantTTC ,QuantiteAchat ,quantite_a_la_date,QTStock) ;
                    listArt_achete_nn_vendu.add(articleAcheteNonVendu) ;


                    z = "succees";
                }


            }
        } catch (SQLException ex) {
            z = "tablelist" + ex.toString();
            Log.e("ERROR", "Art_achet_nn_vendu "+ex.getMessage().toString());


        }
        return z;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(String r) {
        pb.setVisibility(View.GONE);

        ArticleAcheteNonVenduAdapterRV  articleAcheteNonVenduAdapterRV  = new ArticleAcheteNonVenduAdapterRV(activity  , listArt_achete_nn_vendu ,"") ;
        rv_list.setAdapter(articleAcheteNonVenduAdapterRV);

        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);

        ArticleAcheteNonVenduActivity.txt_total_ttc.setText(instance.format(total_achat_ttc)+" Dt");
        ArticleAcheteNonVenduActivity.txt_tot_quantite_achat.setText(qt_achat+"");
        ArticleAcheteNonVenduActivity.txt_recherche.setText(nbr_article+" articles trouvés");

    }

}