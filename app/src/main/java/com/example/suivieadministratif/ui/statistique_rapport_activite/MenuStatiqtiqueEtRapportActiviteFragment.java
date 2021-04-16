package com.example.suivieadministratif.ui.statistique_rapport_activite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.ui.diagramme.MenuDiagrammeFragment;
import com.example.suivieadministratif.menu.MenuViewModel;
import com.example.suivieadministratif.menu.StatistiqueMenuActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MenuStatiqtiqueEtRapportActiviteFragment extends Fragment {

    private MenuViewModel menuViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statistique_et_rapport_activite , container, false);


        CardView   btn_rapport = (CardView) root.findViewById(R.id.btn_rapport)  ;
        btn_rapport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Fragment fragment = new MenuDiagrammeFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                if (fragment != null) {
                    ft.replace(R.id.nav_host_fragment, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }


            }
        });


        CardView btn_statistique = (CardView) root.findViewById(R.id.btn_statistique)  ;
        btn_statistique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(getActivity(), StatistiqueMenuActivity.class);
                startActivity(intent7);
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