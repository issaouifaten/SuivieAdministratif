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
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.task.ListContactTaskForSearchableSpinner;
import com.example.suivieadministratif.task.ListDepotTaskForSearchableSpinner;
import com.example.suivieadministratif.task.ListFamilleTaskForSearchableSpinner;
import com.example.suivieadministratif.task.ListFournisseurTaskForSearchableSpinner;
import com.example.suivieadministratif.task.ListMoyenRelationSpinner;
import com.example.suivieadministratif.task.ListNatureRelationSpinner;
import com.example.suivieadministratif.task.ListResponsableTaskForSearchableSpinner;
import com.example.suivieadministratif.ui.menu.MenuViewModel;
import com.example.suivieadministratif.ui.statistique_rapport_activite.SRM.SuivieRelationFournisseurActivity;
import com.example.suivieadministratif.ui.statistique_rapport_activite.article.ArticleNonMouvementeActivity;
import com.example.suivieadministratif.ui.statistique_rapport_activite.importation.SuivieDossierImportationActivity;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StatSRMFragment extends Fragment {

    private MenuViewModel menuViewModel;
    private static final String ARG_SECTION_NUMBER = "section_number";


    public static String CodeFrns_selected = "";
    public static String CodeContactSelected  = "";
    public static String CodeRepresentant_selected = "";
    public static String CodeMoyenRelationSelected  = "";
    public static String CodeNatureSelected    = "";



   public static SearchableSpinner sp_contact  ;

    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;
    public static int id_DatePickerDialog = 0;

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");


    Date date_debut = null;
    Date date_fin = null;

    public static StatSRMFragment newInstance(int index) {
        StatSRMFragment fragment = new StatSRMFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        menuViewModel =  ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate  ( R.layout.fragment_stat_srm  , container, false );


        CardView btn_suivi_relation_fournisseur = (CardView) root.findViewById(R.id.btn_suivi_relation_fournisseur)  ;


        btn_suivi_relation_fournisseur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogSuivieRelationFournisseur(v) ;

                Intent intent6 = new Intent(getActivity(), SuivieRelationFournisseurActivity.class);
               // startActivity(intent6);

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

    public void dialogSuivieRelationFournisseur(View v) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        //ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_suivie_fournisseur, null, false);


        SearchableSpinner sp_fournisseur = (SearchableSpinner) dialogView.findViewById(R.id.sp_fournisseur);
          sp_contact = (SearchableSpinner) dialogView.findViewById(R.id.sp_contact);
        SearchableSpinner sp_representant = (SearchableSpinner) dialogView.findViewById(R.id.sp_representant);


        SearchableSpinner sp_moyen_relation = (SearchableSpinner) dialogView.findViewById(R.id.sp_moyen_relation);
        SearchableSpinner sp_nature = (SearchableSpinner) dialogView.findViewById(R.id.sp_nature);


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



        //  fournisseur
        ListFournisseurTaskForSearchableSpinner listFrnsTaskForSearchableSpinner = new ListFournisseurTaskForSearchableSpinner(getActivity(), sp_fournisseur,"dialogSuivieRelationFournisseur");
        listFrnsTaskForSearchableSpinner.execute();


        //  contact   fournisseur
        ListContactTaskForSearchableSpinner   listContactTaskForSearchableSpinner = new ListContactTaskForSearchableSpinner(getActivity()  ,sp_contact,CodeFrns_selected  ,"dialogSuivieRelationFournisseur");
        listContactTaskForSearchableSpinner.execute() ;



        //    responsable
        ListResponsableTaskForSearchableSpinner  listResponsableTaskForSearchableSpinner  = new ListResponsableTaskForSearchableSpinner(getActivity() , sp_representant ,"dialogSuivieRelationFournisseur") ;
        listResponsableTaskForSearchableSpinner.execute();


        // moyen relation
        ListMoyenRelationSpinner listMoyenRelationSpinner    = new ListMoyenRelationSpinner(getActivity()  , sp_moyen_relation , "dialogSuivieRelationFournisseur") ;
        listMoyenRelationSpinner.execute() ;

        //  nature Relation
        ListNatureRelationSpinner  listNatureRelationSpinner = new ListNatureRelationSpinner(getActivity() , sp_nature  , "dialogSuivieRelationFournisseur") ;
        listNatureRelationSpinner.execute() ;



        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        buttonOk.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("afficher_btn", "date_debut " + txt_date_debut.getText().toString());
                Log.e("afficher_btn", "date_fin " + txt_date_fin.getText().toString());

                Log.e("afficher_btn", "Fournisseur " + CodeFrns_selected);
                Log.e("afficher_btn", "Contact " + CodeContactSelected);
                Log.e("afficher_btn", "representant " + CodeRepresentant_selected);
                Log.e("afficher_btn", "moyenRelation " + CodeMoyenRelationSelected);
                Log.e("afficher_btn", "NatureRelation " + CodeNatureSelected);

                Intent intent6 = new Intent(getActivity(), SuivieRelationFournisseurActivity.class);

                intent6.putExtra("cle_date_debut", txt_date_debut.getText().toString());
                intent6.putExtra("cle_date_fin", txt_date_fin.getText().toString());


                intent6.putExtra("cle_code_frns", CodeFrns_selected);
                intent6.putExtra("cle_code_contact", CodeContactSelected);
                intent6.putExtra("cle_code_responsable", CodeRepresentant_selected);
                intent6.putExtra("cle_code_moyen_relation", CodeMoyenRelationSelected);
                intent6.putExtra("cle_code_nature_relation", CodeNatureSelected);

               startActivity(intent6);


            }
        });

        alertDialog.show();


    }
}