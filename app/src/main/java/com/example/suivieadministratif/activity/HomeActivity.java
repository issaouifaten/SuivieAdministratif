package com.example.suivieadministratif.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import com.example.suivieadministratif.module.Production.Module_Production ;
import com.example.suivieadministratif.menu.MenuAchatFragment;
import com.example.suivieadministratif.menu.MenuCaisseFragment;
import com.example.suivieadministratif.menu.MenuStockFragment;
import com.example.suivieadministratif.menu.MenuPrincipalFragment;
import com.example.suivieadministratif.menu.MenuVenteFragment;
import com.example.suivieadministratif.menu.StatistiqueMenuActivity;
import com.example.suivieadministratif.ui.notifications.NotificationsFragment;
import com.example.suivieadministratif.ui.parametrage.ParametrageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.suivieadministratif.R;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navViewBottom = findViewById(R.id.nav_view_bottom);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder
                (R.id.navigation_menu,   R.id.navigation_workflow ,R.id.navigation_notification ,  R.id.navigation_parametrage)
                .build();


        navViewBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {

                    case R.id.navigation_parametrage:
                        selectedFragment = new ParametrageFragment() ;
                        break;

                    case R.id.navigation_notification:
                        selectedFragment = new NotificationsFragment();
                        break;
                    case R.id.navigation_menu:
                        selectedFragment = new MenuPrincipalFragment() ;
                        break;
                    case R.id.navigation_workflow:
                        Intent intent2 = new Intent(HomeActivity.this, AlerteWorkflow.class);
                        startActivity(intent2);
                        return true;


                }







                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, selectedFragment);
                transaction.commit();
                return true;



            }


        });

















//
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navViewBottom, navController);



        //  latéral
        NavigationView  nav_menu=findViewById(R.id.nav_view);
        View headerView = nav_menu.getHeaderView(0);
      /*  CardView btn_produit = (CardView)   headerView.findViewById(R.id.btn_produit) ;
        btn_produit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Module_Production.class));



            }
        });*/


        CardView btn_achat = (CardView)   headerView.findViewById(R.id.btn_achat) ;
        btn_achat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,new MenuAchatFragment())
                        .addToBackStack(MenuAchatFragment.class.getSimpleName())
                        .commit();


            }
        });


        CardView btn_vente = (CardView)   headerView.findViewById(R.id.btn_vente) ;
        btn_vente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,new MenuVenteFragment())
                        .addToBackStack(MenuAchatFragment.class.getSimpleName())
                        .commit();


            }
        });
        CardView btn_stock = (CardView)   headerView.findViewById(R.id.btn_stock) ;
        btn_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                      getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,new MenuStockFragment())
                        .addToBackStack(MenuAchatFragment.class.getSimpleName())
                        .commit();


            }
        });

        CardView btn_caisse = (CardView)   headerView.findViewById(R.id.btn_caisse) ;
        btn_caisse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,new MenuCaisseFragment())
                        .addToBackStack(MenuAchatFragment.class.getSimpleName())
                        .commit();


            }
        });

        CardView btn_statistique = (CardView)   headerView.findViewById(R.id.btn_statistique) ;
        btn_statistique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),StatistiqueMenuActivity.class));


            }
        });




    }

}