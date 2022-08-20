package com.example.suivieadministratif.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.module.caisse.AlimentationBanqueRecetteActivity;
import com.example.suivieadministratif.module.caisse.AlimentationCaisseActivity;
import com.example.suivieadministratif.module.caisse.CaisseRecetteActivity;
import com.example.suivieadministratif.module.caisse.MouvementCaisseDepenseDetailActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MenuCaisseFragment extends Fragment {

    private MenuViewModel menuViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu_caisse, container, false);

        menuViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });


        CardView btn_mvmnt_caisse_depense  = (CardView) root.findViewById(R.id.btn_mouvement_caisse_depense );
        btn_mvmnt_caisse_depense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toMouvemntCaisseDep  = new Intent(getActivity() , MouvementCaisseDepenseDetailActivity.class) ;
                startActivity(toMouvemntCaisseDep);

            }
        });



        CardView   btn_caisse_recette= (CardView) root.findViewById(R.id.btn_caisse_recette );
        btn_caisse_recette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCaisseRecette  = new Intent(getActivity() , CaisseRecetteActivity.class) ;
                startActivity(toCaisseRecette);

            }
        });


        CardView   btn_alimen_caisse_recette= (CardView) root.findViewById(R.id.btn_alimen_caisse_recette );
        btn_alimen_caisse_recette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Intent toCaisseRecette  = new Intent(getActivity() , AlimentationBanqueRecetteActivity.class) ;
                startActivity(toCaisseRecette);

            }
        });


        CardView   btn_alimen_caisse= (CardView) root.findViewById(R.id.btn_alimen_caisse);
        btn_alimen_caisse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toCaisseRecette  = new Intent(getActivity() , AlimentationCaisseActivity.class) ;
                startActivity(toCaisseRecette);

            }
        });




        return root;
    }
}