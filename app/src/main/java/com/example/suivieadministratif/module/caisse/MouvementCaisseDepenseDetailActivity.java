package com.example.suivieadministratif.module.caisse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import   com.example.suivieadministratif.R ;
import com.example.suivieadministratif.activity.HomeActivity;
import com.example.suivieadministratif.task.ListeMouvementCaisseDepenceTask;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MouvementCaisseDepenseDetailActivity extends AppCompatActivity {

    ProgressBar pb;
    ListView lv_list_mvmnt_caisse  ;
    public TextView txt_date_debut, txt_date_fin;

    int id_DatePickerDialog = 0;
    Date currentDate = new Date();
    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;

    public static Date date_debut = null;
    public static Date date_fin   = null;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");

    RadioGroup rg_depense ;
    RadioButton rb_tout ,rb_personnel  ,rb_fournisseur  , rb_client ;

    public  static  String  TypeDepense="" ;

    public  static  TextView  txt_tot_depense ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouvement_caisse_depense_detail);


        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Caisse Dépense");


        txt_date_debut = findViewById(R.id.txt_date_debut);
        txt_date_fin = findViewById(R.id.txt_date_fin);
        lv_list_mvmnt_caisse  = findViewById( R.id.lv_list_mvmnt_caisse) ;
        pb = (ProgressBar) findViewById(R.id.pb);

        rg_depense=   findViewById(R.id.rg_type_caisse) ;
        rb_tout =  findViewById(R.id.rb_tout) ;
        rb_personnel =  findViewById(R.id.rb_personnel) ;
        rb_fournisseur = findViewById(R.id.rb_fournisseur) ;
        rb_client = findViewById(R.id.rb_client) ;

        txt_tot_depense = (TextView) findViewById(R.id.txt_total_depense) ;

        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        //cal1.add(Calendar.DAY_OF_YEAR, -7);
        year_x1  = cal1.get(Calendar.YEAR);
        month_x1 = cal1.get(Calendar.MONTH);
        day_x1   = cal1.get(Calendar.DAY_OF_MONTH);


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
        NavigationView nav_menu=findViewById(R.id.nav_view);
        View root = nav_menu.getHeaderView(0);
        CardView btn_mvmnt_caisse_depense  = (CardView) root.findViewById(R.id.btn_mouvement_caisse_depense );
        btn_mvmnt_caisse_depense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toMouvemntCaisseDep  = new Intent(getApplicationContext() , MouvementCaisseDepenseDetailActivity.class) ;
                startActivity(toMouvemntCaisseDep);

            }
        });



        CardView   btn_caisse_recette= (CardView) root.findViewById(R.id.btn_caisse_recette );
        btn_caisse_recette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCaisseRecette  = new Intent(getApplicationContext() , CaisseRecetteActivity.class) ;
                startActivity(toCaisseRecette);

            }
        });

        CardView   btn_home= (CardView) root.findViewById(R.id.btn_home );
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCaisseRecette  = new Intent(getApplicationContext() , HomeActivity.class) ;
                startActivity(toCaisseRecette);

            }
        });

        rb_tout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TypeDepense = "";
                updateData () ;
            }
        });


        rb_personnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TypeDepense = "N";
                updateData () ;
            }
        });

        rb_fournisseur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TypeDepense = "F";
                updateData () ;
            }
        });

        rb_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TypeDepense = "C";
                updateData () ;
            }
        });


        updateData () ;

        txt_date_debut.setOnClickListener(new View.OnClickListener  ()  {
            @Override
            public void onClick(View v)  {

                id_DatePickerDialog = 0;
                Log.e("month_x1", "On picker  : " + month_x1);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MouvementCaisseDepenseDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                                updateData () ;


                            } catch (Exception e) {
                                Log.e("Exception--", " " + e.getMessage());
                            }
                        }
                    }
                }, year_x1, month_x1, day_x1);
                datePickerDialog.show();
            }
        });



        txt_date_fin.setOnClickListener(new View.OnClickListener()  {

            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 1;

                DatePickerDialog datePickerDialog = new DatePickerDialog(MouvementCaisseDepenseDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                                updateData () ;

                            } catch (Exception e) {
                                Log.e("Exception --", " " + e.getMessage());
                            }

                        }
                    }
                }, year_x2, month_x2, day_x2);
                datePickerDialog.show();
            }
        });



    }



    public   void   updateData ()
    {
        ListeMouvementCaisseDepenceTask listeMouvementCaisseDepenceTask  = new ListeMouvementCaisseDepenceTask(this ,lv_list_mvmnt_caisse , pb ,date_debut ,date_fin ,TypeDepense) ;
        listeMouvementCaisseDepenceTask.execute() ;

    }
}