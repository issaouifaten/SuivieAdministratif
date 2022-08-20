package com.example.suivieadministratif.module.vente;

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
import com.example.suivieadministratif.task.ListeClientSelectTask;

public class SelectClientRappelPayementFragment extends Fragment {


    ProgressBar pb  ;
    RecyclerView rv_list_client  ;
    SearchView search_bar_client;
    private MenuViewModel menuViewModel;
    private static final String ARG_SECTION_NUMBER = "section_number";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_select_client, container, false);
        pb = (ProgressBar) root.findViewById(R.id.pb) ;

        rv_list_client =   (RecyclerView) root.findViewById(R.id.rv_list_client );
        rv_list_client.setHasFixedSize(true);
        rv_list_client.setLayoutManager(new LinearLayoutManager(getActivity()));

        search_bar_client = (SearchView) root.findViewById(R.id.search_bar_client);

        menuViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                ListeClientSelectTask listeClientSelectTask =   new ListeClientSelectTask(getActivity()  , rv_list_client  , pb ,search_bar_client,"RappelPayement")  ;
                listeClientSelectTask.execute() ;

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