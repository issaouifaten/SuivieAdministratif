package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.SpinnerAdapter;
import com.example.suivieadministratif.model.MoyenRelation;
import com.example.suivieadministratif.model.ResponsableAdministration;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatSRMFragment;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListMoyenRelationSpinner extends AsyncTask<String,String,String> {

    Connection con;
    String res ;

    Activity activity  ;
    SearchableSpinner sp_relation ;

    String origine  ;

    ArrayList<String> listlibelleRelation  = new ArrayList<>() ;
    ArrayList<MoyenRelation> listRelation  = new ArrayList<MoyenRelation>() ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    public ListMoyenRelationSpinner(Activity activity , SearchableSpinner sp_relation , String origine) {
        this.activity = activity  ;
        this.sp_relation = sp_relation  ;
        this.origine=origine ;


        SharedPreferences pref = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip   = pref.getString("ip", ip);
        base = pref.getString("base", base);
        password = pref.getString("password", password);

        connectionClass = new ConnectionClass();



    }


    //  lancement  de progress dialog
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // txt_client.setHint("Patientez SVP ...");

    }




    //  donwnload  data
    @Override
    protected String doInBackground(String... strings) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);       // Connect to database
            Log.e("con", "" + con);
            if (con == null) {
                res = "Check Your Internet Access!";
            } else {

                String  CONDIION= "  " ;

                if (origine .equals("dialogSuivieRelationFournisseur")) {

                }

                String query = "   select  CodeTypeRelation , Libelle  from  TypeRelation  " +
                        "\n   where 1 =1  "+CONDIION   ;
                     ;

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query) ;


                listlibelleRelation.clear();
                listRelation.clear();

                listRelation.add(new MoyenRelation("" ,"Tout les Relations")) ;
                listlibelleRelation.add("Tout les Relations")  ;


                while ( rs.next() ) {

                    String CodeTypeRelation = rs.getString("CodeTypeRelation") ;
                    String Libelle =rs.getString("Libelle") ;



                    listRelation.add(new MoyenRelation( CodeTypeRelation  , Libelle))  ;
                    listlibelleRelation.add(Libelle) ;



                }
            }
            con.close();

        } catch (Exception ex) {

            res = ex.getMessage();
            Log.e("ERROR", res) ;

        }

        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        SpinnerAdapter adapter = new SpinnerAdapter(activity  , listlibelleRelation)  ;
        sp_relation.setAdapter(adapter);
        sp_relation.setSelection(0);

        sp_relation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("Relation_selected"  ,""+listRelation.get(position).toString())  ;

                if (origine.equals("dialogSuivieRelationFournisseur"))
                {

                    if ( listRelation.get(position).getLibelle().equals("Tout les Relations") )
                    {
                        StatSRMFragment.CodeMoyenRelationSelected= "" ;
                    }
                    else {

                        StatSRMFragment.CodeMoyenRelationSelected = listRelation.get(position).getLibelle() ;
                    }


                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
        );

    }


}
