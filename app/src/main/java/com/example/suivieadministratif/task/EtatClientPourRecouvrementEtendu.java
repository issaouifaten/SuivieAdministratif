package com.example.suivieadministratif.task;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
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

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.adapter.ClientRecouvAdapterLV;
import com.example.suivieadministratif.dialog.DialogDetailFactureVente;
import com.example.suivieadministratif.dialog.DialogReglementClientEtendu;
import com.example.suivieadministratif.model.ClientRegEtendu;
import com.example.suivieadministratif.model.SoldeClient;
import com.example.suivieadministratif.module.achat.FactureAchat;
import com.example.suivieadministratif.module.achat.LigneFactureAchat;

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

public class EtatClientPourRecouvrementEtendu extends AsyncTask<String, String, String> {

    Activity activity;
    ListView lv_list;
    ProgressBar pb;

    Date  date_debut ; Date date_fin ;
    String CodeClientSelected  ;


    String z = "";
    Boolean test = false;

    double total = 0;

    ConnectionClass connectionClass ;
    String user, password, base, ip ;

    ArrayList<ClientRegEtendu>  listClientReg  = new ArrayList<>() ;


    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    ArrayList<SoldeClient> listSoldeCLient = new ArrayList<>();
    public EtatClientPourRecouvrementEtendu(Activity activity, Date  date_debut , Date date_fin  , ListView lv_list, ProgressBar pb , String CodeClientSelected  ) {
        this.activity = activity;
        this.date_debut = date_debut  ;
        this.date_fin = date_fin  ;
        this.lv_list = lv_list;
        this.pb = pb;
        this.CodeClientSelected=CodeClientSelected;


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


    }

    @Override
    protected String doInBackground(String... params) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);
            if (con == null) {
                z = "Error in connection with SQL server";
            } else {

                String query_solde = "DECLARE\t@return_value int\n" +
                        "\n" +
                        "EXEC\t@return_value = [dbo].[SoldeClients]\n" +
                        "\t\t@DateAu = '" + df.format(new Date()) + "'\n" +
                        "\n" +
                        "SELECT\t'Return Value' = @return_value  ";


                Log.e("query_solde", query_solde);

                Statement stmt_solde = con.createStatement();
                ResultSet rs_solde = stmt_solde.executeQuery(query_solde);

                while ( rs_solde.next() ) {

                    String CodeClient  = rs_solde.getString("CodeClient");
                    double soldeClient = rs_solde.getDouble("TotalDebit") - rs_solde.getDouble("TotalCredit");

                    SoldeClient soldeClient_1 = new SoldeClient(CodeClient, soldeClient);
                    listSoldeCLient.add(soldeClient_1);

                }


                String queryTable = " DECLARE\t@return_value int\n" +
                        "\n" +
                        "EXEC\t@return_value = [dbo].[RecouvrementClientGlobalAndroid]\n" +
                        "\t\t@DateDebut = '"+df.format(date_debut)+"',\n" +
                        "\t\t@DateFin = '"+df.format(date_fin)+"',\n" +
                        "\t\t@CodeClient = N'"+CodeClientSelected+"'\n" +
                        "SELECT\t'Return Value' = @return_value\n" ;

                PreparedStatement ps = con.prepareStatement(queryTable);
                Log.e("query", queryTable);

                ResultSet rs = ps.executeQuery();
                z = "e";

                String   last_client  = ""  ;
                while (rs.next()) {

                    String  CodeClient  =  rs.getString("CodeClient") ;
                    String  RaisonSociale  =  rs.getString("RaisonSociale") ;

                   //// ClientRegEtendu   c_reg_etendu   = new ClientRegEtendu(CodeClient  , RaisonSociale)  ;


                    if ( !last_client.equals(CodeClient))
                    {
                        last_client = CodeClient ;
                        listClientReg.add(new ClientRegEtendu(CodeClient  , RaisonSociale)) ;
                    }



                    test = true;
                    z = "succees";
                }


            }
        } catch (SQLException ex) {
            z = "tablelist" + ex.toString();
            Log.e("erreur", z);


        }
        return z ;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(String r) {
        pb.setVisibility(View.GONE);

        Log.e("listClient_etendu" +listClientReg.size(), listClientReg.toString()) ;

        for (ClientRegEtendu  c_reg_etendu    : listClientReg  )
        {
            for (SoldeClient sc : listSoldeCLient) {
                if (sc.getCodeClient().equals(c_reg_etendu.getCodeClient())) {
                    c_reg_etendu.setSolde(sc.getSolde());
                    break;
                }
            }
        }

        ClientRecouvAdapterLV  clientRecouvAdapterLV  = new ClientRecouvAdapterLV(activity  ,  listClientReg)  ;
        lv_list.setAdapter(clientRecouvAdapterLV);

        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ClientRegEtendu   clientSelected  =  listClientReg.get(position) ;
                Log.e("clientSelected",""+clientSelected.toString()) ;

                final FragmentManager fm = activity.getFragmentManager();
                DialogReglementClientEtendu dialog = new  DialogReglementClientEtendu( );
                dialog.setRaisonSociale(clientSelected.getRaisonSociale());
                dialog.setCodeClient(clientSelected.getCodeClient());
                dialog.setDate_debut(df.format(date_debut));
                dialog.setDate_fin(df.format(date_fin));
                dialog.show(fm, "");

            }
        });
    }

}