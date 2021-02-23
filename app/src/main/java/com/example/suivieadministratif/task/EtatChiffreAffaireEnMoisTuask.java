package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.adapter.SuivieFournisseurExpandableListAdapter;
import com.example.suivieadministratif.model.CaParPeriode;
import com.example.suivieadministratif.model.EnteteRelationFournisseur;
import com.example.suivieadministratif.model.RelationFournisseur;
import com.example.suivieadministratif.param.Param;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EtatChiffreAffaireEnMoisTuask extends AsyncTask<String, String, String> {


    Activity activity;

    BarChart barGraph ;

    Date date_debut, date_fin;

    ProgressBar pb;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;


    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    ArrayList<CaParPeriode> listCAParPeriode= new ArrayList<>();

  ArrayList<BarEntry> listTaux = new ArrayList<>();

    ArrayList<String> listCode = new ArrayList<>() ;

    int listColor [] = {R.color.color_g_1 ,R.color.color_g_2 ,R.color.color_g_3,R.color.color_g_4 ,R.color.color_g_5
            ,R.color.color_g_6 ,R.color.color_g_7 ,R.color.color_g_8,R.color.color_g_9 ,R.color.color_g_10
            ,R.color.color_g_11 ,R.color.color_g_12 ,R.color.color_g_13,R.color.color_g_14 ,R.color.color_g_15
            ,R.color.color_g_16 ,R.color.color_g_17 ,R.color.color_g_18,R.color.color_g_19 ,R.color.color_g_20,
            R.color.color_g_21} ;


    public EtatChiffreAffaireEnMoisTuask(Activity activity,   BarChart barGraph ,Date date_debut, Date date_fin, ProgressBar pb) {
        this.activity = activity;
        this.barGraph = barGraph  ;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
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

                String query_entete_suivie_relation = " select  YEAR(DatePiece) as annee,\n" +
                        "MONTH(DatePiece) as mois,\n" +
                        "sum(NetHT) as Total, \n" +
                        "CAST(MONTH(DatePiece) AS varchar(2)) + '/' +    CAST(year(DatePiece) AS varchar(4)) as Code    \n" +
                        "from  dbo.Vue_ListeVenteGlobal   \n" +
                        "WHERE DatePiece between '"+df.format(date_debut)+"' AND '"+df.format(date_fin)+"'    \n" +
                        "GROUP by   MONTH(DatePiece) ,YEAR(DatePiece)  \n" +
                        "ORDER BY CONVERT(DATE,'01/'+CAST(MONTH(DatePiece) AS varchar(2)) + '/' +    CAST(year(DatePiece) AS varchar(4))) \n ";


                Log.e("query_entete", "" + query_entete_suivie_relation);
                PreparedStatement ps = con.prepareStatement(query_entete_suivie_relation);
                ResultSet rs = ps.executeQuery();

                int  index  =0  ;

                String  Label = "" ;

                while (rs.next()) {

                    int annee = rs.getInt("annee");
                    int mois = rs.getInt("mois");
                    double Total = rs.getDouble("Total");
                    String Code  = rs.getString("Code") ;

                    CaParPeriode  caParPeriode  = new CaParPeriode(annee ,mois ,Total ,Code  ) ;
                    listCAParPeriode .add(caParPeriode) ;


                    listTaux.add( new BarEntry(  index  ,(float) Total ,Code ) );
                    listCode.add(Code) ;
                    index ++ ;
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


        CreateHistogramme (listCAParPeriode) ;

    }

    public  void CreateHistogramme (   ArrayList<CaParPeriode> listCAParPeriode )
    {

        ArrayList<Integer> arrayListColor = new ArrayList<>();
        for (int c : listColor) {
            arrayListColor.add(c);

        }


        ArrayList<String> labels  = new ArrayList<>() ;
        labels.add("") ;






        List<IBarDataSet> listBarDataSet  = new ArrayList<>() ;
        int  index  =0 ;
        for (CaParPeriode  ca  :  listCAParPeriode)
        {
            ArrayList<BarEntry> listTaux = new ArrayList<>();

            listTaux.add(new BarEntry(  index  ,(float) ca.getTotal() , ca.getCode() ) ) ;

            BarDataSet barDataSet = new BarDataSet( listTaux ,ca.getCode() ) ;


             barDataSet.setColor(  activity.getResources().getColor(listColor[index])  );
            listBarDataSet.add(barDataSet) ;
            index ++ ;

        }

        BarData data = new BarData(  listBarDataSet );

        barGraph.setDrawBarShadow(false);
        barGraph.setData(data);
        barGraph.animateY(3000);

        barGraph.setDrawGridBackground(false);
       //  barGraph.setDescriptionColor(R.color.color_xt);
        barGraph.setBorderColor(R.color.color_xt);
        barGraph.getAxisRight().setEnabled(false);
        //barGraph.getAxisLeft().setEnabled(false);


    }


}