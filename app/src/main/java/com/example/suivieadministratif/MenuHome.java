package com.example.suivieadministratif;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suivieadministratif.activity.EtatCommande;
import com.example.suivieadministratif.activity.EtatLivraisonActivity;
import com.example.suivieadministratif.activity.EtatRetourActivity;
import com.example.suivieadministratif.activity.LoginActivity;
import com.example.suivieadministratif.fragment.PrincipalFragment;
import com.example.suivieadministratif.param.Parametrage;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MenuHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    ConnectionClass connectionClass;
    String user, password, base, ip, NomUtilisateur = "",NomSociete="";
    final Context co = this;
    ProgressBar pbbar;
    String query_jeux = "";
    TextView txt_titre, txt_version,txt_nomste;
    ImageView img_logo_hannibal;

    GridView gridSituation;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //sql session
        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);
        NomSociete = pref.getString("NomSociete", NomSociete);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

         TextView txt_nom_soc = (TextView) headerView.findViewById(R.id.txt_nom_soc);
        ImageView img_societe = (ImageView) headerView.findViewById(R.id.img_societe);
        /*TextView navDepot = (TextView) headerView.findViewById(R.id.txt_depot_user);*/

        txt_nom_soc.setText( NomSociete);

        if (NomSociete.equals("CCM"))
        {
            img_societe.setImageResource(R.drawable.ic_logo_ccm);
        }
        else  if (NomSociete.equals("GMT"))
        {
            img_societe.setImageResource(R.drawable.ic_logo_gmt);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        drawer.openDrawer(Gravity.LEFT);


        //user session
        SharedPreferences prefe = getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);




        txt_nomste=(TextView)findViewById(R.id.txt_nomste);
        txt_nomste.setText(NomSociete);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Société : " + NomSociete);
        }

        displaySelectedScreen (R.id.nav_principal) ;
    }






    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(  id) ;
        return true;
    }



    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //initializing the fragment object which is selected
        switch (itemId) {

            case R.id.nav_principal:

                fragment = new PrincipalFragment();
                break;

            case R.id.nav_etat_chiffre_affaire:

                Intent intent = new Intent(getApplicationContext(), EtatChiffreAffaire.class);
                startActivity(intent);
                break;

            case R.id.nav_etat_stock_article :
                Intent intent2 = new Intent(getApplicationContext(), StockArticle.class);
                startActivity(intent2);
                break;


            case R.id.nav_etat_globale :

                Intent intent3 = new Intent(getApplicationContext(), ChiffreAffaireGlobale.class);
                startActivity(intent3);
                break;


            case R.id.nav_etat_journal_vente_article :
                Intent intent4 = new Intent(getApplicationContext(), EtatJournalArticleVendu.class);
                startActivity(intent4);

                break;

            case R.id.nav_etat_list_commande :

                Intent intent5 = new Intent(getApplicationContext(), EtatCommande.class);
                startActivity(intent5);
                break;

            case R.id.nav_livraison_vente :
                Intent intent6 = new Intent(getApplicationContext(), EtatLivraisonActivity.class);
                startActivity(intent6);

                break;

            case R.id.nav_retour_vente:
                Intent intent7 = new Intent(getApplicationContext(), EtatRetourActivity.class);
                startActivity(intent7);


            case R.id.nav_deconnexion:
                deconnexion  () ;

            case R.id.nav_param_server:
                Parametrage  () ;

                break;
        }

        //replacing the fragment
        if (fragment != null) {

            ft.replace(R.id.content_frame, fragment);
            ft.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    public   void  deconnexion  ()
    {

        SharedPreferences pref = getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        edt.putBoolean("etat", false);
        edt.commit();

        finish();
        MenuServeur.fa.finish();
        Intent inte = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(inte);

    }

    public  void  Parametrage  ()
    {
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

    }


}
