package com.example.suivieadministratif.menu;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.QuickContactBadge;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.suivieadministratif.R;

import com.example.suivieadministratif.module.tresorerie.ImpayeClientActivity;
import com.example.suivieadministratif.module.tresorerie.ImpayeFournisseurActivity;
import com.example.suivieadministratif.module.tresorerie.PortFeuilleChequeActivity;
import com.example.suivieadministratif.module.tresorerie.PortFeuilleTraiteActvity;
import com.example.suivieadministratif.module.tresorerie.ReacapEcheancierFournisseurActivity;
import com.example.suivieadministratif.module.tresorerie.RecapEcheancierClientActivity;
import com.example.suivieadministratif.task.ListBanqueTaskForSearchableSpinner;
import com.example.suivieadministratif.task.ListClientTaskForSearchSpinner;
import com.example.suivieadministratif.task.ListFournisseurTaskForSearchableSpinner;
import com.example.suivieadministratif.task.ListModeReglementTaskForSearchableSpinner;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MenuTresorerieFragment extends Fragment {

    private MenuViewModel menuViewModel;

    public static int select = 0; // 0 -->  client  1  -->  fournisseur

    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;
    public static int id_DatePickerDialog = 0;

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");

    Date date_debut = null;
    Date date_fin = null;

    public static String CodeModeRegSeleted = "";
    public static String ModeRegSeleted = "";

    public static String CodeClientSelected = "";
    public static String ClientSelected = "";

    public static String CodeBanqueSelected = "";
    public static String BanqueSelected = "";

    public static String CodeFournissseurSelected = "";
    public static String FournisseurSelected = "";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu_tresorerie, container, false);


        CardView card_client = (CardView) root.findViewById(R.id.card_client);
        CardView card_frns = (CardView) root.findViewById(R.id.card_fournisseur);

        final FrameLayout frm_frns = (FrameLayout) root.findViewById(R.id.frm_frns);
        final FrameLayout frm_client = (FrameLayout) root.findViewById(R.id.frm_client);


        //client
        final CardView btn_prtf_cheque = (CardView) root.findViewById(R.id.btn_portfeuil_cheque);
        final CardView btn_prtf_traite = (CardView) root.findViewById(R.id.btn_portfeuil_traite);
        final CardView btn_gestion_echeancier_client = (CardView) root.findViewById(R.id.btn_gestion_echeancier_client);
        final CardView btn_impaye_client = (CardView) root.findViewById(R.id.btn_impaye_client);


        //  fournisseur
        final CardView btn_gestion_echeancier_frns = (CardView) root.findViewById(R.id.btn_gestion_echeancier_frns);
        final CardView btn_impaye_fournisseur = (CardView) root.findViewById(R.id.btn_impaye_fournisseur);

        btn_gestion_echeancier_frns.setVisibility(View.GONE);
        btn_impaye_fournisseur.setVisibility(View.GONE);


        card_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                select = 0;
                frm_client.setBackgroundResource(R.drawable.back_ground_card);
                frm_frns.setBackgroundResource(R.drawable.back_gris);

                btn_prtf_cheque.setVisibility(View.VISIBLE);
                btn_prtf_traite.setVisibility(View.VISIBLE);
                btn_gestion_echeancier_client.setVisibility(View.VISIBLE);
                btn_impaye_client.setVisibility(View.VISIBLE);

                btn_gestion_echeancier_frns.setVisibility(View.GONE);
                btn_impaye_fournisseur.setVisibility(View.GONE);

            }
        });


        card_frns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                select = 1;
                frm_frns.setBackgroundResource(R.drawable.back_ground_card);
                frm_client.setBackgroundResource(R.drawable.back_gris);

                btn_prtf_cheque.setVisibility(View.GONE);
                btn_prtf_traite.setVisibility(View.GONE);
                btn_gestion_echeancier_client.setVisibility(View.GONE);
                btn_impaye_client.setVisibility(View.GONE);


                btn_gestion_echeancier_frns.setVisibility(View.VISIBLE);
                btn_impaye_fournisseur.setVisibility(View.VISIBLE);
            }
        });


        btn_prtf_cheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), PortFeuilleChequeActivity.class));
            }
        });


        btn_prtf_traite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), PortFeuilleTraiteActvity.class));
            }
        });


        btn_gestion_echeancier_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogChoixEcheancierClient(view);

            }
        });

        btn_gestion_echeancier_frns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogChoixEcheancierFourniseur(view);
                ;
            }
        });

        btn_impaye_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogImpayeClient(view);
            }
        });


        btn_impaye_fournisseur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogImpayeFournisseur(view);
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

    public void dialogImpayeClient(View v) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        //ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_impaye_client, null, false);


        SearchableSpinner sp_client = (SearchableSpinner) dialogView.findViewById(R.id.sp_client);
        SearchableSpinner sp_banque = (SearchableSpinner) dialogView.findViewById(R.id.sp_banque);
        SearchableSpinner sp_mode_reglement = (SearchableSpinner) dialogView.findViewById(R.id.sp_mode_reglement);

        final CheckBox cb_paye = (CheckBox) dialogView.findViewById(R.id.cb_paye);
        final CheckBox cb_p_remplace = (CheckBox) dialogView.findViewById(R.id.cb_pp);
        final CheckBox cb_nn_paye = (CheckBox) dialogView.findViewById(R.id.cb_non_paye);


        ListClientTaskForSearchSpinner listClientTaskForSearchableSpinner = new ListClientTaskForSearchSpinner(getActivity(), sp_client, "MenuTresorerieFragment");
        listClientTaskForSearchableSpinner.execute();


        ListBanqueTaskForSearchableSpinner listBanqueTaskForSearchableSpinner = new ListBanqueTaskForSearchableSpinner(getActivity(), sp_banque, "MenuTresorerieFragment");
        listBanqueTaskForSearchableSpinner.execute();


        ListModeReglementTaskForSearchableSpinner listModeReglementTaskForSearchableSpinner = new ListModeReglementTaskForSearchableSpinner(getActivity(), sp_mode_reglement);
        listModeReglementTaskForSearchableSpinner.execute();


        Date currentDate = new Date();


        final TextView txt_date_debut = (TextView) dialogView.findViewById(R.id.txt_date_debut);
        final TextView txt_date_fin = (TextView) dialogView.findViewById(R.id.txt_date_fin);


        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        cal1.add(Calendar.MONTH, -1);
        year_x1 = cal1.get(Calendar.YEAR);
        month_x1 = cal1.get(Calendar.MONTH);
        day_x1 = cal1.get(Calendar.DAY_OF_MONTH);


        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(currentDate);
        //cal2.add(Calendar.MONTH, +3);
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


        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("afficher_btn", "date_debut " + txt_date_debut.getText().toString());
                Log.e("afficher_btn", "date_fin " + txt_date_fin.getText().toString());
                Log.e("afficher_btn", "mode_reg " + CodeModeRegSeleted);
                Log.e("afficher_btn", "client " + CodeClientSelected);
                Log.e("afficher_btn", "banque " + CodeBanqueSelected);

                String Condition_etat = "in  ( ''  ";

                if (cb_paye.isChecked()) {

                    Condition_etat += " , 'E08' ,'E16' ";

                } else {

                    Condition_etat += "   ";
                }

                if (cb_p_remplace.isChecked()) {

                    Condition_etat += "    , 'E24' ";
                } else {

                    Condition_etat += "";
                }


                if (cb_nn_paye.isChecked()) {
                    Condition_etat += "  , 'E01' ";
                } else {
                    Condition_etat += "";
                }

                Condition_etat += " ) ";

                if (!cb_paye.isChecked() && !cb_nn_paye.isChecked() && !cb_p_remplace.isChecked()) {
                    Condition_etat = "";
                }

                Log.e("afficher_btn", "afficher_btn_etat  " + Condition_etat);

                Intent intent6 = new Intent(getActivity(), ImpayeClientActivity.class);

                intent6.putExtra("cle_date_debut", txt_date_debut.getText().toString());
                intent6.putExtra("cle_date_fin", txt_date_fin.getText().toString());

                intent6.putExtra("cle_code_client", CodeClientSelected);
                intent6.putExtra("cle_raison_sociale", ClientSelected);

                intent6.putExtra("cle_code_banque", CodeBanqueSelected);
                intent6.putExtra("cle_banque", BanqueSelected);

                intent6.putExtra("cle_code_mode_reglement", CodeModeRegSeleted);
                intent6.putExtra("cle_mode_reglement", ModeRegSeleted);

                intent6.putExtra("cle_condition_etat", Condition_etat);


                startActivity(intent6);

            }
        });

        alertDialog.show();


    }


    public void dialogImpayeFournisseur(View v) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        //ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_impaye_fournisseur, null, false);


        SearchableSpinner sp_fournisseur = (SearchableSpinner) dialogView.findViewById(R.id.sp_fournisseur);


        final CheckBox cb_inclure_mnt_min_max = (CheckBox) dialogView.findViewById(R.id.cb_inclure_mnt_min_max);
        final EditText ed_mnt_min = (EditText) dialogView.findViewById(R.id.ed_mnt_min);
        final EditText ed_mnt_max = (EditText) dialogView.findViewById(R.id.ed_mnt_max);


        final RadioButton rb_tt = (RadioButton) dialogView.findViewById(R.id.rb_tt);
        final RadioButton rb_impaye = (RadioButton) dialogView.findViewById(R.id.rb_impaye);
        final RadioButton rb_remplace = (RadioButton) dialogView.findViewById(R.id.rb_remplace);
        final RadioButton rb_verse = (RadioButton) dialogView.findViewById(R.id.rb_verse);


        ListFournisseurTaskForSearchableSpinner listFournisseurTaskForSearchableSpinner = new ListFournisseurTaskForSearchableSpinner(getActivity(), sp_fournisseur, "MenuTresorerieFragment");
        listFournisseurTaskForSearchableSpinner.execute();


        Date currentDate = new Date();


        final TextView txt_date_debut = (TextView) dialogView.findViewById(R.id.txt_date_debut);
        final TextView txt_date_fin = (TextView) dialogView.findViewById(R.id.txt_date_fin);


        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        cal1.add(Calendar.MONTH, -2);
        year_x1 = cal1.get(Calendar.YEAR);
        month_x1 = cal1.get(Calendar.MONTH);
        day_x1 = cal1.get(Calendar.DAY_OF_MONTH);


        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(currentDate);
        //cal2.add(Calendar.MONTH, +3);
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


        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("afficher_btn", "date_debut " + txt_date_debut.getText().toString());
                Log.e("afficher_btn", "date_fin " + txt_date_fin.getText().toString());
                Log.e("afficher_btn", "frns " + CodeFournissseurSelected);

                if (cb_inclure_mnt_min_max.isChecked()) {

                    Log.e("afficher_btn", "mnt_min_max " + "1");
                    Log.e("afficher_btn", "mnt_min " + Double.parseDouble(ed_mnt_min.getText().toString()));
                    Log.e("afficher_btn", "mnt_max " + Double.parseDouble(ed_mnt_max.getText().toString()));

                } else {
                    Log.e("afficher_btn", "mnt_min_max " + "0");
                }

                String  Condition_etat = ""  ;

                 if (rb_tt.isChecked())
                 {
                     Condition_etat = ""  ;
                 }
                 else if (rb_impaye.isChecked())
                 {
                     Condition_etat = "AND  ImpayeFournisseur.NumeroEtat  = 'E15' "  ;
                 }
                 else if (rb_remplace.isChecked())
                 {
                     Condition_etat = "AND  ImpayeFournisseur.NumeroEtat  = 'E16' "  ;
                 }

                 else if (rb_verse.isChecked())
                 {
                     Condition_etat = "AND  ImpayeFournisseur.NumeroEtat  = 'E14' "  ;
                 }

                Log.e("afficher_btn", Condition_etat );

                //Log.e("afficher_btn", "afficher_btn_etat  " +  Condition_etat );

                Intent intent6 = new Intent(getActivity(), ImpayeFournisseurActivity.class);

                intent6.putExtra("cle_date_debut", txt_date_debut.getText().toString());
                intent6.putExtra("cle_date_fin", txt_date_fin.getText().toString());

                intent6.putExtra("cle_code_fournisseur", CodeFournissseurSelected);
                intent6.putExtra("cle_raison_sociale", FournisseurSelected);

                intent6.putExtra("cle_condition_etat", Condition_etat );

                if (cb_inclure_mnt_min_max.isChecked()) {

                    intent6.putExtra("cle_mnt_min_max", "1");

                    intent6.putExtra("cle_mnt_min", Double.parseDouble(ed_mnt_min.getText().toString()));
                    intent6.putExtra("cle_mnt_max", Double.parseDouble(ed_mnt_max.getText().toString()));

                } else {
                    intent6.putExtra("cle_mnt_min_max", "0");

                }

                startActivity(intent6);

            }
        });

        alertDialog.show();


    }

    public void dialogChoixEcheancierClient(View v) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        //ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_gest_echeancier_client, null, false);


        SearchableSpinner sp_mode_reglement = (SearchableSpinner) dialogView.findViewById(R.id.sp_mode_reglement);

        Date currentDate = new Date();

        final TextView txt_date_debut = (TextView) dialogView.findViewById(R.id.txt_date_debut);
        final TextView txt_date_fin = (TextView) dialogView.findViewById(R.id.txt_date_fin);


        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        // cal1.add(Calendar.MONTH, -3);
        year_x1 = cal1.get(Calendar.YEAR);
        month_x1 = cal1.get(Calendar.MONTH);
        day_x1 = cal1.get(Calendar.DAY_OF_MONTH);


        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(currentDate);
        cal2.add(Calendar.MONTH, +3);
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


        ListModeReglementTaskForSearchableSpinner listModeReglementTaskForSearchableSpinner = new ListModeReglementTaskForSearchableSpinner(getActivity(), sp_mode_reglement);
        listModeReglementTaskForSearchableSpinner.execute();


        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("afficher_btn", "date_debut " + txt_date_debut.getText().toString());
                Log.e("afficher_btn", "date_fin " + txt_date_fin.getText().toString());
                Log.e("afficher_btn", "mode_reg " + CodeModeRegSeleted);
                Intent intent6 = new Intent(getActivity(), RecapEcheancierClientActivity.class);

                intent6.putExtra("cle_date_debut", txt_date_debut.getText().toString());
                intent6.putExtra("cle_date_fin", txt_date_fin.getText().toString());
                intent6.putExtra("cle_code_mode_reglement", CodeModeRegSeleted);
                intent6.putExtra("cle_mode_reglement", ModeRegSeleted);
                startActivity(intent6);

            }
        });

        alertDialog.show();


    }


    public void dialogChoixEcheancierFourniseur(View v) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        //ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_gest_echeancier_client, null, false);


        SearchableSpinner sp_mode_reglement = (SearchableSpinner) dialogView.findViewById(R.id.sp_mode_reglement);

        Date currentDate = new Date();

        final TextView txt_date_debut = (TextView) dialogView.findViewById(R.id.txt_date_debut);
        final TextView txt_date_fin = (TextView) dialogView.findViewById(R.id.txt_date_fin);

        final TextView txt_titre = (TextView) dialogView.findViewById(R.id.txt_titre);
        txt_titre.setText("Gestion Echéancier Fournisseur");


        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        // cal1.add(Calendar.MONTH, -3);
        year_x1 = cal1.get(Calendar.YEAR);
        month_x1 = cal1.get(Calendar.MONTH);
        day_x1 = cal1.get(Calendar.DAY_OF_MONTH);


        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(currentDate);
        cal2.add(Calendar.MONTH, +3);
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


        ListModeReglementTaskForSearchableSpinner listModeReglementTaskForSearchableSpinner = new ListModeReglementTaskForSearchableSpinner(getActivity(), sp_mode_reglement);
        listModeReglementTaskForSearchableSpinner.execute();


        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("afficher_btn", "date_debut " + txt_date_debut.getText().toString());
                Log.e("afficher_btn", "date_fin " + txt_date_fin.getText().toString());
                Log.e("afficher_btn", "mode_reg " + CodeModeRegSeleted);


                Intent intent6 = new Intent(getActivity(), ReacapEcheancierFournisseurActivity.class);

                intent6.putExtra("cle_date_debut", txt_date_debut.getText().toString());
                intent6.putExtra("cle_date_fin", txt_date_fin.getText().toString());
                intent6.putExtra("cle_code_mode_reglement", CodeModeRegSeleted);
                intent6.putExtra("cle_mode_reglement", ModeRegSeleted);
                startActivity(intent6);

            }
        });

        alertDialog.show();


    }
}