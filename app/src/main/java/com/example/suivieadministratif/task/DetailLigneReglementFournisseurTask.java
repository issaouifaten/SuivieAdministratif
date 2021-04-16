package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.DetailReglementfrnsAdapterRV;
import com.example.suivieadministratif.adapter.LigneReglementFrnsAdapterRV;
import com.example.suivieadministratif.model.DetailReglementFournisseur;
import com.example.suivieadministratif.model.LigneReglementFournisseur;
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


public class DetailLigneReglementFournisseurTask extends AsyncTask<String, String, String> {

    String res;
    Activity activity;

    String  NumeroRegFournisseur ;
    RecyclerView  rv_ligne_reg_frns ;
    RecyclerView  rv_detail_reg_frns ;


    ProgressBar pb_ligne , pb_detail   ;

    Date  date_debut , date_fin ;
    ConnectionClass connectionClass ;
    String user, password, base, ip;

    SimpleDateFormat  sdf  = new SimpleDateFormat("dd/MM/yyyy") ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ArrayList <DetailReglementFournisseur> listDetailReglementFournisseurs = new ArrayList<>() ;
    ArrayList <LigneReglementFournisseur> listLigneReglementFournisseurs = new ArrayList<>() ;
    double total_reglement  =0 ;

    public DetailLigneReglementFournisseurTask(Activity activity , String  NumeroRegFournisseur  , RecyclerView  rv_ligne_reg_frns,
                                               RecyclerView  rv_detail_reg_frns  , ProgressBar pb_ligne , ProgressBar pb_detail   ) {
        this.activity  = activity  ;
        this.NumeroRegFournisseur=NumeroRegFournisseur;
        this.rv_ligne_reg_frns = rv_ligne_reg_frns  ;
        this.rv_detail_reg_frns = rv_detail_reg_frns  ;
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

                String query_detail_reg_client = " select NumeroReglementFournisseur ,CodeModeReglement  , Reference  ,Echeance , MontantRecu  \n" +
                        "from DetailReglementFournisseur  where NumeroReglementFournisseur  = '"+NumeroRegFournisseur+"' " ;

                Log.e("query_detail_reg_client", query_detail_reg_client);

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query_detail_reg_client);

                while (rs.next()) {

                    String NumeroReglementFournisseur  = rs.getString("NumeroReglementFournisseur");
                    String CodeModeReglement  = rs.getString("CodeModeReglement");
                    String Reference  = rs.getString("Reference");
                    Date Echeance  =dtfSQL.parse( rs.getString("Echeance"));
                    double MontantRecu  = rs.getDouble("MontantRecu");

                    total_reglement  =  total_reglement + MontantRecu ;

                    DetailReglementFournisseur detailReglementFournisseur  = new  DetailReglementFournisseur (NumeroReglementFournisseur ,CodeModeReglement , Reference ,Echeance, MontantRecu ) ;
                    listDetailReglementFournisseurs.add(detailReglementFournisseur) ;

                }


                String query_ligne_reg_client  = "select NumeroPiece , TypePiece ,\n" +
                        "case when TypePiece ='BA' then (select  DateBonLivraisonAchat   from  BonLivraisonAchat  where  NumeroBonLivraisonAchat =  NumeroPiece)\n" +
                        "when TypePiece ='FA' then (select  DateFactureAchat  from  FactureAchat  where  NumeroFactureAchat=  NumeroPiece)\n" +
                        "end   \n" +
                        "as datePiece  \n" +
                        ",MontantPieceTTC ,TotalRecu  ,TotalPayee  --, TotalRetenu\n" +
                        "from LigneReglementFournisseur  where NumeroReglementFournisseur = '"+NumeroRegFournisseur+"' " ;

                Log.e("query_ligne_reg_client",query_ligne_reg_client) ;

                Statement stmt_1 = con.createStatement();
                ResultSet rs_1 = stmt_1.executeQuery(query_ligne_reg_client);

                while (rs_1.next()) {

                    String NumeroPiece  = rs_1.getString("NumeroPiece");
                    String TypePiece  = rs_1.getString("TypePiece");
                    Date datePiece  =dtfSQL.parse( rs_1.getString("datePiece"));
                    Double MontantPieceTTC  = rs_1.getDouble("MontantPieceTTC");
                    Double TotalRecu  = rs_1.getDouble("TotalRecu");
                    Double TotalPayee  = rs_1.getDouble("TotalPayee");
                 //   Double TotalRetenu  = rs_1.getDouble("TotalRetenu");

                    LigneReglementFournisseur ligneReglementFournisseur  = new LigneReglementFournisseur (NumeroPiece ,TypePiece ,datePiece ,MontantPieceTTC ,TotalRecu ,TotalPayee  ) ;
                    listLigneReglementFournisseurs.add(ligneReglementFournisseur) ;


                }
            }
            con.close();

        } catch (Exception ex) {

            res = ex.getMessage();
            Log.e("ERROR_FRNS", res.toString());

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

        LigneReglementFrnsAdapterRV adapterRV = new LigneReglementFrnsAdapterRV(activity , listLigneReglementFournisseurs , "") ;
        rv_ligne_reg_frns.setAdapter(adapterRV);

        DetailReglementfrnsAdapterRV adapterRV1 = new DetailReglementfrnsAdapterRV(activity  , listDetailReglementFournisseurs , "") ;
        rv_detail_reg_frns.setAdapter(adapterRV1);

        //ReglementClientActivity.txt_tot_reglement.setText(decF.format(total_reglement) +" Dt");

    }

}
