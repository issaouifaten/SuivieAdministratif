package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.EcheanceClientAdapterLV;
import com.example.suivieadministratif.adapter.WorkFlowAdapterRV;
import com.example.suivieadministratif.model.EcheanceClient;
import com.example.suivieadministratif.model.WorkFlow;
import com.example.suivieadministratif.module.reglementClient.RapportEcheanceClientActivity;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.recyclerview.widget.RecyclerView;

public class WorkFlowTask extends AsyncTask<String, String, String> {


    Activity activity;

    RecyclerView rv_work_flow;
    ProgressBar pb;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    String NomUtilisateur;
    Date  date_debut , date_fin  ;
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<WorkFlow> listWorkFlow = new ArrayList<>();

    double tot_echeance = 0 ;

    public WorkFlowTask(Activity activity, Date  date_debut , Date date_fin  , RecyclerView rv_work_flow, ProgressBar pb ) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.rv_work_flow = rv_work_flow;
        this.pb = pb;


        SharedPreferences prefe = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        user = prefe.getString("user", user);
        ip = prefe.getString("ip", ip);
        password = prefe.getString("password", password);
        base = prefe.getString("base", base);


        Log.e("BON_CMD" ,Param.PEF_SERVER +"-"+ip+"-"+base) ;

        //user session
        SharedPreferences prefeuser = activity.getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefeuser.edit();
        NomUtilisateur = prefeuser.getString("NomUtilisateur", NomUtilisateur);


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


                String condition_user1="(WorkFlow.UtilisateurConserner1='"+NomUtilisateur+"' and Vue1=0  and  GETDATE() between DateDebut1 and DateFin1 )";
                String condition_user2="(WorkFlow.UtilisateurConserner2='"+NomUtilisateur+"' and Vue2=0  and  GETDATE() between DateDebut2 and DateFin2 )";
                String condition_user3="(WorkFlow.UtilisateurConserner3='"+NomUtilisateur+"' and Vue3=0  and  GETDATE() between DateDebut3 and DateFin3 )";


                String query_work_flow = "  select NumeroWorkflow ,WorkFlow.CodeEvenement ,\n" +
                        " EvenementWorkflow.Libelle ,  DateCreation ,CreerPar ,NumeroPiece ,\n" +
                        " WorkFlow. UtilisateurConserner1 ,Vue1  ,DateDebut1 , DateFin1 ,\n" +
                        " WorkFlow. UtilisateurConserner2, Vue2 , DateVue2 , DateDebut2 ,DateFin2 ,\n" +
                        " WorkFlow.UtilisateurConserner3 ,Vue3 ,DateVue3 ,DateDebut3 , DateFin3 ,\n" +
                        " UtilisateurValideur , DateFin , Fini\n" +
                        " from  WorkFlow\n" +
                        " left  join  EvenementWorkflow on  EvenementWorkflow.CodeEvenement =  WorkFlow.CodeEvenement " +
                        "  where Fini=0 and ( "+condition_user1+" or "+condition_user2+" or "+condition_user3+"  )";


                Log.e("query_work_flow",""+query_work_flow) ;
                PreparedStatement ps = con.prepareStatement(query_work_flow);
                ResultSet rs = ps.executeQuery();


                while (rs.next()) {

                    int  NumeroWorkflow = rs.getInt("NumeroWorkflow");
                    String CodeEvenement = rs.getString("CodeEvenement");
                    String Libelle = rs.getString("Libelle");


                    Date DateCreation =null ;
                    try { DateCreation =dtfSQL.parse( rs.getString("DateCreation")); }
                    catch (Exception ex )
                    { Log.e("ERROR_date_creation",""+ex.getMessage().toString()) ; }





                    String CreerPar = rs.getString("CreerPar");
                    String NumeroPiece = rs.getString("NumeroPiece");

                    String UtilisateurConserner1 = rs.getString("UtilisateurConserner1");
                    int Vue1 = rs.getInt("Vue1");

                    Date DateDebut1 = new Date() ;
                    Date DateFin1 = new Date() ;
                    try { DateDebut1 = dtfSQL.parse(rs.getString("DateDebut1"));
                          DateFin1 = dtfSQL.parse(rs.getString("DateFin1")); }
                  catch (Exception  ex)
                  {  Log.e("ERROR_date_d_f_1",""+ex.getMessage().toString()) ;  }



                    String UtilisateurConserner2 = rs.getString("UtilisateurConserner2");
                    int Vue2 = rs.getInt("Vue2");

                    Date DateVue2 = null ;
                    Date DateDebut2= null ;
                    Date DateFin2= null ;

                    try { DateVue2 = dtfSQL.parse(rs.getString("DateVue2")); }
                    catch (Exception  ex)
                    { Log.e("ERROR_date_vue_2",""+ex.getMessage().toString()) ; }



                    try {  DateDebut2 = dtfSQL.parse(rs.getString("DateDebut2")); }
                    catch (Exception  ex)
                    { Log.e("ERROR_date_d_2",""+ex.getMessage().toString()) ; }



                    try { DateFin2 =dtfSQL.parse( rs.getString("DateFin2"));    }
                    catch (Exception  ex)
                    {  Log.e("ERROR_date_f2",""+ex.getMessage().toString()) ; }



                    String UtilisateurConserner3 = rs.getString("UtilisateurConserner3");
                    int Vue3 = rs.getInt("Vue3");


                    Date DateVue3= null ;
                    Date DateDebut3= null ;
                    Date DateFin3= null ;

                    try {  DateVue3 = dtfSQL.parse(rs.getString("DateVue3")); }
                    catch (Exception ex)
                    { Log.e("ERROR_date_vue_3",""+ex.getMessage().toString()) ; }




                    try { DateDebut3 =  dtfSQL.parse(rs.getString("DateDebut3")); }
                    catch (Exception ex)
                    { Log.e("ERROR_debut_3",""+ex.getMessage().toString()) ; }


                    try {   DateFin3 = dtfSQL.parse( rs.getString("DateFin3")); }
                    catch (Exception ex)
                    { Log.e("ERROR_fin_3",""+ex.getMessage().toString()) ;   }




                    String UtilisateurValideur = rs.getString("UtilisateurValideur");


                    Date  DateFin = null ;
                    try {
                          DateFin =dtfSQL.parse( rs.getString("DateFin"));
                    }
                    catch (Exception  ex)
                    {
                        Log.e("ERROR_date_fin",""+ex.getMessage().toString()) ;
                    }


                    int Fini = rs.getInt("Fini");

                    WorkFlow workFlow = new WorkFlow(NumeroWorkflow ,CodeEvenement ,Libelle ,DateCreation ,CreerPar,NumeroPiece ,UtilisateurConserner1 ,
                            Vue1 , DateDebut1 , DateFin1 ,UtilisateurConserner2 ,Vue2 ,DateVue2 ,DateDebut2 ,DateFin2 ,
                            UtilisateurConserner3 ,Vue3 ,DateVue3 ,DateDebut3 ,DateFin3 ,UtilisateurValideur ,DateFin ,Fini) ;

                    listWorkFlow.add(workFlow) ;
                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
            Log.e("ERROR_work_flow" ,""+ex.getMessage().toString()) ;
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);

        DecimalFormat   decF  = new DecimalFormat("0.000") ;
        Log.e("listWorkFlow",""+listWorkFlow.size()) ;
        Log.e("listWorkFlow",""+listWorkFlow.toString()) ;

        WorkFlowAdapterRV  workFlowAdapterRV = new WorkFlowAdapterRV(activity  ,listWorkFlow) ;
        rv_work_flow.setAdapter(workFlowAdapterRV);

    }


}