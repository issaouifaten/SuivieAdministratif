package com.example.suivieadministratif.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.module.reglementClient.RapportEcheanceClientActivity;
import com.example.suivieadministratif.module.reglementClient.ReglementClientActivity;
import com.example.suivieadministratif.module.tresorerie.PortfeuilCheque;
import com.example.suivieadministratif.module.tresorerie.PortfeuilTraite;
import com.example.suivieadministratif.module.vente.EtatCommande;
import com.example.suivieadministratif.module.vente.EtatDevisVente;
import com.example.suivieadministratif.module.vente.EtatFactureVente;
import com.example.suivieadministratif.module.vente.EtatLivraisonActivity;
import com.example.suivieadministratif.module.vente.EtatRetourActivity;
import com.example.suivieadministratif.module.vente.MouvementVenteServiceActivity;

public class MenuTresorerieFragment extends Fragment {

    private MenuViewModel menuViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu_tresorerie, container, false);




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

        CardView   btn_portfeuil_cheque = (CardView)   root.findViewById(R.id.btn_portfeuil_cheque);
        btn_portfeuil_cheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent1 = new Intent(getActivity() , PortfeuilCheque.class) ;
                startActivity(intent1);
            }
        });

        CardView   btn_portfeuil_traite = (CardView)   root.findViewById(R.id.btn_portfeuil_traite);
        btn_portfeuil_traite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent1 = new Intent(getActivity() , PortfeuilTraite.class) ;
                startActivity(intent1);
            }
        });



        menuViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });


        return root;
    }
}