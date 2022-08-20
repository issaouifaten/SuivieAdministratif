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
import com.example.suivieadministratif.adapter.ArticleFaibleRotationAdapterLV;

import com.example.suivieadministratif.model.ArticleFaibleRotation;

import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.article.ArticleFaibleRotationActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

public class ArticleFaibleRotationTask extends AsyncTask<String, String, String> {


    Activity activity;

    ListView lv_article_faible_rotation;
    SearchView search_bar;
    ProgressBar pb;

    String CodeDepot;
    String DateDebut;
    String DateFin;

    String CodeFrns;
    String CodeFamille;
    int FrnsEtranger;

    double  taux_rot_inf, taux_rot_max  ;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    ArrayList<ArticleFaibleRotation> listArticleFaibleRotation = new ArrayList<>();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    double CA = 0;
    double Benif = 0;

    public ArticleFaibleRotationTask(Activity activity, ListView lv_article_faible_rotation, SearchView search_bar, ProgressBar pb,
                                     String DateDebut, String DateFin, String CodeDepot, String CodeFrns, String CodeFamille, int FrnsEtranger,double taux_rot_inf,double   taux_rot_max ) {
        this.activity = activity;
        this.lv_article_faible_rotation = lv_article_faible_rotation;
        this.pb = pb;
        this.DateDebut = DateDebut;
        this.DateFin = DateFin;
        this.search_bar = search_bar;
        this.CodeFrns = CodeFrns;
        this.CodeFamille = CodeFamille;
        this.CodeDepot = CodeDepot;
        this.FrnsEtranger = FrnsEtranger;
        this.taux_rot_inf = taux_rot_inf ;
        this.taux_rot_max= taux_rot_max ;

    /*    this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.lv_article_faible_rotation = lv_article_faible_rotation;
        this.pb = pb;*/

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

        if (taux_rot_inf!=0)
        {
            ArticleFaibleRotationActivity.txt_recherche.setText("calcul en cours ...");
            ArticleFaibleRotationActivity.progressBar.setVisibility(View.VISIBLE);
        }
        else   if (taux_rot_inf==0)
        {
            ArticleFaibleRotationActivity.txt_recherche.setText("Chercher");
            ArticleFaibleRotationActivity.progressBar.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);
            if (con == null) {
                z = "Error in connection with SQL server";
            } else {


                String QueryProc = " DECLARE\t@return_value int \n" +
                        "EXEC\t" +
                        "@return_value = [dbo].[ArticlesNonMouvementeParPeriodeParDepot]\n" +
                        "\t\t@CodeDepot = N'" + CodeDepot + "',\n" +
                        "\t\t@DateDebut = '" + DateDebut + "',\n" +
                        "\t\t@DateFin = '" + DateFin + "'\n" +
                        "\n" +
                        "SELECT\t'Return Value' = @return_value\n";


                Log.e("QueryProc", "" + QueryProc);
                PreparedStatement ps1 = con.prepareStatement(QueryProc);
                ps1.executeQuery();


                String CONDITION = "  ";

                if (CodeFrns.equals("")) {
                    CONDITION = CONDITION + "";

                } else {

                    CONDITION = CONDITION + "\n and    CodeFournisseur  =  '" + CodeFrns + "'   ";
                }

                if (CodeFamille.equals("")) {
                    CONDITION = CONDITION + "";
                } else {
                    CONDITION = CONDITION + "\n and    CodeFamille    =  '" + CodeFamille + "'   ";
                }


                if (FrnsEtranger == 2) {
                    CONDITION = CONDITION + "";
                } else {
                    CONDITION = CONDITION + "\n and    Etrange    =  '" + FrnsEtranger + "'   ";
                }


                String query_art_nn_mvmnt_periode_depot =
                        " select  0.00  As TauxRotation,\n" +
                                "0.00 As TauxBenifice,\n" +
                                "0.00 As TauxCA ,\n" +
                                "0.00 As PourCBenifice,\n" +
                                "0.00 As Coeff, \n" +
                                "RotationArticle.CodeDepot,\n" +
                                "RotationArticle.CodeArticle,\n" +
                                "RotationArticle.Quantite,\n" +
                                "RotationArticle.PrixAchatHT,\n" +
                                "RotationArticle.ValeurStock,\n" +
                                "RotationArticle.QuantiteVendu,\n" +
                                "RotationArticle.ValeurVenteHT,\n" +
                                "RotationArticle.ValeurAchatHT,\n" +
                                "RotationArticle.AchatMoin20j,\n" +
                                "RotationArticle.TauxRotationCalculer,\n" +
                                "Article.Designation,CodeFamille,\n" +
                                "CodeFournisseur,\n" +
                                "Article.PrixAchatHT as  PrixAchatHTArticle ,\n" +
                                "PrixVenteHT, \n" +
                                "ISNULL(NonMouvementArticle.ValeurStock,0) As ValeurStockNonMouvementer \n" +
                                "from  RotationArticle \n" +
                                "inner join Article  on Article.CodeArticle= RotationArticle.CodeArticle\n" +
                                "left  join NonMouvementArticle on NonMouvementArticle.CodeArticle = RotationArticle.CodeArticle \n" +
                                "where 1= 1 " + CONDITION;


                Log.e("query_art_nn_mvmnt", "" + query_art_nn_mvmnt_periode_depot);
                PreparedStatement ps = con.prepareStatement(query_art_nn_mvmnt_periode_depot);
                ResultSet rs = ps.executeQuery();

                CA = 0;
                Benif = 0;


                int order = 1;
                while (rs.next()) {


                    String CodeDepot = rs.getString("CodeDepot");
                    String CodeArticle = rs.getString("CodeArticle");


                    double TauxRotation = rs.getDouble("TauxRotation");
                    double TauxBenifice = rs.getDouble("TauxBenifice");
                    double TauxCA = rs.getDouble("TauxCA");
                    double PourCBenifice = rs.getDouble("PourCBenifice");
                    double Coeff = rs.getDouble("Coeff");


                    int Quantite = rs.getInt("Quantite");
                    double PrixAchatHT = rs.getDouble("PrixAchatHT");

                    int QuantiteVendu = rs.getInt("QuantiteVendu");
                    int ValeurVenteHT = rs.getInt("ValeurVenteHT");
                    int ValeurAchatHT = rs.getInt("ValeurAchatHT");

                    int AchatMoin20j = rs.getInt("AchatMoin20j");
                    double TauxRotationCalculer = rs.getDouble("TauxRotationCalculer");

                    String Designation = rs.getString("Designation");
                    String CodeFamille = rs.getString("CodeFamille");
                    String CodeFournisseur = rs.getString("CodeFournisseur");

                    double PrixAchatHTArticle = rs.getDouble("PrixAchatHTArticle");
                    double PrixVenteHT = rs.getDouble("PrixVenteHT");
                    int ValeurStockNonMouvementer = rs.getInt("ValeurStockNonMouvementer");


                    CA = CA + ValeurVenteHT;
                    Benif = Benif + ValeurVenteHT - ValeurAchatHT;


                    ArticleFaibleRotation articleFaibleRotation = new ArticleFaibleRotation(TauxRotation, TauxBenifice, TauxCA, PourCBenifice, Coeff, CodeDepot,
                            CodeArticle, Quantite, PrixAchatHT, QuantiteVendu, ValeurVenteHT, ValeurAchatHT, AchatMoin20j, TauxRotationCalculer,
                            Designation, CodeFamille, CodeFournisseur, PrixAchatHTArticle, PrixVenteHT, ValeurStockNonMouvementer, order);

                    listArticleFaibleRotation.add(articleFaibleRotation);
                    order++;

                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
            Log.e("ERROR_art_faible_rot", "" + ex.getMessage().toString());
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);


        //  final ArrayList<ArticleFaibleRotation>  list_art_faible_rot = new ArrayList<>()  ;

        for (ArticleFaibleRotation art : listArticleFaibleRotation) {

            if (art.getQuantite() > art.getAchatMoin20j()) {
                if ((art.getQuantiteVendu() * 0.2) > (art.getQuantite() - art.getAchatMoin20j())) {
                    art.setTauxRotation((Math.round(art.getQuantiteVendu() / art.getQuantiteVendu())) * 100);

                    Log.e("" + art.getDesignation(), "COND1 " + art.getTauxRotation());
                } else {

                    double _taux_rotation =     (double ) ((double )art.getQuantiteVendu() / (double ) (art.getQuantite() - art.getAchatMoin20j())  )* 100;

                    double __taux_rotation = Math.round( _taux_rotation *100) /100  ;
                    art.setTauxRotation(__taux_rotation);


                    Log.e("" + art.getDesignation(), "COND2 " + art.getTauxRotation());
                }
            } else {

                if (art.getQuantite() != 0 && art.getQuantite() < art.getAchatMoin20j()) {
                    if (art.getQuantiteVendu() * 0.2 > art.getQuantite() && art.getQuantiteVendu() != 0)///modifier par marwa le 05/10/2019 tentative division par 0
                    {
                        art.setTauxRotation((Math.round(art.getQuantiteVendu() / art.getQuantiteVendu())) * 100);

                        Log.e("" + art.getDesignation(), "COND3 " + art.getTauxRotation());
                    } else {
                        art.setTauxRotation(Math.round(art.getQuantiteVendu() / art.getQuantite()) * 100);
                        Log.e("" + art.getDesignation(), "COND4 " + art.getTauxRotation());
                    }
                }
                if (art.getQuantite() == 0 && art.getQuantiteVendu() != 0) {
                    art.setTauxRotation(100);
                    Log.e("" + art.getDesignation(), "COND5 " + art.getTauxRotation());
                }

            }


            if (art.getValeurAchatHT() != 0) {
                if (art.getValeurVenteHT() != 0)
                    art.setTauxBenifice((((art.getValeurVenteHT() - art.getValeurAchatHT()) / art.getValeurVenteHT())) * 100);
            }
            if (CA != 0) {
                art.setTauxCA(((art.getValeurVenteHT() / CA)) * 100);

            }
            if (Benif != 0) {
                art.setPourCBenifice((((art.getValeurVenteHT() - art.getValeurAchatHT()) / Benif)) * 100);
            }


            if (art.getAchatMoin20j() != 0) {
                art.setCoeff(((art.getTauxCA() * 60) + (art.getPourCBenifice() * 30) + art.getTauxRotation()) / 10); // * (Convert.ToDecimal(DD["TauxCA"]))
            } else {
                art.setCoeff(((art.getTauxCA() * 60) + (art.getPourCBenifice() * 30) + art.getTauxRotation()) / 12); //* (Convert.ToDecimal(DD["TauxCA"]))
            }

            if (art.getTauxRotation() > 0) {
                //list_art_faible_rot.add(art)    ;
            }

        }


        if (taux_rot_inf !=0)
        {

            ArrayList<ArticleFaibleRotation> list_art_sup_rot  = new ArrayList<>() ;
            for (ArticleFaibleRotation  art  : listArticleFaibleRotation)
            {
                if (art.getTauxRotation()>taux_rot_inf  &&  art.getTauxRotation() < taux_rot_max  )
                    list_art_sup_rot.add(art);
            }
            listArticleFaibleRotation = list_art_sup_rot ;
        }


        ArticleFaibleRotationActivity.txt_recherche.setText("Chercher");
        ArticleFaibleRotationActivity.progressBar.setVisibility(View.INVISIBLE);

        ArticleFaibleRotationAdapterLV adapterLV = new ArticleFaibleRotationAdapterLV(activity, listArticleFaibleRotation);
        lv_article_faible_rotation.setAdapter(adapterLV);


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
                    ArticleFaibleRotationAdapterLV adapterLV = new ArticleFaibleRotationAdapterLV(activity, listArticleFaibleRotation);
                    lv_article_faible_rotation.setAdapter(adapterLV);
                } else {

                    final ArrayList<ArticleFaibleRotation> fitlerArtFR = filterArticleFR1(listArticleFaibleRotation, query);
                    ArticleFaibleRotationAdapterLV adapterLV = new ArticleFaibleRotationAdapterLV(activity, fitlerArtFR);
                    lv_article_faible_rotation.setAdapter(adapterLV);

                }

                return false;

            }
        });


    }


    private ArrayList<ArticleFaibleRotation> filterArticleFR1(ArrayList<ArticleFaibleRotation> listArtFR, String term) {

        term = term.toLowerCase();
        final ArrayList<ArticleFaibleRotation> filetrListArtFR = new ArrayList<>();

        for (ArticleFaibleRotation a : listArtFR) {

            String txtDesign = a.getDesignation().toLowerCase().toString();
            if (txtDesign.contains(term)) {
                filetrListArtFR.add(a);
            }


        }


        return filetrListArtFR;

    }



}