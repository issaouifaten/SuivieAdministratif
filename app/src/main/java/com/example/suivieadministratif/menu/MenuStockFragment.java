package com.example.suivieadministratif.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.module.Stock.BonRedressement;
import com.example.suivieadministratif.module.Stock.EtatDeStockActivity;

import com.example.suivieadministratif.module.Stock.BonEntre;
import com.example.suivieadministratif.module.Stock.BonSortie;
import com.example.suivieadministratif.module.Stock.BonTransfertStock;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MenuStockFragment extends Fragment {

    private MenuViewModel menuViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu_stock, container, false);

        menuViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });

        CardView  btn_etat_de_stock = (CardView) root.findViewById(R.id.btn_etat_de_stock) ;
        btn_etat_de_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), EtatDeStockActivity.class);
                startActivity(intent2);
            }
        });
        //btn_bon_entree
        CardView  btn_bon_entree = (CardView) root.findViewById(R.id.btn_bon_entree) ;
        btn_bon_entree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), BonEntre.class);
                startActivity(intent2);
            }
        });
        //btn_bonsortie_de_stock
        CardView  btn_bonsortie_de_stock = (CardView) root.findViewById(R.id.btn_bonsortie_de_stock) ;
        btn_bonsortie_de_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), BonSortie.class);
                startActivity(intent2);
            }
        });
        //btn_passation_bon_transfert

        CardView  btn_passation_bon_transfert = (CardView) root.findViewById(R.id.btn_passation_bon_transfert) ;
        btn_passation_bon_transfert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), BonTransfertStock.class);
                startActivity(intent2);
            }
        });
        //btn_passation_bon_transfert

        CardView  btn_bon_redressement = (CardView) root.findViewById(R.id.btn_bon_redressement) ;
        btn_bon_redressement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), BonRedressement.class);
                startActivity(intent2);
            }
        });






        return root;
    }
}