package com.example.suivieadministratif.ui.statistique_rapport_activite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.menu.MenuViewModel;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Achat.ArticleAcheteNonVenduActivity;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Achat.EtatArticlePerime;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Achat.EtatGlobalAchat;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Achat.EtatJournalArticleAchete;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Achat.JournalFactureAchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class StatAchatFragment extends Fragment {

    private MenuViewModel menuViewModel;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static StatAchatFragment newInstance(int index) {
        StatAchatFragment fragment = new StatAchatFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stat_achat, container, false);


        CardView btn_journal_article_achete = (CardView) root.findViewById(R.id.btn_journal_article_achete)  ;
        btn_journal_article_achete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getActivity(), EtatJournalArticleAchete.class);
                startActivity(intent6);
            }
        });


        CardView btn_global_achat = (CardView) root.findViewById(R.id.btn_global_achat)  ;
        btn_global_achat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(getActivity(), EtatGlobalAchat.class);
               startActivity(intent7);
            }
        });


        CardView  btn_article_perime = (CardView) root.findViewById(R.id.btn_article_perime) ;
        btn_article_perime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getActivity(), EtatArticlePerime.class);
                startActivity(intent5);

            }
        });


        CardView  btn_journal_facture_achat= (CardView) root.findViewById(R.id.btn_journal_facture_achat) ;
        btn_journal_facture_achat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getActivity(), JournalFactureAchat.class);
                 startActivity(intent5);
            }
        });


        CardView   btn_reglement_client = (CardView)  root.findViewById(R.id.btn_article_achete_nn_vendu)  ;
        btn_reglement_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent1 = new Intent(getActivity() , ArticleAcheteNonVenduActivity.class) ;
                startActivity(intent1);
            }
        });

/*
        CardView   btn_echeance_client = (CardView)   root.findViewById(R.id.btn_echeance_Client);
        btn_echeance_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent1 = new Intent(getActivity() , RapportEcheanceClientActivity.class) ;
                startActivity(intent1);
            }
        });
*/

        menuViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });


        return root;
    }
}