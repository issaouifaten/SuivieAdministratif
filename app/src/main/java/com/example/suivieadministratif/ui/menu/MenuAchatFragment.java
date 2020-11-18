package com.example.suivieadministratif.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.module.achat.BonCommandeAchatActivity;
import com.example.suivieadministratif.module.achat.BonLivraisonAchatActivity;
import com.example.suivieadministratif.module.achat.BonRetourAchatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MenuAchatFragment extends Fragment {

    private MenuViewModel menuViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu_achat, container, false);


        menuViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });


        CardView  btn_bon_liv_achat = (CardView)  root.findViewById(R.id.btn_bon_livraison_achat) ;
        btn_bon_liv_achat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  intent1 = new Intent(getActivity() , BonLivraisonAchatActivity.class) ;
                startActivity(intent1);
            }
        });


        CardView  btn_bon_cmd_achat = (CardView)  root.findViewById(R.id.btn_bon_commande_achat) ;
        btn_bon_cmd_achat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent1 = new Intent(getActivity() , BonCommandeAchatActivity.class) ;
                startActivity(intent1);
            }
        });



        CardView  btn_bon_retour_achat = (CardView)  root.findViewById(R.id.btn_bon_retour_achat) ;
        btn_bon_retour_achat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent1 = new Intent(getActivity() , BonRetourAchatActivity.class) ;
                startActivity(intent1);
            }
        });




        return root;
    }
}