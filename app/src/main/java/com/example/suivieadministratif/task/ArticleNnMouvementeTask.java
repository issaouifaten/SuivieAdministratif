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
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.ArticleNonMouvementeAdapterLV;
import com.example.suivieadministratif.model.ArticleFaibleRotation;
import com.example.suivieadministratif.model.ArticleNonMouvemente;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.article.ArticleNonMouvementeActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

public class ArticleNnMouvementeTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv_article_faible_rotation ;
    SearchView search_bar;
    ProgressBar pb;

    String CodeDep;
    String DateDebut;
    String DateFin;

    String CodeFrns;
    String CodeFam;
    int FrnsEtranger;

    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    ArrayList<ArticleNonMouvemente> listArticleNonMouvemente = new ArrayList<>();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

   int  qt_stock ;
    int qt_achat ;
    double  total_ht ;

    public ArticleNnMouvementeTask(Activity activity, ListView lv_article_faible_rotation, SearchView search_bar, ProgressBar pb,
                                   String DateDebut, String DateFin, String CodeDepot, String CodeFrns, String CodeFam, int FrnsEtranger) {
        this.activity = activity;
        this.lv_article_faible_rotation = lv_article_faible_rotation;
        this.pb = pb;
        this.DateDebut = DateDebut;
        this.DateFin = DateFin;
        this.search_bar = search_bar;
        this.CodeFrns = CodeFrns;
        this.CodeFam = CodeFam;
        this.CodeDep = CodeDepot;
        this.FrnsEtranger = FrnsEtranger;


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

                String QueryProc = "DECLARE\t@return_value int \n" +
                        "EXEC\t@return_value = [dbo].[StockReelEdition]\n" +
                        "\t\t@CodeDepot = N'" + CodeDep + "',\n" +
                        "\t\t@DateDebut = '" +  DateDebut  + "' \n" +

                        "\n" +
                        "SELECT\t'Return Value' = @return_value\n";


                Log.e("QueryProc", "" + QueryProc);
                PreparedStatement ps1 = con.prepareStatement(QueryProc);
                ResultSet rs1 = ps1.executeQuery();


                String CONDITION = "  ";

                if (CodeFrns.equals("")) {
                    CONDITION = CONDITION + "";

                } else {

                    CONDITION = CONDITION + "\n and    CodeFournisseur  =  '" + CodeFrns + "'   ";
                }

                if (CodeDep.equals("")) {
                    CONDITION = CONDITION + "";

                } else {

                    CONDITION = CONDITION + "\n and    CodeDepot  =  '" + CodeDep + "'   ";
                }

                if (CodeFam.equals("")) {
                    CONDITION = CONDITION + "";
                } else {
                    CONDITION = CONDITION + "\n and    CodeFamille    =  '" + CodeFam + "'   ";
                }


                if (FrnsEtranger == 2) {
                    CONDITION = CONDITION + "";
                } else {
                    CONDITION = CONDITION + "\n and    Etrange    =  '" + FrnsEtranger + "'   ";
                }


                String query_art_nn_mvmnt_periode_depot = " \n" +
                        "select * from \n" +
                        "\n" +
                        "(\n" +
                        "\n" +
                        "SELECT dbo.Fournisseur.Etrange,  StockCalculerAuDateEdition.CodeDepot AS CodeDepot,\n" +
                        "dbo.Article.CodeArticle,\n" +
                        "ISNULL(Quantite,0) AS QuantiteInitial,\n" +
                        "(SELECT ISNULL(SUM(quantite),0) FROM dbo.LigneBonLivraisonAchat INNER JOIN dbo.BonLivraisonAchat ON dbo.LigneBonLivraisonAchat.NumeroBonLivraisonAchat = \n" +
                        "dbo.BonLivraisonAchat.NumeroBonLivraisonAchat\n" +
                        " WHERE dbo.LigneBonLivraisonAchat.CodeArticle = dbo.Article.CodeArticle\n" +
                        " AND CONVERT(DATE, DateBonLivraisonAchat) BETWEEN CONVERT(DATE, '"+DateDebut+"') AND CONVERT(DATE, '"+DateFin+"') AND CodeDepot =  StockCalculerAuDateEdition.CodeDepot ) \n" +
                        " +\n" +
                        "(SELECT ISNULL(SUM(quantite),0) FROM dbo.LigneFactureAchat INNER JOIN dbo.FactureAchat\n" +
                        "ON dbo.LigneFactureAchat.NumeroFactureAchat = dbo.FactureAchat.NumeroFactureAchat\n" +
                        " WHERE dbo.LigneFactureAchat.CodeArticle = dbo.Article.CodeArticle \n" +
                        " AND  NumeroBonLivraisonAchat = ''\n" +
                        " AND CONVERT(DATE, DateFactureAchat) BETWEEN CONVERT(DATE, '"+DateDebut+"') AND CONVERT(DATE, '"+DateFin+"') AND CodeDepot =  StockCalculerAuDateEdition.CodeDepot )  \n" +
                        "  \n" +
                        " AS QuantiteAchete\n" +
                        "  ,dbo.FamilleArticle.CodeFamille,dbo.FamilleArticle.Libelle,\n" +
                        "  dbo.Fournisseur.CodeFournisseur,dbo.Fournisseur.RaisonSociale,dbo.Article.PrixAchatHT,dbo.Article.Designation\n" +
                        " ,ISNULL(Quantite,0)* Article.PrixAchatHT As ValeurHT\n" +
                        " FROM dbo.StockCalculerAuDateEdition\n" +
                        "INNER JOIN dbo.Article  ON dbo.Article.CodeArticle = dbo.StockCalculerAuDateEdition.CodeArticle\n" +
                        "INNER JOIN Dbo.Fournisseur ON Dbo.Fournisseur.CodeFournisseur = dbo.Article.codefournisseur\n" +
                        "INNER JOIN Dbo.FamilleArticle ON Dbo.FamilleArticle.CodeFamille = dbo.Article.CodeFamille\n" +
                        "WHERE \n" +
                        " dbo.Article.CodeNature = '1' and StockCalculerAuDateEdition.Quantite>0  \n" +
                        "AND\n" +
                        "  dbo.StockCalculerAuDateEdition.CodeArticle NOT IN (SELECT DISTINCT(CodeArticle) FROM dbo.LigneBonLivraisonVente\n" +
                        "INNER JOIN dbo.BonLivraisonVente ON dbo.LigneBonLivraisonVente.NumeroBonLivraisonVente = dbo.BonLivraisonVente.NumeroBonLivraisonVente\n" +
                        "WHERE CONVERT(DATE, DateBonLivraisonVente) BETWEEN CONVERT(DATE, '"+DateDebut+"') AND CONVERT(DATE, '"+DateFin+"') AND CodeDepot =  StockCalculerAuDateEdition.CodeDepot\n" +
                        "union all\n" +
                        "SELECT DISTINCT(CodeArticle) FROM dbo.LigneFactureVente\n" +
                        "INNER JOIN dbo.FactureVente ON dbo.LigneFactureVente.NumeroFactureVente = dbo.FactureVente.NumeroFactureVente\n" +
                        " WHERE NumeroBonLivraisonVente = '' \n" +
                        " AND  CONVERT(DATE, DateFactureVente) BETWEEN CONVERT(DATE, '"+DateDebut+"') AND CONVERT(DATE, '"+DateFin+"') AND CodeDepot=  StockCalculerAuDateEdition.CodeDepot)\n" +
                        " \n" +
                        " ) as  T \n" +
                        " where 1=1  "+CONDITION  ;


                Log.e("query_art_nn_mvmnt", "" + query_art_nn_mvmnt_periode_depot);
                PreparedStatement ps = con.prepareStatement(query_art_nn_mvmnt_periode_depot);
                ResultSet rs = ps.executeQuery();


                while (rs.next()) {

                    int Etrange = rs.getInt("Etrange");

                    String CodeDepot = rs.getString("CodeDepot");
                    String CodeArticle = rs.getString("CodeArticle");

                    int QuantiteInitial = rs.getInt("QuantiteInitial");
                    int QuantiteAchete = rs.getInt("QuantiteAchete");
                    String CodeFamille = rs.getString("CodeFamille");
                    String Libelle = rs.getString("Libelle");

                    String CodeFournisseur = rs.getString("CodeFournisseur");
                    String RaisonSociale = rs.getString("RaisonSociale");
                    double PrixAchatHT = rs.getDouble("PrixAchatHT");
                    String Designation = rs.getString("Designation");
                    double valeurHT = rs.getDouble("valeurHT");

                    qt_stock += QuantiteInitial ;
                    qt_achat+= QuantiteAchete ;
                    total_ht += valeurHT ;

                    ArticleNonMouvemente articleNonMouvemente = new ArticleNonMouvemente(Etrange ,CodeDepot ,CodeArticle ,QuantiteInitial ,
                            QuantiteAchete ,CodeFamille , Libelle ,CodeFournisseur ,RaisonSociale ,PrixAchatHT ,Designation , valeurHT ) ;

                    listArticleNonMouvemente.add(articleNonMouvemente);

                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
            Log.e("ERROR_art_nn_mvnt", "" + ex.getMessage().toString());
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);


        ArticleNonMouvementeAdapterLV adapterLV = new ArticleNonMouvementeAdapterLV(activity, listArticleNonMouvemente);
        lv_article_faible_rotation.setAdapter(adapterLV);

        ArticleNonMouvementeActivity.txt_qt_achat.setText(qt_achat+"");
        ArticleNonMouvementeActivity.txt_qt_stock.setText(qt_stock+"");

        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
        instance.setMinimumFractionDigits(3);
        instance.setMaximumFractionDigits(3);


        ArticleNonMouvementeActivity.txt_total_ht.setText(instance.format(total_ht)+"");

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

             if (query.equals("")) {
                 ArticleNonMouvementeAdapterLV adapterLV = new ArticleNonMouvementeAdapterLV(activity, listArticleNonMouvemente);
                    lv_article_faible_rotation.setAdapter(adapterLV);
                } else {

                    final ArrayList<ArticleNonMouvemente> fitlerArtFR = filterArticleFR(listArticleNonMouvemente, query);
                    ArticleNonMouvementeAdapterLV adapterLV = new ArticleNonMouvementeAdapterLV(activity, fitlerArtFR);
                    lv_article_faible_rotation.setAdapter(adapterLV);

                }

                return false;

            }
        });


    }


    private ArrayList<ArticleNonMouvemente> filterArticleFR(ArrayList<ArticleNonMouvemente> listArtFR, String term) {

        term = term.toLowerCase();
        final ArrayList<ArticleNonMouvemente> filetrListArtFR = new ArrayList<>();

        ArrayList<String> aList = new ArrayList(Arrays.asList(term.split(" ")));

        Iterator<String> iterator = aList.iterator();


        while (iterator.hasNext()) {

            String t = iterator.next();

            if (t.contains(" "))
                t.replace(" ", "");
            t.trim();

            if (t.equals(""))
                iterator.remove();

        }


        Log.e("aList", aList.toString());

        for (ArticleNonMouvemente a : listArtFR) {

            final String txtDesign = a.getDesignation().toLowerCase();

            for (String input : aList) {
                if (txtDesign.contains(input)) {
                    filetrListArtFR.add(a);
                    break;
                }
            }

          /*  if (txtDesign.contains(term) || txtDesign.contains()) {
                filetrListArtFR.add(a);
            }*/
        }

        return filetrListArtFR;

    }


}