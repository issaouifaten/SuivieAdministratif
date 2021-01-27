package com.example.suivieadministratif.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.activity.AlerteWorkflow;
import com.example.suivieadministratif.param.Param;
import com.example.suivieadministratif.ui.statistique_rapport_activite.MenuStatiqtiqueEtRapportActiviteFragment;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import de.hdodenhof.circleimageview.CircleImageView;

public class MenuPrincipalFragment extends Fragment {

    private MenuViewModel menuViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate( R.layout.fragment_menu_principal , container, false);


        SharedPreferences pref = getActivity().getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete","") ;
       TextView txt_libelle_soc =(TextView) root.findViewById(R.id.txt_libelle_soc) ;

        CircleImageView img_soc = (CircleImageView)   root.findViewById(R.id.img_soc) ;

        txt_libelle_soc.setText(NomSociete);
        if (NomSociete.equals("CCM"))
        {
            img_soc.setImageResource(R.drawable.ic_logo_ccm);
        }
        else  if (NomSociete.equals("GMT"))
        {
            img_soc.setImageResource(R.drawable.ic_logo_gmt);
        }
        else  if (NomSociete.contains("I2S"))
        {
            img_soc.setImageResource(R.drawable.i2s);
        }
        else  if (NomSociete.contains("CMVI"))
        {
            img_soc.setImageResource(R.drawable.cmvi_logo);
        }

        else  if (NomSociete.contains("MTD"))
        {
            img_soc.setImageResource(R.drawable.mtd_logo_transportatio);
        }

        CardView btn_vente = (CardView)   root.findViewById(R.id.btn_vente) ;
        btn_vente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment fragment = new MenuVenteFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                if (fragment != null) {
                    ft.replace(R.id.nav_host_fragment, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }



            }
        });




        CardView btn_achat = (CardView)   root.findViewById(R.id.btn_achat) ;
        btn_achat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MenuAchatFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                if (fragment != null) {
                    ft.replace(R.id.nav_host_fragment, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });





        CardView  btn_caisse_depense = (CardView)  root.findViewById(R.id.btn_caisse);
        btn_caisse_depense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MenuCaisseFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                if (fragment != null) {
                    ft.replace(R.id.nav_host_fragment, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });


        CardView  btn_stock = (CardView)  root.findViewById(R.id.btn_etat_de_stock);
        btn_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MenuEtatDeStockFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                if (fragment != null) {
                    ft.replace(R.id.nav_host_fragment, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });



        CardView  btn_statistique_et_rapport_act = (CardView)  root.findViewById(R.id.btn_statistique_rapport);
        btn_statistique_et_rapport_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MenuStatiqtiqueEtRapportActiviteFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                if (fragment != null) {
                    ft.replace(R.id.nav_host_fragment, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });

        CardView   btn_workflow= (CardView) root.findViewById(R.id.btn_workflow);
        btn_workflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getActivity(), AlerteWorkflow.class);
                startActivity(intent3);

            }
        });
        //btn_tresorerie
        CardView btn_tresorerie = (CardView)   root.findViewById(R.id.btn_tresorerie) ;
        btn_tresorerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MenuTresorerieFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                if (fragment != null) {
                    ft.replace(R.id.nav_host_fragment, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }

            }
        });

/*
        CardView btn_etat_stock ;
        CardView btn_bon_commande,  btn_bon_livraison , btn_bon_retour , btn_vente_journal_article;
        CardView btn_ca_soc  , btn_ca_golbal  ;

        TextView   txt_ca_soc  ;
        ImageView img_societe ;

        SharedPreferences pref = getActivity().getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");

        txt_ca_soc =(TextView)    root.findViewById(R.id.txt_ca_soc);
        img_societe = (ImageView)  root.findViewById(R.id.img_societe);

        btn_etat_stock = (CardView) root.findViewById(R.id.btn_etat_de_stock);

        btn_bon_commande = (CardView) root.findViewById(R.id.btn_bon_commande);
        btn_bon_livraison = (CardView) root.findViewById(R.id.btn_bon_livraison);
        btn_bon_retour= (CardView) root.findViewById(R.id.btn_bon_retour);
        btn_vente_journal_article= (CardView) root.findViewById(R.id.btn_journal_vente_article);

        btn_ca_soc= (CardView) root.findViewById(R.id.btn_ca_soc);
        btn_ca_golbal= (CardView) root.findViewById(R.id.btn_ca_global);

        if (NomSociete.equals("CCM"))
        {
            img_societe.setImageResource(R.drawable.ic_logo_ccm);
            txt_ca_soc.setText("Chiffre d'Affaire "+NomSociete);
        }
        else  if (NomSociete.equals("GMT"))
        {
            img_societe.setImageResource(R.drawable.ic_logo_gmt);
            txt_ca_soc.setText("Chiffre d'Affaire "+NomSociete);
        }


        btn_etat_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(getActivity(), StockArticle.class);
                startActivity(intent2);
            }
        });


        btn_bon_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent5 = new Intent(getActivity(), EtatCommande.class);
                startActivity(intent5);

            }
        });

        btn_bon_livraison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent6 = new Intent(getActivity(), EtatLivraisonActivity.class);
                startActivity(intent6);
            }
        });

        btn_bon_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent7 = new Intent(getActivity(), EtatRetourActivity.class);
                startActivity(intent7);

            }
        });

        btn_vente_journal_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(getActivity(), EtatJournalArticleVendu.class);
                startActivity(intent4);

            }
        });

        btn_ca_soc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), EtatChiffreAffaire.class);
                startActivity(intent);
            }
        });
        btn_ca_golbal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent3 = new Intent(getActivity(), ChiffreAffaireGlobale.class);
                startActivity(intent3);

            }
        });


        menuViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
*/

        return root;
    }
}