package com.example.suivieadministratif.module.Stock.FicheArticle.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suivieadministratif.R ;

import com.example.suivieadministratif.module.Stock.FicheArticle.FicheArticleActivity;

import com.example.suivieadministratif.task.FicheArticlekParDepotParArticleTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class FicheArticleFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_CODE_DEPOT = "CODE_DEPOT";
    private PageViewModel pageViewModel;
    String CodeDepot = "";

    public  static RecyclerView rv_list_fiche_article ;
    public  static  ProgressBar progressBar ;


    public static FicheArticleFragment newInstance(int index, String CodeDepot) {
        FicheArticleFragment fragment = new FicheArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putString(ARG_SECTION_CODE_DEPOT, CodeDepot);
        fragment.setArguments(bundle);
        Log.e("x_code_depot_selected","newInstance "+  FicheArticleActivity.tab_depot.getSelectedTabPosition() ) ;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            CodeDepot = getArguments().getString(ARG_SECTION_CODE_DEPOT);
            Log.e("x_code_depot_selected","onCreate "+  FicheArticleActivity.tab_depot.getSelectedTabPosition()  ) ;
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fiche_article, container, false);


        rv_list_fiche_article = root.findViewById(R.id.rv_list_fiche_article);


        rv_list_fiche_article.setHasFixedSize(true);
        rv_list_fiche_article.setLayoutManager( new LinearLayoutManager( getActivity() ) );


        FicheArticleActivity.CodeDepotSelected = CodeDepot ;

        Log.e("x_code_depot_selected","onCreateView "+  FicheArticleActivity.tab_depot.getSelectedTabPosition()  ) ;
//final ProgressBar
         progressBar = root.findViewById(R.id.pb);

        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {


                //FicheArticleActivity.tab_depot.getTabAt(FicheArticleActivity.index_tab_selected).select();

                FicheArticlekParDepotParArticleTask ficheArticlekParDepotParArticleTask = new FicheArticlekParDepotParArticleTask(getActivity(), rv_list_fiche_article,CodeDepot,      FicheArticleActivity. CodeArticleSelected ,FicheArticleActivity.date_debut , FicheArticleActivity.date_fin,progressBar   );
                ficheArticlekParDepotParArticleTask.execute();

                //textView.setText(s);

            }
        });


        return root;
    }
}