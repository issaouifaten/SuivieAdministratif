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
import com.example.suivieadministratif.model.Contact;
import com.example.suivieadministratif.model.ResponsableAdministration;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatSRMFragment;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListResponsableTaskForSearchableSpinner extends AsyncTask<String,String,String> {

    Connection con;
    String res ;

    Activity activity  ;
    SearchableSpinner sp_responsable ;

    String origine  ;

    ArrayList<String> listNomResponsable = new ArrayList<>() ;
    ArrayList<ResponsableAdministration> listResponsable = new ArrayList<ResponsableAdministration>() ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    public ListResponsableTaskForSearchableSpinner(Activity activity , SearchableSpinner sp_responsable ,  String origine) {
        this.activity = activity  ;
        this.sp_responsable = sp_responsable  ;
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

                String query = "   select CodeRespensable ,Nom  from  Respensable " +
                        "\n   where 1 =1  "+CONDIION +
                        "\n Order by CodeRespensable  "
                     ;

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query) ;


                listResponsable.clear();
                listNomResponsable.clear();

                listResponsable.add(new ResponsableAdministration("" ,"Tout les Responsables")) ;
                listNomResponsable.add("Tout les Responsables")  ;


                while ( rs.next() ) {

                    String CodeRespensable = rs.getString("CodeRespensable") ;
                    String Nom =rs.getString("Nom") ;

                    ResponsableAdministration responsable  = new ResponsableAdministration(CodeRespensable ,Nom) ;

                    listResponsable.add(new ResponsableAdministration( CodeRespensable  , Nom))  ;
                    listNomResponsable.add(Nom) ;



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

        SpinnerAdapter adapter = new SpinnerAdapter(activity  , listNomResponsable)  ;
        sp_responsable.setAdapter(adapter);
        sp_responsable.setSelection(0);

        sp_responsable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("Responsable_selected"  ,""+listResponsable.get(position).toString())  ;

                if (origine.equals("dialogSuivieRelationFournisseur"))
                {
                     StatSRMFragment.CodeRepresentant_selected = listResponsable.get(position).getCodeResponsable() ;

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
        );

    }


}
