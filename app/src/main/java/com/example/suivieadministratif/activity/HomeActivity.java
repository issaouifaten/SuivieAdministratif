package com.example.suivieadministratif.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.suivieadministratif.ui.menu.MenuAchatFragment;
import com.example.suivieadministratif.ui.menu.MenuCaisseFragment;
import com.example.suivieadministratif.ui.menu.MenuEtatDeStockFragment;
import com.example.suivieadministratif.ui.menu.MenuStatistiqueFragment;
import com.example.suivieadministratif.ui.menu.MenuTresorerieFragment;
import com.example.suivieadministratif.ui.menu.MenuVenteFragment;
import com.example.suivieadministratif.ui.menu.StatistiqueMenuActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.suivieadministratif.R;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navViewBottom = findViewById(R.id.nav_view_bottom);


        //  latéral
        NavigationView  nav_menu=findViewById(R.id.nav_view);
        View headerView = nav_menu.getHeaderView(0);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder
                (R.id.navigation_menu,   R.id.navigation_workflow ,  R.id.navigation_parametrage)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navViewBottom, navController);



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
                        .replace(R.id.nav_host_fragment,new MenuEtatDeStockFragment())
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