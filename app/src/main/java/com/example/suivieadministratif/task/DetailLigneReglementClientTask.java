package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.DetailReglementAdapterRV;
import com.example.suivieadministratif.adapter.LigneReglementAdapterRV;
import com.example.suivieadministratif.adapter.ReglementClientAdapterLV;
import com.example.suivieadministratif.model.DetailReglementClient;
import com.example.suivieadministratif.model.LigneReglementClient;
import com.example.suivieadministratif.model.ReglementClient;
import com.example.suivieadministratif.module.reglementClient.DetailReglementClientActivity;
import com.example.suivieadministratif.module.reglementClient.ReglementClientActivity;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.recyclerview.widget.RecyclerView;


public class DetailLigneReglementClientTask extends AsyncTask<String, String, String> {

    String res;
    Activity activity;

    String  NumeroRegClient ;
    RecyclerView  rv_ligne_reg_client ;
    RecyclerView  rv_detail_reg_client ;


    ProgressBar pb_ligne , pb_detail   ;

    Date  date_debut , date_fin ;
    ConnectionClass connectionClass ;
    String user, password, base, ip;

    SimpleDateFormat  sdf  = new SimpleDateFormat("dd/MM/yyyy") ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ArrayList <DetailReglementClient> listDetailReglementClient = new ArrayList<>() ;
    ArrayList <LigneReglementClient> listLigneReglementClient = new ArrayList<>() ;
    double total_reglement  =0 ;

    public DetailLigneReglementClientTask ( Activity activity , String  NumeroRegClient  , RecyclerView  rv_ligne_reg_client,
            RecyclerView  rv_detail_reg_client  ,   ProgressBar pb_ligne ,ProgressBar pb_detail   ) {
        this.activity  = activity  ;
        this.NumeroRegClient=NumeroRegClient;
        this.rv_ligne_reg_client = rv_ligne_reg_client  ;
        this.rv_detail_reg_client = rv_detail_reg_client  ;
        this.pb_ligne  = pb_ligne   ;
        this.pb_detail  = pb_detail   ;


        SharedPreferences pref = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip   = pref.getString("ip", ip);
        base = pref.getString("base", base);
        password = pref.getString("password", password);

        connectionClass = new ConnectionClass();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb_ligne.setVisibility(View.VISIBLE);
        pb_detail.setVisibility(View.VISIBLE);
        total_reglement  =0 ;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);       // Connect to database
            Log.e("con", "" + con);
            if (con == null) {
                res = "Check Your Internet Access!";
            } else {


                String query_detail_reg_client = " select NumeroReglementClient ,CodeModeReglement  , Reference  ,Echeance , MontantRecu  \n" +
                        "from DetailReglementClient  where NumeroReglementClient = '"+NumeroRegClient+"' " ;

                Log.e("query_detail_reg_client", query_detail_reg_client);

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query_detail_reg_client);

                while (rs.next()) {

                    String NumeroReglementClient  = rs.getString("NumeroReglementClient");
                    String CodeModeReglement  = rs.getString("CodeModeReglement");
                    String Reference  = rs.getString("Reference");
                    Date Echeance  =dtfSQL.parse( rs.getString("Echeance"));
                    double MontantRecu  = rs.getDouble("MontantRecu");

                    total_reglement  =  total_reglement + MontantRecu ;

                    DetailReglementClient detailReglementClient  = new  DetailReglementClient (NumeroReglementClient ,CodeModeReglement , Reference ,Echeance, MontantRecu ) ;
                    listDetailReglementClient.add(detailReglementClient) ;


                }


                String query_ligne_reg_client  = "select NumeroPiece , TypePiece ,\n" +
                        "case when TypePiece ='BL' then (select  DateBonLIvraisonVente   from  BonLivraisonVente  where  NumeroBonLivraisonVente =  NumeroPiece)\n" +
                        "when TypePiece ='FV' then (select  DateFactureVente  from  FactureVente  where  NumeroFactureVente=  NumeroPiece)\n" +
                        "end   \n" +
                        "as datePiece  \n" +
                        ",MontantPieceTTC ,TotalRecu  ,TotalPayee , TotalRetenu\n" +
                        "from LigneReglementClient  where NumeroReglementClient = '"+NumeroRegClient+"' " ;

                Statement stmt_1 = con.createStatement();
                ResultSet rs_1 = stmt_1.executeQuery(query_ligne_reg_client);
                while (rs_1.next()) {

                    String NumeroPiece  = rs_1.getString("NumeroPiece");
                    String TypePiece  = rs_1.getString("TypePiece");
                    Date datePiece  =dtfSQL.parse( rs_1.getString("datePiece"));
                    Double MontantPieceTTC  = rs_1.getDouble("MontantPieceTTC");
                    Double TotalRecu  = rs_1.getDouble("TotalRecu");
                    Double TotalPayee  = rs_1.getDouble("TotalPayee");
                    Double TotalRetenu  = rs_1.getDouble("TotalRetenu");


                    LigneReglementClient ligneReglementClient  = new  LigneReglementClient (NumeroPiece ,TypePiece ,datePiece ,MontantPieceTTC ,TotalRecu ,TotalPayee ,TotalRetenu ) ;
                    listLigneReglementClient.add(ligneReglementClient) ;


                }
            }
            con.close();

        } catch (Exception ex) {

            res = ex.getMessage();
            Log.e("ERROR_listRDV", res.toString());

        }
        return null;

    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        pb_ligne.setVisibility(View.GONE);
        pb_detail.setVisibility(View.GONE);

        DecimalFormat  decF  = new DecimalFormat("0.000") ;

        LigneReglementAdapterRV adapterRV = new LigneReglementAdapterRV(activity , listLigneReglementClient , "") ;
        rv_ligne_reg_client.setAdapter(adapterRV);

        DetailReglementAdapterRV adapterRV1 = new DetailReglementAdapterRV(activity  , listDetailReglementClient , "") ;
        rv_detail_reg_client.setAdapter(adapterRV1);

        ReglementClientActivity.txt_tot_reglement.setText(decF.format(total_reglement) +" Dt");

    }

}
