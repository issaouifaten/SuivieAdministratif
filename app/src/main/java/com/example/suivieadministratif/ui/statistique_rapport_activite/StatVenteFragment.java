package com.example.suivieadministratif.ui.statistique_rapport_activite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.ui.menu.MenuViewModel;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.FicheClient;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.ListRetenuClient;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.PieceNonPayeClient;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Vente.EtatChiffreAffaire;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class StatVenteFragment extends Fragment {

    private MenuViewModel menuViewModel;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static StatVenteFragment newInstance(int index) {
        StatVenteFragment fragment = new StatVenteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        menuViewModel =  ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(  R.layout.fragment_stat_vente , container, false );


        CardView btn_chiffre_affaire = (CardView) root.findViewById(R.id.btn_chiffre_affaire)  ;
        btn_chiffre_affaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getActivity(), EtatChiffreAffaire.class);
                startActivity(intent6);
            }
        });


        CardView btn_piece_non_paye_client = (CardView) root.findViewById(R.id.btn_piece_non_paye_client)  ;
        btn_piece_non_paye_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent7 = new Intent(getActivity(), PieceNonPayeClient.class);
                startActivity(intent7);
            }
        });


        CardView  btn_suivie_client = (CardView) root.findViewById(R.id.btn_suivie_client) ;
        btn_suivie_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getActivity(), FicheClient.class);
                startActivity(intent5);

            }
        });


        CardView  btn_retenu_client= (CardView) root.findViewById(R.id.btn_retenu_client) ;
        btn_retenu_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getActivity(), ListRetenuClient.class);
                startActivity(intent5);
            }
        });

/*
        CardView   btn_reglement_client = (CardView)  root.findViewById(R.id.btn_reg_client)  ;
        btn_reglement_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent1 = new Intent(getActivity() , ReglementClientActivity.class) ;
                startActivity(intent1);
            }
        });


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