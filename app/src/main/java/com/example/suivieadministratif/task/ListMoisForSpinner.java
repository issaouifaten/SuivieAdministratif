package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.SpinnerAdapter;
import com.example.suivieadministratif.adapter.SpinnerAnneMoisAdapter;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Graphique.VariationCAEnMoisActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ListMoisForSpinner extends AsyncTask<String, String, String> {

    Connection con;
    String res;

    Activity activity;
    Spinner sp_mois;
    String annee ;
    String param  ;
    String  origine  ;

    ArrayList<String> listMois = new ArrayList<>();
    ArrayList<String> listLibelleMois = new ArrayList<>();

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateFormat dfSQL = new SimpleDateFormat("dd/MM/yyyy");

    ConnectionClass connectionClass;
    String user, password, base, ip;


    public ListMoisForSpinner(Activity activity, Spinner sp_mois, String annee,  String param   ,  String  origine ) {
        this.activity = activity;
        this.sp_mois = sp_mois;
        this.annee = annee;
        this.param=param  ;
        this.origine = origine  ;

        SharedPreferences prefe = activity.getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);

        connectionClass = new ConnectionClass();
    }


    //  lancement  de progress dialog
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //  txt_client.setHint("Patientez SVP ...");

    }


    //  donwnload  data
    @Override
    protected String doInBackground(String... strings) {


        try {

            Connection con = connectionClass.CONN(ip, password, user, base);       // Connect to database
            Log.e("con", "" + con);
            if (con == null) {
                res = "Check Your Internet Access ! ";
            } else {

                // AjoutDemandeInterventionAdmin

                String query = " \n" +
                        "   select  distinct  ( MONTH ( DatePiece ) )  as mois  from   dbo.Vue_ListeVenteGlobal  where  YEAR ( DatePiece) = '"+annee+"'\n" +
                        "    order by  mois";

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query);

                listMois.clear();
                listLibelleMois.clear();
                listMois.add("0");
                listLibelleMois.add("Mois");

                while (rs.next()) {

                    String mois = rs.getString("mois");
                    String LibelleMois = "";
                    //1 Janvier
                    // 2 Février
                    // 3 Mars
                    // 4 Avril
                    // 5 Mai
                    // 6 Juin
                    // 7 Juillet
                    // 8  Août
                    // 9  Septembre
                    // 10 Octobre
                    // 11 Novembre
                    // 12 Décembre

                    switch (mois) {

                        case "1":
                            LibelleMois="Janvier";
                            break;
                        case "2":
                            LibelleMois="Février";
                            break;
                        case "3":
                            LibelleMois="Mars";
                            break;
                        case "4":
                            LibelleMois="Avril";
                            break;
                        case "5":
                            LibelleMois="Mai";
                            break;
                        case "6":
                            LibelleMois="Juin";
                            break;
                        case "7":
                            LibelleMois="Juillet";
                            break;
                        case "8":
                            LibelleMois="Août";
                            break;
                        case "9":
                            LibelleMois="Septembre";
                            break;
                        case "10":
                            LibelleMois="Octobre";
                            break;
                        case "11":
                            LibelleMois="Novembre";
                            break;
                        case "12":
                            LibelleMois="Décembre";
                            break;

                    }

                    listLibelleMois.add(LibelleMois);
                    listMois.add(mois);

                }

                con.close();
            }

        } catch (Exception ex) {

            res = ex.getMessage();

        }

        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        SpinnerAnneMoisAdapter adapter = new SpinnerAnneMoisAdapter(activity, listLibelleMois);
        sp_mois.setAdapter(adapter);


        sp_mois.setSelection(listMois.size() - 1);

        if (origine.equals("Debut"))
        {
            VariationCAEnMoisActivity.mois_debut_selected = listMois.get( listMois.size() - 1) ;
        }
        else  if (origine .equals("Fin"))
        {
            VariationCAEnMoisActivity.mois_fin_selected = listMois.get( listMois.size() - 1) ;
        }


        Log.e("Period_selected" , "du  "+  VariationCAEnMoisActivity.mois_debut_selected  +"/"+ VariationCAEnMoisActivity.annee_debut_selected +"  au  "+  VariationCAEnMoisActivity.mois_fin_selected  +"/"+ VariationCAEnMoisActivity.annee_fin_selected ) ;

        //   etat  here

        sp_mois.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)  {

                if (position > 0) {

                    if (origine.equals("Debut"))
                    {
                        VariationCAEnMoisActivity.mois_debut_selected = listMois.get( position) ;
                    }
                      if (origine .equals("Fin"))
                    {
                        VariationCAEnMoisActivity.mois_fin_selected = listMois.get( position) ;
                    }


                   if (origine.equals("Fin")) {
                       Log.e("Period_selected", "du  " + VariationCAEnMoisActivity.mois_debut_selected + "/" + VariationCAEnMoisActivity.annee_debut_selected
                               + "  au  " + VariationCAEnMoisActivity.mois_fin_selected + "/" + VariationCAEnMoisActivity.annee_fin_selected);
                       //   etat  here


                       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                       DecimalFormat df = new DecimalFormat("00");

                       Date dateDebut = new Date();
                       int mois = Integer.parseInt(VariationCAEnMoisActivity.mois_debut_selected);

                       Log.e("gggg", "01/" + df.format(mois) + "/" + VariationCAEnMoisActivity.annee_debut_selected);

                       try {

                           dateDebut = sdf.parse("01/" + df.format(mois) + "/" + VariationCAEnMoisActivity.annee_debut_selected);

                       } catch (ParseException e) {
                           e.printStackTrace();
                           Log.e("Exep", e.getMessage().toString());

                       }


                       Date dateFin = new Date();
                       int mois_fin = Integer.parseInt(VariationCAEnMoisActivity.mois_fin_selected);
                       try {
                           dateFin = sdf.parse("01/" + df.format(mois_fin) + "/" + VariationCAEnMoisActivity.annee_fin_selected);
                           Calendar cal = Calendar.getInstance();
                           cal.setTime(dateFin);
                           int dayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  // get(Calendar.DAY_OF_MONTH);

                           dateFin = sdf.parse(df.format(dayOfMonth) + "/" + df.format(mois_fin) + "/" + VariationCAEnMoisActivity.annee_fin_selected);

                       } catch (ParseException e) {
                           e.printStackTrace();
                       }

                       Log.e("Period_selected_x", "du  " + sdf.format(dateDebut) + "  au  " + sdf.format(dateFin));

                       EtatChiffreAffaireEnMoisTuask etatChiffreAffaireEnMoisTuask  = new EtatChiffreAffaireEnMoisTuask(activity  ,VariationCAEnMoisActivity.barGraph  ,dateDebut , dateFin ,VariationCAEnMoisActivity.pb, param)  ;
                       etatChiffreAffaireEnMoisTuask.execute() ;

                   }
                   else  if (origine .equals("Debut"))
                   {


                       Log.e("Period_selected", "du  " + VariationCAEnMoisActivity.mois_debut_selected + "/" + VariationCAEnMoisActivity.annee_debut_selected + "  au  " + VariationCAEnMoisActivity.mois_fin_selected + "/" + VariationCAEnMoisActivity.annee_fin_selected);
                       //   etat  here


                       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                       DecimalFormat df = new DecimalFormat("00");

                       Date dateDebut = new Date();
                       int mois = Integer.parseInt(VariationCAEnMoisActivity.mois_debut_selected);

                       Log.e("gggg", "01/" + df.format(mois) + "/" + VariationCAEnMoisActivity.annee_debut_selected);

                       try {

                           dateDebut = sdf.parse("01/" + df.format(mois) + "/" + VariationCAEnMoisActivity.annee_debut_selected);

                       } catch (ParseException e) {
                           e.printStackTrace();
                           Log.e("Exep", e.getMessage().toString());

                       }


                       Date dateFin = new Date();
                       try {
                           int mois_fin = Integer.parseInt(VariationCAEnMoisActivity.mois_fin_selected);
                           try {
                               dateFin = sdf.parse("01/" + df.format(mois_fin) + "/" + VariationCAEnMoisActivity.annee_fin_selected);
                               Calendar cal = Calendar.getInstance();
                               cal.setTime(dateFin);
                               int dayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  // get(Calendar.DAY_OF_MONTH);

                               dateFin = sdf.parse(df.format(dayOfMonth) + "/" + df.format(mois_fin) + "/" + VariationCAEnMoisActivity.annee_fin_selected);

                           } catch (ParseException e) {
                               e.printStackTrace();
                           }

                           Log.e("Period_selected_x", "du  " + sdf.format(dateDebut) + "  au  " + sdf.format(dateFin));

                           EtatChiffreAffaireEnMoisTuask etatChiffreAffaireEnMoisTuask  = new EtatChiffreAffaireEnMoisTuask(activity  ,VariationCAEnMoisActivity.barGraph  ,dateDebut , dateFin ,VariationCAEnMoisActivity.pb , param)  ;
                           etatChiffreAffaireEnMoisTuask.execute() ;
                       }
                       catch (Exception  ex)
                       {

                       }


                   }





                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

    }

}
