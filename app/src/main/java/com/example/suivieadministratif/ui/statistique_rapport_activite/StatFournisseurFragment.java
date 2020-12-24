package com.example.suivieadministratif.ui.statistique_rapport_activite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.ui.menu.MenuViewModel;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.CommandeFournisseurNonConforme;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.ListeRetenuFrs;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.PieceNonPayeFrs;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.SuivieCommandeFrs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class StatFournisseurFragment extends Fragment {

    private MenuViewModel menuViewModel;
    private static final String ARG_SECTION_NUMBER = "section_number";


    public static StatFournisseurFragment newInstance(int index) {
        StatFournisseurFragment fragment = new StatFournisseurFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stat_fournisseur, container, false);


        CardView btn_retenu_frs = (CardView) root.findViewById(R.id.btn_retenu_frs);
        btn_retenu_frs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getActivity(), ListeRetenuFrs.class);
                startActivity(intent6);
            }
        });



        CardView btn_piece_non_paye = (CardView) root.findViewById(R.id.btn_piece_non_paye);
        btn_piece_non_paye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getActivity(), PieceNonPayeFrs.class);
                startActivity(intent6);
            }
        });




        CardView btn_suivie_des_commande = (CardView) root.findViewById(R.id.btn_suivie_des_commande)  ;
        btn_suivie_des_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(getActivity(), SuivieCommandeFrs.class);
                startActivity(intent7);
            }
        });


        CardView  btn_cmd_frns_nn_conforme = (CardView) root.findViewById(R.id.btn_cmd_frns_nn_conforme) ;
        btn_cmd_frns_nn_conforme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getActivity(), CommandeFournisseurNonConforme.class);
                startActivity(intent5);

            }
        });

  /*
        CardView  btn_mvmnt_vente_service= (CardView) root.findViewById(R.id.btn_mvmnt_vente_service) ;
        btn_mvmnt_vente_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getActivity(), MouvementVenteServiceActivity.class);
                startActivity(intent5);
            }
        });


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
        });*/


        menuViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });


        return root;
    }
}