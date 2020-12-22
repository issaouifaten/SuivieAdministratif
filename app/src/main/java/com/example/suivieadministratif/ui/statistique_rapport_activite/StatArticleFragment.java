package com.example.suivieadministratif.ui.statistique_rapport_activite;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.suivieadministratif.ui.statistique_rapport_activite.article.EtatJournalArticleVendu;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.ListDepotTaskForSearchableSpinner;
import com.example.suivieadministratif.task.ListFamilleTaskForSearchableSpinner;
import com.example.suivieadministratif.task.ListFournisseurTaskForSearchableSpinner;
import com.example.suivieadministratif.ui.menu.MenuViewModel;
import com.example.suivieadministratif.ui.statistique_rapport_activite.article.ArticleDLC;
import com.example.suivieadministratif.ui.statistique_rapport_activite.article.ArticleFaibleRotationActivity;
import com.example.suivieadministratif.ui.statistique_rapport_activite.article.ArticleNonMouvementeActivity;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class StatArticleFragment extends Fragment {

    private MenuViewModel menuViewModel;
    private static final String ARG_SECTION_NUMBER = "section_number";


    public static String CodeFrns_selected = "";
    public static String RaisonFrns_selected = "";


    public static String CodeFamille_selected = "";
    public static String LibelleFamille_selected = "";

    public static String CodeDepot_selected = "";
    public static String LibelleDepot_selected = "";


    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;
    public static int id_DatePickerDialog = 0;

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");


    Date date_debut = null;
    Date date_fin = null;


    public static StatArticleFragment newInstance(int index) {

        StatArticleFragment fragment = new StatArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stat_article, container, false);


        CardView btn_article_faible_rotation = (CardView) root.findViewById(R.id.btn_article_faible_rotation);
        btn_article_faible_rotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialogChoixArtFaibleRotation(v) ;

            }
        });


        CardView btn_article_nn_mouvemente = (CardView) root.findViewById(R.id.btn_article_nn_mouvemente);
        btn_article_nn_mouvemente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialogArticleNonMouvemente(v);
            }
        });



        CardView btn_journal_article = (CardView) root.findViewById(R.id.btn_journal_article)  ;
        btn_journal_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(getActivity(), EtatJournalArticleVendu.class);
                startActivity(intent7);
            }
        });

        //btn_article_dlc
        CardView btn_article_dlc = (CardView) root.findViewById(R.id.btn_article_dlc)  ;
        btn_article_dlc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(getActivity(), ArticleDLC.class);
                startActivity(intent7);
            }
        });




   /*
        CardView  btn_bon_commande = (CardView) root.findViewById(R.id.btn_bon_commande) ;
        btn_bon_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getActivity(), EtatCommande.class);
                startActivity(intent5);

            }
        });


        CardView  btn_mvmnt_vente_service= (CardView) root.findViewById(R.id.btn_mvmnt_vente_service) ;
        btn_mvmnt_vente_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getActivity(), MouvementVenteServiceActivity.class);
                startActivity(intent5);
            }
        });


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

*/

        menuViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });


        return root;
    }


    public void dialogChoixArtFaibleRotation(View v) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        //ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_article_faible_rotation, null, false);

        RadioGroup rg_fournisseur = (RadioGroup) dialogView.findViewById(R.id.rg_fournisseur);

        final RadioButton rb_tt = (RadioButton) dialogView.findViewById(R.id.rb_tout);
        final RadioButton rb_frns_local = (RadioButton) dialogView.findViewById(R.id.rb_frns_local);
        final RadioButton rb_frns_entranger = (RadioButton) dialogView.findViewById(R.id.rb_fournisseur_etranger);


        SearchableSpinner sp_fournisseur = (SearchableSpinner) dialogView.findViewById(R.id.sp_fournisseur);
        SearchableSpinner sp_famille = (SearchableSpinner) dialogView.findViewById(R.id.sp_famille);
        SearchableSpinner sp_depot = (SearchableSpinner) dialogView.findViewById(R.id.sp_depot);

        Date currentDate = new Date();

        final TextView txt_date_debut = (TextView) dialogView.findViewById(R.id.txt_date_debut);
        final TextView txt_date_fin = (TextView) dialogView.findViewById(R.id.txt_date_fin);


        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        cal1.add(Calendar.MONTH, -3);
        year_x1 = cal1.get(Calendar.YEAR);
        month_x1 = cal1.get(Calendar.MONTH);
        day_x1 = cal1.get(Calendar.DAY_OF_MONTH);


        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(currentDate);
        //  cal2.add(Calendar.DAY_OF_YEAR, +7);
        year_x2 = cal2.get(Calendar.YEAR);
        month_x2 = cal2.get(Calendar.MONTH);
        day_x2 = cal2.get(Calendar.DAY_OF_MONTH);


        date_debut = cal1.getTime();
        String _date_du = df.format(cal1.getTime());
        txt_date_debut.setText(_date_du);

        date_fin = cal2.getTime();
        String _date_au = df.format(cal2.getTime());
        txt_date_fin.setText(_date_au);


        txt_date_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 0;
                Log.e("month_x1", "On picker  : " + month_x1);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        if (id_DatePickerDialog == 0) {
                            year_x1 = year;
                            month_x1 = monthOfYear;
                            day_x1 = dayOfMonth;

                            txt_date_debut.setText("" + formatter.format(day_x1) + "/" + formatter.format(month_x1 + 1) + "/" + year_x1);

                            String _date_du = formatter.format(day_x1) + "/" + formatter.format(month_x1 + 1) + "/" + year_x1 + " ";
                            String _date_au = txt_date_fin.getText().toString();


                            try {
                                date_debut = df.parse(_date_du);
                                date_fin = df.parse(_date_au);

                            } catch (Exception e) {
                                Log.e("Exception--", " " + e.getMessage());
                            }

                        }
                    }
                }, year_x1, month_x1, day_x1);
                datePickerDialog.show();
            }
        });


        txt_date_fin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 1;

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (id_DatePickerDialog == 1) {

                            year_x2 = year;
                            month_x2 = monthOfYear;
                            day_x2 = dayOfMonth;

                            txt_date_fin.setText("" + formatter.format(day_x2) + "/" + formatter.format(month_x2 + 1) + "/" + year_x2);

                            String _date_au = "" + formatter.format(day_x2) + "/" + formatter.format(month_x2 + 1) + "/" + year_x2;
                            String _date_du = txt_date_debut.getText().toString();


                            try {
                                date_debut = df.parse(_date_du);
                                date_fin = df.parse(_date_au);

                            } catch (Exception e) {
                                Log.e("Exception--", " " + e.getMessage());
                            }


                        }
                    }
                }, year_x2, month_x2, day_x2);
                datePickerDialog.show();
            }
        });



        ListFournisseurTaskForSearchableSpinner listFrnsTaskForSearchableSpinner = new ListFournisseurTaskForSearchableSpinner(getActivity(), sp_fournisseur ,"dialogChoixArtFaibleRotation");
        listFrnsTaskForSearchableSpinner.execute();


        ListFamilleTaskForSearchableSpinner ListFamilleTask = new ListFamilleTaskForSearchableSpinner(getActivity(), sp_famille);
        ListFamilleTask.execute();


        ListDepotTaskForSearchableSpinner listDepotTaskForSearchableSpinner = new ListDepotTaskForSearchableSpinner(getActivity(), sp_depot , "dialogChoixArtFaibleRotation");
        listDepotTaskForSearchableSpinner.execute();


        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("afficher_btn", "date_debut " + txt_date_debut.getText().toString());
                Log.e("afficher_btn", "date_fin " + txt_date_fin.getText().toString());

                Log.e("afficher_btn", "Fournisseur " + CodeFrns_selected);
                Log.e("afficher_btn", "Famille " + CodeFamille_selected);
                Log.e("afficher_btn", "Depot " + CodeDepot_selected);


                int etranger = 0;

                if (rb_tt.isChecked()) {
                    etranger = 2;
                } else if (rb_frns_local.isChecked()) {
                    etranger = 0;
                } else if (rb_frns_entranger.isChecked()) {
                    etranger = 1;
                }

                Log.e("afficher_btn", "Etranger " + etranger);


                Intent intent6 = new Intent(getActivity(), ArticleFaibleRotationActivity.class);

                intent6.putExtra("cle_date_debut", txt_date_debut.getText().toString());
                intent6.putExtra("cle_date_fin", txt_date_fin.getText().toString());


                intent6.putExtra("cle_code_frns", CodeFrns_selected);
                intent6.putExtra("cle_code_famille", CodeFamille_selected);
                intent6.putExtra("cle_code_depot", CodeDepot_selected);
                intent6.putExtra("cle_frns_etranger", etranger);


                startActivity(intent6);
                /*
                Intent toDetailSoldeClient = new Intent(getActivity(), DetailSoldeClientActivity.class);

                toDetailSoldeClient.putExtra("cle_code_client", CodeClientSeleted);
                toDetailSoldeClient.putExtra("cle_raison_client", RaisonClientSeleted);

                toDetailSoldeClient.putExtra("cle_code_recouv", CodeRecouvreurSeleted);
                toDetailSoldeClient.putExtra("cle_Nom_recouv", NomRecouvreurSeleted);

                toDetailSoldeClient.putExtra("cle_date_debut", df.format(date_debut));
                toDetailSoldeClient.putExtra("cle_date_fin", df.format(date_fin));

                startActivity(toDetailSoldeClient);
                */

            }
        });

        alertDialog.show();


    }

    public void dialogArticleNonMouvemente(View v) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        //ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_article_non_mouvemente, null, false);


        final RadioButton rb_tt = (RadioButton) dialogView.findViewById(R.id.rb_tout);
        final RadioButton rb_frns_local = (RadioButton) dialogView.findViewById(R.id.rb_frns_local);
        final RadioButton rb_frns_entranger = (RadioButton) dialogView.findViewById(R.id.rb_fournisseur_etranger);


        SearchableSpinner sp_fournisseur = (SearchableSpinner) dialogView.findViewById(R.id.sp_fournisseur);
        SearchableSpinner sp_famille = (SearchableSpinner) dialogView.findViewById(R.id.sp_famille);
        SearchableSpinner sp_depot = (SearchableSpinner) dialogView.findViewById(R.id.sp_depot);

        Date currentDate = new Date();

        final TextView txt_date_debut = (TextView) dialogView.findViewById(R.id.txt_date_debut);
        final TextView txt_date_fin = (TextView) dialogView.findViewById(R.id.txt_date_fin);


        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        cal1.add(Calendar.MONTH, -6);
        year_x1 = cal1.get(Calendar.YEAR);
        month_x1 = cal1.get(Calendar.MONTH);
        day_x1 = cal1.get(Calendar.DAY_OF_MONTH);


        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(currentDate);
        //  cal2.add(Calendar.DAY_OF_YEAR, +7);
        year_x2 = cal2.get(Calendar.YEAR);
        month_x2 = cal2.get(Calendar.MONTH);
        day_x2 = cal2.get(Calendar.DAY_OF_MONTH);


        date_debut = cal1.getTime();
        String _date_du = df.format(cal1.getTime());
        txt_date_debut.setText(_date_du);

        date_fin = cal2.getTime();
        String _date_au = df.format(cal2.getTime());
        txt_date_fin.setText(_date_au);


        txt_date_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 0;
                Log.e("month_x1", "On picker  : " + month_x1);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        if (id_DatePickerDialog == 0) {
                            year_x1 = year;
                            month_x1 = monthOfYear;
                            day_x1 = dayOfMonth;

                            txt_date_debut.setText("" + formatter.format(day_x1) + "/" + formatter.format(month_x1 + 1) + "/" + year_x1);

                            String _date_du = formatter.format(day_x1) + "/" + formatter.format(month_x1 + 1) + "/" + year_x1 + " ";
                            String _date_au = txt_date_fin.getText().toString();


                            try {
                                date_debut = df.parse(_date_du);
                                date_fin = df.parse(_date_au);

                            } catch (Exception e) {
                                Log.e("Exception--", " " + e.getMessage());
                            }

                        }
                    }
                }, year_x1, month_x1, day_x1);
                datePickerDialog.show();
            }
        });


        txt_date_fin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 1;

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (id_DatePickerDialog == 1) {

                            year_x2 = year;
                            month_x2 = monthOfYear;
                            day_x2 = dayOfMonth;

                            txt_date_fin.setText("" + formatter.format(day_x2) + "/" + formatter.format(month_x2 + 1) + "/" + year_x2);

                            String _date_au = "" + formatter.format(day_x2) + "/" + formatter.format(month_x2 + 1) + "/" + year_x2;
                            String _date_du = txt_date_debut.getText().toString();


                            try {
                                date_debut = df.parse(_date_du);
                                date_fin = df.parse(_date_au);

                            } catch (Exception e) {
                                Log.e("Exception--", " " + e.getMessage());
                            }


                        }
                    }
                }, year_x2, month_x2, day_x2);
                datePickerDialog.show();
            }
        });



        ListFournisseurTaskForSearchableSpinner listFrnsTaskForSearchableSpinner = new ListFournisseurTaskForSearchableSpinner(getActivity(), sp_fournisseur,"dialogArticleNonMouvemente");
        listFrnsTaskForSearchableSpinner.execute();


        ListFamilleTaskForSearchableSpinner ListFamilleTask = new ListFamilleTaskForSearchableSpinner(getActivity(), sp_famille);
        ListFamilleTask.execute();


        ListDepotTaskForSearchableSpinner listDepotTaskForSearchableSpinner = new ListDepotTaskForSearchableSpinner(getActivity(), sp_depot , "dialogArticleNonMouvemente");
        listDepotTaskForSearchableSpinner.execute();


        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("afficher_btn", "date_debut " + txt_date_debut.getText().toString());
                Log.e("afficher_btn", "date_fin " + txt_date_fin.getText().toString());

                Log.e("afficher_btn", "Fournisseur " + CodeFrns_selected);
                Log.e("afficher_btn", "Famille " + CodeFamille_selected);
                Log.e("afficher_btn", "Depot " + CodeDepot_selected);


                int etranger = 0;

                if (rb_tt.isChecked()) {
                    etranger = 2;
                } else if (rb_frns_local.isChecked()) {
                    etranger = 0;
                } else if (rb_frns_entranger.isChecked()) {
                    etranger = 1;
                }

                Log.e("afficher_btn", "Etranger " + etranger);


                Intent intent6 = new Intent(getActivity(), ArticleNonMouvementeActivity.class);

                intent6.putExtra("cle_date_debut", txt_date_debut.getText().toString());
                intent6.putExtra("cle_date_fin", txt_date_fin.getText().toString());


                intent6.putExtra("cle_code_frns", CodeFrns_selected);
                intent6.putExtra("cle_code_famille", CodeFamille_selected);
                intent6.putExtra("cle_code_depot", CodeDepot_selected);
                intent6.putExtra("cle_frns_etranger", etranger);


                startActivity(intent6);
                /*
                Intent toDetailSoldeClient = new Intent(getActivity(), DetailSoldeClientActivity.class);

                toDetailSoldeClient.putExtra("cle_code_client", CodeClientSeleted);
                toDetailSoldeClient.putExtra("cle_raison_client", RaisonClientSeleted);

                toDetailSoldeClient.putExtra("cle_code_recouv", CodeRecouvreurSeleted);
                toDetailSoldeClient.putExtra("cle_Nom_recouv", NomRecouvreurSeleted);

                toDetailSoldeClient.putExtra("cle_date_debut", df.format(date_debut));
                toDetailSoldeClient.putExtra("cle_date_fin", df.format(date_fin));

                startActivity(toDetailSoldeClient);
                */

            }
        });

        alertDialog.show();


    }

}