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
import com.example.suivieadministratif.adapter.ImpayeClientAdapterLV;
import com.example.suivieadministratif.adapter.ImpayeFournisseurAdapterLV;
import com.example.suivieadministratif.adapter.ImpayeFournisseurExtensibleAdapter;
import com.example.suivieadministratif.model.ImpayeClient;
import com.example.suivieadministratif.model.ImpayeClientEntete;
import com.example.suivieadministratif.model.ImpayeFournisseur;
import com.example.suivieadministratif.model.ImpayeFournisseurEntete;
import com.example.suivieadministratif.module.tresorerie.ImpayeClientActivity;
import com.example.suivieadministratif.module.tresorerie.ImpayeFournisseurActivity;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ImpayeFournisseurTask extends AsyncTask<String, String, String> {

    Activity activity;

    String  date_debut , date_fin  ;
    String  CodeFournisseur ;
    String  mnt_min_max  ;
    double  mnt_min  ;
    double  mnt_max ;
    String  ConditionEtat  ;

    ExpandableListView elv_list  ;
    ProgressBar pb;

    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<ImpayeFournisseur> listImpayeFournisseur  = new ArrayList<>();

    double   tot_impayer =0  ;


    public ImpayeFournisseurTask(Activity activity, String date_debut, String date_fin, String codeFournisseur, String mnt_min_max, double mnt_min, double mnt_max, String conditionEtat, ExpandableListView elv_list, ProgressBar pb) {
        this.activity = activity;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        CodeFournisseur = codeFournisseur;
        this.mnt_min_max = mnt_min_max;
        this.mnt_min = mnt_min;
        this.mnt_max = mnt_max;
        ConditionEtat = conditionEtat;
        this.elv_list = elv_list;
        this.pb = pb;

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
                z = "Error in connection with SQL server";
            } else {

                String  condition  = "" ;

                if (!CodeFournisseur.equals(""))
                {
                    condition += "\n  and  ImpayeFournisseur.CodeFournisseur  = '"+CodeFournisseur+"' "  ;
                }


                condition += "\n and ImpayeFournisseur.DateImpayeFournisseur between    '"+date_debut+"'  and   '"+date_fin+"'   "   ;


                if (mnt_min_max.equals("1"))
                {
                    condition += "\n  and MontantImpayeFournisseur  between  '"+mnt_min+"'  and '"+mnt_max+"'"  ;
                }

                 condition += "\n  "+ConditionEtat  ;


                String query_echeance_fournisseur  = " SELECT ImpayeFournisseur.DateImpayeFournisseur, ImpayeFournisseur.CodeFournisseur,    Fournisseur.RaisonSociale, \n" +
                        "  ImpayeFournisseur.MontantImpayeFournisseur, \n" +
                        "  Ville.DesignationVille,\n" +
                        "  ImpayeFournisseur.NumeroImpayeFournisseur\n" +
                        "    \n" +
                        "    FROM   ((((ImpayeFournisseur ImpayeFournisseur \n" +
                        "    \n" +
                        "    LEFT OUTER JOIN Fournisseur Fournisseur ON ImpayeFournisseur.CodeFournisseur=Fournisseur.CodeFournisseur) \n" +
                        "    LEFT OUTER JOIN SousRegion SousRegion ON Fournisseur.CodeSousRegion=SousRegion.CodeSousRegion)\n" +
                        "    LEFT OUTER JOIN Ville Ville ON Fournisseur.CodeVille=Ville.CodeVille) \n" +
                        "    LEFT OUTER JOIN Respensable RAD ON Fournisseur.CodeRAD=RAD.CodeRespensable) \n" +
                        "    LEFT OUTER JOIN Respensable REP ON Fournisseur.CodeRD=REP.CodeRespensable\n" +
                        "    \n" +
                        "    where  1= 1 \n" +
                             condition  +
                        "   \n    ORDER BY ImpayeFournisseur.CodeFournisseur   " ;

                Log.e("query_impaye_frns",""+query_echeance_fournisseur) ;
                PreparedStatement ps = con.prepareStatement(query_echeance_fournisseur);
                ResultSet rs = ps.executeQuery();


                while (rs.next()) {

                    Date DateImpayeFournisseur = dtfSQL.parse(rs.getString("DateImpayeFournisseur"));

                    String CodeFournisseur = rs.getString("CodeFournisseur");
                    String RaisonSociale = rs.getString("RaisonSociale");

                    double MontantImpayeFournisseur = rs.getDouble("MontantImpayeFournisseur");

                    String DesignationVille = rs.getString("DesignationVille");
                    String NumeroImpayeFournisseur = rs.getString("NumeroImpayeFournisseur");

                    ImpayeFournisseur   impayeFournisseur  = new ImpayeFournisseur( DateImpayeFournisseur ,CodeFournisseur , RaisonSociale ,MontantImpayeFournisseur  ,DesignationVille ,NumeroImpayeFournisseur) ;

                   Log.e("imp_frnsxxx" ,impayeFournisseur.toString() ) ;
                    listImpayeFournisseur.add(impayeFournisseur) ;

                    tot_impayer += MontantImpayeFournisseur ;
                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
            Log.e("ERROR_impaye_frns" ,""+ex.getMessage().toString()) ;
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);


        ArrayList<String> fournisseur_distinct = new ArrayList<>();
        ArrayList<ImpayeFournisseurEntete> list_impaye_frns = new ArrayList<>();

        for (ImpayeFournisseur ifrns : listImpayeFournisseur) {

            if (!fournisseur_distinct.contains(ifrns.getCodeFournisseur()))
                fournisseur_distinct.add(ifrns.getCodeFournisseur());
        }


        for (String code_frns : fournisseur_distinct) {

            ImpayeFournisseurEntete if_entete = new ImpayeFournisseurEntete();
            String raison = "";
            ArrayList<ImpayeFournisseur> list_imp_par_frns = new ArrayList<>();
            double mnt_imp = 0;


            for (ImpayeFournisseur   i_frns  : listImpayeFournisseur)
            {
                if (i_frns.getCodeFournisseur().equals(code_frns))
                {
                    raison = i_frns.getRaisonSociale() ;
                    mnt_imp+= i_frns.getMontantImpayeFournisseur() ;

                    list_imp_par_frns.add(i_frns) ;
                }

            }

            if_entete.setCodeFournisseur(code_frns);
            if_entete.setRaisonSociale(raison);
            if_entete.setMontantImpayeFournisseur(mnt_imp);
            if_entete.setList_imp_frns(list_imp_par_frns);

            list_impaye_frns.add(if_entete) ;
        }


        Log.e("impaye_frns"  , list_impaye_frns.toString())  ;



        ImpayeFournisseurExtensibleAdapter adapter  = new ImpayeFournisseurExtensibleAdapter(activity  , list_impaye_frns) ;
        elv_list.setAdapter(adapter);


        final NumberFormat format = NumberFormat.getNumberInstance(Locale.FRENCH);
        format.setMinimumFractionDigits(3);
        format.setMaximumFractionDigits(3);

        ImpayeFournisseurActivity.txt_total_impaye.setText(format.format(tot_impayer));

    }
}