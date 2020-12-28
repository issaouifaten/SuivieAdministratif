package com.example.suivieadministratif.ui.statistique_rapport_activite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.ui.menu.MenuViewModel;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Stock.Etat_Stock;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Stock.Etat_indic_dispo_stock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class StatStockFragment extends Fragment {

    private MenuViewModel menuViewModel;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static StatStockFragment newInstance(int index) {
        StatStockFragment fragment = new StatStockFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stat_stock , container, false);


        CardView btn_indic_dispo_stock = (CardView) root.findViewById(R.id.btn_indic_dispo_stock)  ;
        btn_indic_dispo_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getActivity(), Etat_indic_dispo_stock.class);
                startActivity(intent6);
            }
        });


        CardView btn_etat_stock = (CardView) root.findViewById(R.id.btn_etat_stock)  ;
       btn_etat_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(getActivity(), Etat_Stock.class);
                startActivity(intent7);
            }
        });

    /*
        CardView  btn_bon_commande = (CardView) root.findViewById(R.id.btn_bon_commande) ;
        btn_bon_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getActivity(), EtatCommande.class);
                startActivity(intent5);

            }
        });


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