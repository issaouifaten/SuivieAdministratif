package com.example.suivieadministratif.module.Stock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.adapter.BonRetourAdapter;
import com.example.suivieadministratif.model.BonRetourVente;
import com.example.suivieadministratif.model.Depot;
import com.example.suivieadministratif.task.ListDepotForTabTask;
import com.example.suivieadministratif.task.StockArticleTask;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class EtatDeStockActivity extends AppCompatActivity {

    TabLayout tab_depot;
    public static RecyclerView rv_list_article;
    public static ProgressBar pb;
    public static TextView txt_recherche;


    public static ArrayList<Depot> listDepot;
    public static String CodeDepotSelected = "";
    public static String LibelleDepotSelected = "";
    public static String termRecherche = "";
    StockArticleTask stockArticleTask;

    public static TextView txt_tot_ht, txt_tot_tva, txt_tot_ttc, txt_tot_quantite;


  CardView btn_rechrche ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etat_de_stock);

        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Etat de Stock");


        listDepot = new ArrayList<>();
        tab_depot = (TabLayout) findViewById(R.id.tab_depot);

        rv_list_article = (RecyclerView) findViewById(R.id.rv_list );
        rv_list_article.setHasFixedSize(true);
        rv_list_article.setLayoutManager(new LinearLayoutManager(this));

        btn_rechrche = (CardView)   findViewById(R.id.btn_rechrche)  ;

        pb = (ProgressBar) findViewById(R.id.pb);
        txt_recherche = (TextView) findViewById(R.id.txt_recherche);


        txt_tot_ht = (TextView) findViewById(R.id.txt_total_achat_ht);
        txt_tot_tva = (TextView) findViewById(R.id.txt_total_tva);
        txt_tot_ttc = (TextView) findViewById(R.id.txt_total_ttc);
        txt_tot_quantite = (TextView) findViewById(R.id.txt_tot_quantite);


        txt_recherche.setText("Recherche ici");
        pb.setVisibility(View.INVISIBLE);
        stockArticleTask = new StockArticleTask(EtatDeStockActivity.this, rv_list_article, txt_recherche, pb, CodeDepotSelected, LibelleDepotSelected, termRecherche);

        ListDepotForTabTask listDepotForTabTask = new ListDepotForTabTask(this, tab_depot);
        listDepotForTabTask.execute();

      final   SearchView search_bar_article;
        search_bar_article = (SearchView) findViewById(R.id.search_bar_article);

        Button btn_pourcent = (Button) findViewById(R.id.btn_pourcent);
        btn_pourcent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String last_query = search_bar_article.getQuery().toString();
                String new_query = last_query + "%" ;
                search_bar_article.setQuery(new_query, false);
            }
        });





        btn_rechrche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                termRecherche  = search_bar_article.getQuery().toString();
                stockArticleTask = new StockArticleTask(EtatDeStockActivity.this, rv_list_article, txt_recherche, pb, CodeDepotSelected, LibelleDepotSelected, termRecherche);
                stockArticleTask.execute();
            }
        });


    }


}