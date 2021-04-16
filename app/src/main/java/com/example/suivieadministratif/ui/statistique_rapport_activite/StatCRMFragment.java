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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.adapter.TypeMvmntGVAdapter;
import com.example.suivieadministratif.model.TypeMouvementClick;
import com.example.suivieadministratif.task.ListTypeMvmntDisticntTask;
import com.example.suivieadministratif.menu.MenuViewModel;
import com.example.suivieadministratif.ui.statistique_rapport_activite.CRM.EtatJournalActivite;
import com.example.suivieadministratif.ui.statistique_rapport_activite.CRM.Etat_Suivie_VoitureParMission;
import com.example.suivieadministratif.ui.statistique_rapport_activite.CRM.ListeCauseRetour;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.FicheClientActivity2;
import com.example.suivieadministratif.ui.statistique_rapport_activite.article.JournalActiviteActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class StatCRMFragment extends Fragment {

    private MenuViewModel menuViewModel;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static ArrayList<TypeMouvementClick> listType = new ArrayList<>();

    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;
    public static int id_DatePickerDialog = 0;

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");


    Date date_debut = null;
    Date date_fin = null;


    public static StatCRMFragment newInstance(int index) {
        StatCRMFragment fragment = new StatCRMFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stat_crm, container, false);


        CardView   btn_suivie_vente = (CardView) root.findViewById(R.id.btn_suivie_vente)  ;
        btn_suivie_vente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getActivity(), Etat_Suivie_VoitureParMission.class);
                startActivity(intent6);
            }
        });

        CardView btn_journal_activite = (CardView) root.findViewById(R.id.btn_journal_activite)  ;
        btn_journal_activite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(getActivity(), EtatJournalActivite.class);
                startActivity(intent7);
            }
        });



        CardView  btn_cause_retour = (CardView) root.findViewById(R.id.btn_cause_retour) ;
        btn_cause_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getActivity(), ListeCauseRetour.class);
                startActivity(intent5);

            }
        });

        CardView btn_suivie_client = (CardView) root.findViewById(R.id.btn_suivie_client)  ;
        btn_suivie_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getActivity(), FicheClientActivity2.class);
                startActivity(intent6);
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


    public void dialogChoixActiviteJournalier(View v) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        //ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_choix_activite_journalier, null, false);

        Date currentDate = new Date();

        final TextView txt_date_debut = (TextView) dialogView.findViewById(R.id.txt_date_debut);
        final TextView txt_date_fin = (TextView) dialogView.findViewById(R.id.txt_date_fin);

        final CheckBox cb_select_tt = (CheckBox) dialogView.findViewById(R.id.cb_select_tt);

        final GridView gv_choix_type_mvmnt = (GridView) dialogView.findViewById(R.id.gv_choix_type_mvmnt);
        final ProgressBar pb = (ProgressBar) dialogView.findViewById(R.id.pb);


        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        // cal1.add(Calendar.MONTH, -3);
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


        ListTypeMvmntDisticntTask listTypeMvmntDisticntTask = new ListTypeMvmntDisticntTask(getActivity(), date_debut, date_fin, gv_choix_type_mvmnt, pb);
        listTypeMvmntDisticntTask.execute();


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

                                ListTypeMvmntDisticntTask listTypeMvmntDisticntTask = new ListTypeMvmntDisticntTask(getActivity(), date_debut, date_fin, gv_choix_type_mvmnt, pb);
                                listTypeMvmntDisticntTask.execute();


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

                                ListTypeMvmntDisticntTask listTypeMvmntDisticntTask = new ListTypeMvmntDisticntTask(getActivity(), date_debut, date_fin, gv_choix_type_mvmnt, pb);
                                listTypeMvmntDisticntTask.execute();


                            } catch (Exception e) {
                                Log.e("Exception--", " " + e.getMessage());
                            }

                        }
                    }
                }, year_x2, month_x2, day_x2);
                datePickerDialog.show();
            }
        });


        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();


        cb_select_tt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    for (TypeMouvementClick t_mvmnt : listType) {
                        t_mvmnt.setNbrClick(1);
                    }
                }
                else {
                    for (TypeMouvementClick t_mvmnt : listType) {
                        t_mvmnt.setNbrClick(0);
                    }
                }


                TypeMvmntGVAdapter typeMvmntGVAdapter = new TypeMvmntGVAdapter(getActivity()  ,  listType)  ;
                gv_choix_type_mvmnt.setAdapter(typeMvmntGVAdapter);
            }

        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("afficher_btn", "date_debut " + txt_date_debut.getText().toString());
                Log.e("afficher_btn", "date_fin " + txt_date_fin.getText().toString());


                boolean existe =  false  ;


                for (TypeMouvementClick t_mvmnt : listType) {

                    if (t_mvmnt.getNbrClick() ==1)
                    {
                        existe=true ;
                        break;
                    }

                }

                if (existe == false  )
                {
                    Toast.makeText(getActivity() , "Selectionnez au moin un type Mouvement" , Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent6 = new Intent(getActivity(), JournalActiviteActivity.class);

                    intent6.putExtra("cle_date_debut", txt_date_debut.getText().toString());
                    intent6.putExtra("cle_date_fin", txt_date_fin.getText().toString());

                    startActivity(intent6);
                }


            }
        });

        alertDialog.show();


    }

}