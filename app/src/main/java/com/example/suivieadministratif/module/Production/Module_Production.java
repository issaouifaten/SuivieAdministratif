package com.example.suivieadministratif.module.Production;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.Machine;
import com.example.suivieadministratif.param.Param;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class Module_Production extends AppCompatActivity {

    ArrayList<String> listLibelle = new ArrayList<>() ;
    ArrayList<Machine> listDepot = new ArrayList<Machine>() ;

    SearchableSpinner sp_Machine ,sp_Cannelure ,sp_Client ;


    ConnectionClass connectionClass;
    String user, password, base, ip;

    public static  RecyclerView rv_list_suivi_cmd_frns;
    public static  ProgressBar pb;


    public TextView txt_date_debut, txt_date_fin;

    int id_DatePickerDialog = 0;
    Date currentDate = new Date();
    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;

    public static Date date_debut = null;
    public static Date date_fin = null;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");

    public static TextView txt_tot_ht;
    SearchView search_bar_article;
    public   static   String  CodeDepotSelected  = "" ;
    public   static   String  DepotSelected  = "" ;
    public   static   String  term_rech_art  = "" ;
    public   static   String  CodeNatureArticleSelected  = "" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_production);


        SharedPreferences pref = getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip   = pref.getString("ip", ip);
        base = pref.getString("base", base);
        password = pref.getString("password", password);

        connectionClass = new ConnectionClass();


        sp_Client = findViewById(R.id.sp_Client);
        sp_Cannelure  = findViewById(R.id.sp_Cannelure);
        sp_Machine = findViewById(R.id.sp_Machine);


        txt_date_fin = findViewById(R.id.txt_date_fin);


        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(currentDate);

        year_x2 = cal2.get(Calendar.YEAR);
        month_x2 = cal2.get(Calendar.MONTH);
        day_x2 = cal2.get(Calendar.DAY_OF_MONTH);


        DecimalFormat df_month = new DecimalFormat("00");
        DecimalFormat df_year = new DecimalFormat("0000");
        String _date_au = df.format(cal2.getTime());
        txt_date_fin.setText(_date_au);
        txt_date_fin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 1;

                DatePickerDialog datePickerDialog = new DatePickerDialog(Module_Production.this, new DatePickerDialog.OnDateSetListener() {
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

                            }

                        }
                    }
                }, year_x2, month_x2, day_x2);
                datePickerDialog.show();
            }
        });


        String query = "select  * from  Machine";

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<String> data = new ArrayList<String>();
            data.add("Tout");
            while (rs.next()) {
                String Libelle = rs.getString("LibelleMachine");
                data.add(Libelle);

            }
            String[] array = data.toArray(new String[0]);


            ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getApplicationContext(),
                    R.layout.spinner, data);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_Machine.setAdapter(adapter);


        } catch (SQLException e) {
            e.printStackTrace();
            // Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }




        String query_ = "select * from Cannelure";

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);
            PreparedStatement stmt;
            stmt = con.prepareStatement(query);
            ResultSet rsss = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();
            data.add("Tout");
            while (rsss.next()) {
                String Libelle = rsss.getString("Libelle");
                data.add(Libelle);


            }
            String[] array = data.toArray(new String[0]);


            ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getApplicationContext(),
                    R.layout.spinner, data);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_Cannelure.setAdapter(adapter);


        } catch (SQLException e) {
            e.printStackTrace();
            // Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

        String query_client = "select * from Client";

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);
            Log.e("query_client", query_client) ;
            PreparedStatement stmt;
            stmt = con.prepareStatement(query_client);
            ResultSet rsss = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();
            data.add("Tout");
            while (rsss.next()) {
                String Libelle = rsss.getString("RaisonSociale");
                data.add(Libelle);

            }
            String[] array = data.toArray(new String[0]);


            ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getApplicationContext(),
                    R.layout.spinner, data);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_Cannelure.setAdapter(adapter);


        } catch (SQLException e) {
            e.printStackTrace();
            // Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }



    }
}