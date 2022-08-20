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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.menu.MenuViewModel;
import com.example.suivieadministratif.task.ListClientTaskForSearchableSpinner;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.FicheClientActivity2;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.IndicateurEncourClient;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.ListRetenuClientActivity;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.MoyenReglementClient;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.PieceNonPayeClient;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.RecouvrementClientEtenduActivity;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.SuivieCommandeClient;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.SuivieCommandeNonConformeClient;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Vente.JournalFactureVenteActivity;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StatClientFragment extends Fragment {

    private MenuViewModel menuViewModel;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;
    public static int id_DatePickerDialog = 0;

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");

    Date date_debut = null;
    Date date_fin = null;

    public static String CodeClient_selected = "";
    public static String RaisonClient_selected = "";



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



        CardView  btn_recouvrement_etendu  = (CardView)  root.findViewById(R.id.btn_recouvrement_etendu) ;
        btn_recouvrement_etendu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChoixRecouvrementClientEtendu ( v );
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




    public void dialogChoixRecouvrementClientEtendu  (View v) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        //ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_choix_reg_etendu , null, false);



        TextView txt_journal = (TextView)  dialogView.findViewById(R.id.txt_journal);
        LinearLayout ll_chargement_client = (LinearLayout)  dialogView.findViewById(R.id.ll_chargement_client);
        SearchableSpinner sp_client = (SearchableSpinner) dialogView.findViewById(R.id.sp_client);


       // txt_journal.setText("Journal Facture Vente");

        Date currentDate = new Date();

        final TextView txt_date_debut = (TextView) dialogView.findViewById(R.id.txt_date_debut);
        final TextView txt_date_fin = (TextView) dialogView.findViewById(R.id.txt_date_fin);


        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        //cal1.add(Calendar.MONTH, -1);
        year_x1 = cal1.get(Calendar.YEAR);
        month_x1 = cal1.get(Calendar.MONTH);
        day_x1 = 1;


        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(currentDate);

        year_x2 = cal2.get(Calendar.YEAR);
        month_x2 = cal2.get(Calendar.MONTH);
        day_x2 = cal2.get(Calendar.DAY_OF_MONTH);


        DecimalFormat df_month = new DecimalFormat("00");
        DecimalFormat df_year = new DecimalFormat("0000");

        Log.e("date_debut ", "01/" + df_month.format(cal1.get(Calendar.MONTH) + 1) + "/" + df_year.format(cal1.get(Calendar.YEAR)));
        String _date_du = "01/" + df_month.format(cal1.get(Calendar.MONTH) + 1) + "/" + df_year.format(cal1.get(Calendar.YEAR));

        try {
            date_debut = df.parse(_date_du);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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


        // client
        ListClientTaskForSearchableSpinner listClientTaskForSearchableSpinner  = new ListClientTaskForSearchableSpinner(getActivity(), sp_client , ll_chargement_client ,"dialogChoixRecouvrementClientEtendu");
        listClientTaskForSearchableSpinner.execute();


        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("afficher_btn", "date_debut " + txt_date_debut.getText().toString());
                Log.e("afficher_btn", "date_fin " + txt_date_fin.getText().toString());

                Log.e("afficher_btn", "Client " + CodeClient_selected);

                Intent intent6 = new Intent(getActivity(), RecouvrementClientEtenduActivity.class);

                intent6.putExtra("cle_date_debut", txt_date_debut.getText().toString());
                intent6.putExtra("cle_date_fin", txt_date_fin.getText().toString());

                intent6.putExtra("cle_code_client", CodeClient_selected);
                startActivity(intent6);

            }
        });

        alertDialog.show();


    }


}