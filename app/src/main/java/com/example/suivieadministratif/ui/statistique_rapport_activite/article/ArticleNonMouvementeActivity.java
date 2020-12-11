package com.example.suivieadministratif.ui.statistique_rapport_activite.article;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.ArticleNnMouvementeTask;


public class ArticleNonMouvementeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_non_mouvemente);
        SearchView search_bar = (SearchView)  findViewById(R.id.search_bar) ;

        ListView lv_list_art_nn_mvmnt = (ListView)   findViewById(R.id.lv_list_art_nn_mvmnt) ;
        ProgressBar pb  = (ProgressBar)   findViewById(R.id.pb) ;

        String _date_debut  = getIntent().getStringExtra("cle_date_debut" ) ;
        String _date_fin  = getIntent().getStringExtra("cle_date_fin" ) ;
        String CodeDepot  = getIntent().getStringExtra("cle_code_depot" ) ;


        String CodeFrns  = getIntent().getStringExtra("cle_code_frns" ) ;
        String CodeFamille  = getIntent().getStringExtra("cle_code_famille" ) ;
        int FrnsEtranger  = getIntent().getIntExtra("cle_frns_etranger" ,0) ;


        ArticleNnMouvementeTask articleNonMouvementetask = new ArticleNnMouvementeTask(this,lv_list_art_nn_mvmnt,search_bar,pb, _date_debut ,_date_fin ,CodeDepot, CodeFrns,CodeFamille  ,FrnsEtranger);
        articleNonMouvementetask.execute();

    }
}