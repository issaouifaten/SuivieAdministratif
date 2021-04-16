package com.example.suivieadministratif.ui.statistique_rapport_activite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.menu.MenuViewModel;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.FicheClientActivity2;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.IndicateurEncourClient;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.ListRetenuClientActivity;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.MoyenReglementClient;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.PieceNonPayeClient;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.SuivieCommandeClient;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.SuivieCommandeNonConformeClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class StatClientFragment extends Fragment {

    private MenuViewModel menuViewModel;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static StatClientFragment newInstance(int index) {
        StatClientFragment fragment = new StatClientFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stat_client, container, false);


        CardView btn_suivie_client = (CardView) root.findViewById(R.id.btn_suivie_client)  ;
        btn_suivie_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getActivity(), FicheClientActivity2.class);
                startActivity(intent6);
            }
        });


        CardView btn_list_retenu_cl = (CardView) root.findViewById(R.id.btn_list_retenu_cl)  ;
        btn_list_retenu_cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(getActivity(), ListRetenuClientActivity.class);
                startActivity(intent7);
            }
        });


        CardView  btn_piece_non_paye_client = (CardView) root.findViewById(R.id.btn_piece_non_paye_client) ;
        btn_piece_non_paye_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getActivity(), PieceNonPayeClient.class);
                startActivity(intent5);

            }
        });


        CardView  btn_suivie_cmd_client= (CardView) root.findViewById(R.id.btn_suivie_cmd_client) ;
        btn_suivie_cmd_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getActivity(), SuivieCommandeClient.class);
                startActivity(intent5);
            }
        });

        CardView   btn_cmd_non_conforme = (CardView)  root.findViewById(R.id.btn_cmd_non_conforme)  ;
        btn_cmd_non_conforme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent1 = new Intent(getActivity() , SuivieCommandeNonConformeClient.class) ;
                startActivity(intent1);
            }
        });



        CardView   btn_moy_reg = (CardView)   root.findViewById(R.id.btn_moy_reg);
        btn_moy_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent1 = new Intent(getActivity() , MoyenReglementClient.class) ;
                startActivity(intent1);
            }
        });

//btn_indicateur_solde
        CardView   btn_indicateur_solde = (CardView)   root.findViewById(R.id.btn_indicateur_solde);
        btn_indicateur_solde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent1 = new Intent(getActivity() , IndicateurEncourClient.class) ;
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