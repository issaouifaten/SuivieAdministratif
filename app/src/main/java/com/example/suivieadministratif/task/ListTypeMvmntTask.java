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
import com.example.suivieadministratif.adapter.TypeMvmntAdapterLV;
import com.example.suivieadministratif.model.TypeMouvement;
import com.example.suivieadministratif.model.TypeMouvementClick;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListTypeMvmntTask extends AsyncTask<String, String, String> {


    Activity activity;
    String date_debut, date_fin;
    ListView lv_type_mvmnt;
    ArrayList<TypeMouvementClick> listChoixTypeMvmnt;
    ArrayList<TypeMouvement> listTypeMvmnt = new ArrayList<>();
    ProgressBar pb;


    String z = "";
    ConnectionClass connectionClass;
    String user, password, base, ip;

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ListTypeMvmntTask(Activity activity, String date_debut, String date_fin, ListView lv_type_mvmnt, ArrayList<TypeMouvementClick> listChoixTypeMvmnt, ProgressBar pb) {
        this.activity = activity;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.lv_type_mvmnt = lv_type_mvmnt;
        this.listChoixTypeMvmnt = listChoixTypeMvmnt;
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

                String CONDITION_type = "( ";

                for (TypeMouvementClick t : listChoixTypeMvmnt) {
                    if (t.getNbrClick() == 1)

                        CONDITION_type = CONDITION_type + " 'Type Mouvement " + t.getLibelle().trim() + "' , ";

                }

                CONDITION_type = CONDITION_type + " ''  )";

                String query_type_distinct = " select  Numero , Date  , Nom ,Montant ,Type  from TypeMouvement\n" +
                        " where CONVERT (Date ,Date)  between  '" + date_debut + "'  and  '" + date_fin + "'\n" +
                        " and Type in   " + CONDITION_type + "\n" +
                        " Order BY Type\n ";


                Log.e("query_type_mvmnt", "" + query_type_distinct);
                PreparedStatement ps = con.prepareStatement(query_type_distinct);
                ResultSet rs = ps.executeQuery();


                while (rs.next()) {

                    String Numero = rs.getString("Numero");
                    Date Date = dtfSQL.parse(rs.getString("Date"));
                    String Nom = rs.getString("Nom");
                    String Montant = rs.getString("Montant");

                    double mnt = 0;
                    try {
                        mnt = Double.parseDouble(Montant);

                    } catch (Exception ex) {
                        Log.e("Eror_converstion_mnt", ex.getMessage().toString());
                    }
                    String Type = rs.getString("Type");

                    TypeMouvement typeMouvement = new TypeMouvement(Numero, Date, Nom, mnt, Type);
                    listTypeMvmnt.add(typeMouvement);

                }

                z = "Success";
            }
        } catch (Exception ex) {
            z = "Error retrieving data from table";
            Log.e("ERROR_type_mvmnt", "" + ex.getMessage().toString());
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r) {

        pb.setVisibility(View.INVISIBLE);


        try {


            ArrayList<TypeMouvementClick> listTypeDistinct= new ArrayList<>() ;

            for (TypeMouvementClick t : listChoixTypeMvmnt) {
                if (t.getNbrClick() == 1)
                    listTypeDistinct.add(t) ;
            }


            Log.e("listTypeDistinct", listTypeDistinct.size()+""+listTypeDistinct.toString()) ;

           /* for (TypeMouvementClick t : listChoixTypeMvmnt) {
            }*/

           /* for (TypeMouvement tm : listTypeMvmnt) {
                if (!listTypeDistinct.contains(tm.getType()))
                    listTypeDistinct.add(tm.getType());
            }*/

            if (listTypeMvmnt.size() > 0  &&  listTypeDistinct.size() >0) {


                listTypeMvmnt.add(0, new TypeMouvement("", new Date(), "", listTypeDistinct.get(0).getTotal_montant(), listTypeDistinct.get(0).getLibelle()));
                listTypeDistinct.remove(0);



                for (int i = 0; i < listTypeMvmnt.size(); i++) {

                    if ( i < ( listTypeMvmnt.size() - 1)) {

                        if (!listTypeMvmnt.get(i).getType().replace("Type Mouvement","").trim()  . equals(listTypeMvmnt.get(i + 1).getType().replace("Type Mouvement","").trim())) {
                            Log.e("condition_satisfait"," "+listTypeMvmnt.get(i).getType()+  " ** "+listTypeMvmnt.get(i+1).getType()) ;

                            listTypeMvmnt.add( (i + 1), new TypeMouvement("", new Date(), "", listTypeDistinct.get(0).getTotal_montant(), listTypeDistinct.get(0).getLibelle()));
                            listTypeDistinct.remove(0);

                        }

                    }

                }

            }






            for (TypeMouvement tm : listTypeMvmnt) {

                Log.e("TYPE_MVMNT", tm.toString());

            }

            Log.e("TypeMVMNT", listChoixTypeMvmnt.toString());
        } catch (Exception ex) {

            Log.e("type_mvmnt_Error", ex.getMessage().toString());
        }

        TypeMvmntAdapterLV adapterLV = new TypeMvmntAdapterLV(activity, listTypeMvmnt);
        lv_type_mvmnt.setAdapter(adapterLV);

    }

}