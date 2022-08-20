package com.example.suivieadministratif.task;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.ClientRecouvAdapterLV;
import com.example.suivieadministratif.adapter.RegClientEtenduELVAdapter;
import com.example.suivieadministratif.adapter.RegEtenduClientELVAdapter;
import com.example.suivieadministratif.dialog.DialogReglementClientEtendu;
import com.example.suivieadministratif.model.ClientRegEtendu;
import com.example.suivieadministratif.model.PieceRecouvrementEtendu;
import com.example.suivieadministratif.model.SoldeClient;
import com.example.suivieadministratif.model.TypePiece;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EtatRecouvrementEtenduParClient extends AsyncTask<String, String, String> {

    Activity activity;

    ExpandableListView elv_list;
    ProgressBar pb;

    String date_debut;
    String date_fin;
    String CodeClientSelected;

    String z = "";
    Boolean test = false;

    double total = 0;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    ArrayList<PieceRecouvrementEtendu> listPieceRecouvEntendu = new ArrayList<>();

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public EtatRecouvrementEtenduParClient(Activity activity, String date_debut, String date_fin, ExpandableListView elv_list, ProgressBar pb, String CodeClientSelected) {
        this.activity = activity;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.elv_list = elv_list;
        this.pb = pb;
        this.CodeClientSelected = CodeClientSelected;


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


                String queryTable = " DECLARE\t@return_value int\n" +
                        "\n" +
                        "EXEC\t@return_value = [dbo].[RecouvrementClientGlobalAndroid]\n" +
                        "\t\t@DateDebut = '"+ date_debut +"',\n" +
                        "\t\t@DateFin = '"+ date_fin +"',\n" +
                        "\t\t@CodeClient = N'"+CodeClientSelected+"'\n" +
                        "SELECT\t'Return Value' = @return_value\n" ;

                PreparedStatement ps = con.prepareStatement(queryTable);
                Log.e("query", queryTable);

                ResultSet rs = ps.executeQuery();
                z = "e";

                while (rs.next()) {

                    String CodeClient = rs.getString("CodeClient");
                    int OrdreAffichage = rs.getInt("OrdreAffichage");
                    String RaisonSociale = rs.getString("RaisonSociale");

                    String NumeroPiece = rs.getString("NumeroPiece");
                    Date datePiece = dtfSQL.parse(rs.getString("DatePiece"));

                    String libelleAffichage = rs.getString("LibelleAffichage");
                    double montant = rs.getDouble("Debit");
                    double montantRegle = rs.getDouble("Credit");

                    double RestantDue = montant - montantRegle;

                    double MargeDt = rs.getDouble("MargeFacture");
                    double MargePourcent = rs.getDouble("PourcentageMarge");


                    ///if (CodeClient.equals(CodeClientSelected))
                        listPieceRecouvEntendu.add(new PieceRecouvrementEtendu
                                (CodeClient, OrdreAffichage, NumeroPiece, datePiece, libelleAffichage, montant, montantRegle, RestantDue, MargeDt, MargePourcent));


                    test = true;
                    z = "succees";
                }


            }
        } catch (SQLException | ParseException ex) {
            z = "tablelist" + ex.toString();
            Log.e("erreurRECET", z);


        }
        return z;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(String r) {
        pb.setVisibility(View.GONE);

        Log.e("REG_EYTENDU", listPieceRecouvEntendu.size() + "");

        ArrayList<TypePiece> listEnteteTypePiece = new ArrayList<>();



        int last_type = 0;

        for (PieceRecouvrementEtendu pre : listPieceRecouvEntendu) {
            if (last_type != pre.getOrderAffichage()) {

                last_type = pre.getOrderAffichage();

                ArrayList<PieceRecouvrementEtendu> listPieceParType = new ArrayList<>();
                double  total_mnt = 0 ;
                String libelle = "";
                if (pre.getOrderAffichage() == 1)
                    libelle = "Recouvrement";

                else if (pre.getOrderAffichage() == 2)
                    libelle = "Impayés";

                else if (pre.getOrderAffichage() == 3)
                    libelle = "BL + RV Non Facturé";

                else if (pre.getOrderAffichage() == 4)
                    libelle = "Engagements Chéque";

                else if (pre.getOrderAffichage() == 5)
                    libelle = "Engagements Traite";
                else
                    libelle = "";

                listPieceParType.add(pre);

                total_mnt +=  pre.getMontant() ;
                TypePiece typePiece = new TypePiece(pre.getCodeClient(), pre.getOrderAffichage(), libelle);
                typePiece.setListPieceRecouvEtendu(listPieceParType);

                typePiece.setTotal_mnt(total_mnt);


                listEnteteTypePiece.add(typePiece);

                Log.e("type_x ", typePiece.getLibelle()+"");
                Log.e("type_x ", typePiece.getListPieceRecouvEtendu().size()+"");
                Log.e("type_x ", typePiece.getListPieceRecouvEtendu().toString()+"");

                Log.e("type_x ",  "-------********------");
            }
        }


        Log.e("reg_etendu ", listEnteteTypePiece.toString() + "");
        //  adapter  here

        RegEtenduClientELVAdapter elvAdapter = new RegEtenduClientELVAdapter (activity, listEnteteTypePiece);
        elv_list.setAdapter(elvAdapter);


    }


}