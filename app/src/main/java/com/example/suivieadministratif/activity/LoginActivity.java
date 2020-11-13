package com.example.suivieadministratif.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.MenuServeur;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.param.Parametrage;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity
{

    private TextInputLayout ed_login , ed_password ;
    Button btn_login ;
    final Context co = this;
    ConnectionClass connectionClass;
    
    String user, password, base, ip;
   
    String    NomUtilisateur,   CodeEmployer ;
    boolean st = false;
    String NomSociete = "",MotDePasse="";
    Boolean Actif = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow() . setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);


        btn_login = findViewById(R.id.btn_login);
        ed_login = findViewById(R.id.login);
        ed_password = findViewById(R.id.password);


        connectionClass = new ConnectionClass();

        btn_login = (Button) findViewById(R.id.btn_login);
        //  pbbar = (ProgressBar) findViewById(R.id.pbbar);



        final Context co = this;
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!valideLogin() | !valideMtp()) {
                    return;
                }
                String _login = ed_login.getEditText().getText().toString();
                String _mot_de_passe = ed_password.getEditText().getText().toString();

                DoLogin doLogin = new DoLogin(LoginActivity.this , _login, _mot_de_passe,btn_login);
                doLogin.execute("");


            }
        });


        //********************************************************************////////////
        //********************************************************************////////////


    }


    private Boolean valideLogin() {
        String val = ed_login.getEditText().getText().toString();
        if (val.isEmpty()) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
//set icon
                    .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                    .setTitle("alert")
//set message
                    .setMessage("champ mail est vide")
//set positive button
//set negative button
                    .setNegativeButton("okey", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what should happen when negative button is clicked
                            Toast.makeText(getApplicationContext(), "Nothing Happened", Toast.LENGTH_LONG).show();
                        }
                    })
                    .show();
            ed_login.setError("le champ ne peut pas être vide");
            return false;
        } else {
            ed_login.setError(null);
            ed_login.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean valideMtp() {
        String val = ed_password.getEditText().getText().toString();
        if (val.isEmpty()) {

            AlertDialog alertDialog = new AlertDialog.Builder(this)
//set icon
                    .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                    .setTitle("alert")
//set message
                    .setMessage("champ mot de passe est vide")
//s
//set negative button
                    .setNegativeButton("okey", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what should happen when negative button is clicked
                            Toast.makeText(getApplicationContext(), "Nothing Happened", Toast.LENGTH_LONG).show();
                        }
                    })
                    .show();

            ed_password.setError("le champ ne peut pas être vide");
            return false;
        } else {
            ed_password.setError(null);
            ed_password.setErrorEnabled(false);
            return true;
        }
    }



    public class DoLogin extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;


        Activity activity ;
        String _login;
        String _password;
        Button btn_connexion   ;

        public DoLogin(Activity activity, String _login, String _password, Button btn_connexion) {
            this.activity = activity;
            this._login = _login;
            this._password = _password;
            this.btn_connexion = btn_connexion;



            SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
            SharedPreferences.Editor edt = pref.edit();
            user = pref.getString("user", user);
            ip = pref.getString("ip", ip);
            password = pref.getString("password", password);
            base = pref.getString("base", base);
            connectionClass = new ConnectionClass();


        }




        @Override
        protected void onPreExecute() {
            btn_connexion.setText("Connexion en cours ...");
            btn_connexion.setEnabled(false);
        }

        @Override
        protected void onPostExecute(String r) {

            Toast.makeText(activity, r+Actif, Toast.LENGTH_SHORT).show();
            if (Actif) {
                if (isSuccess) {

                    SharedPreferences prefs = activity.getSharedPreferences(Param.PREF_USER, Context.MODE_PRIVATE);
                    SharedPreferences.Editor edt = prefs.edit();
                    edt.putBoolean("etat", true);
                    edt.putString("NomUtilisateur", NomUtilisateur);
                    edt.putString("MotDePasse", MotDePasse);
                    edt.putString("NomSociete", NomSociete);

                    edt.commit();

                    Intent i = new Intent(LoginActivity.this,  MenuServeur.class);
                    startActivity(i);
                    finish();
                    Toast.makeText(getApplicationContext(), NomUtilisateur, Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getApplicationContext(), "Verifiez vos Données", Toast.LENGTH_SHORT).show();
                    btn_connexion.setText("Connexion");
                    btn_connexion.setEnabled(true);

                }
            } else {


                AlertDialog.Builder alt = new AlertDialog.Builder(co);

                alt.setIcon(R.drawable.i2s);
                alt.setTitle("Erreur License    " );
                alt.setMessage("Prière de contacter IDEAL SOFTWARE SOLUTION sur\nTel : 74 440 602 \nMail : sales@ideal2s.com) ");
                alt.setCancelable(false);
                alt.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                AlertDialog d = alt.create();
                d.show();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            if (_login.trim().equals("") || password.trim().equals(""))
                z = "Please enter User Id and Password";
            else {
                try {
                    Log.e("DoLogin" ,"connectionClass"+ip+ " "+base) ;
                    Connection con = connectionClass.CONN(ip, password, user, base);
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        String query = "SELECT  *, (select RaisonSociale from Societe) as NomSociete from Utilisateur  " +
                                " where Utilisateur.NomUtilisateur='" + _login + "' and MotDePasse='" + _password + "'";

                        Log.e("query_login",query);
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);


                        if (rs.next()) {
                            boolean TEST = false;

                            NomUtilisateur = rs.getString("NomUtilisateur");
                            NomSociete = rs.getString("NomSociete");
                            Actif = rs.getBoolean("Actif");
                            Log.e("Actif",""+Actif);
                            MotDePasse = rs.getString("MotDePasse");

                            CodeEmployer = "";
                            isSuccess = true;
                            z = "Login avec succée";

                        }


                    }
                } catch (SQLException ex) {
                    isSuccess = false;
                    // z = ex.toString();
                }
            }
            return z;
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menuparametre, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            LayoutInflater li = LayoutInflater.from(co);
            View px = li.inflate(R.layout.print, null);
            final AlertDialog.Builder alt = new AlertDialog.Builder(co);
            alt.setIcon(R.drawable.i2s);
            alt.setTitle("Parametre");
            alt.setView(px);

            connectionClass = new ConnectionClass();

            final EditText edtuserid = (EditText) px.findViewById(R.id.edtuserid);
            final EditText edtpass = (EditText) px.findViewById(R.id.edtpass);

            alt.setCancelable(false)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface di, int i) {

                                    if (edtuserid.getText().toString().equals("admin") && edtpass.getText().toString().equals("admin")) {
                                        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor edt = pref.edit();
                                        edt.putBoolean("etatsql", false);
                                        edt.commit();
                                        Intent inte = new Intent(getApplicationContext(), Parametrage.class);
                                        startActivity(inte);
                                    } else {


                                        Toast.makeText(getApplicationContext(), "Erreur login", Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                    .setNegativeButton("Annuler",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface di, int i) {
                                    di.cancel();
                                }
                            });
            final AlertDialog d = alt.create();


            d.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    //   d.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundResource(R.drawable.bt);
                    //  d.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundResource(R.drawable.bt);


                }
            });

            d.show();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
