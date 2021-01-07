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
import com.example.suivieadministratif.model.Fournisseur;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.CommandeFournisseurNonConforme;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatSRMFragment;
import com.example.suivieadministratif.ui.statistique_rapport_activite.importation.SuivieDossierImportationActivity;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListContactTaskForSearchableSpinner extends AsyncTask<String,String,String> {

    Connection con;
    String res ;

    Activity activity  ;
    SearchableSpinner sp_contact ;

    String CodeFournisseur ;
    String origine  ;

    ArrayList<String> listContact = new ArrayList<>() ;
    ArrayList<Contact> listContact_  = new ArrayList<Contact>() ;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    public ListContactTaskForSearchableSpinner(Activity activity , SearchableSpinner sp_contact , String CodeFournisseur ,String origine) {
        this.activity = activity  ;
        this.sp_contact = sp_contact  ;
        this.CodeFournisseur=CodeFournisseur  ;
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

                    if(CodeFournisseur.equals(""))
                    {
                        CONDIION=CONDIION+"" ;
                    }
                    else {

                        CONDIION=CONDIION+" and  ContactFournisseur.CodeFournisseur =  '"+CodeFournisseur+"'" ;

                    }

                }

                String query = "    select   id  , Contact , Fournisseur.RaisonSociale  from  ContactFournisseur \n" +
                        "  inner  join  Fournisseur  on Fournisseur .CodeFournisseur =ContactFournisseur.CodeFournisseur" +
                        "  \n   where 1 =1  "+CONDIION ;

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Log.e("query", query) ;


                listContact.clear();


                    listContact_.add(new Contact("" ,"Tout les Contacts")) ;
                    listContact.add("Tout les Contacts")  ;


                while ( rs.next() ) {

                    String id = rs.getString("id") ;
                    String Contact =rs.getString("Contact") ;
                    String raisonSocial  =rs.getString("RaisonSociale") ;
                    Contact contact  = new Contact(id ,Contact) ;
                    listContact_.add(contact) ;
                    listContact.add(contact.getContact()+ "( "+raisonSocial+" )")  ;
                    Log.e("Fournisseur ", contact.getId() + " - " +contact.getContact()  );

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

        SpinnerAdapter adapter = new SpinnerAdapter(activity  , listContact)  ;
        sp_contact.setAdapter(adapter);
        sp_contact.setSelection(0);

        sp_contact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("Contact_selected"  ,""+listContact_.get(position).toString())  ;

                if (origine.equals("dialogSuivieRelationFournisseur"))
                {
                     StatSRMFragment.CodeContactSelected = listContact_.get(position).getId() ;

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
        );

    }


}
