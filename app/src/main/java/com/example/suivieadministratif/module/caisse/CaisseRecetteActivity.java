package com.example.suivieadministratif.module.caisse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import  com.example.suivieadministratif.R ;
import com.example.suivieadministratif.task.EtatDeCaisseTask;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CaisseRecetteActivity extends AppCompatActivity {

    FloatingActionButton fab_arrow;
    RelativeLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;

    RadioGroup rg_type_caisse ;
    RadioButton rb_tout ,rb_compte  ,rb_utilisateur ;

    public  static String CodeFonctionSlected ="" ;

    public static double tot_caisse_compte =0  , tot_caisse_user  =0 ;
    public static TextView txt_tot_caisse_compte  , txt_tot_caisse_user  ;
    RecyclerView rv_list_caisse_depense  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caisse_recette);


        final RecyclerView rv_caisse = findViewById(R.id.rv_caisse);
        final ProgressBar progressBar = findViewById(R.id.progress_bar);
        rv_list_caisse_depense = findViewById(R.id.rv_list_caisse_depense)  ;


        rv_caisse.setHasFixedSize(true);
        rv_caisse.setLayoutManager( new LinearLayoutManager( this ));


        rv_list_caisse_depense.setHasFixedSize(true);
        rv_list_caisse_depense.setLayoutManager( new LinearLayoutManager(this));


        rg_type_caisse=  findViewById(R.id.rg_type_caisse) ;
        rb_tout =  findViewById(R.id.rb_tout) ;
        rb_compte =  findViewById(R.id.rb_compte) ;
        rb_utilisateur =  findViewById(R.id.rb_utilisateur) ;


        txt_tot_caisse_compte =  findViewById(R.id.txt_tot_compte) ;
        txt_tot_caisse_user = findViewById(R.id.txt_tot_utilisateur) ;


        rb_tout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EtatDeCaisseTask etatDeCaisseTask  = new EtatDeCaisseTask(CaisseRecetteActivity.this , rv_caisse , progressBar, "Tout") ;
                etatDeCaisseTask.execute() ;

            }
        });

        rb_compte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EtatDeCaisseTask  etatDeCaisseTask  = new EtatDeCaisseTask(CaisseRecetteActivity.this  , rv_caisse , progressBar ,"Compte") ;
                etatDeCaisseTask.execute() ;
            }
        });

        rb_utilisateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EtatDeCaisseTask  etatDeCaisseTask  = new EtatDeCaisseTask(CaisseRecetteActivity.this  , rv_caisse , progressBar ,"User") ;
                etatDeCaisseTask.execute() ;
            }
        });

/*

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
*/
                EtatDeCaisseTask  etatDeCaisseTask  = new EtatDeCaisseTask(this , rv_caisse , progressBar ,"Tout") ;
                etatDeCaisseTask.execute() ;


               /* EtatDeCaisseDepenseTask etatDeCaisseDepenseTask  = new EtatDeCaisseDepenseTask(getActivity() , rv_list_caisse_depense , progressBar   ) ;
                etatDeCaisseDepenseTask.execute() ;*/

         //   }
      //  });



        layoutBottomSheet = (RelativeLayout) findViewById(R.id.bottom_sheet);

        fab_arrow = (FloatingActionButton) findViewById(R.id.fab_arrow);

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setHideable(false);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

                        // Toast.makeText(getActivity() , "Close Sheet" ,Toast.LENGTH_LONG).show();
                        fab_arrow.setImageResource(R.drawable.ic_arrow_down);

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        // Toast.makeText(getActivity() , "Expand Sheet" ,Toast.LENGTH_LONG).show();
                        fab_arrow.setImageResource(R.drawable.ic_arrow_up);
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });



    }
}