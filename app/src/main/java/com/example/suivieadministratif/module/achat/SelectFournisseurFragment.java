package com.example.suivieadministratif.module.achat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.menu.MenuViewModel;

import com.example.suivieadministratif.task.ListeFournisseurSelectTask;

public class SelectFournisseurFragment extends Fragment {

    ProgressBar pb  ;
    RecyclerView rv_list_fournisseur  ;
    SearchView search_bar_client;
    private MenuViewModel menuViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_select_fournisseur, container, false);
        pb = (ProgressBar) root.findViewById(R.id.pb) ;

        rv_list_fournisseur =   (RecyclerView) root.findViewById(R.id.rv_list_fournisseur);
        rv_list_fournisseur.setHasFixedSize(true);
        rv_list_fournisseur.setLayoutManager(new LinearLayoutManager(getActivity()));

        search_bar_client = (SearchView) root.findViewById(R.id.search_bar_client);

        menuViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                ListeFournisseurSelectTask listeFournisseurSelectTask =   new ListeFournisseurSelectTask(getActivity()  , rv_list_fournisseur  , pb ,search_bar_client)  ;
                listeFournisseurSelectTask.execute() ;

            }
        });





        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        //getActivity().setTitle("Selectionner un Fournisseur");
    }


}