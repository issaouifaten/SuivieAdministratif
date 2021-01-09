package com.example.suivieadministratif.ui.diagramme;

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
import com.example.suivieadministratif.module.vente.EtatCommande;
import com.example.suivieadministratif.module.vente.EtatDevisVente;
import com.example.suivieadministratif.module.vente.EtatFactureVente;
import com.example.suivieadministratif.module.vente.EtatLivraisonActivity;
import com.example.suivieadministratif.module.vente.EtatRetourActivity;
import com.example.suivieadministratif.module.vente.MouvementVenteServiceActivity;
import com.example.suivieadministratif.ui.menu.MenuViewModel;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Graphique.Diagramme;

public class MenuDiagrammeFragment extends Fragment {

    private MenuViewModel menuViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu_diagramme, container, false);






        CardView   btn_vente_parametrable   = (CardView) root.findViewById(R.id.btn_vente_parametrable)  ;
        btn_vente_parametrable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent6 = new Intent(getActivity(), Diagramme.class);
              startActivity(intent6);
            }
        });




        CardView   btn_var_ca_par_periode = (CardView) root.findViewById(R.id.btn_var_ca_par_periode)  ;
        btn_var_ca_par_periode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent6 = new Intent(getActivity(), EtatLivraisonActivity.class);
                startActivity(intent6);*/
            }
        });


        CardView btn_var_ca_en_mois = (CardView) root.findViewById(R.id.btn_var_ca_en_mois)  ;
        btn_var_ca_en_mois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent7 = new Intent(getActivity(), EtatRetourActivity.class);
                startActivity(intent7);*/
            }
        });


        CardView  btn_var_ca_en_annee = (CardView) root.findViewById(R.id.btn_var_ca_en_annee) ;
        btn_var_ca_en_annee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent5 = new Intent(getActivity(), EtatCommande.class);
                startActivity(intent5);*/

            }
        });
        CardView  btn_ca_par_force_vente = (CardView) root.findViewById(R.id.btn_ca_par_force_vente) ;
        btn_ca_par_force_vente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent intent5 = new Intent(getActivity(), EtatFactureVente.class);
                startActivity(intent5);*/

            }
        });

        CardView  btn_variation_en_nbr_ticket= (CardView) root.findViewById(R.id.btn_variation_en_nbr_ticket) ;
        btn_variation_en_nbr_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent5 = new Intent(getActivity(), MouvementVenteServiceActivity.class);
                startActivity(intent5);*/
            }
        });


        CardView   btn_vente_article_qt = (CardView)  root.findViewById(R.id.btn_vente_article_qt)  ;
        btn_vente_article_qt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent  intent1 = new Intent(getActivity() , ReglementClientActivity.class) ;
                startActivity(intent1);*/
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