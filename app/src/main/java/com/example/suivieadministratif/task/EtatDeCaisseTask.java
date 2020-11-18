package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.CaisseAdapterRV;
import com.example.suivieadministratif.model.Caisse;
import com.example.suivieadministratif.module.caisse.CaisseRecetteActivity;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public class EtatDeCaisseTask extends AsyncTask<String, String, String> {

    String res;

    Activity activity;
    RecyclerView rv_list_caisse ;
    ProgressBar pb_chargement;

    String TypePieceInput  ;
    //TextView txt_tot_liv , txt_tot_retour  , txt_tot_gratuit ,txt_tot_draft_reg  , txt_tot_caisse  ;

    String CodeDepot ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    ArrayList<Caisse> listCaisse = new ArrayList<>()  ;

    SimpleDateFormat  sdf  = new SimpleDateFormat("dd/MM/yyyy") ;



    public EtatDeCaisseTask(Activity activity  , RecyclerView rv_list_caisse , ProgressBar pb_chargement , String TypePieceInput  ) {
        this.activity = activity;

        this.rv_list_caisse = rv_list_caisse  ;
        this.pb_chargement = pb_chargement ;
        this.TypePieceInput =TypePieceInput ;



        SharedPreferences pref = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        base = pref.getString("base", base);
        password = pref.getString("password", password);

        connectionClass = new ConnectionClass();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
         pb_chargement.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);       // Connect to database
            Log.e("con", "" + con);
            if (con == null) {
                res = "Check Your Internet Access!";
            } else {

                String query = "select CodeCompte   ,\n" +
                        "NomCompte ,\n" +
                        "ModeReglement ,\n" +
                        "Solde  , TypePiece from  Vue_RecapRecette  " +
                         "where NomCompte  not like  '%DEPENSE%'     and CodeTypeCompte <> 'I'  " ;


                Log.e("query_list_personnel", query);
                listCaisse.clear();

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                CaisseRecetteActivity.tot_caisse_compte =0 ;
                CaisseRecetteActivity.tot_caisse_user =0  ;


                while (rs.next()) {

                    String CodeCompte  = rs.getString("CodeCompte");
                    String NomCompte  = rs.getString("NomCompte");
                    String ModeReglement  = rs.getString("ModeReglement");
                    Double Solde  = rs.getDouble("Solde");
                    String TypePiece  = rs.getString("TypePiece");

                    if (Solde!= 0) {

                        if (TypePieceInput.equals(TypePiece)) {
                            Caisse caisse = new Caisse(CodeCompte, NomCompte, ModeReglement, Solde, TypePiece);
                            listCaisse.add(caisse);



                        } else if (TypePieceInput.equals("Tout")) {



                            Caisse caisse = new Caisse(CodeCompte, NomCompte, ModeReglement, Solde, TypePiece);
                            listCaisse.add(caisse);
                        }



                        if (TypePiece.equals("Compte")) {

                            CaisseRecetteActivity.tot_caisse_compte = CaisseRecetteActivity.tot_caisse_compte + Solde;
                        } else if (TypePiece.equals("User")) {
                            CaisseRecetteActivity.tot_caisse_user = CaisseRecetteActivity.tot_caisse_user + Solde;

                        }



                    }

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


        pb_chargement.setVisibility(View.INVISIBLE);


        CaisseAdapterRV adapterRV   = new CaisseAdapterRV(activity  , listCaisse) ;
        rv_list_caisse.setAdapter(adapterRV);



        DecimalFormat  fo  = new DecimalFormat("0.000") ;
        CaisseRecetteActivity.txt_tot_caisse_user.setText(fo.format(CaisseRecetteActivity.tot_caisse_user));
        CaisseRecetteActivity.txt_tot_caisse_compte.setText(fo.format(CaisseRecetteActivity.tot_caisse_compte));


    }







}
