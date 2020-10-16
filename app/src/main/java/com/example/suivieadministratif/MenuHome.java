package com.example.suivieadministratif;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
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
    TextView txt_titre, txt_version;
    ImageView img_logo_hannibal;

    GridView gridSituation;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
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
//sql session
        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);
        NomSociete = pref.getString("NomSociete", NomSociete);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Societe : " + NomSociete);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menudeconnecter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent inte = new Intent(getApplicationContext(), MenuServeur.class);
            startActivity(inte);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //nav_etat_chiffre_affaire
        if (id == R.id.nav_etat_chiffre_affaire) {

            Intent intent = new Intent(getApplicationContext(), EtatChiffreAffaire.class);
            startActivity(intent);
        }
        //nav_etat_stock_article
      else  if (id == R.id.nav_etat_stock_article) {

            Intent intent = new Intent(getApplicationContext(), StockArticle.class);
            startActivity(intent);
        }
        else  if (id == R.id.nav_etat_globale) {

            Intent intent = new Intent(getApplicationContext(), Globale.class);
            startActivity(intent);
        }
        else  if (id == R.id.nav_etat_journal_vente_article) {

            Intent intent = new Intent(getApplicationContext(), EtatJournalArticleVendu.class);
            startActivity(intent);
        }
        else  if (id == R.id.nav_etat_list_commande) {

            Intent intent = new Intent(getApplicationContext(), EtatCommande.class);
            startActivity(intent);
        }
      //nav_etat_list_commande

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
