package com.example.suivieadministratif.ui.statistique_rapport_activite.article;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.ArticleFaibleRotationTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

public class ArticleFaibleRotationActivity extends AppCompatActivity {


    EditText  ed_taux_rotation_min  , ed_taux_rotation_max ;
    public  static CardView btn_recherche ;
    public  static ProgressBar progressBar ;
    public  static TextView txt_recherche ;
   TextView txt_error_taux_rot ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_faible_rotation);

        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Article Faible Rotation");

        final SearchView search_bar = (SearchView)  findViewById(R.id.search_bar) ;


        txt_error_taux_rot  = (TextView) findViewById(R.id.txt_error_taux_rot);

        ed_taux_rotation_min = (EditText) findViewById(R.id.ed_taux_rotation_min);
        ed_taux_rotation_max = (EditText) findViewById(R.id.ed_taux_rotation_max);

        progressBar = (ProgressBar) findViewById(R.id.pb_bc);
        txt_recherche = (TextView)findViewById(R.id.txt_recherche);
        btn_recherche  = (CardView)  findViewById(R.id.btn_rechrche) ;

         final   ListView lv_list_art_faible_rot = (ListView)   findViewById(R.id.lv_list_art_faible_rot) ;
        final   ProgressBar  pb  = (ProgressBar)   findViewById(R.id.pb) ;

        final   String _date_debut  = getIntent().getStringExtra("cle_date_debut" ) ;
        final  String _date_fin  = getIntent().getStringExtra("cle_date_fin" ) ;
        final  String CodeDepot  = getIntent().getStringExtra("cle_code_depot" ) ;


        final   String CodeFrns  = getIntent().getStringExtra("cle_code_frns" ) ;
        final  String CodeFamille  = getIntent().getStringExtra("cle_code_famille" ) ;
        final  int FrnsEtranger  = getIntent().getIntExtra("cle_frns_etranger" ,0) ;



        ArticleFaibleRotationTask articleFaibleRotationTask =new ArticleFaibleRotationTask(this,lv_list_art_faible_rot,search_bar,pb, _date_debut ,_date_fin ,CodeDepot, CodeFrns,CodeFamille  ,FrnsEtranger,0, 100) ;
        articleFaibleRotationTask.execute() ;



        btn_recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String _val_taux_min = ed_taux_rotation_min.getText().toString() ;
                String _val_taux_max = ed_taux_rotation_max.getText().toString() ;
                if (!_val_taux_min.equals("")  && !_val_taux_max.equals("") )
                {
                    double  _taux_rot_inf  = Double.parseDouble(_val_taux_min) ;
                    double  _taux_rot_max   = Double.parseDouble(_val_taux_max) ;

                    if (_taux_rot_inf <0  || _taux_rot_inf >100  || _taux_rot_max<0  || _taux_rot_max > 100 ||  _taux_rot_inf> _taux_rot_max)
                    {
                        txt_error_taux_rot.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        txt_error_taux_rot.setVisibility(View.INVISIBLE);
                        ArticleFaibleRotationTask articleFaibleRotationTask =new ArticleFaibleRotationTask(ArticleFaibleRotationActivity.this ,lv_list_art_faible_rot,search_bar,pb, _date_debut ,_date_fin ,CodeDepot, CodeFrns,CodeFamille  ,FrnsEtranger,_taux_rot_inf,_taux_rot_max) ;
                        articleFaibleRotationTask.execute() ;
                    }


                }

            }
        });


    }
}